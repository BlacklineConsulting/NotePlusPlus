package consulting.blackline.NotePlusPlus;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EasyNoteTable")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;
    private String content;
    private String date;
    private String time;

    Note(String title,String content,String date, String time){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
