package AppLogic;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.NotesLogic.Note;
import AppLogic.TaskLogic.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private ArrayList<Directory> directoryList ;
    private Directory currentDirectory;
    private Task currentTask;
    private Note currentNote;

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
    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
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
    public void removeDirectoryFromFiles() {
        String dirName = currentDirectory.getName();
            if (dirName == null || dirName.trim().isEmpty()) {
                System.err.println("Error: Directory name cannot be empty.");
                return;
            }

            String cleanedDirName = dirName.trim();
            File mainFile = new File("main" + File.separator + "main.txt");

            System.out.println("Attempting to remove '" + cleanedDirName + "' from files...");

            // Part 1: Remove the directory from main.txt
            List<String> directoryList = new ArrayList<>();
            if (mainFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(mainFile))) {
                    String line = reader.readLine();
                    if (line != null && !line.trim().isEmpty()) {
                        String[] dirs = line.split(";");
                        for (String dir : dirs) {
                            directoryList.add(dir.trim());
                        }
                    }
                } catch (IOException e) {
                    System.err.println("An error occurred while reading the main directory file: " + e.getMessage());
                    return; // Stop if the read fails
                }

                // Remove the directory name from the list
                if (directoryList.remove(cleanedDirName)) {
                    // Now, write the modified list back to the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(mainFile, false))) {
                        for (int i = 0; i < directoryList.size(); i++) {
                            writer.write(directoryList.get(i));
                            if (i < directoryList.size() - 1) {
                                writer.write(" ; ");
                            }
                        }
                        System.out.println("Directory '" + cleanedDirName + "' removed from main.txt.");
                    } catch (IOException e) {
                        System.err.println("An error occurred while updating the main directory file: " + e.getMessage());
                    }
                } else {
                    System.err.println("Directory '" + cleanedDirName + "' not found in main.txt.");
                }
            } else {
                System.err.println("Error: main.txt not found.");
                return;
            }

            // Part 2: Delete all files that belong to this directory
            File mainDir = new File("main");
            if (mainDir.exists() && mainDir.isDirectory()) {
                File[] files = mainDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        // Check if the file's name starts with the directory name
                        // and handles both .txt and .md extensions
                        if (file.getName().startsWith(cleanedDirName + ".") || file.getName().startsWith(cleanedDirName + "-")) {
                            if (file.delete()) {
                                System.out.println("Deleted file: " + file.getName());
                            } else {
                                System.err.println("Failed to delete file: " + file.getName());
                            }
                        }
                    }
                }
            } else {
                System.err.println("Error: Main directory not found.");
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
    public void removeTaskFromFiles() {
        if (currentTask != null) {
            currentDirectory.getTasks().remove(currentTask);
            saveTaskToFile(); // Saves the directory file without the removed task
        } else {
            System.err.println("Error: No task is currently selected to be removed.");
            return;
        }

        // New logic to remove the notes file
        try {
            // Construct the filename using the same pattern as the saveNotesToFile() method
            String cleanedTaskName = currentTask.getName().replaceAll(" ", "_");
            String notesFileName = "main" + File.separator + currentDirectory.getName() + "-" + cleanedTaskName + ".md";

            File notesFile = new File(notesFileName);

            if (notesFile.exists()) {
                if (notesFile.delete()) {
                    System.out.println("Associated notes file '" + notesFileName + "' deleted successfully.");
                } else {
                    System.err.println("Error: Failed to delete notes file '" + notesFileName + "'.");
                }
            } else {
                System.out.println("No notes file found for task '" + currentTask.getName() + "'.");
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while trying to delete the notes file: " + e.getMessage());
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
                        String[] noteItem = note.split("%");
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
    public void removeNotesFromFile(){
        currentTask.getNotes().remove(currentNote);
        saveNotesToFile();
    }


}
