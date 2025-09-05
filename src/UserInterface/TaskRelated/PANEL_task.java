package UserInterface.TaskRelated;

import AppLogic.TaskLogic.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PANEL_task extends JPanel {
    private int HEIGHT;
    private int WIDTH;
    private JLabel taskname = new JLabel("Task Name:");

    public PANEL_task(Task task) {

        this.setLayout(null);
        taskname.setText(task.getName());
        taskname.setBounds(0, 0,100,50);
        this.add(taskname);


        this.setBackground(Color.green);
    }
}
