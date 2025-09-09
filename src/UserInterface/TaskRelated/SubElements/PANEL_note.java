package UserInterface.TaskRelated.SubElements;

import AppLogic.EventHandler;
import AppLogic.FontLoader;
import AppLogic.NotesLogic.Note;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_note extends JPanel {
    private Note currentNote;
    private JLabel noteName = new JLabel();
    private EventHandler eventHandler;
    public PANEL_note(Note note) {
        this.currentNote = note;
        this.setBackground(ColorTheme.getNoteColor());
        this.setLayout(null);
        noteName.setText("\uDB85\uDF81  "+currentNote.getDate());
        noteName.setBounds(0, 0,200,50);
        noteName.setHorizontalAlignment(JLabel.CENTER);
        noteName.setFont(FontLoader.getCozyFont().deriveFont(15f));
        this.add(noteName);
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getAccent_green(), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

    }

    public void setHEIGHTandWIDTH(int height, int width){
        noteName.setBounds(0,0,width,height);

    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler= eventHandler;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eventHandler.setCurrentNote(currentNote);
                eventHandler.getPanelMainmenu().getPanel_noteinfo().addNoteInfo(currentNote);
                eventHandler.getPanelList().setNoteSelected(true);

                System.out.println(eventHandler.getCurrentNote());
            }
        });
    }
}
