package AppLogic.DirectoryLogic;

import AppLogic.TaskLogic.Task;

import java.util.ArrayList;

public class Directory {
    private ArrayList<Task> tasks = new ArrayList<>();
    private String Name;

    public void addTask(Task task){
        tasks.add(task);
    }

    public  Directory(String name) {
        Name = name;
    }

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
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("name: "+Name+" ");
        return sb.toString();
    }
}
