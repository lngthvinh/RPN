package RPN;

import java.util.Stack;

public class InFix {
	
	public static String str;
	
	public static String toPostFix(String infix) {
		
		String postfix="";
		Stack<String> str = InFix2PostFix(infix);
		for(int i=0; i<str.size(); i++)
			postfix += str.get(i);
		return postfix;
	}
	
	public static Stack<String> InFix2PostFix(String str) {
		
		Stack<String> infix = strToTokens(str);
		Stack<String> postfix = new Stack<>();
        Stack<Character> operator = new Stack<>();
        char popped;

        for (int i = 0; i < infix.size(); i++) {

        	String s = infix.get(i);
            char c = s.charAt(0);
            
            if (!isOperator(c) || s.length()>1)
                postfix.push(s);
            else if (c == ')')
                while ((popped = operator.pop()) != '(')
                	postfix.push("" + popped);
            else {
                while (!operator.isEmpty() && c != '(' && precedence(operator.peek()) >= precedence(c))
                	postfix.push("" + operator.pop());
                operator.push(c);
            }
        }
        while (!operator.isEmpty())
        	postfix.push("" + operator.pop());

        return postfix;
	}
	
	public static Stack<String> strToTokens(String infix) {
		
		Stack<String> tokens = new Stack<>();
		String tempStr = "";
		for(int i=0; i<infix.length(); i++) {
			
			char c = infix.charAt(i);
			if(!isOperator(c)) {
				
				tempStr += c;
			}
			else {
				
				if(!tempStr.isEmpty()) {
					
					tokens.push(tempStr);
				}
				tempStr = "";
				tokens.push("" + c);
			}
			if(i==infix.length()-1)
				if (!tempStr.isEmpty())
					tokens.push(tempStr);
		}
		return tokens;
	}
	
	private static boolean isOperator(char i) {
    	
        return precedence(i) > 0;
    }
    
    private static int precedence(char i) {

        if (i == '(' || i == ')') return 1;
        else if (i == '-' || i == '+') return 2;
        else if (i == '*' || i == '/' || i == '^') return 3;
        else return 0;
    }
}
