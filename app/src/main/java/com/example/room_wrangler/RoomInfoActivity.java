package com.example.room_wrangler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;


public class RoomInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);
        Intent intent = getIntent();
        Room room = (Room) intent.getExtras().get("Room");
        setUpRoom(room);
        setUpDate();
        setUpSlidingTimeSlots();
        setUpBookingButton();
    }

    //Display pic and description
    private void setUpRoom(Room room) {
        ImageView imageViewPic = findViewById(R.id.imageView_roomInfo_pic);
        ImageView imageViewIcon = findViewById(R.id.imageView_roomInfo_icon);
        TextView roomNumber = findViewById(R.id.textView_roomInfo_room_number);
        TextView peopleNum = findViewById(R.id.textView_roomInfo_room_people);
        imageViewPic.setImageResource(room.getRoomPicture());
        imageViewIcon.setImageResource(R.drawable.group_people_icon);
        roomNumber.setText("Room " + room.getRoomNumber());
        peopleNum.setText(room.getMaxNumOfPeople());
    }

    //SetUp date and add listener to it
    private void setUpDate() {
        LocalDate today = LocalDate.now();
        TextView date = findViewById(R.id.textView_roomInfo_date);
        date.setText(today.toString() + " " + today.getDayOfWeek());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String dateString = makeDateString(day, month, year);
                        date.setText(dateString);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(RoomInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    //Format selected date
    private String makeDateString(int day, int month, int year) {
        LocalDate temp = LocalDate.of(year, month, day);
        return year + "-" + month + "-" + day + " " + temp.getDayOfWeek();
    }

    private void setUpSlidingTimeSlots() {
        ArrayList<ArrayList<String>> timeLabels = new ArrayList<>();
        ArrayList<String> morningLabels = new ArrayList<>();
        ArrayList<String> afternoonLabels = new ArrayList<>();
        ArrayList<String> nightLabels = new ArrayList<>();
        morningLabels.add("8:00 - 9:00");
        morningLabels.add("9:00 - 10:00");
        morningLabels.add("10:00 - 11:00");
        morningLabels.add("11:00 - 12:00");
        afternoonLabels.add("12:00 - 13:00");
        afternoonLabels.add("13:00 - 14:00");
        afternoonLabels.add("14:00 - 15:00");
        afternoonLabels.add("15:00 - 16:00");
        nightLabels.add("16:00 - 17:00");
        nightLabels.add("17:00 - 18:00");
        nightLabels.add("18:00 - 19:00");
        nightLabels.add("19:00 - 20:00");
        timeLabels.add(morningLabels);
        timeLabels.add(afternoonLabels);
        timeLabels.add(nightLabels);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_roomInfo);
        SlidingTimeSlots slidingTimeSlots = new SlidingTimeSlots(this, timeLabels);
        recyclerView.setAdapter(slidingTimeSlots);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setUpBookingButton() {
        Button button = findViewById(R.id.button_book_room);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}