package AppLogic;

import java.util.ArrayList;
import java.util.Comparator;

public class Directory {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private String Name;

    public void addTask(Task task){
        boolean taskExists = tasks.stream().anyMatch(t -> t.getName().equals(task.getName()));
        if (!taskExists){
            tasks.add(task);
        }else{
            System.out.println("task already exists");
        }
    }
    public boolean taskNameExists(String taskName){
        return tasks.stream().anyMatch(t -> t.getName().equals(taskName));
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

    public void sortTasksByUrgency(boolean ascending){
        if(ascending){
            tasks.sort(Comparator.comparingInt(Task::getUrgency));
        }else{
            tasks.sort(Comparator.comparingInt(Task::getUrgency).reversed());
        }
    }
    public void sortTasksByDifficulty(boolean ascending){
        if(ascending){
            tasks.sort(Comparator.comparingInt(Task::getDifficulty));
        }else{
            tasks.sort(Comparator.comparingInt(Task::getDifficulty).reversed());
        }
    }
    public void sortTaskByFinishedStatus(boolean ascending) {
        Comparator<Task> finishedComparator = Comparator.comparing(Task::isFinished);
        if (ascending) {
            tasks.sort(finishedComparator.reversed());
        } else {
            tasks.sort(finishedComparator);
        }
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("name: ").append(Name).append("\n ");
        for(Task t:tasks){
            sb.append(t).append("\n");
        }
        return sb.toString();
    }
}
