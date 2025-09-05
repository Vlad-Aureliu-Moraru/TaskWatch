package UserInterface.NavBar;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;

import javax.swing.*;
import java.awt.*;

public class PANEL_navbar extends JPanel {
    private JLabel currentPATH = new JLabel("~");
    private JButton addBUTTON = new JButton("+");

    private int HEIGHT;
    private int WIDTH;
    private EventHandler eventHandler;

   public PANEL_navbar(EventHandler eventHandler) {
       this.eventHandler = eventHandler;
        this.setBackground(Color.green);
        this.setLayout(null);
        currentPATH.setBounds(getHeight()/2,10,100,30);
        addBUTTON.setBounds(getHeight()/3,10,100,30);

        this.add(currentPATH);
        this.add(addBUTTON);
        addBUTTON.addActionListener(actionEvent -> {
            System.out.println(eventHandler.getCurrentDirectory());
            Directory newdir = new Directory("DIR3");
            eventHandler.addDirectory(newdir);
            eventHandler.printDirectoryList();
        });

    }

    public void setHEIGHTandWIDTH(int WIDTH,int HEIGHT){
        this.HEIGHT=HEIGHT;
        this.WIDTH=WIDTH;
        currentPATH.setBounds((WIDTH/2)-50,10,100,30);
        addBUTTON.setBounds((WIDTH-50),10,50,40);
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
