package com.example.opeyemi.takeme;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;
import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.User;
import com.example.opeyemi.takeme.viewHolders.MenuVeiwHolder;
import com.example.opeyemi.takeme.model.Job;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;


public class MainActivity extends BaseActivity {

    FirebaseRecyclerOptions<Job> options;

    private final String TAG = "MainActivity";
    private String userPhoneNumber; //to hold phoneNumber of clicked jobItem
    private String userName; //to hold userName of each clicked jobItem
    private String jobId; //to hold the jobId of the partcular job menu clicked;

    public RecyclerView menuRecyclerView;
    public RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Job, MenuVeiwHolder> mCategoryAdapter;
    DatabaseReference userRef;
    DatabaseReference jobRef;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference job = database.getReference("job");
        job.keepSynced(true);

        userRef = FirebaseDatabase.getInstance().getReference("user");
        jobRef = FirebaseDatabase.getInstance().getReference("job");
        query = jobRef.orderByKey();

        options = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(query, Job.class)
                .build();

        //load recyclerview with menu list
        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        menuRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        menuRecyclerView.setLayoutManager(layoutManager);

        loadMenu();

    }

    private void loadMenu() {

        mCategoryAdapter = new FirebaseRecyclerAdapter<Job, MenuVeiwHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MenuVeiwHolder holder, final int position, @NonNull final Job model) {

                //populate different part of the viewholder with the right data
                holder.jobTitleTextView.setText(model.getTitle());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(holder.jobImageView);
                holder.menuJobLocationTextView.setText(getString(R.string.job_location_details,
                        model.getLocation().getArea(), model.getLocation().getCity()));
                if(model.getTimestamp() != null){
                    holder.menuDateTextView.setText(DateFormat.format("dd:MMM", new Date(Long.valueOf(model.getTimestamp()))));
                }

                holder.moneyTextView.setText(model.getPrice());

                //Get the user (owner of the job) details for the the the particular job
                //being populated on the recycler view
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final User user = dataSnapshot.child(model.getUserID()).getValue(User.class);
                        holder.jobOwnerNameTextView.setText(user.getName());
                        /*TODO get user image*/


                        //set the onclick Listener for the details to switch to job details view
                        //with the information of the item clicked
                        holder.detailsView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, JobDetailsActivity.class);
                                Log.e(TAG, "holder adapter position: "+ holder.getAdapterPosition());

                                intent.putExtra("userObject", user);
                                intent.putExtra("jobObject", model);
                                startActivity(intent);
                            }
                        });





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @NonNull
            @Override
            public MenuVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.item_menu, parent, false);
                return new MenuVeiwHolder(view);
            }

        };

        menuRecyclerView.setAdapter(mCategoryAdapter);
    }

    public void showCallAlertDialog(String userName, final String userPhoneNumber) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(userName)
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


    public boolean requestCallPermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Common.MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCallAlertDialog(userPhoneNumber, userName);
                }
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.putExtra("searchQuery", query);
                startActivity(searchIntent);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchFirebase(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchFirebase(String searchText){

        if(searchText.isEmpty()){
            query = jobRef.orderByKey();

            options = new FirebaseRecyclerOptions.Builder<Job>()
                    .setQuery(query, Job.class)
                    .build();
        }else {

            Query fireBaseSearchQuery = jobRef.orderByChild("description").startAt(searchText).endAt(searchText + "\uf8ff");

            options = new FirebaseRecyclerOptions.Builder<Job>()
                    .setQuery(fireBaseSearchQuery, Job.class)
                    .build();
        }

        loadMenu();

    }


    //overriding BaseActivity method
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCategoryAdapter.startListening();
        if(Common.currentUser == null){
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCategoryAdapter.stopListening();
    }


}
