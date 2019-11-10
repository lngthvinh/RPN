package RPN;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.event.ActionEvent;

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
		setBounds(100, 100, 1024, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(20);
		
		JButton btnNewButton = new JButton("InFix to PostFix");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InFix infix = new InFix(textField.getText().replace(" ", ""));
				PostFix postfix = new PostFix(infix);
				textField.setText(infix.getStr());
				textField_1.setText(postfix.getStr());
				textField_2.setText(postfix.getResult());
				infixToPostfix(infix);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		table = new JTable();
		table.setShowHorizontalLines(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Token", "Stack", "Postfix"},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 2;
		gbc_table.gridheight = 7;
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		contentPane.add(table, gbc_table);
		
		lblNewLabel_1 = new JLabel("PostFix Expression:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		btnNewButton_1 = new JButton("PostFix to InFix");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PostFix postfix = new PostFix(textField_1.getText().trim());
				InFix infix = new InFix(postfix);
				textField.setText(infix.getStr());
				textField_1.setText(postfix.getStr());
				textField_2.setText(postfix.getResult());
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 3;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		lblNewLabel_2 = new JLabel("Result:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 4;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 5;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		btnNewButton_2 = new JButton("Reset");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
	            DefaultTableModel model = (DefaultTableModel) table.getModel();
	            model.setRowCount(1);
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 3;
		gbc_btnNewButton_2.gridy = 5;
		contentPane.add(btnNewButton_2, gbc_btnNewButton_2);
	}
}
