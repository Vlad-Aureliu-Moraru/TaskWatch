package UserInterface.CLI;

import Logic.Handlers.EventHandler;
import Note.Model.Note;
import Task.Model.Task;
import Logic.Loaders.ConfigLoader;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;
import Logic.Loaders.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static UserInterface.PanelListElements.ListStages.*;

public class PANEL_cli extends JPanel {
    private final JTextField commandField = new JTextField();
    private EventHandler eventHandler;
    private boolean active = false;
    private int step = 1;
    private boolean commandFound;
    private boolean editing;
    private final ArrayList<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1;

    private Task addedTask;

    private final CommandHelper commandHelper = new CommandHelper();


    public PANEL_cli() {
        setBackground(ThemeLoader.getColor(ThemeColorKey.CONSOLE_COLOR));
        this.setLayout(null);
        this.add(commandField);
        commandField.setEditable(true);
        commandField.setOpaque(false);
        commandField.setBackground(new Color(0, 0, 0, 0));
        commandField.setCaretColor(Color.white);
        setVisible(false);
        commandField.setForeground(ThemeLoader.getColor(ThemeColorKey.CONSOLE_TEXT_COLOR));
        commandField.setFont(new Font("ARIAL", Font.PLAIN, 14));
        commandField.setBorder(null);
        commandField.setBounds(0, 0, 20, 20);
        commandField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (commandHistory.isEmpty()) return;

                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP -> {
                        if (historyIndex > 0) {
                            historyIndex--;
                            commandField.setText(commandHistory.get(historyIndex));
                        }
                    }
                    case java.awt.event.KeyEvent.VK_DOWN -> {
                        if (historyIndex < commandHistory.size() - 1) {
                            historyIndex++;
                            commandField.setText(commandHistory.get(historyIndex));
                        } else {
                            // Move beyond last command -> empty input
                            historyIndex = commandHistory.size();
                            commandField.setText("");
                        }
                    }
                }
            }
        });

    }

    public void setHEIGHTandWIDTH(int height, int width) {
        commandField.setBounds(2, 0, width, height);
    }

    public void activate() {
        if (active) {
            System.out.println("FROM UserInterface.CLI func DEACTIVATE");
            step = 1;
            active = false;
            this.setVisible(false);
            commandField.setText("");
        } else {
            System.out.println("FROM UserInterface.CLI func ACTIVATE");
            active = true;
            this.setVisible(true);
            commandField.setText(":");
            commandField.requestFocus();
        }
    }

    private void loadArchiveInput(String archiveName) {
        commandField.setText("Archive_Name:" + archiveName);
    }

    private void loadDirrectoryInput(String dirName) {
        System.out.println("FROM UserInterface.CLI LOADING DIR INPUT");
        commandField.setText("Directory_Name:" + dirName);
    }

    private void loadTaskInput(int step, boolean editing) {
        if (step == 1) {
            addedTask = new Task();
            if (editing) {
                commandField.setText("Task_Name:" + eventHandler.getCurrentTask().getName());
            } else {
                commandField.setText("Task_Name:");
//                addedTask = new Task();
            }
        } else if (step == 2) {
            if (editing) {
                commandField.setText("Task_Description:" + eventHandler.getCurrentTask().getDescription());
            } else {
                commandField.setText("Task_Description:");
            }
        } else if (step == 3) {
            if (editing) {
                commandField.setText("Task_Priority:" + eventHandler.getCurrentTask().getUrgency());
            } else {
                commandField.setText("Task_Priority:");
            }
        } else if (step == 4) {
            if (editing) {
                String content = eventHandler.getCurrentTask().getDeadline();
                if (content != null) {
                    if (!content.equals("none")) {
                        commandField.setText("Task_Completion_Date:" + eventHandler.getCurrentTask().getDeadline());
                    } else {
                        commandField.setText("Task_Completion_Date:");
                    }
                }
            } else {
                commandField.setText("Task_Completion_Date:");
            }
        } else if (step == 5) {
            if (editing) {
                commandField.setText("Task_Completion_Time:" + eventHandler.getCurrentTask().getTimeDedicated());
            } else {
                commandField.setText("Task_Completion_Time:");
            }
        } else if (step == 6) {
            if (editing) {
                commandField.setText("Difficulty:" + eventHandler.getCurrentTask().getDifficulty());
            } else {
                commandField.setText("Difficulty:");
            }
        } else if (step == 7) {
            if (editing) {
                commandField.setText("isRepeatable:" + eventHandler.getCurrentTask().isRepeatable());
            } else {
                commandField.setText("isRepeatable:");
            }
        } else if (step == 8) {
            if (editing) {
                commandField.setText("RepeatableType:" + eventHandler.getCurrentTask().getRepeatableType());
            } else {
                commandField.setText("RepeatableType:");
            }
        } else if (step == 9) {
            if (editing) {
                boolean value = eventHandler.getCurrentTask().isHasToBeCompletedToRepeat();
                String strVal = value ? "y" : "n";
                commandField.setText("Has To Be Completed To Repeat(y/n):" + strVal);
            } else {
                commandField.setText("Has To Be Completed To Repeat(y/n):");
            }
        } else if (step == 10) {
            if (editing) {
                commandField.setText("Repeats On Specific Day (Mon|Tue|Wed|Thu|Fri|Sat|Sun):" + eventHandler.getCurrentTask().getRepeatOnSpecificDay());
            } else {
                commandField.setText("Repeats On Specific Day (Mon|Tue|Wed|Thu|Fri|Sat|Sun):");
            }
        }
    }

    private void loadNoteInput(String note) {
        commandField.setText("Note:" + note);
    }

    private void loadConfirmation() {
        commandField.setText("Are You Sure (y/n):");
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        commandField.addActionListener(e -> {
            String command = commandField.getText();
            HandleBasicCommands(command);
            if (!commandFound) {
                HandleThemeRelatedCommands(command);
            }
            if (!commandFound) {
                HandleThemeRelatedCommands(command);
            }
            if (!commandFound) {
                HandleThemeSettingCommands(command);
            }
            if (!commandFound){
                HandleConfigRelatedCommands(command);
            }
            if (!command.trim().isEmpty() && commandFound) {
                commandHistory.add(command);
                historyIndex = commandHistory.size();
            }
            if (commandHistory.size() > 100) {
                commandHistory.remove(0);
            }
            //not saving
            if (!commandFound) {
                HandleArchiveRelatedCommands(command);
            }
            if (!commandFound) {
                HandleDirRelatedCommands(command);
            }
            if (!commandFound) {
                HandleNoteRelatedCommands(command);
            }
            if (!commandFound) {
                HandleTaskRelatedCommands(command);
            }
            if (!commandFound) {
                eventHandler.getPanelnavbar().displayTempMessage("Command not recognized.", true);
            }



        });
    }

    private void HandleBasicCommands(String command) {
        commandFound = true;
        if (command.matches(commandHelper.getAddCommand())) {
            editing = false;
            if (eventHandler.getPanelList().getStage() == ARCHIVE_MENU) {
                loadDirrectoryInput("");
            } else if (eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                loadTaskInput(step, false);
            } else if (eventHandler.getPanelList().getStage() == TASK_MENU) {
                loadNoteInput("");
            } else if (eventHandler.getPanelList().getStage() == MAIN_MENU) {
                loadArchiveInput("");
            }

        } else if (command.matches(commandHelper.getCancelCommand())) {
            activate();
            step = 1;
            editing = false;
        }
        else if (command.matches(commandHelper.getEditCommand())) {
            editing = true;
            if (eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                loadDirrectoryInput(eventHandler.getCurrentDirectory().getName());
            } else if (eventHandler.getPanelList().getStage() == NOTE_CLICKED) {
                loadNoteInput(eventHandler.getCurrentNote().getNote());
            } else if (eventHandler.getPanelList().getStage() == TASK_MENU) {
                loadTaskInput(step, editing);
            } else if (eventHandler.getPanelList().getStage() == ARCHIVE_MENU) {
                loadArchiveInput(eventHandler.getCurrentArchive().getArchiveName());
            }
        }
        else if (command.matches(commandHelper.getSortByUrgencyCommand()) && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
            String value = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
            eventHandler.getPanelList().sortTasksByUrgency(value.equals("a"));
            activate();
        }
        else if (command.matches(commandHelper.getRemoveCommand())) {
            if (eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                loadConfirmation();
            } else if (eventHandler.getPanelList().getStage() == TASK_MENU && !(eventHandler.getPanelList().getStage() == NOTE_CLICKED)) {
                loadConfirmation();
            } else if (eventHandler.getPanelList().getStage() == NOTE_CLICKED) {
                loadConfirmation();
            } else if (eventHandler.getPanelList().getStage() == ARCHIVE_MENU) {
                loadConfirmation();
            }
        }
        else if (command.matches(commandHelper.getConfirmation())) {
            String commandParameeter = command.substring(command.indexOf(":") + 1);
            if (commandParameeter.equals("y")) {
                System.out.println("[LOG] deleting file");
                switch (eventHandler.getPanelList().getStage()) {
                    case ARCHIVE_MENU:
                        eventHandler.deleteArchive();
                        eventHandler.getPanelnavbar().returnFunction(true);
                        activate();
//                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                        break;
                    case DIRECTORY_MENU:
                        eventHandler.deleteDirectory();
                        eventHandler.getPanelnavbar().returnFunction(true);
                        activate();
//                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                        break;

                    case TASK_MENU:
                        eventHandler.deleteTask();
                        eventHandler.getPanelnavbar().returnFunction(true);
                        activate();
                        break;
                    case NOTE_CLICKED:
                        eventHandler.deleteNote();
                        activate();
                        break;
                }
            } else {
                activate();
            }
            System.out.println(commandParameeter);
        }
        else if (command.matches(commandHelper.getStartTimerCommand())) {
            int value = Integer.parseInt(command.substring(command.indexOf("(") + 1, command.indexOf(")")));
            if (value < 1) {
                eventHandler.getPanelnavbar().displayTempMessage("Enter a value >=1", true);
            } else {
                eventHandler.getPanelMainmenu().getPanel_clock().startTaskTimer(value);
                activate();
            }
        }
        else if (command.matches(commandHelper.getStopTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().stopTaskTimerandStartClockTimer();
            activate();

        }
        else if (command.matches(commandHelper.getStartSelectedTaskTimer()) && eventHandler.getCurrentTask()!=null){
            if (!eventHandler.getCurrentTask().isFinished()) {
                eventHandler.getPanelMainmenu().getPanel_clock().startTaskTimer(eventHandler.getCurrentTask(), eventHandler.getCurrentDirectory());
                activate();
            }
        }
        else if (command.matches(commandHelper.getPauseTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().pauseOrunpauseTaskTimer();
            activate();

        }
        else if (command.matches(commandHelper.getRestartTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().resetTimer();
            activate();
        }
        else if (command.matches(commandHelper.getSortByDifficultyCommand()) && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
            String value = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
            eventHandler.getPanelList().sortByDifficulty(value.equals("a"));
            activate();
        }
        else if (command.matches(commandHelper.getFinishTaskCommand()) && eventHandler.getPanelList().getStage() == TASK_MENU) {

            if (eventHandler.getCurrentTask().isFinished()) {
                System.out.println("[LOG] reached finished statement");
                eventHandler.setTaskFinished(false);
                eventHandler.getCurrentTask().setFinishedDate(null);

            } else {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime = now.format(formatter);
                eventHandler.setTaskFinished(true);
                eventHandler.getCurrentTask().setFinishedDate(formattedDateTime);
                eventHandler.updateDeadlineForRepeatableTasks(eventHandler.getCurrentTask());
            }
            activate();
            eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());
            eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();

        } else if (command.matches(commandHelper.getShowFinishedTasks()) && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
            eventHandler.getPanelList().switchShowingFinished();
            eventHandler.getPanelList().loadCurrentTasks(null);
            activate();
        }
        else if (command.matches(commandHelper.getHelpCommand())) {
            eventHandler.getPanelMainmenu().getPanel_help().switchVisible();
            eventHandler.getPanelMainmenu().getPanel_clock().switchVisible();
            activate();
        }
        else if (command.matches(commandHelper.getExitCommand())) {
            System.out.println("exit ?");
            System.exit(0);
        }
        else if (command.matches(commandHelper.getSwitchScheduleDisplay())) {
            eventHandler.getPanelMainmenu().getPanel_reminder().loadCorrectPanel();
            activate();
        }
        else {
            commandFound = false;
        }

    }
    private void HandleArchiveRelatedCommands(String command) {
        commandFound = true;
        String commandParameeter = command.substring(command.indexOf(":") + 1);
        if (command.matches(commandHelper.getArchiveNameRegEx())) {
            if (!editing) {
                System.out.println("[LOG] creating archive " + commandParameeter);
                eventHandler.addArchive(commandParameeter);
                activate();
            } else {
                eventHandler.updateArchive(commandParameeter);
                activate();
            }
        } else {
            commandFound = false;
        }

    }
    private void HandleDirRelatedCommands(String command) {
        commandFound = true;
        if (command.matches(commandHelper.getDirectoryNameRegEx())) {
            String commandParameeter = command.substring(command.indexOf(":") + 1);
            if (commandParameeter.isEmpty() || command.contains(";")) {
                eventHandler.getPanelnavbar().displayTempMessage("INVALID DIR NAME", true);
            } else if (!editing && eventHandler.getPanelList().getStage() == ARCHIVE_MENU) {
                eventHandler.addDirectory(commandParameeter);
                activate();
            } else if (eventHandler.getPanelList().getStage() == DIRECTORY_MENU && editing) {
                eventHandler.updateDirectoryName(commandParameeter);
                editing = false;
                activate();
            }
        } else {
            commandFound = false;
        }
    }
    private void HandleNoteRelatedCommands(String command) {
        commandFound = true;
        if (command.matches(commandHelper.getNoteRegEx())) {
            if (command.contains(";")) {
                eventHandler.getPanelnavbar().displayTempMessage("INVALID NOTE MESSAGE", true);
            } else if (!editing) {
                Note note = new Note();
                note.setNote(command.substring(command.indexOf(":") + 1));

                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
                String formattedDateTime = now.format(formatter);
                note.setDate(formattedDateTime);
                eventHandler.addNote(note);
                activate();
            } else {
                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
                String formattedDateTime = now.format(formatter);
                Note note = new Note();
                note.setNote(command.substring(command.indexOf(":") + 1));
                note.setDate(formattedDateTime);
                eventHandler.updateNote(note);
                eventHandler.getPanelMainmenu().getPanel_noteinfo().addNoteInfo(eventHandler.getCurrentNote());
                eventHandler.getPanelList().loadCurrentTaskNotes();
                editing = false;
                activate();
            }
        } else {
            commandFound = false;
        }
    }
    private void HandleTaskRelatedCommands(String command) {
        commandFound = true;
        String commandParameeter = command.substring(command.indexOf(":") + 1);
        if (command.matches(commandHelper.getTaskNameRegEx())) {
            if (commandParameeter.trim().isEmpty() || commandParameeter.contains(";")) {
                eventHandler.getPanelnavbar().displayTempMessage("INVALID TASK NAME", true);
            } else {
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    addedTask.setName(commandParameeter);
                } else if (eventHandler.getPanelList().getStage() == TASK_MENU && editing) {
                    addedTask.setName(commandParameeter);
                }
                step++;
                loadTaskInput(step, editing);
            }
        } else if (command.matches(commandHelper.getTaskDescriptionRegEx())) {
            if (commandParameeter.contains(";")) {
                eventHandler.getPanelnavbar().displayTempMessage("INVALID TASK DESCRIPTION", true);
            } else {
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    addedTask.setDescription(command.substring(command.indexOf(":") + 1));
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    addedTask.setDescription(command.substring(command.indexOf(":") + 1));
                }
                step++;
                loadTaskInput(step, editing);
            }
        } else if ((command.matches(commandHelper.getTaskPriorityRegEx()))) {
            if (commandParameeter.isEmpty()) {
                step++;
                loadTaskInput(step, editing);
            } else if (Integer.parseInt(commandParameeter) >= 1 && Integer.parseInt(commandParameeter) <= 5) {
                int Urgency = Integer.parseInt(command.substring(command.indexOf(":") + 1));
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    addedTask.setUrgency(Urgency);
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    addedTask.setUrgency(Urgency);
                }
                step++;
                loadTaskInput(step, editing);
            } else {
                eventHandler.getPanelnavbar().displayTempMessage("Priority must be between 1 and 5", true);
            }
        } else if ((command.matches(commandHelper.getTaskCompletionDateRegEx()))) {
            LocalDate now = LocalDate.now();
            if (!commandParameeter.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate = LocalDate.parse(commandParameeter, formatter);
                    if (inputDate.isBefore(now)) {
                        eventHandler.getPanelnavbar().displayTempMessage("DATE CAN'T BE IN PAST", true);
                    } else {
                        if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                            addedTask.setDeadline(commandParameeter);
                        } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                            addedTask.setDeadline(commandParameeter);
                        }
                        step++;
                        loadTaskInput(step, editing);
                    }
                } catch (DateTimeParseException e) {
                    eventHandler.getPanelnavbar().displayTempMessage("INVALID FORMAT dd/MM/yyyy", true);
                    System.out.println("[ERROR] The date format is invalid. Please use dd/MM/yyyy.");
                }
            } else {
                step++;
                loadTaskInput(step, editing);
            }
        } else if (command.matches(commandHelper.getTaskCompletionTimeRegEx())) {
            if (!commandParameeter.isEmpty()) {
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    addedTask.setTimeDedicated(Integer.parseInt(commandParameeter));
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    addedTask.setTimeDedicated(Integer.parseInt(commandParameeter));
                }
            }
            step++;
            loadTaskInput(step, editing);
        } else if (command.matches(commandHelper.getTaskDifficultyRegEx())) {
            if (commandParameeter.isEmpty()) {
                step++;
                loadTaskInput(step, editing);
            } else if (Integer.parseInt(commandParameeter) >= 1 && Integer.parseInt(commandParameeter) <= 5) {
                int Urgency = Integer.parseInt(commandParameeter);
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    addedTask.setDifficulty(Urgency);
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    addedTask.setDifficulty(Urgency);
                }
                step++;
                loadTaskInput(step, editing);
            } else {
                eventHandler.getPanelnavbar().displayTempMessage("Difficulty must be between 1 and 5", true);
            }
        } else if (command.matches(commandHelper.getTaskRepeatableRegEx())) {
            String value = command.substring(command.indexOf(":") + 1);
            if (value.isEmpty() || value.equals("false")) {
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {

                    eventHandler.addTask(addedTask);
                    step = 1;
                    activate();
//                    eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    eventHandler.updateTaskDetails(addedTask);
                    step = 1;
                    activate();
                    editing = false;
                }
            } else if (!editing) {
                addedTask.setRepeatable(true);
                step++;
                loadTaskInput(step, editing);
            } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                addedTask.setRepeatable(true);
                step++;
                loadTaskInput(step, editing);

            }
        } else if (command.matches(commandHelper.getTaskRepeatableTypeRegEx())) {
            if (!commandParameeter.isEmpty()) {
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    addedTask.setRepeatableType(commandParameeter);
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    addedTask.setRepeatableType(commandParameeter);
                }
                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
            }
            step++;
            loadTaskInput(step, editing);
        } else if (command.matches(commandHelper.getTaskHasToBeCompletedToRepeatRegEx())) {
            boolean value = false;
            if (commandParameeter.equals("y")) {
                value = true;
            }
            if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                addedTask.setHasToBeCompletedToRepeat(value);
            } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                addedTask.setHasToBeCompletedToRepeat(value);
            }
            if (addedTask.getDeadline() == "none" && !addedTask.getRepeatableType().equals("daily")) {
                step++;
                loadTaskInput(step, editing);
            } else {
                if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                    eventHandler.addTask(addedTask);
                    step = 1;
                    activate();
                } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                    eventHandler.updateTaskDetails(addedTask);
                    step = 1;
                    activate();
                }
            }
        } else if (command.matches(commandHelper.getRepeatsOnSpecificDayRegEx())) {
            String dayParam = commandParameeter.trim();
            addedTask.setRepeatOnSpecificDay(dayParam);

            if (addedTask.getDeadline().equals("none")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate today = LocalDate.now();

                String firstDay = dayParam.split("\\|")[0].substring(0, 3).toLowerCase();

                java.time.DayOfWeek targetDay = switch (firstDay) {
                    case "mon" -> java.time.DayOfWeek.MONDAY;
                    case "tue" -> java.time.DayOfWeek.TUESDAY;
                    case "wed" -> java.time.DayOfWeek.WEDNESDAY;
                    case "thu" -> java.time.DayOfWeek.THURSDAY;
                    case "fri" -> java.time.DayOfWeek.FRIDAY;
                    case "sat" -> java.time.DayOfWeek.SATURDAY;
                    case "sun" -> java.time.DayOfWeek.SUNDAY;
                    default -> null;
                };

                if (targetDay != null) {
                    LocalDate nextDate = today;
                    while (nextDate.getDayOfWeek() != targetDay) {
                        nextDate = nextDate.plusDays(1);
                    }
                    addedTask.setDeadline(nextDate.format(formatter));
                }
            }

            // Save or update task
            if (!editing && eventHandler.getPanelList().getStage() == DIRECTORY_MENU) {
                eventHandler.addTask(addedTask);
                step = 1;
                activate();
            } else if (editing && eventHandler.getPanelList().getStage() == TASK_MENU) {
                eventHandler.updateTaskDetails(addedTask);
                step = 1;
                activate();
            }

        } else {
            commandFound = false;
        }
    }
    private void HandleThemeRelatedCommands(String command) {
        commandFound = true;
        if (command.matches(commandHelper.getCreateThemeCommand())) {
            String themename = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
            try {
                ThemeManager.createThemeFile(themename);
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            activate();
        } else if (command.matches(commandHelper.getRemoveThemeCommand())) {
            String themename = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
            try {
                ThemeManager.deleteThemeFile(themename);
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            activate();
        } else if (command.matches(commandHelper.getSetThemeCommand())) {
            String themename = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
            ConfigLoader.setTheme(themename + ".css");
            eventHandler.getMainFrame().refreshComponents();

            ConfigLoader.loadConfig();
            ThemeLoader.loadTheme();
            ThemeLoader.notifyThemeChanged();
            activate();
        } else if (command.matches(commandHelper.getCurrentThemeCommand())) {
            eventHandler.getPanelnavbar().displayTempMessage(ConfigLoader.getTheme().substring(0, ConfigLoader.getTheme().indexOf(".")), false);
            activate();
        } else if (command.matches(commandHelper.getListThemesCommand())) {
            eventHandler.getPanelnavbar().displayTempMessage(ThemeManager.listAvailableThemes() + "", false);
            activate();
        } else {
            commandFound = false;
        }
    }
    private void HandleThemeSettingCommands(String command) {
        commandFound = true;
        boolean matched = false;
        for (ThemeColorKey key : ThemeColorKey.values()) {
            String regex = commandHelper.getSetColorCommandForKey(key);
            if (command.matches(regex)) {
                String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")") + 1);
                Color color = ThemeLoader.parseColor("rgb" + colorNumbersForRGB);

                ThemeLoader.setColor(key, color);
                matched = true;
                break;
            }
        }

        if (matched) {
            activate();
        } else {
            commandFound = false;
        }
    }
    private void HandleConfigRelatedCommands(String command) {
        commandFound = false;
        if(command.matches(commandHelper.getProgressBarClockWidthCommand())){
            int barWidth = Integer.parseInt(command.trim().substring(command.trim().indexOf("(") + 1, command.trim().indexOf(")")));
            ConfigLoader.setBarWidth(barWidth);
            System.out.println("setBarWidth: " + barWidth);
            activate();
            commandFound = true;
        }
        else if (command.matches(commandHelper.getProgressBarClockSpacingCommand())) {
            int barSpacing = Integer.parseInt(command.trim().substring(command.trim().indexOf("(") + 1, command.trim().indexOf(")")));
            ConfigLoader.setBarSpacing(barSpacing);
            System.out.println("setBarWidth: " +barSpacing);
            activate();
            commandFound = true;

        }
        else if (command.matches(commandHelper.getClockIncrementCommand())) {
            int clockInc = Integer.parseInt(command.trim().substring(command.trim().indexOf("(") + 1, command.trim().indexOf(")")));
            ConfigLoader.setClockUpdateTime(clockInc);
            System.out.println("setBarWidth: " +clockInc);
            activate();
            commandFound = true;

        }
    }
}
