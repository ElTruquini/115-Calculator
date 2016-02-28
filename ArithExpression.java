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
import java.util.*;
import java.lang.Math.*;


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
	 * The result is captured in the infixTokens list, where each token is 
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
		postfixTokens  = new TokenList(infixTokens.toString().length());
		String token = "";
		StringStack stacky = new StringStack ();
		//2.4 -3 5 + *
		//System.out.println("To convert: " + infixTokens.toString() );

		Scanner reader = new Scanner (infixTokens.toString());
		reader.useDelimiter("\\s");

		while (reader.hasNext()){
			token = ""+reader.next();
			//System.out.println("**********TOKEN TO PROCESS: " + token );
			//looking for Operands
			if (!isOperator(token) && !token.equals("(") && !token.equals(")")){
				//System.out.println("I made IF 1 !isOperator token :"+ token + " prevToken :" + prevToken);;
				postfixTokens.append(token);
			}
			if (token.equals("(")){ 
				//System.out.println("I made IF 2 .equals'(' token :"+ token + " prevToken :" + prevToken);
				stacky.push(token);
			}
			if (token.equals(")")){
				//System.out.println("I made IF 3 .equals')' token :"+ token + " prevToken :" + prevToken);
				while(!stacky.peek().equals("(")){
					postfixTokens.append(stacky.pop());
				}
				String openParen = stacky.pop();
			}

			if (isOperator(token)){
				//System.out.println("I made IF 4 isOperator token :"+ token + " prevToken :" + prevToken);
				while ( !stacky.isEmpty() && !stacky.peek().equals("(") 
					&& precedence(token) <= precedence (stacky.peek()) ){
					postfixTokens.append(stacky.pop());
				}
				stacky.push(token);
			}
		
		//System.out.println("===STRING: " + postfixTokens.toString() );	
		}
		while (!stacky.isEmpty()){
			postfixTokens.append(stacky.pop());
		}
		//System.out.println("from method " + postfixTokens.toString());
	
	}
	
	public int precedence (String token){
		if (token == "^"){
			return 5;
		}
		if ((token == "*") || (token == "/")){
			return 4;
		}
		if ((token == "+") || (token == "-")){
			return 3;
		}
		if (token == "(") {
			return 2;
		}
		if (token == ")"){
			return 1;
		}
		return 0;
	}
	public String getInfixExpression() {
		return infixTokens.toString();
	}

	public String getPostfixExpression() {
	 	return postfixTokens.toString();
	}
		
	public double evaluate() {
		StringStack operandStack = new StringStack ();
		Scanner reader = new Scanner (postfixTokens.toString());
		reader.useDelimiter("\\s");
		/*
		String dom ="Daniel   x  Olaya Moran";
		Scanner testing = new Scanner (dom);
		while (testing.hasNext()){
			System.out.println(testing.next());
		}
		return 2.0;
	}
	*/
		System.out.println("Evaluate method with string: " + postfixTokens.toString());
		int count =0;
		double operand1, operand2, result = 0;
		while (reader.hasNext()){
			String token = reader.next();
			System.out.println("token processed: " + token);
			if (token.equals(" ")){
				String holder = token;
			}
			if (!isOperator(token) && !token.equals(" ")){
				operandStack.push(token);	
				System.out.println("Pushing token: " + token);
			}
			if (isOperator(token) && !token.equals(" ")){
				System.out.println("peeking!: " + operandStack.peek());
				operand1 = Double.parseDouble(operandStack.pop());
				System.out.println("peeking!: " + operandStack.peek());

				operand2 = Double.parseDouble(operandStack.pop());
				System.out.println("operand1: " + operand1 + "operand2: " + operand2);
				switch (token){
					case "+": 
						result = operand1 + operand2;
						break;
					case "-": 
						result = operand1 - operand2;
						break;
					case "*": 
						result = operand1 * operand2;
						break;
					case "/": 
						result = operand1 / operand2;
						break;
					case "^": 
						result = Math.pow(operand1, operand2);
						break;
				}
			System.out.println("Pushing result: " + result);
			String resultString = ""+result;
			operandStack.push(resultString);
			}
		}

		return result;
	}
	//Internal method for testing		
	public static void main(String[] args) {
		//ArithExpression test = new ArithExpression("2 * ( -3 + 5 )");// Result:4 - Correct!
		//ArithExpression test = new ArithExpression("6 +1 +9 +(10 *2)");// Result:36 - Correct!
		//ArithExpression test = new ArithExpression("2/ 1");
		//ArithExpression test = new ArithExpression("(2 /2) +1");
		//ArithExpression test = new ArithExpression("-5+3+(6*2)");
		//ArithExpression test = new ArithExpression("15*10-2");
		ArithExpression test = new ArithExpression("6 +1 +9 +(10 *2)");
		//ArithExpression test = new ArithExpression("6 +1 +9 +(10 *2)");
		//ArithExpression test = new ArithExpression("6 +1 +9 +(10 *2)");


		test.getInfixExpression();
		test.getPostfixExpression();

		System.out.println("Evaluate = " + test.evaluate());
		
	}
			
}
