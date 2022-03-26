package com.example.room_wrangler;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDrm;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class SortedRoomAdapter extends RecyclerView.Adapter<SortedRoomAdapter.ViewHolder> {

    private ArrayList<Room> localDataSet;
    private String choseDate;
    private int peopleNum;
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
        private final View layout;

        public ViewHolder(View view) {
            super(view);
            imageViewPic = view.findViewById(R.id.imageView_item_room);
            roomNumber = view.findViewById(R.id.textView_item_room_room_number);
            roomDescription = view.findViewById(R.id.textView_item_room_room_description);
            people = view.findViewById(R.id.textView_item_room_people);
            imageViewIcon = view.findViewById(R.id.imageView_item_icon);
            layout = view.findViewById(R.id.linearLayout_item);
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

        public View getLayout() {
            return layout;
        }
    }

    public SortedRoomAdapter(ArrayList<Room> localDataSet, String choseDate, int peopleNum, Context context) {
        this.localDataSet = localDataSet;
        this.choseDate = choseDate;
        this.peopleNum = peopleNum;
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
        Room room = localDataSet.get(position);
        ImageView pic = viewHolder.getImageViewPic();
        TextView roomNumber = viewHolder.getRoomNumber();
        ImageView icon = viewHolder.getImageViewIcon();
        TextView people = viewHolder.getPeople();
        TextView roomDescription = viewHolder.getRoomDescription();

        if (Integer.parseInt(room.getMaxNumOfPeople()) >= peopleNum) {
            FirebaseFirestore.getInstance().collection("bookings").document(choseDate
                    .concat(" ").concat(room.getRoomNumber())).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot result = task.getResult();
                    if (result.exists()) {
                        ArrayList<String> slotsBooked = (ArrayList<String>) result.get("duration");
                        if (slotsBooked.size() != 12) {
                            //viewHolder.getLayout().setVisibility(View.GONE);
                            pic.setImageResource(room.getRoomPicture());
                            String nameAndNumOfPeople = "<b>" + "Room " + room.getRoomNumber();
                            roomNumber.setText(Html.fromHtml(nameAndNumOfPeople));
                            icon.setImageResource(R.drawable.group_people_icon);
                            people.setText(room.getMaxNumOfPeople());
                            roomDescription.setText(room.getRoomDesc());
                            pic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, RoomInfoActivity.class);
                                    intent.putExtra("Room", room);
                                    context.startActivity(intent);
                                }
                            });
                        } else {
                            pic.setVisibility(View.GONE);
                            roomNumber.setVisibility(View.GONE);
                            icon.setVisibility(View.GONE);
                            people.setVisibility(View.GONE);
                            roomDescription.setVisibility(View.GONE);
                        }
                    } else {
                        pic.setImageResource(room.getRoomPicture());
                        String nameAndNumOfPeople = "<b>" + "Room " + room.getRoomNumber();
                        roomNumber.setText(Html.fromHtml(nameAndNumOfPeople));
                        icon.setImageResource(R.drawable.group_people_icon);
                        people.setText(room.getMaxNumOfPeople());
                        roomDescription.setText(room.getRoomDesc());
                        pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, RoomInfoActivity.class);
                                intent.putExtra("Room", room);
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            });
        } else {
            pic.setVisibility(View.GONE);
            roomNumber.setVisibility(View.GONE);
            icon.setVisibility(View.GONE);
            people.setVisibility(View.GONE);
            roomDescription.setVisibility(View.GONE);
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}