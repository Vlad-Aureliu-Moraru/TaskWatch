package AppLogic;

import java.util.ArrayList;
import java.util.Comparator;

public class Directory {
    private String Name;
    private int id;
    private int ArchiveId;


    public Directory( int id, int ArchiveId, String Name) {
        this.Name = Name;
        this.id = id;
        this.ArchiveId = ArchiveId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArchiveId() {
        return ArchiveId;
    }

    public void setArchiveId(int archiveId) {
        ArchiveId = archiveId;
    }
    public String toString(){
        return "{id:"+id+";archiveId:"+ArchiveId+";name:"+Name+"}\n";
    }
}
