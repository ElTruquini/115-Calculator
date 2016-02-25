/**
 * CSC115 Assignment 3 : Calculator
 * ArithExpression.java
 * Created for use by CSC115 Spring2016 
 * Name: Daniel Olaya Moran
 * ID: V00855054
 * Date: Feb 18, 2016 
 * Details: CSC115 Assignment 3 Calculator
*/
import java.util.regex.*;

public class ArithExpression {

	private TokenList postfixTokens;
	private TokenList infixTokens;

	/**
	 * Sets up a legal standard Arithmetic expression.
	 * The only parentheses accepted are "(" and ")".
	 * @param word An arithmetic expression in standard infix order.
	 * 		An invalid expression is not expressly checked for, but will not be
	 * 		successfully evaluated, when the <b>evaluate</b> method is called.
	 * @throws InvalidExpressionException if the expression cannot be properly parsed,
	 *  	or converted to a postfix expression.
	 */
	public ArithExpression(String word) {
		if (Tools.isBalancedBy("()",word)) {
			tokenizeInfix(word);
			infixToPostfix();
		} else {
			throw new InvalidExpressionException("Parentheses unbalanced");
		}
	}

	/*
	 * A private helper method that tokenizes a string by separating out
	 * any arithmetic operators or parens from the rest of the string.
	 * It does no error checking.
	 * The method makes use of Java Pattern matching and Regular expressions to
	 * isolate the operators and parentheses.
	 * The operands are assumed to be the substrings delimited by the operators and parentheses.
	 * The result is captured in the infixToken list, where each token is 
	 * an operator, a paren or a operand.
	 * @param express The string that is assumed to be an arithmetic expression.
	 */
	private void tokenizeInfix(String express) {
		infixTokens  = new TokenList(express.length());

		// regular expression that looks for any operators or parentheses.
		Pattern opParenPattern = Pattern.compile("[-+*/^()]");
		Matcher opMatcher = opParenPattern.matcher(express);

		String matchedBit, nonMatchedBit;
		int lastNonMatchIndex = 0;
		String lastMatch = "";

		// find all occurrences of a matched substring
		while (opMatcher.find()) {
			matchedBit = opMatcher.group();
			// get the substring between matches
			nonMatchedBit = express.substring(lastNonMatchIndex, opMatcher.start());
			nonMatchedBit = nonMatchedBit.trim(); //removes outside whitespace
			// The very first '-' or a '-' that follows another operator is considered a negative sign
			if (matchedBit.charAt(0) == '-') {
				if (opMatcher.start() == 0 || isOperator(lastMatch) || lastMatch.equals("(")) {
					continue;  // ignore this match
				}
			}
			// nonMatchedBit can be empty when an operator follows a ')'
			if (nonMatchedBit.length() != 0) {
				infixTokens.append(nonMatchedBit);
			}
			lastNonMatchIndex = opMatcher.end();
			infixTokens.append(matchedBit);
			lastMatch = matchedBit;
		}
		// parse the final substring after the last operator or paren:
		if (lastNonMatchIndex < express.length()) {
			nonMatchedBit = express.substring(lastNonMatchIndex,express.length());
			nonMatchedBit.trim();
			infixTokens.append(nonMatchedBit);
		}

	}

	/**
	 * Determines whether a single character string is an operator.
	 * The allowable operators are {+,-,*,/,^}.
	 * @param op The string in question.
	 * @return True if it is recognized as a an operator.
	 */
	public static boolean isOperator(String op) {
		switch(op) {
			case "+":
			case "-":
			case "/":
			case "*":
			case "^":
				return true;
			default:
				return false;
		}
	}
		
	/**
	 * NOTE TO STUDENT: These requirements don't show up in the 
	 * java documentation because it is a private method.
	 * It is private because it directly accesses the data fields.	
	 * 
	 * A private method that initializes the postfixTokens data field.
	 * It takes the information from the infixTokens data field.
	 * If, during the process, it is determined that the expression is invalid,
	 * an InvalidExpressionException is thrown.
 	 * Note that since the only method that calls this method is the constructor,
	 * the Exception is propogated through the constructor.
	 */
	private void infixToPostfix() {
		String postFixExp = "";
		System.out.println(infixTokens);
		String token = "";
		StringStack stacky = new StringStack ();

		for (int i = 0; i < infixTokens.toString().length(); i++){
			token = "" + infixTokens.toString().charAt(i);
			
			//System.out.println(token);
			switch (token){
				case "1": case "2": case "3": case "4": case "5": case "6":
				case "7": case "8": case "9":
					postFixExp = postFixExp + token;
					break;

				case "(":
					stacky.push(token);
					break;

				case ")":
					while(!stacky.peek().equals("(")){
						postFixExp = postFixExp + stacky.pop();
					}
					String openParen = stacky.pop();
					break;

				case "+": case "-": case "*": case "/": case "^":  
					while (!stacky.isEmpty() && !stacky.peek().equals("(") ){
						// need to include prescedence
						postFixExp = postFixExp + stacky.pop();
					}
					stacky.push(token);
					break;
			}
		}
		while (!stacky.isEmpty()){
			postFixExp = postFixExp + stacky.pop();
		}
		System.out.println(postFixExp);
		
	
	}

	public String getInfixExpression() {
		return "";
	}

	public String getPostfixExpression() {
	 	return "";
	}
		
	public double evaluate() {
		return 9.0;
	}
						
	public static void main(String[] args) {
		ArithExpression test = new ArithExpression("2+2*3+3");
	}
			
}
