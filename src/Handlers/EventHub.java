package Handlers;

import AppLogic.Archive;
import AppLogic.Directory;
import AppLogic.Note;
import AppLogic.Task;
import UserInterface.FRAME_main;
import UserInterface.PANEL_list;
import UserInterface.PANEL_mainmenu;
import UserInterface.PANEL_navbar;

import java.util.ArrayList;

public class EventHub {

    private PANEL_list panelList;
    private PANEL_mainmenu panelMainmenu;
    private PANEL_navbar panelnavbar;
    private FRAME_main mainFrame;
//    private final FileHandler fileHandler ;

    private final ArrayList<Archive> archiveList = new ArrayList<>();

    private Archive currentArchive;
    private Directory currentDirectory;
    private Task currentTask;
    private Note currentNote;

    public EventHub() {
//        fileHandler = new FileHandler(this);
    }

//    public void loadEverythingInMemory(){
//        System.out.println("Loading Everything...");
//        fileHandler.getArchiveListFromFile();
//        for (Archive archive : archiveList){
//            for(Directory directory :archive.getDirectories()){
//                fileHandler.getTaskListFromFile(archive,directory);
//            }
//        }
//    }
//
//    public void addArchive(Archive archive){
//        for (Archive arch :archiveList) {
//            System.out.println(arch);
//            if (arch.getArchiveName().equals(archive.getArchiveName())) {
//                System.out.println("Archive already exists.");
//                return;
//            }
//        }
//        archiveList.add(archive);
//        fileHandler.saveArchiveListToFile();
//        panelList.reloadArchives();
//
//    }
}
