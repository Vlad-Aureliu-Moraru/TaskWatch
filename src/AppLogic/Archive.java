package AppLogic;

import java.util.ArrayList;

public class Archive {
    private ArrayList<Directory> directories =  new ArrayList<>();
    private String archiveName;


    public Archive(String archiveName){
        this.archiveName = archiveName;
    }
    public ArrayList<Directory> getDirectories() {
        return directories;
    }
    public void setDirectories(ArrayList<Directory> directories) {
        this.directories = directories;
    }
    public void addDirectory(Directory directory) {
        directories.add(directory);
    }
    public String getArchiveName() {
        return archiveName;
    }
    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    @Override
    public String toString() {
        String directoriesString = "[";
        for(Directory dir : directories){
            directoriesString+=dir.getName()+",";
        }
        directoriesString = directoriesString.substring(0,directoriesString.length()-1);
        directoriesString+="]";
        return "{"+"name:"+archiveName+";dirs:"+directoriesString+"}\n";

    }
}
