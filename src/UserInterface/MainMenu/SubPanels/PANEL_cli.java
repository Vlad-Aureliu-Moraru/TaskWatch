package UserInterface.MainMenu.SubPanels;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.NotesLogic.Note;
import AppLogic.TaskLogic.Task;
import UserInterface.MainMenu.CommandHelper;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PANEL_cli extends JPanel {
    private JTextField commandField= new JTextField();
    private EventHandler eventHandler;
    private boolean active = false;
    private int stage =0; //? 0 - cmd ; 1 - dir content ; 2 - task content ; 3 note content this prevents the user to manually write DirName:test for exemple
    private int step = 1;
    private boolean commandFound ;
    private boolean editing;

    private Task addedTask;

    private CommandHelper commandHelper = new CommandHelper();


    public PANEL_cli() {
        setBackground(ColorTheme.getConsoleColor());
        this.setLayout(null);
        this.add(commandField);
        commandField.setEditable(true);
        commandField.setOpaque(false);
        setVisible(false);
        commandField.setForeground(ColorTheme.getConsoleTextColor());
        commandField.setFont(new Font("ARIAL",Font.PLAIN, 14));
        commandField.setBorder(null);
        commandField.setBounds(0,0,20,20);
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public void setStep(int step) {}
    public void setHEIGHTandWIDTH(int height,int width){
        commandField.setBounds(2,0,width,height);
    }
    public void activate(){
        if(active){
            System.out.println("deactivating");
            stage =0;
            step=1;
            active=false;
            this.setVisible(false);
            commandField.setText("");
        }else {
            System.out.println("activating");
            active=true;
            this.setVisible(true);
            commandField.setText(":");
            commandField.requestFocus();
        }
    }
    private void loadDirrectoryInput(String dirName){
        System.out.println("loading dirrectory input");
        stage = 1;
        commandField.setText("Directory_Name:"+dirName);
    }
    private void loadTaskInput(int step,boolean editing){
        stage = 2;
        if(step==1){
            if(editing){
                commandField.setText("Task_Name:"+eventHandler.getCurrentTask().getName());
            }else{
                commandField.setText("Task_Name:");
                addedTask = new Task();
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
                    if (!content.trim().equalsIgnoreCase("null")) {
                        commandField.setText("Task_Completion_Date:" + eventHandler.getCurrentTask().getDeadline());
                    } else {

                        commandField.setText("Task_Completion_Date:" + "");
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
        stage = 3;
        commandField.setText("Note:"+note);
    }
    //!ADD EDITTING AND FINISHING
    public void setEventHandler(EventHandler eventHandler) {
        System.out.println(eventHandler +" from other child");
        this.eventHandler = eventHandler;
        commandField.addActionListener(actionEvent -> {
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
            if (!commandFound) {
                System.out.println("Command not recognized.");
                eventHandler.getPanelnavbar().displayErrorMessage("Command not recognized.");
            }

        });
    }
    private void HandleBasicCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getAddCommand())){
            editing=false;
            if (eventHandler.getPanelList().getStage()==0){
                loadDirrectoryInput("");
            } else if (eventHandler.getPanelList().getStage()==1 ) {
                loadTaskInput(step,false);
            }
            else if (eventHandler.getPanelList().getStage()==2 ) {
                loadNoteInput("");
            }

        }
        else if (command.matches(commandHelper.getCancelCommand())){
            activate();
            step = 1;
            editing = false;
        }
        else if (command.matches(commandHelper.getEditCommand())){
            editing = true;
            if (eventHandler.getPanelList().getStage()==1 ){
                loadDirrectoryInput(eventHandler.getCurrentDirectory().getName());
            }
            else if (eventHandler.getPanelList().isNoteSelected()){
                loadNoteInput(eventHandler.getCurrentNote().getNote());
            }else if (eventHandler.getPanelList().getStage()==2){
                loadTaskInput(step,editing);
            }
        }
        else if (command.matches(commandHelper.getSortByUrgencyCommand())&&eventHandler.getPanelList().getStage()==1) {
            String value = command.substring(command.indexOf("(")+1, command.indexOf(")"));
            if (value.equals("a")){
                eventHandler.getPanelList().sortTasksByUrgency(true);
            }else{
                eventHandler.getPanelList().sortTasksByUrgency(false);
            }
            activate();
        }
        else if (command.matches(commandHelper.getRemoveCommand())) {
            System.out.println(eventHandler.getPanelList().getStage());
            if (eventHandler.getPanelList().getStage()==1) {
                eventHandler.getFileHandler().removeDirectoryFromFiles();
                eventHandler.getPanelnavbar().returnFunction();
                activate();
            }else if (eventHandler.getPanelList().getStage()==2 && !eventHandler.getPanelList().isNoteSelected()) {
                System.out.println("trying to remove task");
                eventHandler.getFileHandler().removeTaskFromFiles();
                eventHandler.getPanelMainmenu().getPanel_taskinfo().deactivate();
                eventHandler.getPanelMainmenu().getPanel_clock().activate();
                eventHandler.getPanelMainmenu().getPanel_noteinfo().deactivate();
                eventHandler.getPanelnavbar().returnFunction();
                activate();
            }
            else if (eventHandler.getPanelList().isNoteSelected()) {
                System.out.println("trying to remove note");
                eventHandler.getFileHandler().removeNotesFromFile();
                eventHandler.getPanelMainmenu().getPanel_taskinfo().deactivate();
                eventHandler.getPanelMainmenu().getPanel_clock().activate();
                eventHandler.getPanelMainmenu().getPanel_noteinfo().deactivate();
                eventHandler.getPanelnavbar().returnFunction();
                activate();
            }
        }
        else if (command.matches(commandHelper.getStartTimerCommand())) {
            int value = Integer.parseInt(command.substring(command.indexOf("(")+1,command.indexOf(")")));
            if (value<1){
                eventHandler.getPanelnavbar().displayErrorMessage("Enter a value >=1");
            }else{
                eventHandler.getPanelMainmenu().getPanel_clock().startTaskTimer(value);
                activate();
            }
        }
        else if (command.matches(commandHelper.getStopTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().stopTaskTimerandStartClockTimer();
            activate();

        }
        else if(command.matches(commandHelper.getStartSelectedTaskTimer()) && eventHandler.getPanelList().getStage()>=2){
            int value = eventHandler.getCurrentTask().getTimeDedicated();
            eventHandler.getPanelMainmenu().getPanel_clock().startTaskTimer(value);
            activate();
        }
        else if (command.matches(commandHelper.getPauseTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().pauseOrunpauseTaskTimer();
            activate();

        }
        else if (command.matches(commandHelper.getRestartTimerCommand())) {
            eventHandler.getPanelMainmenu().getPanel_clock().resetTimer();
            activate();
        }
        else if (command.matches(commandHelper.getSortByDifficultyCommand())&&eventHandler.getPanelList().getStage()==1) {
            String value = command.substring(command.indexOf("(")+1, command.indexOf(")"));
            if (value.equals("a")){
                eventHandler.getPanelList().sortByDifficulty(true);
            }else{
                eventHandler.getPanelList().sortByDifficulty(false);
            }
            activate();
        }
        else if (command.matches(commandHelper.getFinishTaskCommand())&&eventHandler.getPanelList().getStage()==2) {
            eventHandler.getCurrentTask().setFinished(true);
            eventHandler.getFileHandler().saveTaskToFile();
            activate();
            eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());

        }
        else if (command.matches(commandHelper.getShowFinishedTasks())&&eventHandler.getPanelList().getStage()==1) {
            eventHandler.getPanelList().switchShowingFinished();
            eventHandler.getPanelList().loadCurrentTasks();
            activate();
        }

        else {
            commandFound=false;
        }

    }
    private void HandleDirRelatedCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getDirectoryNameRegEx()) && stage == 1) {
            String commandParameeter = command.substring(command.indexOf(":")+1);
            if (commandParameeter.isEmpty() || command.contains(";")){
                eventHandler.getPanelnavbar().displayErrorMessage("INVALID DIR NAME");
            }else if (!editing){
                eventHandler.addDirectory(new Directory(commandParameeter));
                activate();
            } else if (editing) {
                eventHandler.getFileHandler().renameCurrentDirectory(commandParameeter);
                eventHandler.getCurrentDirectory().setName(commandParameeter);
                eventHandler.getFileHandler().saveDirectoryListToFile();
                eventHandler.getPanelnavbar().returnFunction();
                editing = false;
                activate();
            }
        }else{
                commandFound=false;
            }
    }
    private void HandleNoteRelatedCommands(String command){
        commandFound=true;
        if (command.matches(commandHelper.getNoteRegEx()) && stage==3){
            if (command.contains(";")){
                eventHandler.getPanelnavbar().displayErrorMessage("INVALID NOTE MESSAGE");
            }else if (!editing){
                Note note = new Note();
                note.setNote(command.substring(command.indexOf(":")+1));

                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
                String formattedDateTime = now.format(formatter);
                note.setDate(formattedDateTime);
                eventHandler.addNote(note);
                activate();
            }else if (editing){
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
        commandFound=true;
        if (command.matches(commandHelper.getTaskNameRegEx()) && stage == 2) {
            String commandParameeter = command.substring(command.indexOf(":")+1);
            if (commandParameeter.isEmpty() || commandParameeter.contains(";")) {
                eventHandler.getPanelnavbar().displayErrorMessage("INVALID TASK NAME");
            }else {
                if (!editing && !eventHandler.getCurrentDirectory().taskNameExists(commandParameeter)) {
                    addedTask.setName(commandParameeter);
                }else if (editing){
                    eventHandler.getFileHandler().renameCurrentTask(commandParameeter);
                    eventHandler.getCurrentTask().setName(commandParameeter);
                }
                step++;
                loadTaskInput(step,editing);
            }
        }
        else if(command.matches(commandHelper.getTaskRepeatableRegEx()) &&stage==2 ) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty() || value.equals("false")) {
                if (!editing) {
                    eventHandler.addTask(addedTask);
                    step=1;
                    activate();
                }else{
                    step = 1;
                    eventHandler.getFileHandler().saveTaskToFile();
                    eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());
                    eventHandler.getPanelnavbar().setCurrentPATH(eventHandler.getCurrentDirectory().getName()+"/"+eventHandler.getCurrentTask().getName());
                    activate();
                    editing = false;
                }
            }else if (!editing){
                Boolean repeatable = Boolean.parseBoolean(value);
                addedTask.setRepeatable(repeatable);
                step++;
                loadTaskInput(step,editing);
            }else if (editing){
                Boolean repeatable = Boolean.parseBoolean(value);
                eventHandler.getCurrentTask().setRepeatable(repeatable);
                step++;
                loadTaskInput(step,editing);

            }
        }
        else if (command.matches(commandHelper.getTaskDescriptionRegEx()) && stage == 2) {
            if (command.substring(command.indexOf(":")+1).contains(";")) {
                eventHandler.getPanelnavbar().displayErrorMessage("INVALID TASK DESCRIPTION");
            }else{
                if (!editing){
                    addedTask.setDescription(command.substring(command.indexOf(":")+1));
                }else{
                    eventHandler.getCurrentTask().setDescription(command.substring(command.indexOf(":")+1));
                }
                step++;
                loadTaskInput(step,editing);
            }
        }
        else if ((command.matches(commandHelper.getTaskPriorityRegEx())) && stage == 2) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty()) {
                step++;
                loadTaskInput(step,editing);
            }else if (Integer.parseInt(value)>=1&&Integer.parseInt(value)<=5){
                int Urgency =  Integer.parseInt(command.substring(command.indexOf(":")+1));
                if (!editing){
                    addedTask.setUrgency(Urgency);
                }else {
                    eventHandler.getCurrentTask().setUrgency(Urgency);
                }
                step++;
                loadTaskInput(step,editing);
            }else{
                eventHandler.getPanelnavbar().displayErrorMessage("Priority must be between 1 and 5");
            }
        }
        else if ((command.matches(commandHelper.getTaskCompletionDateRegEx())) && stage == 2) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty()) {
                step++;
                loadTaskInput(step,editing);}
            else{
                if (!editing){
                    addedTask.setDeadline(command.substring(command.indexOf(":")+1));
                }else{
                    eventHandler.getCurrentTask().setDeadline(command.substring(command.indexOf(":")+1));
                }
                step++;
                loadTaskInput(step,editing);
            }
        }
        else if (command.matches(commandHelper.getTaskCompletionTimeRegEx()) && stage == 2) {
            String  value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty()) {
                step++;
                loadTaskInput(step,editing);
            }else{
                if (!editing){
                    addedTask.setTimeDedicated(Integer.parseInt(command.substring(command.indexOf(":")+1)));
                }else{
                    eventHandler.getCurrentTask().setTimeDedicated(Integer.parseInt(command.substring(command.indexOf(":")+1)));
                }
                step++;
                loadTaskInput(step,editing);
            }
        }
        else if (command.matches(commandHelper.getTaskDifficultyRegEx())&& stage==2) {
            String value=  command.substring(command.indexOf(":")+1);
            if (value.isEmpty()) {
                step++;
                loadTaskInput(step,editing);
            }else if (Integer.parseInt(value)>=1&&Integer.parseInt(value)<=5){
                int Urgency =  Integer.parseInt(command.substring(command.indexOf(":")+1));
                if (!editing){
                    addedTask.setDifficulty(Urgency);
                }else {
                    eventHandler.getCurrentTask().setDifficulty(Urgency);
                }
                step++;
                loadTaskInput(step,editing);
            }else{
                eventHandler.getPanelnavbar().displayErrorMessage("Difficulty must be between 1 and 5");
            }
        }
        else if (command.matches(commandHelper.getTaskRepeatableTypeRegEx())&&stage==2){
            String value=  command.substring(command.indexOf(":")+1);
            if (!value.isEmpty()) {
                if (!editing){
                    addedTask.setRepeatableType(value);
                    eventHandler.addTask(addedTask);
                    step=1;
                    activate();
                }else{
                    eventHandler.getCurrentTask().setRepeatableType(value);
                    step = 1;
                    eventHandler.getFileHandler().saveTaskToFile();
                    eventHandler.getPanelMainmenu().getPanel_taskinfo().updateTaskInfo(eventHandler.getCurrentTask());
                    eventHandler.getPanelnavbar().setCurrentPATH(eventHandler.getCurrentDirectory().getName()+"/"+eventHandler.getCurrentTask().getName());
                    activate();
                    editing = false;
                }
            }
        }
        else{
            commandFound=false;
        }
    }
}
