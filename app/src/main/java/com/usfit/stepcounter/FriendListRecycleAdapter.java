package com.usfit.stepcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendListRecycleAdapter extends RecyclerView.Adapter<FriendListRecycleAdapter.ViewHolder>{

    private ArrayList<String> mFriendNames = new ArrayList<>();
    private ArrayList<String> mFriendUIDS = new ArrayList<>();
    private Context mContext;

    public FriendListRecycleAdapter(ArrayList<String> mFriendNames, ArrayList<String> mFriendUIDS, Context mContext) {
        this.mFriendNames = mFriendNames;
        this.mFriendUIDS = mFriendUIDS;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_friend_holder, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.friendName.setText(mFriendNames.get(i));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView friendBody, friendTop, friendBot, friendFoot;
        TextView friendName;
        ConstraintLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            friendBody = itemView.findViewById(R.id.friendBodyImageView);
            friendTop = itemView.findViewById(R.id.friendTopImageView);
            friendBot = itemView.findViewById(R.id.friendBottomImageView);
            friendFoot = itemView.findViewById(R.id.friendFootImageView);
            parentLayout = itemView.findViewById(R.id.FriendListLayout);
        }
    }

}
