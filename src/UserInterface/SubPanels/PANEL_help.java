package UserInterface.SubPanels;

import ConfigRelated.FontLoader;
import ConfigRelated.ThemeLoader;

import javax.swing.*;
import java.awt.*;

public class PANEL_help extends JScrollPane{
    private final JPanel panel_help = new JPanel();

    private JLabel[] urgencyBoxLables = new JLabel[5];
    private modifiedTextArea urgencyExplanation = new modifiedTextArea("\uEEBF  - TASK URGENCY ICON & URGENCY COLORS 1-5 ");


    private JLabel[] difficultyBoxLables = new JLabel[5];
    private modifiedTextArea difficultyExplanation = new modifiedTextArea("\uEAD7  - TASK DIFFICULTY BORDER & DIFFICULTY COLORS 1-5 ");

    private modifiedTextArea commandInfo= new modifiedTextArea("");
    private modifiedTextArea themeSettingsInfo= new modifiedTextArea("");

    private int boxSpace = 20;

    public PANEL_help() {
        this.setViewportView(panel_help);
        panel_help.setBackground(ThemeLoader.getMainColor());
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
        addThemeSettingsInfo();
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
        commandInfo.setBounds(20,105,width-30,700);
        currentHeight+=700;
        themeSettingsInfo.setBounds(20,currentHeight+12,width-30,1600);
        currentHeight+=1600;
        panel_help.setPreferredSize(new Dimension(width,currentHeight));
    }
    private void addCommandInfo(){

        String commandinfo = """
                :h | :help           - DISPLAYS THIS HELP MESSAGE
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
                :exit                     - EXITS THE APPLICATION
                :ct(name) | :createTheme(name) - CREATES A NEW THEME FILE WITH THE GIVEN NAME
                :rt(name) | :removeTheme(name) - REMOVES THE THEME FILE WITH THE GIVEN NAME
                :set(name) | :setTheme(name) - SETS THE CURRENT THEME TO THE GIVEN NAME
                :theme                    - SHOWS THE CURRENTLY ACTIVE THEME
                :lst | :listThemes          - LISTS ALL AVAILABLE THEME FILES
                
                                              
                """
                ;
        commandInfo.setText(commandinfo);
        commandInfo.setFont(FontLoader.getCozyFont().deriveFont(17f));
        commandInfo.setForeground(ThemeLoader.getSecndAccent());
        panel_help.add(commandInfo);
    }
    private void addThemeSettingsInfo(){

        String commandinfo = """
                 THEME CUSTOMIZATION
            
:setMainColor:(R, G, B)           - SETS MAIN COLOR
:setSecondaryColor:(R, G, B)      - SETS SECONDARY COLOR
:setFirstAccent:(R, G, B)         - SETS FIRST ACCENT COLOR
:setSecndAccent:(R, G, B)         - SETS SECOND ACCENT COLOR
:setSecondaryGreen:(R, G, B)      - SETS SECONDARY GREEN COLOR
:setAccentGreen:(R, G, B)         - SETS ACCENT GREEN COLOR
:setDirColor:(R, G, B)            - SETS DIRECTORY COLOR
:setDirHoverColor:(R, G, B)       - SETS DIRECTORY HOVER COLOR
:setTaskColor:(R, G, B)           - SETS TASK COLOR
:setTaskHoverColor:(R, G, B)      - SETS TASK HOVER COLOR
:setTaskTextColor:(R, G, B)       - SETS TASK TEXT COLOR
:setNoteColor:(R, G, B)           - SETS NOTE COLOR
:setPausedTimerColor:(R,G,B)      - SETS PAUSED TIMER COLOR 
:setUrgency1:(R, G, B)            - SETS URGENCY 1 COLOR
:setUrgency2:(R, G, B)            - SETS URGENCY 2 COLOR
:setUrgency3:(R, G, B)            - SETS URGENCY 3 COLOR
:setUrgency4:(R, G, B)            - SETS URGENCY 4 COLOR
:setUrgency5:(R, G, B)            - SETS URGENCY 5 COLOR
:setUrgency1List:(R, G, B)        - SETS URGENCY 1 LIST COLOR
:setUrgency2List:(R, G, B)        - SETS URGENCY 2 LIST COLOR
:setUrgency3List:(R, G, B)        - SETS URGENCY 3 LIST COLOR
:setUrgency4List:(R, G, B)        - SETS URGENCY 4 LIST COLOR
:setUrgency5List:(R, G, B)        - SETS URGENCY 5 LIST COLOR
:setDifficulty1:(R, G, B)         - SETS DIFFICULTY 1 COLOR
:setDifficulty2:(R, G, B)         - SETS DIFFICULTY 2 COLOR
:setDifficulty3:(R, G, B)         - SETS DIFFICULTY 3 COLOR
:setDifficulty4:(R, G, B)         - SETS DIFFICULTY 4 COLOR
:setDifficulty5:(R, G, B)         - SETS DIFFICULTY 5 COLOR
:setTaskCompletedIconColor:(R, G, B) - SETS COMPLETED TASK ICON COLOR
:setTaskUrgentIconColor:(R, G, B) - SETS URGENT TASK ICON COLOR
:setTaskUrgentPassed:(R, G, B, A) - SETS URGENT TASK PASSED COLOR (RGBA)
:setConsoleColor:(R, G, B)        - SETS CONSOLE BACKGROUND COLOR
:setConsoleTextColor:(R, G, B)    - SETS CONSOLE TEXT COLOR
:setTimerOnBreakColor:(R, G, B)   - SETS TIMER ON BREAK COLOR
:setTimerOnPrepColor:(R, G, B)    - SETS TIMER ON PREP COLOR
"""
                ;
        themeSettingsInfo.setText(commandinfo);
        themeSettingsInfo.setFont(FontLoader.getCozyFont().deriveFont(17f));
        themeSettingsInfo.setForeground(ThemeLoader.getSecndAccent());
        panel_help.add(themeSettingsInfo);
    }
    private void addUrgencyBoxes(){
        for(int i = 0; i < urgencyBoxLables.length; i++){
            urgencyBoxLables[i] = new JLabel("\uF04D");
            urgencyBoxLables[i].setForeground(ThemeLoader.getUrgency(i+1));
            urgencyBoxLables[i].setFont(FontLoader.getCozyFont().deriveFont(22f));
            urgencyBoxLables[i].setBounds((boxSpace*i)+10,5,200,50);

            this.panel_help.add(urgencyBoxLables[i]);
        }
        urgencyExplanation.setFont(FontLoader.getCozyFont().deriveFont(16f));
        urgencyExplanation.setForeground(ThemeLoader.getSecndAccent());
        this.panel_help.add(urgencyExplanation);
    }
    private void addDifficultyBoxes(){
        for(int i = 0; i < difficultyBoxLables.length; i++){
            difficultyBoxLables[i] = new JLabel("\uF04D");
            difficultyBoxLables[i].setForeground(ThemeLoader.getDifficulty(i+1));
            difficultyBoxLables[i].setFont(FontLoader.getCozyFont().deriveFont(22f));
            difficultyBoxLables[i].setBounds((boxSpace*i)+10,40,50,50);

            this.panel_help.add(difficultyBoxLables[i]);
        }
        difficultyExplanation.setFont(FontLoader.getCozyFont().deriveFont(16f));
        difficultyExplanation.setForeground(ThemeLoader.getSecndAccent());
        this.panel_help.add(difficultyExplanation);
    }
}
