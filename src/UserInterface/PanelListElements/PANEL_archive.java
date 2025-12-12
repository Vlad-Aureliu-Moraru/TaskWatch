package UserInterface.PanelListElements;

import Archive.Model.Archive;
import Logic.Handlers.EventHandler;
import Logic.Loaders.FontLoader;
import Logic.Loaders.ThemeChangeListener;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_archive extends JPanel implements ThemeChangeListener {
        private final JLabel titleLabel =  new JLabel();
        private final Archive archive;


        private int WIDTH = 200;
        private int HEIGHT = 30;

        public PANEL_archive(Archive archive) {
            ThemeLoader.addThemeChangeListener(this);
            this.archive = archive;
            this.setBackground(ThemeLoader.getColor(ThemeColorKey.ARCHIVE_COLOR));
            this.setLayout(null);
            titleLabel.setText("\uF187  "+archive.getArchiveName());
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 20));


            Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT), 1);
            Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

            this.setBorder(compoundBorder);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    PANEL_archive.this.setBackground(ThemeLoader.getColor(ThemeColorKey.ARCHIVE_HOVER_COLOR));
                    titleLabel.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 25));
                }
                public void mouseExited(MouseEvent e) {
                    PANEL_archive.this.setBackground(ThemeLoader.getColor(ThemeColorKey.ARCHIVE_COLOR));
                    titleLabel.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 20));
                }
            });

            titleLabel.setBounds(0,0,100,30);
            titleLabel.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
            this.add(titleLabel);
        }
        public void setHEIGHTandWIDTH(int height, int width){
            titleLabel.setBounds(this.getWidth()/2-(WIDTH/2),this.getHeight()/2-(HEIGHT/2),WIDTH,HEIGHT);
        }

        public void setEventHandler(EventHandler eventHandler) {
            this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.out.println(archive.getArchiveName()+"/");
                    eventHandler.setCurrentArchive(archive);
                    eventHandler.getPanelList().loadDirs();
                    System.out.println(eventHandler.getCurrentArchive());
                }
            });
        }

    @Override
    public void onThemeChanged() {
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.ARCHIVE_COLOR));
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT), 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
        titleLabel.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
    }
}
