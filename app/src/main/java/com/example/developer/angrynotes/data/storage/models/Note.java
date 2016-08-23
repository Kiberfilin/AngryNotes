package com.example.developer.angrynotes.data.storage.models;

/**
 * Created by Developer on 13.06.2016.
 */
public class Note {
    private long id;
    private String titleOfNote;
    private String textOfNote;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleOfNote() {
        return titleOfNote;
    }

    public void setTitleOfNote(String titleOfNote) {
        this.titleOfNote = titleOfNote;
    }

    public String getTextOfNote() {
        return textOfNote;
    }

    public void setTextOfNote(String textOfNote) {
        this.textOfNote = textOfNote;
    }

    public Note() {}

    public Note(String titleOfNote, String textOfNote) {

        this.titleOfNote = titleOfNote;
        this.textOfNote = textOfNote;
    }

    public Note(Long id, String titleOfNote, String textOfNote) {

        this.id = id;
        this.titleOfNote = titleOfNote;
        this.textOfNote = textOfNote;
    }
}
