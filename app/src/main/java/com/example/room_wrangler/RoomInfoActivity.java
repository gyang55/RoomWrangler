package com.example.room_wrangler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class RoomInfoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Room room;
    private TextView date;
    private SlidingTimeSlots slidingTimeSlots;
    private ArrayList<String> timeSlots = new ArrayList<>();
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
        showRoomAmenities();

    }

    //SetUp date and add listener to it
    private void setUpDate() {
        LocalDate today = LocalDate.now();
        date = findViewById(R.id.textView_roomInfo_date);
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
                        chosenDate = dateString;
                        greyOutBookedTimeSlots();
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
        slidingTimeSlots = new SlidingTimeSlots(this, timeLabels, new TimeSlotsOnClick());
        recyclerView.setAdapter(slidingTimeSlots);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        greyOutBookedTimeSlots();
    }

    private void showRoomAmenities() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_roomInfo, AmenitiesFragment.newInstance(room));
        fragmentTransaction.commit();
    }

    private void setUpBookingButton() {
        Button button = findViewById(R.id.button_book_room);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomInfoActivity.this, DisplayBookingsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", chosenDate);
                bundle.putString("roomNumber", room.getRoomNumber());
                bundle.putStringArrayList("timeslots", timeSlots);
                intent.putExtra("Room", room);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void greyOutBookedTimeSlots() {
        db.collection("bookings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Debug", document.getData().toString());
                                if (document.get("date").equals(chosenDate) && document.get("roomNumber").equals(room.getRoomNumber())) {
                                    List<String> group = (List<String>) document.get("duration");
                                    System.out.println(group);
                                    for (String timeslot : group
                                    ) {
                                        for (String slot : timeSlots
                                        ) {
                                            if (slot.equals(timeslot)) {

                                            }
                                        }
                                    }
                                }
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
            } else {
                textView.setBackgroundResource(R.color.light_green);
            }
        }
    }
}