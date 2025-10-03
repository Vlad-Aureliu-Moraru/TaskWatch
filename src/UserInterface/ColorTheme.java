package UserInterface;

import ConfigRelated.ThemeLoader;

import java.awt.*;

public class ColorTheme {
    private final static Color main_color = ThemeLoader.getMainColor();
    private final static Color secondary_color = ThemeLoader.getSecondaryColor();
    private final static Color first_accent = ThemeLoader.getFirstAccent();
    private final static Color secnd_accent = ThemeLoader.getSecndAccent();

    private final static Color secondary_green = ThemeLoader.getSecondaryGreen();
    private final static Color accent_green = ThemeLoader.getAccentGreen();

    private final static Color dirColor = ThemeLoader.getDirColor();
    private final static Color dirHoverColor = ThemeLoader.getDirHoverColor();

    private final static Color taskColor= ThemeLoader.getTaskColor();
    private final static Color taskHoverColor= ThemeLoader.getTaskHoverColor();
    private final static Color taskTextColor = ThemeLoader.getTaskTextColor();

    private final static Color noteColor = ThemeLoader.getNoteColor();

    private final static Color pausedTimerColor = ThemeLoader.getPausedTimerColor();

    private final static Color urgency1 = ThemeLoader.getUrgency1();
    private final static Color urgency2 = ThemeLoader.getUrgency2();
    private final static Color urgency3 =ThemeLoader.getUrgency3();
    private final static Color urgency4 =ThemeLoader.getUrgency4();
    private final static Color urgency5 =ThemeLoader.getUrgency5();

    private final static Color urgency1List =ThemeLoader.getUrgency1List();
    private final static Color urgency2List =ThemeLoader.getUrgency2List();
    private final static Color urgency3List =ThemeLoader.getUrgency3List();
    private final static Color urgency4List =ThemeLoader.getUrgency4List();
    private final static Color urgency5List =ThemeLoader.getUrgency5List();

    private final static Color  difficulty1 = ThemeLoader.getDifficulty1();
    private final static Color  difficulty2 =ThemeLoader.getDifficulty2();
    private final static Color  difficulty3 =ThemeLoader.getDifficulty3();
    private final static Color  difficulty4 =ThemeLoader.getDifficulty4();
    private final static Color  difficulty5 =ThemeLoader.getDifficulty5();

    private final static Color TaskCompletedIconColor = ThemeLoader.getTaskCompletedIconColor();
    private final static Color TaskUrgentIconColor = ThemeLoader.getTaskUrgentIconColor();
    private final static Color TaskUrgentPassed = ThemeLoader.getTaskUrgentPassed();

    private final static Color consoleColor = ThemeLoader.getConsoleColor();
    private final static Color consoleTextColor = ThemeLoader.getConsoleTextColor();

    private final static Color timerOnBreakColor = ThemeLoader.getTimerOnBreakColor();
    private final static Color timerOnPrepColor = ThemeLoader.getTimerOnPrepColor();


}

