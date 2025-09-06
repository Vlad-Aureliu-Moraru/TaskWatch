package UserInterface.MainMenu;

import AppLogic.EventHandler;
import UserInterface.MainMenu.SubPanels.PANEL_cli;
import UserInterface.MainMenu.SubPanels.PANEL_taskinfo;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PANEL_mainmenu extends JPanel {
    private PANEL_cli panel_cli = new PANEL_cli();
    public PANEL_taskinfo panel_taskinfo = new PANEL_taskinfo();

    private int HEIGHT;
    private int WIDTH;
    private int textBarHeight = 30;

    private ColorTheme colorTheme = new ColorTheme();

    public PANEL_mainmenu(EventHandler eventHandler) {
        this.setBackground(colorTheme.main_color);
        this.setLayout(null);

        panel_taskinfo.setBounds(10, 10,300,300);

        panel_cli.setBounds(10, 10, 400, 400);
        panel_cli.setEventHandler(eventHandler);


        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(colorTheme.secnd_accent, 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
        this.add(panel_cli);
        this.add(panel_taskinfo);


    }
    public void setHEIGHTandWIDTH ( int width, int height){
        this.HEIGHT = height;
        this.WIDTH = width;
        panel_taskinfo.setBounds(20, 20, width-40, (height-textBarHeight)/2);

        panel_cli.setBounds(0, HEIGHT-textBarHeight, width,textBarHeight);
        panel_cli.setHEIGHTandWIDTH(textBarHeight,width);


    }

    public PANEL_cli getPanel_form() {
        return panel_cli;
    }
}