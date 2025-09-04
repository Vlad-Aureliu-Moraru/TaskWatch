package UserInterface.TaskRelated;

import javax.swing.*;
import java.awt.*;

public class PANEL_dir extends JPanel {
    private JLabel titleLabel =  new JLabel();
    private JButton test = new JButton();

    public PANEL_dir() {
        this.setBackground(Color.gray);
        this.setLayout(null);

        test.setText("V");
        test.setBounds(200, 10, 100, 30);
        this.add(test);

        titleLabel.setBounds(0,0,100,30);
        this.add(titleLabel);
    }
    public void setTitleLabel(String title){
        titleLabel.setText(title);
    }
}
