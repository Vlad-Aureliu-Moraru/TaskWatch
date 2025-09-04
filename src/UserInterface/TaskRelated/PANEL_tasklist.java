package UserInterface.TaskRelated;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PANEL_tasklist extends JScrollPane {
    private JPanel panel = new JPanel();
    private ArrayList<PANEL_task> tasklist = new ArrayList<>();
    private int HEIGHT;
    private int WIDTH;
    private int GAP = 20;
    private int MARGIN = 10;

    public PANEL_tasklist() {
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

        PANEL_task task1 = new PANEL_task();
        PANEL_task task2 = new PANEL_task();
        PANEL_task task3 = new PANEL_task();
        PANEL_task task4 = new PANEL_task();
        PANEL_task task5 = new PANEL_task();
        PANEL_task task6 = new PANEL_task();
        addTask(task1);
        addTask(task2);
        addTask(task3);
        addTask(task4);
        addTask(task5);
        addTask(task6);
        System.out.println("PANEL_tasklist created"+this.getWidth()+"x"+HEIGHT);
    }

    public void addTask(PANEL_task task) {
        System.out.println("adding task");
        tasklist.add(task);
    }
    public void addTasksToList(){
        panel.removeAll();
        int currentY = 10;

        for(int j=0;j<tasklist.size();j++){
            PANEL_task task = tasklist.get(j);
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