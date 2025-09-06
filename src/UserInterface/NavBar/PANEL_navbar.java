package UserInterface.NavBar;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.NotesLogic.Note;
import AppLogic.TaskLogic.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PANEL_navbar extends JPanel {
    private JLabel currentPATH = new JLabel("~");
    private JButton returnButton = new JButton("<-");
    private JButton addButton = new JButton("+");
    private JButton test = new JButton("Test");

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
        test.setBounds(getHeight()/3,10,100,30);

        this.add(currentPATH);
        this.add(returnButton);
        this.add(test);
        this.add(addButton);
        returnButton.addActionListener(actionEvent -> {
            if (eventHandler.getPanelList().getStage()==2){
                eventHandler.getPanelList().setStage(1);
            } else if (eventHandler.getPanelList().getStage()==1) {
                eventHandler.getPanelList().setStage(0);
            }
        });
        addButton.addActionListener(actionEvent -> {
                eventHandler.getPanelMainmenu().getPanel_form().activate();
        });
        test.addActionListener(actionEvent -> {
            if (eventHandler.getCurrentTask() != null) {
                System.out.println("adding note");
                Note note = new Note();
                note.setDate("2025%03%10");
                note.setNote("DO SOMETHING");
                eventHandler.addNote(note);
                return;
            }
            if (eventHandler.getCurrentDirectory() != null) {
                System.out.println("adding task");
                Task currentTask = new Task();
                currentTask.setName("TESTING");
                currentTask.setTimeDedicated("20:10");
                eventHandler.addTask(currentTask);
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
        test.setBounds((WIDTH-250),10,50,40);
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
