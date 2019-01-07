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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.opeyemi.takeme.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class JobDetailsActivity extends AppCompatActivity {

    private TextView jobPrice;
    private TextView jobDescription;
    private TextView jobName;
    private ImageView jobImage;
    private ImageView userImageView;
    private TextView dateTextView;
    private TextView locationTextView;
    private TextView userPhoneTextView;

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

        //Firebase
        database = FirebaseDatabase.getInstance();
        jobDbRef = database.getReference("job");


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

            jobDescription.setText(intent.getStringExtra("jobDescription"));
            Picasso.with(JobDetailsActivity.this).load(intent.getStringExtra("jobImage")).into(jobImage);
            jobPrice.setText(intent.getStringExtra("jobPrice"));
            jobName.setText(getString(R.string.job_name,intent.getStringExtra("jobName")));
            collapsingToolbarLayout.setTitle(intent.getStringExtra("jobOwnerName"));
            Picasso.with(JobDetailsActivity.this).load(intent.getStringExtra("jobImage")).into(userImageView);

            dateTextView.setText(getString(R.string.date, intent.getStringExtra("jobPostedDay"),
                    intent.getStringExtra("jobPostedMonth"), intent.getStringExtra("jobPostedMonth")));
            locationTextView.setText(getString(R.string.location,
                    intent.getStringExtra("jobLocationAddress"),
                    intent.getStringExtra("jobLocationArea"),
                    intent.getStringExtra("jobLocationCity"),
                    intent.getStringExtra("jobLocationState")));
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

