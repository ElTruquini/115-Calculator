/**
 * StringStack.java
 * Created for use by CSC115 Spring2016 
 * Name: Daniel Olaya Moran
 * ID: V00855054
 * Date: Feb 23, 2016 
 * Details: CSC115 Assignment 3 Calculator
 */


public class StringStack {

	private Node head;

	public StringStack (){
		head = null;
	}
	public boolean isEmpty() {
		return head == null;
	}
	/* Removes the top of the stack. Precondition: None. 
	* Post Condition: if the stack is not empty, the item that was added the most recently is removed and returned.
	* Exception: Throws StackException if the stack is empty.
	*/
	public String pop() {
		if (!isEmpty()){
			Node temp = head;
			head = head.next;
			return temp.item;
		}
		else {
			throw new StackEmptyException ("StackEmptyException on pop: stack Empty");
		}
	}
	/* Returns item first out. Precondition: None. 
	* Post Condition: if the stack is not empty, the item at the top of the list is returned .
	* the stack is unchanged.
	* Exception: Throws StackException if the stack is empty.
	*/
	public String peek() {
		if (!isEmpty()){
			return head.item;
		}
		else {
			throw new StackEmptyException ("StackEmptyException on peek: stack Empty");
		}
	}
	/* Adds a new item to the stack. Precondition: newItem is the item to be added. 
	* Post Condition: If insertion is successful, newItem is on top of the stack.
	*/
	public void push(String newItem) {
		head = new Node (newItem,head); 
	}
	/* Removes all items from list. Precondition: None. 
	* Post Condition: stack is empty.
	*/
	public void popAll() {
		head = null;
	}

	//Internal methods for testing purposes
	public void print(){
		System.out.println("===Printing Stack===");
		//System.out.println("head.next: " + head.next);
		Node curr = head;
		while (curr != null){
			System.out.println(curr.item);
			curr = curr.next;
		}
		System.out.println("===End of Stack===");
	}

	//Internal methods to test different methods
	public static void main(String[] args) {
		StringStack stacky = new StringStack ();
		//sstacky.peek ();
		System.out.println("isEmpty (true)" + stacky.isEmpty()); // Must be True
		stacky.push ("Item1");
		System.out.println("isEmpty (false)" + stacky.isEmpty()); // Must be True
		stacky.push ("Item2");
 		stacky.push ("Item3");
		stacky.print();
		//stacky.popAll();
		System.out.println("isEmpty (false)" + stacky.isEmpty()); // Must be false
		System.out.println("peek: " + stacky.peek());//Item 3 must be shown
		//stacky.popAll();
		//System.out.println("pop: " + stacky.pop());
		stacky.print();
		stacky.peek();

	}
}
