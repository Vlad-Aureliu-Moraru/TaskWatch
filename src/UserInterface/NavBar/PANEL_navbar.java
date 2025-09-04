package UserInterface.NavBar;

import javax.swing.*;
import java.awt.*;

public class PANEL_navbar extends JPanel {
    private JLabel task1 = new JLabel("Task_1");
    private int HEIGHT;
    private int WIDTH;

   public PANEL_navbar(){
        this.setBackground(Color.green);
        this.setLayout(null);
        task1.setBounds(getHeight()/2,10,100,30);
        this.add(task1);
    }

    public void setHEIGHTandWIDTH(int WIDTH,int HEIGHT){
        this.HEIGHT=HEIGHT;
        this.WIDTH=WIDTH;
        task1.setBounds(WIDTH/2,10,100,30);
        this.revalidate();
        this.repaint();
    }

}
