package com.edemo.memonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;


public class NoteCreate extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    String note = "";
    String intentnote = "";
    String intentname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CreteNote);
        setContentView(R.layout.activity_note_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addListenerOnButton();

        Intent intent = getIntent();
        intentnote = intent.getStringExtra("intentnote");
        intentname = intent.getStringExtra("intentname");
        if(!intentnote.equals("")){
            EditText editText = findViewById(R.id.editText);
            editText.setText(intentnote);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_note_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        EditText editText = findViewById(R.id.editText);
        if(editText.getText().toString().equals("") || editText.getText().toString().equals(intentnote)){
            super.onBackPressed();
        } else if (!intentnote.equals("")) {
            note = editText.getText().toString();
            updateFile(note);
            Toast.makeText(NoteCreate.this, R.string.Updated, Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            note = editText.getText().toString();
            Toast.makeText(NoteCreate.this, R.string.Saved, Toast.LENGTH_SHORT).show();
            writeFile(note);
            super.onBackPressed();
        }

    }

    public void addListenerOnButton(){

        final EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(editText.getText().toString().equals("") ){
                            Toast.makeText(NoteCreate.this, R.string.noMessage, Toast.LENGTH_SHORT).show();
                        } else if (editText.getText().toString().equals(intentnote)){
                            Toast.makeText(NoteCreate.this, R.string.AlreadyCreated, Toast.LENGTH_SHORT).show();
                        } else if (!intentnote.equals("")) {
                            EditText editText = findViewById(R.id.editText);
                            note = editText.getText().toString();
                            updateFile(note);
                            Toast.makeText(NoteCreate.this, R.string.Updated, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            EditText editText = findViewById(R.id.editText);
                            note = editText.getText().toString();
                            writeFile(note);
                            Toast.makeText(NoteCreate.this, R.string.Saved, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
        );
    }

    public void writeFile(String str) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("note", str);
        cv.put("name", str.substring(0, 10));
        long rowID = db.insert("notes", null, cv);
        Log.d("log", "rowID = " + rowID);
        dbHelper.close();
    }

    public void updateFile(String str){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("note", str);
        cv.put("name", str.substring(0, 10));
        long rowID = db.update("notes", cv,"name = " + "'" + intentname + "'", null);
        Log.d("log", "rowID = " + rowID);
        dbHelper.close();
    }
}
