package RPN;

import java.util.Stack;

public class PostFix {
	
	public static String str;
	
	public static String toInFix(String postfix) {
		
    	Stack<String> infix = new Stack<>();
    	for (int i = 0; i < postfix.length(); i++) {
			char c = postfix.charAt(i);
			if (isOperator(c)) {
				String b = infix.pop();
				String a = infix.pop();
				infix.push("(" + a + c + b + ")");
			} else
				infix.push("" + c);
		}
    	return infix.pop();
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
