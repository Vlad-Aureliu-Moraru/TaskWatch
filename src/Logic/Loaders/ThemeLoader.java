package Logic.Loaders;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ThemeLoader {

    private static final Pattern RGB_PATTERN = Pattern.compile("rgb\\s*\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)");
    private static final Map<String, Color> colorMap = new HashMap<>();
    private static final String CONFIG_DIR = "config/";
    private static String currentThemeFile;

    private static final List<ThemeChangeListener> listeners = new ArrayList<>();

    static {
        loadTheme();
    }

    private ThemeLoader() {
    }

    public static void loadTheme() {
        String themeFileName = "theme_" + ConfigLoader.getTheme();
        File file = new File(CONFIG_DIR + themeFileName);

        if (!file.exists()) {
            try {
                ThemeManager.writeDefaultTheme(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
                String key = parts[0].trim().substring(2); // remove --
                String value = parts[1].trim().replaceAll(";", "");

                Color color = parseColor(value);
                if (color != null) {
                    colorMap.put(key, color);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading theme file. Using default colors.");
        }

        // Ensure all colors from enum exist
        for (ThemeColorKey key : ThemeColorKey.values()) {
            colorMap.putIfAbsent(key.getKey(), key.getDefaultColor());
        }
    }

    public static Color parseColor(String value) {
        value = value.replaceAll("\\s", "");
        Matcher rgbMatcher = RGB_PATTERN.matcher(value);
        if (rgbMatcher.matches()) {
            return new Color(
                    Integer.parseInt(rgbMatcher.group(1)),
                    Integer.parseInt(rgbMatcher.group(2)),
                    Integer.parseInt(rgbMatcher.group(3))
            );
        }
        return null;
    }

    public static Color getColor(ThemeColorKey key) {
        return colorMap.getOrDefault(key.getKey(), key.getDefaultColor());
    }

    public static void setColor(ThemeColorKey key, Color color) {
        colorMap.put(key.getKey(), color);
        saveThemeFile();
        notifyThemeChanged();
    }

    public static Color getDifficultyColor(int difficulty) {
        switch (difficulty) {
            case 1:
                return getColor(ThemeColorKey.DIFFICULTY1);
            case 2:
                return getColor(ThemeColorKey.DIFFICULTY2);
            case 3:
                return getColor(ThemeColorKey.DIFFICULTY3);
            case 4:
                return getColor(ThemeColorKey.DIFFICULTY4);
            case 5:
                return getColor(ThemeColorKey.DIFFICULTY5);
        }
        return null;
    }

    public static Color getUrgencyColor(int difficulty) {
        switch (difficulty) {
            case 1:
                return getColor(ThemeColorKey.URGENCY1);
            case 2:
                return getColor(ThemeColorKey.URGENCY2);
            case 3:
                return getColor(ThemeColorKey.URGENCY3);
            case 4:
                return getColor(ThemeColorKey.URGENCY4);
            case 5:
                return getColor(ThemeColorKey.URGENCY5);
        }
        return null;
    }

    private static void saveThemeFile() {
        File file = new File(currentThemeFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (ThemeColorKey key : ThemeColorKey.values()) {
                Color color = colorMap.get(key.getKey());
                writer.write(String.format("--%s:rgb(%d, %d, %d);", key.getKey(), color.getRed(), color.getGreen(), color.getBlue()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addThemeChangeListener(ThemeChangeListener listener) {
        listeners.add(listener);
    }

    public static void removeThemeChangeListener(ThemeChangeListener listener) {
        listeners.remove(listener);
    }
    public static void notifyThemeChanged() {
        for (ThemeChangeListener listener : listeners) {
            listener.onThemeChanged();
        }
    }
}
