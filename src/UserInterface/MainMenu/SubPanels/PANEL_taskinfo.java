package UserInterface.MainMenu.SubPanels;

import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class PANEL_taskinfo extends JPanel {
    private JLabel taskName = new JLabel("Task Name:");
    private JTextArea taskDescription = new JTextArea("Task Description:");
    private JLabel taskStatus = new JLabel("Task Status:");
    private JLabel taskPriority = new JLabel("Task Priority:");
    private JLabel taskTime = new JLabel("Task Time:");
    private JLabel taskDeadline = new JLabel("Task Deadline:");
    private JLabel taskType = new JLabel("Task Type:");

    private boolean active = false;


    private int HEIGHT;
    private int WIDTH;

    public PANEL_taskinfo() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        this.setVisible(false);

        taskName.setForeground(ColorTheme.getSecnd_accent());
        taskName.setFont(new Font("Times New Roman", Font.BOLD, 20));

        taskDescription.setForeground(ColorTheme.getSecnd_accent());
        taskDescription.setEditable(false);
        taskDescription.setLineWrap(true);
        taskDescription.setWrapStyleWord(true);
        taskDescription.setOpaque(false);
        taskDescription.setFocusable(false);

        taskStatus.setForeground(ColorTheme.getSecnd_accent());
        taskPriority.setForeground(ColorTheme.getSecnd_accent());
        taskTime.setForeground(ColorTheme.getSecnd_accent());
        taskDeadline.setForeground(ColorTheme.getSecnd_accent());
        taskType.setForeground(ColorTheme.getSecnd_accent());

        this.add(taskName);
        this.add(taskDescription);
        this.add(taskStatus);
        this.add(taskPriority);
        this.add(taskTime);
        this.add(taskDeadline);
        this.add(taskType);

    }
    public void setHEIGHTandWIDTH(int height, int width){
        this.HEIGHT = height;
        this.WIDTH = width;
        taskName.setBounds(WIDTH/2-150,0,300,30);
        taskDescription.setBounds(WIDTH/20,height/5,width/2-40,height);
        taskStatus.setBounds(WIDTH/2,height/6,200,30);
        taskPriority.setBounds(WIDTH/2,height/5+20,200,30);
        taskTime.setBounds(WIDTH/2,height/4+40,200,30);
        taskDeadline.setBounds(WIDTH/2,height/3+70,200,30);
        taskType.setBounds(WIDTH/2,height/2+65,200,30);
    }

    public void addTaskInfo(Task task){
        activate();
        taskName.setText("NAME: "+task.getName());
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
    public void closeInfo(){
        this.setVisible(false);
    }

}
