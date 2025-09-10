package UserInterface.MainMenu;

public class CommandHelper {
    //?BASIC TASKS
    private String addCommand = "(:a|:add)";
    private String removeCommand = "(:r|:remove)";
    private String editCommand = "(:e|:edit)";
    private String cancelCommand = "(:c|:cancel)";
    private String finishTaskCommand= "(:f|:finish)";
    private String showFinishedTasks= "(:shf|:showFinished)";

    //?SORTING RELATED
    private String sortByUrgencyCommand = "^\\s*:(su|sortByUrgency)\\((a|d)\\)\\s*$";
    private String sortByDifficultyCommand= "^\\s*:(sd|sortByDifficuly)\\((a|d)\\)\\s*$";
    //?TIMER RELATED
    private String startTimerCommand= ":st\\([0-9]*\\)";
    private String stopTimerCommand = "(:ts|:stopTimer)";
    private String pauseTimerCommand = "(:pt|:pauseTimer)";
    private String restartTimerCommand = "(:rt|:restartTimer)";
    private String startSelectedTaskTimer= "(:st|:startSelected)";

    //?DIR RELATED
    private String DirectoryNameRegEx = "^Directory_Name:\\w+";
    //?TASK RELATED
    private String TaskNameRegEx = "^Task_Name:\\w+";
    private String TaskDescriptionRegEx = "^Task_Description:.*";
    private String TaskPriorityRegEx = "^Task_Priority:(\\d{1}|)";
    private String TaskCompletionTimeRegEx = "^Task_Completion_Time:([0-9]*|)";
    private String TaskCompletionDateRegEx = "^Task_Completion_Date:(\\d{2}-\\d{2}-\\d{4}|)$";
    private String TaskRepeatableRegEx = "^isRepeatable:.*$";
    private String TaskRepeatableTypeRegEx = "^RepeatableType:(daily|weekly|monthly)";
    private String TaskDifficultyRegEx = "^Difficulty:(\\d{1}|)";
    //?NOTE RELATED
    private String NoteRegEx = "^Note:.*";

    public String getShowFinishedTasks() {
        return showFinishedTasks;
    }

    public String getTaskRepeatableTypeRegEx() {
        return TaskRepeatableTypeRegEx;
    }

    public String getTaskDifficultyRegEx() {
        return TaskDifficultyRegEx;
    }

    public String getSortByDifficultyCommand() {
        return sortByDifficultyCommand;
    }

    public String getFinishTaskCommand() {
        return finishTaskCommand;
    }

    public String getStopTimerCommand(){
        return stopTimerCommand;
    }
    public String getStartTimerCommand(){
        return startTimerCommand;
    }
    public String getCancelCommand() {
        return cancelCommand;
    }
    public String getNoteRegEx(){
        return NoteRegEx;
    }
    public String getAddCommand() {
        return addCommand;
    }
    public String getRemoveCommand() {
        return removeCommand;
    }
    public String getEditCommand() {
        return editCommand;
    }
    public String getDirectoryNameRegEx() {
        return DirectoryNameRegEx;
    }
    public String getTaskNameRegEx() {
        return TaskNameRegEx;
    }
    public String getTaskDescriptionRegEx() {
        return TaskDescriptionRegEx;
    }
    public String getTaskPriorityRegEx() {
        return TaskPriorityRegEx;
    }
    public String getTaskCompletionTimeRegEx() {
        return TaskCompletionTimeRegEx;
    }
    public String getTaskCompletionDateRegEx() {
        return TaskCompletionDateRegEx;
    }
    public String getTaskRepeatableRegEx() {
        return TaskRepeatableRegEx;
    }
    public String getPauseTimerCommand() {
        return pauseTimerCommand;
    }
    public String getRestartTimerCommand() {
        return restartTimerCommand;
    }
    public String getStartSelectedTaskTimer() {
        return startSelectedTaskTimer;
    }
    public String getSortByUrgencyCommand() {
        return sortByUrgencyCommand;
    }
}
