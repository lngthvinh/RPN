import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Calculator extends JFrame implements ActionListener
{
	private JPanel panelCalculator, mainPanel, panelOUTPUT;
	private JTextArea textArea;
	private JButton btn;
	private String btnName[] = 
			{ 
					"AC", "C", "+/-" , "^_^",
					"7", "8", "9", "/",
					"4", "5", "6", "*",
					"1", "2", "3", "-",
					"0", ".", "=", "+"
            };
	private JButton myButton(String btnname)
	{
	    btn = new JButton(btnname);
	    btn.setBackground(Color.black);
	    btn.setForeground(Color.white);
	    btn.setFont(new Font("Arial", Font.BOLD, 14));
	    btn.addActionListener(this);
	    return btn;
	}
	private JPanel createTextArea()
	{
		panelOUTPUT = new JPanel(new GridLayout(1, 1));
		panelOUTPUT.setBackground(Color.black);
		textArea = new JTextArea(1, 1);
		//textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textArea.setFont(new Font("Arial", Font.TRUETYPE_FONT, 28));
	    textArea.setEditable(false);
	    textArea.setText("0");
	    panelOUTPUT.add(textArea);
	    return panelOUTPUT;
	}
	public JPanel createButtonOfCalculator()
	{
		panelCalculator = new JPanel(new GridLayout(5,4));
		for(int i=0; i<2; i++)
		{
			panelCalculator.add(myButton(btnName[i]));
			btn.setBackground(Color.red);
		    btn.setForeground(Color.white);
		}
		for(int i=2; i<btnName.length; i++)
		{
			panelCalculator.add(myButton(btnName[i]));
		}
		panelCalculator.setBackground(Color.gray);
		return panelCalculator;
	}
	public JPanel createMainFrame()
	{
		setTitle("RPN Calculator");
		mainPanel = new JPanel(new BorderLayout(1, 1));
		mainPanel.add(createButtonOfCalculator(), BorderLayout.CENTER);
		mainPanel.add(createTextArea(), BorderLayout.NORTH);
	    //mainPanel.setBackground(Color.white);
		return mainPanel;
	}
	public Calculator()
	{
		try 
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            	if ("Nimbus".equals(info.getName())) 
            	{
                	 UIManager.setLookAndFeel(info.getClassName());
                	 break;
            	}
            }
		} 
		catch (Exception e) 
		{
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(240, 330);
		setLocationRelativeTo(null);
		setResizable(false);
		add(createMainFrame());
	}
	private Stack<Double> stack = new Stack<Double>();
	boolean pressedAnswer = false;
	public void actionPerformed(ActionEvent e)
	{
    	String s = textArea.getText();
    	
		if(e.getActionCommand()=="AC")
		{
			textArea.setText("0");
			stack.clear();
			pressedAnswer = false;
		}
		if(e.getActionCommand()=="C")
		{
			if(s.length()==1)
			{
				textArea.setText("0");
			}
			else
			{
				textArea.replaceRange("", s.length()-1, s.length());
			}
			pressedAnswer = false;
		}
	    if(e.getActionCommand()==".")
	    {
	    	boolean sigh = true;
			for(int i=0; i < s.length(); i++)
				if(s.charAt(i) == '.')
				{
					sigh = false; break;
				}
			if(sigh == true) 
			{
				textArea.setText(textArea.getText() + ".");
			}
	    }
	    if(e.getActionCommand()=="+/-")
	    {
	    	if(s.charAt(0)=='-')
	    		textArea.replaceRange("", 0, 1);
	    	else textArea.insert("-", 0);
	    }
		if(e.getActionCommand()=="=")
		{
			stack.push(Double.parseDouble(textArea.getText()));
			pressedAnswer = true;
		}
		if(e.getActionCommand()=="0")
			addNum(s, 0);
		if(e.getActionCommand() == "1")
			addNum(s, 1);
		if(e.getActionCommand()=="2")
			addNum(s, 2);
		if(e.getActionCommand()=="3")
			addNum(s, 3);
		if(e.getActionCommand()=="4")
			addNum(s, 4);
		if(e.getActionCommand()=="5")
			addNum(s, 5);
		if(e.getActionCommand()=="6")
			addNum(s, 6);
		if(e.getActionCommand()=="7")
			addNum(s, 7);
		if(e.getActionCommand()=="8")
			addNum(s, 8);
	    if(e.getActionCommand()=="9")
	    	addNum(s, 9);
	    if(e.getActionCommand()=="+")
	    {
	    	if(stack.size()>0)
	    	{
	    		double result = stack.pop() + Double.parseDouble(textArea.getText());
	    		symply(result);
	    	}
	    }
	    if(e.getActionCommand()=="-")
	    {
	    	if(stack.size()>0)
	    	{
	    		double result = stack.pop() - Double.parseDouble(textArea.getText());
	    		symply(result);
	    	}
	    }
	    if(e.getActionCommand()=="*")
	    {
	    	if(stack.size()>0)
	    	{
	    		double result = stack.pop() * Double.parseDouble(textArea.getText());
	    		symply(result);
	    	}
	    }
	    if(e.getActionCommand()=="/")
	    {
	    	if(stack.size()>0)
	    	{
	    		double result = stack.pop() / Double.parseDouble(textArea.getText());
	    		symply(result);
	    	}
	    }
	}
	private void addNum(String s, Integer n)
	{
		if((s.charAt(0)!='0' || s.length() > 1) && pressedAnswer==false)
			textArea.append(Integer.toString(n));
		else textArea.setText(Integer.toString(n));
		pressedAnswer = false;
	}
	private void symply(double result)
	{
		textArea.setText("");
		String str = Double.toString(result);
		if(result - (int)result != 0)
		{
			textArea.setText(str);			
		}
		else
		{
			for(int i=0; i < str.length(); i++)
			{
				if(str.charAt(i)!='.')
					textArea.setText(textArea.getText()+str.charAt(i));
				else break;
			}
		}
		pressedAnswer = true;
	}
	public static void main(String[] args) 
	{
		new Calculator().setVisible(true);
	}
}
