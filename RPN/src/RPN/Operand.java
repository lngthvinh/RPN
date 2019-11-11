package RPN;

public class Operand {
	
	private String value;

	public String getValue() {
		
		return value;
	}

	public void setValue(String value) {
		
		this.value = value;
	}
	
	public Operand(String value) {
		
		this.value = value;
	}
	
	//Ph�p to�n 2 ng�i
	public static String evaluate(Operand o1, Operand o2, String operator) {
		
		double a,b;
		try {
			
			a = Double.parseDouble(o1.getValue());
			b = Double.parseDouble(o2.getValue());
		}catch(Exception e) {return "NaN";}
		
		double result = 0;
		switch (operator) {
		
			case "+": result = a+b; break;
			case "-": result = a-b;	break;
			case "*": result = a*b;	break;
			case "/": result = a/b;	break;
			case "^": result = Math.pow(a,b); break;
		}
        return Double.toString(result);
     }
	
	//Ph�p to�n 1 ng�i
	public static String evaluate(Operand o, String operator) {

		double a;
		try {

			a = Double.parseDouble(o.getValue());
		}catch(Exception e) {return "NaN";}
		
		double result = 0;
		switch (operator) {
		
			case "abs": result = Math.abs(a); break;
			case "sqrt": result = Math.sqrt(a); break;
			case "sin": result = Math.sin(a);	break;
			case "cos": result = Math.cos(a);	break;
			case "tan": result = Math.tan(a); break;
			case "ln": result = Math.log10(a); break;
			case "exp": result = Math.exp(a); break;
		}
        return Double.toString(result);
     }
}
