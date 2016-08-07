package com.example.developer.angrynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    Toast mCurrentToast;
    RecyclerView rvListOfNotes;
    List<Note> notes = new ArrayList<Note>();
    Intent intent;
    private static long back_pressed;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        rvListOfNotes = (RecyclerView) findViewById(R.id.rv_list_of_notes);
        db = new DbHandler(this);
        onResume();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else showToast(getString(R.string.doble_back_notify));
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvListOfNotes.setLayoutManager(llm);
        final RVAdapter rvAdapter = new RVAdapter(dataForRecyclerView());
        rvListOfNotes.setAdapter(rvAdapter);
        ItemClickSupport.addTo(rvListOfNotes).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, final View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_item:
                                intent = new Intent(v.getContext(), NoteEditor.class);
                                intent.putExtra("id", notes.get(position).getId());
                                startActivity(intent);
                                break;
                            case R.id.menu_delete_item:
                                showToast("Заметка удалена");
                                db.deleteNote(notes.get(position).getId());
                                rvAdapter.notes.remove(position);
                                rvAdapter.notifyItemRemoved(position);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });

        ItemClickSupport.addTo(rvListOfNotes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                intent = new Intent(v.getContext(), NoteViewer.class);
                intent.putExtra("id", notes.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void onFABClick(View view) {
        showToast("Создать новую заметку");
        Long id = db.addNote(new Note("Введите текст заголовка заметки", "Введите текст заметки"));
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

    public List<Note> dataForRecyclerView() {
        if (db.getAllNotes().isEmpty()) {
            db.addNote(new Note("Пример заметки", getString(R.string.dummyText)));
            notes = db.getAllNotes();
        } else notes = db.getAllNotes();
        return notes;

    }

}
