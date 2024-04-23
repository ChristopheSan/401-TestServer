import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;

public class LoginUI extends JFrame {
	private JTextField usernameBox;
	private JPasswordField passwordBox;
	private JButton loginButton;
	private Client client; // Reference to client

	private boolean awaitingServer; // flag to determine if waiting for server response
	
	public LoginUI(Client client) {
		this.client = client;

		//Frames 
		setTitle("Login");
		setSize(350,300); //size
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(new EmptyBorder(50,80,50,80)); //padding for panel
		getContentPane().setBackground(new Color(21, 96, 130)); // bg color
		borderPanel.setOpaque(false); //transparent
		
		//borderPanel.setBorder(null);
		
		//components
		JPanel loginPanel = new JPanel(new GridLayout(3,2,5,5));
		//loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		loginPanel.setOpaque(false);
		//borderPanel.add(loginPanel,new FlowLayout(FlowLayout.CENTER,10,10));
		borderPanel.add(loginPanel);
		
		//add login components
		//username field
	//	loginPanel.add(new JLabel("Username: "));
		usernameBox = new JTextField(15);
		usernameBox.setBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Username: "));
		usernameBox.setForeground(Color.WHITE);
		usernameBox.setOpaque(false);
		//usernameBox.setSelectedTextColor(Color.WHITE);
		usernameBox.setCaretColor(Color.WHITE);
		usernameBox.setSelectionColor(Color.WHITE);
		loginPanel.add(usernameBox);
		
		//password field
		//loginPanel.add(new JLabel("Password: "));
		passwordBox = new JPasswordField(15);  //first 2 letters of name, last 2 of surname, + 4 digit ID
		passwordBox.setBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Password: "));
		passwordBox.setOpaque(false);
		passwordBox.setCaretColor(Color.WHITE);
		loginPanel.add(passwordBox);
		
		//login button
	    loginButton = new JButton("Login");
	    loginButton.setOpaque(true);
	    //loginButton.setBorder(new LineBorder(new Color(78,167,46))); 
	   // loginButton.setBackground(new Color(78,167,46)); // button background
	    loginButton.setForeground(new Color(21, 96, 130)); // text color
	    //loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        borderPanel.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent loginEvent) {
                login();
            }
        });
        
        add(borderPanel);
        //loginPanel.add(loginButton);
        setLocationRelativeTo(null);
        setVisible(true);
	
	}
	
	public void login() {
		awaitingServer = true; // flag set
		setWaiting();

		String username = usernameBox.getText();
		String password = new String(passwordBox.getPassword());
		
		//authentication here
		// Create login message to send to server
		LoginMessage request = new LoginMessage(password, username);

		// pass to a new thread
		new Thread(()->client.sendMessageToServer(request));

		// 
		
		//Pseudocode
		// if (user exists) then 
			//showMessageDialog "Login Success"
		// else
			//showMessageDialog "Login Failed", ERROR_MESSAGE
		
		
		
		//Login Success then Home screen for message app is revealed
		//Show standard view for standard users
		//Show IT view for IT users, it is the same as standard view but with extra buttons 
		//Will be handled in ClientGUI 
		
	}

	// Call this method when a response message is received from the server
	// This will allow the user to retry their login 
	public void updateWaitingStatus(){
		if (awaitingServer) 
			awaitingServer = false;
	}

	public void clearPasswordBox() {
		passwordBox.setText(""); 
	}

	// Private Methods
	private void setWaiting() {
		// Display waiting indicator 

		awaitingServer = true;
	}

	//testing 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginUI(new Client());
			}
		});
	}
}
