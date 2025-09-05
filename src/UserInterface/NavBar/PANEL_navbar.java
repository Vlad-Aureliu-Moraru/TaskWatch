package UserInterface.NavBar;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.TaskLogic.Task;

import javax.swing.*;
import java.awt.*;

public class PANEL_navbar extends JPanel {
    private JLabel currentPATH = new JLabel("~");
    private JButton returnButton = new JButton("<-");
    private JButton addButton = new JButton("+");

    private int HEIGHT;
    private int WIDTH;
    private EventHandler eventHandler;

   public PANEL_navbar(EventHandler eventHandler) {
       this.eventHandler = eventHandler;
        this.setBackground(Color.green);
        this.setLayout(null);
        currentPATH.setBounds(getHeight()/2,10,100,30);
        returnButton.setBounds(getHeight()/3,10,100,30);
        addButton.setBounds(getHeight()/3,10,100,30);

        this.add(currentPATH);
        this.add(returnButton);
        this.add(addButton);
        returnButton.addActionListener(actionEvent -> {
            if (eventHandler.getCurrentDirectory() != null) {
                eventHandler.getPanelList().loadDirs();
                eventHandler.resetCurrentDirectory();
            }
        });
        addButton.addActionListener(actionEvent -> {
            if (eventHandler.getCurrentDirectory() != null) {
                System.out.println("adding task");
                Task currentTask = new Task();
                currentTask.setName("TESTING ADDING");
                currentTask.setTimeDedicated("20:10");
                eventHandler.getCurrentDirectory().addTask(currentTask);
                eventHandler.saveTaskToFile();
                eventHandler.loadcurrentDirectoryTasksToUIList();
            }else{
                System.out.println("Adding Directory");
                Directory dir = new Directory("APACHE");
                eventHandler.addDirectory(dir);
            }
        });

    }

    public void setHEIGHTandWIDTH(int WIDTH,int HEIGHT){
        this.HEIGHT=HEIGHT;
        this.WIDTH=WIDTH;
        currentPATH.setBounds((WIDTH/2)-100,10,300,30);
        returnButton.setBounds((WIDTH-50),10,50,40);
        addButton.setBounds((WIDTH-150),10,50,40);
        if (currentPATH.getX()<0){
            currentPATH.setBounds(0,10,100,30);
        }
        this.revalidate();
        this.repaint();
    }
    public void setCurrentPATH(String path){
       currentPATH.setText(path);
    }

}
