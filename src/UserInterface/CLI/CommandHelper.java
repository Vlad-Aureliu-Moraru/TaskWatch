package UserInterface.CLI;

import Logic.Loaders.ThemeColorKey;

public class CommandHelper {
    public String getSwitchScheduleDisplay(){
        return ":ssd";
    }
    public String getSetColorCommandForKey(ThemeColorKey key) {
        // All commands follow the pattern: "set <color_name> <rgb(...)>"
        // We'll convert the enum name to lower case and underscores to camel case if needed
        String commandPattern;

        switch (key) {
            case SCROLL_THUMB -> commandPattern = ":setScrollThumb:\\s*\\(.*\\)";
            case SCROLL_TRACK-> commandPattern = ":setScrollTrack:\\s*\\(.*\\)";
            case PANEL_MAINMENU-> commandPattern = ":setPanelMain:\\s*\\(.*\\)";
            case PANEL_NAVBAR-> commandPattern = ":setPanelNavBar:\\s*\\(.*\\)";
            case PANEL_LIST-> commandPattern = ":setPanelList:\\s*\\(.*\\)";
            case PANEL_TASKINFO-> commandPattern = ":setPanelTaskInfo:\\s*\\(.*\\)";
            case PANEL_HELP-> commandPattern = ":setPanelHelp:\\s*\\(.*\\)";
            case PANEL_NOTEINFO-> commandPattern = ":setPanelNoteInfo:\\s*\\(.*\\)";
            case PANEL_CLOCK-> commandPattern = ":setPanelClock:\\s*\\(.*\\)";
            case PANEL_REMINDER-> commandPattern = ":setPanelReminder:\\s*\\(.*\\)";
            case PANEL_THISWEEK-> commandPattern = ":setPanelThisWeek:\\s*\\(.*\\)";
            case PANEL_TODAYLIST-> commandPattern = ":setPanelTodayList:\\s*\\(.*\\)";
            case PANEL_WEEKLYSCHEDULE-> commandPattern = ":setPanelWeeklySchedule:\\s*\\(.*\\)";
            case MAIN_COLOR -> commandPattern = ":setMainColor:\\s*\\(.*\\)";
            case SECONDARY_COLOR -> commandPattern = ":setSecondaryColor:\\s*\\(.*\\)";
            case FIRST_ACCENT -> commandPattern = ":setFirstAccent:\\s*\\(.*\\)";
            case SECND_ACCENT -> commandPattern = ":setSecndAccent:\\s*\\(.*\\)";
            case SECONDARY_GREEN -> commandPattern = ":setSecondaryGreen:\\s*\\(.*\\)";
            case ACCENT_GREEN -> commandPattern = ":setAccentGreen:\\s*\\(.*\\)";
            case DIR_COLOR -> commandPattern = ":setDirColor:\\s*\\(.*\\)";
            case DIR_HOVER_COLOR -> commandPattern = ":setDirHoverColor:\\s*\\(.*\\)";
            case TASK_COLOR -> commandPattern = ":setTaskColor:\\s*\\(.*\\)";
            case TASK_HOVER_COLOR -> commandPattern = ":setTaskHoverColor:\\s*\\(.*\\)";
            case TASK_TEXT_COLOR -> commandPattern = ":setTaskTextColor:\\s*\\(.*\\)";
            case NOTE_COLOR -> commandPattern = ":setNoteColor:\\s*\\(.*\\)";
            case PAUSED_TIMER_COLOR -> commandPattern = ":setPausedTimerColor:\\s*\\(.*\\)";
            case URGENCY1 -> commandPattern = ":setUrgency1:\\s*\\(.*\\)";
            case URGENCY2 -> commandPattern = ":setUrgency2:\\s*\\(.*\\)";
            case URGENCY3 -> commandPattern = ":setUrgency3:\\s*\\(.*\\)";
            case URGENCY4 -> commandPattern = ":setUrgency4:\\s*\\(.*\\)";
            case URGENCY5 -> commandPattern = ":setUrgency5:\\s*\\(.*\\)";
            case URGENCY1_LIST -> commandPattern = ":setUrgency1List:\\s*\\(.*\\)";
            case URGENCY2_LIST -> commandPattern = ":setUrgency2List:\\s*\\(.*\\)";
            case URGENCY3_LIST -> commandPattern = ":setUrgency3List:\\s*\\(.*\\)";
            case URGENCY4_LIST -> commandPattern = ":setUrgency4List:\\s*\\(.*\\)";
            case URGENCY5_LIST -> commandPattern = ":setUrgency5List:\\s*\\(.*\\)";
            case DIFFICULTY1 -> commandPattern = ":setDifficulty1:\\s*\\(.*\\)";
            case DIFFICULTY2 -> commandPattern = ":setDifficulty2:\\s*\\(.*\\)";
            case DIFFICULTY3 -> commandPattern = ":setDifficulty3:\\s*\\(.*\\)";
            case DIFFICULTY4 -> commandPattern = ":setDifficulty4:\\s*\\(.*\\)";
            case DIFFICULTY5 -> commandPattern = ":setDifficulty5:\\s*\\(.*\\)";
            case TASK_COMPLETED_ICON-> commandPattern = ":setTaskCompletedIconColor:\\s*\\(.*\\)";
            case TASK_URGENT_ICON-> commandPattern = ":setTaskUrgentIconColor:\\s*\\(.*\\)";
            case TASK_URGENT_PASSED -> commandPattern = ":setTaskUrgentPassed:\\s*\\(.*\\)";
            case CONSOLE_COLOR -> commandPattern = ":setConsoleColor:\\s*\\(.*\\)";
            case CONSOLE_TEXT_COLOR -> commandPattern = ":setConsoleTextColor:\\s*\\(.*\\)";
            case TIMER_ON_BREAK_COLOR -> commandPattern = ":setTimerOnBreakColor:\\s*\\(.*\\)";
            case TIMER_ON_PREP_COLOR -> commandPattern = ":setTimerOnPrepColor:\\s*\\(.*\\)";
            default -> commandPattern = ""; // fallback
        }

        return commandPattern;
    }


