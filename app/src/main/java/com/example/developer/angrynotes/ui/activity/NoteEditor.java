package com.example.developer.angrynotes.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.developer.angrynotes.R;
import com.example.developer.angrynotes.data.storage.db.DbHandler;

/**
 * Created by Developer on 16.06.2016.
 */
public class NoteEditor extends AppCompatActivity {
    Toast mCurrentToast;
    TextInputEditText noteHeader;
    TextInputEditText textOfNote;
    long id;
    DbHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        db = new DbHandler(this);
        id = getIntent().getLongExtra("id", 0);
        noteHeader = (TextInputEditText) findViewById(R.id.noteHeader);
        textOfNote = (TextInputEditText) findViewById(R.id.textOfNote);
        noteHeader.setText(db.readNote(id).getTitleOfNote());
        textOfNote.setText(db.readNote(id).getTextOfNote());
    }

    public void onFABClick(View view) {
        showToast("Заметка сохранена");
        db.updateNote(id, noteHeader.getText().toString(), textOfNote.getText().toString());
        onBackPressed();
    }

    public void showToast(String text) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }
}