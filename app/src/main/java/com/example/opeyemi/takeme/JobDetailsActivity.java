package com.example.opeyemi.takeme;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.Job;
import com.example.opeyemi.takeme.model.User;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

public class JobDetailsActivity extends AppCompatActivity {

    private TextView jobPrice;
    private TextView jobDescription;
    private TextView jobName;
    private ImageView jobImage;
    private ImageView userImageView;
    private TextView dateTextView;
    private TextView locationTextView;
    private TextView jobOwnerName;

    private Job jobObject;
    private User userObject;

    private final String  TAG = "JOBDETAILSACTIVITY";

    FirebaseDatabase database;
    DatabaseReference jobDbRef;

    FloatingActionButton message_fab;
    FloatingActionButton call_fab;


    CollapsingToolbarLayout collapsingToolbarLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_24dp));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(JobDetailsActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            });
        }
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //init View
        jobDescription = findViewById(R.id.job_description_text_view);
        jobImage = findViewById(R.id.job_image);
        jobPrice = findViewById(R.id.job_price);
        jobName = findViewById(R.id.job_name);
        userImageView = findViewById(R.id.user_image_view);
        dateTextView = findViewById(R.id.job_date_posted);
        locationTextView = findViewById(R.id.job_location);
        jobOwnerName = findViewById(R.id.job_owner_name_textView);
        //userPhoneTextView = findViewById();


        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        setTextAppearanceCollapseAppbar();



        Intent intent = getIntent();
        if (intent != null){

            userObject = (User) intent.getSerializableExtra("userObject");
            jobObject = (Job)  intent.getSerializableExtra("jobObject");
            jobDescription.setText(jobObject.getDescription());
            jobPrice.setText(jobObject.getPrice());
            jobName.setText(getString(R.string.job_name,jobObject.getTitle()));
            jobOwnerName.setText(userObject.getName());
            collapsingToolbarLayout.setTitle(jobObject.getTitle());
            locationTextView.setText(getString(R.string.location,
                    jobObject.getLocation().getAddress(),
                    jobObject.getLocation().getArea(),
                    jobObject.getLocation().getCity(),
                    jobObject.getLocation().getCity()));

            if(jobObject.getImage() != null){
                Picasso.with(JobDetailsActivity.this).load(jobObject.getImage()).into(jobImage);
            }

            if(userObject.getImage() != null){
                Picasso.with(JobDetailsActivity.this).load(userObject.getImage()).into(userImageView);
            }

            if (jobObject.getTimestamp() != null){
                dateTextView.setText(DateFormat.format("dd-mm-yyyy",new Date(Long.valueOf(jobObject.getTimestamp()))));
            }

        }

        message_fab =  findViewById(R.id.message_fab);
        message_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(JobDetailsActivity.this, FindUserActivity.class);
                Common.createNewChat(userObject.getPhoneNumber());
                startActivity(intent);
            }
        });

        call_fab =  findViewById(R.id.call_fab);
        call_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String userPhoneNumber = userObject.getPhoneNumber();
                String userName = userObject.getName();
                if (requestCallPermission()) {
                    showCallAlertDialog(userName, userPhoneNumber);
                }
            }
        });
    }

    @TargetApi(21)
    public void setTextAppearanceCollapseAppbar(){
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
    }

    public boolean requestCallPermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(JobDetailsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Common.MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            return true;
        }
        return false;
    }


    public void showCallAlertDialog(String userName, final String userPhoneNumber) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JobDetailsActivity.this);
        alertDialogBuilder.setTitle(R.string.phonecall)
                .setMessage(getString(R.string.dialog_make_a_phone_call,userName, userPhoneNumber) )
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        makePhoneCall(userPhoneNumber);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Common.currentUser == null){
            startActivity(new Intent(JobDetailsActivity.this, SignInActivity.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(JobDetailsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        return true;

    }
}

