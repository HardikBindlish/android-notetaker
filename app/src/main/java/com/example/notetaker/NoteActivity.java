package com.example.notetaker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    private EditText mEtTitle,mEtContext;
    private String mNoteFileName;
    private Notes mLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtTitle = (EditText)findViewById(R.id.note_et_title);
        mEtContext = (EditText)findViewById(R.id.note_et_content);

        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName != null && !mNoteFileName.isEmpty()){
            mLoadedNote = Utilities.getNoteByName(this,mNoteFileName);
            if(mLoadedNote != null){
                mEtTitle.setText(mLoadedNote.getTitle());
                mEtContext.setText(mLoadedNote.getContext());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_note_save:
                saveNote();
            break;

            case R.id.action_note_delete:
                deleteNote();
            break;
        }
        return true;
    }

    private void saveNote(){
        Notes notes;

        if(mEtTitle.getText().toString().trim().isEmpty() || mEtContext.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Please fill both the fields!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(mLoadedNote == null) {
            notes = new Notes(System.currentTimeMillis(), mEtTitle.getText().toString(), mEtContext.getText().toString());
        }
        else{
            notes = new Notes(mLoadedNote.getDateTime(), mEtTitle.getText().toString(), mEtContext.getText().toString());
        }
            if(Utilities.saveNote(this, notes)){
            Toast.makeText(this,"Your note is saved.",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"YOur note is not saved, You don't have enough space",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteNote(){
        if(mLoadedNote == null){
            finish();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("You are about to delete " + mEtTitle.getText().toString() +", are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteNote(getApplicationContext(), mLoadedNote.getDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(),mEtTitle.getText().toString() + "is deleted",Toast.LENGTH_LONG).show();
                            finish();
                        }})
                    .setNegativeButton("no",null)
                    .setCancelable(false);

            builder.show();
        }
    }
}
