package UserInterface.NavBar;

import AppLogic.EventHandler;
import AppLogic.FontLoader;
import ConfigRelated.ThemeLoader;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_navbar extends JPanel {
    private final JLabel currentPATH = new JLabel("~");

    private final JLabel statusDisplay = new JLabel("");
    private final int clockStage  = 0 ; //? 0- clock working | 1 timer working | 2 timer paused ...
    private EventHandler eventHandler;
    private Timer timer;

    public PANEL_navbar() {
        this.setBackground(ColorTheme.getSecondary_color());
        this.setLayout(null);
        currentPATH.setForeground(ColorTheme.getSecnd_accent());
        statusDisplay.setForeground(ColorTheme.getSecnd_accent());
        statusDisplay.setFont(FontLoader.getTerminalFont().deriveFont(Font.PLAIN, 14));
        currentPATH.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 17));
        statusDisplay.setForeground(ColorTheme.getSecondary_green());

       Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
       Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 1);
       Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
       this.setBorder(compoundBorder);

        this.add(currentPATH);
        this.add(statusDisplay);
        timer = new Timer(2000, _ -> {
            if(clockStage == 0){
                setClockWorkingStatus();
            } else if (clockStage==1) {
                setClockWorkingStatus();
            } else if (clockStage == 2) {
                setTimerPausedStatus();
            }
            timer.stop();
        });
        timer.setRepeats(false);
    }

    public void setHEIGHTandWIDTH(int WIDTH){
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
    public void displayTempMessage(String message,boolean error){
        statusDisplay.setText(error?"Error//"+message:message);
        statusDisplay.setForeground(error?ColorTheme.getUrgency5(): ThemeLoader.getTaskCompletedIconColor());
        timer.start();
    }
    public void setPreparingStatus(){
        statusDisplay.setText("\uDB80\uDCBB  :: preparing");
        statusDisplay.setForeground(ColorTheme.getTimerOnPrepColor());
    }
    public void setBreakStatus(){
        statusDisplay.setText("\uDB86\uDEEA  :: break");
        statusDisplay.setForeground(ColorTheme.getTimerOnBreakColor());
    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
