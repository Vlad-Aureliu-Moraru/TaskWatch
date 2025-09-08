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
    private int HEIGHT;
    private int WIDTH;
    private boolean active = false;
    private int stage =0; //? 0 - cmd ; 1 - dir content ; 2 - task content ; 3 note content
    private int step = 1;

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
        this.HEIGHT=height;
        this.WIDTH=width;
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
    private void loadDirrectoryInput(){
        System.out.println("loading dirrectory input");
        stage = 1;
        commandField.setText("Directory_Name:");
    }
    private void loadTaskInput(int step){
        stage = 2;
        if(step==1){
            commandField.setText("Task_Name:");
            addedTask = new Task();
        }else if(step==2){
            commandField.setText("Task_Description:");
        } else if (step==3) {
            commandField.setText("Task_Priority:");
        }else if(step==4){
            commandField.setText("Task_Completion_Date:");
        }
        else if(step==5){
            commandField.setText("Task_Completion_Time:");
        }
        else if(step==6){
            commandField.setText("isRepeatable:");
        }
    }
    private void loadNoteInput(){
        stage = 3;
        commandField.setText("Note:");
    }
    //!ADD ERROR HANDLING AND CANCEL METHOD and removing method
    public void setEventHandler(EventHandler eventHandler) {
        System.out.println(eventHandler +" from other child");
        this.eventHandler = eventHandler;
        commandField.addActionListener(actionEvent -> {
            String command = commandField.getText();
            if (command.matches(commandHelper.getAddCommand())){
                if (eventHandler.getPanelList().getStage()==0){
                    loadDirrectoryInput();
                } else if (eventHandler.getPanelList().getStage()==1 ) {
                    loadTaskInput(step);
                }
                else if (eventHandler.getPanelList().getStage()==2 ) {
                    loadNoteInput();
                }

            }
            else if (command.matches(commandHelper.getCancelCommand())){
                activate();
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
            else if (command.matches(commandHelper.getNoteRegEx()) && stage==3){
                Note note = new Note();
                note.setNote(command.substring(command.indexOf(":")+1));

                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
                String formattedDateTime = now.format(formatter);
                note.setDate(formattedDateTime);
                eventHandler.addNote(note);
                activate();
            }
            else if (command.matches(commandHelper.getDirectoryRegEx()) && stage == 1) {
                if (command.substring(command.indexOf(":")+1).isEmpty()){
                    eventHandler.getPanelnavbar().displayErrorMessage("Directory Name must not be empty");
                }else{
                    eventHandler.addDirectory(new Directory(command.substring(command.indexOf(":")+1)));
                    activate();
                }
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
            else if (command.matches(commandHelper.getTaskNameRegEx()) && stage == 2) {
                if (command.substring(command.indexOf(":")+1).isEmpty()){
                    eventHandler.getPanelnavbar().displayErrorMessage("Task Name must not be empty");
                }else{
                    addedTask.setName(command.substring(command.indexOf(":")+1));
                    step++;
                    loadTaskInput(step);
                }
            }
            else if(command.matches(commandHelper.getTaskRepeatableRegEx()) &&stage==2 ) {
                String value=  command.substring(command.indexOf(":")+1);
                if (value.isEmpty()) {
                    step = 1;
                    eventHandler.addTask(addedTask);
                    activate();
                }else{
                    Boolean repeatable = Boolean.parseBoolean(value);
                    addedTask.setRepeatable(repeatable);
                    step = 1;
                    eventHandler.addTask(addedTask);
                    activate();
                }
            }
            else if (command.matches(commandHelper.getTaskDescriptionRegEx()) && stage == 2) {
                addedTask.setDescription(command.substring(command.indexOf(":")+1));
                step++;
                loadTaskInput(step);
            }
            else if ((command.matches(commandHelper.getTaskPriorityRegEx())) && stage == 2) {
                String value=  command.substring(command.indexOf(":")+1);
                if (value.isEmpty()) {
                    step++;
                    loadTaskInput(step);
                }else if (Integer.parseInt(value)>1&&Integer.parseInt(value)<5){
                int Urgency =  Integer.parseInt(command.substring(command.indexOf(":")+1));
                addedTask.setUrgency(Urgency);
                step++;
                loadTaskInput(step);
                }else{
                    eventHandler.getPanelnavbar().displayErrorMessage("Priority must be between 1 and 5");
                }
            }
            else if ((command.matches(commandHelper.getTaskCompletionDateRegEx())) && stage == 2) {
                String value=  command.substring(command.indexOf(":")+1);
                if (value.isEmpty()) {
                    step++;
                    loadTaskInput(step);}else{
                    addedTask.setDeadline(command.substring(command.indexOf(":")+1));
                    step++;
                    loadTaskInput(step);
                }
            }
            else if (command.matches(commandHelper.getTaskCompletionTimeRegEx()) && stage == 2) {
                String  value=  command.substring(command.indexOf(":")+1);
                if (value.isEmpty()) {
                    step++;
                    loadTaskInput(step);
                }else{
                    addedTask.setTimeDedicated(Integer.parseInt(command.substring(command.indexOf(":")+1)));
                    step++;
                    loadTaskInput(step);
                }
            }
            else{
                System.out.println("Command not recognized.");
                eventHandler.getPanelnavbar().displayErrorMessage("Command not recognized.");
//                activate();
            }
        });
    }
}
