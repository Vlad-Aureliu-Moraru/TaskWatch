package UserInterface;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import UserInterface.MainMenu.PANEL_mainmenu;
import UserInterface.NavBar.PANEL_navbar;
import UserInterface.TaskRelated.PANEL_list;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FRAME_main extends JFrame {
    //?addons
    private EventHandler  eventHandler = new EventHandler();
    private int WIDTH = 1000;
    private int HEIGHT= 500;
    private PANEL_list tasklist = new PANEL_list(eventHandler);
    private PANEL_navbar navbar = new PANEL_navbar(eventHandler);
    private PANEL_mainmenu mainmenu = new PANEL_mainmenu(eventHandler);


    public FRAME_main() {
        this.setTitle("PRODUCTIVITY-APP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        eventHandler.setPanelList(tasklist);
        eventHandler.setPanelnavbar(navbar);
        eventHandler.setPanelMainmenu(mainmenu);

        updateAllComponents();

        this.add(mainmenu);
        this.add(navbar);
        this.add(tasklist);
        eventHandler.getFileHandler().checkFileStructure();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateAllComponents();
            }
        });
    }
    public void updateAllComponents() {
        int currentWIDTH = this.getSize().width;
        int currentHEIGHT = this.getSize().height;
        int navbarHEIGHT = currentHEIGHT / 10;
        int tasklistWIDTH = (currentWIDTH / 2) - 60;
        if (navbarHEIGHT>50){
            navbarHEIGHT = 50;
        }


        mainmenu.setBounds(0, navbarHEIGHT, currentWIDTH - tasklistWIDTH, currentHEIGHT - navbarHEIGHT);
        navbar.setBounds(0, 0, currentWIDTH, navbarHEIGHT);
        tasklist.setBounds(currentWIDTH - tasklistWIDTH, navbarHEIGHT, tasklistWIDTH, currentHEIGHT - navbarHEIGHT);
        tasklist.setHEIGHTandWIDTH(tasklistWIDTH,HEIGHT-navbarHEIGHT);
        navbar.setHEIGHTandWIDTH(currentWIDTH,navbarHEIGHT);



        this.revalidate();
        this.repaint();
    }
}
