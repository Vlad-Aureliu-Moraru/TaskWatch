package UserInterface.TaskRelated;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import UserInterface.TaskRelated.SubElements.PANEL_dir;
import UserInterface.TaskRelated.SubElements.PANEL_note;
import UserInterface.TaskRelated.SubElements.PANEL_task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class PANEL_list extends JScrollPane {

    private JPanel panel = new JPanel();
    private ArrayList<PANEL_dir> dirList = new ArrayList();


    private int HEIGHT;
    private int WIDTH;
    private int GAP = 20;
    private int MARGIN = 10;
    private int stage = 0 ;//? 0 - mainmenu | 1 - dirmenu | 2 - taskmenu | 3 - noteclicked ;
    private boolean noteSelected = false;

    private EventHandler eventHandler;

    public PANEL_list() {

        panel.setBackground(ColorTheme.getSecondary_color());

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
        getVerticalScrollBar().setUnitIncrement(80);


        System.out.println("PANEL_tasklist created"+this.getWidth()+"x"+HEIGHT);
    }

    public void addToList(PANEL_dir dir) {
        System.out.println("adding to DIR List ");
        dirList.add(dir);

    }
    public void addDirsToLayout(){
        panel.removeAll();
        int currentY = 10;

        for(int j=0;j<dirList.size();j++){
            PANEL_dir task =dirList.get(j);
            task.setBounds(MARGIN,currentY,WIDTH-40,HEIGHT/7);
            task.setHEIGHTandWIDTH(WIDTH-40,HEIGHT/7);
            panel.add(task);
            currentY+= task.getHeight()+GAP;
        }
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void setHEIGHTandWIDTH(int width,int height) {
        this.HEIGHT = height;
        this.WIDTH = width;
        if (stage ==0){
            addDirsToLayout();
        }else if (stage ==1){
            loadCurrentDirTasks();
        } else if (stage == 2) {
            loadCurrentTaskNotes();

        }


    }
    public void loadDirs(){
        stage=0;
        dirList.clear();
        eventHandler.getFileHandler().getDirectoryListFromFile();
        System.out.println("loadDirs");
        for (Directory dir : eventHandler.getDirectoryList()){
            PANEL_dir dirPanel = new PANEL_dir(dir);
            dirPanel.setEventHandler(eventHandler);
            addToList( dirPanel);
        }
        addDirsToLayout();
    }
    public void loadCurrentDirTasks(){
        stage = 1;
        panel.removeAll();
        int currentY = 10;

        for(int j=0;j<eventHandler.getCurrentDirectory().getTasks().size();j++){
            PANEL_task task = new PANEL_task(eventHandler.getCurrentDirectory().getTasks().get(j));
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
            loadCurrentDirTasks();
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
        loadCurrentDirTasks();
        eventHandler.getFileHandler().saveTaskToFile();

    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        loadDirs();
    }
}