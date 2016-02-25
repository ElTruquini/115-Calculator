/*
 * CSC115 Assignment 3 : Calculator
 * Node.java
 * Created for use by CSC115 Spring2016 
 * Name: Daniel Olaya Moran
 * ID: V00855054
 * Date: Feb 18, 2016 
 * Details: CSC115 Assignment 3 Calculator
*/

public class Node{

	protected String item;
	protected Node next;

	/**
	 * Creates a Node that contains a token.
 	 * @param item, Token contained in the node.
	 * @param next, The node that comes after this one in the list.
	 */
	protected Node (String newItem, Node nextNode){
		this.item = newItem;
		this.next = nextNode;
	}

	protected Node (String newItem){
		this.item = newItem;
		this.next = null;
	}
	/**
	 * Creates a Node without any paramete.rs.
	 */
	protected Node (){
		this(null, null);
	}

}