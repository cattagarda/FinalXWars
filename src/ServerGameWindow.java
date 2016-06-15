import javax.swing.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ServerGameWindow{
	static Unit aa;
	static Unit bb;
	static Unit cc;
	JFrame frame = new JFrame("FrameDemo");
	Field panel;
	public ServerGameWindow(){
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());
		
		panel = new Field();
		Unit.units = 0;
		 aa=new Unit(50,150,100000, 1, "Crystal Tower",0,0);
		 bb=new Unit(950,150,100000, 2, "Crystal Tower",0,0);
		 cc=new Unit(500,500,100000, 3, "Crystal Tower",0,0);
		JButton button1 = new JButton("toggle");
		button1.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
             frame.dispose();
             
         	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.getContentPane().setLayout(new FlowLayout());
    		 for(int i=0; i<750; i++){
    			 try{
    	        	panel.battlefield[i].interrupt = true;
    			 }catch(Exception d){
    				 
    			 }
    	      }
    		
    		panel = new Field();
    		Unit.units = 0;
   		 	aa=new Unit(50,150,10000, 1, "Crystal Tower",0,0);
   		 	bb=new Unit(950,150,10000, 2, "Crystal Tower",0,0);
   		 	cc=new Unit(500,500,10000, 3, "Crystal Tower",0,0);
   		 
    		JButton button1 = new JButton("toggle");
    		
    		
    		
    		button1.setPreferredSize(new Dimension(100,100));
    		frame.getContentPane().add(panel);
    		frame.getContentPane().add(button1);
    		frame.setPreferredSize(new Dimension(1000,800));
    		frame.pack();
    		frame.setVisible(true);
    		
    		GreetingServer.gs.id = 1;
    		
            }
        });
		button1.setPreferredSize(new Dimension(100,100));
		frame.getContentPane().add(panel);
		frame.getContentPane().add(button1);
		frame.setPreferredSize(new Dimension(1000,800));
		frame.pack();
		frame.setVisible(true);
		
		aa.damage=10;
		bb.damage=10;
		cc.damage=10;
		
		aa.action = "ATK";
		bb.action = "ATK";
		cc.action = "ATK";
		
		aa.start();
		bb.start();
		cc.start();
	
	}
}
