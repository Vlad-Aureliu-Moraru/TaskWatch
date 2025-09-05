package AppLogic.NotesLogic;

import java.util.Date;

public class Note {
    private Date date;
    private String note;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "{" +
                "date:" + date +
                "; note:" + note  +
                '}';
    }
}
