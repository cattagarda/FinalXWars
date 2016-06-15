import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LogViewer {
	 protected  JTextArea  field = new JTextArea(); //Declare it here for Data Passing
	 
	public LogViewer() {
		JFrame mainframe = new JFrame("Log Viewer");
		JPanel mainpanel = new JPanel();
		
		
		mainpanel.setPreferredSize(new Dimension(600,800));
		 field.setPreferredSize(new Dimension(550,750));
		mainpanel.add(field);
		mainframe.setContentPane(mainpanel);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainframe.pack();
		mainframe.setVisible(true);
		
	}
	
	
	public void SetString(String s){
		field.append(s);
	} 
}
