package com.example.opeyemi.takeme.ProfileFragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opeyemi.takeme.ProfileFragments.JobPostFragment.OnListFragmentInteractionListener;
import com.example.opeyemi.takeme.R;
import com.example.opeyemi.takeme.model.Job;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Job} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyJobPostRecyclerViewAdapter extends RecyclerView.Adapter<MyJobPostRecyclerViewAdapter.ProfileJobsViewHolder> {

    private final List<Job> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyJobPostRecyclerViewAdapter(List<Job> jobItems, OnListFragmentInteractionListener listener) {
        mValues = jobItems;
        mListener = listener;
    }

    @Override
    public ProfileJobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_post, parent, false);
        return new ProfileJobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfileJobsViewHolder holder, int position) {


        Picasso.with(holder.mJobImageView.getContext()).load(mValues.get(position)
                .getImage()).into(holder.mJobImageView);

        holder.mPostTitleView.setText(mValues.get(position).getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ProfileJobsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mJobImageView;
        public final TextView mPostTitleView;
        public Job mItem;

        public ProfileJobsViewHolder(View view) {
            super(view);
            mView = view;
            mJobImageView =  view.findViewById(R.id.job_item_image);
            mPostTitleView = view.findViewById(R.id.job_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPostTitleView.getText() + "'";
        }
    }
}
