package RPN;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

public class main extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JLabel lb0, lb1, lb2, lb3;
	private JTextField textField1, textField2, textField3;
	private JButton btn1, btn2, btn3;
	
	public void setTextField() {
	
		textField1.setText(InFix.str);
		textField2.setText(PostFix.str);
		textField3.setText(PostFix.result());
	}
	public void createLabel() {
		
		lb0 = new JLabel("RPN Convert");
		GridBagConstraints gbc_lb0 = new GridBagConstraints();
		gbc_lb0.gridwidth = 2;
		gbc_lb0.gridx = 0;
		gbc_lb0.gridy = 0;
		contentPane.add(lb0, gbc_lb0);
		
		lb1 = new JLabel("InFix Expression:");
		GridBagConstraints gbc_lb1 = new GridBagConstraints();
		gbc_lb1.anchor = GridBagConstraints.WEST;
		gbc_lb1.gridx = 0;
		gbc_lb1.gridy = 1;
		contentPane.add(lb1, gbc_lb1);

		lb2 = new JLabel("PostFix Expression:");
		GridBagConstraints gbc_lb2 = new GridBagConstraints();
		gbc_lb2.anchor = GridBagConstraints.WEST;
		gbc_lb2.gridx = 0;
		gbc_lb2.gridy = 3;
		contentPane.add(lb2, gbc_lb2);
		
		lb3 = new JLabel("Result:");
		GridBagConstraints gbc_lb3 = new GridBagConstraints();
		gbc_lb3.anchor = GridBagConstraints.WEST;
		gbc_lb3.gridx = 0;
		gbc_lb3.gridy = 5;
		contentPane.add(lb3, gbc_lb3);
	}
	public void createTextField() {
		
		textField1 = new JTextField();
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField1.gridx = 0;
		gbc_textField1.gridy = 2;
		contentPane.add(textField1, gbc_textField1);
		textField1.setColumns(28);

		textField2 = new JTextField();
		GridBagConstraints gbc_textField2 = new GridBagConstraints();
		gbc_textField2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField2.gridx = 0;
		gbc_textField2.gridy = 4;
		contentPane.add(textField2, gbc_textField2);
		textField2.setColumns(28);
		
		textField3 = new JTextField();
		GridBagConstraints gbc_textField3 = new GridBagConstraints();
		gbc_textField3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField3.gridx = 0;
		gbc_textField3.gridy = 6;
		contentPane.add(textField3, gbc_textField3);
		textField3.setColumns(28);
	}
	public void createButton() {
		
		btn1 = new JButton("InFix to PostFix");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InFix.str = textField1.getText().replace(" ", "");
				PostFix.str="";
				for(int i=0;i<InFix.toPostFix().size();i++)
					PostFix.str+=InFix.toPostFix().get(i);
				setTextField();
			}
		});
		GridBagConstraints gbc_btn1 = new GridBagConstraints();
		gbc_btn1.gridx = 1;
		gbc_btn1.gridy = 2;
		contentPane.add(btn1, gbc_btn1);
		
		btn2 = new JButton("PostFix to InFix");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PostFix.str = textField2.getText().replace(" ", "");
				InFix.str = PostFix.toInFix();
				setTextField();
			}
		});
		GridBagConstraints gbc_btn2 = new GridBagConstraints();
		gbc_btn2.gridx = 1;
		gbc_btn2.gridy = 4;
		contentPane.add(btn2, gbc_btn2);
		
		btn3 = new JButton("Reset");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InFix.str = "";
				PostFix.str = "";
				setTextField();
			}
		});
		GridBagConstraints gbc_btn3 = new GridBagConstraints();
		gbc_btn3.gridx = 1;
		gbc_btn3.gridy = 6;
		contentPane.add(btn3, gbc_btn3);
	}
	public JPanel createContentPane() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		createLabel();
		createTextField();
		createButton();
		return contentPane;
	}
	public main() {
		
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("RPN");
		setSize(430,200);
		setLocationRelativeTo(null);
		setResizable(false);
		add(createContentPane());
	}
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
	}
	public static void main(String[] args) {
		new main().setVisible(true);
	}
}
