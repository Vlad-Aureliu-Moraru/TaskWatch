package UserInterface;
import java.awt.Toolkit;
import java.awt.FontMetrics;

public class DPIscale{

    private static double getScaleFactor() {
        return Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
    }

    public static int scale(int value) {
        return (int) (value * getScaleFactor());
    }
}