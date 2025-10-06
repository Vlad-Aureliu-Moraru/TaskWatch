package Handlers.Repositories;
import AppLogic.Note;

import java.io.*;
import java.util.ArrayList;

public class NoteRepository {

    private static final String FILE_PATH = "repository/notes.txt";
    private static final ArrayList<Note> notes = new ArrayList<>();
    private static boolean isLoaded = false;

    public NoteRepository() {
        if (!isLoaded) {
            loadNotesFromFile();
            isLoaded = true;
        }
    }

    private void loadNotesFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Notes file not found. Created new one.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    int idStart = line.indexOf("id:") + 2;
                    int idEnd = line.indexOf(",", idStart);
                    int id = Integer.parseInt(line.substring(idStart, idEnd).trim());

                    int taskIdStart = line.indexOf("taskId:") + 6;
                    int taskIdEnd = line.indexOf(";", taskIdStart);
                    int taskId = Integer.parseInt(line.substring(taskIdStart, taskIdEnd).trim());

                    int dateStart = line.indexOf("date:") + 5;
                    int dateEnd = line.indexOf(";", dateStart);
                    String date = line.substring(dateStart, dateEnd).trim();

                    int noteStart = line.indexOf("note:") + 5;
                    int noteEnd = line.indexOf("}", noteStart);
                    if (noteEnd == -1) noteEnd = line.length();
                    String noteText = line.substring(noteStart, noteEnd).trim();

                    notes.add(new Note(id, taskId, date, noteText));
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Note n : notes) {
                writer.write("{id:" + n.getId() + ";taskId:" + n.getTaskId() + ";date:" + n.getDate() + ";note:" + n.getNote() + "}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    public Note getNoteById(int id) {
        for (Note n : notes) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    public void addNote(Note note) {
        boolean exists = notes.stream().anyMatch(n -> n.getId() == note.getId());
        if (exists) {
            System.out.println("[WARNING] Note with this ID already exists!");
            return;
        }
        notes.add(note);
        saveAllToFile();
    }
    public void updateNote(int id, String newDate, String newNoteText) {
        for (Note n : notes) {
            if (n.getId() == id) {
                n.setDate(newDate);
                n.setNote(newNoteText);
                saveAllToFile();
                break;
            }
        }
    }
    public void deleteNote(int id) {
        notes.removeIf(n -> n.getId() == id);
        saveAllToFile();
    }
    public static void main(String[] args){
        NoteRepository repo = new NoteRepository();
        repo.addNote(new Note(1,2,"23/4/33","test"));
        Note note =  repo.getNoteById(1);
        System.out.println(note.getTaskId());
        repo.updateNote(1,"23/23","testt");
    }
}
