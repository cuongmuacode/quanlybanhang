package com.example.quanlybanhang.activityy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.MyViewPager2Adapter;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.fragment.FragmentHoaDon;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    public ViewPager2 mViewPager2;
    private BottomNavigationView  mBottomNavigationView;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mViewPager2 = findViewById(R.id.viewpager2);
        MyViewPager2Adapter adapter = new MyViewPager2Adapter(this);
        mViewPager2.setAdapter(adapter);
        mViewPager2.setOffscreenPageLimit(4);
        mViewPager2.setCurrentItem(1);
        mViewPager2.setCurrentItem(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_colse);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_banhang){
                    mViewPager2.setCurrentItem(0);
                    mBottomNavigationView.getMenu().findItem(R.id.nav_banhang).setChecked(true);
                }
                else if(id == R.id.nav_hoadon){
                    mViewPager2.setCurrentItem(1);
                    mBottomNavigationView.getMenu().findItem(R.id.nav_hoadon).setChecked(true);
                }
                else if(id == R.id.nav_baocao){
                    mViewPager2.setCurrentItem(2);
                    mBottomNavigationView.getMenu().findItem(R.id.nav_baocao).setChecked(true);
                }
                else if(id == R.id.nav_xemthem){
                    mViewPager2.setCurrentItem(3);
                    mBottomNavigationView.getMenu().findItem(R.id.nav_xemthem).setChecked(true);
                }
                return true;
            }
        });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        toolbar.setTitle(R.string.nav_banhang);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_banhang).setChecked(true);
                        break;
                    case 1:
                        toolbar.setTitle(R.string.nav_hoadon);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_hoadon).setChecked(true);
                        break;
                    case 2:
                        toolbar.setTitle(R.string.nav_baocao);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_baocao).setChecked(true);
                        break;
                    case 3:
                        toolbar.setTitle(R.string.nav_xemthem);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_xemthem).setChecked(true);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){

        }
        else if(id == R.id.nav_favorite) {

        }
        else if(id == R.id.nav_history){

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        int i = 0;
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            i++;
            return;
        }
        int id = mViewPager2.getCurrentItem();
        switch (id){
            case 3:
            case 2:
            case 1:
                mViewPager2.setCurrentItem(0); i++; return;
        }
        if(i == 0) super.onBackPressed();
    }

}