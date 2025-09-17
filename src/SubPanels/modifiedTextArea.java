package SubPanels;

import javax.swing.*;
import java.awt.*;

public class modifiedTextArea extends JTextArea {
    public modifiedTextArea(String text) {
        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setFocusable(false);
        this.setText(text);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(null);
    }
}
