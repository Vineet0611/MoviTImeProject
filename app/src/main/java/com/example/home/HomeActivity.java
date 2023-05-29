package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView city;
    private long lastClick = 0;
    private Fragment[] fragments;
    private int currentFragmentIndex;
    public MeowBottomNavigation btn_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefLocation = getSharedPreferences("location", MODE_PRIVATE);
        String location = prefLocation.getString("city", "Select City");
        city = findViewById(R.id.location);
        city.setText(location);


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClick < 2000){  //Single Click
                    return;
                }
                lastClick = SystemClock.elapsedRealtime();
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_nav = findViewById(R.id.bottom_nav);
        btn_nav.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        btn_nav.add(new MeowBottomNavigation.Model(2, R.drawable.search));
        btn_nav.add(new MeowBottomNavigation.Model(3, R.drawable.categories));
        btn_nav.add(new MeowBottomNavigation.Model(4, R.drawable.user));
        btn_nav.show(1, true);


//        replace(new HomeFragment());
        fragments = new Fragment[4];
        fragments[0] = new HomeFragment();
        fragments[1] = new ViewAllFragment();
        fragments[2] = new SearchFragment();
        fragments[3] = new AccountFragment();
        showFragment(0, false);
        btn_nav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if(model.getId() == 1){
//                    replace(new HomeFragment());
                    showFragment(0,true);
                } else if (model.getId()== 2) {
//                    replace(new SearchFragment());
                    showFragment(2, true);
                } else if (model.getId()==3) {
//                    replace(new ViewAllFragment());
                    showFragment(1,true);
                } else if (model.getId()==4) {
//                    replace(new AccountFragment());
                    showFragment(3,true);
                }
                return null;
            }
        });

    }



    private void showFragment(int fragmentIndex, boolean flag) {
        if (fragmentIndex < 0 || fragmentIndex >= fragments.length) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Hide the current fragment
        if(flag){
            if (currentFragmentIndex >= 0 && currentFragmentIndex < fragments.length) {
            fragmentTransaction.hide(fragments[currentFragmentIndex]);
            }
        }

        // Show the new fragment
        Fragment fragmentToShow = fragments[fragmentIndex];
        if (!fragmentToShow.isAdded()) {
            fragmentTransaction.add(R.id.framelayout, fragmentToShow);
        } else {
            fragmentTransaction.show(fragmentToShow);
        }

        fragmentTransaction.commit();
        currentFragmentIndex = fragmentIndex;
    }
}


//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        MenuItem myItem = menu.findItem(R.id.cityname);
//        myItem.setTitle("Mumbai");
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        return true;
//    }
