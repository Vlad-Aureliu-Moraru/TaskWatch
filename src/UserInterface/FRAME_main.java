package UserInterface;

import AppLogic.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class FRAME_main extends JFrame {
    private final int HEIGHT= 500;
    private final PANEL_navbar navbar = new PANEL_navbar() ;
    private final PANEL_list tasklist  = new PANEL_list() ;
    private final PANEL_mainmenu mainmenu = new PANEL_mainmenu() ;


    public FRAME_main() {
        this.setTitle("TaskWatch");
        try {
            URL iconURL = getClass().getResource("/assets/TaskWatch.png");
            setIconImage(new ImageIcon(iconURL).getImage());
        }catch(Exception e){
            e.printStackTrace();
        }
        int WIDTH = 1000;
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.requestFocus();
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(':'),"cliOpen");
        inputMap.put(KeyStroke.getKeyStroke('`'),"returnFunc");
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put("cliOpen", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                mainmenu.getPanel_form().activate();
            }
        });
        actionMap.put("returnFunc", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                navbar.returnFunction(false);
                mainmenu.getPanel_taskinfo().deactivate();
                mainmenu.getPanel_clock().activate();
                mainmenu.getPanel_help().setVisible(false);
                mainmenu.getPanel_noteinfo().deactivate();
            }
        });

        //?addons
        EventHandler eventHandler = new EventHandler();
        eventHandler.loadEverythingInMemory();
        eventHandler.updateFinishedStatusForRepeatableTasks();

        eventHandler.setPanelnavbar(navbar);
        eventHandler.setPanelList(tasklist);
        eventHandler.setPanelMainmenu(mainmenu);
        eventHandler.setMainFrame(this);
        navbar.setEventHandler(eventHandler);
        tasklist.setEventHandler(eventHandler);
        mainmenu.setEventHandler(eventHandler);
        mainmenu.getPanel_clock().startClockTimer();


        updateAllComponents();


        this.add(mainmenu,BorderLayout.WEST);
        this.add(navbar, BorderLayout.NORTH);
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
        int currentHEIGHT =this.getSize().height;
        int navbarHEIGHT = currentHEIGHT / 10;
        int tasklistWIDTH = (currentWIDTH / 2) - 150;
        if (navbarHEIGHT>50){
            navbarHEIGHT = 50;
        }


        if (navbarHEIGHT<50){
            navbarHEIGHT = 50;
        }
//        navbar.setBounds(0, 0, currentWIDTH, navbarHEIGHT);
        navbar.setPreferredSize(new Dimension(currentWIDTH,navbarHEIGHT));
        navbar.setHEIGHTandWIDTH(currentWIDTH);

//        mainmenu.setBounds(0, navbarHEIGHT, currentWIDTH - tasklistWIDTH, currentHEIGHT - navbarHEIGHT);
        mainmenu.setPreferredSize(new Dimension(currentWIDTH-tasklistWIDTH,currentHEIGHT));
        mainmenu.setHEIGHTandWIDTH(currentWIDTH-tasklistWIDTH,currentHEIGHT-navbarHEIGHT);

//        tasklist.setBounds(currentWIDTH - tasklistWIDTH, navbarHEIGHT, tasklistWIDTH, currentHEIGHT - navbarHEIGHT);
        tasklist.setHEIGHTandWIDTH(tasklistWIDTH,HEIGHT-navbarHEIGHT);

        this.revalidate();
        this.repaint();
    }
    public void refreshComponents(){
        this.revalidate();
        this.repaint();
        navbar.refreshComponents();
        mainmenu.refreshComponents();
        tasklist.refreshComponents();
    }
}
