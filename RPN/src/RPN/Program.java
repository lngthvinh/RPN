package RPN;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Program extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton btnAbs;
	private JButton btnSqrt;
	private JButton btnSin;
	private JButton btnCos;
	private JButton btnTan;
	private JButton btnExp;
	private JButton btnLn;
	private JButton btnLog;
	private JLabel lblNewLabel_3;
	private JTextField textField_3;
	private JLabel lblNewLabel_4;
	
	private boolean isOperator(String s) {
    	
        return precedence(s) > 0;
    }
    
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
	
	private void infixToPostfix(InFix infix) {
		
		LinkedList<String> postfix = new LinkedList<String>();
        Stack<String> operator = new Stack<String>();
        String popped;
        String stackStr = "", postfixStr = "";

        for (int i = 0; i < infix.getTokens().size(); i++) {

        	String s = infix.getTokens().get(i);
            
            if (!isOperator(s))
                postfix.add(s);
            else if (s.equals(")"))
                while (!(popped = operator.pop()).equals("("))
                	postfix.add(popped);
            else {
                while (!operator.isEmpty() && !s.equals("(") && precedence(operator.peek()) >= precedence(s))
                	postfix.add(operator.pop());
                operator.push(s);
            }
            for(int x=0; x<operator.size();x++)
            	stackStr+=operator.get(x)+" ";
            for(int x=0; x<postfix.size();x++)
            	postfixStr+=postfix.get(x)+" ";
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{s, stackStr, postfixStr});
            stackStr="";
            postfixStr="";
        }
        while (!operator.isEmpty())
        	postfix.add(""+operator.pop());
        for(int x=0; x<operator.size();x++)
        	stackStr+=operator.get(x)+" ";
        for(int x=0; x<postfix.size();x++)
        	postfixStr+=postfix.get(x)+" ";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"", stackStr, postfixStr});
	}
	
	private boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	LinkedList<String> u = new LinkedList<String>();
	LinkedList<String> v = new LinkedList<String>();
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JPanel panel_1;
	private JLabel label;
	private JButton button_5;
	private void assignStandard(String assign) {
		
		u = new LinkedList<String>();
		v = new LinkedList<String>();
		String tempStr = "";
		//Các token postfix cách nhau bởi khoảng trắng
		for(int i=0; i<assign.length(); i++) {
			
			char c = assign.charAt(i);
			
			if (c != '=' && c != ',' && c != ';' && c != '(' && c != ')') {
				
				tempStr += c;
			}
			else {
				
				if(!tempStr.isEmpty()) {
					
					if(!isNumeric(tempStr))
						u.add(tempStr);
					else if(isNumeric(tempStr))
						v.add(tempStr);
				}
				tempStr = "";
			}
			if(i==assign.length()-1)
				if (!tempStr.isEmpty()) {
					
					if(!isNumeric(tempStr))
						u.add(tempStr);
					else if(isNumeric(tempStr))
						v.add(tempStr);
				}
		}
	}
	private void assign(Operand o) {
		
		for(int t=0; t<u.size(); t++) {
			if(o.getValue().equals(u.get(t))) {
				o.setValue(v.get(t));
			}
		}
	}
	private void changeResult(String assign, PostFix postfix) {
		
		assignStandard(assign);
		LinkedList<String> result = new LinkedList<String>();
    	for (int i = 0; i < postfix.getTokens().size(); i++) {
			
    		String s = postfix.getTokens().get(i);
			//Nếu toán tử là phép toán 2 ngôi
    		if (precedence(s) == 2 || precedence(s) == 3) {
    			
    			if(result.size()<2) break;
    			Operand b = new Operand(result.getLast());
    			assign(b);
    			result.removeLast();
    			Operand a = new Operand(result.getLast()); 
    			assign(a);
    			result.removeLast();
    			result.add(Operand.evaluate(a, b, s));
    		}
    		//Nếu toán tử là phép toán 1 ngôi
    		else if (precedence(s) == 4) {
    			
    			if(result.size()<1) break;
    			Operand b = new Operand(result.getLast());
    			assign(b);
    			result.removeLast();
    			result.add(Operand.evaluate(b, s));
    		}
    		else result.add(s);
    	}
    	try {
    		textField_2.setText(result.getLast());
    	}catch(Exception e) {textField_2.setText("NaN");}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Program() {
		
		try {
			
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				
            	if ("Nimbus".equals(info.getName())) {
                	 UIManager.setLookAndFeel(info.getClassName());
                	 break;
            	}
            }
		} 
		catch (Exception e) {
			
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		setTitle("RPN");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1072, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblNewLabel = new JLabel("InFix Expression:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(20);
		
		JButton btnNewButton = new JButton("InFix to PostFix");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InFix infix = new InFix(textField.getText());
				PostFix postfix = new PostFix(infix);
				textField.setText(infix.getStr());
				textField_1.setText(postfix.getStr());
				textField_2.setText(postfix.getResult());
				
	            DefaultTableModel model = (DefaultTableModel) table.getModel();
	            model.setRowCount(0);
				infixToPostfix(infix);
				
				String s = new String(textField_3.getText().replace(" ", ""));
				if(!s.equals("")) changeResult(s,postfix); 
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnAbs = new JButton("abs");
		btnAbs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "abs()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		
		button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "+", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition());
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		panel.add(button, gbc_button);
		
		button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "-", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition());
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 0;
		panel.add(button_1, gbc_button_1);
		
		button_2 = new JButton("*");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "*", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition());
			}
		});
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 0;
		panel.add(button_2, gbc_button_2);
		
		button_3 = new JButton("/");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "/", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition());
			}
		});
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 3;
		gbc_button_3.gridy = 0;
		panel.add(button_3, gbc_button_3);
		
		button_4 = new JButton("^");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "^", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition());
			}
		});
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.insets = new Insets(0, 0, 5, 5);
		gbc_button_4.gridx = 4;
		gbc_button_4.gridy = 0;
		panel.add(button_4, gbc_button_4);
		
		button_5 = new JButton("( )");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_button_5 = new GridBagConstraints();
		gbc_button_5.insets = new Insets(0, 0, 5, 5);
		gbc_button_5.gridx = 5;
		gbc_button_5.gridy = 0;
		panel.add(button_5, gbc_button_5);
		GridBagConstraints gbc_btnAbs = new GridBagConstraints();
		gbc_btnAbs.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbs.gridx = 0;
		gbc_btnAbs.gridy = 1;
		panel.add(btnAbs, gbc_btnAbs);
		
		btnSqrt = new JButton("sqrt");
		btnSqrt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "sqrt()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnSqrt = new GridBagConstraints();
		gbc_btnSqrt.insets = new Insets(0, 0, 0, 5);
		gbc_btnSqrt.gridx = 1;
		gbc_btnSqrt.gridy = 1;
		panel.add(btnSqrt, gbc_btnSqrt);
		
		btnSin = new JButton("sin");
		btnSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "sin()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnSin = new GridBagConstraints();
		gbc_btnSin.insets = new Insets(0, 0, 0, 5);
		gbc_btnSin.gridx = 2;
		gbc_btnSin.gridy = 1;
		panel.add(btnSin, gbc_btnSin);
		
		btnCos = new JButton("cos");
		btnCos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "cos()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnCos = new GridBagConstraints();
		gbc_btnCos.insets = new Insets(0, 0, 0, 5);
		gbc_btnCos.gridx = 3;
		gbc_btnCos.gridy = 1;
		panel.add(btnCos, gbc_btnCos);
		
		btnTan = new JButton("tan");
		btnTan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "tan()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnTan = new GridBagConstraints();
		gbc_btnTan.insets = new Insets(0, 0, 0, 5);
		gbc_btnTan.gridx = 4;
		gbc_btnTan.gridy = 1;
		panel.add(btnTan, gbc_btnTan);
		
		btnLn = new JButton("ln");
		btnLn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "ln()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnLn = new GridBagConstraints();
		gbc_btnLn.insets = new Insets(0, 0, 0, 5);
		gbc_btnLn.gridx = 5;
		gbc_btnLn.gridy = 1;
		panel.add(btnLn, gbc_btnLn);
		
		btnLog = new JButton("log");
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "log()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnLog = new GridBagConstraints();
		gbc_btnLog.insets = new Insets(0, 0, 0, 5);
		gbc_btnLog.gridx = 6;
		gbc_btnLog.gridy = 1;
		panel.add(btnLog, gbc_btnLog);
		
		btnExp = new JButton("exp");
		btnExp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.requestFocus();
				try {
					textField.getDocument().insertString(textField.getCaretPosition(), "exp()", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setCaretPosition(textField.getCaretPosition()-1);
			}
		});
		GridBagConstraints gbc_btnExp = new GridBagConstraints();
		gbc_btnExp.gridx = 7;
		gbc_btnExp.gridy = 1;
		panel.add(btnExp, gbc_btnExp);
		
		lblNewLabel_3 = new JLabel("Assign:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 3;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 3;
		contentPane.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Ex: a=1, b=2, ..");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 4;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		lblNewLabel_1 = new JLabel("PostFix Expression:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 5;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		btnNewButton_1 = new JButton("PostFix to InFix");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PostFix postfix = new PostFix(textField_1.getText().trim());
				InFix infix = new InFix(postfix);
				textField.setText(infix.getStr());
				textField_1.setText(postfix.getStr());
				textField_2.setText(postfix.getResult());

	            DefaultTableModel model = (DefaultTableModel) table.getModel();
	            model.setRowCount(0);
			}
		});
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 6;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 6;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		lblNewLabel_2 = new JLabel("Result:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 7;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		btnNewButton_2 = new JButton("Reset");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
	            DefaultTableModel model = (DefaultTableModel) table.getModel();
	            model.setRowCount(0);
			}
		});
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 8;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 4;
		gbc_btnNewButton_2.gridy = 8;
		contentPane.add(btnNewButton_2, gbc_btnNewButton_2);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setShowHorizontalLines(false);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Token", "Stack", "Postfix"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		
		scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 9;
		contentPane.add(panel_1, gbc_panel_1);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Program.class.getResource("/RPN/gif-in-java-4.gif")));
		panel_1.add(label);
	}
}
