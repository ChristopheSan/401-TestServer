import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.awt.GridLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class viewLogChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Client client;
//	private Map<String,String> users;		// Username, display name
	private List<User> users;
	private User currentUser;
	
	// Access all keys to the map and fill list with the values
	
	public viewLogChatPanel() {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		
		users = client.generateUsers(5, 10);
		currentUser = users.get(0);
		
		// Panel for text messages box
//		JPanel chatListBorder = new JPanel();
//		chatListBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		chatListBorder.setBounds(3, 30, 431, 360);
//		add(chatListBorder);
//		chatListBorder.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setBounds(3, 31, 431, 359);
		
		
		DefaultListModel userData = new DefaultListModel();
        for (User user : users) {
        	userData.addElement(user.getUsername());
        }
       
		
//		AbstractListModel<String> userListModel = new AbstractListModel<String>() {
//            @Override
//            public int getSize() {
//                return userData.size();
//            }
//
//            @Override
//            public String getElementAt(int index) {
//                return userData.get(index);
//            }
//        };
       
		JList userList = new JList<String>(userData);
		
		userList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					DefaultListModel chatData = new DefaultListModel();
					for (ChatRoom room : currentUser.getChats())
						chatData.addElement(room);
					
					JList<ChatRoom>  chatrooms = new JList(chatData);
//					AbstractListModel<String> chatListModel = new AbstractListModel<String>() {
//			            @Override
//			            public int getSize() {
//			                return chatData.size();
//			            }
//
//			            @Override
//			            public String getElementAt(int index) {
//			                return (String) chatData.get(index);
//			            }
//			        };
			       
//					JList chatList = new JList<ChatRoom>(chatrooms);
					
					
					splitPane.setRightComponent(chatrooms);
				}
			}
		});

		
		
		splitPane.setLeftComponent(userList);
		
//		List<String> chatData = new ArrayList<>();
//        chatData.add("Chat 1");
//        chatData.add("Chat 2");
//        chatData.add("Chat 3");
//        chatData.add("Chat 4");
//        chatData.add("Chat 5");
//		
//		AbstractListModel<String> chatListModel = new AbstractListModel<String>() {
//            @Override
//            public int getSize() {
//                return chatData.size();
//            }
//
//            @Override
//            public String getElementAt(int index) {
//                return chatData.get(index);
//            }
//        };
//       
//		JList chatList = new JList<String>(chatListModel);
//		
//		
//		splitPane.setRightComponent(chatList);
		add(splitPane);
		
		//add(scrollPane);
		// Panel for participants
		JPanel promptBorder = new JPanel();
		promptBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		promptBorder.setBounds(3, 1, 431, 25);
		add(promptBorder);
		promptBorder.setLayout(new BorderLayout(0, 0));
		
		JTextArea promptText = new JTextArea();
		promptText.setText("Select chat to view\r\n");
		promptText.setToolTipText("List of current room's members.");
		promptText.setEditable(false);
		promptText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		promptBorder.add(promptText, BorderLayout.CENTER);
		
		// Panel for send button... putting button inside a border makes modifications easy!
				JPanel cancelBorder = new JPanel();
				cancelBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
				cancelBorder.setBounds(353, 392, 81, 38);
				add(cancelBorder);
				cancelBorder.setLayout(new BorderLayout(0, 0));
				
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setToolTipText("Hit button to send message...");
				btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
				cancelBorder.add(btnCancel, BorderLayout.CENTER);
				
				JPanel confirmBorder = new JPanel();
				confirmBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
				confirmBorder.setBounds(255, 392, 81, 38);
				add(confirmBorder);
				confirmBorder.setLayout(new BorderLayout(0, 0));
				
				JButton btnConfirm = new JButton("OK");
				confirmBorder.add(btnConfirm, BorderLayout.CENTER);
				btnConfirm.setToolTipText("Press OK to confirm selection");
				btnConfirm.setFont(new Font("Dialog", Font.BOLD, 12));
				btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						
						
						
						
						}
				});
				
		
		
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