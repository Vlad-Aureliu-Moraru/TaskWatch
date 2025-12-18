package Logic.Loaders;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for managing application configuration.
 * It loads from a file, provides getters and setters, and updates the file on changes.
 */
public final class ConfigLoader {

    private static final String CONFIG_FILE_PATH = "config/config.txt";
    private static final String CONFIG_DIR = "config/";
    private static final String THEME_PREFIX = "theme_";

    // Configuration values
    private static String theme;
    private static int clockUpdateTime;
    private static boolean showCompleted;
    private static boolean showWeeklySchedule;
    private static boolean showFocusedSchedule;

    private static int barSpacing;
    private static int barWidth;

    static {
        loadConfig();
    }

    public static void loadConfig() {
        File configFile = new File(CONFIG_FILE_PATH);

        if (!configFile.exists()) {
            System.out.println("Config file not found. Creating default config file.");
            createDefaultConfigFile(configFile);
        }

        Map<String, String> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    configMap.put(parts[0].trim().toUpperCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading configuration file. Using default values.");
            e.printStackTrace();
        }

        theme = configMap.getOrDefault("THEME", "default.css");

        clockUpdateTime = parseInt(configMap.get("CLOCK_UPDATE_TIME"), 5);
        showCompleted = parseBoolean(configMap.getOrDefault("SHOW_COMPLETED", "false"));
        showWeeklySchedule = parseBoolean(configMap.getOrDefault("SHOW_WEEKLYSCHEDULE", "false"));
        showFocusedSchedule = parseBoolean(configMap.getOrDefault("SHOW_FOCUSEDSCHEDULE", "true"));

        barSpacing = Math.max(0, parseInt(configMap.get("BAR_SPACING"), 0));
        barWidth   = Math.max(1, parseInt(configMap.get("BAR_WIDTH"), 3));
    }

    private static void createDefaultConfigFile(File file) {
        file.getParentFile().mkdirs();

        String defaultContent = """
            THEME:default.css
            CLOCK_UPDATE_TIME:5
            SHOW_COMPLETED:false
            SHOW_WEEKLYSCHEDULE:false
            SHOW_FOCUSEDSCHEDULE:true
            BAR_SPACING:0
            BAR_WIDTH:3
            """;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(defaultContent);
        } catch (IOException e) {
            System.err.println("Failed to create default config file.");
            e.printStackTrace();
        }
    }

    private static void updateConfigFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH))) {
            writer.write("THEME:" + theme); writer.newLine();
            writer.write("CLOCK_UPDATE_TIME:" + clockUpdateTime); writer.newLine();
            writer.write("SHOW_COMPLETED:" + showCompleted); writer.newLine();
            writer.write("SHOW_WEEKLYSCHEDULE:" + showWeeklySchedule); writer.newLine();
            writer.write("SHOW_FOCUSEDSCHEDULE:" + showFocusedSchedule); writer.newLine();
            writer.write("BAR_SPACING:" + barSpacing); writer.newLine();
            writer.write("BAR_WIDTH:" + barWidth);
        } catch (IOException e) {
            System.err.println("Failed to update config file.");
            e.printStackTrace();
        }
    }

    // -------- Utilities --------

    private static boolean parseBoolean(String value) {
        return value != null &&
                (value.equalsIgnoreCase("true")
                        || value.equalsIgnoreCase("yes")
                        || value.equals("1"));
    }

    private static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    // -------- Getters --------

    public static String getTheme() { return theme; }
    public static int getClockUpdateTime() { return clockUpdateTime; }
    public static boolean isShowCompleted() { return showCompleted; }
    public static boolean isShowWeeklySchedule() { return showWeeklySchedule; }
    public static boolean isShowFocusedSchedule() { return showFocusedSchedule; }

    public static int getBarSpacing() { return barSpacing; }
    public static int getBarWidth() { return barWidth; }

    // -------- Setters --------

    public static void setTheme(String newTheme) {
        if (newTheme == null || newTheme.trim().isEmpty()) {
            System.err.println("Theme name cannot be empty.");
            return;
        }

        File themeFile = new File(CONFIG_DIR + THEME_PREFIX + newTheme);
        if (themeFile.exists()) {
            theme = newTheme;
            updateConfigFile();
            ThemeLoader.notifyThemeChanged();
        } else {
            System.err.println("Theme file '" + newTheme + "' not found.");
        }
    }

    public static void setClockUpdateTime(int newTime) {
        clockUpdateTime = newTime;
        updateConfigFile();
    }

    public static void setShowCompleted(boolean value) {
        showCompleted = value;
        updateConfigFile();
    }

    public static void setShowWeeklySchedule(boolean value) {
        showWeeklySchedule = value;
        updateConfigFile();
    }

    public static void setShowFocusedSchedule(boolean value) {
        showFocusedSchedule = value;
        updateConfigFile();
    }

    public static void setBarSpacing(int spacing) {
        barSpacing = Math.max(0, spacing);
        updateConfigFile();
    }

    public static void setBarWidth(int width) {
        barWidth = Math.max(1, width);
        updateConfigFile();
    }
}
