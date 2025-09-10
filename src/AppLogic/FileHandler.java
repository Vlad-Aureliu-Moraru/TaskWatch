package AppLogic;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.NotesLogic.Note;
import AppLogic.TaskLogic.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
                        // and handles both .txt and .txt extensions
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
    public void renameCurrentDirectory(String newName) {
        String  oldName= currentDirectory.getName();
        File directory = new File("main");

            // Check if the directory exists and is actually a directory
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("Error: The provided path is not a valid directory.");
                return;
            }

            // Get all files and subdirectories in the directory
            File[] files = directory.listFiles();
            if (files == null) {
                System.err.println("Error: Could not list files in the directory.");
                return;
            }

            // Iterate through each file and rename it if its name contains the oldName
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.equals(oldName+".txt")) {
                    String newFileName = fileName.replace(oldName, newName);
                    File newFile = new File(directory, newFileName);

                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed: " + fileName + " -> " + newFileName);
                    } else {
                        System.err.println("Failed to rename: " + fileName);
                    }
                }else if (fileName.matches(oldName+"-.*.txt")){
                    String savingPart = fileName.substring(oldName.length()+1);
                    String newFileName = newName+"-"+savingPart;
                    File newFile = new File(directory, newFileName);

                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed: " + fileName + " -> " + newFileName);
                    } else {
                        System.err.println("Failed to rename: " + fileName);
                    }
                }
            }
    }
    //?TASK
    public void getTaskListFromFile() {
        // Check for null directory first to prevent a NullPointerException.
        if (currentDirectory == null) {
            System.err.println("Error: No current directory is set to load tasks from.");
            return;
        }

        // Use a platform-independent path.
        // File.separator is better than hardcoding "\\".
        String fileName = "main" + File.separator + currentDirectory.getName() + ".txt";
        File taskFile = new File(fileName);

        if (!taskFile.exists()) {
            System.err.println("Error: Task file not found for directory '" + currentDirectory.getName() + "'.");
            return;
        }

        System.out.println("Loading tasks from " + fileName + "...");
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            // Process each line as a potential Task object
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue; // Skip empty lines
                }

                // A single Task object for the current line
                Task currentTask = new Task();
                boolean isValidTask = true;

                // Remove outer brackets and split into key-value pairs
                if (trimmedLine.startsWith("{") && trimmedLine.endsWith("}")) {
                    String content = trimmedLine.substring(1, trimmedLine.length() - 1);
                    String[] keyValues = content.split(";");

                    for (String kv : keyValues) {
                        // Split each key-value pair at the first colon
                        if (kv == null || kv.trim().isEmpty()) {
                            break;
                        }
                        String[] parts = kv.split(":", 2);
                        if (parts.length < 2) {
                            System.err.println("Invalid format: " + kv);
                            isValidTask = false;
                            break;
                        }
                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        // Use a switch statement for cleaner field assignment
                        switch (key) {
                            case "name":
                                currentTask.setName(value);
                                break;
                            case "urgency":
                                currentTask.setUrgency(Integer.parseInt(value));
                                break;
                            case "difficulty":
                                currentTask.setDifficulty(Integer.parseInt(value));
                                break;
                            case "repeatableType":
                                currentTask.setRepeatableType(value);
                                break;
                            case "description":
                                currentTask.setDescription(value);
                                break;
                            case "isRepeatable":
                                currentTask.setRepeatable(Boolean.parseBoolean(value));
                                break;
                            case "isFinished":
                                currentTask.setFinished(Boolean.parseBoolean(value));
                                break;
                            case "deadline":
                                currentTask.setDeadline(value);
                                break;
                            case "time":
                                // Catch NumberFormatException to prevent crash
                                try {
                                    currentTask.setTimeDedicated(Integer.parseInt(value));
                                } catch (NumberFormatException e) {
                                    System.err.println("Invalid Time value for task: " + currentTask.getName());
                                    isValidTask = false;
                                }
                                break;
                            default:
                                System.err.println("Unknown field: " + key);
                                break;
                        }
                    }
                } else {
                    System.err.println("Skipping malformed line: " + line);
                    isValidTask = false;
                }

                // Only add the task to the list if it was parsed successfully
                if (isValidTask) {
                    currentDirectory.getTasks().add(currentTask);
                }
            }
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
            String notesFileName = "main" + File.separator + currentDirectory.getName() + "-" + cleanedTaskName + ".txt";

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
    public void renameCurrentTask(String newName) {
        String oldName = currentTask.getName().replaceAll(" ", "_");
        String sanitizedNewName = newName.replaceAll(" ", "_");

        File directory = new File("main");
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Error: The provided path is not a valid directory.");
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            System.err.println("Error: Could not list files in the directory.");
            return;
        }

        // Escape any special regex characters in the old name
        String escapedOldName = Pattern.quote(oldName);

        // The pattern now reliably looks for a file ending with "-<escapedOldName>.txt"
        String pattern = ".*-" + escapedOldName + "\\.txt";

        boolean renamed = false;
        for (File file : files) {
            String fileName = file.getName();

            if (fileName.matches(pattern)) {
                // Find the index of the hyphen right before the oldName
                int hyphenIndex = fileName.lastIndexOf("-" + oldName + ".");
                if (hyphenIndex == -1) {
                    // This shouldn't happen if matches() returned true, but as a safeguard.
                    continue;
                }

                String savingPart = fileName.substring(0, hyphenIndex + 1);
                String newFileName = savingPart + sanitizedNewName + ".txt";

                File newFile = new File(directory, newFileName);

                if (file.renameTo(newFile)) {
                    System.out.println("Renamed: " + fileName + " -> " + newFileName);
                    currentTask.setName(newName); // Update in-memory task name
                    renamed = true;
                    break; // Stop after renaming the first match
                } else {
                    System.err.println("Failed to rename: " + fileName);
                    // Optionally add more details: System.err.println("Possible reasons: permissions, file is open.");
                }
            }
        }

        if (!renamed) {
            System.err.println("Error: No file found matching the pattern for task '" + oldName + "'.");
        }
    }
    //?NOTE
    public void getNotesFromFile() {
        ArrayList<Note> noteList = new ArrayList<>();

        if (currentDirectory == null) {
            System.err.println("Error: No current directory is set to load notes from.");
            return;
        }
        if (currentTask == null) {
            System.err.println("Error: No current task is set to load notes from.");
            return;
        }

        String fileName = "main" + File.separator + currentDirectory.getName() + "-" + currentTask.getName().replaceAll(" ", "_") + ".txt";
        File notesFile = new File(fileName);

        if (!notesFile.exists()) {
            System.out.println("No notes file found for task '" + currentTask.getName() + "'. Skipping note loading.");
            return;
        }

        System.out.println("Loading notes from " + fileName + "...");

        try (BufferedReader reader = new BufferedReader(new FileReader(notesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue;
                }

                if (trimmedLine.startsWith("{") && trimmedLine.endsWith("}")) {
                    Note currentNote = new Note();
                    boolean isValidNote = true;

                    String content = trimmedLine.substring(1, trimmedLine.length() - 1);
                    String[] keyValues = content.split(";");

                    for (String kv : keyValues) {
                        String[] parts = kv.split(":", 2);
                        if (parts.length < 2) {
                            System.err.println("Invalid key-value pair format: " + kv);
                            isValidNote = false;
                            break;
                        }
                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        switch (key) {
                            case "date":
                                currentNote.setDate(value);
                                break;
                            case "note":
                                currentNote.setNote(value);
                                break;
                            default:
                                System.err.println("Unknown field in note: " + key);
                                isValidNote = false;
                                break;
                        }
                    }

                    if (isValidNote) {
                        noteList.add(currentNote);
                    }
                } else {
                    System.err.println("Skipping malformed note line: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred while loading notes: " + e.getMessage());
            e.printStackTrace();
        }

        currentTask.setNotes(noteList);
        System.out.println("Notes loaded successfully for task '" + currentTask.getName() + "'. Total notes: " + noteList.size());
        System.out.println("CURRENT TASK NOTES " + currentTask.getNotes());
    }
    public void saveNotesToFile() {
        if (currentDirectory == null) {
            System.err.println("Error: No current directory selected.");
            return;
        }

        String fileName = "main" + File.separator +currentDirectory.getName()+"-"+currentTask.getName().replaceAll(" ","_") + ".txt";
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
