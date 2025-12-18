package UserInterface.SubPanels;

import Logic.Loaders.ConfigLoader;
import Logic.Loaders.ThemeChangeListener;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class BarBorder extends AbstractBorder implements ThemeChangeListener {

    private int maxValue = 100;
    private int currentValue = 0;

    private final int barThickness = ConfigLoader.getBarWidth();
    private final int barSpacing = ConfigLoader.getBarSpacing();

    private Color startColor = ThemeLoader.getColor(ThemeColorKey.PROG_BAR_GRADIENT1);
    private Color midColor   = ThemeLoader.getColor(ThemeColorKey.PROG_BAR_GRADIENT2);
    private Color endColor   = ThemeLoader.getColor(ThemeColorKey.PROG_BAR_GRADIENT3);

    public void setGradientColors(Color start, Color mid, Color end) {
        this.startColor = start;
        this.midColor = mid;
        this.endColor = end;
    }

    // REQUIRED API
    public void setMaxValue(int maxValue) {
        this.maxValue = Math.max(1, maxValue);
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = Math.max(0, Math.min(currentValue, maxValue));
    }

    @Override
    public void paintBorder(Component c, Graphics g,
                            int x, int y, int width, int height) {

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double progress = (double) currentValue / maxValue;
        int perimeter = 2 * (width + height);
        int filled = (int) (perimeter * progress);

        int consumed = 0;

        // TOP
        consumed = drawHorizontal(
                g2d, x, y,
                width, filled, consumed, true
        );

        // RIGHT
        consumed = drawVertical(
                g2d, x + width - barThickness, y,
                height, filled, consumed, true
        );

        // BOTTOM
        consumed = drawHorizontal(
                g2d, x, y + height - barThickness,
                width, filled, consumed, false
        );

        // LEFT
        drawVertical(
                g2d, x, y,
                height, filled, consumed, false
        );

        g2d.dispose();
    }

    // ---------- Helpers ----------

    private int drawHorizontal(Graphics2D g2d, int x, int y, int length,
                               int filled, int consumed, boolean leftToRight) {

        for (int i = 0; i + barThickness <= length; i += barThickness + barSpacing) {
            if (consumed >= filled) return consumed;

            float r = (float) consumed / filled;
            g2d.setColor(interpolateGradient(r));

            int drawX = leftToRight ? x + i : x + length - i - barThickness;
            g2d.fillRect(drawX, y, barThickness, barThickness);

            consumed += barThickness + barSpacing;
        }
        return consumed;
    }

    private int drawVertical(Graphics2D g2d, int x, int y, int length,
                             int filled, int consumed, boolean topToBottom) {

        for (int i = 0; i + barThickness <= length; i += barThickness + barSpacing) {
            if (consumed >= filled) return consumed;

            float r = (float) consumed / filled;
            g2d.setColor(interpolateGradient(r));

            int drawY = topToBottom ? y + i : y + length - i - barThickness;
            g2d.fillRect(x, drawY, barThickness, barThickness);

            consumed += barThickness + barSpacing;
        }
        return consumed;
    }

    private Color interpolateGradient(float t) {
        t = Math.max(0f, Math.min(1f, t));

        if (t < 0.5f) {
            return interpolate(startColor, midColor, t * 2f);
        } else {
            return interpolate(midColor, endColor, (t - 0.5f) * 2f);
        }
    }

    private Color interpolate(Color c1, Color c2, float r) {
        return new Color(
                (int) (c1.getRed()   * (1 - r) + c2.getRed()   * r),
                (int) (c1.getGreen() * (1 - r) + c2.getGreen() * r),
                (int) (c1.getBlue()  * (1 - r) + c2.getBlue()  * r)
        );
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(barThickness, barThickness, barThickness, barThickness);
    }
    public void setBarThickness(int barThickness) {
        barThickness = barThickness;
    }
    public void setBarSpacing(int barSpacing) {
        barSpacing = barSpacing;
    }

    @Override
    public void onThemeChanged() {
        setGradientColors(ThemeLoader.getColor(ThemeColorKey.PROG_BAR_GRADIENT1),ThemeLoader.getColor(ThemeColorKey.PROG_BAR_GRADIENT2),ThemeLoader.getColor(ThemeColorKey.PROG_BAR_GRADIENT3));
    }
}
