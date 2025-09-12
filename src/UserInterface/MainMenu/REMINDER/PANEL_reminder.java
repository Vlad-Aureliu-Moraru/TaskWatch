package UserInterface.MainMenu.REMINDER;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PANEL_reminder extends JPanel {
    private EventHandler eventHandler;

    private PANEL_todaylist panel_todaylist = new PANEL_todaylist();
    private PANEL_thisweek panel_thisweek = new PANEL_thisweek();


    public PANEL_reminder() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        this.add(panel_todaylist);
        this.add(panel_thisweek);
    }

    public void setHEIGHTandWIDTH(int height,int width){
        panel_todaylist.setBounds(0,0,width/2+100,height);
        panel_thisweek.setBounds(width/2+100,0,width-(width/2+100),height);

        panel_todaylist.setHEIGHTandWIDTH(height,width/2+100);
        panel_thisweek.setHEIGHTandWIDTH(height,width-(width/2+100));
    }

    public void loadReminder(){
        panel_todaylist.clearItems();
        panel_thisweek.clearItems();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today= LocalDate.now();
        for (Directory dir : eventHandler.getDirectoryList()){
            for (Task task : dir.getTasks()){
                if (!task.isFinished() && !task.getDeadline().equals("none")){
                    LocalDate taskDeadline = LocalDate.parse(task.getDeadline(), formatter);
                    if (taskDeadline.isEqual(today)){
                        panel_todaylist.addItem(task);
                    } else if
                    (taskDeadline.isAfter(today) && taskDeadline.isBefore(today.plusWeeks(1))) {
                        panel_thisweek.addItem(task);
                    }
                }
            }
        }
        panel_todaylist.loadItems();
        panel_thisweek.loadItems();
        this.repaint();
        this.revalidate();
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        loadReminder();
    }
}
