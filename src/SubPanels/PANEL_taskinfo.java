package SubPanels;

import AppLogic.Task;
import UserInterface.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class PANEL_taskinfo extends JScrollPane{

    private final JPanel mirrorPanel = new JPanel();
    private final JLabel taskName = new JLabel("Task Name:");
    private final JTextArea taskDescription = new JTextArea("Task Description:");
    private final JScrollPane taskDescriptionPane;
    private final JLabel taskStatus = new JLabel("Task Status:");
    private final JLabel taskPriority = new JLabel("Task Priority:");
    private final JLabel taskTime = new JLabel("Task Time:");
    private final JLabel taskDeadline = new JLabel("Task Deadline:");
    private final JLabel taskType = new JLabel("Task Type:");
    private final JLabel taskRepeatableType= new JLabel("Task Repeatable Type:");
    private final JLabel taskDifficulty= new JLabel("Task Difficulty:");
    private final JLabel taskFinishedDate= new JLabel("Task Finished Date:");


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
        taskDifficulty.setForeground(ColorTheme.getSecnd_accent());
        taskRepeatableType.setForeground(ColorTheme.getSecnd_accent());
        taskFinishedDate.setForeground(ColorTheme.getSecnd_accent());


        mirrorPanel.add(taskName);
        mirrorPanel.add(taskDescriptionPane);
        mirrorPanel.add(taskStatus);
        mirrorPanel.add(taskPriority);
        mirrorPanel.add(taskTime);
        mirrorPanel.add(taskDeadline);
        mirrorPanel.add(taskType);
        mirrorPanel.add(taskRepeatableType);
        mirrorPanel.add(taskDifficulty);
        mirrorPanel.add(taskFinishedDate);

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
        int currentY = 20;
        taskName.setBounds(80,0,width,30);
        taskStatus.setBounds(50,0,200,30);
        taskType.setBounds(30,0,200,30);

        taskDescriptionPane.setBounds(WIDTH/20,40,width/2-40,height);
        taskPriority.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskDifficulty.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskTime.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskFinishedDate.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskRepeatableType.setBounds(WIDTH/2+70,currentY,200,30);
        currentY+=30;
        taskDeadline.setBounds(WIDTH/2+70,currentY,200,30);
        if (width<500){
            setFontSizeAll(13);
        }else if (width<700){
            setFontSizeAll(16);
        }
        else{
            setFontSizeAll(20);
        }
        mirrorPanel.setPreferredSize(new Dimension(WIDTH,currentY+30));
        taskDescription.setPreferredSize(new Dimension(WIDTH,currentY*3));
        mirrorPanel.revalidate();
        mirrorPanel.repaint();
        this.revalidate();
        this.repaint();
    }
    public void addTaskInfo(Task task){
        activate();
        taskName.setText(task.getName());
        taskDescription.setText("DESC: "+task.getDescription());
        taskStatus.setText(task.isFinished()?"\uF4A7":"\uDB80\uDD31");
        taskPriority.setText("URGENCY: "+task.getUrgency());
        taskTime.setText("TIME: "+task.getTimeDedicated()+"min");

        if (task.getDeadline() != null) {
            String deadline = task.getDeadline();
            if (!deadline.equals("none")) {
                taskDeadline.setText(deadline);
                taskDeadline.setVisible(true);
            } else {
                taskDeadline.setVisible(false);
            }
        } else {
            taskDeadline.setVisible(false);
        }
        taskDifficulty.setText("DIFFICULTY: "+task.getDifficulty());
        taskType.setText(task.isRepeatable()?"\uDB81\uDC56":"\uDB81\uDC57");
        taskRepeatableType.setText("\uF01E  "+task.getRepeatableType());
        taskFinishedDate.setText("\uED7A  "+task.getFinishedDate());
        taskRepeatableType.setVisible(task.isRepeatable());
        taskFinishedDate.setVisible(task.isFinished());
    }
    public void updateTaskInfo(Task task){
        taskName.setText(task.getName());
        taskDescription.setText("DESC: "+task.getDescription());
        taskStatus.setText(task.isFinished()?"\uF4A7":"\uDB80\uDD31");
        taskPriority.setText("URGENCY: "+task.getUrgency());
        taskTime.setText("TIME: "+task.getTimeDedicated()+"min");

        if (task.getDeadline() != null) {
            String deadline = task.getDeadline();
            if (!deadline.equals("none")) {
                taskDeadline.setText(deadline);
                taskDeadline.setVisible(true);
            } else {
                taskDeadline.setVisible(false);
            }
        } else {
            taskDeadline.setVisible(false);
        }
        taskDifficulty.setText("DIFFICULTY: "+task.getDifficulty());
        taskType.setText(task.isRepeatable()?"\uDB81\uDC56":"\uDB81\uDC57");
        taskRepeatableType.setText("\uF01E  "+task.getRepeatableType());
        taskFinishedDate.setText("\uED7A  "+task.getFinishedDate());
        taskRepeatableType.setVisible(task.isRepeatable());
        taskFinishedDate.setVisible(task.isFinished());
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
        taskStatus.setFont(new Font("Arial", Font.PLAIN, size+5));
        taskPriority.setFont(new Font("Arial", Font.PLAIN, size));
        taskTime.setFont(new Font("Arial", Font.PLAIN, size));
        taskDeadline.setFont(new Font("Arial", Font.PLAIN, size));
        taskType.setFont(new Font("Arial", Font.PLAIN, size+5));
        taskFinishedDate.setFont(new Font("Arial", Font.PLAIN, size));
        taskRepeatableType.setFont(new Font("Arial", Font.PLAIN, size));
        taskDifficulty.setFont(new Font("Arial", Font.PLAIN, size));
    }
}
