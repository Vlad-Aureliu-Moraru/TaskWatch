package Archive;
import Archive.Model.Archive;

import java.io.*;
import java.util.ArrayList;

public class ArchiveRepository {

    private static final String FILE_PATH = "repository/archives.txt";
    private static final ArrayList<Archive> archives = new ArrayList<>();
    private static boolean isLoaded = false;
    private static int currentId = 0;

    public ArchiveRepository() {
        if (!isLoaded) {
            loadArchivesFromFile();
            isLoaded = true;
        }
    }

    private void loadArchivesFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Archives file not found. Created new one.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean idLineFound = false;
            int maxArchiveId = -1;

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
                    int idStart = line.indexOf("id:") + 3;
                    int idEnd = line.indexOf(",", idStart);
                    int id = Integer.parseInt(line.substring(idStart, idEnd).trim());
                    maxArchiveId = Math.max(maxArchiveId, id);

                    int nameStart = line.indexOf("archiveName:") + 12;
                    int nameEnd = line.indexOf("}", nameStart);
                    String archiveName = line.substring(nameStart, nameEnd).trim();

                    archives.add(new Archive(id, archiveName));
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

            if (!idLineFound) {
                if (maxArchiveId >= 0)
                    currentId = maxArchiveId + 1;
                else
                    currentId = 0;
                saveAllToFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            writer.write("id=#" + currentId + "#\n");

            for (Archive archive : archives) {
                writer.write("{id:" + archive.getId() + ", archiveName:" + archive.getArchiveName() + "}\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static int getNextId() {
        /*
        * INCREMENTS THE ID FOR THE NEXT TASK EX : id : 1 -> id : 2
        * */
        currentId++;
        return currentId;
    }

    public ArrayList<Archive> getAllArchives() {
        return new ArrayList<>(archives);
    }

    public Archive getArchiveById(int id) {
        for (Archive a : archives) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    public void addArchive(String archiveName) {
        boolean exists = archives.stream()
                .anyMatch(a -> a.getArchiveName().equalsIgnoreCase(archiveName));

        if (exists) {
            System.out.println("[WARNING] Archive with this name already exists!");
            return;
        }

        int newId = getNextId();
        Archive archive = new Archive(newId, archiveName);
        archives.add(archive);
        saveAllToFile();
    }

    public void updateArchive(int id, String newName) {
        for (Archive a : archives) {
            if (a.getId() == id) {
                a.setArchiveName(newName);
                saveAllToFile();
                return;
            }
        }
        System.out.println("[WARNING] Archive with ID " + id + " not found!");
    }

    public void deleteArchive(int id) {
        archives.removeIf(a -> a.getId() == id);
        saveAllToFile();
    }
public static void main(String[] args) {
        ArchiveRepository repo = new ArchiveRepository();
        repo.addArchive("First Archive");
        repo.addArchive( "Second Archive");

        for (Archive a : repo.getAllArchives()) {
            System.out.println(a);
        }
        System.out.println("Get ID 1: " + repo.getArchiveById(1));
        repo.updateArchive(2, "Updated Archive Name");
    }
}
