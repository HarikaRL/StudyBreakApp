package com.example.studybreakapp;

/**
 * Implements a method for storing the user's goals
 * @author harika
 *
 */
public class Goal {

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
	 * Returns the goal
	 * @return the goal
	 */
	public String getGoalText() { return goalText; }

}
