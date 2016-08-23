package com.example.developer.angrynotes.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.developer.angrynotes.R;
import com.example.developer.angrynotes.data.storage.models.Note;

import java.util.List;

/**
 * Created by Developer on 13.06.2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NoteViewHolder> {

    public List<Note> notes;

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        TextView noteText;

        public NoteViewHolder(View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteText = (TextView) itemView.findViewById(R.id.note_text);
        }
    }

    public RVAdapter(List<Note> notes) {
        this.notes = notes;

    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.item_for_recyclerview, null);
        NoteViewHolder noteViewHolder = new NoteViewHolder(v);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.noteTitle.setText(notes.get(position).getTitleOfNote());
        holder.noteText.setText(notes.get(position).getTextOfNote());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}