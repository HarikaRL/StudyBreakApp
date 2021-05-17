package com.example.studybreakapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Goal adapter adapts goals to recycler view format
 */
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalHolder>{
    private List<Goal> goals = new ArrayList<>();

    /**
     * Creates a new goal holder with the given parent and view type
     * @param parent the given parent
     * @param viewType the given view type
     * @return
     */
    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_item, parent, false);
        return new GoalHolder(itemView);
    }

    /**
     * Sets the goal text in the given goal holder to the text of the goal at the given position
     * @param holder the given goal holder
     * @param position the given position (in the list) of the goal
     */
    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
        Goal currentGoal = goals.get(position);
        holder.textViewGoalText.setText(currentGoal.getGoalText());
    }

    /**
     * Returns the number of goals
     * @return the number of goals
     */
    @Override
    public int getItemCount() {
        return goals.size();
    }

    /**
     * Sets the goals in the goal adapter to the specified list and sends a notification
     * that the data set was changed
     * @param goals the list of goals
     */
    public void setGoals(List<Goal> goals) {
        this.goals = goals;
        notifyDataSetChanged();
    }

    /**
     * Returns the goal at the given position
     * @param position the given position
     * @return the goal at that position
     */
    public Goal getGoalAt(int position) {
        return goals.get(position);
    }

    /**
     * The GoalHolder class extends the ViewHolder class in RecyclerView to display the goals
     */
    class GoalHolder extends RecyclerView.ViewHolder {
        private TextView textViewGoalText;

        /**
         * Parameter constructor:
         * Constructs an object in the GoalHolder class and sets the goal text to the text
         * in the goal_item layout
         * @param itemView the view containing the goal holder
         */
        public GoalHolder(@NonNull View itemView) {
            super(itemView);
            textViewGoalText = itemView.findViewById(R.id.text_view_goalText);

        }
    }
}
