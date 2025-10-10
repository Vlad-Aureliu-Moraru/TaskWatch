package UserInterface.SubPanels.REMINDER;

import AppLogic.Directory;
import AppLogic.Task;
import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;
import Handlers.EventHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PANEL_weeklyschedule extends JScrollPane implements ThemeChangeListener {

    private final JPanel panel = new JPanel();

    private final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private final Map<String, ArrayList<PANEL_item>> schedule = new HashMap<>();
    private final Map<String, JLabel> dayLabels = new HashMap<>();

    private Color backgroundColor = ThemeLoader.getColor(ThemeColorKey.PANEL_WEEKLYSCHEDULE);
    private Color textColor =ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT);


    private EventHandler eventHandler;
    private int HEIGHT, WIDTH;

    public PANEL_weeklyschedule() {
        // Base setup
        panel.setBackground(backgroundColor);
        ThemeLoader.addThemeChangeListener(this);
        panel.setLayout(null);

        this.setViewportView(panel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setViewportBorder(BorderFactory.createEmptyBorder());
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);
        this.getVerticalScrollBar().setUnitIncrement(50);
        this.setAutoscrolls(true);
        this.setWheelScrollingEnabled(true);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(textColor, 2);
        panel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        // Initialize structure for each day
        for (String day : days) {
            schedule.put(day, new ArrayList<>());
            JLabel label = new JLabel(day.toUpperCase(), SwingConstants.CENTER);
            label.setForeground(textColor);
            label.setFont(new Font("Segoe UI", Font.BOLD, 13));
            label.setOpaque(true);
            label.setBackground(backgroundColor);
            dayLabels.put(day, label);
        }
    }

    // Add task to a specific day
    public void addMon(Directory d, Task t) { addTaskToDay("Mon", d, t); }
    public void addTue(Directory d, Task t) { addTaskToDay("Tue", d, t); }
    public void addWed(Directory d, Task t) { addTaskToDay("Wed", d, t); }
    public void addThu(Directory d, Task t) { addTaskToDay("Thu", d, t); }
    public void addFri(Directory d, Task t) { addTaskToDay("Fri", d, t); }
    public void addSat(Directory d, Task t) { addTaskToDay("Sat", d, t); }
    public void addSun(Directory d, Task t) { addTaskToDay("Sun", d, t); }

    public void addTaskToDay(String day, Directory dir, Task task) {
        ArrayList<PANEL_item> list = schedule.get(day);
        if (list == null) return;
        PANEL_item item = new PANEL_item(dir, task);
        if (!list.contains(item)) list.add(item);
    }

    // Draw the weekly view horizontally (7 columns)
    public void loadItems() {
        panel.removeAll();

        if (WIDTH <= 0 || HEIGHT <= 0) return;

        int columnWidth = (WIDTH-10) / 7;       // divide total width into 7 equal columns
        int labelHeight = 30;
        int topPadding = 10;

        int maxColumnHeight = 0;

        for (int i = 0; i < days.length; i++) {
            String day = days[i];
            int x = i * columnWidth;

            // Day label
            JLabel label = dayLabels.get(day);
            label.setBounds(x+14, 20, columnWidth-24, labelHeight);
            panel.add(label);

            int currentY = labelHeight + topPadding;
            ArrayList<PANEL_item> items = schedule.get(day);

            if (items != null && !items.isEmpty()) {
                for (PANEL_item item : items) {
                    item.setHEIGHTandWIDTH(30, columnWidth - 15);
                    item.setEventHandler(eventHandler);
                    item.setBounds(x + 12, currentY+10, columnWidth - 15, 30);
                    item.setFontSize(11);
                    panel.add(item);
                    currentY += 32;
                }
            } else {
                JLabel empty = new JLabel("No tasks", SwingConstants.CENTER);
                empty.setForeground(Color.GRAY);
                empty.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                empty.setBounds(x, currentY+5, columnWidth, 20);
                panel.add(empty);
                currentY += 30;
            }

            maxColumnHeight = Math.max(maxColumnHeight, currentY + 20);
        }

        // Fit horizontally; scroll only vertically
        panel.setPreferredSize(new Dimension(WIDTH, Math.max(maxColumnHeight, HEIGHT)));
        panel.repaint();
        panel.revalidate();
    }

    public void setHEIGHTandWIDTH(int height, int width) {
        this.HEIGHT = height;
        this.WIDTH = width;
        loadItems();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void clearAll() {
        for (ArrayList<PANEL_item> list : schedule.values()) list.clear();
        panel.removeAll();
        panel.repaint();
        panel.revalidate();
    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
    @Override
    public void onThemeChanged() {
        backgroundColor = ThemeLoader.getColor(ThemeColorKey.PANEL_WEEKLYSCHEDULE);
        textColor = ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT);

        // Update panel and scroll background
        panel.setBackground(backgroundColor);
        this.setBackground(backgroundColor);

        // Update border colors
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(textColor, 2);
        panel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        // Update all day labels
        for (JLabel label : dayLabels.values()) {
            label.setForeground(textColor);
            label.setBackground(backgroundColor);
        }

        // (Optional) also update all PANEL_item components if they use theme colors
        for (ArrayList<PANEL_item> list : schedule.values()) {
            for (PANEL_item item : list) {
                item.onThemeChanged(); // if PANEL_item also implements ThemeChangeListener
            }
        }

        panel.repaint();
        panel.revalidate();
    }

}
