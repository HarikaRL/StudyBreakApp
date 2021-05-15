package com.example.studybreakapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalHolder>{
    private List<Goal> goals = new ArrayList<>();

    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_item, parent, false);
        return new GoalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
        Goal currentGoal = goals.get(position);
        holder.textViewGoalText.setText(currentGoal.getGoalText());
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
        notifyDataSetChanged();
    }

    public Goal getGoalAt(int position) {
        return goals.get(position);
    }

    class GoalHolder extends RecyclerView.ViewHolder {
        private TextView textViewGoalText;

        public GoalHolder(@NonNull View itemView) {
            super(itemView);
            textViewGoalText = itemView.findViewById(R.id.text_view_goalText);

        }
    }
}
