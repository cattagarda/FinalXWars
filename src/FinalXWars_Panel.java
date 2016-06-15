import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FinalXWars_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	public String filename = "";
	
	public FinalXWars_Panel(String filename){
		this.filename = filename;
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Image img = null;
		
	    	try {
				img = ImageIO.read(new File("Images/"+this.filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    g.drawImage(img, 0, 0, null);
	    
		revalidate();
		repaint();
	    
	}
}
