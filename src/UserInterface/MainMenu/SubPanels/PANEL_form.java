package UserInterface.MainMenu.SubPanels;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;

import javax.swing.*;
import java.awt.*;

public class PANEL_form extends JPanel {
    private JLabel name = new JLabel("Name: ");
    private  JButton button = new JButton("Submit");
    private JTextField nameField = new JTextField();

    private EventHandler eventHandler;
    public PANEL_form() {
        setBackground(Color.gray);
        this.setLayout(null);
        name.setBounds(10, 10, 100, 30);
        nameField.setBounds(110, 10, 100, 30);
        button.setBounds(110, 100, 100, 30);
        this.add(name);
        this.add(button);
        this.add(nameField);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        button.addActionListener(actionEvent -> {
            String name = nameField.getText();
            if(name.equals("")){
                System.out.println("THIS IS SUPPOSED TO PRINT AN ERROR FIX IT !! ");
                return;
            }
            if (eventHandler.getCurrentDirectory() != null) {
                System.out.println("THIS IS SUPPOSED TO BE A TASK FIX IT!");
                return;
            }
            else {
                Directory directory = new Directory(name);
                eventHandler.addDirectory(directory);
            }
        });
    }
}
