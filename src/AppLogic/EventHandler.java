package AppLogic;

import AppLogic.DirectoryLogic.Directory;

import java.io.*;
import java.util.ArrayList;

public class EventHandler {
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
        directoryList.add(directory);
    }
    public ArrayList<Directory> getDirectoryList() {
        return directoryList;
    }
    public void printDirectoryList() {
        for (Directory directory : directoryList) {
            System.out.println(directory.getName());
        }
    }
}