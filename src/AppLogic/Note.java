package AppLogic;

public class Note {
    private String date;
    private String note;
    private int id;
    private int TaskId;

    public Note(int id, int taskId, String date, String note) {
        this.id = id;
        this.TaskId = taskId;
        this.date = date;
        this.note = note;
    }

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    @Override
    public String toString() {
        return "{id:"+id+"taskId:"+TaskId+";date:" + date+";" +"note:" + note+"}\n"  ;
    }
}
