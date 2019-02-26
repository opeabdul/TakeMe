package com.example.opeyemi.takeme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.opeyemi.takeme.model.Job;
import com.example.opeyemi.takeme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class JobDetailsActivity extends AppCompatActivity {

    private TextView jobPrice;
    private TextView jobDescription;
    private TextView jobName;
    private ImageView jobImage;
    private ImageView userImageView;
    private TextView dateTextView;
    private TextView locationTextView;
    private TextView userPhoneTextView;

    private Job jobObject;
    private User userObject;

    private final String  TAG = "JOBDETAILSACTIVITY";

    FirebaseDatabase database;
    DatabaseReference jobDbRef;

    ElegantNumberButton numberButton;
    FloatingActionButton fab;


    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);




        //init View
        numberButton =  findViewById(R.id.number_button);
        jobDescription = findViewById(R.id.job_description_text_view);
        jobImage = findViewById(R.id.job_image);
        jobPrice = findViewById(R.id.job_price);
        jobName = findViewById(R.id.job_name);
        userImageView = findViewById(R.id.user_image_view);
        dateTextView = findViewById(R.id.job_date_posted);
        locationTextView = findViewById(R.id.job_location);
        //userPhoneTextView = findViewById();


        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        setTextAppearanceCollapseAppbar();


        Intent intent = getIntent();
        if (intent != null){

            userObject = (User) intent.getSerializableExtra("userObject");
            jobObject = (Job)  intent.getSerializableExtra("jobObject");


            jobDescription.setText(jobObject.getDescription());
            Picasso.with(JobDetailsActivity.this).load(jobObject.getImage()).into(jobImage);
            jobPrice.setText(jobObject.getPrice());
            jobName.setText(getString(R.string.job_name,jobObject.getTitle()));
            collapsingToolbarLayout.setTitle(userObject.getName());
            Picasso.with(JobDetailsActivity.this).load(jobObject.getImage()).into(userImageView);

            dateTextView.setText(DateFormat.format("dd-mm-yyyy",new Date(Long.valueOf(jobObject.getTimestamp()))));
            locationTextView.setText(getString(R.string.location,
                    jobObject.getLocation().getAddress(),
                    jobObject.getLocation().getArea(),
                    jobObject.getLocation().getCity(),
                    jobObject.getLocation().getCity()));
        }

        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @TargetApi(21)
    public void setTextAppearanceCollapseAppbar(){
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
    }

}

