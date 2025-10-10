package UserInterface.SubPanels;

import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * A simple modern flat-style custom scrollbar UI.
 */
public class CustomScrollBarUI extends BasicScrollBarUI implements ThemeChangeListener {

    private static final int THUMB_ARC = 0;
    private static final int THUMB_WIDTH = 8;
    public CustomScrollBarUI() {
        ThemeLoader.addThemeChangeListener(this);
    }

    @Override
    protected void configureScrollBarColors() {
        thumbColor = ThemeLoader.getColor(ThemeColorKey.SCROLL_THUMB);
        trackColor = ThemeLoader.getColor(ThemeColorKey.SCROLL_TRACK);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setOpaque(false);
        button.setFocusable(false);
        button.setVisible(false);
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (!scrollbar.isEnabled() || thumbBounds.width > thumbBounds.height && scrollbar.getOrientation() == JScrollBar.HORIZONTAL)
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = scrollbar.isEnabled() ? thumbColor : new Color(100, 100, 100, 80);
        g2.setColor(color);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, THUMB_ARC, THUMB_ARC);

        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(trackColor);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2.dispose();
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(THUMB_WIDTH, THUMB_WIDTH);
    }

    @Override
    public void onThemeChanged() {
        configureScrollBarColors();
        if (scrollbar != null) {
            scrollbar.repaint();
        }
    }
}
