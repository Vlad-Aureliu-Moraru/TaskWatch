#!/usr/bin/env bash
set -euo pipefail

APP_NAME="TaskWatch"
DESKTOP_FILENAME="taskwatch.desktop"

echo "Uninstall TaskWatch"
echo "1) Uninstall for current user (no sudo)"
echo "2) Uninstall system-wide (requires sudo)"
read -r -p "Choice [1/2] (default 1): " CHOICE
CHOICE="${CHOICE:-1}"

if [ "$CHOICE" = "1" ]; then
  rm -rf "$HOME/.local/share/${APP_NAME}"
  rm -f "$HOME/.local/share/applications/${DESKTOP_FILENAME}"
  rm -f "$HOME/.local/share/icons/hicolor/128x128/apps/TaskWatch.png" || true
  rm -f "$HOME/.local/bin/taskwatch" || true
  echo "User uninstall complete."

else
  if [ "$(id -u)" -ne 0 ]; then
    SUDO="sudo"
  else
    SUDO=""
  fi
  $SUDO rm -rf "/opt/${APP_NAME}"
  $SUDO rm -f "/usr/share/applications/${DESKTOP_FILENAME}"
  $SUDO rm -f "/usr/share/icons/hicolor/128x128/apps/TaskWatch.png" || true
  $SUDO rm -f "/usr/local/bin/taskwatch" || true
  echo "System uninstall complete."
fi

echo "Done."

