package UserInterface.SubPanels;

import Logic.Loaders.FontLoader;
import Logic.Loaders.ThemeChangeListener;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;
import UserInterface.PanelListElements.CustomScrollBarUI;
import UserInterface.PanelListElements.modifiedTextArea;

import javax.swing.*;
import java.awt.*;

/**
 * PANEL_help displays help and theme customization information.
 * Supports dynamic theme updates and scrollable layout.
 */
public class PANEL_help extends JScrollPane implements ThemeChangeListener {

    private final JPanel panel_help = new JPanel();

    private final JLabel[] urgencyBoxLabels = new JLabel[5];
    private final modifiedTextArea urgencyExplanation =
            new modifiedTextArea("\uEEBF  - TASK URGENCY ICON & URGENCY COLORS 1–5");

    private final JLabel[] difficultyBoxLabels = new JLabel[5];
    private final modifiedTextArea difficultyExplanation =
            new modifiedTextArea("\uEAD7  - TASK DIFFICULTY BORDER & DIFFICULTY COLORS 1–5");

    private final modifiedTextArea commandInfo = new modifiedTextArea("");
    private final modifiedTextArea themeSettingsInfo = new modifiedTextArea("");

    private static final int BOX_SPACING = 20;

    public PANEL_help() {
        // Setup scroll panel
        setViewportView(panel_help);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setViewportBorder(BorderFactory.createEmptyBorder());
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setFocusable(false);
        setVisible(false);
        setAutoscrolls(true);
        setWheelScrollingEnabled(true);
        getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));
        getVerticalScrollBar().setUnitIncrement(10);
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());


        // Setup content panel
        panel_help.setLayout(null);
        panel_help.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_HELP));

        // Register theme listener
        ThemeLoader.addThemeChangeListener(this);

        // Add UI sections
        addUrgencyBoxes();
        addDifficultyBoxes();
        addCommandInfo();
        addThemeSettingsInfo();
    }

    /** Toggles visibility of this help panel. */
    public void switchVisible() {
        boolean nowVisible = !this.isVisible();
        this.setVisible(nowVisible);

        if (nowVisible) {
            SwingUtilities.invokeLater(() -> getVerticalScrollBar().setValue(0));
        }
    }


    /** Dynamically sets layout dimensions based on parent width/height. */
    public void setHEIGHTandWIDTH(int height, int width) {
        int y = 0;

        urgencyExplanation.setBounds((BOX_SPACING * 5) + 20, 20, width - 30, 50);
        y += 70;

        difficultyExplanation.setBounds((BOX_SPACING * 5) + 20, 55, width - 30, 50);
        y += 70;

        commandInfo.setBounds(20, 105, width - 30, 700);
        y += 700;

        themeSettingsInfo.setBounds(20, y + 12, width - 30, 1600);
        y += 1600;

        panel_help.setPreferredSize(new Dimension(width, y));
    }

    /** Adds the list of command shortcuts. */
    private void addCommandInfo() {
        final String commandText = """
                :h | :help            - SHOWS THIS HELP MESSAGE
                :a | :add             - ADDS DIRECTORIES/TASKS/NOTES
                :r | :remove          - REMOVES DIRECTORIES/TASKS/NOTES
                :c | :cancel          - CANCELS COMMAND INPUT
                :e | :edit            - EDITS DIRECTORIES/TASKS/NOTES
                :f | :finish          - TOGGLES TASK COMPLETION
                :st(int time)         - STARTS A BASIC TIMER
                :st | :startSelected  - STARTS SELECTED TASK (POMODORO)
                :ts | :timerStop      - STOPS TIMER
                :pt | :pauseTimer     - TOGGLES PAUSE/UNPAUSE TIMER
                :rt | :resetTimer     - RESETS TIMER
                :shf | :showFinished  - SHOWS FINISHED TASKS
                su(a|d) | sortByUrgency(a|d)    - SORT BY URGENCY
                sd(a|d) | sortByDifficulty(a|d) - SORT BY DIFFICULTY
                :exit                      - EXITS APPLICATION
                :ct(name) | :createTheme   - CREATES NEW THEME
                :rt(name) | :removeTheme   - REMOVES THEME
                :set(name) | :setTheme     - SETS ACTIVE THEME
                :theme                     - SHOWS ACTIVE THEME
                :lst | :listThemes          - LISTS AVAILABLE THEMES
                
                                              
                """;

        commandInfo.setText(commandText);
        commandInfo.setFont(FontLoader.getCozyFont().deriveFont(17f));
        commandInfo.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        panel_help.add(commandInfo);
    }

    /** Adds theme customization help text. */
    private void addThemeSettingsInfo() {
        final String themeText = """
                THEME CUSTOMIZATION
:setScrollThumb:(R,G,B)           - SETS SCROLL THUMB COLOR
:setScrollTrack:(R,G,B)           - SETS SCROLL_TRACK COLOR
:setPanelMain:(R,G,B)             - SETS PANEL MAINMENU COLOR
:setPanelNavBar:(R,G,B)           - SETS PANEL NAVBAR COLOR
:setPanelList:(R,G,B)             - SETS PANEL LIST COLOR
:setPanelTaskInfo:(R,G,B)         - SETS PANEL TASK COLOR
:setPanelHelp:(R,G,B)             - SETS PANEL HELP COLOR
:setPanelNoteInfo:(R,G,B)         - SETS PANEL NOTE COLOR
:setPanelClock:(R,G,B)            - SETS PANEL CLOCK COLOR
:setPanelReminder:(R,G,B)         - SETS PANEL REMINDER COLOR
:setPanelThisWeek:(R,G,B)         - SETS PANEL THIS_WEEK COLOR
:setPanelTodayList:(R,G,B)        - SETS PANEL TODAY COLOR
:setPanelWeeklySchedule:(R,G,B)   - SETS PANEL WEEKLY COLOR
:setMainColor:(R,G,B)             - SETS MAIN COLOR COLOR
:setSecondaryColor:(R,G,B)        - SETS SECONDARY COLOR
:setFirstAccent:(R,G,B)           - SETS FIRST ACCENT COLOR
:setSecndAccent:(R,G,B)           - SETS SECOND ACCENT COLOR
:setSecondaryGreen:(R,G,B)        - SETS SECONDARY GREEN COLOR
:setAccentGreen:(R,G,B)           - SETS ACCENT GREEN COLOR
:setDirColor:(R,G,B)              - SETS DIRECTORY COLOR
:setDirHoverColor:(R,G,B)         - SETS DIRECTORY HOVER COLOR
:setTaskColor:(R,G,B)             - SETS TASK COLOR
:setTaskHoverColor:(R,G,B)        - SETS TASK HOVER COLOR
:setTaskTextColor:(R,G,B)         - SETS TASK TEXT COLOR
:setNoteColor:(R,G,B)             - SETS NOTE COLOR
:setPausedTimerColor:(R,G,B)      - SETS PAUSED TIMER COLOR
:setUrgency1:(R,G,B)              - SETS URGENCY 1 COLOR
:setUrgency2:(R,G,B)
:setUrgency3:(R,G,B)
:setUrgency4:(R,G,B)
:setUrgency5:(R,G,B)
:setUrgency1List:(R,G,B)
:setUrgency2List:(R,G,B)
:setUrgency3List:(R,G,B)
:setUrgency4List:(R,G,B)
:setUrgency5List:(R,G,B)
:setDifficulty1:(R,G,B)
:setDifficulty2:(R,G,B)
:setDifficulty3:(R,G,B)
:setDifficulty4:(R,G,B)
:setDifficulty5:(R,G,B)
:setTaskCompletedIconColor:(R,G,B)
:setTaskUrgentIconColor:(R,G,B)
:setTaskUrgentPassed:(R,G,B,A)
:setConsoleColor:(R,G,B)
:setConsoleTextColor:(R,G,B)
:setTimerOnBreakColor:(R,G,B)
:setTimerOnPrepColor:(R,G,B)
""";

        themeSettingsInfo.setText(themeText);
        themeSettingsInfo.setFont(FontLoader.getCozyFont().deriveFont(17f));
        themeSettingsInfo.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        panel_help.add(themeSettingsInfo);
    }

    /** Adds the urgency color indicators and explanation text. */
    private void addUrgencyBoxes() {
        for (int i = 0; i < urgencyBoxLabels.length; i++) {
            JLabel label = new JLabel("\uF04D");
            label.setFont(FontLoader.getCozyFont().deriveFont(22f));
            label.setBounds((BOX_SPACING * i) + 10, 5, 50, 50);
            label.setForeground(ThemeLoader.getUrgencyColor(i + 1));
            urgencyBoxLabels[i] = label;
            panel_help.add(label);
        }
        urgencyExplanation.setFont(FontLoader.getCozyFont().deriveFont(16f));
        panel_help.add(urgencyExplanation);
    }

    /** Adds the difficulty color indicators and explanation text. */
    private void addDifficultyBoxes() {
        for (int i = 0; i < difficultyBoxLabels.length; i++) {
            JLabel label = new JLabel("\uF04D");
            label.setFont(FontLoader.getCozyFont().deriveFont(22f));
            label.setBounds((BOX_SPACING * i) + 10, 40, 50, 50);
            label.setForeground(ThemeLoader.getDifficultyColor(i + 1));
            difficultyBoxLabels[i] = label;
            panel_help.add(label);
        }
        difficultyExplanation.setFont(FontLoader.getCozyFont().deriveFont(16f));
        panel_help.add(difficultyExplanation);
    }

    /** Reacts live to theme changes. */
    @Override
    public void onThemeChanged() {
        panel_help.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_HELP));

        // Update text colors
        Color accent = ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT);
        commandInfo.setForeground(accent);
        themeSettingsInfo.setForeground(accent);
        urgencyExplanation.setForeground(accent);
        difficultyExplanation.setForeground(accent);

        // Update indicator colors
        for (int i = 0; i < urgencyBoxLabels.length; i++) {
            urgencyBoxLabels[i].setForeground(ThemeLoader.getUrgencyColor(i + 1));
        }
        for (int i = 0; i < difficultyBoxLabels.length; i++) {
            difficultyBoxLabels[i].setForeground(ThemeLoader.getDifficultyColor(i + 1));
        }

        revalidate();
        repaint();
    }
}
