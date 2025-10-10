package UserInterface.PanelListElements;

import Handlers.EventHandler;
import Loaders.FontLoader;
import AppLogic.Task;
import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PANEL_task extends JPanel implements ThemeChangeListener {
    private final JLabel taskname = new JLabel("Task Name:");
    private final JLabel taskUrgency = new JLabel("\uEEBF");
    private final JLabel taskFinished = new JLabel("\uDB81\uDF91");
    private final JLabel taskUrgent= new JLabel("\uDB81\uDEBD");

    private final Task currentTask;


    private final int WIDTH = 250;
    private final int HEIGHT = 30;



    public PANEL_task(Task task) {
        currentTask = task;
        ThemeLoader.addThemeChangeListener(this);
        this.setLayout(null);
        taskname.setText(task.getName());
        taskname.setBounds(0, 0, 100, 50);
        taskname.setHorizontalAlignment(JLabel.CENTER);
        taskname.setFont(FontLoader.getCozyFont().deriveFont(15f));
        taskUrgency.setFont(FontLoader.getCozyFont().deriveFont(17f));
        taskFinished.setFont(FontLoader.getCozyFont().deriveFont(23f));
        taskUrgent.setFont(FontLoader.getCozyFont().deriveFont(25f));
        taskUrgent.setVisible(false);


        taskFinished.setForeground(ThemeLoader.getColor(ThemeColorKey.TASK_COMPLETED_ICON));
        this.add(taskname);
        this.add(taskUrgency);
        this.add(taskFinished);
        this.add(taskUrgent);

        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getDifficultyColor(currentTask.getDifficulty()), 2);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

        taskUrgency.setForeground(ThemeLoader.getUrgencyColor(currentTask.getUrgency()));
        taskname.setForeground(ThemeLoader.getColor(ThemeColorKey.TASK_TEXT_COLOR));

        this.setBackground(ThemeLoader.getColor(ThemeColorKey.TASK_COLOR));
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
            taskUrgent.setForeground(taskDeadline.isBefore(today)?ThemeLoader.getColor(ThemeColorKey.TASK_URGENT_PASSED): ThemeLoader.getColor(ThemeColorKey.TASK_URGENT_ICON));
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
                eventHandler.getPanelList().loadCurrentTaskNotes();
                eventHandler.getPanelMainmenu().getPanel_help().setVisible(false);
                System.out.println("current archive "+eventHandler.getCurrentArchive());
            }
            public void mouseEntered(MouseEvent e) {
                PANEL_task.this.setBackground(ThemeLoader.getColor(ThemeColorKey.TASK_HOVER_COLOR));
                taskname.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 17));
            }
            public void mouseExited(MouseEvent e) {
                PANEL_task.this.setBackground(ThemeLoader.getColor(ThemeColorKey.TASK_COLOR));
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

    @Override
    public void onThemeChanged() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today= LocalDate.now();
        LocalDate taskDeadline = LocalDate.parse(currentTask.getDeadline(), formatter);
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.TASK_COLOR));
        taskUrgency.setForeground(ThemeLoader.getUrgencyColor(currentTask.getUrgency()));
        taskname.setForeground(ThemeLoader.getColor(ThemeColorKey.TASK_TEXT_COLOR));
        taskUrgent.setForeground(taskDeadline.isBefore(today)?ThemeLoader.getColor(ThemeColorKey.TASK_URGENT_PASSED): ThemeLoader.getColor(ThemeColorKey.TASK_URGENT_ICON));
    }
}
