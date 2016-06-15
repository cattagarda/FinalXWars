import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.*;


/*
 * Game Server Class
 * Contains the Networking Functions of the Game Core.
 * Functionalities inside:
 * - Waits for a connection from client and identifies it whether it is man in the moddle or a player
 * 	If player, assign an ID and put its details on the server playerlist
 *  If Man in the middle, transfer the connections to the man in the middle "server"
 * 
 */
public class GameServer implements Runnable{
	CopyOnWriteArrayList<GameCommunicator> clientlist = new CopyOnWriteArrayList<GameCommunicator>();

	DatagramSocket datagramSocket;
	protected int id = 1;
	ServerGameWindow ge = new ServerGameWindow();
	
    public GameServer() throws IOException {
    	boolean MITMConn = false;
    	 GreetingServer.logs.SetString("INITIALIZING GAME SERVER...");
    	 datagramSocket = new DatagramSocket(10224); 
		    byte[] buffer = new byte[1000];
		    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    		GreetingServer.logs.SetString("SETTING UP GAME ENVIRONMENT...");
    		
    			
    		//This Thread Sends ALL Troops' deployed in the Server Game Panel to ALL Clients connected
		    Thread d = new Thread(this);
		    d.start();
		    GameCommunicator MITM = null;
		    	
		    GreetingServer.logs.SetString("GAME SERVER STARTED.. \n WAITING FOR USERS");
		    	 
		    	while(true){
		    		//Wait for connections...
		    		buffer = new byte[1000];
		  		    packet = new DatagramPacket(buffer, buffer.length);
		    		datagramSocket.receive(packet);	
		    		GreetingServer.logs.SetString(new String(packet.getData())+"\n");
		    		
		    		//Parse the data
		    		String data = new String(packet.getData());
		    		String [] split = data.split("#");
		    		
		    		//A Client wants to connect
		    		if(split[0].equals("CONN")){	
		    			GameCommunicator g = new GameCommunicator(packet.getAddress(), packet.getPort());
		    			
		    			String s = "ACK#"+id;
		    			id++;
		    			g.sendMessage(s.getBytes());
		    			
		    			if(MITMConn == true){
		    				String t = "RECONN#"+MITM.receiverAddress.getHostAddress()+"#10225";
		    				System.out.println(t);
		    				g.sendMessage(t.getBytes());
		    				MITM.sendMessage(("CLICON#"+g.receiverAddress.toString()+"#"+g.port+"").getBytes());
		    			}else{
		    				clientlist.add(g);
		    			}
		    			
		    
		    		}    	
		    		//A Client Deploys a Troop!
		    		else if(split[0].equals("TROOP")){
		    			double getx = Double.parseDouble(split[3]);
		    			double gety = Double.parseDouble(split[4]);
		    			
		    			
		    			int spd = Integer.parseInt(split[7].trim());
		    			int damage = Integer.parseInt(split[9].trim());
		    			int hp = Integer.parseInt(split[8].trim());
		    			
		    			Unit temp = new Unit(getx, gety, hp, Integer.parseInt(split[5]), split[2], spd,damage);
		    			temp.start();
		    		}
		    		//A Man in the moddle connects
		    		else if(split[0].equals("MMIDCONN")){
		    			
		    			MITM = new GameCommunicator(packet.getAddress(), Integer.parseInt("10226"));
		    			System.out.println("MAN IN THE MIDDLE CONNECTED");
		    			MITMConn = true;
		    			clientlist.add(MITM);
		    		}	
		    	}		    	
}  
   //This function scans all users connected to the server and sends the message
    //pAssed on this function
   public void broadcast(byte[] buffer) {
	   for(GameCommunicator i: clientlist){   	
		   i.sendMessage(buffer);   	
	   } 
   }
   
  //send the data of the player status over udp
@Override
public void run() {
	while(true){ //Once running this will run foreverr
		for(int i= 0; i<Unit.units; i++){ //Scan all units deployed on server
			if(Field.battlefield[i] != null){
			try{
			String s = "TROOPUPD#"+Field.battlefield[i].faction+"#"+Field.battlefield[i].id+"#"+Field.battlefield[i].x+"#"+Field.battlefield[i].y+"#"+Field.battlefield[i].hp+"#"+Field.battlefield[i].action+"#"+Field.battlefield[i].name; //This is the string that will be passed on all clients
			broadcast(s.getBytes());
			}catch(Exception e){
				
			}
		
			
			
			}
		}
		
	}
}   
}