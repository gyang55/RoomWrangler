package com.example.room_wrangler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.StringValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmenitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmenitiesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Room mParam1;

    public AmenitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AmentitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AmenitiesFragment newInstance(Room param1) {
        AmenitiesFragment fragment = new AmenitiesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Room) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amentities, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView roomIcon = view.findViewById(R.id.imageView_amenities_roomSize);
        roomIcon.setImageDrawable(getResources().getDrawable(R.drawable.roomsize));
        TextView roomSize = view.findViewById(R.id.textView_amenities_roomSize);
        roomSize.setText(String.valueOf(mParam1.getRoomSize()));
        ImageView seatIcon = view.findViewById(R.id.imageView_amenities_seat);
        seatIcon.setImageDrawable(getResources().getDrawable(R.drawable.seat));
        TextView seat = view.findViewById(R.id.textView_amenities_seat);
        seat.setText(mParam1.getMaxNumOfPeople());

        if (mParam1.isHasTV()) {
            ImageView tvIcon = view.findViewById(R.id.imageView_amenities_TV);
            tvIcon.setImageDrawable(getResources().getDrawable(R.drawable.tv));
        } else {
            view.findViewById(R.id.linearLayout_amenities_TV).setVisibility(View.GONE);
        }
        if (mParam1.isHasWhiteboard()) {
            ImageView tvIcon = view.findViewById(R.id.imageView_amenities_whiteboard);
            tvIcon.setImageDrawable(getResources().getDrawable(R.drawable.whiteboard));
        } else {
            view.findViewById(R.id.linearLayout_amenities_whiteboard).setVisibility(View.GONE);
        }
    }
}