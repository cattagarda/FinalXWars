import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class FinalXWars_GameBase extends FinalXWars_Panel implements ActionListener, Runnable, MouseListener{
	
	private static final long serialVersionUID = 1L;
	CopyOnWriteArrayList<FinalXWars_Troops> characters = new CopyOnWriteArrayList<FinalXWars_Troops>();
	
	static int currentTroop = -1;
	static int team = 0;
	static int numdeployed = 0;
	
	public FinalXWars_GameBase(String filename) {
		super(filename);

		this.addMouseListener(this);
		
		Thread t = new Thread(this);
		t.start();
		
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	    for(FinalXWars_Troops b: characters){
	    	//System.out.println(b.name+"= "+b.imageIcon);
	    	g.setColor(Color.BLACK);
	    	if(b.name.equals("Crystal Tower")){
	    		g.fillRect((int)(b.xcoordinate-10), (int)(b.ycoordinate-15), 20, 5);
	    	}
    		if(b.owner == 1){
    			g.setColor(Color.BLUE);
    		}else if(b.owner == 2){
    			g.setColor(Color.RED);
    		}
    		else if(b.owner == 3){
    			g.setColor(Color.YELLOW);
    		}
    		if(b.name.equals("Crystal Tower")){
    			g.fillRect((int)(b.xcoordinate-10), (int)(b.ycoordinate-15), (int)(((float)b.hp/(float)b.maxhp)), 5);
    		}else{
    			g.fillOval((int)(b.xcoordinate-10), (int)(b.ycoordinate-15), 10, 10);
    		}
	    	g.drawImage(b.imageIcon.getImage(), (int)b.xcoordinate, (int)b.ycoordinate, b.width, b.height, null);	
	    }
	   
	    
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(currentTroop != -1 && (arg0.getX() <= 1100) && (arg0.getY() <= 570)){
			
			//generateTroop(arg0.getX(), arg0.getY(), getUnitName(currentTroop), team);
			
		if(numdeployed < 100){
			FinalXWars_Troops temp = new FinalXWars_Troops(0,0,getUnitName(currentTroop),team);
			String msg = "TROOP#"+team+"#"+getUnitName(currentTroop)+"#"+arg0.getX()+"#"+arg0.getY()+"#"+team+"#"+getUnitName(currentTroop)+"#"+temp.speed+"#"+temp.hp+"#"+temp.damage;
			FinalXWars_UI.gc.sendMessage(msg.getBytes());
			numdeployed++;
			FinalXWars_UI.labl.setText("Number of troops deployed: "+numdeployed);
		}else{
			for(JButton k:FinalXWars_UI.characterButton){
				k.setEnabled(false); 
			}
		}
		}
	
		
	}
	
	public int generateTroop(int x, int y,String unit, int team){
		FinalXWars_Troops troop = new FinalXWars_Troops(x,y,unit,team);
		System.out.println(x+" "+y);
	
		characters.add(troop);
		Thread t = new Thread(troop);
		t.start();
		
		return 1;
	}

	public String getUnitName(int type){
		String name = "";
		switch(type){
			case 1: name = "White Mage";
					break;
			case 2: name = "Black Mage";
					break;
			case 3: name = "Knight";
					break;
			case 4: name = "Ninja";
					break;
			case 5: name = "Thief";
					break;
		}
		
		
		
		return name;
	}
	
	public int getSpeed(int type){
		int speed = 0;
		switch(type){
			case 1: speed = 10;
					break;
			case 2: speed = 100;
					break;
			case 3: speed = 10;
					break;
			case 4: speed = 10;
					break;
			case 5: speed = 100;
					break;
		}
		
		return speed;
	}
	
	public int getHP(int type){
		int speed = 0;
		switch(type){
			case 1: speed = 250;
					break;
			case 2: speed = 100;
					break;
			case 3: speed = 200;
					break;
			case 4: speed = 150;
					break;
			case 5: speed = 150;
					break;
		}
		
		return speed;
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void run() {
		int[] towerstats  = {0,0,0};
		int numtowerout = 0;
		while(true){
		
			
		    for(FinalXWars_Troops b: characters){
		    	if(b.hp == 0){
		    		characters.remove(b);
		    		if(b.name.equals("Crystal Tower")){
		    			System.out.println("A Crystal Tower has diedz");
		    			towerstats[b.owner-1] = 1;
		    			numtowerout++;
		    		}
		    	}
		    }
		    
		    if(numtowerout == 2){
		    	for(int i=0; i<towerstats.length; i++){
		    		if(towerstats[i] == 0){
		    			JOptionPane.showMessageDialog(null,"Player "+(i+1)+" Wins!");
		    			System.exit(0);
		    		}
		    	}
		    }
			this.revalidate();
			this.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
