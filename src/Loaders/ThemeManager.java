package Loaders;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ThemeManager handles theme file creation, deletion, and listing.
 * Theme files are stored in the "config" directory and follow the naming pattern: "theme_<name>.css".
 * Each theme file defines color properties based on {@link ThemeColorKey}.
 */
public final class ThemeManager {

    private static final String CONFIG_DIR = "config/";
    private static final String THEME_PREFIX = "theme_";
    private static final String THEME_EXTENSION = ".css";
    private static final String DEFAULT_THEME_FILE_NAME = THEME_PREFIX + "default" + THEME_EXTENSION;

    // Prevent instantiation
    private ThemeManager() {}

    /**
     * Creates a new theme file with default colors.
     *
     * @param themeName the desired theme name (without prefix or extension)
     * @throws IllegalArgumentException if the name is invalid
     * @throws IOException if file creation or writing fails
     */
    public static void createThemeFile(String themeName) throws IOException {
        validateThemeName(themeName);
        File configDir = new File(CONFIG_DIR);
        if (!configDir.exists() && !configDir.mkdirs()) {
            throw new IOException("Failed to create config directory: " + CONFIG_DIR);
        }

        File themeFile = new File(CONFIG_DIR + THEME_PREFIX + themeName + THEME_EXTENSION);
        if (themeFile.exists()) {
            throw new IOException("Theme '" + themeName + "' already exists.");
        }

        writeDefaultTheme(themeFile);
        System.out.println("[ThemeManager] Created new theme: " + themeFile.getAbsolutePath());
    }

    /**
     * Deletes a theme file safely (cannot delete the default theme).
     *
     * @param themeName the name of the theme to delete
     * @throws IllegalArgumentException if the name is invalid or the default theme
     * @throws IOException if deletion fails
     */
    public static void deleteThemeFile(String themeName) throws IOException {
        validateThemeName(themeName);

        if ("default".equalsIgnoreCase(themeName)) {
            throw new IllegalArgumentException("Cannot delete the default theme.");
        }

        File themeFile = new File(CONFIG_DIR + THEME_PREFIX + themeName + THEME_EXTENSION);
        if (!themeFile.exists()) {
            throw new FileNotFoundException("Theme '" + themeName + "' does not exist.");
        }

        if (!themeFile.delete()) {
            throw new IOException("Failed to delete theme file: " + themeFile.getAbsolutePath());
        }

        System.out.println("[ThemeManager] Deleted theme: " + themeName);
    }

    /**
     * Lists all available themes in the config directory.
     *
     * @return a list of available theme names (without prefix or extension)
     */
    public static List<String> listAvailableThemes() {
        List<String> themes = new ArrayList<>();
        File configDir = new File(CONFIG_DIR);

        if (!configDir.exists() || !configDir.isDirectory()) {
            System.err.println("[ThemeManager] No config directory found: " + CONFIG_DIR);
            return themes;
        }

        File[] files = configDir.listFiles((dir, name) ->
                name.startsWith(THEME_PREFIX) && name.endsWith(THEME_EXTENSION)
        );

        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                String themeName = name.substring(THEME_PREFIX.length(), name.length() - THEME_EXTENSION.length());
                themes.add(themeName);
            }
        }

        return themes;
    }

    /**
     * Writes a theme file with all default colors.
     *
     * @param file the file to write to
     * @throws IOException if writing fails
     */
    public static void writeDefaultTheme(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("{\n");
            for (ThemeColorKey key : ThemeColorKey.values()) {
                Color c = key.getDefaultColor();
                writer.write(String.format("  --%s: rgb(%d, %d, %d);\n", key.getKey(), c.getRed(), c.getGreen(), c.getBlue()));
            }
            writer.write("}\n");
        }
    }

    /**
     * Validates a theme name to prevent invalid filesystem characters.
     *
     * @param name the theme name to validate
     * @throws IllegalArgumentException if invalid
     */
    private static void validateThemeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Theme name cannot be null or empty.");
        }
        if (!name.matches("[A-Za-z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid theme name: " + name +
                    ". Only letters, digits, underscores, and hyphens are allowed.");
        }
    }
}
