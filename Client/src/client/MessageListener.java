/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: MessageListener.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;

import org.json.simple.parser.JSONParser;

public class MessageListener extends Thread {

	private DataInputStream reader;
	private DataOutputStream writer;
	private ClientGUI frame;
	
	
	public MessageListener(DataInputStream reader, ClientGUI frame) {
		this.setReader(reader);
		this.frame =frame;
	}
	

	

	@Override
	public void run() {
		
		try {
			//Read messages from the server while the end of the stream is not reached
			while(true) {
			if(getReader().available() > 0) {
				JSONParser parser = new JSONParser();
				JSONObject serverMsg = (JSONObject) parser.parse(getReader().readUTF());
				String command = (String)serverMsg.get("command");
				if (command.equals("")) {
					String word =(String)serverMsg.get("word");
					String message =(String)serverMsg.get("message");
					String meaning =(String)serverMsg.get("meaning");
					frame.display(word, meaning, message);
				}
				else {
					JFrame parent = new JFrame(); 
					JOptionPane.showMessageDialog(parent, "SERVER CLOSE, PLEASE RECONNECT!");
					System.exit(0);
				}
			}
			}

		} catch (SocketException e) {
			frame.display("", "", "PLEASE RECONNECT!");
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "PLEASE RECONNECT!");
			System.out.println("Socket closed because the user typed exit");
		} catch(IOException e) {
			//e.printStackTrace();
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "PLEASE RECONNECT!");
			frame.display("", "", "PLEASE RECONNECT!");
			System.out.println("I/O ERROR!"); 
		} catch (Exception e) {
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "PLEASE RECONNECT!");
			frame.display("", "", "PLEASE RECONNECT!");
			System.out.println("ERROR!"); 
			//e.printStackTrace();
		} 
		
	}



	public DataInputStream getReader() {
		return reader;
	}



	public void setReader(DataInputStream reader) {
		this.reader = reader;
	}
	
}


