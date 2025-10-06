package Handlers;

import AppLogic.Archive;
import AppLogic.Directory;
import AppLogic.Note;
import AppLogic.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
    private ArrayList<Archive> archiveList;
//    private ArrayList<Directory> directoryList ;

    private Archive currentArchive;
    private Directory currentDirectory;
    private Task currentTask;
    private Note currentNote;

    public FileHandler(EventHandler eventHandler) {
//        this.directoryList = eventHandler.getDirectoryList();
        archiveList = eventHandler.getArchiveList();
        this.currentDirectory = eventHandler.getCurrentDirectory();
    }
    public void setCurrentArchive(Archive currentArchive) {
        this.currentArchive = currentArchive;
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

    //?Archive
    public void saveArchiveListToFile(){
    }
    public void getArchiveListFromFile() {}
    public void removeArchiveListFromFile(){}
    public void renameDirectoryFromFile(){}

    //?DIR
    public void removeDirectoryFromFiles() {}
    public void renameCurrentDirectory(String newName) {}
    //?TASK
    public void saveTaskToFile() {}
    public void removeTaskFromFiles() {}
    public void renameCurrentTask(String newName) {}
    public void getTaskListFromFile(Archive currentArchive,Directory currentDirectory) {}
    public void saveTaskToFile(Directory currentDirectory) {
    }

    //?NOTE
    public void getNotesFromFile() {}
    public void saveNotesToFile() {}
    public void removeNotesFromFile(){
    }

   }
