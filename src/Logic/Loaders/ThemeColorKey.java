package Logic.Loaders;

import java.awt.Color;

public enum ThemeColorKey {

    SCROLL_THUMB("scroll_thumb",new Color(211, 77, 77)),
    SCROLL_TRACK("scroll_track",new Color(149, 99, 99)),
    PANEL_MAINMENU("panel_mainmenu",new Color(57,50,50)),
    PANEL_NAVBAR("panel_navbar",new Color(57,50,50)),
    PANEL_LIST("panel_list",new Color(77,69,69)),
    PANEL_TASKINFO("panel_taskinfo",new Color(57,50,50)),
    PANEL_HELP("panel_help",new Color(57,50,50)),
    PANEL_NOTEINFO("panel_noteinfo",new Color(57,50,50)),
    PANEL_CLOCK("panel_clock",new Color(57,50,50)),
    PANEL_REMINDER("panel_reminder",new Color(57,50,50)),
    PANEL_THISWEEK("panel_thisweek",new Color(57,50,50)),
    PANEL_TODAYLIST("panel_todaylist",new Color(57,50,50)),
    PANEL_WEEKLYSCHEDULE("panel_weeklyschedule",new Color(57,50,50)),

    PROG_BAR_GRADIENT1("prog_bar_gradient1",new Color(221, 120, 120)),
    PROG_BAR_GRADIENT2("prog_bar_gradient2",new Color(174, 73, 73)),
    PROG_BAR_GRADIENT3("prog_bar_gradient3",new Color(113, 46, 46)),

    MAIN_COLOR("main_color", new Color(57, 50, 50)),
    SECONDARY_COLOR("secondary_color", new Color(77, 69, 69)),
    FIRST_ACCENT("first_accent", new Color(141, 98, 98)),
    SECND_ACCENT("secnd_accent", new Color(237, 141, 141)),
    SECONDARY_GREEN("secondary_green", new Color(53, 155, 112)),
    ACCENT_GREEN("accent_green", new Color(33, 50, 73)),
    ARCHIVE_COLOR("dirColor", new Color(77, 79, 174)),
    ARCHIVE_HOVER_COLOR("dirHoverColor", new Color(83, 85, 172)),
    DIR_COLOR("dirColor", new Color(55, 56, 103)),
    DIR_HOVER_COLOR("dirHoverColor", new Color(36, 37, 72)),
    TASK_COLOR("taskColor", new Color(57, 50, 50)),
    TASK_HOVER_COLOR("taskHoverColor", new Color(33, 30, 30)),
    TASK_TEXT_COLOR("taskTextColor", new Color(255, 255, 255)),
    NOTE_COLOR("noteColor", new Color(211, 164, 89)),
    PAUSED_TIMER_COLOR("pausedTimerColor", new Color(255, 255, 255)),
    URGENCY1("urgency1", new Color(156, 246, 213)),
    URGENCY2("urgency2", new Color(116, 215, 129)),
    URGENCY3("urgency3", new Color(255, 242, 0)),
    URGENCY4("urgency4", new Color(255, 106, 0)),
    URGENCY5("urgency5", new Color(255, 0, 0)),
    URGENCY1_LIST("urgency1List", new Color(141, 215, 188)),
    URGENCY2_LIST("urgency2List", new Color(88, 171, 101)),
    URGENCY3_LIST("urgency3List", new Color(190, 185, 64)),
    URGENCY4_LIST("urgency4List", new Color(192, 107, 46)),
    URGENCY5_LIST("urgency5List", new Color(192, 76, 76)),
    DIFFICULTY1("difficulty1", new Color(156, 246, 213)),
    DIFFICULTY2("difficulty2", new Color(116, 215, 129)),
    DIFFICULTY3("difficulty3", new Color(255, 242, 0)),
    DIFFICULTY4("difficulty4", new Color(255, 106, 0)),
    DIFFICULTY5("difficulty5", new Color(255, 0, 0)),
    TASK_COMPLETED_ICON("TaskCompletedIconColor", new Color(4, 189, 255)),
    TASK_URGENT_ICON("TaskUrgentIconColor", new Color(255, 4, 79)),
    TASK_URGENT_PASSED("TaskUrgentPassed", new Color(162, 161, 171)),
    CONSOLE_COLOR("consoleColor", new Color(82, 94, 84)),
    CONSOLE_TEXT_COLOR("consoleTextColor", new Color(222, 222, 222)),
    TIMER_ON_BREAK_COLOR("timerOnBreakColor", new Color(75, 111, 122)),
    TIMER_ON_PREP_COLOR("timerOnPrepColor", new Color(158, 121, 92));

    private final String key;
    private final Color defaultColor;

    ThemeColorKey(String key, Color defaultColor) {
        this.key = key;
        this.defaultColor = defaultColor;
    }

    public String getKey() {
        return key;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }
}
