package com.example.notetaker;


import android.content.Context;
import android.provider.ContactsContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class Utilities {

    public static final String FILE_EXTENSION = ".bin";

    public static boolean saveNote(Context context, Notes note){
        String filename = String.valueOf(note.getDateTime()) + FILE_EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(filename, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
            fos.close();

        }catch (IOException e ){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static ArrayList<Notes> getAllSavedNotes(Context context){
        ArrayList<Notes> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for(String file : filesDir.list()){
            if(file.endsWith(FILE_EXTENSION)){
                noteFiles.add(file);
            }
        }
        FileInputStream fis;
        ObjectInputStream ois;

        for(int i = 0; i<noteFiles.size(); i++){
            try{
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);
                notes.add((Notes) ois.readObject());

                fis.close();
                ois.close();

            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return notes;
    }

    public static Notes getNoteByName(Context context,String fileName){
        File file = new File(context.getFilesDir(),fileName);
        Notes note;

        if(file.exists()){
            FileInputStream fis;
            ObjectInputStream ois;
            try {
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);

                note = (Notes) ois.readObject();

                fis.close();
                ois.close();
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
            return note;
        }
        return null;
    }

    public static void deleteNote(Context context,String fileName){
        File file = new File(context.getFilesDir(),fileName);

        if(file.exists()){
            file.delete();
        }
    }
}
