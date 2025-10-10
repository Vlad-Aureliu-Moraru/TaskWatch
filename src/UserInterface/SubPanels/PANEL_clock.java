package UserInterface.SubPanels;

import Loaders.*;
import AppLogic.Directory;
import Handlers.EventHandler;
import AppLogic.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static UserInterface.PanelListElements.ListStages.DIRECTORY_MENU;

public class PANEL_clock extends JPanel implements ThemeChangeListener {
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
    private int totalSeconds = 0;
    private int originalSeconds = 0;

    private final int clockUpdateInSec = ConfigLoader.getClockUpdateTime();
    private int taskUpdateInSec = 1;

    private int workingSeconds = 1;
    private int breakSeconds = 1;

    private ArrayList<Integer> schedule = new ArrayList<>();
    private int CurrentSchedule;
    private int CurrentScheduleIndex = 0;

    private Task currentTask;
    private Directory directory;

    private EventHandler eventHandler;

    private final JLabel  scheduleIndex = new JLabel("") ;
    private final JLabel  taskInfo = new JLabel("") ;
    private final JLabel  scheduleInfo = new JLabel("") ;

    private Color backgroundColor;


    public PANEL_clock() {
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_CLOCK));
        ThemeLoader.addThemeChangeListener(this);
        this.setLayout(null);
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        clockTimer = new Timer(clockUpdateInSec*1000, e -> updateTime());
        taskTimer = new Timer(taskUpdateInSec*1000, e -> TimerLogicUpdate());

        timeDisplay.setFont(FontLoader.getTerminalFont().deriveFont(70f));
        timeDisplay.setHorizontalAlignment(JLabel.CENTER);
        timeDisplay.setVerticalAlignment(JLabel.CENTER);
        timeDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        this.add(timeDisplay);
        scheduleIndex.setBounds(0,0, 80, 20);
        scheduleIndex.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        this.add(scheduleIndex);
        taskInfo.setBounds(40,0, 80, 20);
        taskInfo.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        this.add(taskInfo);
        scheduleInfo.setBounds(0,15, 280, 20);
        scheduleInfo.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        this.add(scheduleInfo);

    }
    public void TimerLogicUpdate(){
        CurrentSchedule-=taskUpdateInSec;
        scheduleIndex.setText(""+CurrentScheduleIndex+"/"+schedule.size());
        totalSeconds-=taskUpdateInSec;
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
                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                if (eventHandler.getPanelList().getStage()== DIRECTORY_MENU ){
                    eventHandler.getPanelList().loadCurrentTasks(null);
                }
            }
            stopTaskTimerandStartClockTimer();
            System.out.println("Task Timer Stopped");
            }else{
                CurrentScheduleIndex++;
                CurrentSchedule=schedule.get(CurrentScheduleIndex);
                boolean isWorkingPhase = CurrentScheduleIndex % 2 == 1;
                timeDisplay.setForeground(isWorkingPhase ?ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT): ThemeLoader.getColor(ThemeColorKey.TIMER_ON_BREAK_COLOR));
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
        if (taskTimer.isRunning() || totalSeconds!=0){
            System.out.println("Clock Timer Stopped");
            clockTimer.stop();
        }else{
            System.out.println("Clock Timer works");
            clockTimer.start();
        }
        this.setVisible(true);
    }
    public void switchVisible(){
        this.setVisible(!this.isVisible());
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
            timeDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        }
    }
    public void startTaskTimer(int minutes){
        totalSeconds = minutes*60;
        originalSeconds = totalSeconds;
        CurrentSchedule = totalSeconds;
        schedule.add(CurrentSchedule);
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
        taskInfo.setText(directory.getName()+"/"+currentTask.getName());
        this.currentTask = currentTask;
        this.directory = directory;

        clockTimer.stop();
        eventHandler.getPanelnavbar().setTimerWorkingStatus();
        findPOMOValues(currentTask);
        scheduleIndex.setText(""+CurrentScheduleIndex+"/"+schedule.size());
        eventHandler.getPanelnavbar().setPreparingStatus();
        timeDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.TIMER_ON_PREP_COLOR));
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
        scheduleIndex.setText("");
        scheduleInfo.setText("");
        taskInfo.setText("");
        totalSeconds = 0;
        originalSeconds = 0;
        currentTask = null;
        schedule = new ArrayList<>();
        startClockTimer();
    }
    public void pauseOrunpauseTaskTimer(){
        if (taskTimer.isRunning()){
            System.out.println("pausing");
            eventHandler.getPanelnavbar().setTimerPausedStatus();
            taskTimer.stop();
        }else if (totalSeconds!=0)  {
            System.out.println("unpausing");
            taskTimer.start();
            eventHandler.getPanelnavbar().setTimerWorkingStatus();
        }
        System.out.println(totalSeconds + " " +  currentTask + " " + schedule );
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
        int workMinutes = workingSeconds/ 60;
        int breakMinutes = breakSeconds/ 60;
        scheduleInfo.setText("work/break :"+workMinutes+"min /"+breakMinutes+"min");

        System.out.println("--- Time Allocation Plan ---");
        System.out.println(".................................");
        System.out.println("Resulting Time Allocation:");
        System.out.println("---------------------------------");
        for (int val : schedule){
            System.out.println(val);
        }
    }

    @Override
    public void onThemeChanged() {
        // Automatically apply theme colors to this panel and its labels
//        ThemeLoader.applyTheme(this, timeDisplay, scheduleIndex, taskInfo, scheduleInfo);

        // Adjust the timeDisplay color if a task timer is running
        if (taskTimer.isRunning() && currentTask != null) {
            if (CurrentScheduleIndex == 0) {
                timeDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.TIMER_ON_PREP_COLOR));
            } else if (CurrentScheduleIndex % 2 == 1) {
                timeDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
            } else {
                timeDisplay.setForeground(ThemeLoader.getColor(ThemeColorKey.TIMER_ON_BREAK_COLOR));
            }
        }

        // Refresh visuals
        repaint();
        revalidate();
    }


}
