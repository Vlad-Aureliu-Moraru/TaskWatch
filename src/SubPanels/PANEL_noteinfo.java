package SubPanels;

import AppLogic.FontLoader;
import AppLogic.Note;
import ConfigRelated.ThemeLoader;
import UserInterface.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class PANEL_noteinfo extends JPanel {
    private final JTextArea noteinfo =  new JTextArea();
    private final JLabel noteDate =  new JLabel();
    public PANEL_noteinfo() {
        this.setBackground(ThemeLoader.getMainColor());
        this.setVisible(false);
        this.setLayout(null);
        noteinfo.setEditable(false);
        noteinfo.setOpaque(false);
        noteinfo.setFocusable(false);
        noteinfo.setWrapStyleWord(true);
        noteinfo.setLineWrap(true);
        noteinfo.setForeground(ThemeLoader.getSecndAccent());
        noteDate.setForeground(ThemeLoader.getSecndAccent());
        noteinfo.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 15));
        this.add(noteDate);
        this.add(noteinfo);
    }
    public void setHEIGHTandWIDTH(int height,int width){
        noteDate.setBounds(0,0,width,30);
        noteinfo.setBounds(0,40,width,height-40);
    }
    public void addNoteInfo(Note note){
        this.setVisible(true);
        noteinfo.setText(note.getNote());
        noteDate.setText(note.getDate());
    }

    public void deactivate(){
        this.setVisible(false);
    }
}
