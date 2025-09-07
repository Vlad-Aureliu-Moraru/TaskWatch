package UserInterface.MainMenu.SubPanels;

import AppLogic.FontLoader;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PANEL_clock extends JPanel {
    private Timer pomodoroTimer;
    private DateTimeFormatter dtf ;
    private Timer clockTimer;
    private Timer taskTimer;
    private JLabel timeDisplay = new JLabel() ;
    private boolean active = true;
    private int totalSeconds;


    public PANEL_clock(){
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        clockTimer = new Timer(10000,new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               updateTime();
           }
        });
        taskTimer = new Timer(5000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                totalSeconds-=5;
                updateTimeForTaskTimer();
                if(totalSeconds<=0){
                    taskTimer.stop();
                    System.out.println("Task Timer Stopped");
                }
            }
        });
        clockTimer.start();
        updateTime();

        timeDisplay.setFont(FontLoader.getFont().deriveFont(70f));
        timeDisplay.setHorizontalAlignment(JLabel.CENTER);
        timeDisplay.setVerticalAlignment(JLabel.CENTER);
        timeDisplay.setForeground(ColorTheme.getSecnd_accent());
        this.add(timeDisplay);

    }
    public void setHEIGHTandWIDTH(int height, int width){
        timeDisplay.setBounds(0,0,width,height);
        if (width<400){
            timeDisplay.setFont(FontLoader.getFont().deriveFont(50f));
        } else{
            timeDisplay.setFont(FontLoader.getFont().deriveFont(70f));
        }
    }
    private void updateTime(){
        LocalTime now = LocalTime.now();
        String time = now.format(dtf);
        timeDisplay.setText(time);
    }
    public void activate(){
        active = true;
        clockTimer.start();
        this.setVisible(true);
    }
    public void Switch(){
        if (active){
            active = false;
            clockTimer.stop();
            this.setVisible(false);
        }
        else{
            active = true;
            clockTimer.start();
            this.setVisible(true);
        }
    }
    public void deactivate(){
        active=false;
        this.setVisible(false);
        clockTimer.stop();
    }
    public void startTimer(int minutes){
        totalSeconds = minutes*60;
        clockTimer.stop();
        if (taskTimer != null && taskTimer.isRunning()) {
            taskTimer.stop();
            System.out.println("Task Timer Stopped");
        }
        taskTimer.start();
        updateTimeForTaskTimer();
    }
    private void updateTimeForTaskTimer() {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeDisplay.setText(timeString);
    }
}
