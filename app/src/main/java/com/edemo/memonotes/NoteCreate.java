package com.edemo.memonotes;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NoteCreate extends AppCompatActivity {

    final String FILENAME = "note";
    String note = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CreteNote);
        setContentView(R.layout.activity_note_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addListenerOnButton();

        final EditText editText = findViewById(R.id.editText);

        readFile();
        editText.setText(note);
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

    public void addListenerOnButton(){

        Button button = findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        EditText editText = findViewById(R.id.editText);
                        note = editText.getText().toString();
                        Toast.makeText(NoteCreate.this, note, Toast.LENGTH_SHORT).show();
                        writeFile(note);
                    }
                }
        );
    }

    void writeFile(String str) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            note = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
