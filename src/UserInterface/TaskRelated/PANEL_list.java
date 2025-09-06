package UserInterface.TaskRelated;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import UserInterface.TaskRelated.SubElements.PANEL_dir;
import UserInterface.TaskRelated.SubElements.PANEL_note;
import UserInterface.TaskRelated.SubElements.PANEL_task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PANEL_list extends JScrollPane {
    private JPanel panel = new JPanel();
    private ArrayList<PANEL_dir> dirList = new ArrayList();

    private int HEIGHT;
    private int WIDTH;
    private int GAP = 20;
    private int MARGIN = 10;
    private int stage = 0;
    private EventHandler eventHandler;
    public PANEL_list(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        panel.setBackground(Color.blue);
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
        getVerticalScrollBar().setUnitIncrement(80);

        loadDirs();

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

}