package com.cou.cse.alamgir.smartdiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cou.cse.alamgir.smartdiary.Fragment.FragmentA;
import com.cou.cse.alamgir.smartdiary.Fragment.FragmentB;
import com.cou.cse.alamgir.smartdiary.Fragment.FragmentC;
import com.cou.cse.alamgir.smartdiary.Fragment.SelectionPageAdapter;
import com.cou.cse.alamgir.smartdiary.Model.Diary;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiaryActiviry extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SelectionPageAdapter selectionPageAdapter;
    private ViewPager mviewpager;
    private TabLayout tableLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout mdrawerLayout;
    // this is my project
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_activiry);
         toolbar= (Toolbar) findViewById(R.id.toolbar);
        mviewpager= (ViewPager) findViewById(R.id.contrainer);
        selectionPageAdapter=new SelectionPageAdapter(getSupportFragmentManager());
         setMviewpager(mviewpager);
         tableLayout= (TabLayout) findViewById(R.id.tabs);
         toolbar=findViewById(R.id.toolbar);
        tableLayout.setupWithViewPager(mviewpager);
        toolbar.inflateMenu(R.menu.menu_item);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        setUpNavigationView();
       // setupTabIcon();



    }
   // public void setupTabIcon()
    //{
       // tableLayout.getTabAt(0).setIcon(R.drawable.profileicon);
   // }
    public void setMviewpager(ViewPager viewPager)
    {
        SelectionPageAdapter selectionPageAdapter=new SelectionPageAdapter(getSupportFragmentManager());
        selectionPageAdapter.addFragment(new FragmentA(),"Tab1");
        selectionPageAdapter.addFragment(new FragmentB(),"tab2");
        selectionPageAdapter.addFragment(new FragmentC(),"tab3");
        viewPager.setAdapter(selectionPageAdapter);

    }

    public void setUpNavigationView(){
        navigationView=findViewById(R.id.mnevigationview);
        mdrawerLayout=findViewById(R.id.mdrawerlayout);
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(DiaryActiviry.this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}
