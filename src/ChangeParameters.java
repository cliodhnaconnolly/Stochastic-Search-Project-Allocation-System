/* Encoka St. - Stochastic Search Project Allocation System */
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ChangeParameters extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField timesToRunGATextField;
	private JTextField timesToRunSATextField;
	private JCheckBox chckbxS;
	

	public ChangeParameters() {
		setTitle("Change Parameters - SSPAS \u00A9 Encoka St.");
		setIconImage(Toolkit.getDefaultToolkit().getImage(StochasticSearchWindow.class.getResource("/resources/EncokaStLogo.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(294, 229, 140, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JLabel lblHowManyTimes = new JLabel("How many times to run Genetic Algorithm?");
			lblHowManyTimes.setBounds(10, 11, 252, 14);
			getContentPane().add(lblHowManyTimes);
		}
		
		timesToRunGATextField = new JTextField();
		timesToRunGATextField.setBounds(279, 8, 86, 20);
		getContentPane().add(timesToRunGATextField);		
		timesToRunGATextField.setColumns(10);
		
		JLabel lblHowManyTimes_1 = new JLabel("How many times to run Simulated Annealing?");
		lblHowManyTimes_1.setBounds(10, 41, 252, 14);
		getContentPane().add(lblHowManyTimes_1);
		
		timesToRunSATextField = new JTextField();
		timesToRunSATextField.setColumns(10);
		timesToRunSATextField.setBounds(279, 38, 86, 20);
		getContentPane().add(timesToRunSATextField);
		
		JLabel lblpleaseBearIn = new JLabel("(Please bear in mind SA takes between 2-10 seconds to run and ");
		lblpleaseBearIn.setBounds(10, 66, 393, 14);
		getContentPane().add(lblpleaseBearIn);
		
		JLabel lblAndGaAround = new JLabel("and GA around 15-40 seconds )");
		lblAndGaAround.setBounds(10, 81, 235, 14);
		getContentPane().add(lblAndGaAround);
		
		JLabel lblActivateVeryGood = new JLabel("Activate very good, but slow SA");
		lblActivateVeryGood.setBounds(10, 105, 198, 14);
		getContentPane().add(lblActivateVeryGood);
		
		chckbxS = new JCheckBox("Yes");
		chckbxS.setBounds(279, 101, 78, 23);
		getContentPane().add(chckbxS);
		
		JLabel lblweWouldRecommend = new JLabel("(We would recommend only running this once)");
		lblweWouldRecommend.setBounds(10, 119, 312, 14);
		getContentPane().add(lblweWouldRecommend);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 149, 393, 2);
		getContentPane().add(separator);
		
		JLabel lblHelp = new JLabel("Need help?");
		lblHelp.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHelp.setBounds(10, 162, 112, 20);
		getContentPane().add(lblHelp);
		
		JLabel lblPleaseDoNot = new JLabel("Please do not hesistate to contact us at");
		lblPleaseDoNot.setBounds(146, 167, 257, 14);
		getContentPane().add(lblPleaseDoNot);
		
		JLabel lblEncokastgmailcomIfYou = new JLabel(" encokast@gmail.com if you need help with bugs, would like");
		lblEncokastgmailcomIfYou.setBounds(20, 184, 383, 14);
		getContentPane().add(lblEncokastgmailcomIfYou);
		
		JLabel lblGeneralHelpWith = new JLabel("general help with this program or if you have any suggestions");
		lblGeneralHelpWith.setBounds(20, 204, 383, 14);
		getContentPane().add(lblGeneralHelpWith);
		
		JLabel lblOnHowWe = new JLabel("on how we could improve.");
		lblOnHowWe.setBounds(20, 224, 383, 14);
		getContentPane().add(lblOnHowWe);
	}
	
	public boolean getSlowSAValue(){
		return chckbxS.isSelected();
	}
	
	public int getTimesToRunGA(){
		int timesToRunGA = 1;
		String temp = timesToRunGATextField.getText();
		try{
			if(!temp.equals("") && isInteger(temp)){
				timesToRunGA = Integer.parseInt(temp);
			}
		} catch (NullPointerException e) {}		
		return timesToRunGA;
		
	}
	
	public int getTimesToRunSA(){
		int timesToRunSA = 10;
		String temp = timesToRunSATextField.getText();
		try{
			if(!temp.equals("") && isInteger(temp)){
				timesToRunSA = Integer.parseInt(temp);
			}
		} catch (NullPointerException e) {}
		return timesToRunSA;
		
	}
	
	private boolean isInteger(String s){
		for(int i=0; i<s.length(); i++){
			if(!Character.isDigit(s.charAt(i))){
				return false;
			}
		}
		return true;
	}
}
