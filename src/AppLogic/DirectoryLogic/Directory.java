package AppLogic.DirectoryLogic;

import AppLogic.TaskLogic.Task;

import java.util.ArrayList;

public class Directory {
    private ArrayList<Task> tasks = new ArrayList<>();
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
