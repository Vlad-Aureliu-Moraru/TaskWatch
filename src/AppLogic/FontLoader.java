package AppLogic;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

/**
 * A utility class to load a custom font from the classpath.
 * The font is loaded only once, the first time it is requested.
 */
public class FontLoader {

    private static Font terminalFont = null;
    private static Font cozyFont = null;

    private static final String terminalFontPath= "/fonts/terminalFont.ttf";
    private static final String cozyFontPath= "/fonts/cozyFont.ttf";

    private FontLoader() {
        // Private constructor to prevent instantiation of the utility class.
    }

    /**
     * Lazily loads and returns the custom font.
     * The font is loaded only once and then cached for subsequent calls.
     * @return The loaded Font object or a default font if loading fails.
     */
    public static Font getCozyFont() {
        if (cozyFont == null) {
            try {
                // The font path is relative to the classpath root.
                // Assuming the font is in src/main/resources/font/
                InputStream fontStream = FontLoader.class.getResourceAsStream(cozyFontPath);

                if (fontStream != null) {
                    cozyFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

                    // Register the font with the graphics environment
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(cozyFont);

                    fontStream.close(); // Close the stream
                } else {
                    // This is the most likely error case: the file was not found on the classpath.
                    System.err.println("Font file not found at the specified path.");
                    cozyFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
                }
            } catch (IOException | FontFormatException e) {
                // Handles errors during file reading or invalid font format
                System.err.println("Error loading custom font.");
                e.printStackTrace();
                cozyFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
            }
        }
        return cozyFont;
    }
    public static Font getTerminalFont() {
        if (terminalFont== null) {
            try {
                // The font path is relative to the classpath root.
                // Assuming the font is in src/main/resources/font/
                InputStream fontStream = FontLoader.class.getResourceAsStream(terminalFontPath);

                if (fontStream != null) {
                    terminalFont= Font.createFont(Font.TRUETYPE_FONT, fontStream);

                    // Register the font with the graphics environment
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(terminalFont);

                    fontStream.close(); // Close the stream
                } else {
                    // This is the most likely error case: the file was not found on the classpath.
                    System.err.println("Font file not found at the specified path.");
                    terminalFont= new Font(Font.SANS_SERIF, Font.PLAIN, 12);
                }
            } catch (IOException | FontFormatException e) {
                // Handles errors during file reading or invalid font format
                System.err.println("Error loading custom font.");
                e.printStackTrace();
                terminalFont= new Font(Font.SANS_SERIF, Font.PLAIN, 12);
            }
        }
        return terminalFont;
    }
}