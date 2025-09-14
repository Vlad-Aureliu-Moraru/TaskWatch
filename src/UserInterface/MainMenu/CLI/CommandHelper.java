package UserInterface.MainMenu.CLI;

public class CommandHelper {

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
}
