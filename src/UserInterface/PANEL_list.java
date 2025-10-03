package UserInterface;

import AppLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.Task;
import ConfigRelated.ThemeLoader;
import SubElements.PANEL_dir;
import SubElements.PANEL_note;
import SubElements.PANEL_task;

import javax.swing.*;
import java.awt.*;

public class PANEL_list extends JScrollPane {

    private final JPanel panel = new JPanel();


    private int HEIGHT;
    private int WIDTH;
    private final int GAP = 20;
    private final int MARGIN = 10;
    private int stage = -1 ;//? 0 - mainmenu | 1 - dirmenu | 2 - taskmenu | 3 - noteclicked ;
    private boolean noteSelected = false;
    private boolean showingFinished = false;

    private EventHandler eventHandler;

    public PANEL_list() {

        panel.setBackground(ThemeLoader.getSecondaryColor());

        panel.setLayout(null);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setViewportView(panel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setViewportBorder(BorderFactory.createEmptyBorder());
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);
        this.setVisible(true);
        this.setAutoscrolls(true);
        this.setWheelScrollingEnabled(true);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        getVerticalScrollBar().setUnitIncrement(10);


    }

    public void setHEIGHTandWIDTH(int width,int height) {
        this.HEIGHT = height;
        this.WIDTH = width;
        if (stage ==0){
            loadDirs();
        }else if (stage ==1){
            loadCurrentTasks();
        } else if (stage == 2) {
            loadCurrentTaskNotes();

        }
    }

    public void loadDirs(){
        stage=0;
        panel.removeAll();
        int currentY = 10;
        for(Directory dir : eventHandler.getDirectoryList()){
            PANEL_dir directory = new PANEL_dir(dir);
            directory.setEventHandler(eventHandler);
            directory.setBounds(MARGIN,currentY,WIDTH-40,HEIGHT/7);
            directory.setHEIGHTandWIDTH(WIDTH-40,HEIGHT/7);
            panel.add(directory);
            currentY+= directory.getHeight()+GAP;
        }
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void reloadDirs(){
        stage=0;
        panel.removeAll();
        int currentY = 10;
        eventHandler.loadEverythingInMemory();
        for(Directory dir : eventHandler.getDirectoryList()){
            PANEL_dir directory = new PANEL_dir(dir);
            directory.setEventHandler(eventHandler);
            directory.setBounds(MARGIN,currentY,WIDTH-40,HEIGHT/7);
            directory.setHEIGHTandWIDTH(WIDTH-40,HEIGHT/7);
            panel.add(directory);
            currentY+= directory.getHeight()+GAP;
        }
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void loadCurrentDirTasks(){
        stage = 1;
        panel.removeAll();
        int currentY = 10;

        for(Task taskItem: eventHandler.getCurrentDirectory().getTasks()){
            if (!taskItem.isFinished()){
                PANEL_task task = new PANEL_task(taskItem);
                task.setBounds(MARGIN,currentY,WIDTH-40,HEIGHT/7);
                task.setHEIGHTandWIDTH(HEIGHT/7,WIDTH-40);
                task.setEventHandler(eventHandler);
                panel.add(task);
                currentY+= task.getHeight()+GAP;
            }
        }
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void loadCurrentDirTasksFinished(){
        stage = 1;
        panel.removeAll();
        int currentY = 10;
        eventHandler.getCurrentDirectory().sortTaskByFinishedStatus(false);

        for(Task taskItem: eventHandler.getCurrentDirectory().getTasks()){

                PANEL_task task = new PANEL_task(taskItem);
                task.setBounds(MARGIN,currentY,WIDTH-40,HEIGHT/7);
                task.setHEIGHTandWIDTH(HEIGHT/7,WIDTH-40);
                task.setEventHandler(eventHandler);
                panel.add(task);
                currentY+= task.getHeight()+GAP;
        }
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void loadCurrentTasks(){
        if (showingFinished){
            loadCurrentDirTasksFinished();
        }else{
            loadCurrentDirTasks();
        }
    }
    public void switchShowingFinished(){
        showingFinished = !showingFinished;
    }
    public void loadCurrentTaskNotes(){
        stage = 2;
        panel.removeAll();
        int currentY = 10;

        for(int j=0;j<eventHandler.getCurrentTask().getNotes().size();j++){
            PANEL_note note= new PANEL_note(eventHandler.getCurrentTask().getNotes().get(j));
            note.setBounds(MARGIN,currentY,WIDTH-40,HEIGHT/7);
            note.setHEIGHTandWIDTH(HEIGHT/7,WIDTH-40);
            note.setEventHandler(eventHandler);
            panel.add(note);
            currentY+=note.getHeight()+GAP;
        }
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void setStage(int stage) {
        System.out.println("setting stage " + stage);
        if (stage == 0){
            loadDirs();
            eventHandler.resetCurrentDirectory();
        }
        else if (stage ==1){
            loadCurrentTasks();
            eventHandler.resetCurrentTask();
        }
        else if (stage ==2){
            loadCurrentTaskNotes();
        }
    }
    public int getStage(){
        return stage;
    }


    public boolean isNoteSelected(){
        return noteSelected;
    }
    public void setNoteSelected(boolean selected){
        noteSelected = selected;
    }


    public void sortTasksByUrgency(boolean ascending){
        eventHandler.getCurrentDirectory().sortTasksByUrgency(ascending);
        loadCurrentTasks();
        eventHandler.getFileHandler().saveTaskToFile();

    }
    public void sortByDifficulty(boolean ascending){
        eventHandler.getCurrentDirectory().sortTasksByDifficulty(ascending);
        loadCurrentTasks();
        eventHandler.getFileHandler().saveTaskToFile();

    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        loadDirs();
    }
    public void refreshComponents(){
        this.revalidate();
        this.repaint();
        for(Component component: panel.getComponents()){
            component.repaint();
            component.revalidate();
        }
    }
}