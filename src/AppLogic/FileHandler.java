package AppLogic;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.NotesLogic.Note;
import AppLogic.TaskLogic.Task;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private ArrayList<Directory> directoryList ;
    private Directory currentDirectory;
    private Task currentTask;

    public FileHandler(EventHandler eventHandler) {
        this.directoryList = eventHandler.getDirectoryList();
        this.currentDirectory = eventHandler.getCurrentDirectory();
    }

    public void setCurrentDirectory(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
    public void checkFileStructure() {
        File mainDir = new File("main");
        if (!mainDir.exists()) {
            System.out.println("Directory 'main' not found. Creating it...");
            boolean dirCreated = mainDir.mkdir();
            if (dirCreated) {
                System.out.println("Directory 'main' created successfully.");
            } else {
                System.err.println("Failed to create directory 'main'.");
                return;
            }
        } else {
            System.out.println("Directory 'main' already exists.");
        }
        File mainFile = new File(mainDir, "main.txt");
        if (!mainFile.exists()) {
            System.out.println("File 'main.txt' not found. Creating it...");
            try {
                boolean fileCreated = mainFile.createNewFile();
                if (fileCreated) {
                    System.out.println("File 'main.txt' created successfully.");
                } else {
                    System.err.println("Failed to create file 'main.txt'.");
                }
            } catch (IOException e) {
                System.err.println("An error occurred while creating 'main.txt': " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("File 'main.txt' already exists.");
        }
    }

    //?DIR
    public void saveDirectoryListToFile() {
        File mainFile = new File("main/main.txt");

        System.out.println("Saving directory list to main.txt...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mainFile))) {
            for (Directory directory : directoryList) {
                writer.write(directory.getName()+" ; ");

            }
            System.out.println("Directory list successfully saved to main.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void getDirectoryListFromFile() {
        directoryList.clear();

        File mainFile = new File("main/main.txt");

        if (!mainFile.exists()) {
            System.err.println("Error: 'main.txt' file not found.");
            return;
        }

        System.out.println("Reading directory list from main.txt...");

        try (BufferedReader reader = new BufferedReader(new FileReader(mainFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] dirs = line.split(";");
                    for (String dir : dirs) {
                        if (!dir.trim().isEmpty()) {
                            directoryList.add(new Directory(dir.trim()));
                        }
                    }
                }
            }
            System.out.println("Directory list loaded successfully from file.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //?TASK
    public void getTaskListFromFile() {
        ArrayList<Task> taskList = new ArrayList<>();

        if (currentDirectory == null) {
            System.err.println("Error: No current directory is set to load tasks from.");
            return;
        }

        String fileName = "main" + File.separator + currentDirectory.getName() + ".txt";
        File taskFile = new File(fileName);

        if (!taskFile.exists()) {
            System.err.println("Error: Task file not found for directory '" + currentDirectory.getName() + "'.");
            return;
        }

        System.out.println("Loading tasks from " + fileName + "...");
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null && line.trim().length() > 0) {
                String[] taskLine = line.split(";");
                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < taskLine.length; i++) {
                    if (!taskLine[i].trim().isEmpty()) {
                        String item =taskLine[i].substring(taskLine[i].indexOf(":")+1).trim().replaceAll("}", "");
                        items.add(item);
                    }
                }
                System.out.println(items.get(0));
                Task task = new Task();
                task.setName(items.get(0));
                task.setDescription(items.get(1));
                task.setRepeatable(Boolean.parseBoolean(items.get(2)));
                task.setFinished(Boolean.parseBoolean(items.get(3)));
                task.setDeadline(items.get(4));
                task.setUrgency(Integer.parseInt(items.get(5)));
                task.setTimeDedicated(Integer.parseInt(items.get(6)));
                taskList.add(task);
            }
            currentDirectory.setTasks(taskList);
            System.out.println("Tasks loaded successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while loading tasks: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(currentDirectory);
    }
    public void saveTaskToFile() {
        if (currentDirectory == null) {
            System.err.println("Error: No current directory selected.");
            return;
        }

        String fileName = "main" + File.separator + currentDirectory.getName() + ".txt";
        File taskFile = new File(fileName);

        System.out.println("Saving tasks for directory '" + currentDirectory.getName() + "' to " + fileName + "...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile,false))) {
            for (Task task : currentDirectory.getTasks()) {
                String taskLine = task+"";
                writer.write(taskLine);

            }
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //?NOTE
    public void getNotesFromFile(){
        ArrayList<Note> noteList = new ArrayList<>();
        if (currentTask == null) {
            System.err.println("Error: No current directory is set to load tasks from.");
            return;
        }

        String fileName = "main" + File.separator +currentDirectory.getName()+"-"+currentTask.getName().replaceAll(" ","_") + ".md";
        File taskFile = new File(fileName);

        if (!taskFile.exists()) {
            System.err.println("Error: Task file not found for directory '" + fileName + "'.");
            return;
        }

        System.out.println("Loading notes from " + fileName + "...");
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null && line.trim().length() > 0) {
                String[]  noteLine = line.split(";");
                for (String note : noteLine) {
                    if (!note.trim().isEmpty()) {
                        String[] noteItem = note.split("-");
                        String Date = noteItem[0].substring(noteItem[0].indexOf(":")+1);
                        String Note = noteItem[1].substring(noteItem[1].indexOf(":")+1);
                        Note noteObj = new Note();
                        noteObj.setDate(Date);
                        noteObj.setNote(Note);
                        noteList.add(noteObj);
                        }
                        }
                    }


        } catch (IOException e) {
            System.err.println("An error occurred while loading notes: " + e.getMessage());
            e.printStackTrace();
        }
        currentTask.setNotes(noteList);
        System.out.println("CURRENT TASK NOTES "+currentTask.getNotes());
    }
    public void saveNotesToFile() {
        if (currentDirectory == null) {
            System.err.println("Error: No current directory selected.");
            return;
        }

        String fileName = "main" + File.separator +currentDirectory.getName()+"-"+currentTask.getName().replaceAll(" ","_") + ".md";
        File taskFile = new File(fileName);

        System.out.println("Saving tasks for directory '" +currentDirectory.getName()+"/"+currentTask.getName().replaceAll(" ","_") + "' to " + fileName + "...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile,false))) {
            for (Note task :currentTask.getNotes()) {
                String taskLine = task+"";
                writer.write(taskLine);

            }
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
