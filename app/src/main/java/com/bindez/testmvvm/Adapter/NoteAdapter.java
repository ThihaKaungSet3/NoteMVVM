package com.bindez.testmvvm.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bindez.testmvvm.R;
import com.bindez.testmvvm.model.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context mContext;
    private List<Note> notes= new ArrayList<>();

    public NoteAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(mContext).inflate(R.layout.note_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentnote = notes.get(position);

        holder.mPriority.setText(currentnote.getPriority() + "");
        holder.mDescription.setText(currentnote.getDescription());
        holder.mTitle.setText(currentnote.getTitle());

        Log.d("Note",currentnote.getDescription() + currentnote.getPriority() + currentnote.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    protected class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mPriority;
        private TextView mDescription;

        public  NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.text_view_title);
            mDescription = itemView.findViewById(R.id.text_view_description);
            mPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
