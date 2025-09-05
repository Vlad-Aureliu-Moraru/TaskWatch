package AppLogic.DirectoryLogic;

import AppLogic.TaskLogic.Task;

import java.util.ArrayList;

public class Directory {
    private ArrayList<Task> tasks = new ArrayList<>();
    private String Name;

    public void addTask(Task task){
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getName().equals(task.getName())){
            System.out.println("THERE IS ALREADY THE TASK");
            return;
            }
        }
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
        sb.append("name: "+Name+"\n ");
        for(Task t:tasks){
            sb.append(t+"\n");
        }
        return sb.toString();
    }
}
