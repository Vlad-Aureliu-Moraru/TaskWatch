package Handlers.Repositories;
import AppLogic.Note;
import AppLogic.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final String FILE_PATH = "repository/tasks.txt";
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static boolean isLoaded = false;

    public TaskRepository() {
        if (!isLoaded) {
            loadTasksFromFile();
            isLoaded = true;
        }
    }

    // Load tasks from file
    private void loadTasksFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Tasks file not found. Created new one.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    // Parse fields using indexOf and substring
                    int id = Integer.parseInt(getValue(line, "id:", ";"));
                    int directoryId = Integer.parseInt(getValue(line, "DirectoryId:", ";"));
                    String name = getValue(line, "name:", ";");
                    String description = getValue(line, "description:", ";");
                    boolean repeatable = Boolean.parseBoolean(getValue(line, "isRepeatable:", ";"));
                    boolean finished = Boolean.parseBoolean(getValue(line, "isFinished:", ";"));
                    String deadline = getValue(line, "deadline:", ";");
                    int urgency = Integer.parseInt(getValue(line, "urgency:", ";"));
                    int timeDedicated = Integer.parseInt(getValue(line, "time:", ";"));
                    int difficulty = Integer.parseInt(getValue(line, "difficulty:", ";"));
                    String repeatableType = getValue(line, "repeatableType:", ";");
                    String finishedDate = getValue(line, "finishedDate:", "}");

                    Task task = new Task(id, directoryId, name, description, repeatable, finished,
                            deadline, urgency, timeDedicated, difficulty, repeatableType, finishedDate);

                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper to extract value from line
    private String getValue(String line, String key, String endChar) {
        int start = line.indexOf(key) + key.length();
        int end = line.indexOf(endChar, start);
        return line.substring(start, end).trim();
    }

    // Save all tasks to file
    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Task t : tasks) {
                writer.write(t.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all tasks
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // Get task by ID
    public Task getTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    // Add task (prevent duplicate ID)
    public void addTask(Task task) {
        boolean exists = tasks.stream().anyMatch(t -> t.getId() == task.getId());
        if (exists) {
            System.out.println("[WARNING] Task with this ID already exists!");
            return;
        }
        tasks.add(task);
        saveAllToFile();
    }

    // Update a task by ID
    public void updateTask(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == updatedTask.getId()) {
                tasks.set(i, updatedTask);
                saveAllToFile();
                break;
            }
        }
    }

    // Delete a task by ID
    public void deleteTask(int id) {
        tasks.removeIf(t -> t.getId() == id);
        saveAllToFile();
    }

    // =========================
    // Filter methods
    // =========================

    public List<Task> getTasksByDirectoryId(int directoryId) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDirectoryId() == directoryId) result.add(t);
        }
        return result;
    }

    public List<Task> getRepeatableTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isRepeatable()) result.add(t);
        }
        return result;
    }

    public List<Task> getFinishedTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isFinished()) result.add(t);
        }
        return result;
    }

    public List<Task> getTasksWithDeadline() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.getDeadline().isEmpty()) result.add(t);
        }
        return result;
    }
    public static void main(String[] args){
        TaskRepository repo = new TaskRepository();
        repo.addTask(new Task(1,2,"test","test", false, false,"12/23/23",2,33,2,"none",null));
        Task task =  repo.getTaskById(1);
        System.out.println(task);
    }

    // You can add more filters as needed, e.g., by urgency, difficulty, repeatable type, etc.
}
