package com.edemo.memonotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Fragment_notes extends Fragment {

    FloatingActionButton fab;
    public ArrayList<String> notes = new ArrayList<>();

    public Fragment_notes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        fab = v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.NoteCreate");
                intent.putExtra("intentnote", "");
                intent.putExtra("intentname", "");
                startActivity(intent);
            }
        });

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = v.findViewById(R.id.recView);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        DBHelper dbHelper = new DBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        String name = notes.get(position).substring(0, 10) + "'";
                        Cursor c = db.query("notes", null, null, null, null, null, null);

                        if (c.moveToFirst()) {
                            // определяем номера столбцов по имени в выборке
                            name = name.substring(0, name.length() - 1);
                            do {
                                int nameColIndex = c.getColumnIndex("name");
                                int noteColIndex = c.getColumnIndex("note");
                                Log.d("log", "name = " + name + " colIndex = " + c.getString(nameColIndex));
                                if(c.getString(nameColIndex).equals(name)) {
                                    Intent intent = new Intent("android.intent.action.NoteCreate");
                                    intent.putExtra("intentnote", c.getString(noteColIndex));
                                    intent.putExtra("intentname", c.getString(nameColIndex));
                                    startActivity(intent);
                                    Log.d("log", "colIndex = " + c.getString(nameColIndex) + ". Name = "  + name + ". Note = " + c.getString(noteColIndex));
                                }
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        DBHelper dbHelper = new DBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        String name = "'" + notes.get(position).substring(0, 10) + "'";
                        int delCount = db.delete("notes", "name = " + name, null);
                        Log.d("log", "deleted rows count = " + delCount + ". Deleted id = "  + name);
                        db.close();
                        Toast.makeText(getActivity(), R.string.Deleted, Toast.LENGTH_SHORT).show();
                    }
                })
        );

        // используем linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        TextView noNotes = v.findViewById(R.id.noNotes);

        if(notes.size() == 0){
            noNotes.setVisibility(View.VISIBLE);
        } else {
            noNotes.setVisibility(View.INVISIBLE);
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public ArrayList<String> getDataSet() {
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("notes", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int noteColIndex = c.getColumnIndex("note");

            do {

                if(c.getString(noteColIndex).length() > 250) {
                    notes.add(c.getString(noteColIndex).substring(0, 250) + "...");
                } else {
                    notes.add(c.getString(noteColIndex));
                }

            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();
        return notes;
    }
}
