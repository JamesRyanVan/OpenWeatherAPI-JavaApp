package main.java;

/**
 * Interface that represents an action listener for AddLocationDialog class.
 * 
 * @author Team 10
 *
 */
public interface DialogListener {
	
	/**
	 * To be implemented method for when an event in the AddLocatinoDIalog class occurs.
	 * @param event the DialogEvent from AddLocationDialog
	 */
	public void dialogEventOccurred(DialogEvent event);
	
	
}
