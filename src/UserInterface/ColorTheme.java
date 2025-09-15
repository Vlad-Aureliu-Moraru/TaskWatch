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


    public static Color getUrgency(int urgency){
        switch (urgency){
            case 1:
                return urgency1;
                case 2:
                return urgency2;
                case 3:
                return urgency3;
                case 4:
                return urgency4;
                case 5:
                return urgency5;
        }
        return null;
    }
    public static Color getDifficulty(int difficulty){
        switch (difficulty){
            case 1:
                return difficulty1;
            case 2:
                return difficulty2;
            case 3:
                return difficulty3;
            case 4:
                return difficulty4;
            case 5:
                return difficulty5;
        }
        return null;
    }
    public static Color getTaskHoverColor() {
        return taskHoverColor;
    }

    public static Color getTimerOnPrepColor() {
        return timerOnPrepColor;
    }

    public static Color getTimerOnBreakColor() {
        return timerOnBreakColor;
    }

    public static Color getTaskUrgentIconColor() {
        return TaskUrgentIconColor;
    }

    public static Color getUrgency1List() {
        return urgency1List;
    }

    public static Color getUrgency2List() {
        return urgency2List;
    }

    public static Color getUrgency3List() {
        return urgency3List;
    }

    public static Color getUrgency4List() {
        return urgency4List;
    }

    public static Color getUrgency5List() {
        return urgency5List;
    }

    public static Color getTaskCompletedIconColor() {
        return TaskCompletedIconColor;
    }

    public static Color getDirHoverColor() {
        return dirHoverColor;
    }

    public static  Color getPausedTimerColor() {
        return pausedTimerColor;
    }

    public static Color getDirColor() {
        return dirColor;
    }

    public static Color getTaskTextColor() {
        return taskTextColor;
    }

    public static Color getTaskColor() {
        return taskColor;
    }

    public static Color getNoteColor() {
        return noteColor;
    }

    public static Color getMain_color() {
        return main_color;
    }

    public static Color getSecondary_color() {
        return secondary_color;
    }

    public static Color getFirst_accent() {
        return first_accent;
    }

    public static Color getSecnd_accent() {
        return secnd_accent;
    }

    public static Color getSecondary_green() {
        return secondary_green;
    }

    public static Color getAccent_green() {
        return accent_green;
    }

    public static Color getUrgency5() {
        return urgency5;
    }

    public static Color getConsoleColor() {
        return consoleColor;
    }

    public static Color getConsoleTextColor() {
        return consoleTextColor;
    }

    public static Color getTaskUrgentPassed() {
        return TaskUrgentPassed;
    }
}

