package com.example.opeyemi.takeme.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.opeyemi.takeme.JobDetailsActivity;
import com.example.opeyemi.takeme.MainActivity;
import com.example.opeyemi.takeme.R;
import com.example.opeyemi.takeme.model.Job;
import com.example.opeyemi.takeme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class SearchResultAdapter extends RecyclerView.Adapter<MenuVeiwHolder> {

    ArrayList<Job> jobList;
    Context context;

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");

    public  SearchResultAdapter(ArrayList<Job> jobList, Context context){
        this.jobList = jobList;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MenuVeiwHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false)) ;

    }

    @Override
    public void onBindViewHolder(@NonNull final MenuVeiwHolder holder, int position) {
        final Job currentJob = jobList.get(position);
        holder.jobTitleTextView.setText(currentJob.getTitle());
        Picasso.with(context).load(currentJob.getImage())
                .into(holder.jobImageView);
        String currentImage = currentJob.getImage();
        holder.menuJobLocationTextView.setText(context.getString(R.string.job_location_details,
                currentJob.getLocation().getArea(), currentJob.getLocation().getCity()));
        if(currentJob.getTimestamp() != null){
            holder.menuDateTextView.setText(DateFormat.format("dd:MMM", new Date(Long.valueOf(currentJob.getTimestamp()))));
        }

        holder.moneyTextView.setText(currentJob.getPrice());

        //Get the user (owner of the job) details for the the the particular job
        //being populated on the recycler view
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.child(currentJob.getUserID()).getValue(User.class);
                holder.jobOwnerNameTextView.setText(user.getName());


                if(user.getImage()!= null){
                    if(!user.getImage().equals("")){
                        Picasso.with(holder.jobOwnerImageView.getContext())
                                .load(user.getImage()).into(holder.jobOwnerImageView);
                    }

                }



                //set the onclick Listener for the details to switch to job details view
                //with the information of the item clicked
                holder.detailsView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, JobDetailsActivity.class);


                        intent.putExtra("userObject", user);
                        intent.putExtra("jobObject", currentJob);
                        context.startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }
}
