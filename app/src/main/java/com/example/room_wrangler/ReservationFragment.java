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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Room mParam1;
//    private String mParam2;

    public ReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String owner;

        FirebaseDatabase database;
        FirebaseFirestore db;
        DatabaseReference userRef;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;
        RoomBooking roomBooking;

        //get database info

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        owner = firebaseUser.getUid();
        db = FirebaseFirestore.getInstance();

        CollectionReference bookings = db.collection("bookings");
        Query query = bookings.whereEqualTo("owner", owner);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String roomNumber, date;
                ArrayList<String> duration;

                for (QueryDocumentSnapshot documents: task.getResult()) {
                   RoomBooking roomBooking = documents.toObject(RoomBooking.class);
                   roomNumber = roomBooking.getRoomNumber();
                   setCard(view, roomNumber, roomBooking);

                }
            }
        });



//        roomImage.setImageDrawable(getResources().getDrawable(R.d));

    }
    public void setCard(View view, String roomNumber, RoomBooking roomBooking) {
        ImageView roomImage;
        TextView tvPeople, tvDate, tvDuration;

        if (roomNumber.equals("666")) {
            roomImage = view.findViewById(R.id.imageView_room_reservation_card);
            roomImage.setImageDrawable(getResources().getDrawable(R.drawable.study_room_1));
            tvPeople = view.findViewById(R.id.textView_people_reservation_card);
            tvPeople.setText("4");
        } else if (roomNumber.equals("667")) {
            roomImage = view.findViewById(R.id.imageView_room_reservation_card);
            roomImage.setImageDrawable(getResources().getDrawable(R.drawable.study_room_2));
            tvPeople = view.findViewById(R.id.textView_people_reservation_card);
            tvPeople.setText("4");
        } else {
            roomImage = view.findViewById(R.id.imageView_room_reservation_card);
            roomImage.setImageDrawable(getResources().getDrawable(R.drawable.study_room_3));
            tvPeople = view.findViewById(R.id.textView_people_reservation_card);
            tvPeople.setText("6");
        }

        tvDate = view.findViewById(R.id.textView_date_reservation_card);
        tvDate.setText(roomBooking.getDate());

        tvDuration = view.findViewById(R.id.textView_duration_reservation_card);
        tvDuration.setText(roomBooking.getDuration().toString());

    }
}