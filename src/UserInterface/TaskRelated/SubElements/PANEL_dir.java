package UserInterface.TaskRelated.SubElements;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_dir extends JPanel {
    private JLabel titleLabel =  new JLabel();
    private EventHandler eventHandler = new EventHandler();
    private ColorTheme colorTheme= new ColorTheme();
    private Directory directory;

    public PANEL_dir(Directory directory) {
        this.directory = directory;
        this.setBackground(colorTheme.secondary_green);
        this.setLayout(null);
        titleLabel.setText(directory.getName());
        titleLabel.setOpaque(true);
        titleLabel.setBackground(colorTheme.secondary_green);


        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(colorTheme.secnd_accent, 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
        titleLabel.setBounds(0,0,100,30);
        titleLabel.setForeground(colorTheme.secnd_accent);
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
