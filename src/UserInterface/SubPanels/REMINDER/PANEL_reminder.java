package UserInterface.SubPanels.REMINDER;

import Directory.Model.Directory;
import Task.Model.Task;
import Logic.Loaders.*;
import Logic.Handlers.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class PANEL_reminder extends JPanel implements ThemeChangeListener {
    private EventHandler eventHandler;

    private PANEL_todaylist panel_todaylist = new PANEL_todaylist();
    private PANEL_thisweek panel_thisweek = new PANEL_thisweek();
    private PANEL_weeklyschedule panel_weeklyschedule = new PANEL_weeklyschedule();
    private boolean showingFocused = !ConfigLoader.isShowFocusedSchedule();

    private Color backgroundColor;

    public PANEL_reminder() {
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_REMINDER));
        ThemeLoader.addThemeChangeListener(this);
        this.setLayout(null);
        this.add(panel_todaylist);
        this.add(panel_thisweek);
        this.add(panel_weeklyschedule);
        if (ConfigLoader.isShowFocusedSchedule()){
        panel_todaylist.setVisible(true);
        panel_thisweek.setVisible(true);
        panel_weeklyschedule.setVisible(false);
        }else{
            panel_todaylist.setVisible(false);
            panel_thisweek.setVisible(false);
            panel_weeklyschedule.setVisible(true);

        }
    }

    public void setHEIGHTandWIDTH(int height, int width) {
        panel_weeklyschedule.setBounds(0, 0, width, height);
        panel_todaylist.setBounds(0, 0, width / 2 + 100, height);
        panel_thisweek.setBounds(width / 2 + 100, 0, width - (width / 2 + 100), height);

        panel_todaylist.setHEIGHTandWIDTH(height, width / 2 + 100);
        panel_thisweek.setHEIGHTandWIDTH(height, width - (width / 2 + 100));
        panel_weeklyschedule.setHEIGHTandWIDTH(height, width);
    }

    public void loadReminder() {
        if (eventHandler == null) return;

        panel_todaylist.clearItems();
        panel_thisweek.clearItems();
        panel_weeklyschedule.clearAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();

        // Load non-repeatable tasks for today and this week
        for (Task task : eventHandler.getAllTasks()) {
            if (!task.isFinished() && !task.getDeadline().equals("none")) {
                LocalDate taskDeadline = LocalDate.parse(task.getDeadline(), formatter);

                if (taskDeadline.isEqual(today)) {
                    panel_todaylist.addItem(eventHandler.getDirectoryRepository().getDirectoryById(task.getDirectoryId()), task);
                } else if (taskDeadline.isAfter(today) && taskDeadline.isBefore(today.plusWeeks(1))) {
                    panel_thisweek.addItem(eventHandler.getDirectoryRepository().getDirectoryById(task.getDirectoryId()), task);
                }
            }
        }

        // Load weekly schedule (including repeatables)
        loadWeeklyRepeatables();

        panel_todaylist.loadItems();
        panel_thisweek.loadItems();
        panel_weeklyschedule.loadItems();

        this.repaint();
        this.revalidate();
    }

    private void loadWeeklyRepeatables() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(java.time.temporal.WeekFields.of(java.util.Locale.getDefault()).dayOfWeek(), 1);
        LocalDate weekEnd = weekStart.plusDays(6);

        for (Task task : eventHandler.getAllTasks()) {
            if (task.isFinished() && task.isHasToBeCompletedToRepeat()) continue;

            Directory dir = eventHandler.getDirectoryRepository().getDirectoryById(task.getDirectoryId());
            String deadlineStr = task.getDeadline();
            if (deadlineStr.equals("none") || deadlineStr.isEmpty()) continue;

            LocalDate deadline = LocalDate.parse(deadlineStr, formatter);
            String repeatType = task.getRepeatableType().toLowerCase();
            String specificDays = (task.getRepeatOnSpecificDay() == null || task.getRepeatOnSpecificDay().equals("none"))
                    ? ""
                    : task.getRepeatOnSpecificDay().trim();

            switch (repeatType) {
                case "daily": {
                    // Show on the weekday matching deadline only
                    if (!deadline.isBefore(weekStart) && !deadline.isAfter(weekEnd)) {
                        String day = capitalizeDay(deadline.getDayOfWeek().name().substring(0, 3));
                        panel_weeklyschedule.addTaskToDay(day, dir, task);
                    }
                    break;
                }

                case "weekly": {
                    if (!specificDays.isEmpty()) {
                        for (String day : specificDays.split("\\|")) {
                            panel_weeklyschedule.addTaskToDay(day, dir, task);
                        }
                    } else {
                        if (!deadline.isBefore(weekStart) && !deadline.isAfter(weekEnd)) {
                            String day = capitalizeDay(deadline.getDayOfWeek().name().substring(0, 3));
                            panel_weeklyschedule.addTaskToDay(day, dir, task);
                        }
                    }
                    break;
                }

                case "biweekly": {
                    long weeksBetween = java.time.temporal.ChronoUnit.WEEKS.between(deadline, today);
                    if (weeksBetween % 2 == 0) {
                        if (!specificDays.isEmpty()) {
                            for (String day : specificDays.split("\\|")) {
                                panel_weeklyschedule.addTaskToDay(day, dir, task);
                            }
                        } else if (!deadline.isBefore(weekStart) && !deadline.isAfter(weekEnd)) {
                            String day = capitalizeDay(deadline.getDayOfWeek().name().substring(0, 3));
                            panel_weeklyschedule.addTaskToDay(day, dir, task);
                        }
                    }
                    break;
                }

                case "monthly": {
                    if (deadline.getMonth() == today.getMonth()) {
                        if (!specificDays.isEmpty()) {
                            for (String day : specificDays.split("\\|")) {
                                panel_weeklyschedule.addTaskToDay(day, dir, task);
                            }
                        } else {
                            String day = capitalizeDay(deadline.getDayOfWeek().name().substring(0, 3));
                            panel_weeklyschedule.addTaskToDay(day, dir, task);
                        }
                    }
                    break;
                }

                default: { // Non-repeatable
                    if (!deadline.isBefore(weekStart) && !deadline.isAfter(weekEnd)) {
                        String day = capitalizeDay(deadline.getDayOfWeek().name().substring(0, 3));
                        panel_weeklyschedule.addTaskToDay(day, dir, task);
                    }
                    break;
                }
            }
        }
    }

    private String capitalizeDay(String day) {
        if (day == null || day.length() < 3) return "Mon";
        return day.substring(0, 1).toUpperCase() + day.substring(1, 3).toLowerCase();
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        panel_weeklyschedule.setEventHandler(eventHandler);
        panel_thisweek.setEventHandler(eventHandler);
        panel_todaylist.setEventHandler(eventHandler);
        loadReminder();
    }
    public void loadCorrectPanel(){
        if (showingFocused){
            showingFocused = false;
            ConfigLoader.setShowFocusedSchedule(!showingFocused);
            ConfigLoader.setShowWeeklySchedule(showingFocused);
            panel_todaylist.setVisible(true);
            panel_thisweek.setVisible(true);
            panel_weeklyschedule.setVisible(false);
        }else{
            ConfigLoader.setShowFocusedSchedule(showingFocused);
            ConfigLoader.setShowWeeklySchedule(!showingFocused);
            showingFocused = true;
            panel_todaylist.setVisible(false);
            panel_thisweek.setVisible(false);
            panel_weeklyschedule.setVisible(true);

        }
    }

    @Override
    public void onThemeChanged() {
    backgroundColor = ThemeLoader.getColor(ThemeColorKey.PANEL_REMINDER);
    this.setBackground(backgroundColor);
    repaint();
    revalidate();
    }
}
