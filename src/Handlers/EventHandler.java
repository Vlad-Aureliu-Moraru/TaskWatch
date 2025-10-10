package Handlers;

import AppLogic.Archive;
import AppLogic.Directory;
import AppLogic.Note;
import AppLogic.Task;
import Handlers.Repositories.ArchiveRepository;
import Handlers.Repositories.DirectoryRepository;
import Handlers.Repositories.NoteRepository;
import Handlers.Repositories.TaskRepository;
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

    private Archive currentArchive;
    private Directory currentDirectory;
    private Task currentTask;
    private Note currentNote;

    private ArchiveRepository archiveRepository =  new ArchiveRepository();
    private DirectoryRepository directoryRepository =  new DirectoryRepository();
    private TaskRepository taskRepository =  new TaskRepository();
    private NoteRepository noteRepository =  new NoteRepository();


    public EventHandler() {
        fileHandler = new FileHandler(this);
    }
    //?archive related
    public void addArchive(String archiveName){
        archiveRepository.addArchive(archiveName);
        panelList.loadArchives();
    }
    public void updateArchive(String newArchiveName){
        archiveRepository.updateArchive(currentArchive.getId(),newArchiveName);
        //todo method to update path
    }
    public void deleteArchive(){
        archiveRepository.deleteArchive(currentArchive.getId());
        directoryRepository.deleteDirectoryByArchiveId(currentArchive.getId());

        currentArchive = null;
        currentDirectory = null;
        currentTask = null;
        currentNote = null;
    }
    public ArrayList<Archive> getAllArchives() {
        return  archiveRepository.getAllArchives();
    }

    //?dir related
    public void addDirectory(String directoryName) {
        directoryRepository.addDirectory(currentArchive.getId(),directoryName);
        panelList.loadDirs();
    }
    public void updateDirectoryName(String newDirectoryName) {
        directoryRepository.updateDirectoryName(currentDirectory.getId(),newDirectoryName);
        //todo method to update path
    }
    public void deleteDirectory(){
        directoryRepository.deleteDirectory(currentDirectory.getId());
        taskRepository.deleteTaskByDirectoryId(currentDirectory.getId());
        currentDirectory = null;
        currentTask = null;
        currentNote = null;
    }
    public ArrayList<Directory> getDirectories(){
        return directoryRepository.getAllDirectories();
    }

    //?task related
    public void addTask(Task task) {
        taskRepository.addTask(currentDirectory.getId(),task);
        panelList.loadCurrentTasks(null);
        panelMainmenu.getPanel_reminder().loadReminder();
    }
    public void updateTaskDetails(Task task) {
        taskRepository.updateTask(currentTask.getId(),currentDirectory.getId(),task);
        currentTask = taskRepository.getTaskById(currentTask.getId());
        getPanelMainmenu().getPanel_reminder().loadReminder();
        panelMainmenu.getPanel_taskinfo().updateTaskInfo(currentTask);
        panelMainmenu.getPanel_reminder().loadReminder();
        //todo method to update path
    }
    public void deleteTask() {
        taskRepository.deleteTask(currentTask.getId());
        taskRepository.getTasksByDirectoryId(currentDirectory.getId());
        currentTask = null;
        currentNote = null;
        panelMainmenu.getPanel_reminder().loadReminder();
        getPanelMainmenu().getPanel_taskinfo().deactivate();
        getPanelMainmenu().getPanel_clock().activate();
        getPanelMainmenu().getPanel_noteinfo().deactivate();
    }
    public void setTaskFinished(Boolean finished) {
        taskRepository.setTaskFinished(currentTask.getId() , finished);
        panelMainmenu.getPanel_reminder().loadReminder();
    }
    public ArrayList<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void addNote(Note note) {
        noteRepository.addNote(currentTask.getId(),note);
        panelList.loadCurrentTaskNotes();
    }
    public void updateNote(Note note) {
        noteRepository.updateNote(currentNote.getId(),note.getNote());
    }
    public void deleteNote() {
        noteRepository.deleteNote(currentNote.getId());
        getPanelMainmenu().getPanel_taskinfo().deactivate();
        getPanelMainmenu().getPanel_clock().activate();
        getPanelMainmenu().getPanel_noteinfo().deactivate();
        panelList.loadCurrentTasks(null);
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
    public void setCurrentArchive(Archive currentArchive) {
        this.currentArchive = currentArchive;
//        fileHandler.setCurrentArchive(currentArchive);
        panelnavbar.setCurrentPATH(currentArchive.getArchiveName()+"/");
    }
    public void setCurrentDirectory(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
//        fileHandler.setCurrentDirectory(currentDirectory);
        panelnavbar.setCurrentPATH(currentArchive.getArchiveName()+"/"+currentDirectory.getName()+"/");
    }
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
//        fileHandler.setCurrentTask(currentTask);
        panelnavbar.setCurrentPATH(currentArchive.getArchiveName()+"/"+currentDirectory.getName()+"/"+currentTask.getName()+"/");
    }
    public Task getCurrentTask() {
        return currentTask;
    }
    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
//        fileHandler.setCurrentNote(currentNote);
    }
    public Note getCurrentNote() {
        return currentNote;
    }
    public void resetCurrentArchive() {
        this.currentArchive= null;
        panelnavbar.setCurrentPATH("\uF506 ");
    }
    public void resetCurrentDirectory() {
        this.currentDirectory = null;
        panelnavbar.setCurrentPATH(currentArchive.getArchiveName()+"/");
    }
    public void resetCurrentTask() {
        this.currentTask = null;
        panelnavbar.setCurrentPATH(currentArchive.getArchiveName()+"/"+currentDirectory.getName()+"/");
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void updateDeadlineForRepeatableTasks(Task task) {
        taskRepository.updateTaskDeadline(task.getId());
    }


    public void updateFinishedStatusForRepeatableTasks() {
        taskRepository.updateTaskFinishedStatus();
    }

    public void setMainFrame(FRAME_main mainFrame) {
        this.mainFrame = mainFrame;
    }

    public Archive getCurrentArchive() {
        return currentArchive;
    }


    public FRAME_main getMainFrame() {
        return mainFrame;
    }

    public ArchiveRepository getArchiveRepository() {
        return archiveRepository;
    }

    public DirectoryRepository getDirectoryRepository() {
        return directoryRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
