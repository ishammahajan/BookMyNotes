package com.TeamLiquid.bookmynotes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class notesGridView extends Activity {

    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view);

        SearchView searchNotes;
        final ImageView showimg;
        final EditText notes;
        Button addImg;
        final ArrayList<NotesObject> content = new ArrayList<>();
        final int[] j = {0}; //To store the position of the arraylist of notesObject I am in right now.

        searchNotes = findViewById(R.id.searchNotes);
        notes = findViewById(R.id.write);
        addImg = findViewById(R.id.addImg);

        final NotesAdapter adaptbitch = new NotesAdapter(getApplicationContext(), content, getLayoutInflater());
        final ListView notelist = findViewById(R.id.notesList);
        notelist.setAdapter(adaptbitch);

        //Add button onclick method to capture images.
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Saving text typed out before this point.
                NotesObject note1 = new NotesObject();
                getTheStuff();
                note1.setEditabletext(notes.getText());
                content.add(note1);
                saveTheStuff(content);

                //Taking new picture and storing it.
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent.
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go.
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        Log.d(null, "Created file.");

                    } catch (IOException ex) {
                        Log.d("No cam application", "No FILE. Please CREATE FILE.");
                    }
                    // Continue only if the File was successfully created.
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.android.fileprovider",
                                photoFile);

                        //Saving URI of the to be generated image in the content file.
                        note1.setImguri(photoURI);
                        content.add(note1);

                        //Actually taking the picture by action activity.
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, 1);


                    }
                }

                //Creating the view again.
                NotesAdapter adaptbitch = new NotesAdapter(getApplicationContext(), content, getLayoutInflater());
                ListView notelist = findViewById(R.id.notesList);

            }
        });

    }

    private ArrayList<NotesObject> getTheStuff() {
        try {
            SharedPreferences subjects = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Gson gsonSUBJECTS = new Gson();
            String jsonSUBJECTS = subjects.getString("notes", "");
            Type type = new TypeToken<ArrayList<NotesObject>>(){}.getType();
            ArrayList<NotesObject> content = gsonSUBJECTS.fromJson(jsonSUBJECTS, type);
            return content;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveTheStuff(ArrayList<NotesObject> content) {
        SharedPreferences subjects = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor1 = subjects.edit();
        Gson gsonNotes = new Gson();
        String jsonNotes = gsonNotes.toJson(content);
        editor1.putString("notes", jsonNotes);
        editor1.apply();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
