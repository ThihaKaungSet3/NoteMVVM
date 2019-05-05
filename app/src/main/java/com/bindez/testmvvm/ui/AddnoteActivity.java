package com.bindez.testmvvm.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.bindez.testmvvm.R;

public class AddnoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.bindez.testmvvm.ui.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.bindez.testmvvm.ui.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.bindez.testmvvm.ui.EXTRA_PRIORITY";
    private EditText mTitle;
    private EditText mDescription;
    private NumberPicker mPicker;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        initView();

        mPicker.setMinValue(1);
        mPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_nav,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_save :
                save();
                return true;
                default:  return super.onOptionsItemSelected(item);
        }

    }

    private void save() {
        String title = mTitle.getText().toString().trim();
        String description = mDescription.getText().toString().trim();
        int priority = mPicker.getValue();

        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DESCRIPTION,description);
        intent.putExtra(EXTRA_PRIORITY,priority);

        setResult(RESULT_OK,intent);
        finish();


    }

    private void initView(){
        mTitle = findViewById(R.id.edit_text_title);
        mDescription = findViewById(R.id.edit_text_Description);
        mPicker = findViewById(R.id.number_picker);
    }
}
