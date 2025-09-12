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
    setDifficultyColor();
    this.setBackground(backgroundColor);
}
    public void setHEIGHTandWIDTH(int height,int width){
        titleLabel.setBounds(5,5,width,20);
    }

    private void setDifficultyColor() {
        if (currentTask.getDifficulty() == 1) {
            backgroundColor= ColorTheme.getDifficulty1();
        }
        if (currentTask.getDifficulty() == 2) {
            backgroundColor = ColorTheme.getDifficulty2();
        }
        if (currentTask.getDifficulty() == 3) {
            backgroundColor = ColorTheme.getDifficulty3();
        }
        if (currentTask.getDifficulty() == 4) {
            backgroundColor = ColorTheme.getDifficulty4();
        }
        if (currentTask.getDifficulty() == 5) {
            backgroundColor = ColorTheme.getDifficulty5();

        }
    }
}
