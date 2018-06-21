package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private ArrayList<String> achievementHeading = new ArrayList<>();
    private ArrayList<String> achievementDescription = new ArrayList<>();
    private ArrayList<String> achievementAward = new ArrayList<>();
    private Context mContext;

    public RecycleViewAdapter(ArrayList<String> achievementHeading, ArrayList<String> achievementDescription, ArrayList<String> achievementAward, Context mContext) {
        this.achievementHeading = achievementHeading;
        this.achievementDescription = achievementDescription;
        this.achievementAward = achievementAward;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.aHeading.setText(achievementHeading.get(position));
        holder.aDesc.setText(achievementDescription.get(position));
        holder.aAward.setText(achievementAward.get(position));
    }

    @Override
    public int getItemCount() {
        return achievementHeading.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Declarations
        TextView aHeading, aDesc, aAward;
        ConstraintLayout aLayout;



        public ViewHolder(View itemView) {
            super(itemView);

            //Assign components
            aHeading = itemView.findViewById(R.id.achievementHeading);
            aDesc = itemView.findViewById(R.id.achievementDescription);
            aAward = itemView.findViewById(R.id.achievementAward);
            aLayout = itemView.findViewById(R.id.list_view_layout);

        }
    }

}
