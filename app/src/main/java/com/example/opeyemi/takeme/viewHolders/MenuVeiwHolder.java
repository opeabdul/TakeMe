package com.example.opeyemi.takeme.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opeyemi.takeme.Interface.ItemClickListener;
import com.example.opeyemi.takeme.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuVeiwHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView jobTitleTextView;
    public ImageView jobImageView;
    public TextView menuJobLocationTextView;
    public TextView menuDateTextView;
    public TextView menuJobRatersTextView;
    public TextView menuJobRatingTextView;
    public ImageView menuStar;
    public TextView jobOwnerNameTextView;
    public CircleImageView jobOwnerImageView;
    public TextView moneyTextView;
    public View messageView;
    public View callView;
    public View detailsView;


    private ItemClickListener itemClickListener;

    public MenuVeiwHolder(View itemView) {
        super(itemView);

        jobTitleTextView = itemView.findViewById(R.id.job_title_textView);
        jobImageView = itemView.findViewById(R.id.menu_image);
        menuJobLocationTextView = itemView.findViewById(R.id.menu_job_location);
        menuDateTextView = itemView.findViewById(R.id.post_date);
        menuJobRatersTextView = itemView.findViewById(R.id.job_raters);
        menuJobRatingTextView = itemView.findViewById(R.id.job_rating_textView);
        menuStar = itemView.findViewById(R.id.menu_star);
        jobOwnerNameTextView = itemView.findViewById(R.id.job_owner_name_textView);;
        jobOwnerImageView = itemView.findViewById(R.id.menu_account_image);
        messageView = itemView.findViewById(R.id.message_layout);
        callView = itemView.findViewById(R.id.call_layout);
        moneyTextView =  itemView.findViewById(R.id.menu_money_value);
        detailsView = itemView.findViewById(R.id.job_details_layout);



        detailsView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
