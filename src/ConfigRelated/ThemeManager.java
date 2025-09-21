package ConfigRelated;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ThemeManager {

    private static final String CONFIG_DIR = "config/";
    private static final String THEME_PREFIX = "theme_";
    private static final String DEFAULT_THEME_FILE_NAME = THEME_PREFIX + "default.css";

    private static final String DEFAULT_THEME_CONTENT = """
        {
        --main_color: rgb(57, 50, 50);
        --secondary_color: rgb(77, 69, 69);
        --first_accent: rgb(141, 98, 98);
        --secnd_accent: rgb(237, 141, 141);
        --secondary_green: rgb(53, 155, 112);
        --accent_green: rgb(33, 50, 73);
        --dirColor: rgb(55, 56, 103);
        --dirHoverColor: rgb(36, 37, 72);
        --taskColor: rgb(57, 50, 50);
        --taskHoverColor: rgb(33, 30, 30);
        --taskTextColor: rgb(255, 255, 255);
        --noteColor: rgb(211, 164, 89);
        --pausedTimerColor: rgb(255,255,255);
        --urgency1: rgb(156, 246, 213);
        --urgency2: rgb(116, 215, 129);
        --urgency3: rgb(255, 242, 0);
        --urgency4: rgb(255, 106, 0);
        --urgency5: rgb(255, 0, 0);
        --urgency1List: rgb(141, 215, 188);
        --urgency2List: rgb(88, 171, 101);
        --urgency3List: rgb(190, 185, 64);
        --urgency4List: rgb(192, 107, 46);
        --urgency5List: rgb(192, 76, 76);
        --difficulty1: rgb(156, 246, 213);
        --difficulty2: rgb(116, 215, 129);
        --difficulty3: rgb(255, 242, 0);
        --difficulty4: rgb(255, 106, 0);
        --difficulty5: rgb(255, 0, 0);
        --TaskCompletedIconColor: rgb(4, 189, 255);
        --TaskUrgentIconColor: rgb(255, 4, 79);
        --TaskUrgentPassed: rgb(162, 161, 171);
        --consoleColor: rgb(82, 94, 84);
        --consoleTextColor: rgb(222, 222, 222);
        --timerOnBreakColor: rgb(75, 111, 122);
        --timerOnPrepColor: rgb(8, 76, 34);
        }
        """;

    static {
        File defaultFile = new File(CONFIG_DIR + DEFAULT_THEME_FILE_NAME);
        if (!defaultFile.exists()) {
            System.out.println("Default theme file not found. Creating it now...");
            writeDefaultTheme(defaultFile);
        }
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ThemeManager() {}

    /**
     * Creates a new theme file with the default values.
     * The filename will be automatically prepended with "theme_".
     *
     * @param filename The desired filename for the new theme (e.g., "dark.css").
     */
    public static void createThemeFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            System.err.println("Filename cannot be empty or null.");
            return;
        }

        String fullFileName = THEME_PREFIX + filename+".css";
        File newThemeFile = new File(CONFIG_DIR + fullFileName);

        if (newThemeFile.exists()) {
            System.out.println("Theme file '" + fullFileName + "' already exists. No new file created.");
        } else {
            System.out.println("Creating new theme file: " + fullFileName);
            writeDefaultTheme(newThemeFile);
        }
    }

    public static List<String> listAvailableThemes() {
        List<String> themeNames = new ArrayList<>();
        File configDir = new File(CONFIG_DIR);

        if (configDir.exists() && configDir.isDirectory()) {
            File[] files = configDir.listFiles((dir, name) -> name.startsWith(THEME_PREFIX) && name.endsWith(".css"));
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();
                    // Remove the prefix and extension to get a clean name
                    themeNames.add(name.substring(THEME_PREFIX.length(), name.length() - 4));
                }
            }
        }
        return themeNames;
    }
    public static void deleteThemeFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            System.err.println("Filename cannot be empty or null.");
            return;
        }

        // Prevent deletion of the crucial default theme file
        if (filename.equals(DEFAULT_THEME_FILE_NAME.replace(THEME_PREFIX, ""))) {
            System.err.println("Cannot delete the default theme file.");
            return;
        }

        String fullFileName = THEME_PREFIX + filename+".css";
        File themeFile = new File(CONFIG_DIR + fullFileName);

        if (themeFile.exists()) {
            if (themeFile.delete()) {
                System.out.println("Successfully deleted theme file: " + fullFileName);
            } else {
                System.err.println("Failed to delete theme file: " + fullFileName);
            }
        } else {
            System.out.println("Theme file '" + fullFileName + "' not found. Nothing to delete.");
        }
    }
    private static void writeDefaultTheme(File file) {
        file.getParentFile().mkdirs(); // Ensure the directory exists
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(DEFAULT_THEME_CONTENT);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + file.getName());
            e.printStackTrace();
        }
    }
}