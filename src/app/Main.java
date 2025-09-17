package app;

import UserInterface.FRAME_main;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fix Nimbus background painters
        fixNimbusTransparency();

        // Optional: normalize fonts
        Font uiFont = new Font("SansSerif", Font.PLAIN, 13);
        UIManager.put("Label.font", uiFont);
        UIManager.put("Button.font", uiFont);
        UIManager.put("TextField.font", uiFont);
        UIManager.put("TextArea.font", uiFont);

        SwingUtilities.invokeLater(FRAME_main::new);
    }

    private static void fixNimbusTransparency() {
        String[] keys = {
                "TextField[Enabled].backgroundPainter",
                "TextArea[Enabled].backgroundPainter",
                "FormattedTextField[Enabled].backgroundPainter",
                "PasswordField[Enabled].backgroundPainter",
                "FormattedTextField[Focused].backgroundPainter",
                "TextField[Focused].backgroundPainter",
                "TextArea[Focused].backgroundPainter",
                "PasswordField[Focused].backgroundPainter"
        };
        for (String key : keys) {
            UIManager.put(key, null);
        }
    }
}


