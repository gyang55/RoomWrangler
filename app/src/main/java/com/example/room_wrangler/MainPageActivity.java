package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainPageActivity extends AppCompatActivity {
    protected static Room[] rooms = {new Room(R.drawable.study_room_1, 400, "4", true,
            true, "666", "Computer, 42 inch LCD Display, " +
            "Laptop Hookup, and Whiteboard"),
            new Room(R.drawable.study_room_2, 400, "4", false,
                    true, "667", "Computer, 42 inch LCD Display, " +
                    "Laptop Hookup, and Whiteboard"),
            new Room(R.drawable.study_room_3, 500, "6", true,
                    false, "668", "Computer, 80 inch LCD Display, Laptop " +
                    "Hookup, and Whiteboard")};
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
    String formattedDate = LocalDate.now().format(dateTimeFormatter);
    private String chosenDate = formattedDate + " " + LocalDate.now().getDayOfWeek();
    private int peopleNum;

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
                    DatePicker datePicker = datePickerAndPeopleNumView.findViewById(R.id.datePicker_datepicker_peoplenum);
                    datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                            month += 1;
                            String dateString = makeDateString(day, month, year);
                            chosenDate = dateString;
                        }
                    });
                    Button button1 = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_peoplenum_1);
                    Button button2 = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_peoplenum_2);
                    Button button3 = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_peoplenum_3);
                    Button button4 = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_peoplenum_4);
                    Button button5 = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_peoplenum_5);
                    Button button6 = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_peoplenum_6);
                    button1.setOnClickListener(new AlertDialogButtonListener(button1, button2, button3, button4, button5, button6));
                    button2.setOnClickListener(new AlertDialogButtonListener(button2, button1, button3, button4, button5, button6));
                    button3.setOnClickListener(new AlertDialogButtonListener(button3, button2, button1, button4, button5, button6));
                    button4.setOnClickListener(new AlertDialogButtonListener(button4, button2, button3, button1, button5, button6));
                    button5.setOnClickListener(new AlertDialogButtonListener(button5, button2, button3, button4, button1, button6));
                    button6.setOnClickListener(new AlertDialogButtonListener(button6, button2, button3, button4, button5, button1));

                    Button check = datePickerAndPeopleNumView.findViewById(R.id.button_datepicker_check);
                    check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecyclerView recyclerView = findViewById(R.id.recyclerView_mainPage_rooms);
                            ArrayList<Room> roomsArrayList = new ArrayList<>(Arrays.asList(rooms));
                            SortedRoomAdapter roomsRecycler = new SortedRoomAdapter(roomsArrayList, chosenDate, peopleNum, MainPageActivity.this);
                            recyclerView.setAdapter(roomsRecycler);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainPageActivity.this));
                            alertDialog.dismiss();

                        }
                    });
                } else if (item.getTitle().equals("Account")) {
                    goToAccountPage();
                }
                return false;
            }
        });
    }

    //Go to account page
    private void goToAccountPage() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    //Format selected date
    private String makeDateString(int day, int month, int year) {
        LocalDate temp = LocalDate.of(year, month, day);
        return year + "-" + month + "-" + day + " " + temp.getDayOfWeek();
    }

    class AlertDialogButtonListener implements View.OnClickListener {
        Button current;
        Button other1;
        Button other2;
        Button other3;
        Button other4;
        Button other5;

        public AlertDialogButtonListener(Button current, Button other1, Button other2, Button other3, Button other4, Button other5) {
            this.current = current;
            this.other1 = other1;
            this.other2 = other2;
            this.other3 = other3;
            this.other4 = other4;
            this.other5 = other5;
        }

        @Override
        public void onClick(View view) {
            DrawableCompat.setTint(DrawableCompat.wrap(current.getBackground()), getResources().getColor(R.color.green));
            peopleNum = Integer.parseInt(String.valueOf(current.getText()));
            DrawableCompat.setTint(DrawableCompat.wrap(other1.getBackground()), getResources().getColor(R.color.light_grey));
            DrawableCompat.setTint(DrawableCompat.wrap(other2.getBackground()), getResources().getColor(R.color.light_grey));
            DrawableCompat.setTint(DrawableCompat.wrap(other3.getBackground()), getResources().getColor(R.color.light_grey));
            DrawableCompat.setTint(DrawableCompat.wrap(other4.getBackground()), getResources().getColor(R.color.light_grey));
            DrawableCompat.setTint(DrawableCompat.wrap(other5.getBackground()), getResources().getColor(R.color.light_grey));
        }
    }
}