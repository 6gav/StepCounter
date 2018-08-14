package com.usfit.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendListRecycleAdapter extends RecyclerView.Adapter<FriendListRecycleAdapter.ViewHolder>{

    private ArrayList<String> mFriendNames = new ArrayList<>();
    private ArrayList<String> mFriendUIDS = new ArrayList<>();
    private Context mContext;
    ArrayList<User> mUsers = new ArrayList<>();

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.friendName.setText(mFriendNames.get(i));
        db.child("users").child(mFriendUIDS.get(i)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             User temp = dataSnapshot.getValue(User.class);
                mUsers.add(temp);

                viewHolder.friendTop.setImageDrawable(mContext.getDrawable(temp.mTop + R.drawable.outfit_t00));
                viewHolder.friendBot.setImageDrawable(mContext.getDrawable(temp.mBot + R.drawable.outfit_b00));
                viewHolder.friendFoot.setImageDrawable(mContext.getDrawable(temp.mFoot + R.drawable.outfit_f00));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(mContext, ProfileActivity.class);
                StaticHolderClass.displayedUser = mUsers.get(viewHolder.getAdapterPosition());
                n.putExtra("DisplayType","FriendSearch");
                mContext.startActivity(n);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriendNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView friendBody, friendTop, friendBot, friendFoot;
        TextView friendName, friendSteps;
        ConstraintLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            friendBody = itemView.findViewById(R.id.friendBodyImageView);
            friendTop = itemView.findViewById(R.id.friendTopImageView);
            friendBot = itemView.findViewById(R.id.friendBottomImageView);
            friendFoot = itemView.findViewById(R.id.friendFootImageView);
            parentLayout = itemView.findViewById(R.id.FriendListLayout);
            friendName = itemView.findViewById(R.id.friendUsernameTextView);
            friendSteps = itemView.findViewById(R.id.friendTotalStepsTextView);
        }
    }

}
