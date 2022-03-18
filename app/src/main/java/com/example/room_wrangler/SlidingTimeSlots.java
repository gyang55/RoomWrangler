package com.example.room_wrangler;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SlidingTimeSlots extends RecyclerView.Adapter<SlidingTimeSlots.ViewHolder> {

    View view;
    Context context;
    private ArrayList<ArrayList<String>> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView col1;
        private TextView col2;
        private TextView col3;
        private TextView col4;


        public ViewHolder(View view) {
            super(view);
            col1 = view.findViewById(R.id.textView_slidngTimeSlots_col1_morning);
            col2 = view.findViewById(R.id.textView_slidngTimeSlots_col2_morning);
            col3 = view.findViewById(R.id.textView_slidngTimeSlots_col3_morning);
            col4 = view.findViewById(R.id.textView_slidngTimeSlots_col4_morning);

        }

        public TextView getCol1() {
            return col1;
        }

        public TextView getCol2() {
            return col2;
        }

        public TextView getCol3() {
            return col3;
        }

        public TextView getCol4() {
            return col4;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public SlidingTimeSlots(Context context, ArrayList<ArrayList<String>> data) {
        this.localDataSet = data;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sliding_time_slots, viewGroup, false); //error here should be expected, this is a template

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getCol1().setText(localDataSet.get(position).get(0));
        viewHolder.getCol2().setText(localDataSet.get(position).get(1));
        viewHolder.getCol3().setText(localDataSet.get(position).get(2));
        viewHolder.getCol4().setText(localDataSet.get(position).get(3));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}