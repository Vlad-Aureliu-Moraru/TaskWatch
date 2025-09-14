package UserInterface.MainMenu.SubPanels;

import AppLogic.FontLoader;
import UserInterface.Theme.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class PANEL_help extends JScrollPane{
    private final JPanel panel_help = new JPanel();

    private JLabel[] urgencyBoxLables = new JLabel[5];
    private modifiedTextArea urgencyExplanation = new modifiedTextArea("\uEEBF  - TASK URGENCY ICON & URGENCY COLORS 1-5 ");


    private JLabel[] difficultyBoxLables = new JLabel[5];
    private modifiedTextArea difficultyExplanation = new modifiedTextArea("\uEAD7  - TASK DIFFICULTY BORDER & DIFFICULTY COLORS 1-5 ");

    private modifiedTextArea commandInfo= new modifiedTextArea("");

    private int boxSpace = 20;

    public PANEL_help() {
        this.setViewportView(panel_help);
        panel_help.setBackground(ColorTheme.getMain_color());
        panel_help.setLayout(null);

        this.setFocusable(false);
        this.setVisible(false);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setViewportBorder(BorderFactory.createEmptyBorder());
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);
        this.setAutoscrolls(true);
        this.setWheelScrollingEnabled(true);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        getVerticalScrollBar().setUnitIncrement(10);

        addUrgencyBoxes();
        addDifficultyBoxes();
        addCommandInfo();
    }

    public void switchVisible(){
        this.setVisible(!this.isVisible());
    }

    public void setHEIGHTandWIDTH(int height, int width){
        int currentHeight =0;
        urgencyExplanation.setBounds((boxSpace*5)+20,20,width-30,50);
        currentHeight+=70;
        difficultyExplanation.setBounds((boxSpace*5)+20,55,width-30,50);
        currentHeight+=70;
        commandInfo.setBounds(20,105,width-30,300);
        currentHeight+=300;

        panel_help.setPreferredSize(new Dimension(width,currentHeight));
    }
    private void addCommandInfo(){

        String commandinfo = """
                :a | :add            - ADDS DIRECTORIES/TASKS/NOTES
                :r | :remove         - REMOVES DIRECTORIES/TASKS/NOTES
                :c | :cancel         - CANCELS COMMAND INPUT
                :e | :edit           - EDIT DIRECTORIES/TASKS/NOTES
                :f | :finish         - TOGGLES FINISH/UNFINISH TASK
                :st(int time)        - STARTS A BASIC TIMER
                :st | :startSelected - STARTS SELECTED TASK WITH A POMODORO TIMER
                :ts | :timerStop     - STOPS TIMER
                :pt | :pauseTimer    - TOGGLES PAUSE/UNPAUSE TIMER
                :rt | :resetTimer    - RESETS TIMER
                :shf | :showFinished - SHOWS FINISHED TASKS
                su( a | d ) | sortByUrgency ( a | d )    - SORTS TASKS BY URGENCY
                sd( a | d ) | sortByDifficulty ( a | d ) - SORTS TASKS BY DIFFICULTY
                """
                ;
        commandInfo.setText(commandinfo);
        commandInfo.setFont(FontLoader.getCozyFont().deriveFont(17f));
        commandInfo.setForeground(ColorTheme.getSecnd_accent());
        panel_help.add(commandInfo);
    }
    private void addUrgencyBoxes(){
        for(int i = 0; i < urgencyBoxLables.length; i++){
            urgencyBoxLables[i] = new JLabel("\uF04D");
            urgencyBoxLables[i].setForeground(ColorTheme.getUrgency(i+1));
            urgencyBoxLables[i].setFont(FontLoader.getCozyFont().deriveFont(22f));
            urgencyBoxLables[i].setBounds((boxSpace*i)+10,5,200,50);

            this.panel_help.add(urgencyBoxLables[i]);
        }
        urgencyExplanation.setFont(FontLoader.getCozyFont().deriveFont(16f));
        urgencyExplanation.setForeground(ColorTheme.getSecnd_accent());
        this.panel_help.add(urgencyExplanation);
    }
    private void addDifficultyBoxes(){
        for(int i = 0; i < difficultyBoxLables.length; i++){
            difficultyBoxLables[i] = new JLabel("\uF04D");
            difficultyBoxLables[i].setForeground(ColorTheme.getDifficulty(i+1));
            difficultyBoxLables[i].setFont(FontLoader.getCozyFont().deriveFont(22f));
            difficultyBoxLables[i].setBounds((boxSpace*i)+10,40,50,50);

            this.panel_help.add(difficultyBoxLables[i]);
        }
        difficultyExplanation.setFont(FontLoader.getCozyFont().deriveFont(16f));
        difficultyExplanation.setForeground(ColorTheme.getSecnd_accent());
        this.panel_help.add(difficultyExplanation);
    }
}
