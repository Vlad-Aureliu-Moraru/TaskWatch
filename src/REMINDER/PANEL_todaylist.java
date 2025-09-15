package REMINDER;

import AppLogic.Task;
import UserInterface.ColorTheme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class PANEL_todaylist extends JScrollPane{
    private JPanel panel = new JPanel();
    private JLabel today= new JLabel("TODAY");

    private ArrayList<PANEL_item> items = new ArrayList<>();

    private int HEIGHT,WIDTH;

    public PANEL_todaylist() {
        panel.setBackground(ColorTheme.getMain_color());
        panel.setLayout(null);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        today.setBackground(ColorTheme.getMain_color());
        today.setOpaque(true);
        today.setForeground(ColorTheme.getSecnd_accent());

        this.setViewportView(panel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setViewportBorder(BorderFactory.createEmptyBorder());
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);
        this.setAutoscrolls(true);
        this.setWheelScrollingEnabled(true);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        getVerticalScrollBar().setUnitIncrement(80);
        Border outerBorder = BorderFactory.createEmptyBorder(15, 0, 5, 0);
        Border innerBorder = BorderFactory.createLineBorder(ColorTheme.getSecnd_accent(), 2);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        panel.setBorder(compoundBorder);
        panel.add(today);
    }
    public void addItem(Task task){
        PANEL_item panelitem = new PANEL_item(task);
        if (!items.contains(panelitem)){
            items.add(panelitem);
        }
    }
    public void loadItems(){
        panel.removeAll();
        panel.add(today);
        int currentY= 30;
        for(PANEL_item panelitem:items){
            panelitem.setBounds(15,currentY,WIDTH-30,30);
            panelitem.setHEIGHTandWIDTH(30,WIDTH-30);
            panel.add(panelitem);
            currentY+=40;
        }
        panel.setPreferredSize(new Dimension(WIDTH,currentY));
        panel.revalidate();
        panel.repaint();
    }

    public void setHEIGHTandWIDTH(int height,int width){
        this.HEIGHT=height;
        this.WIDTH=width;
        today.setBounds(width/2-25,0,50,30);
        loadItems();
    }
    public void clearItems(){
        items.clear();
    }
}
