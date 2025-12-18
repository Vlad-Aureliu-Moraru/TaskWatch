package UserInterface;

import UserInterface.CLI.PANEL_cli;
import Logic.Handlers.EventHandler;
import Logic.Loaders.ThemeChangeListener;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;
import UserInterface.SubPanels.REMINDER.PANEL_reminder;
import UserInterface.SubPanels.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_mainmenu extends JPanel implements ThemeChangeListener {

    private final PANEL_cli panel_cli = new PANEL_cli();
    private final PANEL_taskinfo panel_taskinfo = new PANEL_taskinfo();
    private final PANEL_clock panel_clock = new PANEL_clock();
    private final PANEL_noteinfo panel_noteinfo = new PANEL_noteinfo();
    private final PANEL_reminder panel_reminder = new PANEL_reminder();
    private final PANEL_help panel_help = new PANEL_help();

    private Color backgroundColor;

    public PANEL_mainmenu() {
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_MAINMENU));
        ThemeLoader.addThemeChangeListener(this);
        this.setLayout(null);

        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
//        this.setBorder(compoundBorder);

        this.add(panel_cli);
        this.add(panel_help);
        this.add(panel_taskinfo);
        this.add(panel_clock);
        this.add(panel_noteinfo);
        this.add(panel_reminder);
    }

    public void setHEIGHTandWIDTH(int width, int height) {
        int textBarHeight = 30;
        panel_clock.setBounds(20, 20, width - 40, (height - textBarHeight) / 2);
        panel_cli.setBounds(0, height - textBarHeight, width, textBarHeight);
        panel_taskinfo.setBounds(20, 20, width - 40, (height - textBarHeight) / 2);
        panel_noteinfo.setBounds(20, height - panel_taskinfo.getHeight(), width - 40, height - textBarHeight - panel_taskinfo.getHeight() - 30);
        panel_reminder.setBounds(20, height - panel_taskinfo.getHeight(), width - 40, height - textBarHeight - panel_taskinfo.getHeight() - 30);
        panel_help.setBounds(20, 20, width - 40, height - 50);

        panel_cli.setHEIGHTandWIDTH(textBarHeight, width);
        panel_help.setHEIGHTandWIDTH(height - 50, width - 40);
        panel_taskinfo.setHEIGHTandWIDTH((height - textBarHeight) / 2, width - 40);
        panel_clock.setHEIGHTandWIDTH((height - textBarHeight) / 2, width - 40);
        panel_noteinfo.setHEIGHTandWIDTH(height - textBarHeight - panel_taskinfo.getHeight() - 30, width - 40);
        panel_reminder.setHEIGHTandWIDTH(height - textBarHeight - panel_taskinfo.getHeight() - 30, width - 40);
    }

    public PANEL_reminder getPanel_reminder() {
        return panel_reminder;
    }

    public PANEL_cli getPanel_form() {
        return panel_cli;
    }

    public PANEL_taskinfo getPanel_taskinfo() {
        return panel_taskinfo;
    }

    public PANEL_clock getPanel_clock() {
        return panel_clock;
    }

    public PANEL_noteinfo getPanel_noteinfo() {
        return panel_noteinfo;
    }

    public PANEL_help getPanel_help() {
        return panel_help;
    }

    public void setEventHandler(EventHandler eventHandler) {
        panel_clock.setEventHandler(eventHandler);
        panel_cli.setEventHandler(eventHandler);
        panel_reminder.setEventHandler(eventHandler);
    }

    public void refreshComponents() {
        this.revalidate();
        this.repaint();
        for (Component component : this.getComponents()) {
            component.repaint();
            component.revalidate();
        }
    }

    @Override
    public void onThemeChanged() {
        backgroundColor = ThemeLoader.getColor(ThemeColorKey.PANEL_MAINMENU);
        setBackground(backgroundColor);

        // Update border color dynamically
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

        // Propagate theme updates to all subpanels
        for (Component c : this.getComponents()) {
            if (c instanceof ThemeChangeListener listener) {
                listener.onThemeChanged();
            }
            c.repaint();
            c.revalidate();
        }

        repaint();
        revalidate();
    }
}
