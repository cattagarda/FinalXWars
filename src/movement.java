import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//This class contains the AI for the playable characters


class Field extends JPanel implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int D_W = 1200;
    private static final int D_H = 700;
	static Unit[] battlefield = new Unit[750];
	static int state=2;
	Timer timer;
	//Game Server JPanel 
	public Field() {
        setBackground(Color.WHITE);
		addMouseListener(this);
         timer = new Timer(50, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
        
        for(int i=0; i<750; i++){
        	battlefield[i] = null;
        }
    }
    
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] numtroopsgrouped = {0,0,0};
        int numfactions = 0;
        
        for(int i=0; i<750; i++){
        	if(!(battlefield[i]==null)){
        		numtroopsgrouped[battlefield[i].faction-1]++;
        		
        		if(battlefield[i].faction==1) g.setColor(Color.BLUE);
        		else if(battlefield[i].faction==2) g.setColor(Color.RED);
        		else g.setColor(Color.YELLOW);
        		g.fillOval((int)(battlefield[i].x-10), (int)(battlefield[i].y-10), 20, 20);
        		g.setColor(Color.BLACK);
        		g.fillRect((int)(battlefield[i].x-10), (int)(battlefield[i].y-15), 20, 5);
        		g.setColor(Color.GREEN);
        		g.fillRect((int)(battlefield[i].x-10), (int)(battlefield[i].y-15), (int)(20*((float)battlefield[i].hp/(float)battlefield[i].maxhp)), 5);
        	}
        	
        }
      //  System.out.println("====================");
        for(int i=0; i<3; i++){
        //	System.out.println(numtroopsgrouped[i]);
        	if(numtroopsgrouped[i] > 0)
        		numfactions++;
        }
        if(numfactions == 1){
        	
        	ServerGameWindow.aa = null;
        	ServerGameWindow.bb = null;
        	ServerGameWindow.cc = null;
        	for(int i=0; i>750; i++){
        		battlefield[i] = null;
        	}
        timer.stop();
        }
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(D_W, D_H);
    }
    
    public void actionPerformed(ActionEvent ae) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }
    
    @Override
    //Click na pang test
    public void mouseClicked(MouseEvent e) {
	    Unit temp = new Unit(e.getX(),e.getY(),100, state, "White Mage", 10,10);
	    temp.start();
	}
}


//UNIT = Player 
class Unit extends Thread{
	static int units=0, unitsred=0, unitsyellow=0;
	
	int id,faction;
	double x,y, range=50.0;
	String action;
	int maxhp,hp,damage=10,aoe=1,speed = 0;
	String name;
	
	Unit target;
	boolean interrupt = false;
	
	public Unit(double x, double y, int hp, int f, String name, int speed, int damage){
		this.name = name;
		this.x=x;
		this.y=y;
		this.action = "NORMAL";
		maxhp=hp;
		this.hp=hp;
		this.speed = speed;
		faction=f;
		id=units;
		this.damage = damage;
		units++;
		Field.battlefield[id]=this;
		//this.id=id;
	}
	
	public void attack(Unit target, int damage, double aoe){
			if(this.action.equals("ATTACK")){
				this.action = "ATKINV";
			}else{
				this.action = "ATK";
			}
			for(int i=0; i<750; i++){
				if(!(Field.battlefield[i]==null) && Field.battlefield[i].faction!=faction){
					if(Math.sqrt((target.x-Field.battlefield[i].x)*(target.x-Field.battlefield[i].x)+(target.y-Field.battlefield[i].y)*(target.y-Field.battlefield[i].y))<aoe){
			    			Field.battlefield[i].hp-=damage;
					}
				}
        		}
	}
	
	public Unit findtarget(){
		Unit temp=null;
		double min=99999999999.0;
		while(temp==null){
		for(int i=0; i<750; i++){
			if(!(Field.battlefield[i]==null) && (Field.battlefield[i].hp>0)&& Field.battlefield[i].faction!=faction){
				double temp2=Math.sqrt((Field.battlefield[i].x-x)*(Field.battlefield[i].x-x)+(Field.battlefield[i].y-y)*(Field.battlefield[i].y-y));
				if(temp2<min){
			    		min=temp2;
			    		temp=Field.battlefield[i];
				}
			}
        	}
        	}
        	return temp;
	}
	
	public void computepos(){
		
			try {
				double oldx = x;
				double xtemp=target.x-x;
				double ytemp=target.y-y;
				double div=Math.sqrt((xtemp*xtemp)+(ytemp*ytemp));
				xtemp/=div;
				ytemp/=div;
				x+=xtemp;
				y+=ytemp;
				
				if(oldx>x){
					this.action = "INVERSE";
				}else{
					this.action = "NORMAL";
				}
				if(hp<=0){
					Field.battlefield[id]=null;
					System.out.println("someone died");
				}
				
				while(target.hp<=0 || target==null){
					target=findtarget();
				}
				
				if(Math.sqrt((target.x-x)*(target.x-x)+(target.y-y)*(target.y-y))<=range){
					while(hp>0){
						if(interrupt == true){
							return;
						}
						if(Math.sqrt((target.x-x)*(target.x-x)+(target.y-y)*(target.y-y))>range || target.hp<=0){
							target=findtarget();
							break;
						}
						attack(target,damage,aoe);
						Thread.sleep(speed*10);
					}
				}
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
	}
	
	public void run(){
		if(!this.name.equals("Crystal Tower")){
		target=findtarget();
		while(hp>0){
		
			if(interrupt == true)
				return;
			computepos();
		}
		Field.battlefield[id]=null;
		}else{
			while(hp>0){
				if(interrupt == true)
					return;
			}
			Field.battlefield[id]=null;
		}
		
	}

}
