/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: ClientGUI.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package client;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import org.json.simple.JSONObject;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.io.*;
import java.net.SocketException;
import java.util.StringTokenizer;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ClientGUI(DataInputStream reader, DataOutputStream writer) {
		setTitle("Dictionary");
		setBackground(new Color(240, 248, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setForeground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.ORANGE);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = getTextField().getText();
				String meaning = getTextField_1().getText();
				getTextField().setText("");
				getTextField_1().setText("");
				getTextField_2().setText("");
				try {
					if(word.equals("")) {
					getTextField_2().setText("Please input word.");
					
				    }
					else if (meaning.equals("")) {
						getTextField_2().setText("Please input the meaning of the word.");
					
					}
					else {
						JSONObject clientMesg = new JSONObject();
						clientMesg.put("command", "add");
						clientMesg.put("word", word);
						clientMesg.put("meaning", meaning);
						System.out.println(clientMesg.toJSONString());
						writer.writeUTF(clientMesg.toJSONString());
						writer.flush();
					}
				}catch(SocketException e2) {
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "SERVER HAS CLOSED!"); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "ERROR!"); 
				}
			}
		});
		btnAdd.setBounds(48, 87, 107, 35);
		contentPane.add(btnAdd);
		
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setForeground(new Color(147, 112, 219));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = getTextField().getText();
				getTextField().setText("");
				getTextField_1().setText("");
				getTextField_2().setText("");
				try {
					if (word.equals("")) {
						getTextField_2().setText("Please input word.");
					}
					else {
						JSONObject clientMesg = new JSONObject() ;
						clientMesg.put("command", "delete");
						clientMesg.put("word", word);
						clientMesg.put("meaning", null);
						writer.writeUTF(clientMesg.toJSONString());
						writer.flush();
					}
				} catch(SocketException e2) {
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "SERVER HAS CLOSED!"); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "ERROR!"); 
				}
			}
		});
		deleteButton.setBounds(48, 145, 107, 35);
		contentPane.add(deleteButton);
		
		JButton modiButton = new JButton("Modification");
		modiButton.setForeground(new Color(144, 238, 144));
		modiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = getTextField().getText();
				String meaning = getTextField_1().getText();
				getTextField().setText("");
				getTextField_1().setText("");
				getTextField_2().setText("");
				try {
					if( !(word.equals("")) && !(meaning.equals("")))
					{
						JSONObject clientMesg = new JSONObject() ;
						clientMesg.put("command", "modification");
						clientMesg.put("word", word);
						clientMesg.put("meaning", meaning);
						writer.writeUTF(clientMesg.toJSONString());
						writer.flush();
					}
					else {
						if (word.equals(""))
							getTextField_2().setText("Please input word.");
						else if (meaning.equals(""))
							getTextField_2().setText("Please input the meaning of the word.");
					}
				} catch(SocketException e2) {
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "SERVER HAS CLOSED!"); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "ERROR!"); 
				}
			}
		});
		modiButton.setBounds(48, 204, 107, 35);
		contentPane.add(modiButton);
		
		JButton btnNewButton_2 = new JButton("Query");
		btnNewButton_2.setForeground(SystemColor.textHighlight);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = getTextField().getText();
				getTextField().setText("");
				getTextField_1().setText("");
				getTextField_2().setText("");
				try {
					if (word.equals("")) {
						getTextField_2().setText("Please input word.");
					}
					else {
						JSONObject clientMesg = new JSONObject() ;
						clientMesg.put("command", "query");
						clientMesg.put("word", word);
						clientMesg.put("meaning", "");
						writer.writeUTF(clientMesg.toJSONString());
						writer.flush();
					}
				} catch(SocketException e2) {
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "SERVER HAS CLOSED!"); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "ERROR!"); 
				}
			}
		});
		

		btnNewButton_2.setBounds(48, 26, 107, 35);
		contentPane.add(btnNewButton_2);
		
		setTextField(new JTextField());
		getTextField().setBounds(251, 25, 197, 35);
		contentPane.add(getTextField());
		getTextField().setColumns(10);
		
		JLabel lblWords = new JLabel("Word：");
		lblWords.setBounds(186, 30, 53, 16);
		contentPane.add(lblWords);
		
		JLabel lblMeaning = new JLabel("Meaning：");
		lblMeaning.setBounds(186, 77, 74, 16);
		contentPane.add(lblMeaning);
		
		
		
		textField_1= new JTextArea();
		textField_1.setFont(new Font("Yuppy TC", Font.PLAIN, 13));
		textField_1.setBounds(257, 77, 187, 162);
		//contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setLineWrap(true);   
		textField_1.setWrapStyleWord(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
       		scrollPane_1.setBounds(257, 77, 187, 162);
        	this.getContentPane().add(scrollPane_1);
        	scrollPane_1.setColumnHeaderView(textField_1);
	
		JLabel label = new JLabel("");
		label.setBounds(201, 223, 38, 16);
		contentPane.add(label);
		
		JLabel lblHint = new JLabel("Hint：");
		lblHint.setBounds(48, 261, 47, 16);
		contentPane.add(lblHint);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Yuppy TC", Font.PLAIN, 13));
		textField_2.setBounds(96, 251, 352, 38);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setSelectedTextColor(Color.RED);

		this.setVisible(true);
		
	//	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		

	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
		textField.setFont(new Font("Yuppy TC", Font.PLAIN, 13));
	}

	public JTextArea getTextField_1() {
		return textField_1;
	}

	public void setTextField_1(JTextArea jTextArea) {
		this.textField_1 = jTextArea; 
	}	

	public JTextField getTextField_2() {
		return textField_2;
	}

	public void setTextField_2(JTextArea textField_2) {
		this.textField_1 = textField_2;
	}	

public void display(String word, String meaning, String message) {
	// Print the messages to the console
	getTextField().setText(word);
	getTextField_1().setText(meaning);
	getTextField_2().setText(message);
}
}
