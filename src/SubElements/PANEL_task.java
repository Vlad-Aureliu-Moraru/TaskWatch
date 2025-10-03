package SubElements;

import AppLogic.EventHandler;
import AppLogic.FontLoader;
import AppLogic.Task;
import ConfigRelated.ThemeLoader;
import UserInterface.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PANEL_task extends JPanel {
    private final JLabel taskname = new JLabel("Task Name:");
    private final JLabel taskUrgency = new JLabel("\uEEBF");
    private final JLabel taskFinished = new JLabel("\uDB81\uDF91");
    private final JLabel taskUrgent= new JLabel("\uDB81\uDEBD");

    private final Task currentTask;


    private final int WIDTH = 250;
    private final int HEIGHT = 30;



    public PANEL_task(Task task) {
        currentTask = task;
        this.setLayout(null);
        taskname.setText(task.getName());
        taskname.setBounds(0, 0, 100, 50);
        taskname.setHorizontalAlignment(JLabel.CENTER);
        taskname.setFont(FontLoader.getCozyFont().deriveFont(15f));
        taskUrgency.setFont(FontLoader.getCozyFont().deriveFont(17f));
        taskFinished.setFont(FontLoader.getCozyFont().deriveFont(23f));
        taskUrgent.setFont(FontLoader.getCozyFont().deriveFont(25f));
        taskUrgent.setVisible(false);


        taskFinished.setForeground(ThemeLoader.getTaskCompletedIconColor());
        this.add(taskname);
        this.add(taskUrgency);
        this.add(taskFinished);
        this.add(taskUrgent);

        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getDifficulty(currentTask.getDifficulty()), 2);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

        taskUrgency.setForeground(ThemeLoader.getUrgency(currentTask.getUrgency()));
        taskname.setForeground(ThemeLoader.getTaskTextColor());

        this.setBackground(ThemeLoader.getTaskColor());
        if (task.isFinished()) {
            taskFinished.setVisible(true);
            taskUrgency.setVisible(false);

        }else{
            taskFinished.setVisible(false);
            taskUrgency.setVisible(true);
        }
        if (!task.isFinished() && !task.getDeadline().equals("none")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today= LocalDate.now();
        LocalDate taskDeadline = LocalDate.parse(task.getDeadline(), formatter);
        if (taskDeadline.isEqual(today) || taskDeadline.isBefore(today) ) {
            taskUrgency.setVisible(false);
            taskUrgent.setForeground(taskDeadline.isBefore(today)?ThemeLoader.getTaskUrgentPassed(): ThemeLoader.getTaskUrgentIconColor());
            taskUrgent.setVisible(true);

        }else{
            taskFinished.setVisible(false);
            taskUrgent.setVisible(false);
            taskUrgency.setVisible(true);
        }
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eventHandler.setCurrentTask(currentTask);
                eventHandler.getPanelMainmenu().getPanel_taskinfo().addTaskInfo(currentTask);
                eventHandler.getPanelMainmenu().getPanel_clock().deactivate();
                eventHandler.getFileHandler().getNotesFromFile();
                eventHandler.getPanelList().loadCurrentTaskNotes();
                eventHandler.getPanelMainmenu().getPanel_help().setVisible(false);
            }
            public void mouseEntered(MouseEvent e) {
                PANEL_task.this.setBackground(ThemeLoader.getTaskHoverColor());
                taskname.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 17));
            }
            public void mouseExited(MouseEvent e) {
                PANEL_task.this.setBackground(ThemeLoader.getTaskColor());
                taskname.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN,15));
            }
        });
    }


    public void setHEIGHTandWIDTH(int height, int width) {
        taskname.setBounds(0, 0, width, height);
        taskUrgency.setBounds(20, 0, width, height);
        taskFinished.setBounds(20,0, width, height);
        taskUrgent.setBounds(20,0, width, height);

    }

}
