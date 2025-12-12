package Task.Model;

public class Task {
    private int id;
    private int DirectoryId;
    private String Name;
    private String Description;
    private int TimeDedicated = 0 ;
    private boolean repeatable = false;
    private boolean finished = false;
    private String deadline = "none";
    private int urgency =1;
    private String repeatableType="none";
    private int difficulty=1;
    private String finishedDate ="none";
    private boolean hasToBeCompletedToRepeat =true;
    private String repeatOnSpecificDay = "none";
    public Task() {}
    public Task(int id, int directoryId, String name, String description, boolean repeatable,
                boolean finished, String deadline, int urgency, int timeDedicated,
                int difficulty, String repeatableType, String finishedDate, boolean hasToBeCompletedToRepeat, String repeatOnSpecificDay) {
        this.id = id;
        this.repeatOnSpecificDay = repeatOnSpecificDay;
        this.hasToBeCompletedToRepeat = hasToBeCompletedToRepeat;
        this.DirectoryId = directoryId;
        this.Name = name;
        this.Description = description;
        this.repeatable = repeatable;
        this.finished = finished;
        this.deadline = deadline;
        this.urgency = urgency;
        this.TimeDedicated = timeDedicated;
        this.difficulty = difficulty;
        this.repeatableType = repeatableType;
        this.finishedDate = finishedDate;
    }
    public String getFinishedDate() {
        return finishedDate;
    }
    public void setFinishedDate(String finished) {
        this.finishedDate = finished;
    }
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
    public boolean isHasToBeCompletedToRepeat() {
        return hasToBeCompletedToRepeat;
    }
    public void setHasToBeCompletedToRepeat(boolean hasToBeCompletedToRepeat) {
        this.hasToBeCompletedToRepeat = hasToBeCompletedToRepeat;
    }
    public String getRepeatOnSpecificDay() {
        return repeatOnSpecificDay;
    }
    public void setRepeatOnSpecificDay(String repeatOnSpecificDay) {
        this.repeatOnSpecificDay = repeatOnSpecificDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDirectoryId() {
        return DirectoryId;
    }

    public void setDirectoryId(int directoryId) {
        DirectoryId = directoryId;
    }

    public String toString(){
        return "{" +
                "id:"+id + ";DirectoryId:"+DirectoryId+
                ";name:" + Name + ";description:" + Description +
                ";isRepeatable:" + repeatable + ";isFinished:" + finished +
                ";deadline:" + deadline + ";urgency:" + urgency +
                ";time:" + TimeDedicated + ";difficulty:" + difficulty +
                ";repeatableType:" + repeatableType + ";finishedDate:" + finishedDate +
                ";hasToBeCompletedToRepeat:" + hasToBeCompletedToRepeat +
                ";repeatOnSpecificDay:" + repeatOnSpecificDay +
                "}" +
                "\n";
    }
}
