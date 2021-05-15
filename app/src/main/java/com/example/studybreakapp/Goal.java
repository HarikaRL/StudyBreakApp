package com.example.studybreakapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Implements a class for storing the user's goals
 * @author harika
 *
 */
@Entity(tableName = "goal_table")
public class Goal {

	@PrimaryKey(autoGenerate = true)
	private int id;

	// Data
	private String goalText;
	private boolean completionStatus;

	// Constructor(s)
	/**
	 * Default constructor: constructs a goal with empty text and completion
	 * status set to incomplete
	 */
	public Goal() {
		goalText = " ";
		completionStatus = false;
	}

	/**
	 * Parameter constructor:
	 * Constructs a new goal with the user-provided text and completion status
	 * set to incomplete
	 * @param userInput the given goal text
	 */
	public Goal(String userInput) {
		goalText = userInput;
		completionStatus = false;
	}

	// Methods
	/**
	 * Marks the goal as complete
	 */
	public void complete() {
		completionStatus = true;
	}

	/**
	 * Resets the text of a goal to the new user-provided string
	 * @param newText the user-provided goal text
	 */
	public void editText(String newText) { goalText = newText; }

	/**
	 * Returns the id
	 * @return the id
	 */
	public int getId() { return id; }

	/**
	 * Returns the goal
	 * @return the goal
	 */
	public String getGoalText() { return goalText; }

	/**
	 * Returns the completion status
	 * @return the completion status
	 */
	public boolean getCompletionStatus() { return completionStatus; }

	/**
	 * Sets the id to the given int value
	 * @param id the given value
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the goal text to the given string
	 * @param text the given text
	 */
	public void setGoalText(String text) {
		this.goalText = text;
	}

	/**
	 * Sets the completion status to the given boolean
	 * @param status the given boolean
	 */
	public void setCompletionStatus(boolean status) {
		completionStatus = status;
	}




}
