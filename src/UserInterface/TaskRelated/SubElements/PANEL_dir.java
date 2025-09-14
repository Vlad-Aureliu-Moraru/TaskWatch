package UserInterface.TaskRelated.SubElements;

import AppLogic.DirectoryLogic.Directory;
import AppLogic.EventHandler;
import AppLogic.FontLoader;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_dir extends JPanel {
    private final JLabel titleLabel =  new JLabel();
    private final Directory directory;

    private int WIDTH = 200;
    private int HEIGHT = 30;

    public PANEL_dir(Directory directory) {
        this.directory = directory;
        this.setBackground(ColorTheme.getDirColor());
        this.setLayout(null);
        titleLabel.setText("\uF4D3  "+directory.getName());
        titleLabel.setBackground(ColorTheme.getSecondary_green());
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 20));


        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

        this.setBorder(compoundBorder);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                PANEL_dir.this.setBackground(ColorTheme.getDirHoverColor());
                titleLabel.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 25));
            }
            public void mouseExited(MouseEvent e) {
                PANEL_dir.this.setBackground(ColorTheme.getDirColor());
                titleLabel.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 20));
            }
        });

        titleLabel.setBounds(0,0,100,30);
        titleLabel.setForeground(ColorTheme.getSecnd_accent());
        this.add(titleLabel);
    }
    public void setHEIGHTandWIDTH(int height, int width){
        titleLabel.setBounds(this.getWidth()/2-(WIDTH/2),this.getHeight()/2-(HEIGHT/2),WIDTH,HEIGHT);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(directory.getName()+"/");
                eventHandler.setCurrentDirectory(directory);
//                eventHandler.getFileHandler().getTaskListFromFile();
                eventHandler.getPanelList().loadCurrentTasks();
                System.out.println(directory);
            }
        });
    }
}
