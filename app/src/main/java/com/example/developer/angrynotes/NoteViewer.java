package com.example.developer.angrynotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Developer on 16.06.2016.
 */
public class NoteViewer extends AppCompatActivity {
    Toast mCurrentToast;
    TextView noteHeader;
    TextView textOfNote;
    long id;
    DbHandler db;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_viewer);
        db = new DbHandler(this);
        id = getIntent().getLongExtra("id", 0);
        noteHeader = (TextView) findViewById(R.id.noteHeader);
        textOfNote = (TextView) findViewById(R.id.textOfNote);
        noteHeader.setText(db.readNote(id).getTitleOfNote());
        textOfNote.setText(db.readNote(id).getTextOfNote());
    }

    @Override
    protected void onResume() {
        noteHeader.setText(db.readNote(id).getTitleOfNote());
        textOfNote.setText(db.readNote(id).getTextOfNote());
        super.onResume();
    }

    public void onFABClick(View view) {
        showToast("Редактировать заметку");
        intent = new Intent(this, NoteEditor.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void showToast(String text) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }
}