package AppLogic.TaskLogic;

import AppLogic.NotesLogic.Note;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    private ArrayList<Note> notes = new ArrayList<>();
    private String Name;
    private String Description;
    private int TimeDedicated = 0 ;
    private boolean repeatable = false;
    private boolean finished = false;
    private String deadline = null;
    private int urgency =1;
    private String repeatableType="none";
    private int difficulty=1;


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
    public String getRepeatableType() {
        return repeatableType;
    }
    public void setRepeatableType(String repeatableType) {
        this.repeatableType = repeatableType;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("name:"+Name+";description:"+Description);
        sb.append(";isRepeatable:"+repeatable+";isFinished:"+finished);
        sb.append(";deadline:"+deadline+";urgency:"+urgency);
        sb.append(";time:"+TimeDedicated+";difficulty:"+difficulty);
        sb.append(";repeatableType:"+repeatableType);
        sb.append("}");
        sb.append("\n");
        return sb.toString();
    }
}
