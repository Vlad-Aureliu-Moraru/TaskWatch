package UserInterface.MainMenu.SubPanels;

import AppLogic.EventHandler;
import AppLogic.FontLoader;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PANEL_clock extends JPanel {
    private Timer pomodoroTimer;
    private DateTimeFormatter dtf ;
    private Timer clockTimer;
    private Timer taskTimer;
    private JLabel timeDisplay = new JLabel() ;
    private boolean active = true;
    private int totalSeconds = 1;
    private int originalSeconds = 1;

    private int clockUpdateInSec = 5;
    private int taskUpdateInSec = 5;

    private EventHandler eventHandler;


    public PANEL_clock() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        clockTimer = new Timer(clockUpdateInSec*1000,new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               System.out.println("clock working");
               updateTime();
           }
        });
        taskTimer = new Timer(taskUpdateInSec*1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("task working");
                totalSeconds-=taskUpdateInSec;
                updateTimeForTaskTimer();
                if(totalSeconds<=1){
                    stopTaskTimerandStartClockTimer();
                    System.out.println("Task Timer Stopped");
                }
            }
        });

        timeDisplay.setFont(FontLoader.getTerminalFont().deriveFont(70f));
        timeDisplay.setHorizontalAlignment(JLabel.CENTER);
        timeDisplay.setVerticalAlignment(JLabel.CENTER);
        timeDisplay.setForeground(ColorTheme.getSecnd_accent());
        this.add(timeDisplay);

    }
    public void setHEIGHTandWIDTH(int height, int width){
        timeDisplay.setBounds(0,0,width,height);
        if (width<400){
            timeDisplay.setFont(FontLoader.getTerminalFont().deriveFont(40f));
        } else if (width<700){
            timeDisplay.setFont(FontLoader.getTerminalFont().deriveFont(70f));
        }else{
            timeDisplay.setFont(FontLoader.getTerminalFont().deriveFont(130f));
        }
    }
    private void updateTime(){
        LocalTime now = LocalTime.now();
        String time = now.format(dtf);
        timeDisplay.setText(time);
    }
    public void activate(){
        active = true;
        if (taskTimer.isRunning()){
            clockTimer.stop();
        }else{
            clockTimer.start();
        }
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
    public void startClockTimer(){
        if (!clockTimer.isRunning()){
            clockTimer.start();
            eventHandler.getPanelnavbar().setClockWorkingStatus();
            updateTime();
        }
    }
    public void startTaskTimer(int minutes){
        totalSeconds = minutes*60;
        originalSeconds = totalSeconds;
        clockTimer.stop();
        eventHandler.getPanelnavbar().setTimerWorkingStatus();
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
    public void stopTaskTimerandStartClockTimer(){
        taskTimer.stop();
        startClockTimer();
    }
    public void pauseOrunpauseTaskTimer(){
        if (taskTimer.isRunning()){
            eventHandler.getPanelnavbar().setTimerPausedStatus();
            taskTimer.stop();
        }else {
            taskTimer.start();
            eventHandler.getPanelnavbar().setTimerWorkingStatus();
        }
    }
    public void resetTimer(){
        totalSeconds = originalSeconds;
        updateTimeForTaskTimer();
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        System.out.println(eventHandler.getPanelnavbar()+" from child");
    }
}
