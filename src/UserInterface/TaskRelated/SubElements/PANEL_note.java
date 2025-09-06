package UserInterface.TaskRelated.SubElements;

import AppLogic.EventHandler;
import AppLogic.NotesLogic.Note;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PANEL_note extends JPanel {
    private Note currentNote;
    private JLabel noteName = new JLabel();
    private EventHandler eventHandler;
    private ColorTheme colorTheme = new ColorTheme();
    public PANEL_note(Note note) {
        this.currentNote = note;
        this.setBackground(colorTheme.accent_green2);
        this.setLayout(null);
        noteName.setText(currentNote.getDate());
        noteName.setBounds(0, 0,100,50);
        this.add(noteName);

    }
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler= eventHandler;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eventHandler.setCurrentNote(currentNote);

                System.out.println(eventHandler.getCurrentNote());
            }
        });
    }
}
