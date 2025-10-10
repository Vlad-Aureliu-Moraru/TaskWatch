package UserInterface.SubPanels.REMINDER;

import AppLogic.Directory;
import Loaders.FontLoader;
import AppLogic.Task;
import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;
import Handlers.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_item extends JPanel implements ThemeChangeListener {
    private Task currentTask;
    private Directory directory;
    private Color backgroundColor;
    private JLabel titleLabel = new JLabel();
    private EventHandler eventHandler;


    public PANEL_item(Directory directory, Task task) {
        this.currentTask = task;
        this.directory = directory;
        ThemeLoader.addThemeChangeListener(this);
        this.setLayout(null);

        titleLabel.setText(directory.getName() + "/" + task.getName());
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setFont(FontLoader.getTerminalFont().deriveFont(Font.PLAIN, 15));

        add(titleLabel);
        setUrgencyColor();
        this.setBackground(backgroundColor);

        // 🔹 Add hover and click effects
        addMouseEffects();
    }

    public void setHEIGHTandWIDTH(int height, int width) {
        titleLabel.setBounds(5, 5, width, height-15);
        if (width < 65) {
            titleLabel.setText(currentTask.getName());
        } else {
            titleLabel.setText(directory.getName() + "/" + currentTask.getName());
        }
    }

    private void setUrgencyColor() {
        switch (currentTask.getUrgency()) {
            case 1 -> backgroundColor = ThemeLoader.getColor(ThemeColorKey.URGENCY1_LIST);
            case 2 -> backgroundColor = ThemeLoader.getColor(ThemeColorKey.URGENCY2_LIST);
            case 3 -> backgroundColor = ThemeLoader.getColor(ThemeColorKey.URGENCY3_LIST);
            case 4 -> backgroundColor = ThemeLoader.getColor(ThemeColorKey.URGENCY4_LIST);
            case 5 -> backgroundColor = ThemeLoader.getColor(ThemeColorKey.URGENCY5_LIST);
            default -> backgroundColor = ThemeLoader.getColor(ThemeColorKey.MAIN_COLOR);
        }
    }

    public void setFontSize(int fontSize) {
        titleLabel.setFont(FontLoader.getTerminalFont().deriveFont(Font.PLAIN, fontSize));
    }

    private void addMouseEffects() {
        Color originalColor = backgroundColor;
        Color hoverColor =darkenColor(originalColor, 0.15f);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalColor);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(eventHandler);
                if (eventHandler != null) {
                    eventHandler.getPanelMainmenu().getPanel_clock().deactivate();
                    eventHandler.getPanelMainmenu().getPanel_taskinfo().setVisible(true);
                    eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(currentTask);
                }else{
                    System.out.println("Clicked on task: " + currentTask.getName());
                }
            }
        });
    }

    private Color darkenColor(Color color, float factor) {
        int r = (int) Math.max(0, color.getRed() * (1 - factor));
        int g = (int) Math.max(0, color.getGreen() * (1 - factor));
        int b = (int) Math.max(0, color.getBlue() * (1 - factor));
        return new Color(r, g, b);
    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onThemeChanged() {
    setUrgencyColor();
    this.setBackground(backgroundColor);
    this.repaint();
    this.revalidate();
    }
}
