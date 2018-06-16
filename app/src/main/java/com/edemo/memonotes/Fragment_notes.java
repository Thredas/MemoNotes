package com.edemo.memonotes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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
                startActivity(intent);
            }
        });

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ArrayList<String> myDataset = getDataSet();

        RecyclerView mRecyclerView = v.findViewById(R.id.recView);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "item " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "There is  " + notes.size(), Toast.LENGTH_SHORT).show();
                    }
                })
        );

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(false);

        // используем linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public ArrayList<String> getDataSet() {

        for (int i = 0; i < 10; i++) {
            notes.add("item" + i);
        }
        return notes;
    }

    public void add(String str){
        notes.add(str);
    }
}
