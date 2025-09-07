package UserInterface.MainMenu.SubPanels;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.FontLoader;
import AppLogic.TaskLogic.Task;
import UserInterface.MainMenu.CommandHelper;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;

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

    public void setHEIGHTandWIDTH(int height,int width){
        this.HEIGHT=height;
        this.WIDTH=width;
        commandField.setBounds(2,0,width,height);
    }
    public void activate(){
        if(active){
            System.out.println("deactivating");
            stage =0;
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
            commandField.setText("Task_Repeatable:");
        }
    }
    //!ADD ERROR HANDLING AND CANCEL METHOD and removing method
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        commandField.addActionListener(actionEvent -> {
            String command = commandField.getText();
            if (command.equals(commandHelper.getAddCommand())){
                if (eventHandler.getPanelList().getStage()==0){
                    loadDirrectoryInput();
                } else if (eventHandler.getPanelList().getStage()==1 ) {
                    loadTaskInput(step);
                }
//                else if (eventHandler.getPanelList().getStage()==2 ) {
//                    loadTaskInput(1);
//                }

            }if (command.equals(commandHelper.getCancelCommand())){
                activate();
            }

            if (command.matches(commandHelper.getDirectoryRegEx()) && stage == 1) {
                eventHandler.addDirectory(new Directory(command.substring(command.indexOf(":")+1)));
                activate();
            }
            if (command.matches(commandHelper.getTaskNameRegEx()) && stage == 2) {
                addedTask.setName(command.substring(command.indexOf(":")+1));
                    step++;
                    loadTaskInput(step);
            }
            if (command.matches(commandHelper.getTaskDescriptionRegEx()) && stage == 2) {
                addedTask.setDescription(command.substring(command.indexOf(":")+1));
                step++;
                loadTaskInput(step);
            }
            if (command.matches(commandHelper.getTaskPriorityRegEx()) && stage == 2) {
                int Urgency =  Integer.parseInt(command.substring(command.indexOf(":")+1));
                addedTask.setUrgency(Urgency);
                step++;
                loadTaskInput(step);
            }
            if (command.matches(commandHelper.getTaskCompletionDateRegEx()) && stage == 2) {

                addedTask.setDeadline(command.substring(command.indexOf(":")+1));
                step++;
                loadTaskInput(step);
            }
            if (command.matches(commandHelper.getTaskCompletionTimeRegEx()) && stage == 2) {
                addedTask.setTimeDedicated(Integer.parseInt(command.substring(command.indexOf(":")+1)));
                step++;
                loadTaskInput(step);
            }
            if (command.matches(commandHelper.getTaskRepeatableRegEx()) && stage == 2 ) {
                    Boolean repeatable = Boolean.parseBoolean(command.substring(command.indexOf(":") + 1));
                    addedTask.setRepeatable(repeatable);
                    step = 1;
                    eventHandler.addTask(addedTask);
                    activate();
            }

        });
    }
}
