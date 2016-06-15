   // File Name GreetingServer.java

import java.net.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * Greeting Server Class:
 * 
 * This class contains the MAIN Function for the WHOLE GAME server AND the server
 * functions for the CHAT SERVERs
 */
public class GreetingServer extends Thread
{
	//for the logs of the server
	public static LogViewer logs;
   
   private Socket server;
   private static LinkedList<GreetingServer> users = new LinkedList<GreetingServer>();
   private static ServerSocket serverSocket;
   private DataOutputStream out;  
   public String nickname = "DEFAULT NICKNAME";
   
   static GameServer gs;
   static Thread st;
   
   public GreetingServer(Socket server) throws IOException
   {
         this.server = server ;
         this.out = new DataOutputStream(this.server.getOutputStream());
   }

   public void broadcast(String message, String addr){
	   System.out.println("SENDING "+ message + " TO ALL USERS");
	        for(GreetingServer t: users){
            try{
           
            String addr2 = (String) t.server.getRemoteSocketAddress().toString();     
            if(!addr.equals(addr2)){
            	String input = "\n"+this.nickname+": "+message;
            
            	  t.out.writeUTF(input);
            }
            }catch(Exception e){
       
            	
            }
        }
   }
   public void run()
   {
        DataInputStream in;
      boolean connected = true;
      while(connected)
      {
         try
         {

         GreetingServer.logs.SetString("Client Connected: " + this.server.getRemoteSocketAddress().toString()+"\n");
            //GET THE NICKNAME
              in = new DataInputStream(this.server.getInputStream());
             this.nickname = in.readUTF();
            
            broadcast(this.nickname+" Joined the Chat", this.server.getRemoteSocketAddress().toString());
            if(this.nickname.equals("MANINTHEMIDDLE")){
            	
            	for(GreetingServer t: users){
            		if(t == this){
            			broadcast("RECONN",this.server.getRemoteSocketAddress().toString());
            			System.out.println("XD");
            		}else{
            			//users.remove(t);
            		}
            	}
            	
            }
            while (true){
	            /* Read data from the ClientSocket */
	             in = new DataInputStream(this.server.getInputStream());
	           
	             String temp = in.readUTF();
	             System.out.println("SERVER RECEIVED"+temp);
	           
	             broadcast(temp, this.server.getRemoteSocketAddress().toString());
	            
           }
         }catch(SocketTimeoutException s)
         {
        	 GreetingServer.logs.SetString("Socket timed out!\n");
            break;
         }catch(IOException e)
         {
            //e.printStackTrace();
        	 GreetingServer.logs.SetString("Server ended connection to"+ server.getRemoteSocketAddress()+"\n");
            break;
         }
      } 
   }
   public static void main(String [] args) throws IOException
   {
	   	GreetingServer.logs = new LogViewer();
	   	
	   logs.SetString("=======================================\nWELCOME TO FINAL X WARS VERSON 1.0\n");
	   
	   st = new Thread(new Runnable(){
		   
		@Override
		public void run() {
			// TODO Auto-generated method stub
			 try {
				 
				gs = new GameServer();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   });
	   st.start();
	   
      try
      {
    	  
        int port = 10001;
        waitConn(port);
      }catch(ArrayIndexOutOfBoundsException e)
      {
    	  GreetingServer.logs.SetString("Usage: java GreetingServer <port no.> ");
      }
   }


public static void waitConn(int port){
	 GreetingServer.logs.SetString("CHAT SERVER STARTED...\n");
	
    while(true){
         try
        {
         serverSocket = new ServerSocket(port);
         GreetingServer.logs.SetString("Waiting for client on port " + serverSocket.getLocalPort() + "...\n");
         Socket tempserver = serverSocket.accept();
         users.add(new GreetingServer(tempserver));
            users.getLast().start();
            GreetingServer.logs.SetString("Awaiting for more users...\n");
        serverSocket.close();
        }catch(IOException e)
        {
          
        }
             
        }
}

}


/**
a) Socket server = serverSocket.accept();
b) serverSocket = new ServerSocket(port);
**/
