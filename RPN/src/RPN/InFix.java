package RPN;

import java.util.LinkedList;

public class InFix {
	
	private String str;
	private LinkedList<String> tokens;

	public String getStr() {
		
		return str;
	}

	public void setStr(String str) {
		
		this.str = infixToStandard(str);
		this.tokens = infixToTokens(str);
	}

	public LinkedList<String> getTokens() {
		
		return tokens;
	}
	
	public InFix() {}
	public InFix(String str) {
		
		this.str = infixToStandard(str);
		this.tokens = infixToTokens(this.str);
	}
	public InFix(PostFix postfix) {
		
		String Str = "";
		for(int i=0; i<postfixToInfix(postfix).size(); i++)
			Str += postfixToInfix(postfix).get(i);
		this.str = Str;
		this.tokens = postfixToInfix(postfix);
	}
	//Chuẩn hóa chuỗi
	private String infixToStandard(String str) {
		
		for(int i=0; i<str.length(); i++) {
			
			str = str.replace("++", "+");
			str = str.replace("+-", "-");
			str = str.replace("-+", "-");
			str = str.replace("--", "+");
			str = str.replace("(+", "(");
			str = str.replace("(-(", "((-1)*(");
		}
		if(str.charAt(0)=='+')
			str = str.substring(1);
		else if(str.charAt(0)=='-' && str.charAt(1)=='(')
			str = "(-1)*" + str.substring(1);
		return str;
	}
	//Kiểm tra kí tự toán tử
	private boolean isOperator(String s) {
    	
        return precedence(s) > 0;
    }
    //Xét độ ưu tiên toán tử
    private int precedence(String s) {

        if (s.equals("(") || s.equals(")")) return 1;
        else if (s.equals("+") || s.equals("-")) return 2;
        else if (s.equals("*") || s.equals("/") || s.equals("^")) return 3;
        else if (s.equals("abs") || 
        		s.equals("sqrt") || 
        		s.equals("sin") || s.equals("cos") || s.equals("tan") || 
        		s.equals("ln") || 
        		s.equals("exp")) return 4;
        else return 0;
    }
    //Sử dụng LinkedList để lưu từng toán tử và toán hạng thành các token
	private LinkedList<String> infixToTokens(String str) {
		
		LinkedList<String> tokens = new LinkedList<String>();
		String tempStr = "";
		for(int i=0; i<str.length(); i++) {

			//Kiểm tra phát hiện toán tử 1 ngôi
			String s = "";
			if(i < str.length()-2 &&  str.substring(i, i+2)=="abs") {
				//i nhỏ hơn str.length()-2 vì chuỗi "abs" kiểm tra có 3 kí tự tránh lỗi từ str.substring()
				s += "abs";
				i=i+2;
			}
			else if(i < str.length()-3 && str.substring(i, i+3)=="sqrt") {
				
				s += "sqrt";
				i=i+3;
			}
			else if(i < str.length()-2 && str.substring(i, i+2)=="sin") {
				
				s += "sin";
				i=i+2;
			}
			else if(i < str.length()-2 && str.substring(i, i+2)=="cos") {
				
				s += "cos";
				i=i+2;
			}
			else if(i < str.length()-2 && str.substring(i, i+2)=="tan") {
				
				s += "tan";
				i=i+2;
			}
			else if(i < str.length()-1 && str.substring(i, i+1)=="ln") {
				
				s += "ln";
				i=i+1;
			}
			else if(i < str.length()-2 && str.substring(i, i+2)=="exp") {
				
				s += "exp";
				i=i+2;
			}
			else s += str.charAt(i);
			//Kiểm tra phân biệt toán tử 2 ngôi và toán hạng
			if(!isOperator(s) || s.equals("-") && i==0 || s.equals("-") && isOperator(""+str.charAt(i-1))) {
				//Kiểm tra ko phải toán tử hoặc kiểm tra toán hạng có âm
				//Nếu thỏa thì lấy ra toán hạng lưu và chuỗi tạm thời
				tempStr += s;
			}
			//Nếu là toán tử 2 ngôi
			else {
				//Kiểm tra chuỗi tạm thời tồn tại toán hạng thì thêm vào List
				if(!tempStr.isEmpty()) {
					
					tokens.add(tempStr);
				}
				tempStr = "";
				//Thêm toán tử vào List
				tokens.add(s);
			}
			//Thêm toán hạng cuối vào List
			if(i==str.length()-1)
				if (!tempStr.isEmpty())
					tokens.add(tempStr);
		}
		return tokens;
	}
	//Sử dụng LinkedList để lưu infix sau khi chuyển từ postfix
	private LinkedList<String> postfixToInfix(PostFix postfix) {
		
    	LinkedList<String> infix = new LinkedList<String>();
    	String tempStr = "";
    	for (int i = 0; i < postfix.getTokens().size(); i++) {
    		
    		tempStr = postfix.getTokens().get(i);
			//Nếu là toán hạng thì thêm vào List
			if (precedence(tempStr) == 0) {
				
				infix.add(tempStr);
			}
			//Trường hợp toán tử 1 ngôi
			else if (precedence(tempStr) == 4) {
				
				String b ="" + infix.getLast();	infix.removeLast();
				infix.add(tempStr + "(" + b + ")");
			} 
			//Trường hợp toán tử 2 ngôi
    		else {
    			
				String b ="" + infix.getLast();	infix.removeLast();
				String a ="" + infix.getLast();	infix.removeLast();
				infix.add("(" + a + tempStr + b + ")");
    		}
			tempStr = "";
		}
    	return infix;
    }
}
