package UserInterface.SubPanels;

import AppLogic.Task;
import Loaders.FontLoader;
import Loaders.ThemeChangeListener;
import Loaders.ThemeColorKey;
import Loaders.ThemeLoader;

import javax.swing.*;
import java.awt.*;

public class PANEL_taskinfo extends JScrollPane implements ThemeChangeListener {

    private final JPanel mirrorPanel = new JPanel();
    private final JLabel taskName = new JLabel("Task Name:");
    private final JTextArea taskDescription = new JTextArea("Task Description:");
    private final JScrollPane taskDescriptionPane;
    private final JLabel taskStatus = new JLabel("Task Status:");
    private final JLabel taskPriority = new JLabel("Task Priority:");
    private final JLabel taskTime = new JLabel("Task Time:");
    private final JLabel taskDeadline = new JLabel("Task Deadline:");
    private final JLabel taskType = new JLabel("Task Type:");
    private final JLabel taskRepeatableType = new JLabel("Task Repeatable Type:");
    private final JLabel taskDifficulty = new JLabel("Task Difficulty:");
    private final JLabel taskFinishedDate = new JLabel("Task Finished Date:");

    private boolean active = false;
    private int HEIGHT;
    private int WIDTH;

    public PANEL_taskinfo() {
        ThemeLoader.addThemeChangeListener(this);

        mirrorPanel.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_TASKINFO));
        mirrorPanel.setLayout(null);
        setVisible(false);

        // === Text Area Configuration ===
        taskDescription.setEditable(false);
        taskDescription.setLineWrap(true);
        taskDescription.setWrapStyleWord(true);
        taskDescription.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_TASKINFO));
        taskDescription.setOpaque(true);
        taskDescription.setFocusable(false);
        taskDescription.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 15f));
        taskDescription.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));

        // === Description Scroll Pane ===
        taskDescriptionPane = new JScrollPane(taskDescription);
        taskDescriptionPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        taskDescriptionPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        taskDescriptionPane.setViewportBorder(BorderFactory.createEmptyBorder());
        taskDescriptionPane.setBorder(BorderFactory.createEmptyBorder());
        taskDescriptionPane.setOpaque(true);
        taskDescriptionPane.setAutoscrolls(true);
        taskDescriptionPane.setWheelScrollingEnabled(true);
        taskDescriptionPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        // === Foreground accent colors ===
        for (JLabel label : new JLabel[]{
                taskName, taskStatus, taskPriority, taskTime, taskDeadline,
                taskType, taskRepeatableType, taskDifficulty, taskFinishedDate
        }) {
            label.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
            label.setFont(FontLoader.getCozyFont().deriveFont(Font.PLAIN, 15f));
        }

        // === Add components ===
        mirrorPanel.add(taskName);
        mirrorPanel.add(taskDescriptionPane);
        mirrorPanel.add(taskStatus);
        mirrorPanel.add(taskPriority);
        mirrorPanel.add(taskTime);
        mirrorPanel.add(taskDeadline);
        mirrorPanel.add(taskType);
        mirrorPanel.add(taskRepeatableType);
        mirrorPanel.add(taskDifficulty);
        mirrorPanel.add(taskFinishedDate);

        // === Scroll pane settings ===
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mirrorPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setViewportView(mirrorPanel);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setViewportBorder(BorderFactory.createEmptyBorder());
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setAutoscrolls(true);
        setWheelScrollingEnabled(true);
        getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        getVerticalScrollBar().setUnitIncrement(80);
    }

    public void setHEIGHTandWIDTH(int height, int width) {
        this.HEIGHT = height;
        this.WIDTH = width;

        int currentY = 20;
        taskName.setBounds(80, 0, width, 30);
        taskStatus.setBounds(50, 0, 200, 30);
        taskType.setBounds(30, 0, 200, 30);

        taskDescriptionPane.setBounds(WIDTH / 20, 40, width / 2 - 40, height);
        taskPriority.setBounds(WIDTH / 2 + 70, currentY, 200, 30);
        currentY += 30;
        taskDifficulty.setBounds(WIDTH / 2 + 70, currentY, 200, 30);
        currentY += 30;
        taskTime.setBounds(WIDTH / 2 + 70, currentY, 200, 30);
        currentY += 30;
        taskFinishedDate.setBounds(WIDTH / 2 + 70, currentY, 200, 30);
        currentY += 30;
        taskRepeatableType.setBounds(WIDTH / 2 + 70, currentY, 200, 30);
        currentY += 30;
        taskDeadline.setBounds(WIDTH / 2 + 70, currentY, 200, 30);

        if (width < 500) {
            setFontSizeAll(13);
        } else if (width < 700) {
            setFontSizeAll(16);
        } else {
            setFontSizeAll(20);
        }

        mirrorPanel.setPreferredSize(new Dimension(WIDTH, currentY + 30));
        taskDescription.setPreferredSize(new Dimension(WIDTH, currentY * 3));
        mirrorPanel.revalidate();
        mirrorPanel.repaint();
        revalidate();
        repaint();
    }

    public void addTaskInfo(Task task) {
        activate();
        updateTaskInfo(task);
    }

    public void updateTaskInfo(Task task) {
        taskName.setText(task.getName());
        taskDescription.setText("DESC: " + task.getDescription());
        taskStatus.setText(task.isFinished() ? "\uF4A7" : "\uDB80\uDD31");
        taskPriority.setText("URGENCY: " + task.getUrgency());
        taskTime.setText("TIME: " + task.getTimeDedicated() + "min");

        if (task.getDeadline() != null && !task.getDeadline().equals("none")) {
            taskDeadline.setText(task.getDeadline());
            taskDeadline.setVisible(true);
        } else {
            taskDeadline.setVisible(false);
        }

        taskDifficulty.setText("DIFFICULTY: " + task.getDifficulty());
        taskType.setText(task.isRepeatable() ? "\uDB81\uDC56" : "\uDB81\uDC57");
        taskRepeatableType.setText("\uF01E  " + task.getRepeatableType());
        taskFinishedDate.setText("\uED7A  " + task.getFinishedDate());
        taskRepeatableType.setVisible(task.isRepeatable());
        taskFinishedDate.setVisible(task.isFinished());
        revalidate();
        repaint();
    }

    public void activate() {
        setVisible(!active);
        active = !active;
    }

    public void deactivate() {
        setVisible(false);
        active = false;
    }

    private void setFontSizeAll(int size) {
        Font base = FontLoader.getCozyFont().deriveFont(Font.PLAIN, size);
        taskName.setFont(base);
        taskDescription.setFont(base);
        taskStatus.setFont(base.deriveFont((float) size + 5));
        taskPriority.setFont(base);
        taskTime.setFont(base);
        taskDeadline.setFont(base);
        taskType.setFont(base.deriveFont((float) size + 5));
        taskFinishedDate.setFont(base);
        taskRepeatableType.setFont(base);
        taskDifficulty.setFont(base);
    }

    @Override
    public void onThemeChanged() {
        // Apply background color
        mirrorPanel.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_TASKINFO));
        taskDescription.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_TASKINFO));

        // Update all label & text colors
        for (JLabel label : new JLabel[]{
                taskName, taskStatus, taskPriority, taskTime, taskDeadline,
                taskType, taskRepeatableType, taskDifficulty, taskFinishedDate
        }) {
            label.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));
        }
        taskDescription.setForeground(ThemeLoader.getColor(ThemeColorKey.SECND_ACCENT));

        repaint();
        revalidate();
    }
}
