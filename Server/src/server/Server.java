/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: Server.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package server;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

public class Server {
	
	private static serverGUI GUI;
	public static void main(String[] args) {

		ServerSocket listeningSocket = null;
		
		try {
			
			try {
			listeningSocket = new ServerSocket( Integer.parseInt(args[0]));
			Dictionary.getInstance().ReadJSON(args[1]);
			}catch(Exception e){
				JFrame parent = new JFrame();
			    //JOptionPane.showMessageDialog(parent, "There is a server has opened!");
			    Object[] options = {"CLOSE"}; 
			    JOptionPane.showOptionDialog(null, "OPEN ERROR!", "Warning", 
			    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
			    null, options, options[0]);
			    System.exit(0);
			}
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					GUI = new serverGUI();
					
					GUI.addWindowListener(new WindowAdapter()
				    {
				        public void windowClosing( WindowEvent e)
				        {
				            try {
				            	Dictionary.getInstance().WriteJSON(args[1]);
				            	
				            	JSONObject mesg = new JSONObject();
				            	mesg.put("command", "exit");
				            	List<ClientConnection> list = ServerState.getInstance().getConnectedClients();
				            	for(int i=0;i< list.size();i++) {
				            		list.get(i).getWriter().writeUTF(mesg.toJSONString());
				            		list.get(i).getWriter().flush();
				            	}

								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
				            System.exit(0);
				                
				        }
				    });
				}
			});
			
			int clientNum = 0;
			
			//Listen for incoming connections for ever 
			while (true) {
				//Accept an incoming client connection request 
				Socket clientSocket = listeningSocket.accept();
				System.out.println(Thread.currentThread().getName() + " - Client conection accepted");
				clientNum++;
								
				//Create a client connection to listen for and process all the messages
				//sent by the client
				ClientConnection clientConnection = new ClientConnection(clientSocket, clientNum, GUI);
				clientConnection.setName("Thread" + clientNum);
				clientConnection.start(); 
				
				//Update the server state to reflect the new connected client
				ServerState.getInstance().clientConnected(clientConnection);
			}
		} catch (IOException e) {
			System.out.println("Client lost connection1!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Client lost connection2!");
			e.printStackTrace();
		} finally {
			if(listeningSocket != null) {
				try {
					Object[] options = {"CLOSE"}; 
				    JOptionPane.showOptionDialog(null, "SERVER ERROR!", "Warning", 
				    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
				    null, options, options[0]);
				    
					listeningSocket.close();
				} catch (IOException e) {
					Object[] options = {"CLOSE"}; 
				    JOptionPane.showOptionDialog(null, "SERVER ERROR!", "Warning", 
				    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
				    null, options, options[0]);
					System.out.println("Server close2!");
					e.printStackTrace();
				}
			}
		}
	
	}

}