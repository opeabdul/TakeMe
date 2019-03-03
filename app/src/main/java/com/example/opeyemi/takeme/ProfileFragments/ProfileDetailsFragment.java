package com.example.opeyemi.takeme.ProfileFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.opeyemi.takeme.ProfileActivity;
import com.example.opeyemi.takeme.ProfileEditActivity;
import com.example.opeyemi.takeme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetailsFragment extends Fragment {


    private TextView mAddressTextView;
    private TextView mPhoneNumberTextView;
    private TextView mEmailTextView;
    private TextView mSkillsTextView;
    private FloatingActionButton editProfileFloatingActionButton;

    String profileId;

    public ProfileDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details,container,false);
        initializeViews(view);

        profileId = ((ProfileActivity)getActivity()).getProfileId();

        initializeViewsDetailsWithId(profileId);

        return view;
    }

    private void initializeViewsDetailsWithId(String profileId) {

        DatabaseReference currentUserdb = FirebaseDatabase.getInstance()
                .getReference("user").child(profileId);

        currentUserdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.child("phoneNumber").getValue() != null) {
                        mPhoneNumberTextView.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    }

                    if (dataSnapshot.child("address").getValue() != null) {
                        mAddressTextView.setText(dataSnapshot.child("address").getValue().toString());
                    }

                    if (dataSnapshot.child("email").getValue() != null) {
                        mEmailTextView.setText(dataSnapshot.child("email").getValue().toString());
                    }

                    if (dataSnapshot.child("skills").getValue() != null) {
                        mSkillsTextView.setText(dataSnapshot.child("skills").getValue().toString());
                    }

                    if (dataSnapshot.child("gender").getValue() != null) {
                        mEmailTextView.setText(dataSnapshot.child("gender").getValue().toString());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initializeViews(View view) {
        mAddressTextView = view.findViewById(R.id.profile_address_TextView);
        mPhoneNumberTextView = view.findViewById(R.id.profile_phone_number_TextView);
        mEmailTextView = view.findViewById(R.id.profile_email_TextView);
        mSkillsTextView = view.findViewById(R.id.profile_skills_TextView);
        editProfileFloatingActionButton = view.findViewById(R.id.edit_profile_floatingActionButton);


        editProfileFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ProfileEditActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
