package com.TeamLiquid.bookmynotes;

import android.net.Uri;
import android.text.Editable;

/**
 * Created by root on 24/1/18.
 */

public class NotesObject {
    android.text.Editable editabletext;
    Uri imguri;

    public Uri getImguri() {
        return imguri;
    }

    public void setImguri(Uri imguri) {
        this.imguri = imguri;
    }

    public Editable getEditabletext() {

        return editabletext;
    }

    public void setEditabletext(Editable editabletext) {
        this.editabletext = editabletext;
    }
}
