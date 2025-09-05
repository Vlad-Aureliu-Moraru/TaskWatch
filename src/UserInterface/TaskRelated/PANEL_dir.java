package UserInterface.TaskRelated;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PANEL_dir extends JPanel {
    private JLabel titleLabel =  new JLabel();
    private JButton test = new JButton();
    private EventHandler eventHandler = new EventHandler();
    private Directory directory;

    public PANEL_dir(Directory directory) {
        this.directory = directory;
        this.setBackground(Color.gray);
        this.setLayout(null);
        titleLabel.setText(directory.getName());

        test.setText("V");
        test.setBounds(200, 10, 100, 30);
        this.add(test);

        titleLabel.setBounds(0,0,100,30);
        this.add(titleLabel);
    }
    public void setTitleLabel(String title){
        titleLabel.setText(title);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(directory.getName()+"/");
                eventHandler.setCurrentDirectory(directory);
            }
        });
    }
}
