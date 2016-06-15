import java.net.*;
import java.util.HashMap;
import java.io.*;

/*
 * Game Communicator Class
 * Contains the UDP Communication Functions for Both Server and Client
 * 
 * 
 */
public class GameCommunicator implements Runnable{
	  private DatagramPacket packet;
	  private DatagramSocket datagramSocket;
	  protected InetAddress receiverAddress;
	  protected int port;
	  String datacollected;
	  HashMap<Integer,FinalXWars_Troops> objects = new HashMap<Integer,FinalXWars_Troops>();
	  
	  
	 //Each client in the server has a "Game Communicator that allows it to send packets to the server"
    public GameCommunicator(InetAddress address, int port) throws IOException{
        datagramSocket = new DatagramSocket();

        //get port and IP address of CLIENT
        this.port = port;
        this.receiverAddress = address;
        this.datacollected = "";
    
    }
    
    public void sendMessage(String message){
    	byte[] buffer = new byte[message.length()];
        packet = new DatagramPacket(
                buffer, buffer.length, receiverAddress, port);
        packet.setData(message.getBytes());
        try {
			datagramSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void sendMessage(byte[] message){
    	packet = new DatagramPacket(message, message.length, this.receiverAddress, this.port);
       // System.out.println("SENDING.."+new String(message));
        try {
			datagramSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //CLIENT FUNCTION: This will receive packets from SERVER 
	@Override
	public void run() {
	
		    while(true){
		    	//System.out.println("Thread running...");
		        try {
		        	byte[] buffer = new byte[1000];
				    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					datagramSocket.receive(packet);
					//System.out.println("RECEIVED FROM SERVER"+new String(packet.getData()));
					datacollected = new String(packet.getData());
					   buffer = packet.getData();
				       getMessageContent(new String(buffer));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     
		       
		    }  
	    
	}
	
	public void getMessageContent(String message){
		//System.out.println("CLIENT GOT "+message);
		
		String [] messages = message.split("#"); // Split the Message
		
		if(messages[0].equals("ACK")){  //This is the message that will be sent by the server upon sending the CONN message
			Double id2 = Double.parseDouble(messages[1]); 
			FinalXWars_GameBase.team  =  id2.intValue(); //I commented this function para gawa ka ng ID na attribute sa class
			System.out.println("ID IS NOW"+messages[1]); //print for verification
			
		}else if(messages[0].equals("TROOPUPD")){  //This deploys / updates position of a troop in the game panel
			/* TROOP ID 
			*	Yung function sa baba ay naglalaman ng UNIQUE ID ng troop sa SERVER. Eto na yung ID ng isang troop hanggang 
			*   sa maubusan sya ng HP
			* 	Si client ay magmamaintain din ng sarili nyang object list based sa nakukuha nya dito.
			*/
			int x = Integer.parseInt(messages[2]); 
			
			if(this.objects.containsKey(x)){ //Find the troop ID on the object list sa client and then pag nahanap...
				double xc = Double.parseDouble(messages[3]);
				double yc = Double.parseDouble(messages[4]);
				//Yung implementation dito depende sa implementation mo ng Player sa client.
				//Kung pano ka nagdedeploy ng troop sa client side

				//Update lang ng x and y coordinate and HP	
					FinalXWars_Troops d = this.objects.get(x);
					d.xcoordinate = (float) xc;
					d.ycoordinate = (float) yc;
					d.hp = Integer.parseInt(messages[5]);
					d.action = messages[6]; //Yung actions ay para sa pagpapapalit ng images ng client (walking, attack, etc.) Nasa baba yung possible actions
				
					 if(d.action.equals("ATK")){
						d.setAttack();
					}else if(d.action.equals("NORMAL")){
						d.setNormal();
					}
						
			}else {
				double xc = Double.parseDouble(messages[3]);
				double yc = Double.parseDouble(messages[4]);
				x = Integer.parseInt(messages[2]);
			
				try{
				
				if(FinalXWars_UI.mainGame != null){
					
						FinalXWars_Troops temp = new FinalXWars_Troops((int) xc ,(int) yc, messages[7].trim(), Integer.parseInt(messages[1]));
						FinalXWars_UI.mainGame.characters.add(temp);
						objects.put(x, temp);
						
				}
				
			}catch(Exception e){
				e.printStackTrace();
				//System.out.println("BROKEN PACKET");
			}
			}
			
		}else if(messages[0].equals("RECONN")){
			this.port = Integer.parseInt(messages[2].trim());
			try {
				this.receiverAddress = InetAddress.getByName(messages[1]);
				System.out.println(this.receiverAddress.getHostAddress()+messages[2]);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Man in the Middle reconnectsh");
		}	

	}
	
}