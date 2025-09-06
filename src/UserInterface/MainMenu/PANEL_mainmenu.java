package UserInterface.MainMenu;

import AppLogic.EventHandler;
import UserInterface.MainMenu.SubPanels.PANEL_form;

import javax.swing.*;
import java.awt.*;

public class PANEL_mainmenu extends JPanel {
    private PANEL_form panel_form = new PANEL_form();
    private int HEIGHT;
    private int WIDTH;
    private int textBarHeight = 30;

    public PANEL_mainmenu(EventHandler eventHandler) {
        this.setBackground(Color.yellow);
        this.setLayout(null);
        panel_form.setBounds(10, 10, 400, 400);
        panel_form.setEventHandler(eventHandler);
        this.add(panel_form);

    }
    public void setHEIGHTandWIDTH ( int width, int height){
        this.HEIGHT = height;
        this.WIDTH = width;
        panel_form.setBounds(0, HEIGHT-textBarHeight, width,textBarHeight);
        panel_form.setHEIGHTandWIDTH(textBarHeight,width);


    }

    public PANEL_form getPanel_form() {
        return panel_form;
    }
}