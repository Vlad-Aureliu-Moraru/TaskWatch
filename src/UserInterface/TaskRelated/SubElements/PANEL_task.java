package UserInterface.TaskRelated.SubElements;

import AppLogic.EventHandler;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_task extends JPanel {
    private int HEIGHT;
    private int WIDTH;
    private JLabel taskname = new JLabel("Task Name:");
    private EventHandler eventHandler;
    private Task currentTask;

    private Color bordercolor;

    public PANEL_task(Task task) {
        currentTask = task;
        this.setLayout(null);
        taskname.setText(task.getName());
        taskname.setBounds(0, 0,100,50);
        this.add(taskname);
        setUrgencyColor();
        this.setBackground(ColorTheme.getAccent_green());
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
                System.out.println(eventHandler.getCurrentTask().getNotes().size());
            }
        });
    }
    private void setUrgencyColor() {
        if (currentTask.getUrgency() == 1) {
            Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getUrgency1(), 3);
            Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
            this.setBorder(compoundBorder);
        }
        if (currentTask.getUrgency() == 2) {
            Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getUrgency2(), 3);
            Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
            this.setBorder(compoundBorder);
        }
        if (currentTask.getUrgency() == 3) {
            Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getUrgency3(), 3);
            Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
            this.setBorder(compoundBorder);
        }
        if (currentTask.getUrgency() == 4) {
            Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getUrgency4(), 3);
            Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
            this.setBorder(compoundBorder);
        }
        if (currentTask.getUrgency() == 5) {
            Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getUrgency5(), 3);
            Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
            this.setBorder(compoundBorder);
        }
    }
}
