/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: ClientConnection.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ClientConnection extends Thread {

	private Socket clientSocket;
	private DataInputStream reader;
	private DataOutputStream writer;
	private int clientNum;
	private serverGUI GUI;

	public ClientConnection(Socket clientSocket, int clientNum, serverGUI GUI) {
		try {
			this.GUI = GUI;
			this.clientSocket = clientSocket;
			reader = new DataInputStream (clientSocket.getInputStream());
			writer = new DataOutputStream (clientSocket.getOutputStream());
			this.clientNum = clientNum;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DataInputStream getReader() {
		return reader;
	}
	
	public DataOutputStream getWriter() {
		return writer;
	}

	
	
	public int getNum() {
		return clientNum;
	}

	@Override
	public void run() {
		String word = "";
		String command = "";
		String meaning = "";
		

		try {
			String mesg ="";
			mesg = "Client " + clientNum + " log in ~";
			GUI.display(ServerState.getInstance().getConnectedClients(), mesg);
			JSONParser parser = new JSONParser();
			System.out.println(Thread.currentThread().getName() 
					+ " - Reading messages from client's " + clientNum + " connection");
			while(true) {
			
			if(reader.available() > 0){
		    	// Attempt to convert read data to JSON
				String m = reader.readUTF();
		    	JSONObject clientMsg = (JSONObject) parser.parse(m);
		    	System.out.println("COMMAND RECEIVED: "+clientMsg.toJSONString());
		    	command =(String)clientMsg.get("command");
		    	if(command.equals("exit")){
		    		mesg = "Client" + clientNum+" loss connection!";
		    		System.out.println("Client" + clientNum+" loss connection!");
		    		break;
		    	}	
		    	else{
		    	word =(String)clientMsg.get("word");
		    	meaning =(String)clientMsg.get("meaning");
		    	JSONObject serverMsg = new JSONObject();
		    	serverMsg = Dictionary.getInstance().process(command, word, meaning);
		    	writer.writeUTF(serverMsg.toJSONString());
		    	writer.flush();
		    	}
		    		//JSONObject results = new JSONObject();
		    		//results.put("result", result);
		    		//writer.writeUTF(results.toJSONString());
				}
		    //}
			}
			ServerState.getInstance().clientDisconnected(this);
			GUI.display(ServerState.getInstance().getConnectedClients(), mesg) ;
			

				//Broadcast the client message to all other clients connected
				//to the server.
			//List<ClientConnection> clients = ServerState.getInstance().getConnectedClients();
			//for(ClientConnection client : clients) {
			//		client.write(clientMsg.toJSONString());
			//}
		
			//clientSocket.close();
			//ServerState.getInstance().clientDisconnected(this);
			//System.out.println(Thread.currentThread().getName() 
					//+ " - Client " + clientNum + " disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Needs to be synchronized because multiple threads can me invoking this method at the same
	//time
	//public synchronized void write(JSONObject msg) {
	//	try {
	//		String serverMsg = msg.toJSONString();
	//		writer.writeUTF(serverMsg);
	//		writer.flush();
	//		System.out.println(Thread.currentThread().getName() + " - Message sent to client " + clientNum);
	//	} catch (IOException e) {
	//		e.printStackTrace();
	//	}
	//}

}