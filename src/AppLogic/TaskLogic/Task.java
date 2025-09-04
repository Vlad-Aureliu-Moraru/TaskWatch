package AppLogic.TaskLogic;

import AppLogic.NotesLogic.Notes;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    private ArrayList<Notes> notes;
    private String Name;
    private String Description;
    private boolean repeatable;
    private boolean finished;
    private Date deadline;
    private int urgency;

    public ArrayList<Notes> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Notes> notes) {
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
