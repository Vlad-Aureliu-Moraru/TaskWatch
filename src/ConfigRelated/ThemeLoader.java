package ConfigRelated;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages loading, retrieving, and updating the application's color theme.
 * The theme file name is determined by the ConfigLoader class.
 */
public final class ThemeLoader {

    // Regex patterns for parsing color values from the file
    private static final Pattern RGB_PATTERN = Pattern.compile("rgb\\s*\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)");

    // The in-memory storage for color values
    private static final Map<String, Color> colorMap = new HashMap<>();

    // The file path for the currently loaded theme
    private static String currentThemeFile;
    private static final String CONFIG_DIR = "config/";

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

    // Static initializer block to load the theme on class access
    static {
        // NOTE: In a real app, this would get the theme file name from your ConfigLoader.
        // For this runnable example, we'll use a hardcoded value.

        loadTheme();
    }

    // Private constructor to prevent instantiation
    private ThemeLoader() {}


    public static void loadTheme() {
        String themeFileName = "theme_"+ConfigLoader.getTheme();
        System.out.println("loaded theme " + ConfigLoader.getTheme());
        File file = new File(CONFIG_DIR + themeFileName);

        if (!file.exists()) {
            System.out.println("Theme file not found. Creating a default theme file: " +file.getName());
            createThemeFile(file);
        }
        colorMap.clear();
        currentThemeFile = file.getPath();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || !line.contains(":") || line.startsWith("{") || line.startsWith("}")) {
                    continue;
                }

                String[] parts = line.split(":", 2);
                String key = parts[0].trim().substring(2);
                String value = parts[1].trim().replaceAll(";", "");

