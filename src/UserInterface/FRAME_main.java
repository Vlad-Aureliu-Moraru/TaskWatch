package UserInterface;

import UserInterface.MainMenu.PANEL_mainmenu;
import UserInterface.NavBar.PANEL_navbar;
import UserInterface.TaskRelated.PANEL_tasklist;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FRAME_main extends JFrame {
    //?addons
    private int WIDTH = 1000;
    private int HEIGHT= 500;
    private int tasklistWIDTH = (WIDTH/2)-60;
    private int navbarHEIGHT = HEIGHT/10;
    private PANEL_tasklist tasklist= new PANEL_tasklist();
    private PANEL_navbar navbar = new PANEL_navbar();
    private PANEL_mainmenu mainmenu = new PANEL_mainmenu();
    public FRAME_main() {
        this.setTitle("PRODUCTIVITY-APP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        mainmenu.setBounds(0,navbarHEIGHT,WIDTH-tasklistWIDTH,HEIGHT-navbarHEIGHT);
        this.add(mainmenu);
        navbar.setBounds(0,0,WIDTH,navbarHEIGHT);
        this.add(navbar);
        tasklist.setBounds(WIDTH-tasklistWIDTH, navbarHEIGHT,tasklistWIDTH,HEIGHT-navbarHEIGHT);
        tasklist.setHEIGHTandWIDTH(tasklistWIDTH,HEIGHT-navbarHEIGHT);
        this.add(tasklist);

//!todo add refreshing stuff so it works for all screens and the refreshing should be done automatically preferabbly
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

        System.out.println("Updating components to " + currentWIDTH + "x" + currentHEIGHT);

        mainmenu.setBounds(0, navbarHEIGHT, currentWIDTH - tasklistWIDTH, currentHEIGHT - navbarHEIGHT);
        navbar.setBounds(0, 0, currentWIDTH, navbarHEIGHT);
        tasklist.setBounds(currentWIDTH - tasklistWIDTH, navbarHEIGHT, tasklistWIDTH, currentHEIGHT - navbarHEIGHT);
        tasklist.setHEIGHTandWIDTH(tasklistWIDTH,HEIGHT-navbarHEIGHT);

        System.out.println("MAINMEUN :"+mainmenu.getHeight()+"x"+mainmenu.getWidth());
        System.out.println("NAVBAR:"+navbar.getHeight()+"x"+navbar.getWidth());
        System.out.println("TASKLIST:"+tasklist.getHeight()+"x"+tasklist.getWidth());


        this.revalidate();
        this.repaint();
    }
}
