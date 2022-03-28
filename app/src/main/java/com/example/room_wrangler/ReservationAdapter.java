package com.example.room_wrangler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private RoomBooking[] localDataSet;
    private String roomNumber, date, duration;


    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage, peopleIcon, timeIcon;
        TextView roomNumber,tvPeople, tvDate, tvDuration;


        public ViewHolder(View view) {
            super(view);

            roomNumber = view.findViewById(R.id.textView_roomNumber_reservation_card);
            tvPeople= view.findViewById(R.id.textView_people_reservation_card);
            tvDate= view.findViewById(R.id.textView_date_reservation_card);
            tvPeople= view.findViewById(R.id.textView_people_reservation_card);
            tvDuration = view.findViewById(R.id.textView_duration_reservation_card);
            peopleIcon = view.findViewById(R.id.imageView_people_reservation_card);
            roomImage = view.findViewById(R.id.imageView_room_reservation_card);
            timeIcon = view.findViewById(R.id.imageView_time_reservation_card);
            //error here should be expected, this is a template
        }

        public TextView getPeople() { return tvPeople;}
        public TextView getDate() {return tvDate;}
        public TextView getTvDuration() {return tvDuration;}
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

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_card, viewGroup, false); //error here should be expected, this is a template

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        roomNumber = localDataSet[position].getRoomNumber();
        String date = localDataSet[position].getDate();
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

        viewHolder.peopleIcon.setImageResource(R.drawable.group_people_icon);
        viewHolder.tvDate.setText(date);
        StringBuilder durations = new StringBuilder();
        String strDuration;
        for (String s: localDataSet[position].getDuration()) {
            if (durations.length() > 0) {
                durations.append(" ");
            }
            durations.append(s);

        }
        strDuration = durations.toString();
        viewHolder.tvDuration.setText(strDuration);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (localDataSet == null) {
            return 0;
        }
        return localDataSet.length;
    }
}