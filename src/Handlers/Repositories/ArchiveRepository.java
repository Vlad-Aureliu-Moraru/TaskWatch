package Handlers.Repositories;

import AppLogic.Archive;

import java.util.ArrayList;

import java.io.*;
import java.util.ArrayList;

public class ArchiveRepository {

    private static final String FILE_PATH = "repository/archives.txt";
    private static final ArrayList<Archive> archives = new ArrayList<>();
    private static boolean isLoaded = false;

    public ArchiveRepository() {
        if (!isLoaded) {
            loadArchivesFromFile();
            isLoaded = true;
        }
    }
    private void loadArchivesFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("Archives file not found. Creating new one...");
            try {
                file.createNewFile();
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

                    int nameStart = line.indexOf("archiveName:") + 12;
                    int nameEnd = line.indexOf("}", nameStart);
                    String archiveName = line.substring(nameStart, nameEnd).trim();

                    archives.add(new Archive(id, archiveName));
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
            for (Archive archive : archives) {
                writer.write("{id:" + archive.getId() + ", archiveName:" + archive.getArchiveName() + "}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Archive> getAllArchives() {
        return new ArrayList<>(archives);
    }
    public Archive getArchiveById(int id) {
        for (Archive a : archives) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
    public void addArchive(Archive archive) {
        if (archives.stream().anyMatch(a -> a.getId() == archive.getId()) && archives.stream().anyMatch(a -> a.getArchiveName().equals(archive.getArchiveName()))) {
            System.out.println("[WARNING] Archive already exists!]");
            return;
        }
        archives.add(archive);
        saveAllToFile();
    }

    public void updateArchive(int id, String newName) {
        for (Archive a : archives) {
            if (a.getId() == id) {
                a.setArchiveName(newName);
                saveAllToFile();
                break;
            }
        }
    }
    public void deleteArchive(int id) {
        archives.removeIf(a -> a.getId() == id);
        saveAllToFile();
    }
    public static void main(String[] args) {
        ArchiveRepository repo = new ArchiveRepository();

        // Add archives
        repo.addArchive(new Archive(1, "First Archive"));
        repo.addArchive(new Archive(2, "Second Archive"));

        // Retrieve all
        for (Archive a : repo.getAllArchives()) {
            System.out.println(a);
        }

        // Get by ID
        System.out.println("Get ID 1: " + repo.getArchiveById(1));

        // Update and save
        repo.updateArchive(2, "Updated Archive Name");
    }
}
