package com.edemo.memonotes;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

            final FragmentManager fragmentManager = getFragmentManager();

            // define your fragments here
            final Fragment fragment1 = new Fragment_notes();
            final Fragment fragment2 = new Fragment_task_lists();
            final Fragment fragment3 = new Fragment_time_tables();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment1).commit();

            // handle navigation selection
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_notes:
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayout, fragment1).commit();
                                    return true;
                                case R.id.action_task_lists:
                                    FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                                    fragmentTransaction2.replace(R.id.frameLayout, fragment2).commit();
                                    return true;
                                case R.id.action_time_tables:
                                    FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                                    fragmentTransaction3.replace(R.id.frameLayout, fragment3).commit();
                                    return true;
                            }
                            return false;
                        }
                    });

    }

}
