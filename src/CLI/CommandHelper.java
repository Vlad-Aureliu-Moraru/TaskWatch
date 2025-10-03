package CLI;

public class CommandHelper {
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
    public String getSetMainColorCommand() {
        return ":setMainColor" + RGB_PATTERN;
    }

    public String getSetSecondaryColorCommand() {
        return ":setSecondaryColor" + RGB_PATTERN;
    }

    public String getSetFirstAccentCommand() {
        return ":setFirstAccent" + RGB_PATTERN;
    }

    public String getSetSecndAccentCommand() {
        return ":setSecndAccent" + RGB_PATTERN;
    }

    public String getSetSecondaryGreenCommand() {
        return ":setSecondaryGreen" + RGB_PATTERN;
    }

    public String getSetAccentGreenCommand() {
        return ":setAccentGreen" + RGB_PATTERN;
    }

    public String getSetDirColorCommand() {
        return ":setDirColor" + RGB_PATTERN;
    }

    public String getSetDirHoverColorCommand() {
        return ":setDirHoverColor" + RGB_PATTERN;
    }

    public String getSetTaskColorCommand() {
        return ":setTaskColor" + RGB_PATTERN;
    }

    public String getSetTaskHoverColorCommand() {
        return ":setTaskHoverColor" + RGB_PATTERN;
    }

    public String getSetTaskTextColorCommand() {
        return ":setTaskTextColor" + RGB_PATTERN;
    }

    public String getSetNoteColorCommand() {
        return ":setNoteColor" + RGB_PATTERN;
    }

    public String getSetPausedTimerCommand() {
        return ":setPausedTimerColor" +RGB_PATTERN;
    }

    public String getSetUrgency1Command() {
        return ":setUrgency1" + RGB_PATTERN;
    }

    public String getSetUrgency2Command() {
        return ":setUrgency2" + RGB_PATTERN;
    }

    public String getSetUrgency3Command() {
        return ":setUrgency3" + RGB_PATTERN;
    }

    public String getSetUrgency4Command() {
        return ":setUrgency4" + RGB_PATTERN;
    }

    public String getSetUrgency5Command() {
        return ":setUrgency5" + RGB_PATTERN;
    }

    public String getSetUrgency1ListCommand() {
        return ":setUrgency1List" + RGB_PATTERN;
    }

    public String getSetUrgency2ListCommand() {
        return ":setUrgency2List" + RGB_PATTERN;
    }

    public String getSetUrgency3ListCommand() {
        return ":setUrgency3List" + RGB_PATTERN;
    }

    public String getSetUrgency4ListCommand() {
        return ":setUrgency4List" + RGB_PATTERN;
    }

    public String getSetUrgency5ListCommand() {
        return ":setUrgency5List" + RGB_PATTERN;
    }

    public String getSetDifficulty1Command() {
        return ":setDifficulty1" + RGB_PATTERN;
    }

    public String getSetDifficulty2Command() {
        return ":setDifficulty2" + RGB_PATTERN;
    }

    public String getSetDifficulty3Command() {
        return ":setDifficulty3" + RGB_PATTERN;
    }

    public String getSetDifficulty4Command() {
        return ":setDifficulty4" + RGB_PATTERN;
    }

    public String getSetDifficulty5Command() {
        return ":setDifficulty5" + RGB_PATTERN;
    }

    public String getSetTaskCompletedIconColorCommand() {
        return ":setTaskCompletedIconColor" + RGB_PATTERN;
    }

    public String getSetTaskUrgentIconColorCommand() {
        return ":setTaskUrgentIconColor:" + RGB_PATTERN;
    }
    public String getSetTaskUrgentPassedCommand() {
        return ":setTaskUrgentPassed:"+RGB_PATTERN;
    }

    public String getSetConsoleColorCommand() {
        return ":setConsoleColor:" + RGB_PATTERN;
    }

    public String getSetConsoleTextColorCommand() {
        return ":setConsoleTextColor:" + RGB_PATTERN;
    }

    public String getSetTimerOnBreakColorCommand() {
        return ":setTimerOnBreakColor:" + RGB_PATTERN;
    }

    public String getSetTimerOnPrepColorCommand() {
        return ":setTimerOnPrepColor:" + RGB_PATTERN;
    }
}
