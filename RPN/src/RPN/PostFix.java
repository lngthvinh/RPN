package RPN;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Stack;

public class PostFix {
	
	public static String toInFix(String postfixStr) {
		
    	LinkedList infix = new LinkedList();
    	for (int i = 0; i < postfixStr.length(); i++) {
			char c = postfixStr.charAt(i);
			if (Operator.check(c)) {
				String b ="" + infix.getLast();	infix.removeLast();
				String a ="" + infix.getLast();	infix.removeLast();
				infix.add("(" + a + c + b + ")");
			} else
				infix.add("" + c);
		}
    	return "" + infix.getLast();
    }
	public static String result(String infixStr) {
		
    	LinkedList result = new LinkedList();
    	for (int i = 0; i < InFix.toPostFix(infixStr).size(); i++) {
			
    		String s ="";
        	s+= InFix.toPostFix(infixStr).get(i);
            char c = s.charAt(0);
			
    		if (Operator.check(c)) 
    		try {
    			double b = Double.parseDouble(""+result.getLast()); 
    			result.removeLast();
    			double a = Double.parseDouble(""+result.getLast()); 
    			result.removeLast();
    			result.add(Operator.evaluate(a, b, c).toString());
    		}catch(Exception e) {return "NaN";}
    		else
    			result.add(s);
    	}
    	return ""+result.getLast();
    }
}
