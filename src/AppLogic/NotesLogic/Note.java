package AppLogic.NotesLogic;

import java.util.Date;

public class Note {
    private String date;
    private String note;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
        return "{date:" + date+";" +"note:" + note+"}\n"  ;
    }
}
