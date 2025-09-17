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

    private static final String TERMINAL_FONT_PATH = "/fonts/Font2.ttf";
    private static final String COZY_FONT_PATH = "/fonts/Font1.ttf";

    private FontLoader() {
        // Private constructor to prevent instantiation of the utility class.
    }

    /**
     * Lazily loads and returns the custom "cozy" font.
     * The font is loaded only once and then cached for subsequent calls.
     * @return The loaded Font object or a default font if loading fails.
     */
    public static Font getCozyFont() {
        if (cozyFont == null) {
            cozyFont = loadFont(COZY_FONT_PATH);
        }
        return cozyFont;
    }

    /**
     * Lazily loads and returns the custom "terminal" font.
     * The font is loaded only once and then cached for subsequent calls.
     * @return The loaded Font object or a default font if loading fails.
     */
    public static Font getTerminalFont() {
        if (terminalFont == null) {
            terminalFont = loadFont(TERMINAL_FONT_PATH);
        }
        return terminalFont;
    }

    /**
     * A private helper method to handle the core font loading logic.
     * It uses a try-with-resources statement to ensure the stream is closed.
     *
     * @param fontPath The path to the font file in the classpath.
     * @return The loaded Font object or a default font if loading fails.
     */
    private static Font loadFont(String fontPath) {
        // The try-with-resources statement ensures the InputStream is closed
        // automatically, even if an exception occurs.
        try (InputStream fontStream = FontLoader.class.getResourceAsStream(fontPath)) {
            if (fontStream != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

                // Register the font with the graphics environment
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);

                return customFont;
            } else {
                // This is the most likely error case: the file was not found on the classpath.
                System.err.println("Font file not found at the specified path: " + fontPath);
            }
        } catch (IOException | FontFormatException e) {
            // Handles errors during file reading or invalid font format.
            System.err.println("Error loading custom font from path: " + fontPath);
            e.printStackTrace();
        }

        // Return a default font if any error occurs
        return new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    }
}