package UserInterface.MainMenu;

public class CommandHelper {
    private String addCommand = "(:a|:add)";
    private String removeCommand = ":r";
    private String editCommand = ":e";
    private String cancelCommand = "(:c|:cancel)";

    //?SORTING RELATED
    private String sortByUrgencyCommand = "^\\s*:(su|sortByUrgency)\\((a|d)\\)\\s*$";
    //?TIMER RELATED
    private String startTimerCommand= ":st\\([0-9]*\\)";
    private String stopTimerCommand = "(:ts|:stopTimer)";
    private String pauseTimerCommand = "(:pt|:pauseTimer)";
    private String restartTimerCommand = "(:rt|:restartTimer)";
    private String startSelectedTaskTimer= "(:st|:startSelected)";

    private String DirectoryRegEx = "^Directory_Name:.*";

    private String TaskNameRegEx = "^Task_Name:.*";
    private String TaskDescriptionRegEx = "^Task_Description:.*";
    private String TaskPriorityRegEx = "^Task_Priority:(\\d{1}|)";
    private String TaskCompletionTimeRegEx = "^Task_Completion_Time:(\\d{3}|)";
    private String TaskCompletionDateRegEx = "^Task_Completion_Date:(\\d{2}-\\d{2}-\\d{4}|)$";
    private String TaskRepeatableRegEx = "^isRepeatable:.*$";

    private String NoteRegEx = "^Note:.*";


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
    public String getDirectoryRegEx() {
        return DirectoryRegEx;
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