                Color color = parseColor(value);
                if (color != null) {
                    colorMap.put(key, color);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading theme file. Using default colors.");
            e.printStackTrace();
            // Set some default colors here if a full reload fails.
            setFallbackColors();
        }
    }

    /**
     * Helper method to parse a color string (RGB or Hex) into a Color object.
     * @param value The string to parse.
     * @return The corresponding Color object, or null if parsing fails.
     */
    public static Color parseColor(String value) {
        value = value.replaceAll("\\s", "");
        Matcher rgbMatcher =RGB_PATTERN.matcher(value);
        if (rgbMatcher.matches()) {
            try {
                int r = Integer.parseInt(rgbMatcher.group(1));
                int g = Integer.parseInt(rgbMatcher.group(2));
                int b = Integer.parseInt(rgbMatcher.group(3));
                return new Color(r, g, b);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    return null;
    }

    /**
     * Sets fallback colors in case the theme file cannot be loaded.
     */
    private static void setFallbackColors() {
        colorMap.put("main_color", new Color(57, 50, 50));
        colorMap.put("secondary_color", new Color(77, 69, 69));
        colorMap.put("first_accent", new Color(141, 98, 98));
        colorMap.put("secnd_accent", new Color(237, 141, 141));
        colorMap.put("secondary_green", new Color(53, 155, 112));
        colorMap.put("accent_green", new Color(33, 50, 73));
        colorMap.put("dirColor", new Color(55, 56, 103));
        colorMap.put("dirHoverColor", new Color(36, 37, 72));
        colorMap.put("taskColor", new Color(57, 50, 50));
        colorMap.put("taskHoverColor", new Color(33, 30, 30));
        colorMap.put("taskTextColor", new Color(255, 255, 255));
        colorMap.put("noteColor", new Color(211, 164, 89));
        colorMap.put("pausedTimerColor", new Color(0xFFFFFF));
        colorMap.put("urgency1", new Color(156, 246, 213));
        colorMap.put("urgency2", new Color(116, 215, 129));
        colorMap.put("urgency3", new Color(255, 242, 0));
        colorMap.put("urgency4", new Color(255, 106, 0));
        colorMap.put("urgency5", new Color(255, 0, 0));
        colorMap.put("urgency1List", new Color(141, 215, 188));
        colorMap.put("urgency2List", new Color(88, 171, 101));
        colorMap.put("urgency3List", new Color(190, 185, 64));
        colorMap.put("urgency4List", new Color(192, 107, 46));
        colorMap.put("urgency5List", new Color(192, 76, 76));
        colorMap.put("difficulty1", new Color(156, 246, 213));
        colorMap.put("difficulty2", new Color(116, 215, 129));
        colorMap.put("difficulty3", new Color(255, 242, 0));
        colorMap.put("difficulty4", new Color(255, 106, 0));
        colorMap.put("difficulty5", new Color(255, 0, 0));
        colorMap.put("TaskCompletedIconColor", new Color(4, 189, 255));
        colorMap.put("TaskUrgentIconColor", new Color(255, 4, 79));
        colorMap.put("TaskUrgentPassed", new Color(162, 161, 171, 255));
        colorMap.put("consoleColor", new Color(82, 94, 84));
        colorMap.put("consoleTextColor", new Color(222, 222, 222));
        colorMap.put("timerOnBreakColor", new Color(75, 111, 122));
        colorMap.put("timerOnPrepColor", new Color(8, 76, 34));
    }

    /**
     * Writes the current in-memory color map back to the theme file.
     */
    private static void saveThemeFile() {
        File file = new File(currentThemeFile);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                Color color = entry.getValue();
                // Write colors back in a consistent RGB format
                String line = String.format("--%s:rgb(%d, %d, %d);", entry.getKey(), color.getRed(), color.getGreen(), color.getBlue());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save theme file.");
            e.printStackTrace();
        }
    }

    /**
     * Creates a new theme file with the default values.
     * @param file The file object to write to.
     */
    public static void createThemeFile(File file) {
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(DEFAULT_THEME_CONTENT);
        } catch (IOException e) {
            System.err.println("Failed to create default theme file.");
            e.printStackTrace();
        }
    }

    // All Getters for the theme colors
    public static Color getMainColor() { return colorMap.get("main_color"); }
    public static Color getSecondaryColor() { return colorMap.get("secondary_color"); }
    public static Color getFirstAccent() { return colorMap.get("first_accent"); }
    public static Color getSecndAccent() { return colorMap.get("secnd_accent"); }
    public static Color getSecondaryGreen() { return colorMap.get("secondary_green"); }
    public static Color getAccentGreen() { return colorMap.get("accent_green"); }
    public static Color getDirColor() { return colorMap.get("dirColor"); }
    public static Color getDirHoverColor() { return colorMap.get("dirHoverColor"); }
    public static Color getTaskColor() { return colorMap.get("taskColor"); }
    public static Color getTaskHoverColor() { return colorMap.get("taskHoverColor"); }
    public static Color getTaskTextColor() { return colorMap.get("taskTextColor"); }
    public static Color getNoteColor() { return colorMap.get("noteColor"); }
    public static Color getPausedTimerColor() { return colorMap.get("pausedTimerColor"); }
    public static Color getUrgency1() { return colorMap.get("urgency1"); }
    public static Color getUrgency2() { return colorMap.get("urgency2"); }
    public static Color getUrgency3() { return colorMap.get("urgency3"); }
    public static Color getUrgency4() { return colorMap.get("urgency4"); }
    public static Color getUrgency5() { return colorMap.get("urgency5"); }
    public static Color getUrgency1List() { return colorMap.get("urgency1List"); }
    public static Color getUrgency2List() { return colorMap.get("urgency2List"); }
    public static Color getUrgency3List() { return colorMap.get("urgency3List"); }
    public static Color getUrgency4List() { return colorMap.get("urgency4List"); }
    public static Color getUrgency5List() { return colorMap.get("urgency5List"); }
    public static Color getDifficulty1() { return colorMap.get("difficulty1"); }
    public static Color getDifficulty2() { return colorMap.get("difficulty2"); }
    public static Color getDifficulty3() { return colorMap.get("difficulty3"); }
    public static Color getDifficulty4() { return colorMap.get("difficulty4"); }
    public static Color getDifficulty5() { return colorMap.get("difficulty5"); }
    public static Color getTaskCompletedIconColor() { return colorMap.get("TaskCompletedIconColor"); }
    public static Color getTaskUrgentIconColor() { return colorMap.get("TaskUrgentIconColor"); }
    public static Color getTaskUrgentPassed() { return colorMap.get("TaskUrgentPassed"); }
    public static Color getConsoleColor() { return colorMap.get("consoleColor"); }
    public static Color getConsoleTextColor() { return colorMap.get("consoleTextColor"); }
    public static Color getTimerOnBreakColor() { return colorMap.get("timerOnBreakColor"); }
    public static Color getTimerOnPrepColor() { return colorMap.get("timerOnPrepColor"); }

    // All Setters for the theme colors
    public static void setMainColor(Color color) { colorMap.put("main_color", color);
        System.out.println("gotten " + color);
        saveThemeFile(); }
    public static void setSecondaryColor(Color color) { colorMap.put("secondary_color", color); saveThemeFile(); }
    public static void setFirstAccent(Color color) { colorMap.put("first_accent", color); saveThemeFile(); }
    public static void setSecndAccent(Color color) { colorMap.put("secnd_accent", color); saveThemeFile(); }
    public static void setSecondaryGreen(Color color) { colorMap.put("secondary_green", color); saveThemeFile(); }
    public static void setAccentGreen(Color color) { colorMap.put("accent_green", color); saveThemeFile(); }
    public static void setDirColor(Color color) { colorMap.put("dirColor", color); saveThemeFile(); }
    public static void setDirHoverColor(Color color) { colorMap.put("dirHoverColor", color); saveThemeFile(); }
    public static void setTaskColor(Color color) { colorMap.put("taskColor", color); saveThemeFile(); }
    public static void setTaskHoverColor(Color color) { colorMap.put("taskHoverColor", color); saveThemeFile(); }
    public static void setTaskTextColor(Color color) { colorMap.put("taskTextColor", color); saveThemeFile(); }
    public static void setNoteColor(Color color) { colorMap.put("noteColor", color); saveThemeFile(); }
    public static void setPausedTimerColor(Color color) { colorMap.put("pausedTimerColor", color); saveThemeFile(); }
    public static void setUrgency1(Color color) { colorMap.put("urgency1", color); saveThemeFile(); }
    public static void setUrgency2(Color color) { colorMap.put("urgency2", color); saveThemeFile(); }
    public static void setUrgency3(Color color) { colorMap.put("urgency3", color); saveThemeFile(); }
    public static void setUrgency4(Color color) { colorMap.put("urgency4", color); saveThemeFile(); }
    public static void setUrgency5(Color color) { colorMap.put("urgency5", color); saveThemeFile(); }
    public static void setUrgency1List(Color color) { colorMap.put("urgency1List", color); saveThemeFile(); }
    public static void setUrgency2List(Color color) { colorMap.put("urgency2List", color); saveThemeFile(); }
    public static void setUrgency3List(Color color) { colorMap.put("urgency3List", color); saveThemeFile(); }
    public static void setUrgency4List(Color color) { colorMap.put("urgency4List", color); saveThemeFile(); }
    public static void setUrgency5List(Color color) { colorMap.put("urgency5List", color); saveThemeFile(); }
    public static void setDifficulty1(Color color) { colorMap.put("difficulty1", color); saveThemeFile(); }
    public static void setDifficulty2(Color color) { colorMap.put("difficulty2", color); saveThemeFile(); }
    public static void setDifficulty3(Color color) { colorMap.put("difficulty3", color); saveThemeFile(); }
    public static void setDifficulty4(Color color) { colorMap.put("difficulty4", color); saveThemeFile(); }
    public static void setDifficulty5(Color color) { colorMap.put("difficulty5", color); saveThemeFile(); }
    public static void setTaskCompletedIconColor(Color color) { colorMap.put("TaskCompletedIconColor", color); saveThemeFile(); }
    public static void setTaskUrgentIconColor(Color color) { colorMap.put("TaskUrgentIconColor", color); saveThemeFile(); }
    public static void setTaskUrgentPassed(Color color) { colorMap.put("TaskUrgentPassed", color); saveThemeFile(); }
    public static void setConsoleColor(Color color) { colorMap.put("consoleColor", color); saveThemeFile(); }
    public static void setConsoleTextColor(Color color) { colorMap.put("consoleTextColor", color); saveThemeFile(); }
    public static void setTimerOnBreakColor(Color color) { colorMap.put("timerOnBreakColor", color); saveThemeFile(); }
    public static void setTimerOnPrepColor(Color color) { colorMap.put("timerOnPrepColor", color); saveThemeFile(); }
    public static Color getUrgency(int urgency){
        switch (urgency){
            case 1:
                return colorMap.get("urgency1");
            case 2:
                return colorMap.get("urgency2");
            case 3:
                return colorMap.get("urgency3");
            case 4:
                return colorMap.get("urgency4");
            case 5:
                return colorMap.get("urgency5");
        }
        return null;
    }
    public static Color getDifficulty(int difficulty){
        switch (difficulty){
            case 1:
                return colorMap.get("difficulty1");
            case 2:
                return colorMap.get("difficulty2");
            case 3:
                return colorMap.get("difficulty3");
            case 4:
                return colorMap.get("difficulty4");
            case 5:
                return colorMap.get("difficulty5");
        }
        return null;
    }
}