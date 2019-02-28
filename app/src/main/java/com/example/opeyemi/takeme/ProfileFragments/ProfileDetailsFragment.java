package com.example.opeyemi.takeme.ProfileFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.opeyemi.takeme.ProfileEditActivity;
import com.example.opeyemi.takeme.R;
import com.example.opeyemi.takeme.common.Common;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetailsFragment extends Fragment {

    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mPhoneNumberTextView;
    private TextView mEmailTextView;
    private TextView mSkillsTextView;
    private FloatingActionButton editProfileFloatingActionButton;


    public ProfileDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details,container,false);

        mNameTextView = view.findViewById(R.id.profile_name_TextView);
        mAddressTextView = view.findViewById(R.id.profile_address_TextView);
        mPhoneNumberTextView = view.findViewById(R.id.profile_phone_number_TextView);
        mEmailTextView = view.findViewById(R.id.profile_email_TextView);
        mSkillsTextView = view.findViewById(R.id.profile_skills_TextView);
        editProfileFloatingActionButton = view.findViewById(R.id.edit_profile_floatingActionButton);
        mNameTextView.setText(Common.currentUser.getName());
        mNameTextView.setText(Common.currentUser.getName());
        mPhoneNumberTextView.setText(Common.currentUser.getPhoneNumber());

        editProfileFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ProfileEditActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
