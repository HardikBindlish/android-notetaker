package com.example.notetaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {
    ArrayList<Notes> notes;
    Context context;

    public NoteAdapter(Context context, int resource, ArrayList<Notes> notes) {
        this.notes=notes;
        this.context=context;
    }

    public void update(ArrayList<Notes> result){
        if(notes != null && notes.size() > 0){
//            notes.clear();
            notes = new ArrayList<>();
            notes.addAll(result);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, null);
        }

        Notes notes = (Notes) getItem(position);
        if(notes != null){
            TextView title = (TextView)convertView.findViewById(R.id.list_note_title);
            TextView date = (TextView)convertView.findViewById(R.id.list_note_date);
            TextView content = (TextView)convertView.findViewById(R.id.list_note_content);

            title.setText(notes.getTitle());
            date.setText(notes.getDateTimeFormatted(context));

            if(notes.getContext().length() > 50 ) {
                content.setText(notes.getContext().substring(0,50));
            }
            else{
                content.setText(notes.getContext());
            }
        }
        return convertView;
    }
}