    public String getTaskHasToBeCompletedToRepeatRegEx() {
        return "Has To Be Completed To Repeat\\(y/n\\):\\s*(y|n)";
    }

    public String getRepeatsOnSpecificDayRegEx() {
        return "Repeats On Specific Day \\(Mon\\|Tue\\|Wed\\|Thu\\|Fri\\|Sat\\|Sun\\):\\s*(Mon|Tue|Wed|Thu|Fri|Sat|Sun)";
    }
    public String getConfirmation(){
        return "^Are You Sure \\(y/n\\):(y|n)$";
    }
    public String getArchiveNameRegEx(){
        return "^Archive_Name:.*";
    }

    private static final String RGB_PATTERN = "\\s*\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)";
    public String getShowFinishedTasks() {
        return "(:shf|:showFinished)";
    }

    public String getTaskRepeatableTypeRegEx() {
        return "^RepeatableType:(daily|weekly|biweekly|monthly)";
    }

    public String getTaskDifficultyRegEx() {
        return "^Difficulty:(\\d{1}|)";
    }

    public String getSortByDifficultyCommand() {
        return "^\\s*:(sd|sortByDifficulty)\\((a|d)\\)\\s*$";
    }

    public String getFinishTaskCommand() {
        return "(:f|:finish)";
    }

    public String getStopTimerCommand(){
        return "(:ts|:stopTimer)";
    }
    public String getStartTimerCommand(){
        //?TIMER RELATED
        return ":st\\([0-9]*\\)";
    }
    public String getCancelCommand() {
        return "(:c|:cancel)";
    }
    public String getNoteRegEx(){
        //?NOTE RELATED
        return "^Note:.*";
    }
    public String getAddCommand() {
        //?BASIC TASKS
        return "(:a|:add)";
    }
    public String getRemoveCommand() {
        return "(:r|:remove)";
    }
    public String getEditCommand() {
        return "(:e|:edit)";
    }
    public String getDirectoryNameRegEx() {
        //?DIR RELATED
        return "^Directory_Name:\\w+";
    }
    public String getTaskNameRegEx() {
        //?TASK RELATED
        return "^Task_Name:\\w+";
    }
    public String getTaskDescriptionRegEx() {
        return "^Task_Description:.*";
    }
    public String getTaskPriorityRegEx() {
        return "^Task_Priority:(\\d{1}|)";
    }
    public String getTaskCompletionTimeRegEx() {
        return "^Task_Completion_Time:([0-9]*|)";
    }
    public String getTaskCompletionDateRegEx() {
        return "^Task_Completion_Date:(\\d{2}/\\d{2}/\\d{4}|)$";
    }
    public String getTaskRepeatableRegEx() {
        return "^isRepeatable:.*$";
    }
    public String getPauseTimerCommand() {
        return "(:pt|:pauseTimer)";
    }
    public String getRestartTimerCommand() {
        return "(:rt|:restartTimer)";
    }
    public String getStartSelectedTaskTimer() {
        return "(:st|:startSelected)";
    }
    public String getSortByUrgencyCommand() {
        //?SORTING RELATED
        return "^\\s*:(su|sortByUrgency)\\((a|d)\\)\\s*$";
    }
    public String getHelpCommand() {
        return "(:h|:help)";
    }
    public String getExitCommand() {
        return "(:exit)";
    }
    public String getCreateThemeCommand() {
        return "(:ct\\(\\w+\\)|:createTheme\\(\\w+\\))";
    }
    public String getRemoveThemeCommand(){
        return "(:rt\\(\\w+\\)|:removeTheme\\(\\w+\\))";
    }
    public String getSetThemeCommand(){
        return "(:set\\(\\w+\\)|:setTheme\\(\\w+\\))";
    }
    public String getCurrentThemeCommand(){
        return "(:theme)";
    }
    public String getListThemesCommand(){
        return "(:lst|:listThemes)";
    }
}
