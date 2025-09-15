package SubPanels;

import javax.swing.*;

public class modifiedTextArea extends JTextArea {
    public modifiedTextArea(String text) {
        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setFocusable(false);
        setOpaque(false);
        this.setText(text);
    }
}
