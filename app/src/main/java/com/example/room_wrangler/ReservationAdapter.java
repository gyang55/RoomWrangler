package com.example.room_wrangler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private RoomBooking[] localDataSet;
    private String roomNumber;


    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage;
        TextView tvPeople, tvDate, tvDuration;


        public ViewHolder(View view) {
            super(view);

            tvPeople= view.findViewById(R.id.textView_people_reservation_card);
            tvDate= view.findViewById(R.id.textView_date_reservation_card);
            tvPeople= view.findViewById(R.id.textView_people_reservation_card);
            //error here should be expected, this is a template
        }

        public TextView getPeople() { return tvPeople;}
        public TextView getDate() {return tvDate;}
        public TextView getTvDuration() {return tvDuration;}
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
        if (roomNumber.equals("666")) {
            viewHolder.getPeople().setText("4");
            viewHolder.roomImage.setImageResource(R.drawable.study_room_1);
        } else if (roomNumber.equals("667")) {
            viewHolder.getPeople().setText("4");
            viewHolder.roomImage.setImageResource(R.drawable.study_room_2);
        } else {
            viewHolder.getPeople().setText("6");
            viewHolder.roomImage.setImageResource(R.drawable.study_room_3);
        }
        viewHolder.getDate().setText(localDataSet[position].getDate());
        viewHolder.getTvDuration().setText((CharSequence) localDataSet[position].getDuration());

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