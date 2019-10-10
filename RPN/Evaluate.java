package RPN;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Evaluate {
	public static String str;

	private static Double PosFfixSubEval(double a, double b, char sym) {
		
		double result = 0;
		if(sym == '+')
			result = a+b;
		if(sym == '-')
			result = a-b;
		if(sym == '*')
			result = a*b;
		if(sym == '/')
			result = a/b;
		if(sym == '^')
			result = Math.pow(a,b);
		return result;
	}
	
	public static String result(String infix) {
		
    	Stack<String> result = new Stack<>();
    	Stack<String> tokens = InFix.InFix2PostFix(infix);
    	for (int i = 0; i < tokens.size(); i++) {
			
    		String s = tokens.get(i);
    		char c = s.charAt(0);
			
    		if (isOperator(c)) 
    		try {
    			double b = Double.parseDouble(result.pop());
    			double a = Double.parseDouble(result.pop());
    			result.push(PosFfixSubEval(a, b, c).toString());
    		}catch(Exception e) {return "NaN";}
    		else
    			result.push(s);
    	}
    	NumberFormat nf = new DecimalFormat("#.################");

    	String s1 = nf.format(Double.parseDouble(result.pop()));
    	return s1;
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
