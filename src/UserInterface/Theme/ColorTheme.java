package UserInterface.Theme;

import java.awt.*;

public class ColorTheme {
    private static Color main_color = new Color(57, 50, 50);          // #393232
    private static Color secondary_color = new Color(77, 69, 69);      // #4D4545
    private static Color first_accent = new Color(141, 98, 98);        // #8D6262
    private static Color secnd_accent = new Color(237, 141, 141);      // #ED8D8D

    private static Color main_green = new Color(56, 41, 51);           // #382933
    private static Color secondary_green = new Color(53, 155, 112);      // #3B5249
    private static Color accent_green = new Color(33, 50, 73);       // #519872
    private static Color accent_green2 = new Color(114, 128, 101);     // #A4B494

    private static Color dirColor = new Color(55, 56, 103);
    private static Color taskColor= new Color(103, 82, 82);
        private static Color taskTextColor = new Color(255, 255, 255);
    private static Color noteColor = new Color(211, 164, 89);

    private static Color pausedTimerColor = new Color(0xFFFFFF);

    private static Color urgency1 = new Color(115, 201, 168);
    private static Color urgency2 = new Color(116, 215, 129);
    private static Color urgency3 = new Color(211, 215, 103);
    private static Color urgency4 = new Color(201, 151, 115);
    private static Color urgency5 = new Color(255, 11, 11);

    private static Color consoleColor = new  Color(82, 94, 84);
    private static Color consoleTextColor = new  Color(222, 222, 222);

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

    public static Color getMain_green() {
        return main_green;
    }

    public static Color getSecondary_green() {
        return secondary_green;
    }

    public static Color getAccent_green() {
        return accent_green;
    }

    public static Color getAccent_green2() {
        return accent_green2;
    }

    public static Color getUrgency1() {
        return urgency1;
    }

    public static Color getUrgency2() {
        return urgency2;
    }

    public static Color getUrgency3() {
        return urgency3;
    }

    public static Color getUrgency4() {
        return urgency4;
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
}

