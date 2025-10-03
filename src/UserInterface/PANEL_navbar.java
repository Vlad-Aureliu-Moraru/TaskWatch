package UserInterface;

import Handlers.EventHandler;
import ConfigRelated.FontLoader;
import ConfigRelated.ThemeLoader;
import UserInterface.PanelListElements.ListStages;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_navbar extends JPanel {
    private final JLabel currentPATH = new JLabel("\uF441 ");

    private final JLabel statusDisplay = new JLabel("");
    private final int clockStage  = 0 ; //? 0- clock working | 1 timer working | 2 timer paused ...
    private EventHandler eventHandler;
    private Timer timer;

    public PANEL_navbar() {
        this.setBackground(ThemeLoader.getSecondaryColor());
        this.setLayout(null);
        currentPATH.setForeground(ThemeLoader.getSecndAccent());
        statusDisplay.setForeground(ThemeLoader.getSecndAccent());
        statusDisplay.setFont(FontLoader.getTerminalFont().deriveFont(Font.PLAIN, 14));
        currentPATH.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 17));
        statusDisplay.setForeground(ThemeLoader.getSecondaryGreen());

       Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
       Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getSecndAccent(), 1);
       Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
       this.setBorder(compoundBorder);

        this.add(currentPATH);
        this.add(statusDisplay);
        timer = new Timer(2000, e -> {
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
        if (eventHandler.getPanelList().getStage()== ListStages.NOTE_CLICKED){
            eventHandler.getPanelList().setStage(ListStages.TASK_MENU);
            eventHandler.getPanelList().setNoteSelected(false);
        } else if (eventHandler.getPanelList().getStage()==ListStages.TASK_MENU) {
            if (!reloading){
                eventHandler.getPanelList().setStage(ListStages.DIRECTORY_MENU);
            }else{
                eventHandler.getPanelList().reloadDirs();
            }
        }
    }
    public void setClockWorkingStatus(){
       statusDisplay.setText("\uDB82\uDD54  :: clock");
        statusDisplay.setForeground(ThemeLoader.getSecondaryGreen());
    }
    public void setTimerWorkingStatus(){
       statusDisplay.setText("\uDB84\uDCD0  :: timer");
       statusDisplay.setForeground(ThemeLoader.getSecondaryGreen());
    }
    public void setTimerPausedStatus(){
       statusDisplay.setText("\uF28B  :: paused");
       statusDisplay.setForeground(ThemeLoader.getPausedTimerColor());
    }
    public void displayTempMessage(String message,boolean error){
        statusDisplay.setText(error?"Error//"+message:message);
        statusDisplay.setForeground(error?ThemeLoader.getUrgency5(): ThemeLoader.getFirstAccent());
        timer.start();
    }
    public void setPreparingStatus(){
        statusDisplay.setText("\uDB80\uDCBB  :: preparing");
        statusDisplay.setForeground(ThemeLoader.getTimerOnPrepColor());
    }
    public void setBreakStatus(){
        statusDisplay.setText("\uDB86\uDEEA  :: break");
        statusDisplay.setForeground(ThemeLoader.getTimerOnBreakColor());
    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void refreshComponents(){
        this.revalidate();
        this.repaint();
        for(Component component:this.getComponents()){
            component.repaint();
            component.revalidate();
        }
    }
}
