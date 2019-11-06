package com.example.notetaker;

        import android.content.Intent;
        import android.support.v4.view.MenuItemCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.support.v7.widget.SearchView;
        import android.widget.Toast;
        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mListViewNotes;
    ArrayList<Notes> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewNotes = (ListView)findViewById(R.id.main_listview_notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Notes> result = new ArrayList<>();
                for(Notes x: notes){
                    if(x.getTitle().contains(newText)){
                        result.add(x);
                    }
                }
                ((NoteAdapter)mListViewNotes.getAdapter()).update(result);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_main_new_note:
                Intent intent = new Intent(this,NoteActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListViewNotes.setAdapter(null);

        notes = Utilities.getAllSavedNotes(this);
        if(notes == null || notes.size() == 0){
            Toast.makeText(this,"You have no saved notes.",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            NoteAdapter na = new NoteAdapter(this,R.layout.item_note,notes);
            mListViewNotes.setAdapter(na);

            mListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String filename = ((Notes)mListViewNotes.getItemAtPosition(position)).getDateTime() + Utilities.FILE_EXTENSION;
                    Intent tent = new Intent(getApplicationContext(),NoteActivity.class);
                    tent.putExtra("NOTE_FILE",filename);
                    startActivity(tent);
                }
            });
        }
    }
}
