package UserInterface;

import Archive.Model.Archive;
import Directory.Model.Directory;
import Note.Model.Note;
import Task.Model.Task;
import Logic.Handlers.EventHandler;
import Logic.Loaders.ThemeChangeListener;
import Logic.Loaders.ThemeColorKey;
import Logic.Loaders.ThemeLoader;
import UserInterface.PanelListElements.*;
import UserInterface.PanelListElements.CustomScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static UserInterface.PanelListElements.ListStages.*;

public class PANEL_list extends JScrollPane implements ThemeChangeListener {

    private final JPanel panel = new JPanel();

    private int HEIGHT;
    private int WIDTH;
    private static final int GAP = 20;
    private static final int MARGIN = 10;
    private ListStages listStages = MAIN_MENU;
    private boolean noteSelected = false;
    private boolean showingFinished = false;

    private EventHandler eventHandler;

    public PANEL_list() {
        ThemeLoader.addThemeChangeListener(this);

        panel.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_LIST));
        panel.setLayout(null);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setViewportView(panel);

        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setViewportBorder(BorderFactory.createEmptyBorder());
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setVisible(true);
        setAutoscrolls(true);
        setWheelScrollingEnabled(true);
        getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        getVerticalScrollBar().setUnitIncrement(10);
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
    }

    // === Dynamic sizing ===
    public void setHEIGHTandWIDTH(int width, int height) {
        this.HEIGHT = height;
        this.WIDTH = width;

        switch (listStages) {
            case MAIN_MENU -> loadArchives();
            case ARCHIVE_MENU -> loadDirs();
            case DIRECTORY_MENU -> loadCurrentTasks(null);
            case TASK_MENU -> loadCurrentTaskNotes();
        }
    }

    // === Archive / Directory / Task Loading ===
    public void loadArchives() {
        listStages = MAIN_MENU;
        panel.removeAll();

        int currentY = 10;
        for (Archive arch : eventHandler.getAllArchives()) {
            PANEL_archive archive = new PANEL_archive(arch);
            archive.setEventHandler(eventHandler);
            archive.setBounds(MARGIN, currentY, WIDTH - 40, HEIGHT / 7);
            archive.setHEIGHTandWIDTH(WIDTH - 40, HEIGHT / 7);
            panel.add(archive);
            currentY += archive.getHeight() + GAP;
        }

        updatePanelSize(currentY);
    }

    public void loadDirs() {
        listStages = ARCHIVE_MENU;
        panel.removeAll();

        int currentY = 10;
        for (Directory dir : eventHandler.getDirectoryRepository()
                .getDirectoriesByArchiveId(eventHandler.getCurrentArchive().getId())) {

            PANEL_dir directory = new PANEL_dir(dir);
            directory.setEventHandler(eventHandler);
            directory.setBounds(MARGIN, currentY, WIDTH - 40, HEIGHT / 7);
            directory.setHEIGHTandWIDTH(WIDTH - 40, HEIGHT / 7);
            panel.add(directory);
            currentY += directory.getHeight() + GAP;
        }

        updatePanelSize(currentY);
    }

    public void loadCurrentDirTasks(boolean showingFinished, ArrayList<Task> currentTasks) {
        listStages = DIRECTORY_MENU;
        panel.removeAll();

        if (currentTasks == null) {
            currentTasks = eventHandler.getTaskRepository()
                    .getTasksByDirectoryId(eventHandler.getCurrentDirectory().getId());
        }

        currentTasks.sort(Comparator.comparing(Task::isFinished));

        int currentY = 10;
        for (Task taskItem : currentTasks) {
            if (showingFinished || !taskItem.isFinished()) {
                PANEL_task task = new PANEL_task(taskItem);
                task.setEventHandler(eventHandler);
                task.setBounds(MARGIN, currentY, WIDTH - 40, HEIGHT / 7);
                task.setHEIGHTandWIDTH(HEIGHT / 7, WIDTH - 40);
                panel.add(task);
                currentY += task.getHeight() + GAP;
            }
        }

        updatePanelSize(currentY);
    }

    public void loadCurrentTasks(ArrayList<Task> currentTasks) {
        loadCurrentDirTasks(showingFinished, currentTasks);
    }

    public void loadCurrentTaskNotes() {
        listStages = TASK_MENU;
        panel.removeAll();

        int currentY = 10;
        for (Note n : eventHandler.getNoteRepository()
                .getNotesByTaskId(eventHandler.getCurrentTask().getId())) {

            PANEL_note note = new PANEL_note(n);
            note.setEventHandler(eventHandler);
            note.setBounds(MARGIN, currentY, WIDTH - 40, HEIGHT / 7);
            note.setHEIGHTandWIDTH(HEIGHT / 7, WIDTH - 40);
            panel.add(note);
            currentY += note.getHeight() + GAP;
        }

        updatePanelSize(currentY);
    }

    private void updatePanelSize(int currentY) {
        panel.setPreferredSize(new Dimension(WIDTH, currentY + MARGIN));
        panel.revalidate();
        panel.repaint();
        revalidate();
        repaint();
    }

    // === Stage / State Management ===
    public void setStage(ListStages stage) {
        listStages = stage;
        switch (stage) {
            case MAIN_MENU -> {
                loadArchives();
                eventHandler.resetCurrentArchive();
            }
            case ARCHIVE_MENU -> {
                loadDirs();
                eventHandler.resetCurrentDirectory();
            }
            case DIRECTORY_MENU -> {
                loadCurrentTasks(null);
                eventHandler.resetCurrentTask();
            }
            case TASK_MENU -> loadCurrentTaskNotes();
            case NOTE_CLICKED -> loadCurrentTaskNotes();
        }
    }

    public ListStages getStage() {
        return listStages;
    }

    public boolean isNoteSelected() {
        return noteSelected;
    }

    public void setNoteSelected(boolean selected) {
        noteSelected = selected;
    }

    public void switchShowingFinished() {
        showingFinished = !showingFinished;
    }

    public void sortTasksByUrgency(boolean ascending) {
        ArrayList<Task> sorted =
                eventHandler.getTaskRepository()
                        .sortTasksByUrgency(eventHandler.getCurrentDirectory().getId(), ascending);
        loadCurrentTasks(sorted);
    }

    public void sortByDifficulty(boolean ascending) {
        ArrayList<Task> sorted =
                eventHandler.getTaskRepository()
                        .sortTasksByDificulty(eventHandler.getCurrentDirectory().getId(), ascending);
        loadCurrentTasks(sorted);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        setStage(MAIN_MENU);
    }

    public void refreshComponents() {
        revalidate();
        repaint();
        for (Component component : panel.getComponents()) {
            component.repaint();
            component.revalidate();
        }
    }

    // === THEME REACTIVITY ===
    @Override
    public void onThemeChanged() {
        // Update panel background color
        panel.setBackground(ThemeLoader.getColor(ThemeColorKey.PANEL_LIST));

        // Repaint everything inside
        for (Component component : panel.getComponents()) {
            if (component instanceof ThemeChangeListener){
                ((ThemeChangeListener) component).onThemeChanged();
            }
            component.repaint();
            component.revalidate();
        }

        revalidate();
        repaint();
    }
}
