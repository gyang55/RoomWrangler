package com.example.room_wrangler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private final RoomBooking[] localDataSet;


    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage, peopleIcon, timeIcon;
        TextView roomNumber, People, date, duration, totalTime;


        public ViewHolder(View view) {
            super(view);

            roomNumber = view.findViewById(R.id.textView_roomNumber_reservation_card);
            People = view.findViewById(R.id.textView_people_reservation_card);
            date = view.findViewById(R.id.textView_date_reservation_card);
            People = view.findViewById(R.id.textView_people_reservation_card);
            duration = view.findViewById(R.id.textView_duration_reservation_card);
            totalTime = view.findViewById(R.id.textView_totalTme_reservation_card);
            peopleIcon = view.findViewById(R.id.imageView_people_reservation_card);
            roomImage = view.findViewById(R.id.imageView_room_reservation_card);
            timeIcon = view.findViewById(R.id.imageView_time_reservation_card);
        }

        public TextView getPeople() { return People;}
        public TextView getDate() {return date;}
        public TextView getDuration() {return duration;}
        public ImageView getRoomImage() {return roomImage;}
        public TextView getRoomNumber() {return roomNumber;}
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ReservationAdapter(RoomBooking[] dataSet) {
        this.localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_card, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String roomNumber = localDataSet[position].getRoomNumber();
        String date = localDataSet[position].getDate();
        String strTotalTime;
        ImageView roomImage = viewHolder.getRoomImage();


        if (roomNumber.equals("666")) {
            viewHolder.roomNumber.setText("Room 666 ");
            viewHolder.getPeople().setText("4");
            roomImage.setImageResource(R.drawable.study_room_1);
        } else if (roomNumber.equals("667")) {
            viewHolder.getPeople().setText("4");
            viewHolder.roomNumber.setText("Room 667 ");
            roomImage.setImageResource(R.drawable.study_room_2);
        } else {
            viewHolder.getPeople().setText("6");
            viewHolder.roomNumber.setText("Room 668 ");
            roomImage.setImageResource(R.drawable.study_room_3);
        }

        ArrayList<String> duration = localDataSet[position].getDuration();

        strTotalTime = getTotalBookingTime(duration);

        viewHolder.peopleIcon.setImageResource(R.drawable.group_people_icon);
        viewHolder.totalTime.setText(strTotalTime);
        viewHolder.date.setText(date);

        //duration ArrayList handling
        StringBuilder durations = new StringBuilder();
        String strDuration;
        for (String s: duration) {
            if (durations.length() > 0) {
                durations.append(" / ");
            }
            durations.append(s);
        }
        strDuration = durations.toString();
        viewHolder.duration.setText(strDuration);

    }

    @Override
    public int getItemCount() {
        if (localDataSet == null) {
            return 0;
        }
        return localDataSet.length;
    }

    private String getTotalBookingTime(ArrayList<String> duration) {
        int totalTime = duration.size();
        StringBuilder totalTimeString = new StringBuilder();
        String timeNumber = Integer.toString(totalTime);
        totalTimeString.append(timeNumber);
        totalTimeString.append(" hrs");

        return totalTimeString.toString();
    }
}