package com.example.opeyemi.takeme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;
import com.example.opeyemi.takeme.Interface.ItemClickListener;
import com.example.opeyemi.takeme.viewHolders.MenuVeiwHolder;
import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MainActivity extends BaseActivity{

    private TextView mTextMessage;


    FirebaseRecyclerOptions<Category> options;

    public RecyclerView menuRecyclerView;
    public RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Category, MenuVeiwHolder> mCategoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextMessage = (TextView) findViewById(R.id.message);


        //init firebase
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference category = database.getReference("category");
        category.keepSynced(true);

        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("category");
        Query query = categoryRef.orderByKey();


         options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

         //set name for current user
        mTextMessage.setText(Common.currentUser.getName());

        //load recyclerview with menu list
        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        menuRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        menuRecyclerView.setLayoutManager(layoutManager);


        loadMenu();
    }

    private void loadMenu() {

         mCategoryAdapter = new FirebaseRecyclerAdapter<Category, MenuVeiwHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MenuVeiwHolder holder, int position, @NonNull Category model) {
                        holder.menuNameTextView.setText(model.getName());
                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(holder.menuImageView);

                        final Category clickItem = model;
                        holder.setItemClickListener(new ItemClickListener() {

                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Toast.makeText(MainActivity.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public MenuVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
                        View view = inflater.inflate(R.layout.menu_item,parent,false);
                        return new MenuVeiwHolder(view);
                    }

                };

        menuRecyclerView.setAdapter(mCategoryAdapter);
    }

    //overriding BaseActivity method
    public int getContentViewId(){
        return R.layout.activity_main;
    }

    public int getNavigationMenuItemId(){
        return R.id.navigation_home;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCategoryAdapter.startListening();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items


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
