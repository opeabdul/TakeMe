package com.example.opeyemi.takeme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.opeyemi.takeme.model.Job;
import com.example.opeyemi.takeme.viewHolders.SearchResultAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private ArrayList<Job> jobList;
    private TextView searchStringTextView;


    private DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("job");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchStringTextView = findViewById(R.id.search_string_textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeRecycclerView();

        if(getIntent() != null){
            String searchString = getIntent().getStringExtra("searchQuery");
            searchStringTextView.setText(getString(R.string.search_result, searchString));
            getJobList(searchString);

        }

    }


    private void getJobList(final String searchString){

        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    int count = 0;
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        String jobDescription = "";
                        String jobTitle = "";

                        if(snapshot.child("description").getValue() != null |
                                snapshot.child("title").getValue() != null){

                            jobDescription =snapshot.child("description").getValue().toString();
                            jobTitle =snapshot.child("title").getValue().toString();
                            if(jobDescription.contains(searchString) | jobTitle.contains(searchString)){
                                jobList.add(snapshot.getValue(Job.class));
                                count++;
                            }
                        }

                    }

                    if(count == 0)
                        searchStringTextView.setText(getString(R.string.no_search_result, searchString));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void initializeRecycclerView(){
        jobList = new ArrayList<>();
        searchRecyclerView = findViewById(R.id.search_result_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this,
                LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(jobList, SearchActivity.this);
        searchRecyclerView.setAdapter(searchResultAdapter);
    }
}
