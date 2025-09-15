package ConfigRelated;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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



    // Private static fields to hold the configuration values.
    private static String theme;
    private static int clockUpdateTime;

    /**
     * The static initializer block loads the configuration file.
     * It also creates a default file if one is not found.
     */
    static {
     loadConfig();
    }

    public static void  loadConfig(){
        File configFile = new File(CONFIG_FILE_PATH);

        // Check if the file exists, if not, create it with default values
        if (!configFile.exists()) {
            System.out.println("Config file not found. Creating a default config file.");
            createDefaultConfigFile(configFile);
        }

        Map<String, String> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    configMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading configuration file. Using default values.");
            e.printStackTrace();
        }

        // Initialize fields from the map, using defaults if a key is missing
        theme = configMap.getOrDefault("THEME", "config.txt");
        try {
            clockUpdateTime = Integer.parseInt(configMap.getOrDefault("CLOCK_UPDATE_TIME", "5"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for CLOCK_UPDATE_TIME. Using default value.");
            clockUpdateTime = 5;
        }
    }

    private static void createDefaultConfigFile(File file) {
        // Ensure the parent directories exist
        file.getParentFile().mkdirs();

        String defaultContent = """
            THEME:config.txt
            CLOCK_UPDATE_TIME:5
            """;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(defaultContent);
        } catch (IOException e) {
            System.err.println("Failed to create default config file.");
            e.printStackTrace();
        }
    }

    /**
     * Rewrites the entire config file with the current values.
     */
    private static void updateConfigFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH))) {
            writer.write("THEME:" + theme);
            writer.newLine();
            writer.write("CLOCK_UPDATE_TIME:" + clockUpdateTime);
        } catch (IOException e) {
            System.err.println("Failed to update config file.");
            e.printStackTrace();
        }
    }

    // Public getters for accessing the configuration values
    public static String getTheme() {
        return theme;
    }

    public static int getClockUpdateTime() {
        return clockUpdateTime;
    }

    // Public setters for changing the configuration values
    public static void setTheme(String newTheme) {
        if (newTheme == null || newTheme.trim().isEmpty()) {
            System.err.println("Theme name cannot be empty.");
            return;
        }

        // Construct the full path to the potential new theme file
        String fullPath = CONFIG_DIR + THEME_PREFIX + newTheme;
        File themeFile = new File(fullPath);

        // Check if the theme file actually exists
        if (themeFile.exists()) {
            System.out.println("Theme file '" + newTheme + "' found. Updating configuration.");
            theme = newTheme;
            updateConfigFile();
        } else {
            System.err.println("Error: Theme file '" + newTheme + "' does not exist. Configuration not updated.");
        }
    }
    public static void setClockUpdateTime(int newTime) {
        clockUpdateTime = newTime;
        updateConfigFile();
    }
}