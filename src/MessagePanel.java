import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.ScrollPane;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Client client;
	private ChatRoom currentChat;
	//private List<Messages> messages;
	private ObjectOutputStream out;
    private ObjectInputStream in;
	private JFormattedTextField textBox;
	
	private MessageListener listener;

	// Edittable
	JTextArea roomMembers;
	private JTextArea textMessages;
	public interface MessageListener {
		void onSendMessage(Message message) throws IOException;
	}
	
	public void setListener (MessageListener listener) {
		this.listener = listener;
	}

	public MessagePanel(Client client, ChatRoom currentChat) {
		this.currentChat = currentChat;
		this.client = client;
		initUI();
	}

	public JTextArea getTextMessages() {
		return textMessages;
	}

	public void initUI () {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		
		// Panel for text box 
		JPanel textBorder = new JPanel();
		textBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		textBorder.setBounds(3, 391, 431, 40);
		add(textBorder);
		textBorder.setLayout(new BorderLayout(0, 0));
		
		textBox = new JFormattedTextField();
		textBox.setToolTipText("Type message...");
		textBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textBox.setHorizontalAlignment(SwingConstants.LEFT);
		textBox.setColumns(30);
		textBorder.add(textBox);
		
		// Panel for send button... putting button inside a border makes modifications easy!
		JPanel sendBorder = new JPanel();
		sendBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		sendBorder.setBounds(450, 393, 75, 38);
		add(sendBorder);
		sendBorder.setLayout(new BorderLayout(0, 0));
		
		// Panel for text messages box
		JPanel textMessagesBorder = new JPanel();
		textMessagesBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		textMessagesBorder.setBounds(3, 30, 431, 351);
		add(textMessagesBorder);
		textMessagesBorder.setLayout(new BorderLayout(0, 0));
		
		textMessages = new JTextArea();
		populateChatMessages();
		textMessages.setEditable(false);
		//textMessages.append(test);
		//textMessagesBorder.add(textMessages);
		textMessages.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textMessages.setWrapStyleWord(true);
		textMessages.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textMessages);
		scrollPane.setForeground(new Color(216, 191, 216));
		scrollPane.setFont(new Font("Dubai Medium", Font.PLAIN, 5));
		scrollPane.setBackground(new Color(245, 245, 245));
		//scrollPane.setBounds(3, 32, 422, 350);
		textMessagesBorder.add(scrollPane, BorderLayout.CENTER);
		//scrollPane.add(textMessages);
		//add(scrollPane);

		JButton btnNewButton = new JButton("SEND");
		sendBorder.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				sendButtonClicked();
            }
		});
		
		// Panel for participants
		JPanel participantsBorder = new JPanel();
		participantsBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		participantsBorder.setBounds(3, 1, 431, 25);
		add(participantsBorder);
		participantsBorder.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRoomParticipants = new JLabel("Participants:");
		lblRoomParticipants.setBackground(new Color(240, 240, 240));
		lblRoomParticipants.setForeground(new Color(0, 0, 0));
		lblRoomParticipants.setFont(new Font("Monospaced", Font.PLAIN, 12));
		participantsBorder.add(lblRoomParticipants, BorderLayout.WEST);
		
		roomMembers = new JTextArea();
		//roomMembers.setText(" John (Online), Mark, Dave\r\n");
		populateParticipants();
		// roomMembers.setText(test);
		 roomMembers.setToolTipText("List of current room's members.");
		roomMembers.setEditable(false);
		roomMembers.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		participantsBorder.add(roomMembers, BorderLayout.CENTER);
	}

	public void sendButtonClicked() {
		System.out.println("Send Button Pressed");
		if (listener != null) {
			try {
				//listener.onSendMessage(textBox.getText());
				Message newMessage = new Message(textBox.getText(), client.getCurrentUser().getUsername(), currentChat.getChatID());
				listener.onSendMessage(newMessage);
				System.out.println("Listener inside send button" + textBox.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			textBox.setText("");
			textBox.requestFocus();
		}
	}

	/**
	 * Initializes the message panel for a specific chat room.
	 * 
	 * @param chatroom The chat room to set up the message panel for.
	 *                 This method will update the panel to display
	 *                 messages, participants, and other relevant information
	 *                 related to the provided chat room.
	 */
	public void setupChatroom(ChatRoom chatroom) { //change current view
		// update participants list and chat messages
		currentChat = chatroom;
		populateParticipants();
		populateChatMessages();
	}
	// public void setCurrentChat(ChatRoom chatroom) {
	// 	this.currentChat = chatroom;
	// }

	public ChatRoom getCurrentChat() {
		return currentChat;
	}

	private void populateParticipants() {
		String toPlace = "";
		for (String user : currentChat.getParticipants()) {
			toPlace = toPlace + user + ", ";
		}
		roomMembers.setText(toPlace);
		roomMembers.revalidate();
		roomMembers.repaint();
	}

	private void populateChatMessages() {		//String test = "Testing textMessage";
		String test = "";
		// Populate text area
		for (Message m : currentChat.getMesssages())
			test = test + m.toString() + "\n";
		
		textMessages.setText(test);
	}
}