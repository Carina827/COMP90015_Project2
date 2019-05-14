/***
 * The University of Melbourne
 * COMP90015 Distributed Systems
 * FileName: Dictionary.java 
 
 * This class constructs two methods for storing the player's name 
   and returning the number of stones removed.
 
 * @author  Jing Du
 * @Student Number  775074
 * @Username  du2
 * @E-mail.addr  du21@student.unimelb.edu.au
 * @Date  06/09/2018 
 ***/
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;



public class Dictionary {
	
	private static Dictionary instance;
	private static JSONObject dic= new JSONObject();
	
	public static synchronized Dictionary getInstance() {
		if(instance == null) {
			instance = new Dictionary();
		}
		return instance;
	}
	
	public static synchronized JSONObject process(String command,String word, String meaning) {
		
		JSONObject mesg = new JSONObject() ;
		if(command.equals("add")) {
    		mesg = Add(word, meaning);
		}
		else if(command.equals("delete")) {
    		mesg = Delete(word);
		}
		else if(command.equals("query")) {
    		mesg = Query(word);
		}
		else if(command.equals("modification")) {
			mesg = Modify(word, meaning);
		}
		return	mesg;
	}
	
	
	public static JSONObject Add (String word, String meaning) {
		String message = "";
		JSONObject m = new JSONObject();
		try {
			if(dic.get(word)!=null) {
				message = "This word already exists in the dictionary.";
			}
			else {
				dic.put(word, meaning);
				message = "Successful!";
			}
			m.put("word", word);
			m.put("meaning", meaning);
			m.put("message", message);
			m.put("command", "");
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		System.out.println(m.toJSONString());
		return m;
	}
	
	public static JSONObject Delete (String word) {
		String message = "";
		JSONObject m = new JSONObject();
		try {
			if(dic.get(word)==null) {
				message = "This word not exists in the dictionary.";
			}
			else{
				dic.remove(word);
				message = "Successful!";
			}
			m.put("word", word);
			m.put("meaning", null);
			m.put("message", message);
			m.put("command", "");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	
	public static JSONObject Query (String word) {
		String message = "";
		String meaning = "";
		JSONObject m = new JSONObject();
		try {
			if(dic.get(word)==null) {
				message = "This word not exists in the dictionary.";
				
			}
			else {
				 meaning = (String)dic.get(word);
				 System.out.println("meaning"+meaning);
				 message = "Successful!";
			}
			
			m.put("word", word);
			m.put("meaning", meaning);
			m.put("message", message);
			m.put("command", "");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(m.toJSONString());
		System.out.println("OK");
		return m;
	}
	
	public static JSONObject Modify (String word, String meaning) {
		String message = "";
		JSONObject m = new JSONObject();
		try {
			if(dic.get(word)==null) {
				 message = "This word not exists in the dictionary.";
			}
			else {
				 dic.remove(word);
				 dic.put(word, meaning);
				 message = "Successful!";
			}
			m.put("word", word);
			m.put("meaning", meaning);
			m.put("message", message);
			m.put("command", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("OK");
		return m;
	}
	
	
	public static void ReadJSON(String path){
		JSONParser parser = new JSONParser();
		try {
			FileReader in = new FileReader(path);
			dic = (JSONObject) parser.parse(in);
			in.close();
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "READ SUCCESSFUL!");
		} catch (Exception e) {
			JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "READ ERROR!");
			System.exit(0);
			//e.printStackTrace();
		}
	}
		
		
    public static void WriteJSON(String path){
    	
        BufferedWriter writer = null;
        String fileName = path;
        
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(dic.toString());
            JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "WRITE SUCCESSFUL!");
        } catch (Exception e) {
        	JFrame parent = new JFrame(); 
			JOptionPane.showMessageDialog(parent, "WRITE ERROR!");
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (Exception e) {
            	JFrame parent = new JFrame(); 
    			JOptionPane.showMessageDialog(parent, "WRITE ERROR!");
                e.printStackTrace();
            }
        }
    }
    
    
    
	

}
