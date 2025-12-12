package Logic.Handlers;

import java.io.*;

public class FileHandler {

    private final EventHandler eventHandler;
    public FileHandler(EventHandler eventHandler) {
//        this.directoryList = eventHandler.getDirectoryList();
//        archiveList = eventHandler.getArchiveList();
//        this.currentDirectory = eventHandler.getCurrentDirectory();
        this.eventHandler = eventHandler;
    }
    public void checkFileStructure() {
        File repositoryDir = new File("repository");
        if (!repositoryDir.exists()) {
            boolean dirCreated = repositoryDir.mkdir();
            if (dirCreated) {
                System.out.println("Created directory: " + repositoryDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory: " + repositoryDir.getAbsolutePath());
                return;
            }
        }

        String[] fileNames = {"archives.txt", "directories.txt", "tasks.txt", "notes.txt"};
        for (String fileName : fileNames) {
            File file = new File(repositoryDir, fileName);
            if (!file.exists()) {
                try {
                    boolean fileCreated = file.createNewFile();
                    if (fileCreated) {
                        System.out.println("Created file: " + file.getAbsolutePath());
                    } else {
                        System.out.println("Failed to create file: " + file.getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.out.println("Error creating file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }

   }
