package AppLogic;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.TaskLogic.Task;
import UserInterface.MainMenu.PANEL_mainmenu;
import UserInterface.NavBar.PANEL_navbar;
import UserInterface.TaskRelated.PANEL_list;

import java.io.*;
import java.util.ArrayList;

public class EventHandler {
    private PANEL_list panelList;
    private PANEL_mainmenu panelMainmenu;
    private PANEL_navbar panelnavbar;

    private ArrayList<Directory> directoryList = new ArrayList();
    private Directory currentDirectory;
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
    public void addDirectory(Directory directory) {
        this.getDirectoryListFromFile();
        for (Directory dir : directoryList) {
            if (dir.getName().equals(directory.getName())) {
                System.out.println("Directory already exists.");
                return;
            }
        }
        directoryList.add(directory);
        saveDirectoryListToFile();
        panelList.loadDirs();

    }
    public ArrayList<Directory> getDirectoryList() {
        return directoryList;
    }

    public void printDirectoryList() {
        for (int i = 0; i < directoryList.size(); i++) {
            System.out.println(i+" "+directoryList.get(i).getName());
        }
    }
    public Directory getCurrentDirectory() {
        return currentDirectory;
    }
    public PANEL_list getPanelList() {
        return panelList;
    }
    public void setPanelList(PANEL_list panelList) {
        this.panelList = panelList;
    }
    public PANEL_mainmenu getPanelMainmenu() {
        return panelMainmenu;
    }
    public void setPanelMainmenu(PANEL_mainmenu panelMainmenu) {
        this.panelMainmenu = panelMainmenu;
    }
    public PANEL_navbar getPanelnavbar() {
        return panelnavbar;
    }
    public void setPanelnavbar(PANEL_navbar panelnavbar) {
        this.panelnavbar = panelnavbar;
    }
    public void setDirectoryList(ArrayList<Directory> directoryList) {
        this.directoryList = directoryList;
    }
    public void setCurrentDirectory(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
        panelnavbar.setCurrentPATH(currentDirectory.getName()+"/");
    }
    public void resetCurrentDirectory() {
        this.currentDirectory = null;
        panelnavbar.setCurrentPATH("~");
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

    //!WORK ON THIS
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
// task.setRepeatable(taskLine[2]);
                task.setRepeatable(false);
// task.isFinished(taskLine[3]);
                task.setFinished(false);
// task.setDeadline(taskLine[4]);
                task.setDeadline(null);
    // task.setUrgency(taskLine[5]);
                task.setUrgency(1);
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
    public void loadcurrentDirectoryTasksToUIList(){
        panelList.loadCurrentDirTasks();
    }
}