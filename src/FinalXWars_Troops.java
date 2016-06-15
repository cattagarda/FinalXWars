import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FinalXWars_Troops extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double xcoordinate = 0;
	double ycoordinate = 0;
	int width = 20;
	int height = 20;
	int speed = 100;
	int quantity = 0;
	int status = 0;
	int hp = 1000;
	int maxhp = 1000;
	int damage = 0;
	double range = 10.0;
	String action = "NORMAL";
	String filename = "";
	String name = "";
	FinalXWars_Building a;
	int owner = 0;
	FinalXWars_Building target;
	ImageIcon imageIcon;
	static ArrayList<FinalXWars_Troops> charlist = new ArrayList<FinalXWars_Troops>();
	
	public FinalXWars_Troops(int xcoordinate, int ycoordinate, String name, int owner) {
		this.xcoordinate = xcoordinate;
		this.ycoordinate = ycoordinate;
		this.name = name;
		this.owner = owner;
		this.action = "ATK";
		
		if(this.name.equals("Crystal Tower")){
			this.setBuilding();
		}else{
			this.setTroop();
		}
		
		//System.out.println("Generated "+this.name+"@ image= "+this.imageIcon);
	}
	
	private void setTroop(){
		if(this.name.equals("White Mage")){
			this.hp = 250;
			this.maxhp = this.hp;
			this.damage = 2;
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/WhiteMage-Walk.gif"));
			this.width = 30;
			this.height = 40;
			this.speed = 100;
		} else if(this.name.equals("Black Mage")){
			this.hp = 100;
			this.maxhp = this.hp;
			this.damage = 10;
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/BlackMage-Walk.gif"));
			this.width = 30;
			this.height = 40;
			this.speed = 10;
		} else if(this.name.equals("Knight")){
			this.hp = 200;
			this.maxhp = this.hp;
			this.damage = 5;
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Knight-Walk.gif"));
			this.width = 30;
			this.height = 40;
			this.speed = 10;
		} else if(this.name.equals("Ninja")){
			this.hp = 150;
			this.maxhp = this.hp;
			this.damage = 5;
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Ninja-Walk.gif"));
			this.width = 30;
			this.height = 40;
			this.speed = 200;
		} else if(this.name.equals("Thief")){
			this.hp = 150;
			this.maxhp = this.hp;
			this.damage = 2;
			imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Thief-Walk.gif"));
			this.width = 30;
			this.height = 40;
			this.speed = 200;
		}
		
	}
	
	public void setAttack(){
		if(this.name.equals("White Mage")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/WhiteMage-Attack.gif"));
			this.width = 60;
			this.height = 40;
		} else if(this.name.equals("Black Mage")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/BlackMage-Attack.gif"));
			this.width = 60;
			this.height = 40;
		} else if(this.name.equals("Knight")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Knight-Attack.gif"));
			this.width = 60;
			this.height = 40;
		} else if(this.name.equals("Ninja")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Ninja-Attack.gif"));
			this.width = 60;
			this.height = 40;
		} else if(this.name.equals("Thief")){
			imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Thief-Attack.gif"));
			this.width = 30;
			this.height = 40;
		}
	}
	
	public void setNormal(){
		if(this.name.equals("White Mage")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/WhiteMage-Walk.gif"));
			this.width = 30;
			this.height = 40;
		} else if(this.name.equals("Black Mage")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/BlackMage-Walk.gif"));
			this.width = 30;
			this.height = 40;
		} else if(this.name.equals("Knight")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Knight-Walk.gif"));
			this.width = 30;
			this.height = 40;
		} else if(this.name.equals("Ninja")){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Ninja-Walk.gif"));
			this.width = 30;
			this.height = 40;
		} else if(this.name.equals("Thief")){
			imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/Thief-Walk.gif"));
			this.width = 30;
			this.height = 40;
		}
	}
	
	private void setBuilding(){
		if(this.owner == 1){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/crystal1.gif"));
			this.width = 50;
			this.height = 60;
			this.maxhp = this.hp;
		} else if(this.owner == 2){
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/crystal2.gif"));
			this.width = 50;
			this.height = 60;
			this.maxhp = this.hp;
		} else {
			this.imageIcon = new ImageIcon(FinalXWars_GameBase.class.getResource("Images/crystal3.gif"));
			this.width = 50;
			this.height = 60;
			this.maxhp = this.hp;
		}
	}
	
	@Override
	public void run() {
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	    //Convert to Java2D Object
	    Graphics2D g2 = (Graphics2D) g;
	    this.revalidate();
	    this.repaint();
	    
	}
}
