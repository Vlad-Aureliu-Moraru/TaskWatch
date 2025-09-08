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
    private EventHandler eventHandler;
    private Task currentTask;


    private int WIDTH =250;
    private int HEIGHT = 30;

    private Color bordercolor;
    private Color backgroundcolor;

    public PANEL_task(Task task) {
        currentTask = task;
        this.setLayout(null);
        taskname.setText("\uF4A0  "+task.getName());
        taskname.setBounds(0, 0,100,50);
        taskname.setHorizontalAlignment(JLabel.CENTER);
        taskname.setFont(new Font("ARIAL",Font.PLAIN,15));

        this.add(taskname);
        setUrgencyColor();
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(bordercolor, 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

        taskname.setForeground(ColorTheme.getTaskTextColor());

        this.setBackground(backgroundcolor);
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


    public void setHEIGHTandWIDTH(int height, int width){
        taskname.setBounds(0,0,width,height);

    }

    private void setUrgencyColor() {
        if (currentTask.getUrgency() == 1) {
            bordercolor = ColorTheme.getUrgency1();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getUrgency() == 2) {
            bordercolor = ColorTheme.getUrgency2();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getUrgency() == 3) {
            bordercolor = ColorTheme.getUrgency3();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getUrgency() == 4) {
            bordercolor = ColorTheme.getUrgency4();
            backgroundcolor = ColorTheme.getTaskColor();
        }
        if (currentTask.getUrgency() == 5) {
            bordercolor = ColorTheme.getUrgency5();
            backgroundcolor = ColorTheme.getTaskColor();

        }
    }
}
