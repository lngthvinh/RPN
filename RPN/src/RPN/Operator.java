package RPN;

public class Operator {
	
	public static boolean check(char c) {
    	
        return precedence(c) > 0;
    }
    
    public static int precedence(char c) {

        if (c == '(' || c == ')') return 1;
        else if (c == '-' || c == '+') return 2;
        else if (c == '*' || c == '/' || c == '^') return 3;
        else return 0;
    }
    public static Double evaluate(double a, double b, char sym) {
		
		double result = 0;
		switch (sym)
		{
		    case '+': result = a+b; break;
		    case '-': result = a-b;	break;
		    case '*': result = a*b;	break;
		    case '/': result = a/b;	break;
		    case '^': result = Math.pow(a,b); break;
		}
		return result;
	}
}
