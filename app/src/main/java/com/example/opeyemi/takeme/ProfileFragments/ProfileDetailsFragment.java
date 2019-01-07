package com.example.opeyemi.takeme.ProfileFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        mNameTextView.setText(Common.currentUser.getName());
        mNameTextView.setText(Common.currentUser.getName());
        mPhoneNumberTextView.setText(Common.currentUser.getPhoneNumber());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
