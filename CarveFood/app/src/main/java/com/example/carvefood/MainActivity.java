package com.example.carvefood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    AddFoodItem addFragment = new AddFoodItem();
    Home_Fragment homeFragment = new Home_Fragment();
    ViewItems viewItemsFrag = new ViewItems();
    Profile profileFragment = new Profile();
    Orders order = new Orders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView2 = (BottomNavigationView) findViewById(R.id.appbar);
        this.bottomNavigationView = bottomNavigationView2;
        bottomNavigationView2.setOnNavigationItemSelectedListener(this);
        this.bottomNavigationView.setSelectedItemId(R.id.home);

    }
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, this.addFragment).commit();
                return true;

            case R.id.Orders :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, this.order).commit();
                return true;

            case R.id.finished_order :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, this.viewItemsFrag).commit();
                return true;
            case R.id.home :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, this.homeFragment).commit();
                return true;
            case R.id.profile :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, this.profileFragment).commit();
                return true;
            default:
                return false;
        }
    }
}