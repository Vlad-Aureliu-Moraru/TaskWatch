package CLI;

import AppLogic.Archive;
import AppLogic.Directory;
import Handlers.EventHandler;
import AppLogic.Note;
import AppLogic.Task;
import ConfigRelated.ConfigLoader;
import ConfigRelated.ThemeLoader;
import ConfigRelated.ThemeManager;
import UserInterface.PanelListElements.ListStages;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PANEL_cli extends JPanel {
    private final JTextField commandField= new JTextField();
    private EventHandler eventHandler;
    private boolean active = false;
    private int step = 1;
    private boolean commandFound ;
    private boolean editing;

    private Task addedTask;

    private final CommandHelper commandHelper = new CommandHelper();


    public PANEL_cli() {
        setBackground(ThemeLoader.getConsoleColor());
        this.setLayout(null);
        this.add(commandField);
        commandField.setEditable(true);
        commandField.setOpaque(false);
        commandField.setBackground(new Color(0, 0, 0, 0));
        commandField.setCaretColor(Color.white);
        setVisible(false);
        commandField.setForeground(ThemeLoader.getConsoleTextColor());
        commandField.setFont(new Font("ARIAL",Font.PLAIN, 14));
        commandField.setBorder(null);
        commandField.setBounds(0,0,20,20);
    }
    public void setHEIGHTandWIDTH(int height,int width){
        commandField.setBounds(2,0,width,height);
    }
    public void activate(){
        if(active){
            System.out.println("FROM CLI func DEACTIVATE");
            step=1;
            active=false;
            this.setVisible(false);
            commandField.setText("");
        }else {
            System.out.println("FROM CLI func ACTIVATE");
            active=true;
            this.setVisible(true);
            commandField.setText(":");
            commandField.requestFocus();
        }
    }
    private void loadArchiveInput(String archiveName){
        System.out.println("FROM CLI LOADING Arch INPUT");
        commandField.setText("Archive_Name:"+ archiveName);
    }
    private void loadDirrectoryInput(String dirName){
        System.out.println("FROM CLI LOADING DIR INPUT");
        commandField.setText("Directory_Name:"+dirName);
    }
    private void loadTaskInput(int step,boolean editing){
        if(step==1){
            if(editing){
                commandField.setText("Task_Name:"+eventHandler.getCurrentTask().getName());
            }else{
                commandField.setText("Task_Name:");
//                addedTask = new Task();
            }
        }else if(step==2){
            if(editing){
                commandField.setText("Task_Description:"+eventHandler.getCurrentTask().getDescription());
            }else{
                commandField.setText("Task_Description:");
            }
        } else if (step==3) {
            if(editing){
                commandField.setText("Task_Priority:"+eventHandler.getCurrentTask().getUrgency());
            }else{
                commandField.setText("Task_Priority:");
            }
        }else if(step==4){
            if(editing){
                String content = eventHandler.getCurrentTask().getDeadline();
                if (content != null) {
                    if (!content.equals("none")) {
                        commandField.setText("Task_Completion_Date:" + eventHandler.getCurrentTask().getDeadline());
                    } else {
                        commandField.setText("Task_Completion_Date:");
                    }
                }
            }else{
                commandField.setText("Task_Completion_Date:");
            }
        }
        else if(step==5){
            if(editing){
                commandField.setText("Task_Completion_Time:"+eventHandler.getCurrentTask().getTimeDedicated());
            }else{
                commandField.setText("Task_Completion_Time:");
            }
        }
        else if(step==7){
            if(editing){
                commandField.setText("isRepeatable:"+eventHandler.getCurrentTask().isRepeatable());
            }else{
                commandField.setText("isRepeatable:");
            }
        } else if (step==8) {
            if(editing){
                commandField.setText("RepeatableType:"+eventHandler.getCurrentTask().getRepeatableType());
            }else{
                commandField.setText("RepeatableType:");
            }
        } else if (step==6) {
            if(editing){
                commandField.setText("Difficulty:"+eventHandler.getCurrentTask().getDifficulty());
            }else{
                commandField.setText("Difficulty:");
            }
        }
    }
    private void loadNoteInput(String note){
        commandField.setText("Note:"+note);
    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        commandField.addActionListener(e -> {
            String command = commandField.getText();
            HandleBasicCommands(command);
            if (!commandFound){
                HandleDirRelatedCommands(command);
            }
            if (!commandFound){
                HandleNoteRelatedCommands(command);
            }
            if (!commandFound){
                HandleTaskRelatedCommands(command);
            }
            if (!commandFound){
                HandleThemeRelatedCommands(command);
            }
            if (!commandFound){
                HandleThemeSettingCommands(command);
            }
            if (!commandFound){
                HandleArchiveRelatedCommands(command);
            }
            if (!commandFound) {
                eventHandler.getPanelnavbar().displayTempMessage("Command not recognized.",true);
            }

        });
    }
    private void HandleBasicCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getAddCommand())){
            editing=false;
            if (eventHandler.getPanelList().getStage()== ListStages.ARCHIVE_MENU){
                loadDirrectoryInput("");
            } else if (eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU ) {
                loadTaskInput(step,false);
            }
            else if (eventHandler.getPanelList().getStage()==ListStages.TASK_MENU) {
                loadNoteInput("");
            }else if (eventHandler.getPanelList().getStage()==ListStages.MAIN_MENU){
               loadArchiveInput("");
            }

        }
        else if (command.matches(commandHelper.getCancelCommand())){
            activate();
            step = 1;
            editing = false;
        }
        else if (command.matches(commandHelper.getEditCommand())){
            editing = true;
            if (eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU){
                loadDirrectoryInput(eventHandler.getCurrentDirectory().getName());
            }
            else if (eventHandler.getPanelList().isNoteSelected()){
                loadNoteInput(eventHandler.getCurrentNote().getNote());
            }else if (eventHandler.getPanelList().getStage()==ListStages.TASK_MENU){
                loadTaskInput(step,editing);
            }
        }
        else if (command.matches(commandHelper.getSortByUrgencyCommand())&&eventHandler.getPanelList().getStage()==ListStages.ARCHIVE_MENU) {
            String value = command.substring(command.indexOf("(")+1, command.indexOf(")"));
            eventHandler.getPanelList().sortTasksByUrgency(value.equals("a"));
            activate();
        }
        else if (command.matches(commandHelper.getRemoveCommand())) {
            System.out.println(eventHandler.getPanelList().getStage());
            if (eventHandler.getPanelList().getStage()==ListStages.ARCHIVE_MENU) {
                eventHandler.getFileHandler().removeDirectoryFromFiles();
                eventHandler.getPanelnavbar().returnFunction(true);
                eventHandler.resetCurrentDirectory();
                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                activate();
            }else if (eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU && !eventHandler.getPanelList().isNoteSelected()) {
                eventHandler.getFileHandler().removeTaskFromFiles();
                eventHandler.getPanelMainmenu().getPanel_taskinfo().deactivate();
                eventHandler.getPanelMainmenu().getPanel_clock().activate();
                eventHandler.getPanelMainmenu().getPanel_noteinfo().deactivate();
                eventHandler.getPanelnavbar().returnFunction(true);
                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                activate();
            }
            else if (eventHandler.getPanelList().isNoteSelected()) {
                eventHandler.getFileHandler().removeNotesFromFile();
                eventHandler.getPanelMainmenu().getPanel_taskinfo().deactivate();
                eventHandler.getPanelMainmenu().getPanel_clock().activate();
                eventHandler.getPanelMainmenu().getPanel_noteinfo().deactivate();
                eventHandler.getPanelnavbar().returnFunction(false);
                activate();
            }
        }
        else if (command.matches(commandHelper.getStartTimerCommand())) {
            int value = Integer.parseInt(command.substring(command.indexOf("(")+1,command.indexOf(")")));
            if (value<1){
                eventHandler.getPanelnavbar().displayTempMessage("Enter a value >=1",true);
            }else{
                eventHandler.getPanelMainmenu().getPanel_clock().startTaskTimer(value);
                activate();
            }
        }
        else if (command.matches(commandHelper.getStopTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().stopTaskTimerandStartClockTimer();
            activate();

        }
        else if(command.matches(commandHelper.getStartSelectedTaskTimer()) && eventHandler.getPanelList().getStage()==ListStages.TASK_MENU) {
            if (!eventHandler.getCurrentTask().isFinished()){
                eventHandler.getPanelMainmenu().getPanel_clock().startTaskTimer(eventHandler.getCurrentTask(),eventHandler.getCurrentDirectory());
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
        else if (command.matches(commandHelper.getSortByDifficultyCommand())&&eventHandler.getPanelList().getStage()==ListStages.ARCHIVE_MENU) {
            String value = command.substring(command.indexOf("(")+1, command.indexOf(")"));
            eventHandler.getPanelList().sortByDifficulty(value.equals("a"));
            activate();
        }
        else if (command.matches(commandHelper.getFinishTaskCommand())&&eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU) {
            if (eventHandler.getCurrentTask().isFinished()){
                eventHandler.getCurrentTask().setFinished(false);
                eventHandler.getCurrentTask().setFinishedDate(null);

            }else{
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime = now.format(formatter);
                eventHandler.getCurrentTask().setFinished(true);
                eventHandler.getCurrentTask().setFinishedDate(formattedDateTime);
                eventHandler.updateDeadlineForRepeatableTasks(eventHandler.getCurrentTask());
            }
            eventHandler.getFileHandler().saveTaskToFile();
            activate();
            eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());
            eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();

        }
        else if (command.matches(commandHelper.getShowFinishedTasks())&&eventHandler.getPanelList().getStage()==ListStages.ARCHIVE_MENU) {
            eventHandler.getPanelList().switchShowingFinished();
            eventHandler.getPanelList().loadCurrentTasks();
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
        else {
            commandFound=false;
        }

    }
    private void HandleArchiveRelatedCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getArchiveNameRegEx())){
//        eventHandler.addArchive(new Archive(command.substring(command.indexOf(":")+1)));
        }

        else{
            commandFound=false;
        }

    }
    private void HandleDirRelatedCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getDirectoryNameRegEx())  ) {
            String commandParameeter = command.substring(command.indexOf(":")+1);
//            if (commandParameeter.isEmpty() || command.contains(";") || eventHandler.getCurrentArchive().getDirectories().stream().anyMatch(directory -> directory.getName().equals(commandParameeter))){
//                eventHandler.getPanelnavbar().displayTempMessage("INVALID DIR NAME",true);
//            }else if (!editing && eventHandler.getPanelList().getStage() == ListStages.ARCHIVE_MENU){
//               eventHandler.addDirectory(new Directory(commandParameeter,eventHandler.getCurrentArchive()));
//                activate();
//            } else if (eventHandler.getPanelList().getStage() == ListStages.DIRECTORY_MENU && editing){
//                eventHandler.getFileHandler().renameCurrentDirectory(commandParameeter);
//                eventHandler.getCurrentDirectory().setName(commandParameeter);
//                eventHandler.getFileHandler().saveArchiveListToFile();
//                eventHandler.getPanelnavbar().returnFunction(true);
//                editing = false;
//                activate();
//            }
//        }else{
//                commandFound=false;
            }
    }
    private void HandleNoteRelatedCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getNoteRegEx()) && eventHandler.getPanelList().getStage()==ListStages.TASK_MENU){
            if (command.contains(";")){
                eventHandler.getPanelnavbar().displayTempMessage("INVALID NOTE MESSAGE",true);
            }else if (!editing){
//                Note note = new Note();
//                note.setNote(command.substring(command.indexOf(":")+1));

                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
                String formattedDateTime = now.format(formatter);
//                note.setDate(formattedDateTime);
//                eventHandler.addNote(note);
                activate();
            }else {
                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
                String formattedDateTime = now.format(formatter);
                eventHandler.getCurrentNote().setNote(command.substring(command.indexOf(":")+1));
                eventHandler.getCurrentNote().setDate(formattedDateTime);
                eventHandler.getFileHandler().saveNotesToFile();
                eventHandler.getPanelMainmenu().getPanel_noteinfo().addNoteInfo(eventHandler.getCurrentNote());
                eventHandler.getPanelList().loadCurrentTaskNotes();
                editing = false;
                activate();
            }
        }else{
            commandFound=false;
        }
    }
    private void HandleTaskRelatedCommands(String command){
//        commandFound=true;
        if (command.matches(commandHelper.getTaskNameRegEx()) ) {
//            String commandParameeter = command.substring(command.indexOf(":")+1);
//            if (commandParameeter.trim().isEmpty() || commandParameeter.contains(";")) {
//                eventHandler.getPanelnavbar().displayTempMessage("INVALID TASK NAME",true);
//            }else {
//                if (!editing && !eventHandler.getCurrentDirectory().taskNameExists(commandParameeter)&& eventHandler.getPanelList().getStage()== ListStages.DIRECTORY_MENU ){
//                    addedTask.setName(commandParameeter);
//                }else if (editing && eventHandler.getPanelList().getStage()== ListStages.TASK_MENU){
//                    eventHandler.getFileHandler().renameCurrentTask(commandParameeter);
//                    eventHandler.getCurrentTask().setName(commandParameeter);
//                }
//                step++;
//                loadTaskInput(step,editing);
//            }
        }
        else if(command.matches(commandHelper.getTaskRepeatableRegEx()) ) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty() || value.equals("false")) {
                if (!editing&&eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU ) {
                    eventHandler.addTask(addedTask);
                    step=1;
                    activate();
                    eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                }else if (editing && eventHandler.getPanelList().getStage()==ListStages.TASK_MENU) {
                    step = 1;
                    eventHandler.getFileHandler().saveTaskToFile();
                    eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());
                    eventHandler.getPanelnavbar().setCurrentPATH(eventHandler.getCurrentDirectory().getName()+"/"+eventHandler.getCurrentTask().getName());
                    activate();
                    editing = false;
                    eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                }
            }else if (!editing){
                boolean repeatable = Boolean.parseBoolean(value);
                addedTask.setRepeatable(repeatable);
                step++;
                loadTaskInput(step,editing);
            }else if (editing && eventHandler.getPanelList().getStage()==ListStages.TASK_MENU) {
                boolean repeatable = Boolean.parseBoolean(value);
                eventHandler.getCurrentTask().setRepeatable(repeatable);
                step++;
                loadTaskInput(step,editing);

            }
        }
        else if (command.matches(commandHelper.getTaskDescriptionRegEx()) ) {
            if (command.substring(command.indexOf(":")+1).contains(";")) {
                eventHandler.getPanelnavbar().displayTempMessage("INVALID TASK DESCRIPTION",true);
            }else{
                if (!editing && eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU){
                    addedTask.setDescription(command.substring(command.indexOf(":")+1));
                }else if (editing && eventHandler.getPanelList().getStage()==ListStages.TASK_MENU) {
                    eventHandler.getCurrentTask().setDescription(command.substring(command.indexOf(":")+1));
                }
                step++;
                loadTaskInput(step,editing);
            }
        }
        else if ((command.matches(commandHelper.getTaskPriorityRegEx())) ) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty()) {
                step++;
                loadTaskInput(step,editing);
            }else if (Integer.parseInt(value)>=1&&Integer.parseInt(value)<=5){
                int Urgency =  Integer.parseInt(command.substring(command.indexOf(":")+1));
                if (!editing&&  eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU){
                    addedTask.setUrgency(Urgency);
                }else if (editing && eventHandler.getPanelList().getStage()== ListStages.TASK_MENU){
                    eventHandler.getCurrentTask().setUrgency(Urgency);
                }
                step++;
                loadTaskInput(step,editing);
            }else{
                eventHandler.getPanelnavbar().displayTempMessage("Priority must be between 1 and 5",true);
            }
        }
        else if ((command.matches(commandHelper.getTaskCompletionDateRegEx())) ) {
            String value = command.substring(command.indexOf(":") + 1).trim();
            LocalDate now = LocalDate.now();

            if (!value.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate = LocalDate.parse(value, formatter);
                    if (inputDate.isBefore(now)) {
                        eventHandler.getPanelnavbar().displayTempMessage("DATE CAN'T BE IN PAST",true);
                    } else {
                        if (!editing &&  eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU) {
                            addedTask.setDeadline(value);
                        }else if (editing && eventHandler.getPanelList().getStage()== ListStages.TASK_MENU){
                            eventHandler.getCurrentTask().setDeadline(value);
                        }
                        step++;
                        loadTaskInput(step, editing);
                    }
                } catch (DateTimeParseException e) {
                    eventHandler.getPanelnavbar().displayTempMessage("INVALID FORMAT dd/MM/yyyy",true);
                    System.out.println("Error: The date format is invalid. Please use dd/MM/yyyy.");
                }
            }else{
                step++;
                loadTaskInput(step, editing);
            }
        }
        else if (command.matches(commandHelper.getTaskCompletionTimeRegEx()) ) {
            String  value=  command.substring(command.indexOf(":")+1);
            if (!value.isEmpty()) {
                if (!editing &&  eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU) {
                    addedTask.setTimeDedicated(Integer.parseInt(command.substring(command.indexOf(":") + 1)));
                }else if (editing && eventHandler.getPanelList().getStage()== ListStages.TASK_MENU){
                    eventHandler.getCurrentTask().setTimeDedicated(Integer.parseInt(command.substring(command.indexOf(":") + 1)));
                }
            }
            step++;
            loadTaskInput(step,editing);
        }
        else if (command.matches(commandHelper.getTaskDifficultyRegEx())) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty()) {
                step++;
                loadTaskInput(step,editing);
            }else if (Integer.parseInt(value)>=1&&Integer.parseInt(value)<=5){
                int Urgency =  Integer.parseInt(command.substring(command.indexOf(":")+1));
                if (!editing&&  eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU){
                    addedTask.setDifficulty(Urgency);
                }else if (editing && eventHandler.getPanelList().getStage()== ListStages.TASK_MENU){
                    eventHandler.getCurrentTask().setDifficulty(Urgency);
                }
                step++;
                loadTaskInput(step,editing);
            }else{
                eventHandler.getPanelnavbar().displayTempMessage("Difficulty must be between 1 and 5",true);
            }
        }
        else if (command.matches(commandHelper.getTaskRepeatableTypeRegEx())){
            String value=  command.substring(command.indexOf(":")+1);
            if (!value.isEmpty()) {
                if (!editing&& eventHandler.getPanelList().getStage()==ListStages.DIRECTORY_MENU){
                    addedTask.setRepeatableType(value);
                    eventHandler.addTask(addedTask);
                    step=1;
                    activate();
                    eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                }else if (editing && eventHandler.getPanelList().getStage()== ListStages.TASK_MENU){
                    eventHandler.getCurrentTask().setRepeatableType(value);
                    step = 1;
                    eventHandler.getFileHandler().saveTaskToFile();
                    eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());
                    eventHandler.getPanelnavbar().setCurrentPATH(eventHandler.getCurrentDirectory().getName()+"/"+eventHandler.getCurrentTask().getName());
                    activate();
                    editing = false;
                    eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
                }
                eventHandler.getPanelMainmenu().getPanel_reminder().loadReminder();
            }
        }
        else{
            commandFound=false;
        }
    }
    private void HandleThemeRelatedCommands(String command){
       commandFound=true;
      if (command.matches(commandHelper.getCreateThemeCommand())){
          String themename = command.substring(command.indexOf("(")+1, command.indexOf(")"));
          ThemeManager.createThemeFile(themename);
          activate();
      } else if (command.matches(commandHelper.getRemoveThemeCommand())) {
          String themename = command.substring(command.indexOf("(")+1, command.indexOf(")"));
          ThemeManager.deleteThemeFile(themename);
          activate();
      }
      else if (command.matches(commandHelper.getSetThemeCommand())) {
          String themename = command.substring(command.indexOf("(")+1, command.indexOf(")"));
          ConfigLoader.setTheme(themename+".css");
          eventHandler.getMainFrame().refreshComponents();

          ConfigLoader.loadConfig();
          ThemeLoader.loadTheme();
          activate();
      }
      else if(command.matches(commandHelper.getCurrentThemeCommand())){
          eventHandler.getPanelnavbar().displayTempMessage(ConfigLoader.getTheme().substring(0,ConfigLoader.getTheme().indexOf(".")),false);
          activate();
      }
      else if (command.matches(commandHelper.getListThemesCommand())) {
          eventHandler.getPanelnavbar().displayTempMessage(ThemeManager.listAvailableThemes()+"",false);
          activate();
      }
      else{
          commandFound=false;
      }
    }
    private void HandleThemeSettingCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getSetMainColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setMainColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetSecondaryColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setSecondaryColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetFirstAccentCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setFirstAccent(color);
            activate();
        } else if (command.matches(commandHelper.getSetSecndAccentCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setSecndAccent(color);
            activate();
        } else if (command.matches(commandHelper.getSetSecondaryGreenCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setSecondaryGreen(color);
            activate();
        } else if (command.matches(commandHelper.getSetAccentGreenCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setAccentGreen(color);
            activate();
        } else if (command.matches(commandHelper.getSetDirColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDirColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetDirHoverColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDirHoverColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTaskColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTaskColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTaskHoverColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTaskHoverColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTaskTextColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTaskTextColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetNoteColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setNoteColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetPausedTimerCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor(colorNumbersForRGB);
            ThemeLoader.setPausedTimerColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency1Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency1(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency2Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency2(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency3Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency3(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency4Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency4(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency5Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency5(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency1ListCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency1List(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency2ListCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency2List(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency3ListCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+"rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency3List(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency4ListCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency4List(color);
            activate();
        } else if (command.matches(commandHelper.getSetUrgency5ListCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setUrgency5List(color);
            activate();
        } else if (command.matches(commandHelper.getSetDifficulty1Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDifficulty1(color);
            activate();
        } else if (command.matches(commandHelper.getSetDifficulty2Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDifficulty2(color);
            activate();
        } else if (command.matches(commandHelper.getSetDifficulty3Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDifficulty3(color);
            activate();
        } else if (command.matches(commandHelper.getSetDifficulty4Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDifficulty4(color);
            activate();
        } else if (command.matches(commandHelper.getSetDifficulty5Command())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setDifficulty5(color);
            activate();
        } else if (command.matches(commandHelper.getSetTaskCompletedIconColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTaskCompletedIconColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTaskUrgentIconColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTaskUrgentIconColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTaskUrgentPassedCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTaskUrgentPassed(color);
            activate();
        } else if (command.matches(commandHelper.getSetConsoleColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setConsoleColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetConsoleTextColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setConsoleTextColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTimerOnBreakColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTimerOnBreakColor(color);
            activate();
        } else if (command.matches(commandHelper.getSetTimerOnPrepColorCommand())) {
            String colorNumbersForRGB = command.substring(command.indexOf("("), command.indexOf(")")+1);
            Color color = ThemeLoader.parseColor("rgb"+colorNumbersForRGB);
            ThemeLoader.setTimerOnPrepColor(color);
            activate();
        } else {
            commandFound = false;
        }

    }
}
