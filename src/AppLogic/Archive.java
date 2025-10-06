package AppLogic;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Archive {
    private String archiveName;
    private int id;


    public Archive(int id ,String archiveName) {
        this.archiveName = archiveName;
        this.id = id;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{id:"+id+", archiveName:"+archiveName+"}\n";
    }
}
