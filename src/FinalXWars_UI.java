import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FinalXWars_UI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 6380403706225051467L;
	static FinalXWars_GameBase mainGame;
	FinalXWars_Panel cover;
	JPanel frontPage, ipPanel;
	JPanel gameContainer;
	JPanel gamePane;
	JPanel chat;
	JPanel characterPanel, status1, buttons, title;
	JLabel ttl;
	static JButton[] characterButton = new JButton[5];
	static JLabel labl;
	JButton login;
	JTextField ipAddress;
	static GameCommunicator gc = null;
	String ip = null;
	
	public FinalXWars_UI(){
		this.setPreferredSize(new Dimension(1400,700));
		this.setLayout(new CardLayout());
		
		try {
				ip = JOptionPane.showInputDialog("Please input IP Address of Game Core / Server");
				//String port = JOptionPane.showInputDialog("Please input Port no of Game Core");
				gc = new GameCommunicator(InetAddress.getByName(ip), Integer.parseInt("10224"));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		gc.sendMessage("CONN#CLIENT");
		
		Thread l = new Thread(gc);
		l.start();
		
		frontPage = new JPanel();
		frontPage.setPreferredSize(new Dimension(1400,700));
		frontPage.setLayout(new BorderLayout());
		frontPage.setBackground(Color.WHITE);
		
		cover = new FinalXWars_Panel("title.png");
		cover.setPreferredSize(new Dimension(1400,200));
		cover.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		cover.setOpaque(false);
		
		ipPanel = new JPanel();
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.Y_AXIS));
		ipPanel.setBackground(Color.WHITE);
		ipPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		JPanel ipContain = new JPanel();
		JLabel ipLabel = new JLabel("Enter Your Name:      ");
		ipAddress = new JTextField("");
		ipAddress.setToolTipText("IGN");
		ipAddress.setPreferredSize(new Dimension(300,50));
		
		ipAddress.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		ipContain.add(ipLabel);
		ipContain.add(ipAddress);
		ipContain.setBackground(Color.WHITE);
		
		login = new JButton("Login");
		login.addActionListener(this);
		login.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ipPanel.add(cover);
		ipPanel.add(ipContain);
		ipPanel.add(login);
		
		JPanel chu = new JPanel();
		chu.setPreferredSize(new Dimension(1024, 100));
		chu.setBackground(new Color(255,255,255,0));
		JPanel chu2 = new JPanel();
		chu2.setPreferredSize(new Dimension(1024, 100));
		chu2.setBackground(new Color(255,255,255,0));
		
		frontPage.add(chu, BorderLayout.NORTH);
		frontPage.add(ipPanel);
		frontPage.add(chu2, BorderLayout.SOUTH);
		
		status1 = new JPanel();
		status1.setOpaque(true);
		status1.setBackground(new Color(200,200,200,200));
		status1.setPreferredSize(new Dimension(300,500));
		
		labl = new JLabel();
		labl.setText("Number of troops deployed: "+FinalXWars_GameBase.numdeployed);
		status1.add(labl);
		
		title = new JPanel();
		ttl = new JLabel("Final X Wars");
		title.add(ttl);
		
		gameContainer = new JPanel();
		gameContainer.setPreferredSize(new Dimension(1400,700));
		
		gamePane = new JPanel();
		gamePane.setLayout(new BorderLayout());
		mainGame = new FinalXWars_GameBase("grass.png");
		mainGame.setPreferredSize(new Dimension(1200,500));
		
		chat = new JPanel();
		chat.setPreferredSize(new Dimension(300,700));
		chat.setBackground(Color.BLUE);

		characterPanel = new JPanel();
		characterPanel.setLayout(new GridLayout(1,3));
		characterPanel.setPreferredSize(new Dimension(1000,100));
		characterPanel.setBackground(Color.WHITE);
		
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,5));
		buttons.setPreferredSize(new Dimension(500,100));
		
		for(int i=0; i<5; i++){
			characterButton[i] = new JButton();
			characterButton[i].setBackground(Color.WHITE);
		}
		
		try{
			characterButton[0].setIcon(new ImageIcon(FinalXWars_UI.class.getResource("Images/WhiteMage-Cover.gif")));
			characterButton[1].setIcon(new ImageIcon(FinalXWars_UI.class.getResource("Images/BlackMage-Cover.gif")));
			characterButton[2].setIcon(new ImageIcon(FinalXWars_UI.class.getResource("Images/Knight-Cover.gif")));
			characterButton[3].setIcon(new ImageIcon(FinalXWars_UI.class.getResource("Images/Ninja-Cover.gif")));
			characterButton[4].setIcon(new ImageIcon(FinalXWars_UI.class.getResource("Images/Thief-Cover.gif")));
		} catch (Exception x){}
		
		for(int i=0; i<5; i++){
			buttons.add(characterButton[i]);
			characterButton[i].addActionListener(this);
		}
		
		characterPanel.add(status1);
		characterPanel.add(title);
		characterPanel.add(buttons);
		
		gamePane.add(mainGame, BorderLayout.CENTER);
		gamePane.add(characterPanel, BorderLayout.SOUTH);
		
		gameContainer.setLayout(new BorderLayout());
		gameContainer.add(gamePane, BorderLayout.CENTER);
		gameContainer.add(chat, BorderLayout.EAST);
		
		this.add(frontPage, "Front");
		this.add(gameContainer, "Game");
		CardLayout cardLayout = (CardLayout) this.getLayout();
		cardLayout.show(this, "Front");
		
	}
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Final X Wars 1.0");
		FinalXWars_UI mainPanel = new FinalXWars_UI();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		for(int i=0; i<5; i++){
			if(arg0.getSource() == characterButton[i]){
				FinalXWars_GameBase.currentTroop = i+1;
			}
		}
		
		if(arg0.getSource() == login){
			CardLayout cardLayout = (CardLayout) this.getLayout();
			cardLayout.show(this, "Game");
			
			ClientGUI a = new ClientGUI(ip,ipAddress.getText());
			chat.add(a.mainpanel); 
		}
	}
		
}