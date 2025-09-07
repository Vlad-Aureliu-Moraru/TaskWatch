package UserInterface.NavBar;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.NotesLogic.Note;
import AppLogic.TaskLogic.Task;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
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
        this.setBackground(ColorTheme.getSecondary_color());
        this.setLayout(null);
        currentPATH.setBounds(getHeight()/2,10,100,30);
        currentPATH.setForeground(ColorTheme.getSecnd_accent());
        returnButton.setBounds(getHeight()/3,10,100,30);
        addButton.setBounds(getHeight()/3,10,100,30);
        test.setBounds(getHeight()/3,10,100,30);

       Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
       Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 1);
       Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
       this.setBorder(compoundBorder);

        this.add(currentPATH);
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
    public void returnFunction()
    {
        if (eventHandler.getPanelList().getStage()==2){
            eventHandler.getPanelList().setStage(1);
        } else if (eventHandler.getPanelList().getStage()==1) {
            eventHandler.getPanelList().setStage(0);
        }
    }
}
