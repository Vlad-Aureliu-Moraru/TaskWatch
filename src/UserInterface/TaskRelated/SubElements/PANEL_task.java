package UserInterface.TaskRelated.SubElements;

import AppLogic.EventHandler;
import AppLogic.TaskLogic.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_task extends JPanel {
    private int HEIGHT;
    private int WIDTH;
    private JLabel taskname = new JLabel("Task Name:");
    private EventHandler eventHandler;
    private Task currentTask;
    public PANEL_task(Task task) {
        currentTask = task;
        this.setLayout(null);
        taskname.setText(task.getName());
        taskname.setBounds(0, 0,100,50);
        this.add(taskname);

        this.setBackground(Color.green);
    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eventHandler.setCurrentTask(currentTask);
                System.out.println(eventHandler.getCurrentTask().getNotes().size());
            }
        });
    }
}
