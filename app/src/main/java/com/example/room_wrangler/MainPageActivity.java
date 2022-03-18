package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainPageActivity extends AppCompatActivity implements RecyclerPicListener {
    protected static Room[] rooms = {new Room(R.drawable.study_room_1, 400, "4", true,
            true, "666", "Computer, 42 inch LCD Display, " +
            "Laptop Hookup, and Whiteboard"),
            new Room(R.drawable.study_room_2, 400, "4", true,
                    true, "667", "Computer, 42 inch LCD Display, " +
                    "Laptop Hookup, and Whiteboard"),
            new Room(R.drawable.study_room_3, 500, "6", true,
                    true, "668", "Computer, 80 inch LCD Display, Laptop " +
                    "Hookup, and Whiteboard")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setUpRoomsRecyclerView();
        setUpBottomNav();
    }

    private void setUpRoomsRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_mainPage_rooms);
        RoomsRecycler roomsRecycler = new RoomsRecycler(rooms, this);
        recyclerView.setAdapter(roomsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpBottomNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav_main_page);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getTitle().equals("Date")) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainPageActivity.this);
                    final View datePickerAndPeopleNumView = getLayoutInflater().inflate(R.layout.datepicker_peoplenum_alertdialog, null);
                    dialogBuilder.setView(datePickerAndPeopleNumView);
                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                    //System.out.println(datePicker.getDayOfMonth() + " /" + (datePicker.getMonth() + 1) + " /" + datePicker.getYear());
                } else if (item.getTitle().equals("Account")) {
                    goToLoginPage();
                }
                return false;
            }
        });
    }

    //Test main_page!!!
    private void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(Room room) {

    }
}