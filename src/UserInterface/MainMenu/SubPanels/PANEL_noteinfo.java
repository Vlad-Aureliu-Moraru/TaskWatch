package UserInterface.MainMenu.SubPanels;

import AppLogic.NotesLogic.Note;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;

public class PANEL_noteinfo extends JPanel {
    private JTextArea noteinfo =  new JTextArea();
    private JLabel noteDate =  new JLabel();
    public PANEL_noteinfo() {
        this.setBackground(ColorTheme.getMain_color());
        this.setLayout(null);
        noteinfo.setEditable(false);
        noteinfo.setOpaque(false);
        noteinfo.setFocusable(false);
        noteinfo.setWrapStyleWord(true);
        noteinfo.setLineWrap(true);
        noteinfo.setForeground(ColorTheme.getSecnd_accent());
        noteDate.setForeground(ColorTheme.getSecnd_accent());
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
