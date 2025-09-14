package UserInterface.MainMenu.SubPanels;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.FontLoader;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PANEL_clock extends JPanel {
    private static final int[][] WORK_PERCENTAGE_MATRIX = {
            // U=1  U=2  U=3  U=4  U=5
            { 68,  61,  74,  77,  90 }, // D=1
            { 63,  56,  69,  72,  85 }, // D=2
            { 58,  52,  66,  67,  80 }, // D=3
            { 53,  49,  54,  62,  75 }, // D=4
            { 46,  48,  49,  57,  70 }  // D=5
    };

    private final DateTimeFormatter dtf ;
    private final Timer clockTimer;
    private final Timer taskTimer;
    private final JLabel timeDisplay = new JLabel() ;
    private int totalSeconds = 1;
    private int originalSeconds = 1;

    private final int clockUpdateInSec = 5;
    private int taskUpdateInSec = 1;

    private int workingSeconds = 1;
    private int breakSeconds = 1;

    private ArrayList<Integer> schedule;
    private int CurrentSchedule;
    private int CurrentScheduleIndex = 0;

    private Task currentTask;
    private Directory directory;

    private EventHandler eventHandler;


    public PANEL_clock() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        clockTimer = new Timer(clockUpdateInSec*1000, _ -> updateTime());
        taskTimer = new Timer(taskUpdateInSec*1000, _ -> TimerLogicUpdate());

        timeDisplay.setFont(FontLoader.getTerminalFont().deriveFont(70f));
        timeDisplay.setHorizontalAlignment(JLabel.CENTER);
        timeDisplay.setVerticalAlignment(JLabel.CENTER);
        timeDisplay.setForeground(ColorTheme.getSecnd_accent());
        this.add(timeDisplay);

    }
    public void TimerLogicUpdate(){
        CurrentSchedule-=taskUpdateInSec;
        updateTimeForTaskTimer();
        if(CurrentSchedule<=0){
            System.out.println(CurrentScheduleIndex);
            if (CurrentScheduleIndex>=schedule.size()-1){
            if (currentTask!=null){
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime = now.format(formatter);
                currentTask.setFinished(true);
                currentTask.setFinishedDate(formattedDateTime);
                eventHandler.updateDeadlineForRepeatableTasks(currentTask);
                eventHandler.getFileHandler().saveTaskToFile(directory);
                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                if (eventHandler.getPanelList().getStage()== 1 ){
                    eventHandler.getPanelList().loadCurrentTasks();
                }
            }
            stopTaskTimerandStartClockTimer();
            System.out.println("Task Timer Stopped");
            }else{
                CurrentScheduleIndex++;
                CurrentSchedule=schedule.get(CurrentScheduleIndex);
                boolean isWorkingPhase = CurrentScheduleIndex % 2 == 1;
                timeDisplay.setForeground(isWorkingPhase ?ColorTheme.getSecnd_accent():ColorTheme.getTimerOnBreakColor());
                if (isWorkingPhase){
                    eventHandler.getPanelnavbar().setTimerWorkingStatus();
                }else{
                    eventHandler.getPanelnavbar().setBreakStatus();
                }
            }
        }

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
        if (taskTimer.isRunning()){
            clockTimer.stop();
        }else{
            clockTimer.start();
        }
        this.setVisible(true);
    }
    public void deactivate(){
        this.setVisible(false);
        clockTimer.stop();

    }
    public void startClockTimer(){
        if (!clockTimer.isRunning()){
            clockTimer.start();
            eventHandler.getPanelnavbar().setClockWorkingStatus();
            updateTime();
            timeDisplay.setForeground(ColorTheme.getSecnd_accent());
        }
    }
    public void startTaskTimer(int minutes){
        totalSeconds = minutes*60;
        originalSeconds = totalSeconds;
        CurrentSchedule = totalSeconds;
        clockTimer.stop();
        eventHandler.getPanelnavbar().setTimerWorkingStatus();
        if (taskTimer != null && taskTimer.isRunning()) {
            taskTimer.stop();
            System.out.println("Task Timer Stopped");
        }
        assert taskTimer != null;
        taskTimer.start();
        updateTimeForTaskTimer();
    }
    public void startTaskTimer(Task currentTask, Directory directory){
        this.currentTask = currentTask;
        this.directory = directory;

        clockTimer.stop();
        eventHandler.getPanelnavbar().setTimerWorkingStatus();
        findPOMOValues(currentTask);
        eventHandler.getPanelnavbar().setPreparingStatus();
        timeDisplay.setForeground(ColorTheme.getTimerOnPrepColor());
        CurrentSchedule = schedule.get(CurrentScheduleIndex);
        if (taskTimer != null && taskTimer.isRunning()) {
            taskTimer.stop();
            System.out.println("Task Timer Stopped");
        }
        assert taskTimer != null;
        taskTimer.start();
        updateTimeForTaskTimer();
    }
    private void updateTimeForTaskTimer() {
        int hours = CurrentSchedule/ 3600;
        int minutes = (CurrentSchedule% 3600) / 60;
        int seconds = CurrentSchedule % 60;
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
        CurrentScheduleIndex = 0;

        int determinatedWorkProcentage = WORK_PERCENTAGE_MATRIX[difficulty - 1][urgency - 1];
        double workPercentAsDouble = determinatedWorkProcentage / 100.0;
        workingSeconds = (int) Math.round(totalSeconds * workPercentAsDouble);
        breakSeconds = totalSeconds - workingSeconds;

        // Step 2: Calculate max work session length (30 - 5 * D) minutes

        schedule.add(15); //start with a preparing sesh
        breakSeconds-=15;
        for (int i = 0 ; i < difficulty; i++){
            schedule.add(workingSeconds/difficulty);
            schedule.add(breakSeconds/difficulty);

        }
        System.out.println("Breaks will be : "+ breakSeconds/difficulty);
        System.out.println("Work time will be : "+ workingSeconds/difficulty);

        displayAllocation();
    }
    public void displayAllocation() {
        double workMinutes = workingSeconds/ 60.0;
        double breakMinutes = breakSeconds/ 60.0;

        System.out.println("--- Time Allocation Plan ---");
        System.out.println(".................................");
        System.out.println("Resulting Time Allocation:");
        System.out.printf("   - Total Productive Time: %.1f minutes \n", workMinutes);
        System.out.printf("   - Total Break Time: %.1f minutes \n", breakMinutes);
        System.out.println("---------------------------------");
        for (int val : schedule){
            System.out.println(val);
        }
    }
}