package com.example.developer.angrynotes.data.storage.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.developer.angrynotes.data.storage.models.Note;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME  = "NOTES_DB";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID  = "id";
    private static final String KEY_HEADER = "header";
    private static final String KEY_TEXT_OF_NOTE = "text_of_note";

    public DbHandler(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HEADER + " TEXT, "
                + KEY_TEXT_OF_NOTE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long addNote(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_HEADER, note.getTitleOfNote());
        cv.put(KEY_TEXT_OF_NOTE, note.getTextOfNote());
        long tempLong = db.insert(TABLE_NOTES, null, cv);
        db.close();
        return tempLong;
    }

    public void updateNote(long id, String header, String textOfNote) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_HEADER, header);
        cv.put(KEY_TEXT_OF_NOTE, textOfNote);
        db.update(TABLE_NOTES, cv, KEY_ID + " = " + id, null);
        db.close();
    }

    public void deleteNote(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = " + id, null);
        db.close();
    }
    public Note readNote(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_ID + " = " + id, null);
        cursor.moveToFirst();
        Note tempNote = new Note();
        tempNote.setId(cursor.getLong(0));
        tempNote.setTitleOfNote(cursor.getString(1));
        tempNote.setTextOfNote(cursor.getString(2));
        return tempNote;
    }

    public List<Note> getAllNotes(){
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES, null);
        while (cursor.moveToNext()){
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setTitleOfNote(cursor.getString(1));
            note.setTextOfNote(cursor.getString(2));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public int getNotesCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES, null);
        return cursor.getCount();
    }


}
