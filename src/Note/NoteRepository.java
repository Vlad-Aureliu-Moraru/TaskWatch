package Note;

import Task.TaskRepository;
import Note.Model.Note;

import java.io.*;
import java.util.ArrayList;

public class NoteRepository {

    private static final String FILE_PATH = "repository/notes.txt";
    private static final ArrayList<Note> notes = new ArrayList<>();
    private static boolean isLoaded = false;
    private static int currentId = 0;

    public NoteRepository() {
        if (!isLoaded) {
            loadNotesFromFile();
            isLoaded = true;
        }
    }

    // Load notes from file
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
            boolean idLineFound = false;
            int maxNoteId = -1;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Handle id line
                if (line.startsWith("id=#") && line.endsWith("#")) {
                    try {
                        String idValue = line.substring(4, line.length() - 1);
                        currentId = Integer.parseInt(idValue);
                        idLineFound = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format in file: " + line);
                    }
                    continue;
                }

                // Parse line: {id:x taskId:y;date:z;note:w}
                try {
                    int idStart = line.indexOf("id:") + 3;
                    int idEnd = line.indexOf(";", idStart);
                    int id = Integer.parseInt(line.substring(idStart, idEnd).trim());

                    int taskIdStart = line.indexOf("taskId:") + 7;
                    int taskIdEnd = line.indexOf(";", taskIdStart);
                    int taskId = Integer.parseInt(line.substring(taskIdStart, taskIdEnd).trim());

                    int dateStart = line.indexOf("date:") + 5;
                    int dateEnd = line.indexOf(";", dateStart);
                    String date = line.substring(dateStart, dateEnd).trim();

                    int noteStart = line.indexOf("note:") + 5;
                    int noteEnd = line.indexOf("}", noteStart);
                    String noteText = line.substring(noteStart, noteEnd).trim();

                    notes.add(new Note(id, taskId, date, noteText));
                    maxNoteId = Math.max(maxNoteId, id);
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

            // If id line not found, compute new one
            if (!idLineFound) {
                if (maxNoteId >= 0)
                    currentId = maxNoteId + 1;
                else
                    currentId = 0;
                saveAllToFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save all notes + currentId to file
    public void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            writer.write("id=#" + currentId + "#\n");

            for (Note n : notes) {
                writer.write("{id:" + n.getId() + ";taskId:" + n.getTaskId() + ";date:" + n.getDate() + ";note:" + n.getNote() + "}\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters for ID handling
    public static int getCurrentId() {
        return currentId;
    }

    public static int getNextId() {
        currentId++;
        return currentId;
    }

    // Get all notes
    public ArrayList<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    // Get note by ID
    public Note getNoteById(int id) {
        for (Note n : notes) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    // Get notes by task ID
    public ArrayList<Note> getNotesByTaskId(int taskId) {
        ArrayList<Note> result = new ArrayList<>();
        for (Note n : notes) {
            if (n.getTaskId() == taskId) result.add(n);
        }
        return result;
    }

    // Add a new note (auto ID)
    public void addNote(int taskId, Note note) {
        int newId = getNextId();
        Note n = new Note(newId, taskId, note.getDate(), note.getNote());
        notes.add(n);
        saveAllToFile();
    }

    // Update a note
    public void updateNote(int id, String newText) {
        for (Note n : notes) {
            if (n.getId() == id) {
                n.setNote(newText);
                saveAllToFile();
                return;
            }
        }
        System.out.println("[WARNING] Note with ID " + id + " not found!");
    }

    // Delete a note
    public void deleteNote(int id) {
        notes.removeIf(n -> n.getId() == id);
        saveAllToFile();
    }
    public void deleteNoteByTaskId(int id) {
        notes.removeIf(n -> n.getTaskId() == id);
        saveAllToFile();
    }
    public static void main(String[] args) {
        TaskRepository repo = new TaskRepository();

    }
}
