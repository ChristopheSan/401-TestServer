import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class CredentialsEditPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtUserName;
	private JTextField txtPassword;
	
	public CredentialsEditPanel() {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		
		// Panel for send button... putting button inside a border makes modifications easy!
		JPanel cancelBorder = new JPanel();
		cancelBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		cancelBorder.setBounds(353, 392, 81, 38);
		add(cancelBorder);
		cancelBorder.setLayout(new BorderLayout(0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Press cancel button to exit");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
		cancelBorder.add(btnCancel, BorderLayout.CENTER);
		
		JPanel applyBorder = new JPanel();
		applyBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		applyBorder.setBounds(255, 392, 81, 38);
		add(applyBorder);
		applyBorder.setLayout(new BorderLayout(0, 0));
		
		JButton btnApply = new JButton("Apply");
		applyBorder.add(btnApply, BorderLayout.CENTER);
		btnApply.setToolTipText("Press Apply to confirm selection");
		btnApply.setFont(new Font("Dialog", Font.BOLD, 12));
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Need to dispatch a new thread and run client.sendMessageToServer()
				// 
				int count = 0;
				String empty = "";
				if ((textBox.getText()).equals(empty)) {
					textBox.setText("");
					textBox.requestFocus();
				} else {
					// handle event of writing passed message to the messages area
					// suggested format: ['timestamp'] + username + ": " + message (textBox.getText()) + Data Type
//					try {
//						//write to message area using suggested format then
//						//flush the buffer;
//					} catch (Exception e1) {
//						textMessages.append("Message was not sent. \n"); //Print error message
//					};
					textBox.setText(""); //set text back to "" 
					textBox.requestFocus(); //send it back to text box..
				}
			}
		});
		
		// Panel for text messages box
		JPanel editMenuBorder = new JPanel();
		editMenuBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		editMenuBorder.setBounds(3, 32, 431, 360);
		add(editMenuBorder);
		editMenuBorder.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(131, 110, 68, 14);
		editMenuBorder.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(131, 135, 68, 14);
		editMenuBorder.add(lblLastName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(131, 160, 68, 14);
		editMenuBorder.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(131, 185, 68, 14);
		editMenuBorder.add(lblPassword);
		
		JLabel lbl_ITPerms = new JLabel("IT User:");
		lbl_ITPerms.setBounds(131, 210, 68, 14);
		editMenuBorder.add(lbl_ITPerms);
		
		txtFirstName = new JTextField();
		txtFirstName.setText("Jose");
		txtFirstName.setBounds(209, 107, 86, 20);
		editMenuBorder.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setText("Galeana");
		txtLastName.setColumns(10);
		txtLastName.setBounds(209, 132, 86, 20);
		editMenuBorder.add(txtLastName);
		
		txtUserName = new JTextField();
		txtUserName.setText("JoseG");
		txtUserName.setColumns(10);
		txtUserName.setBounds(209, 157, 86, 20);
		editMenuBorder.add(txtUserName);
		
		txtPassword = new JTextField();
		txtPassword.setText("drowssap");
		txtPassword.setColumns(10);
		txtPassword.setBounds(209, 182, 86, 20);
		editMenuBorder.add(txtPassword);
		
		JLabel lbl_ITStatus = new JLabel("True");
		lbl_ITStatus.setBounds(209, 210, 46, 14);
		editMenuBorder.add(lbl_ITStatus);
		//add(scrollPane);
		// Panel for participants
		JPanel promptBorder = new JPanel();
		promptBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		promptBorder.setBounds(3, 1, 431, 25);
		add(promptBorder);
		promptBorder.setLayout(new BorderLayout(0, 0));
		
		JTextArea promptText = new JTextArea();
		promptText.setText("Edit credentials in the text fields");
		promptText.setToolTipText("List of current room's members.");
		promptText.setEditable(false);
		promptText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		promptBorder.add(promptText, BorderLayout.CENTER);
		
		// Panel for Online users... Uses JLabel + JList (Might change) 
//		JList list = new JList();
//		list.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		list.setBounds(442, 38, 107, 348);
//		add(list);
//		
//		JPanel usersBorder = new JPanel();
//		usersBorder.setBounds(442, 6, 107, 25);
//		add(usersBorder);
//		usersBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		usersBorder.setLayout(new BorderLayout(0, 0));
//		
//		JLabel lblOnlineUsers = new JLabel("Online Users");
//		lblOnlineUsers.setFont(new Font("Monospaced", Font.PLAIN, 12));
//		lblOnlineUsers.setHorizontalAlignment(SwingConstants.CENTER);
//		usersBorder.add(lblOnlineUsers, BorderLayout.CENTER);


	}
}