package AppLogic.TaskLogic;

import AppLogic.NotesLogic.Note;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    private ArrayList<Note> notes = new ArrayList<>();
    private String Name;
    private String Description;
    private int TimeDedicated;
    private boolean repeatable = false;
    private boolean finished = false;
    private String deadline;
    private int urgency;


    public int getUrgency() {
        return urgency;
    }
    public int getTimeDedicated() {
        return TimeDedicated;
    }
    public void setTimeDedicated(int timeDedicated) {
        TimeDedicated = timeDedicated;
    }
    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }
    public ArrayList<Note> getNotes() {
        return notes;
    }
    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public boolean isRepeatable() {
        return repeatable;
    }
    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }
    public boolean isFinished() {
        return finished;
    }
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public void addNote(Note note) {
        notes.add(note);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{name: "+Name+";description:"+Description+";isRepeatable:"+repeatable+";isFinished:"+finished+";deadline:"+deadline+";urgency:"+urgency+";Time:"+TimeDedicated+"}");
        sb.append("\n");
        return sb.toString();
    }
}
