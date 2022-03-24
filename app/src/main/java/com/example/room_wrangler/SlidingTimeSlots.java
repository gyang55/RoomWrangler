package com.example.room_wrangler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SlidingTimeSlots extends RecyclerView.Adapter<SlidingTimeSlots.ViewHolder> {

    Context context;
    private ArrayList<ArrayList<String>> localDataSet;
    public RecyclerPicListener recyclerPicListener;


    /**
     * Initialize the dataset of the Adapter.
     */
    public SlidingTimeSlots(Context context, ArrayList<ArrayList<String>> data, RecyclerPicListener recyclerPicListener) {
        this.localDataSet = data;
        this.context = context;
        this.recyclerPicListener = recyclerPicListener;
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
        TextView col1 = viewHolder.getCol1();
        col1.setText(localDataSet.get(position).get(0));


        TextView col2 = viewHolder.getCol2();
        col2.setText(localDataSet.get(position).get(1));


        TextView col3 = viewHolder.getCol3();
        col3.setText(localDataSet.get(position).get(2));


        TextView col4 = viewHolder.getCol4();
        col4.setText(localDataSet.get(position).get(3));

        col1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerPicListener.onItemClicked(col1);
            }
        });
        col2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerPicListener.onItemClicked(col2);
            }
        });
        col3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerPicListener.onItemClicked(col3);
            }
        });
        col4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerPicListener.onItemClicked(col4);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        @Override
        public void onClick(View view) {

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
}