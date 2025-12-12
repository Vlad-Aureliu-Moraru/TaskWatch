package UserInterface.SubPanels;

import Note.Model.Note;
import Logic.Loaders.FontLoader;
import Logic.Loaders.ThemeChangeListener;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;

import javax.swing.*;
import java.awt.*;

public class PANEL_noteinfo extends JPanel implements ThemeChangeListener {

    private final JTextArea noteinfo = new JTextArea();
    private final JLabel noteDate = new JLabel();

    public PANEL_noteinfo() {
        setLayout(null);
        setVisible(false);
        setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_NOTEINFO));

        ThemeLoader.addThemeChangeListener(this);

        // Configure note text area
        noteinfo.setEditable(false);
        noteinfo.setOpaque(false);
        noteinfo.setFocusable(false);
        noteinfo.setWrapStyleWord(true);
        noteinfo.setLineWrap(true);
        noteinfo.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 15f));

        // Configure note date label
        noteDate.setFont(FontLoader.getCozyFont().deriveFont(Font.BOLD, 14f));

        // Apply initial theme colors
        noteinfo.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        noteDate.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));

        add(noteDate);
        add(noteinfo);
    }

    public void setHEIGHTandWIDTH(int height, int width) {
        noteDate.setBounds(0, 0, width, 30);
        noteinfo.setBounds(0, 40, width, height - 40);
    }

    public void addNoteInfo(Note note) {
        setVisible(true);
        noteinfo.setText(note.getNote());
        noteDate.setText(note.getDate());
    }

    public void deactivate() {
        setVisible(false);
    }

    @Override
    public void onThemeChanged() {
        // Update background
        setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_NOTEINFO));

        // Apply theme to all components (colors, accents, etc.)

        // Update text colors explicitly if necessary
        noteinfo.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        noteDate.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));

        repaint();
        revalidate();
    }
}
