package RPN;

import java.util.LinkedList;
import java.util.Stack;

public class InFix {
	
	public static String str;
	
	public static LinkedList toTokens() {
		
		LinkedList tokens = new LinkedList();
		String tempStr = "";
		for(int i=0; i<InFix.str.length(); i++) {
			
			char c = InFix.str.charAt(i);
			if(!Operator.check(c)) {
				
				tempStr += c;
			}
			else {
				
				if(!tempStr.isEmpty()) {
					
					tokens.add(tempStr);
				}
				tempStr = "";
				tokens.add("" + c);
			}
			if(i==InFix.str.length()-1)
				if (!tempStr.isEmpty())
					tokens.add(tempStr);
		}
		return tokens;
	}
	
	public static LinkedList toPostFix() {
		
		LinkedList postfix = new LinkedList();
        Stack<Character> operator = new Stack();
        char popped;

        for (int i = 0; i < InFix.toTokens().size(); i++) {

        	String s ="";
        	s+= InFix.toTokens().get(i);
            char c = s.charAt(0);
            
            if (!Operator.check(c) || s.length()>1)
                postfix.add(s);
            else if (c == ')')
                while ((popped = operator.pop()) != '(')
                	postfix.add(popped);
            else {
                while (!operator.isEmpty() && c != '(' && Operator.precedence(operator.peek()) >= Operator.precedence(c))
                	postfix.add(operator.pop());
                operator.push(c);
            }
        }
        while (!operator.isEmpty())
        	postfix.add(operator.pop());

        return postfix;
	}
}
