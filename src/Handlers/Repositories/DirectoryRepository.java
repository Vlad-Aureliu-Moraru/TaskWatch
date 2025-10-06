package Handlers.Repositories;

import AppLogic.Directory;

import java.io.*;
import java.util.ArrayList;

public class DirectoryRepository {

    private static final String FILE_PATH = "repository/directories.txt";
    private static final ArrayList<Directory> directories = new ArrayList<>();
    private static boolean isLoaded = false;

    public DirectoryRepository() {
        if (!isLoaded) {
            loadDirectoriesFromFile();
            isLoaded = true;
        }
    }
    private void loadDirectoriesFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Directories file not found. Created new one.");
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
                    int idStart = line.indexOf("id:") + 3;
                    int idEnd = line.indexOf(",", idStart);
                    int id = Integer.parseInt(line.substring(idStart, idEnd).trim());

                    int archiveIdStart = line.indexOf("archiveId:") + 10;
                    int archiveIdEnd = line.indexOf(";", archiveIdStart);
                    int archiveId = Integer.parseInt(line.substring(archiveIdStart, archiveIdEnd).trim());

                    int nameStart = line.indexOf("name:") + 5;
                    int nameEnd = line.indexOf("}", nameStart);
                    String name = line.substring(nameStart, nameEnd).trim();

                    directories.add(new Directory(id, archiveId, name));
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Directory d : directories) {
                writer.write("{id:" + d.getId() + ", archiveId:" + d.getArchiveId() + ";name:" + d.getName() + "}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Directory> getAllDirectories() {
        return new ArrayList<>(directories); // return a copy
    }

    public Directory getDirectoryById(int id) {
        for (Directory d : directories) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public void addDirectory(Directory dir) {
        boolean exists = directories.stream().anyMatch(d ->
                d.getId() == dir.getId() ||
                        (d.getArchiveId() == dir.getArchiveId() && d.getName().equalsIgnoreCase(dir.getName()))
        );

        if (exists) {
            System.out.println("[WARNING] Directory already exists!");
            return;
        }

        directories.add(dir);
        saveAllToFile();
    }

    public void updateDirectory(int id, String newName) {
        for (Directory d : directories) {
            if (d.getId() == id) {
                d.setName(newName);
                saveAllToFile();
                break;
            }
        }
    }

    public void deleteDirectory(int id) {
        directories.removeIf(d -> d.getId() == id);
        saveAllToFile();
    }
        public static void main(String[] args) {
            DirectoryRepository repo = new DirectoryRepository();

            repo.addDirectory(new Directory(1, 100, "Documents"));
            repo.addDirectory(new Directory(2, 100, "Images"));
            repo.addDirectory(new Directory(3, 101, "Projects"));

            // Attempt to add a duplicate (same id)
            repo.addDirectory(new Directory(1, 102, "Videos")); // Should warn

            // Attempt to add a duplicate name in same archive
            repo.addDirectory(new Directory(4, 100, "Documents")); // Should warn

            // List all directories
            System.out.println("All directories:");
            for (Directory d : repo.getAllDirectories()) {
                System.out.println(d);
            }

            // Get a directory by ID
            System.out.println("\nDirectory with ID 2:");
            System.out.println(repo.getDirectoryById(2));

            // Update a directory
            repo.updateDirectory(2, "Pictures");
            System.out.println("\nAfter update:");
            System.out.println(repo.getDirectoryById(2));

            // Delete a directory
            repo.deleteDirectory(3);
            System.out.println("\nAfter deletion:");
            for (Directory d : repo.getAllDirectories()) {
                System.out.println(d);
            }
        }
}
