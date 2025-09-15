package AppLogic;

import UserInterface.FRAME_main;
import UserInterface.PANEL_mainmenu;
import UserInterface.PANEL_navbar;
import UserInterface.PANEL_list;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventHandler {
    private PANEL_list panelList;
    private PANEL_mainmenu panelMainmenu;
    private PANEL_navbar panelnavbar;
    private FRAME_main mainFrame;
    private final FileHandler fileHandler ;

    private final ArrayList<Directory> directoryList = new ArrayList<>();
    private Directory currentDirectory;
    private Task currentTask;
    private Note currentNote;


    public EventHandler() {
        fileHandler = new FileHandler(this);
    }
    public void loadEverythingInMemory(){
        fileHandler.getDirectoryListFromFile();
        for(Directory directory : directoryList){
            fileHandler.getTaskListFromFile(directory);
        }
    }
    public void addDirectory(Directory directory) {
    loadEverythingInMemory();
        for (Directory dir : directoryList) {
            if (dir.getName().equals(directory.getName())) {
                System.out.println("Directory already exists.");
                return;
            }
        }
        directoryList.add(directory);
        fileHandler.saveDirectoryListToFile();
        panelList.reloadDirs();

    }
    public void addNote(Note note) {
        getCurrentTask().addNote(note);
        getFileHandler().saveNotesToFile();
        getPanelList().loadCurrentTaskNotes();
    }
    public void addTask(Task task) {
        System.out.println("FROM EVENTHANDLER func addTASK");
        this.getCurrentDirectory().addTask(task);
        getFileHandler().saveTaskToFile();
        panelList.loadCurrentTasks();
    }

    public ArrayList<Directory> getDirectoryList() {
        return directoryList;
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
        System.out.println("FROM EVENTHANDLER func setPanelnavbar");
        this.panelnavbar = panelnavbar;
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
        fileHandler.setCurrentNote(currentNote);
    }
    public Note getCurrentNote() {
        return currentNote;
    }
    public void resetCurrentDirectory() {
        this.currentDirectory = null;
        panelnavbar.setCurrentPATH("\uF441 ");
    }
    public void resetCurrentTask() {
        this.currentTask = null;
        panelnavbar.setCurrentPATH(currentDirectory.getName()+"/");
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void updateDeadlineForRepeatableTasks(Task task) {
        if (task.isRepeatable()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate finishedDate = LocalDate.parse(task.getFinishedDate(), formatter);
            String repeatableType = task.getRepeatableType();

            LocalDate newDeadline;

            switch (repeatableType) {
                case "daily" -> newDeadline = finishedDate.plusDays(1);
                case "weekly" -> newDeadline = finishedDate.plusWeeks(1);
                case "biweekly" -> newDeadline = finishedDate.plusWeeks(2);
                case "monthly" -> newDeadline = finishedDate.plusMonths(1);
                default -> {
                    System.out.println("Unknown repeatable type: " + repeatableType);
                    return;
                }
            }
            task.setDeadline(newDeadline.format(formatter));
        }
    }
    public void updateFinishedStatusForRepeatableTasks() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today= LocalDate.now();
        for (Directory directory : directoryList) {
            for (Task task : directory.getTasks()) {
                if (task.isFinished() && task.isRepeatable()) {
                    System.out.println("FINISHED TASK: " + task.getName());
                    LocalDate taskDeadline = LocalDate.parse(task.getDeadline(), formatter);
                    if (taskDeadline.isBefore(today)||taskDeadline.isEqual(today) ) {
                    task.setFinished(false);
                    }
                }
            }
            fileHandler.saveTaskToFile(directory);
        }
    }

    public void setMainFrame(FRAME_main mainFrame) {
        this.mainFrame = mainFrame;
    }

    public FRAME_main getMainFrame() {
        return mainFrame;
    }
}
