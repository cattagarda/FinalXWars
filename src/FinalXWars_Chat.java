import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;



public class FinalXWars_Chat extends Thread
{
  protected static Socket client;
  protected static JTextField inputbox = new JTextField(10); //Declare it here for Data Passing
  protected static JTextArea  field = new JTextArea(); //Declare it here for Data Passing
  protected static boolean running  = true;
  static int numfails = 0;
  static int numsuccess = 0;
  
  
  
  public void run(){
    while(running){
         /* Receive data from the ServerSocket */
            try{
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
         
                String s = in.readUTF();
                String[] st = s.split(" ");
                System.out.println(st[1]);
          
                	field.append(s+"\n");
          
            }catch(Exception e){
            }
    }
  }
   
}

class ClientGUI{
      JPanel mainpanel = new JPanel();
      JFrame mainframe = new JFrame();
      int counter = 0;
      String ign;
   
      
        public ClientGUI(String ip, String name){
          mainpanel.setPreferredSize(new Dimension(300,768));
          mainpanel.setBackground(Color.GRAY);

          FinalXWars_Chat.field.setPreferredSize(new Dimension(300,500));
          FinalXWars_Chat.inputbox.setSize(new Dimension(300,30));
          mainpanel.add(FinalXWars_Chat.field);
          mainpanel.add(FinalXWars_Chat.inputbox);
          mainframe.setContentPane(mainpanel);
          this.ign = name;
        try{
        	
        	
        	String serverName = ip;
        	String port = "10001";
        
         System.out.println("Connecting to " + serverName + " on port " + port);
        
	  FinalXWars_Chat.client = new Socket(serverName, Integer.parseInt(port));
	   OutputStream outToServer;
		try {
		outToServer = FinalXWars_Chat.client.getOutputStream();
		DataOutputStream out = new DataOutputStream(outToServer);
     	out.writeUTF(this.ign);
     
     
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		
		e1.printStackTrace();
	}
  
         System.out.println("Just connected to " + FinalXWars_Chat.client.getRemoteSocketAddress());
         /* Send data to the ServerSocket */
         FinalXWars_Chat.field.append("========================================\nWELCOME TO FINAL X WARS v1.0\nYou are logged in as "+this.ign+"\n");
        
         
         Thread t = new FinalXWars_Chat();
        t.start();
        
        Action submit = new AbstractAction()
        {
          /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override 
          public void actionPerformed(ActionEvent e){
			
            FinalXWars_Chat.field.append("Me: "+FinalXWars_Chat.inputbox.getText()+"\n");
	         	
            OutputStream outToServer;
			try {
				outToServer = FinalXWars_Chat.client.getOutputStream();
				DataOutputStream out = new DataOutputStream(outToServer);
	         	out.writeUTF(FinalXWars_Chat.inputbox.getText());
	         
	         
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
         
          	FinalXWars_Chat.inputbox.setText("");
          }

        };
        FinalXWars_Chat.inputbox.addActionListener(submit);
        
        
         //insert missing line for closing the socket from the client side
	//FinalXWars_Chat.client.close(); 
	//FinalXWars_Chat.running = false;

      }catch(IOException e)
      {
        
      	JOptionPane.showMessageDialog(null,"ERROR: Cannot Connect to Server. \nPlease Restart the Game.\n");
      	System.exit(0);
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
            }
        }
        
        public void SendMessage() {
     	   
			if(counter == 0){
        		 FinalXWars_Chat.field.append("\n\nWelcome "+ "RANDOM STRING" +"!\n");
        		 counter++;
        	}else{
        		FinalXWars_Chat.field.append("Me: "+"RANDOM STRING\n");
        	}
       OutputStream outToServer;
		try {
			outToServer = FinalXWars_Chat.client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
        	out.writeUTF(FinalXWars_Chat.inputbox.getText());
        	System.out.println("Sending"+"RANDOM STRING"+"to"+FinalXWars_Chat.client.getPort());
        	FinalXWars_Chat.numsuccess++;
        
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			FinalXWars_Chat.numfails++;
			e1.printStackTrace();
		}
    
     	FinalXWars_Chat.inputbox.setText("");
       }
   }
   
