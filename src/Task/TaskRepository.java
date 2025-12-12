package Task;

import Task.Model.Task;
import Note.NoteRepository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskRepository {

    private static final String FILE_PATH = "repository/tasks.txt";
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static boolean isLoaded = false;
    private static int currentId = 0;
    private static int taskcount = 0;

    public TaskRepository() {
        if (!isLoaded) {
            loadTasksFromFile();
            isLoaded = true;
        }
    }

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
            boolean idLineFound = false;
            int maxTaskId = -1;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("id=#") && line.endsWith("#")) {
                    try {
                        String idValue = line.substring(4, line.length() - 1);
                        currentId = Integer.parseInt(idValue);
                        idLineFound = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format in file: " + line);
                    }
                    continue;
                }

                try {
                    int id = extractInt(line, "id:", ";DirectoryId");
                    int directoryId = extractInt(line, "DirectoryId:", ";name:");
                    String name = extractString(line, "name:", ";description:");
                    String description = extractString(line, "description:", ";isRepeatable:");
                    boolean isRepeatable = extractBoolean(line, "isRepeatable:", ";isFinished:");
                    boolean isFinished = extractBoolean(line, "isFinished:", ";deadline:");
                    String deadline = extractString(line, "deadline:", ";urgency:");
                    int urgency =   Integer.parseInt( extractString(line, "urgency:", ";time:"));
                    int time =      Integer.parseInt( extractString(line, "time:", ";difficulty:"));
                    int difficulty =Integer.parseInt( extractString(line, "difficulty:", ";repeatableType:"));
                    String repeatableType = extractString(line, "repeatableType:", ";finishedDate:");
                    String finishedDate = extractString(line, "finishedDate:", ";hasToBeCompletedToRepeat:");
                    boolean hasToBeCompletedToRepeat = extractBoolean(line, "hasToBeCompletedToRepeat:", ";repeatOnSpecificDay:");
                    String repeatOnSpecificDay = extractString(line, "repeatOnSpecificDay:", "}");

                    if(tasks.stream().noneMatch(task -> task.getId() == id)) {
                        tasks.add(new Task(id, directoryId, name, description, isRepeatable, isFinished,
                                deadline, urgency, time, difficulty, repeatableType, finishedDate,
                                hasToBeCompletedToRepeat, repeatOnSpecificDay));
                        taskcount++;
                    }else{
                        System.out.println("Task with id " + id + " already exists.");
                        //TODO : ADD AN ACTUAL METHOD THAT CHANGES THE ID CORRECTLY OF DUPES BCS IT SHOULD ALSO CARRY OVER THE NOTES THAT ARE RELATED,
                        //TODO : keep it ordered by taskcount but update it in the notes too , ??maybe??
                    }

                    maxTaskId = Math.max(maxTaskId, id);
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

            if (!idLineFound) {
                if (maxTaskId >= 0)
                    currentId = maxTaskId + 1;
                else
                    currentId = 0;
                saveAllToFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility extractors
    private int extractInt(String line, String start, String end) {
        String value = line.substring(line.indexOf(start) + start.length(), line.indexOf(end)).trim();
        return Integer.parseInt(value);
    }

    private String extractString(String line, String start, String end) {
        int startIndex = line.indexOf(start) + start.length();
        int endIndex = line.contains(end) ? line.indexOf(end, startIndex) : line.length();
        return line.substring(startIndex, endIndex).trim();
    }

    private boolean extractBoolean(String line, String start, String end) {
        String value = extractString(line, start, end);
        return value.equalsIgnoreCase("true") || value.equals("1");
    }

    // Save all tasks + currentId to file
    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            writer.write("id=#" + currentId + "#\n");
            for (Task t : tasks) {
                writer.write(t.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ID management
    public static int getCurrentId() {
        return currentId;
    }

    public static int getNextId() {
        currentId++;
        return currentId;
    }

    // CRUD operations
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task getTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public ArrayList<Task> getTasksByDirectoryId(int directoryId) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDirectoryId() == directoryId) result.add(t);
        }
        return result;
    }

    public ArrayList<Task> getRepeatableTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isRepeatable()) result.add(t);
        }
        return result;
    }

    public ArrayList<Task> getFinishedTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isFinished()) result.add(t);
        }
        return result;
    }

    public ArrayList<Task> getTasksWithDeadline() {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDeadline() != null && !t.getDeadline().isEmpty())
                result.add(t);
        }
        return result;
    }

    public ArrayList<Task> getTasksByUrgency(int urgency) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getUrgency() == urgency)
                result.add(t);
        }
        return result;
    }

    public ArrayList<Task> getTasksByDifficulty(int difficulty) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDifficulty() == difficulty)
                result.add(t);
        }
        return result;
    }

    public void addTask(int directoryId,Task task) {
        boolean exists = tasks.stream().anyMatch(t ->
                t.getName().equalsIgnoreCase(task.getName()) &&
                        t.getDirectoryId() == task.getDirectoryId());

        if (exists) {
            System.out.println("[WARNING] Task with this name already exists in directory " + task.getDirectoryId());
            return;
        }

        task.setId(getNextId());
        task.setDirectoryId(directoryId);
        tasks.add(task);
        saveAllToFile();
    }

    public void updateTask(int id,int directoryId, Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getId() == id) {
                updatedTask.setId(id);
                updatedTask.setDirectoryId(directoryId);
                tasks.set(i, updatedTask);
                saveAllToFile();
                return;
            }
        }
        System.out.println("[WARNING] Task with ID " + id + " not found!");
    }
    public void setTaskFinished(int id , boolean finished) {
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getId() == id) {
                t.setFinished(finished);
                saveAllToFile();
                return;
            }
        }
        System.out.println("[WARNING] Task with ID " + id + " not found!");
    }
    public void deleteTask(int id) {
        NoteRepository noteRepository = new NoteRepository();
        ArrayList<Task> toRemove = new ArrayList<>();
        for (Task t :tasks) {
            if (t.getId() == id) {
                toRemove.add(t);
                noteRepository.deleteNoteByTaskId(t.getId());
            }
        }
        tasks.removeAll(toRemove);
        saveAllToFile();
    }
    public void deleteTaskByDirectoryId(int id) {
        NoteRepository noteRepository = new NoteRepository();
        ArrayList<Task> toRemove = new ArrayList<>();
        for (Task t :tasks) {
            if (t.getDirectoryId() == id) {
                toRemove.add(t);
                noteRepository.deleteNoteByTaskId(t.getId());
            }
        }
        tasks.removeAll(toRemove);
        saveAllToFile();
    }
    public ArrayList<Task> sortTasksByDificulty(int id, boolean ascending) {
        ArrayList<Task> toSort = getTasksByDirectoryId(id);
        if (ascending) {
            toSort.sort(Comparator.comparing(Task::getDifficulty));
        }else{
            toSort.sort(Comparator.comparing(Task::getDifficulty).reversed());
        }
        return toSort;
    }
    public ArrayList<Task> sortTasksByUrgency(int id, boolean ascending) {
        ArrayList<Task> toSort = getTasksByDirectoryId(id);
        if (ascending) {
            toSort.sort(Comparator.comparing(Task::getUrgency));
        }else{
            toSort.sort(Comparator.comparing(Task::getUrgency).reversed());
        }
        return toSort;
    }
    public void updateTaskFinishedStatus(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today= LocalDate.now();
        for (Task task :tasks) {
            if (task.isFinished() && task.isRepeatable()) {
                System.out.println("FINISHED TASK: " + task.getName());
                LocalDate taskDeadline = LocalDate.parse(task.getDeadline(), formatter);
                if (taskDeadline.isBefore(today)||taskDeadline.isEqual(today) ) {
                    task.setFinished(false);
                }
            }
        }
        saveAllToFile();
    }
    public void updateTaskDeadline(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Task task : tasks) {
            if (task.getId() == id && task.isRepeatable()) {
                String repeatableType = task.getRepeatableType();
                LocalDate baseDate;

                if (task.isHasToBeCompletedToRepeat()) {
                    baseDate = LocalDate.parse(task.getFinishedDate(), formatter);
                } else {
                    baseDate = LocalDate.parse(task.getDeadline(), formatter);
                }

                LocalDate newDeadline;
                switch (repeatableType.toLowerCase()) {
                    case "daily" -> newDeadline = baseDate.plusDays(1);
                    case "weekly" -> newDeadline = baseDate.plusWeeks(1);
                    case "biweekly" -> newDeadline = baseDate.plusWeeks(2);
                    case "monthly" -> newDeadline = baseDate.plusMonths(1);
                    default -> {
                        System.out.println("Unknown repeatable type: " + repeatableType);
                        return;
                    }
                }

                task.setDeadline(newDeadline.format(formatter));
            }
        }

        saveAllToFile();
    }



    public static void main(String[] args) {
        TaskRepository repo = new TaskRepository();
        System.out.println(repo.getTasksByDirectoryId(1));

    }
}
