package UserInterface.MainMenu;

import AppLogic.EventHandler;
import UserInterface.MainMenu.CLI.PANEL_cli;
import UserInterface.MainMenu.REMINDER.PANEL_reminder;
import UserInterface.MainMenu.SubPanels.*;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;

public class PANEL_mainmenu extends JPanel {
    private final PANEL_cli panel_cli = new PANEL_cli();
    private final PANEL_taskinfo panel_taskinfo = new PANEL_taskinfo();
    private final PANEL_clock panel_clock = new PANEL_clock() ;
    private final PANEL_noteinfo panel_noteinfo = new PANEL_noteinfo();
    private final PANEL_reminder panel_reminder = new PANEL_reminder();

    public PANEL_mainmenu() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);



        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
        this.add(panel_cli);
        this.add(panel_taskinfo);
        this.add(panel_clock);
        this.add(panel_noteinfo);
        this.add(panel_reminder);


    }
    public void setHEIGHTandWIDTH ( int width, int height){
        int textBarHeight = 30;
        panel_clock.setBounds(20, 20, width-40, (height- textBarHeight)/2);
        panel_cli.setBounds(0, height - textBarHeight, width, textBarHeight);
        panel_taskinfo.setBounds(20, 20, width-40, (height- textBarHeight)/2);
        panel_noteinfo.setBounds(20,height-panel_taskinfo.getHeight(), width-40,height- textBarHeight -panel_taskinfo.getHeight()-20);
        panel_reminder.setBounds(20,height-panel_taskinfo.getHeight(), width-40,height- textBarHeight -panel_taskinfo.getHeight()-20);

        panel_cli.setHEIGHTandWIDTH(textBarHeight,width);
        panel_taskinfo.setHEIGHTandWIDTH((height- textBarHeight)/2,width-40);
        panel_clock.setHEIGHTandWIDTH((height- textBarHeight)/2,width-40);
        panel_noteinfo.setHEIGHTandWIDTH(height- textBarHeight -panel_taskinfo.getHeight()-20,width-40);
        panel_reminder.setHEIGHTandWIDTH(height- textBarHeight -panel_taskinfo.getHeight()-20,width-40);


    }

    public PANEL_reminder getPanel_reminder() {
        return panel_reminder;
    }
    public PANEL_cli getPanel_form() {
        return panel_cli;
    }
    public  PANEL_taskinfo getPanel_taskinfo() {
        return panel_taskinfo;
    }
    public  PANEL_clock getPanel_clock() {
        return panel_clock;
    }
    public   PANEL_noteinfo getPanel_noteinfo() {
        return panel_noteinfo;
    }
    public void setEventHandler(EventHandler eventHandler) {
        panel_clock.setEventHandler(eventHandler);
        panel_cli.setEventHandler(eventHandler);
        panel_reminder.setEventHandler(eventHandler);
    }
}