package UserInterface.TaskRelated;

import AppLogic.EventHandler;

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

    private EventHandler eventHandler = new EventHandler();
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

        PANEL_dir task1 = new PANEL_dir();
        task1.setTitleLabel("Task_1");
        addTask(task1);
        System.out.println("PANEL_tasklist created"+this.getWidth()+"x"+HEIGHT);
    }

    public void addTask(PANEL_dir dir) {
        System.out.println("adding task");
        dirList.add(dir);
    }
    public void addTasksToList(){
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
        addTasksToList();


    }

}