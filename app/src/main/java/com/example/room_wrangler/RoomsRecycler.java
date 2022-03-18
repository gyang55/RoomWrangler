package com.example.room_wrangler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


public class RoomsRecycler extends RecyclerView.Adapter<RoomsRecycler.ViewHolder> {

    private Room[] localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPic;
        private final ImageView imageViewIcon;
        private final TextView roomNumber;
        private final TextView roomDescription;
        private final TextView people;

        public ViewHolder(View view) {
            super(view);

            imageViewPic = view.findViewById(R.id.imageView_item_room);
            roomNumber = view.findViewById(R.id.textView_item_room_room_number);
            roomDescription = view.findViewById(R.id.textView_item_room_room_description);
            people = view.findViewById(R.id.textView_item_room_people);
            imageViewIcon = view.findViewById(R.id.imageView_item_icon);
        }

        public TextView getRoomNumber() {
            return roomNumber;
        }

        public TextView getRoomDescription() {
            return roomDescription;
        }

        public TextView getPeople() {
            return people;
        }

        public ImageView getImageViewPic() {
            return imageViewPic;
        }

        public ImageView getImageViewIcon() {
            return imageViewIcon;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public RoomsRecycler(Room[] dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_room, viewGroup, false); //error here should be expected, this is a template

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Room room = localDataSet[position];
        viewHolder.getImageViewPic().setImageResource(localDataSet[position].getRoomPicture());
        String nameAndNumOfPeople = "<b>" + "Room " + localDataSet[position].getRoomNumber();
        viewHolder.getRoomNumber().setText(Html.fromHtml(nameAndNumOfPeople));
        viewHolder.getImageViewIcon().setImageResource(R.drawable.group_people_icon);
        viewHolder.getPeople().setText(localDataSet[position].getMaxNumOfPeople());
        viewHolder.getRoomDescription().setText(localDataSet[position].getRoomDesc());
        viewHolder.getImageViewPic().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoomInfoActivity.class);
                intent.putExtra("Room", room);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}