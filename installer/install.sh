#!/usr/bin/env bash
set -euo pipefail

APP_NAME="TaskWatch"
APP_DIR_NAME="TaskWatch"           # name of directory bundled by your tar (adjust if different)
LAUNCHER_REL="bin/TaskWatch"      # relative path to the launcher inside the app dir
ICON_FILENAME="TaskWatch.png"      # name of your icon file inside app resources
DESKTOP_FILENAME="taskwatch.desktop"
USER_CHOICE=""

# helper: absolute path to the app dir (the install script assumes you run it from the archive root)
# inside install.sh
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
APP_IMAGE_DIR="$SCRIPT_DIR/../TaskWatch"

if [ ! -d "$APP_IMAGE_DIR" ]; then
    echo "Error: TaskWatch app-image not found in $APP_IMAGE_DIR"
    exit 1
fi

echo "Install TaskWatch"
echo "1) Install for current user (no sudo)"
echo "2) System-wide install (requires sudo)"
read -r -p "Choose install type [1/2] (default 1): " USER_CHOICE
USER_CHOICE="${USER_CHOICE:-1}"

# confirm overwrites
confirm_overwrite() {
  local path="$1"
  if [ -e "$path" ]; then
    read -r -p "'$path' already exists. Overwrite? [y/N]: " ans
    case "$ans" in
      [Yy]*) return 0 ;;
      *) echo "Aborting."; exit 1 ;;
    esac
  fi
}

# create desktop file contents (user or system paths will replace placeholders)
write_desktop_file() {
  local exec_path="$1"
  local icon_name="$2"   # either "taskwatch" (if put in icon theme) or absolute path
  local dest="$3"
  cat > "$dest" <<EOF
[Desktop Entry]
Version=1.0
Type=Application
Name=${APP_NAME}
GenericName=To-Do Application
Comment=${APP_NAME} — to-do app with reminders and Pomodoro timer
Exec=${exec_path} %U
Icon=${icon_name}
Terminal=false
Categories=Utility;Office;Productivity;
StartupNotify=true
StartupWMClass=${APP_NAME}
EOF
  chmod 644 "$dest"
}

# ===== USER install =====
if [ "$USER_CHOICE" = "1" ]; then
  DEST_DIR="$HOME/.local/share/${APP_NAME}"
  DESKTOP_DEST="$HOME/.local/share/applications/${DESKTOP_FILENAME}"
  ICON_DIR="$HOME/.local/share/icons/hicolor/128x128/apps"
  BIN_LINK="$HOME/.local/bin/taskwatch"

  mkdir -p "$DEST_DIR" "$ICON_DIR" "$HOME/.local/share/applications" "$HOME/.local/bin"
  confirm_overwrite "$DEST_DIR"
  rm -rf "$DEST_DIR"
  cp -r "$SRC_DIR" "$DEST_DIR"
  echo "Copied app to $DEST_DIR"

# copy icon to icon theme
if [ -f "$DEST_DIR/lib/${ICON_FILENAME}" ]; then
    cp "$DEST_DIR/lib/${ICON_FILENAME}" "$ICON_DIR/taskwatch.png"
    ICON_SPEC="taskwatch"
else
    ICON_SPEC="${DEST_DIR}/lib/${ICON_FILENAME}"
fi

  # make a symlink in ~/.local/bin for command-line launching
  ln -sf "${DEST_DIR}/${LAUNCHER_REL}" "$BIN_LINK"
  chmod +x "${DEST_DIR}/${LAUNCHER_REL}"
  echo "Created symlink: $BIN_LINK -> ${DEST_DIR}/${LAUNCHER_REL}"

  # write desktop file
  write_desktop_file "${DEST_DIR}/${LAUNCHER_REL}" "${ICON_SPEC}" "$DESKTOP_DEST"
  echo "Installed desktop file to $DESKTOP_DEST"

  # update caches if tools exist
  if command -v gtk-update-icon-cache >/dev/null 2>&1; then
    gtk-update-icon-cache -f --ignore-theme-index "$HOME/.local/share/icons/hicolor" || true
  fi
  if command -v update-desktop-database >/dev/null 2>&1; then
    update-desktop-database "$HOME/.local/share/applications" || true
  fi

  echo "Installation complete. If your menu doesn't show TaskWatch, try logging out/in or run: 'gtk-launch taskwatch' or execute: $BIN_LINK"

# ===== SYSTEM install =====
elif [ "$USER_CHOICE" = "2" ]; then
  if [ "$(id -u)" -eq 0 ]; then
    SUDO=""
  else
    SUDO="sudo"
  fi

  DEST_DIR="/opt/${APP_NAME}"
  DESKTOP_DEST="/usr/share/applications/${DESKTOP_FILENAME}"
  ICON_DIR="/usr/share/icons/hicolor/128x128/apps"
  BIN_LINK="/usr/local/bin/taskwatch"

  $SUDO mkdir -p "$DEST_DIR" "$ICON_DIR" "$(dirname "$DESKTOP_DEST")"
  if $SUDO test -e "$DEST_DIR" ; then
    read -r -p "'$DEST_DIR' already exists. Overwrite? [y/N]: " ans
    case "$ans" in
      [Yy]*) $SUDO rm -rf "$DEST_DIR" ;;
      *) echo "Aborting."; exit 1 ;;
    esac
  fi

  $SUDO cp -r "$SRC_DIR" "$DEST_DIR"
  echo "Copied app to $DEST_DIR"

# copy icon to system icon theme
if $SUDO test -f "$DEST_DIR/lib/${ICON_FILENAME}" ; then
    $SUDO cp "$DEST_DIR/lib/${ICON_FILENAME}" "$ICON_DIR/taskwatch.png"
    ICON_SPEC="taskwatch"
else
    ICON_SPEC="${DEST_DIR}/lib/${ICON_FILENAME}"
fi

  # make launcher executable and symlink
  $SUDO chmod +x "${DEST_DIR}/${LAUNCHER_REL}"
  $SUDO ln -sf "${DEST_DIR}/${LAUNCHER_REL}" "$BIN_LINK"
  echo "Created symlink: $BIN_LINK -> ${DEST_DIR}/${LAUNCHER_REL}"

  # write desktop file (use sudo tee)
  tmpfile="$(mktemp)"
  write_desktop_file "${DEST_DIR}/${LAUNCHER_REL}" "${ICON_SPEC}" "$tmpfile"
  $SUDO mv "$tmpfile" "$DESKTOP_DEST"
  $SUDO chmod 644 "$DESKTOP_DEST"
  echo "Installed desktop file to $DESKTOP_DEST"

  # update caches if available
  if $SUDO bash -c 'command -v gtk-update-icon-cache >/dev/null 2>&1'; then
    $SUDO gtk-update-icon-cache -f --ignore-theme-index /usr/share/icons/hicolor || true
  fi
  if $SUDO bash -c 'command -v update-desktop-database >/dev/null 2>&1'; then
    $SUDO update-desktop-database /usr/share/applications || true
  fi

  echo "System-wide installation complete. You can launch via Start Menu or run: sudo $BIN_LINK (or $BIN_LINK if your user is allowed)"

else
  echo "Invalid choice. Exiting."
  exit 1
fi

echo "Done."

