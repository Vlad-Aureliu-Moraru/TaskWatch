package Directory;

import Directory.Model.Directory;
import Task.TaskRepository;

import java.io.*;
import java.util.ArrayList;

public class DirectoryRepository {

    private static final String FILE_PATH = "repository/directories.txt";
    private static final ArrayList<Directory> directories = new ArrayList<>();
    private static boolean isLoaded = false;
    private static int currentId = 0;

    public DirectoryRepository() {
        if (!isLoaded) {
            loadDirectoriesFromFile();
            isLoaded = true;
        }
    }

    // Load directories from file
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
            boolean idLineFound = false;
            int maxDirectoryId = -1;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Handle id line
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

                // Parse {id:x archiveId:y;name:z}
                try {
                    int idStart = line.indexOf("id:") + 3;
                    int idEnd = line.indexOf(";", idStart);
                    int id = Integer.parseInt(line.substring(idStart, idEnd).trim());

                    int archiveIdStart = line.indexOf("archiveId:") + 10;
                    int archiveIdEnd = line.indexOf(";", archiveIdStart);
                    int archiveId = Integer.parseInt(line.substring(archiveIdStart, archiveIdEnd).trim());

                    int nameStart = line.indexOf("name:") + 5;
                    int nameEnd = line.indexOf("}", nameStart);
                    String name = line.substring(nameStart, nameEnd).trim();

                    directories.add(new Directory(id, archiveId, name));
                    maxDirectoryId = Math.max(maxDirectoryId, id);
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

            // If id line not found, compute new one
            if (!idLineFound) {
                if (maxDirectoryId >= 0)
                    currentId = maxDirectoryId + 1;
                else
                    currentId = 0;
                saveAllToFile(); // Write id line
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save all directories + currentId to file
    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            // Write id line
            writer.write("id=#" + currentId + "#\n");

            // Write all directories
            for (Directory d : directories) {
                writer.write("{id:" + d.getId() + " ;archiveId:" + d.getArchiveId() + ";name:" + d.getName() + "}\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get next ID
    public static int getNextId() {
        currentId++;
        return currentId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    // Get all directories
    public ArrayList<Directory> getAllDirectories() {
        return new ArrayList<>(directories);
    }

    // Get directory by ID
    public Directory getDirectoryById(int id) {
        for (Directory d : directories) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    // Get directories by archive ID
    public ArrayList<Directory> getDirectoriesByArchiveId(int archiveId) {
        ArrayList<Directory> result = new ArrayList<>();
        for (Directory d : directories) {
            if (d.getArchiveId() == archiveId) result.add(d);
        }
        return result;
    }

    public void addDirectory(int archiveId, String name) {
        boolean exists = directories.stream()
                .anyMatch(d -> d.getArchiveId() == archiveId && d.getName().equalsIgnoreCase(name));

        if (exists) {
            System.out.println("[WARNING] Directory with this name already exists in archive " + archiveId);
            return;
        }

        int newId = getNextId();
        Directory newDir = new Directory(newId, archiveId, name);
        directories.add(newDir);
        saveAllToFile();
    }

    public void updateDirectoryName(int id, String newName) {
        for (Directory d : directories) {
            if (d.getId() == id) {
                d.setName(newName);
                saveAllToFile();
                return;
            }
        }
        System.out.println("[WARNING] Directory with ID " + id + " not found!");
    }

    public void updateDirectoryArchiveId(int id,int archiveId) {
        for (Directory d : directories) {
            if (d.getId() == id) {
                d.setArchiveId(archiveId);
                saveAllToFile();
                return;
            }
        }
        System.out.println("[WARNING] Directory with ID " + id + " not found!");
    }
    public void deleteDirectory(int id) {
        TaskRepository taskRepository = new TaskRepository();
        ArrayList<Directory>  toRemove= new ArrayList<>();
        for (Directory d : directories) {
            if (d.getId() == id) {
                taskRepository.deleteTaskByDirectoryId(d.getId());
                toRemove.add(d);
            }
        }
        directories.removeAll(toRemove);
        saveAllToFile();
    }
    public void deleteDirectoryByArchiveId(int archiveId) {
        TaskRepository taskRepository = new TaskRepository();
        ArrayList<Directory>  toRemove= new ArrayList<>();
        for (Directory d : directories) {
            if (d.getArchiveId() == archiveId) {
                toRemove.add(d);
                taskRepository.deleteTaskByDirectoryId(d.getId());
            }
        }
        directories.removeAll(toRemove);
        saveAllToFile();
    }
    public static void main(String[] args) {
        DirectoryRepository directoryRepository = new DirectoryRepository();
        directoryRepository.deleteDirectoryByArchiveId(1);
    }
}
