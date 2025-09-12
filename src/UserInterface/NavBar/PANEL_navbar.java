package UserInterface.NavBar;

import AppLogic.EventHandler;
import AppLogic.FontLoader;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PANEL_navbar extends JPanel {
    private JLabel currentPATH = new JLabel("~");

    private int HEIGHT;
    private int WIDTH;

    private JLabel statusDisplay = new JLabel("");
    private int clockStage  = 0 ; //? 0- clock working | 1 timer working | 2 timer paused ...
    private EventHandler eventHandler;
    private Timer timer;
    private JButton backButton = new JButton("test");

   public PANEL_navbar() {
        this.setBackground(ColorTheme.getSecondary_color());
        this.setLayout(null);
        currentPATH.setForeground(ColorTheme.getSecnd_accent());
        statusDisplay.setForeground(ColorTheme.getSecnd_accent());
        statusDisplay.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 15));
        currentPATH.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 15));
        statusDisplay.setForeground(ColorTheme.getSecondary_green());

       Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
       Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 1);
       Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
       this.setBorder(compoundBorder);

        this.add(currentPATH);
        this.add(statusDisplay);
        timer = new Timer(2000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(clockStage == 0){
                    setClockWorkingStatus();
                } else if (clockStage==1) {
                    setClockWorkingStatus();
                } else if (clockStage == 2) {
                    setTimerPausedStatus();
                }
                timer.stop();
            }

        });
        timer.setRepeats(false);
    }

    public void setHEIGHTandWIDTH(int WIDTH,int HEIGHT){
        this.HEIGHT=HEIGHT;
        this.WIDTH=WIDTH;
        statusDisplay.setBounds(30,10,WIDTH/3,30);
        currentPATH.setBounds((WIDTH/2)-100,10,300,30);
        if (currentPATH.getX()<0){
            currentPATH.setBounds(0,10,100,30);
        }
        this.revalidate();
        this.repaint();
    }
    public void setCurrentPATH(String path){
       currentPATH.setText(path);
    }
    public void returnFunction(boolean reloading) {
        if (eventHandler.getPanelList().getStage()==2){
            eventHandler.getPanelList().setStage(1);
            eventHandler.getPanelList().setNoteSelected(false);
        } else if (eventHandler.getPanelList().getStage()==1) {
            if (!reloading){
                eventHandler.getPanelList().setStage(0);
            }else{
                eventHandler.getPanelList().reloadDirs();
            }
        }
    }
    public void setClockWorkingStatus(){
       statusDisplay.setText("\uDB82\uDD54  :: clock");
        statusDisplay.setForeground(ColorTheme.getSecondary_green());
    }
    public void setTimerWorkingStatus(){
       statusDisplay.setText("\uDB84\uDCD0  :: timer");
       statusDisplay.setForeground(ColorTheme.getSecondary_green());
    }
    public void setTimerPausedStatus(){
       statusDisplay.setText("\uF28B  :: paused");
       statusDisplay.setForeground(ColorTheme.getPausedTimerColor());
    }
    public void displayErrorMessage(String message){
        statusDisplay.setText("Error//"+message);
        statusDisplay.setForeground(ColorTheme.getUrgency5());
        timer.start();
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
