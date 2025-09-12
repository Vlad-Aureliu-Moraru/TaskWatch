package UserInterface.MainMenu.REMINDER;

import AppLogic.FontLoader;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_item extends JPanel {
private Task currentTask;
private Color backgroundColor;
private JLabel titleLabel =  new JLabel();

public  PANEL_item(Task task){
    this.currentTask = task;
    this.setLayout(null);
    titleLabel.setText(task.getName());
    titleLabel.setVerticalAlignment(JLabel.CENTER);
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(FontLoader.getTerminalFont().deriveFont(Font.PLAIN, 15));

    add(titleLabel);
    setUrgencyColor();
    this.setBackground(backgroundColor);
}
    public void setHEIGHTandWIDTH(int height,int width){
        titleLabel.setBounds(5,5,width,20);
    }

    private void setUrgencyColor() {
        if (currentTask.getUrgency() == 1) {
            backgroundColor= ColorTheme.getUrgency1List();
        }
        if (currentTask.getUrgency() == 2) {
            backgroundColor = ColorTheme.getUrgency2List();
        }
        if (currentTask.getUrgency() == 3) {
            backgroundColor = ColorTheme.getUrgency3List();
        }
        if (currentTask.getUrgency() == 4) {
            backgroundColor = ColorTheme.getUrgency4List();
        }
        if (currentTask.getUrgency() == 5) {
            backgroundColor = ColorTheme.getUrgency5List();

        }
    }
}
