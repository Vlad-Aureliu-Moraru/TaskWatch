package UserInterface.MainMenu;

import AppLogic.EventHandler;
import UserInterface.MainMenu.SubPanels.PANEL_cli;
import UserInterface.MainMenu.SubPanels.PANEL_clock;
import UserInterface.MainMenu.SubPanels.PANEL_taskinfo;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_mainmenu extends JPanel {
    private PANEL_cli panel_cli = new PANEL_cli();
    private PANEL_taskinfo panel_taskinfo = new PANEL_taskinfo();
    private PANEL_clock panel_clock = new PANEL_clock();

    private int HEIGHT;
    private int WIDTH;
    private int textBarHeight = 30;


    public PANEL_mainmenu(EventHandler eventHandler) {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);


        panel_cli.setEventHandler(eventHandler);


        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
        this.add(panel_cli);
        this.add(panel_taskinfo);
        this.add(panel_clock);


    }
    public void setHEIGHTandWIDTH ( int width, int height){
        this.HEIGHT = height;
        this.WIDTH = width;
        panel_taskinfo.setBounds(20, 20, width-40, (height-textBarHeight)/2);
        panel_clock.setBounds(20, 20, width-40, (height-textBarHeight)/2);
        panel_cli.setBounds(0, HEIGHT-textBarHeight, width,textBarHeight);

        panel_cli.setHEIGHTandWIDTH(textBarHeight,width);
        panel_taskinfo.setHEIGHTandWIDTH((height-textBarHeight)/2,width-40);
        panel_clock.setHEIGHTandWIDTH((height-textBarHeight)/2,width-40);


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
}