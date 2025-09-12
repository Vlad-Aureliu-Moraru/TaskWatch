package UserInterface.MainMenu.SubPanels;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.FontLoader;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PANEL_clock extends JPanel {
    private static final int[][] WORK_PERCENTAGE_MATRIX = {
            // U=1  U=2  U=3  U=4  U=5
            { 38,  51,  64,  77,  90 }, // D=1
            { 33,  46,  59,  72,  85 }, // D=2
            { 28,  41,  54,  67,  80 }, // D=3
            { 23,  36,  49,  62,  75 }, // D=4
            { 18,  31,  44,  57,  70 }  // D=5
    };

    private int determinatedWorkProcentage;

    private DateTimeFormatter dtf ;
    private Timer clockTimer;
    private Timer taskTimer;
    private JLabel timeDisplay = new JLabel() ;
    private boolean active = true;
    private int totalSeconds = 1;
    private int originalSeconds = 1;

    private int clockUpdateInSec = 5;
    private int taskUpdateInSec = 5;

    private boolean working = false;
    private int workingSeconds = 1;
    private int breakSeconds = 1;
    private ArrayList schedule;

    private Task currentTask;
    private Directory directory;

    private EventHandler eventHandler;


    public PANEL_clock() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        clockTimer = new Timer(clockUpdateInSec*1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        taskTimer = new Timer(taskUpdateInSec*1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                totalSeconds-=taskUpdateInSec;
                updateTimeForTaskTimer();
                if(totalSeconds<=1){
                    if (currentTask!=null){
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDateTime = now.format(formatter);
                        currentTask.setFinished(true);
                        currentTask.setFinishedDate(formattedDateTime);
                        eventHandler.updateDeadlineForRepeatableTasks(currentTask);
                        eventHandler.getFileHandler().saveTaskToFile(directory);
                    }
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
    public void startTaskTimer(Task currentTask, Directory directory){
        this.currentTask = currentTask;
        this.directory = directory;

        clockTimer.stop();
        eventHandler.getPanelnavbar().setTimerWorkingStatus();
        findPOMOValues(currentTask);
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
    }

    public void findPOMOValues(Task currentTask) {
        int urgency = currentTask.getUrgency();
        int difficulty = currentTask.getDifficulty();
        totalSeconds = currentTask.getTimeDedicated() * 60;
        originalSeconds = totalSeconds;
        schedule = new ArrayList<>();

        determinatedWorkProcentage = WORK_PERCENTAGE_MATRIX[difficulty - 1][urgency - 1];
        double workPercentAsDouble = determinatedWorkProcentage / 100.0;
        workingSeconds = (int) Math.round(totalSeconds * workPercentAsDouble);
        breakSeconds = totalSeconds - workingSeconds;

        // Step 2: Calculate max work session length (30 - 5 * D) minutes
        int maxWorkSessionMinutes = 30 - 5 * difficulty;
        int maxWorkSessionSeconds = maxWorkSessionMinutes * 60;

        // Step 3: Determine number of work sessions
        int numWorkSessions = (int) Math.ceil((double) workingSeconds / maxWorkSessionSeconds);

        // Step 4: Calculate work and break durations per session
        if (numWorkSessions == 1) {
            // Single work session followed by break
            schedule.add(workingSeconds);
            if (breakSeconds > 0) {
                schedule.add(breakSeconds);
            }
        } else {
            // Multiple work sessions with interspersed breaks
            int workSessionSeconds = workingSeconds / numWorkSessions;
            int interBreakSeconds = (numWorkSessions > 1) ? breakSeconds / (numWorkSessions - 1) : 0;

            // Build schedule: alternate work and break, end with work
            for (int i = 0; i < numWorkSessions - 1; i++) {
                schedule.add(workSessionSeconds);
                schedule.add(interBreakSeconds);
            }
            schedule.add(workSessionSeconds); // Final work session
        }
        displayAllocation();
    }
    public void displayAllocation() {
        double totalMinutes = totalSeconds/ 60.0;
        double workMinutes = workingSeconds/ 60.0;
        double breakMinutes = breakSeconds/ 60.0;

        System.out.println("--- Time Allocation Plan ---");
        System.out.println(".................................");
        System.out.println("Resulting Time Allocation:");
        System.out.printf("   - Total Productive Time: %.1f minutes \n", workMinutes);
        System.out.printf("   - Total Break Time: %.1f minutes \n", breakMinutes);
        System.out.println("---------------------------------");
    }
}