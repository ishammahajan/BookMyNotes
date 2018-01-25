package com.TeamLiquid.bookmynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by root on 24/1/18.
 */

public class NotesAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotesObject> notenote;
    LayoutInflater inflater;

    public NotesAdapter(Context context, ArrayList<NotesObject> notenote, LayoutInflater inflater) {
        this.context = context;
        this.notenote = notenote;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return notenote.size();
    }

    @Override
    public Object getItem(int i) {
        return notenote.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NotesObject currentObject = (NotesObject) getItem(i);
        if (i%2 == 1) {
            view = inflater.inflate(R.layout.edittextview, null);
            EditText editText = view.findViewById(R.id.notes);
            editText.setText(currentObject.getEditabletext());
            return editText;
        }
        else {
            view = inflater.inflate(R.layout.imgview, null);
            ImageView imageView = view.findViewById(R.id.searchNotes);
            imageView.setImageURI(currentObject.getImguri());
            return imageView;
        }
    }

}
