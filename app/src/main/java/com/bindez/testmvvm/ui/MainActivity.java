package com.bindez.testmvvm.ui;


import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.bindez.testmvvm.Adapter.NoteAdapter;
import com.bindez.testmvvm.R;
import com.bindez.testmvvm.ViewModel.NoteViewModel;
import com.bindez.testmvvm.model.Note;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.List;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    public static final int ADD_NOTE_REQUEST = 1;
    private NoteViewModel noteViewModel;
    private BottomNavigationViewEx navigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationViewEx = findViewById(R.id.bottomnavbar);
        navigationViewEx.enableAnimation(false);
        navigationViewEx.setTextVisibility(false);
        navigationViewEx.enableItemShiftingMode(false);

        navigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home : Toast.makeText(getApplicationContext(),"Home" ,Toast.LENGTH_LONG).show();
                                        checkBottomNavMenuItem(R.id.navigation_home);
                                        break;
                    case R.id.navigation_foryou :   Toast.makeText(getApplicationContext(),"For you" ,Toast.LENGTH_LONG).show();
                        checkBottomNavMenuItem(R.id.navigation_foryou);
                                break;
                    case R.id.navigation_bookmark: Toast.makeText(getApplicationContext(),"Bookmark" ,Toast.LENGTH_LONG).show();
                        checkBottomNavMenuItem(R.id.navigation_bookmark);
                }
                return false;
            }
        });


        FloatingActionButton floatingActionButton = findViewById(R.id.floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddnoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getNoteLists().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Toast.makeText(getApplicationContext(),notes + "",Toast.LENGTH_LONG).show();
                Log.d("Notes",notes + "");
                adapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddnoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddnoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddnoteActivity.EXTRA_PRIORITY,1);

            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
        }
        else {
            Toast.makeText(this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkBottomNavMenuItem(int itemId){
        Menu menu = navigationViewEx.getMenu();
        MenuItem menuItem = menu.findItem(itemId);

        if (!menuItem.isChecked()){
            menuItem.setChecked(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_deleteallnotes : noteViewModel.deleteAllNotes();
            return true;
            default:  return super.onOptionsItemSelected(item);
        }
    }
}
