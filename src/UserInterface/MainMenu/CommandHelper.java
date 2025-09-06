package UserInterface.MainMenu;

public class CommandHelper {
    private String addCommand = ":a";
    private String removeCommand = ":r";
    private String editCommand = ":e";
    private String cancelCommand = ":c";

    private String DirectoryRegEx = "^Directory_Name:.*";

    private String TaskNameRegEx = "^Task_Name:.*";
    private String TaskDescriptionRegEx = "^Task_Description:.*";
    private String TaskPriorityRegEx = "^Task_Priority:[0-9]*";
    private String TaskCompletionTimeRegEx = "^Task_Completion_Time:[0-9]*";
    private String TaskCompletionDateRegEx = "^Task_Completion_Date:\\d{2}%\\d{2}%\\d{4}$";
    private String TaskRepeatableRegEx = "^Task_Repeatable:(true|false)$";


    public CommandHelper() {}

    public String getCancelCommand() {
        return cancelCommand;
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
}
