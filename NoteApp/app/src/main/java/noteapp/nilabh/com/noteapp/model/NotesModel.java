package noteapp.nilabh.com.noteapp.model;


import java.util.Date;

/**
 * Created by nilabh on 11-07-2017.
 */

public class NotesModel {
    int noteId;
    String title;
    String description;
    Date createdDate;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdDate;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdDate = createdTime;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    String imgFileName;
}
