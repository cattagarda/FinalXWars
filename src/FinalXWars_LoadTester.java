import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JOptionPane;

public class FinalXWars_LoadTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameCommunicator gc = null;
		try {
			String ip = JOptionPane.showInputDialog("Please input IP Address of Game Core / Server");
			String port = JOptionPane.showInputDialog("Please input Port no of Game Core");
			gc = new GameCommunicator(InetAddress.getByName(ip), Integer.parseInt(port));
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Trying to connect..");
		gc.sendMessage("CONN#CLIENT");
		
		Thread l = new Thread(gc);
		l.start();
		
		//This will just deploy random troops
		
		Random x = new Random();
		Random y = new Random();
		Random gameId = new Random();
		Random faction = new Random();
		int numthreads = 250;
		String trooptype = "White Mage";
		
		for(int i=0; i< numthreads; i++){
			String msg = "TROOP#"+gameId.nextInt(9999)+"#"+trooptype+"#"+x.nextInt(1600)+"#"+y.nextInt(1600)+"#"+(faction.nextInt(2)+1)+"#"+trooptype+"#100#100#1";
			gc.sendMessage(msg);
		}
	}
	
	public static void getMessageContent(String message){
		

	}

}
