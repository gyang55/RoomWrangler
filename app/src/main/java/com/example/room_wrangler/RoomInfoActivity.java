package com.example.room_wrangler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class RoomInfoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Room room;
    private TextView date;

    private final ArrayList<String> timeSlots = new ArrayList<>();
    private String chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_room_info);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        String formattedDate = LocalDate.now().format(dateTimeFormatter);
        chosenDate = formattedDate + " " + LocalDate.now().getDayOfWeek();

        Intent intent = getIntent();
        room = (Room) intent.getExtras().get("Room");
        setUpRoom(room);
        setUpDate();
        setUpBookingButton();
        setUpSlidingTimeSlots(new ArrayList<>());
        greyOutBookedTimeSlots();
    }

    @Override
    protected void onResume() {
        super.onResume();

        greyOutBookedTimeSlots();
        timeSlots.clear();
    }

    //Display pic and description
    private void setUpRoom(Room room) {
        ImageView imageViewPic = findViewById(R.id.imageView_roomInfo_pic);
        ImageView imageViewIcon = findViewById(R.id.imageView_roomInfo_icon);
        TextView roomNumber = findViewById(R.id.textView_roomInfo_room_number);
        TextView peopleNum = findViewById(R.id.textView_roomInfo_room_people);
        imageViewPic.setImageResource(room.getRoomPicture());
        imageViewIcon.setImageResource(R.drawable.group_people_icon);
        String roomNum = "Room".concat(" ").concat(room.getRoomNumber());
        roomNumber.setText(roomNum);
        peopleNum.setText(room.getMaxNumOfPeople());
        showRoomAmenities();

    }

    //SetUp date and add listener to it
    private void setUpDate() {
        LocalDate today = LocalDate.now();
        date = findViewById(R.id.textView_roomInfo_date);
        String dateWithDay = today.toString() + " " + today.getDayOfWeek();
        date.setText(dateWithDay);

        date.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year1, month1, day1) -> {
                month1 += 1;
                String dateString = makeDateString(day1, month1, year1);
                date.setText(dateString);
                chosenDate = dateString;
                greyOutBookedTimeSlots();
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(RoomInfoActivity.this, android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);
            datePickerDialog.show();
        });
    }

    //Format selected date
    private String makeDateString(int day, int month, int year) {
        LocalDate temp = LocalDate.of(year, month, day);
        return year + "-" + month + "-" + day + " " + temp.getDayOfWeek();
    }

    private void setUpSlidingTimeSlots(ArrayList<String> bookedTimeSlots) {
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
        SlidingTimeSlots slidingTimeSlots = new SlidingTimeSlots(this, timeLabels, new TimeSlotsOnClick(), bookedTimeSlots);
        recyclerView.setAdapter(slidingTimeSlots);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void showRoomAmenities() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_roomInfo, AmenitiesFragment.newInstance(room));
        fragmentTransaction.commit();
    }

    private void setUpBookingButton() {
        Button button = findViewById(R.id.button_book_room);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(RoomInfoActivity.this, DisplayBookingsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("date", chosenDate);
            bundle.putString("roomNumber", room.getRoomNumber());
            bundle.putStringArrayList("timeslots", timeSlots);
            intent.putExtra("Room", room);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void greyOutBookedTimeSlots() {
        db.collection("bookings")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Debug", document.getData().toString());
                            if (Objects.requireNonNull(document.get("date")).equals(chosenDate) && Objects.equals(document.get("roomNumber"), room.getRoomNumber())) {
                                ArrayList<String> group = (ArrayList<String>) document.get("duration");
                                setUpSlidingTimeSlots(group);
                            }
                        }
                    }
                });
    }

    /**
     * Recyclerview listener to get chosen time slots
     */
    class TimeSlotsOnClick implements RecyclerPicListener {
        @Override
        public void onItemClicked(TextView textView) {
            ColorDrawable timeslotColor = (ColorDrawable) textView.getBackground();
            int currentColor = timeslotColor.getColor();
            if (currentColor == getResources().getColor(R.color.light_green)) {
                textView.setBackgroundResource(R.color.light_grey);
                String duration = textView.getText().toString();
                timeSlots.add(duration);
            } else if (currentColor == getResources().getColor(R.color.teal_200)) {
                textView.setBackgroundResource(R.color.teal_200);
            } else {
                textView.setBackgroundResource(R.color.light_green);
            }
        }
    }
}