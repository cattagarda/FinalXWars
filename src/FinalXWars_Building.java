import javax.swing.ImageIcon;


public class FinalXWars_Building extends FinalXWars_Troops implements Runnable{
	private static final long serialVersionUID = 1L;
	int xcoordinate = 0;
	int ycoordinate = 0;
	int quantity = 0;
	int width = 50;
	int height = 50;
	int team = 1;
	String name = "";
	ImageIcon imageIcon;
	
	public FinalXWars_Building(int xcoordinate, int ycoordinate, String name, int owner) {
		super(xcoordinate, ycoordinate, name, owner);
		// TODO Auto-generated constructor stub
		this.xcoordinate = xcoordinate;
		this.ycoordinate = ycoordinate;
		this.name = name;
		this.team = owner;
		
		//this.setBuilding();
		this.hp = 15000;
	}
	
	@Override
	public void run() {
		
	}
	
	
	

}
