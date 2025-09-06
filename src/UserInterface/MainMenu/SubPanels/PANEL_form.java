package UserInterface.MainMenu.SubPanels;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;

public class PANEL_form extends JPanel {
    private JTextField commandField= new JTextField();

    private EventHandler eventHandler;
    private int HEIGHT;
    private int WIDTH;
    private boolean active = false;
    private int stage =0; //? 0 - cmd ; 1 - dir content ; 2 - task content ; 3 note content

    public PANEL_form() {
        setBackground(Color.gray);
        this.setLayout(null);
        this.add(commandField);
        commandField.setEditable(true);
        commandField.setOpaque(false);
        setVisible(false);
        commandField.setForeground(Color.white);
        commandField.setBorder(null);
        commandField.setBounds(0,0,20,20);
    }

    public void setHEIGHTandWIDTH(int height,int width){
        this.HEIGHT=height;
        this.WIDTH=width;
        commandField.setBounds(0,0,width,height);
    }
    public void activate(){
        if(active){
            System.out.println("deactivating");
            active=false;
            this.setVisible(false);
            commandField.setText("");
        }else {
            System.out.println("activating");
            active=true;
            this.setVisible(true);
            commandField.requestFocus();
        }
    }
    private void loadDirrectoryInput(){
        System.out.println("loading dirrectory input");
        stage = 1;
        commandField.setText("Directory_Name:");
    }
    private void loadTaskInput(){
        System.out.println("loading task input");
        stage = 2;
        commandField.setText("Task_Name:");
    }


    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        commandField.addActionListener(actionEvent -> {
            String command = commandField.getText();
            if (command.equals(":a")){
                if (eventHandler.getPanelList().getStage()==0){
                    loadDirrectoryInput();
                } else if (eventHandler.getPanelList().getStage()==1) {
                    loadTaskInput();

                }

            } else if (command.matches("^Directory_Name:.*") && stage == 1) {
                eventHandler.addDirectory(new Directory(command.substring(command.indexOf(":")+1)));
                activate();
                stage =0;
            }
        });
    }
}
