package AppLogic;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.NotesLogic.Note;
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
    private FileHandler fileHandler ;

    private ArrayList<Directory> directoryList = new ArrayList();
    private Directory currentDirectory;
    private Task currentTask;
    private Note currentNote;


    public EventHandler() {
        fileHandler = new FileHandler(this);
    }

    public void addDirectory(Directory directory) {
        fileHandler.getDirectoryListFromFile();
        for (Directory dir : directoryList) {
            if (dir.getName().equals(directory.getName())) {
                System.out.println("Directory already exists.");
                return;
            }
        }
        directoryList.add(directory);
        fileHandler.saveDirectoryListToFile();
        panelList.loadDirs();

    }
    public void addNote(Note note) {
        getCurrentTask().addNote(note);
        getFileHandler().saveNotesToFile();
        getPanelList().loadCurrentTaskNotes();
    }
    public void addTask(Task task) {
        System.out.println("adding task");
        this.getCurrentDirectory().addTask(task);
        getFileHandler().saveTaskToFile();
        panelList.loadCurrentDirTasks();
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
        fileHandler.setCurrentDirectory(currentDirectory);
        panelnavbar.setCurrentPATH(currentDirectory.getName()+"/");
    }
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
        fileHandler.setCurrentTask(currentTask);
        panelnavbar.setCurrentPATH(currentDirectory.getName()+"/"+currentTask.getName());
    }
    public Task getCurrentTask() {
        return currentTask;
    }
    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }
    public Note getCurrentNote() {
        return currentNote;
    }

    public void resetCurrentDirectory() {
        this.currentDirectory = null;
        panelnavbar.setCurrentPATH("~");
    }
    public void resetCurrentTask() {
        this.currentTask = null;
        panelnavbar.setCurrentPATH(currentDirectory.getName()+"/");
    }
    public void resetCurrentNote() {
        panelnavbar.setCurrentPATH("FIXEVENT HANDLER");
        this.currentNote = null;
    }
    public FileHandler getFileHandler() {
        return fileHandler;
    }
}