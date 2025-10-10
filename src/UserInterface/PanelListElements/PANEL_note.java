package UserInterface.PanelListElements;

import Handlers.EventHandler;
import Loaders.FontLoader;
import AppLogic.Note;
import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static UserInterface.PanelListElements.ListStages.NOTE_CLICKED;

public class PANEL_note extends JPanel implements ThemeChangeListener {
    private final Note currentNote;
    private final JLabel noteName = new JLabel();

    public PANEL_note(Note note) {
        this.currentNote = note;
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.NOTE_COLOR));
        ThemeLoader.addThemeChangeListener(this);
        this.setLayout(null);
        noteName.setText("\uDB85\uDF81  "+currentNote.getDate());
        noteName.setBounds(0, 0,200,50);
        noteName.setHorizontalAlignment(JLabel.CENTER);
        noteName.setFont(FontLoader.getCozyFont().deriveFont(15f));
        this.add(noteName);
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.ACCENT_GREEN), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);

    }

    public void setHEIGHTandWIDTH(int height, int width){
        noteName.setBounds(0,0,width,height);

    }
    public void setEventHandler(EventHandler eventHandler) {
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eventHandler.setCurrentNote(currentNote);
                eventHandler.getPanelMainmenu().getPanel_noteinfo().addNoteInfo(currentNote);
                eventHandler.getPanelList().setNoteSelected(true);
                eventHandler.getPanelList().setStage(NOTE_CLICKED);

                System.out.println(eventHandler.getCurrentNote());
            }
        });
    }

    @Override
    public void onThemeChanged() {
        this.setBackground(ThemeLoader.getColor(ThemeColorKey.NOTE_COLOR));
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border innerBorder = BorderFactory.createLineBorder(ThemeLoader.getColor(ThemeColorKey.ACCENT_GREEN), 3);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setBorder(compoundBorder);
    }
}
