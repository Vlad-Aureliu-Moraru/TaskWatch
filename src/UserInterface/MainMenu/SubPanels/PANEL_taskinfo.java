package UserInterface.MainMenu.SubPanels;

import AppLogic.FontLoader;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class PANEL_taskinfo extends JScrollPane{

    private JPanel mirrorPanel = new JPanel();
    private JLabel taskName = new JLabel("Task Name:");
    private JTextArea taskDescription = new JTextArea("Task Description:");
    private JScrollPane taskDescriptionPane;
    private JLabel taskStatus = new JLabel("Task Status:");
    private JLabel taskPriority = new JLabel("Task Priority:");
    private JLabel taskTime = new JLabel("Task Time:");
    private JLabel taskDeadline = new JLabel("Task Deadline:");
    private JLabel taskType = new JLabel("Task Type:");
    private JLabel taskRepeatableType= new JLabel("Task Repeatable Type:");
    private JLabel taskDifficulty= new JLabel("Task Difficulty:");
    private JLabel taskFinishedDate= new JLabel("Task Finished Date:");


    private boolean active = false;


    private int HEIGHT;
    private int WIDTH;

    public PANEL_taskinfo() {
        mirrorPanel.setBackground(ColorTheme.getMain_color());
        mirrorPanel.setLayout(null);
        this.setVisible(false);

        taskName.setForeground(ColorTheme.getSecnd_accent());

        taskDescription.setForeground(ColorTheme.getSecnd_accent());
        taskDescription.setEditable(false);
        taskDescription.setLineWrap(true);
        taskDescription.setWrapStyleWord(true);
        taskDescription.setBackground(ColorTheme.getMain_color());
        taskDescription.setOpaque(true);
        taskDescription.setFocusable(false);

        taskDescriptionPane = new JScrollPane(taskDescription);
        taskDescriptionPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        taskDescriptionPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        taskDescriptionPane.setViewportBorder(BorderFactory.createEmptyBorder());
        taskDescriptionPane.setBorder(BorderFactory.createEmptyBorder());
        taskDescriptionPane.setOpaque(true);
        taskDescriptionPane.setAutoscrolls(true);
        taskDescriptionPane.setWheelScrollingEnabled(true);
        taskDescriptionPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        taskStatus.setForeground(ColorTheme.getSecnd_accent());
        taskPriority.setForeground(ColorTheme.getSecnd_accent());
        taskTime.setForeground(ColorTheme.getSecnd_accent());
        taskDeadline.setForeground(ColorTheme.getSecnd_accent());
        taskType.setForeground(ColorTheme.getSecnd_accent());

        mirrorPanel.add(taskName);
        mirrorPanel.add(taskDescriptionPane);
        mirrorPanel.add(taskStatus);
        mirrorPanel.add(taskPriority);
        mirrorPanel.add(taskTime);
        mirrorPanel.add(taskDeadline);
        mirrorPanel.add(taskType);

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        mirrorPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setViewportView(mirrorPanel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setViewportBorder(BorderFactory.createEmptyBorder());
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);
        this.setAutoscrolls(true);
        this.setWheelScrollingEnabled(true);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        getVerticalScrollBar().setUnitIncrement(80);

    }
    public void setHEIGHTandWIDTH(int height, int width){
        this.HEIGHT = height;
        this.WIDTH = width;
        int currentY = 30;
        taskName.setBounds(WIDTH/2-150,0,width,30);
        taskDescriptionPane.setBounds(WIDTH/20,currentY,width/2-40,height);

        taskStatus.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskPriority.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskTime.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskDeadline.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskType.setBounds(WIDTH/2+70,currentY,200,30);
        if (width<500){
            setFontSizeAll(13);
        }else if (width<700){
            setFontSizeAll(16);
        }
        else{
            setFontSizeAll(20);
        }
        mirrorPanel.setPreferredSize(new Dimension(WIDTH,currentY));
        taskDescription.setPreferredSize(new Dimension(WIDTH,currentY*3));
        mirrorPanel.revalidate();
        mirrorPanel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void addTaskInfo(Task task){
        activate();
        taskName.setText("\uF06A  "+task.getName());
        taskDescription.setText("DESC: "+task.getDescription());
        taskStatus.setText(task.isFinished()?"Finished":"Not Finished");
        taskPriority.setText("URGENCY: "+task.getUrgency()+"");
        taskTime.setText("TIME: "+task.getTimeDedicated()+"min");
        if (task.getDeadline() != null) {
            String deadline = task.getDeadline();

            if (!deadline.equals("none")) {
                taskDeadline.setText(deadline);
            } else {
                taskDeadline.setText("NO DEADLINE SET");
            }
        } else {
            taskDeadline.setText("NO DEADLINE SET");
        }

        taskType.setText(task.isRepeatable()?"Repeatable":"Not Repeatable");
    }
    public void updateTaskInfo(Task task){
        taskName.setText("\uF06A  "+task.getName());
        taskDescription.setText("DESC: "+task.getDescription());
        taskStatus.setText(task.isFinished()?"Finished":"Not Finished");
        taskPriority.setText("URGENCY: "+task.getUrgency()+"");
        taskTime.setText("TIME: "+task.getTimeDedicated()+"min");
        if (task.getDeadline() != null) {
            String deadline = task.getDeadline();

            if (!deadline.trim().equalsIgnoreCase("null")) {
                taskDeadline.setText(deadline);
            } else {
                taskDeadline.setText("NO DEADLINE SET");
            }
        } else {
            taskDeadline.setText("NO DEADLINE SET");
        }

        taskType.setText(task.isRepeatable()?"Repeatable":"Not Repeatable");
    }
    public void activate(){
        if (active){
            this.setVisible(false);
            active = false;
        }
        else{
            this.setVisible(true);
            active = true;
        }
    }
    public void deactivate(){
        this.setVisible(false);
        active = false;
    }
    private void setFontSizeAll(int size){
        taskName.setFont(new Font("Arial", Font.PLAIN,size));
        taskDescription.setFont(new Font("Arial", Font.PLAIN,size));
        taskStatus.setFont(new Font("Arial", Font.PLAIN, size));
        taskPriority.setFont(new Font("Arial", Font.PLAIN, size));
        taskTime.setFont(new Font("Arial", Font.PLAIN, size));
        taskDeadline.setFont(new Font("Arial", Font.PLAIN, size));
        taskType.setFont(new Font("Arial", Font.PLAIN, size));
    }
}
