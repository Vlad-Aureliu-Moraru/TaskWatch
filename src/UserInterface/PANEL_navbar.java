package UserInterface;

import Handlers.EventHandler;
import Loaders.FontLoader;
import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;
import UserInterface.PanelListElements.ListStages;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_navbar extends JPanel implements ThemeChangeListener {

    private final JLabel currentPATH = new JLabel("\uF506 ");
    private final JLabel statusDisplay = new JLabel("");
    private final int clockStage = 0; // 0 - clock working | 1 - timer working | 2 - timer paused
    private EventHandler eventHandler;
    private Timer timer ;

    public PANEL_navbar() {
        ThemeLoader.addThemeChangeListener(this);
        applyTheme();

        this.setLayout(null);

        statusDisplay.setFont(FontLoader.getTerminalFont().deriveFont(Font.PLAIN, 14));
        currentPATH.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 17));

        this.add(currentPATH);
        this.add(statusDisplay);

        timer = new Timer(2000, e -> {
            if (clockStage == 0) {
                setClockWorkingStatus();
            } else if (clockStage == 1) {
                setClockWorkingStatus();
            } else if (clockStage == 2) {
                setTimerPausedStatus();
            }
            timer.stop();
        });
        timer.setRepeats(false);
    }

    private void applyTheme() {
        // Background
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_NAVBAR));

        // Text Colors
        currentPATH.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        statusDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));

        // Border
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT), 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
    }

    public void setHEIGHTandWIDTH(int WIDTH) {
        statusDisplay.setBounds(30, 10, WIDTH / 3, 30);
        currentPATH.setBounds((WIDTH / 2) - 100, 10, 300, 30);
        if (currentPATH.getX() < 0) {
            currentPATH.setBounds(0, 10, 100, 30);
        }
        this.revalidate();
        this.repaint();
    }

    public void setCurrentPATH(String path) {
        currentPATH.setText(path);
    }

    public void returnFunction(boolean reloading) {
        System.out.println("CURRENT STAGE " + eventHandler.getPanelList().getStage());

        switch (eventHandler.getPanelList().getStage()) {
            case ARCHIVE_MENU -> eventHandler.getPanelList().setStage(ListStages.MAIN_MENU);
            case DIRECTORY_MENU -> eventHandler.getPanelList().setStage(ListStages.ARCHIVE_MENU);
            case TASK_MENU, NOTE_CLICKED -> eventHandler.getPanelList().setStage(ListStages.DIRECTORY_MENU);
            case MAIN_MENU -> System.out.println("Already at main menu, no back action taken.");
            default -> System.err.println("Unexpected stage encountered: " + eventHandler.getPanelList().getStage());
        }
    }

    public void setClockWorkingStatus() {
        statusDisplay.setText("\uDB82\uDD54  :: clock");
        statusDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.SECONDARY_GREEN));
    }

    public void setTimerWorkingStatus() {
        statusDisplay.setText("\uDB84\uDCD0  :: timer");
        statusDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.SECONDARY_GREEN));
    }

    public void setTimerPausedStatus() {
        statusDisplay.setText("\uF28B  :: paused");
        statusDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.PAUSED_TIMER_COLOR));
    }

    public void displayTempMessage(String message, boolean error) {
        statusDisplay.setText(error ? "Error//" + message : message);
        statusDisplay.setForeground(error
                ? ThemeLoader.getColor(ThemeColorKey.URGENCY5)
                : ThemeLoader.getColor(ThemeColorKey.FIRST_ACCENT));
        timer.start();
    }

    public void setPreparingStatus() {
        statusDisplay.setText("\uDB80\uDCBB  :: preparing");
        statusDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.TIMER_ON_PREP_COLOR));
    }

    public void setBreakStatus() {
        statusDisplay.setText("\uDB86\uDEEA  :: break");
        statusDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.TIMER_ON_BREAK_COLOR));
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
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
        applyTheme(); // re-apply the new colors and border
        repaint();
        revalidate();
    }
}
