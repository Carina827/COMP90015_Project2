/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: Client.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package client;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.net.*;
import javax.swing.*;
import java.net.*;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.awt.EventQueue;

//import client.config.Config;
//import client.config.ConfigLoader;

public class Client {

	public static void main(String[] args) {

		try {
			//PopWindow pop = new PopWindow();
			int port = Integer.parseInt(args[1]);
			
			// Create a stream socket and connect it to the server 
			Socket socket = new Socket(args[0], port);
			System.out.println("Connection with server established");
			
			DataInputStream reader = new DataInputStream(new DataInputStream(socket.getInputStream()));
			DataOutputStream writer = new DataOutputStream(new DataOutputStream(socket.getOutputStream()));

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					ClientGUI GUI = new ClientGUI(reader, writer);
					//System.out.println("Connection with server established 1");
						
					GUI.addWindowListener(new WindowAdapter()
				    {
				        public void windowClosing( WindowEvent e)
				        {
				            try {
				            	JSONObject mesg = new JSONObject();
				            	mesg.put("command", "exit");
								writer.writeUTF(mesg.toJSONString());
								writer.flush();
					            socket.close();
					            
								} catch (IOException e1) {
									JFrame parent = new JFrame(); 
									JOptionPane.showMessageDialog(parent, "SERVER HAS CLOSED!"); 
									System.exit(0);
									// TODO Auto-generated catch block
									//e1.printStackTrace();
								}catch (Exception e1) {
									JFrame parent = new JFrame(); 
									//JOptionPane.showMessageDialog(parent, "SERVER HAS CLOSED!"); 
									System.exit(0);
									// TODO Auto-generated catch block
									//e1.printStackTrace();
								}
				            System.exit(0);
				                
				        }
				    });
					MessageListener ml = new MessageListener(reader, GUI);
					ml.start();
						
					}
				
			});
					
		} catch (ConnectException e) {
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "CONNECTION ERROR!" );
			System.exit(0);
			//e.printStackTrace();
		} 
		catch (Exception e) {
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "CONNECTION ERROR!");
			System.out.println("SERVER IS CLOSED!");
			System.exit(0);
			//e.printStackTrace();
		} 

	
	}
}