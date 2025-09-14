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
    private static final String DEFAULT_THEME_FILE_NAME = THEME_PREFIX + "default.txt";

    private static final String DEFAULT_THEME_CONTENT = """
        main_color=(57, 50, 50);
        secondary_color=(77, 69, 69);
        first_accent=(141, 98, 98);
        secnd_accent=(237, 141, 141);
        secondary_green=(53, 155, 112);
        accent_green=(33, 50, 73);
        dirColor=(55, 56, 103);
        dirHoverColor=(36, 37, 72);
        taskColor=(57, 50, 50);
        taskHoverColor=(33, 30, 30);
        taskTextColor=(255, 255, 255);
        noteColor=(211, 164, 89);
        pausedTimerColor=(0xFFFFFF);
        urgency1=(156, 246, 213);
        urgency2=(116, 215, 129);
        urgency3=(255, 242, 0);
        urgency4=(255, 106, 0);
        urgency5=(255, 0, 0);
        urgency1List=(141, 215, 188);
        urgency2List=(88, 171, 101);
        urgency3List=(190, 185, 64);
        urgency4List=(192, 107, 46);
        urgency5List=(192, 76, 76);
        difficulty1=(156, 246, 213);
        difficulty2=(116, 215, 129);
        difficulty3=(255, 242, 0);
        difficulty4=(255, 106, 0);
        difficulty5=(255, 0, 0);
        TaskCompletedIconColor=(4, 189, 255);
        TaskUrgentIconColor=(255, 4, 79);
        TaskUrgentPassed=(162, 161, 171, 255);
        consoleColor=(82, 94, 84);
        consoleTextColor=(222, 222, 222);
        timerOnBreakColor=(75, 111, 122);
        timerOnPrepColor=(8, 76, 34);
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
     * @param filename The desired filename for the new theme (e.g., "dark.txt").
     */
    public static void createThemeFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            System.err.println("Filename cannot be empty or null.");
            return;
        }

        String fullFileName = THEME_PREFIX + filename+".txt";
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
            File[] files = configDir.listFiles((dir, name) -> name.startsWith(THEME_PREFIX) && name.endsWith(".txt"));
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

        String fullFileName = THEME_PREFIX + filename+".txt";
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