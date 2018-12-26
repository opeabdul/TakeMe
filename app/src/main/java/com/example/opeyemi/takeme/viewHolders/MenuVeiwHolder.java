package com.example.opeyemi.takeme.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opeyemi.takeme.Interface.ItemClickListener;
import com.example.opeyemi.takeme.R;

public class MenuVeiwHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView menuNameTextView;
    public ImageView menuImageView;

    private ItemClickListener itemClickListener;

    public MenuVeiwHolder(View itemView) {
        super(itemView);

        menuNameTextView = itemView.findViewById(R.id.menu_name);
        menuImageView = itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }


}
