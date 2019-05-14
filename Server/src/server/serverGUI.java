/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: ServerGUI.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package server;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Window.Type;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextField;

public class serverGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea clientList= new JTextArea("");;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public serverGUI() {
		setBackground(new Color(244, 164, 96));
		setTitle("Dictionary System Server");
		setForeground(new Color(250, 240, 230));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 403, 351);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 239, 213));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel listLabel = new JLabel("Client List:");
		listLabel.setFont(new Font("Yuppy SC", Font.PLAIN, 15));
		listLabel.setForeground(new Color(0, 0, 0));
		listLabel.setBounds(26, 61, 80, 22);
		contentPane.add(listLabel);
		
		//JTextArea clientList = new JTextArea("");
		clientList.setFont(new Font("Yuppy TC", Font.PLAIN, 15));
		clientList.setBackground(new Color(255, 255, 255));
		clientList.setLineWrap(true);
		clientList.setWrapStyleWord(true);
		clientList.setToolTipText("");
		clientList.setForeground(new Color(100, 149, 237));
		clientList.setBounds(124, 61, 205, 235);
		//contentPane.add(clientList);
		clientList.setColumns(10);
		clientList.setLineWrap(true);   
		clientList.setWrapStyleWord(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(129, 79, 194, 224);
        this.getContentPane().add(scrollPane_1);
        scrollPane_1.setColumnHeaderView(clientList);
		
		
		
		JLabel lblHint = new JLabel("Hint：");
		lblHint.setFont(new Font("Yuppy TC", Font.PLAIN, 15));
		lblHint.setBounds(57, 14, 41, 35);
		contentPane.add(lblHint);
		
		textField = new JTextField("");
		textField.setBackground(new Color(255, 239, 213));
		textField.setFont(new Font("Yuppy TC", Font.PLAIN, 15));
		textField.setBounds(122, 18, 207, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		this.setVisible(true);
		
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	

	public JTextArea getClientList() {
		return clientList;
	}

	public void setClientList(JTextArea jTextArea) {
		this.clientList = jTextArea; 
	}
	
	

	public void display(List<ClientConnection> cList, String message) {
		//getClientList().setText("");
		String name;
		String Num;
		this.clientList.setText("");
		this.textField.setText("");
		this.textField.setText(message);
		// Print the messages to the console
		for(int i=0;i<cList.size();i++) {
			name = cList.get(i).getName();
			Num = Integer.toString(cList.get(i).getNum());
			getClientList().append(Num + "   " +name+ "\n");
		}
	}

}
