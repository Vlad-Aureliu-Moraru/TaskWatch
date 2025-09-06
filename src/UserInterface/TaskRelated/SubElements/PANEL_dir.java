package UserInterface.TaskRelated.SubElements;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_dir extends JPanel {
    private JLabel titleLabel =  new JLabel();
    private EventHandler eventHandler = new EventHandler();
    private Directory directory;

    public PANEL_dir(Directory directory) {
        this.directory = directory;
        this.setBackground(Color.gray);
        this.setLayout(null);
        titleLabel.setText(directory.getName());


        titleLabel.setBounds(0,0,100,30);
        this.add(titleLabel);
    }
    public void setTitleLabel(String title){
        titleLabel.setText(title);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(directory.getName()+"/");
                eventHandler.setCurrentDirectory(directory);
                eventHandler.getFileHandler().getTaskListFromFile();
                eventHandler.getPanelList().loadCurrentDirTasks();
            }
        });
    }
}
