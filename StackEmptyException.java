/**
 * CSC115 Assignment 3 : Calculator
 * StackEmptyException.java
 * Created for use by CSC115 Spring2016 
 * Name: Daniel Olaya Moran
 * ID: V00855054
 * Date: Feb 18, 2016 
 * Details: CSC115 Assignment 3 Calculator
*/


/**
 * An exception thrown when an Arithmetic expression is determined to be invalid.
 */ 
public class StackEmptyException extends RuntimeException {

	/**
	 * Creates an exception.
	 * @param msg The message to the calling program.
	 */	
	public StackEmptyException(String msg){
		super(msg);
	}
	public StackEmptyException(){
		super();
	}
}