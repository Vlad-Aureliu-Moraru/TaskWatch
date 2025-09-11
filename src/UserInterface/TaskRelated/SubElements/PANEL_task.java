package UserInterface.TaskRelated.SubElements;

import AppLogic.EventHandler;
import AppLogic.FontLoader;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_task extends JPanel {
    private JLabel taskname = new JLabel("Task Name:");
    private JLabel taskUrgency = new JLabel("\uEEBF");
    private JLabel taskFinished = new JLabel("\uDB81\uDF91");
    private JLabel taskUrgent= new JLabel("\uDB81\uDEBD");

    private EventHandler eventHandler;
    private Task currentTask;


    private int WIDTH = 250;
    private int HEIGHT = 30;

    private Color iconColor;
    private Color borderColor;
    private Color backgroundcolor;


    public PANEL_task(Task task) {
        currentTask = task;
        this.setLayout(null);
        taskname.setText(task.getName());
        taskname.setBounds(0, 0, 100, 50);
        taskname.setHorizontalAlignment(JLabel.CENTER);
        taskname.setFont(FontLoader.getCozyFont().deriveFont(15f));
        taskUrgency.setFont(FontLoader.getCozyFont().deriveFont(17f));
        taskFinished.setFont(FontLoader.getCozyFont().deriveFont(23f));


        taskFinished.setForeground(ColorTheme.getTaskCompletedIconColor());
        this.add(taskname);
        this.add(taskUrgency);
        this.add(taskFinished);

        setUrgencyColor();
        setDifficultyColor();
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(borderColor, 2);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

        taskUrgency.setForeground(iconColor);
        taskname.setForeground(ColorTheme.getTaskTextColor());

        this.setBackground(backgroundcolor);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                PANEL_task.this.setBackground(ColorTheme.getTaskHoverColor());
                taskname.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 17));
            }
            public void mouseExited(MouseEvent e) {
                PANEL_task.this.setBackground(ColorTheme.getTaskColor());
                taskname.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN,15));
            }
        });
        if (task.isFinished()) {
            taskFinished.setVisible(true);
            taskUrgency.setVisible(false);

        }else{
            taskFinished.setVisible(false);
            taskUrgency.setVisible(true);
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eventHandler.setCurrentTask(currentTask);
                eventHandler.getPanelMainmenu().getPanel_taskinfo().addTaskInfo(currentTask);
                eventHandler.getPanelMainmenu().getPanel_clock().deactivate();
                eventHandler.getFileHandler().getNotesFromFile();
                eventHandler.getPanelList().loadCurrentTaskNotes();
            }
        });
    }


    public void setHEIGHTandWIDTH(int height, int width) {
        taskname.setBounds(0, 0, width, height);
        taskUrgency.setBounds(20, 0, width, height);
        taskFinished.setBounds(20,0, width, height);

    }

    private void setUrgencyColor() {
        if (currentTask.getUrgency() == 1) {
            iconColor = ColorTheme.getUrgency1();
        }
        if (currentTask.getUrgency() == 2) {
            iconColor = ColorTheme.getUrgency2();
        }
        if (currentTask.getUrgency() == 3) {
            iconColor = ColorTheme.getUrgency3();
        }
        if (currentTask.getUrgency() == 4) {
            iconColor = ColorTheme.getUrgency4();
        }
        if (currentTask.getUrgency() == 5) {
            iconColor = ColorTheme.getUrgency5();

        }
    }

    private void setDifficultyColor() {
        if (currentTask.getDifficulty() == 1) {
            borderColor = ColorTheme.getDifficulty1();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getDifficulty() == 2) {
            borderColor = ColorTheme.getDifficulty2();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getDifficulty() == 3) {
            borderColor = ColorTheme.getDifficulty3();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getDifficulty() == 4) {
            borderColor = ColorTheme.getDifficulty4();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getDifficulty() == 5) {
            borderColor = ColorTheme.getDifficulty5();
            backgroundcolor = ColorTheme.getTaskColor();

        }
    }
}
