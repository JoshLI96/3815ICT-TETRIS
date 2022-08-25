import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class LoginPanel extends JFrame{
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	public Integer xRow = 15;//12
	public Integer yRow = 25;//19
	public int levelNumber = 1;
	public boolean normalModel = true;
	public boolean playerModel = true;
	String username;
	LoginPanel log = this;
	int w=Toolkit.getDefaultToolkit().getScreenSize().width;
    int h=Toolkit.getDefaultToolkit().getScreenSize().height;
	public LoginPanel( ){
		this.setTitle("Tetirs");
		this.setBounds((w-500)/2,(h-600)/2,460,350);
		this.setVisible(true);
		Container cp=getContentPane();
		 Label l2=new Label("2022-3815ICT");
		 Label l3=new Label("Group: Jiashu Li | Xiaonan Zhou");
		 JPanel p1=new JPanel();
		 JPanel p2=new JPanel();
		 JPanel p3=new JPanel();
		 JPanel p4=new JPanel();

	     b1=new JButton("Start");
	     b2=new JButton("Top Score");
	     b3=new JButton("Configure");
	     b4=new JButton("Exit");

	     p1.add(l2);
	     p1.add(l3);
	     p2.setBorder(new MatteBorder(0,0,0,0,Color.BLACK));
	     p3.add(b1);
	     p3.add(b2);
	     p3.add(b3);
	     p3.add(b4);
	     p3.setBorder(new MatteBorder(-3,-3,-3,-3,Color.CYAN));
	     p4.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	     p4.add(p1);
	     p4.add(p2);
	     p4.add(p3);
	     cp.add(p4,BorderLayout.CENTER);

	     b1.addActionListener(new Start());
	     b2.addActionListener(new TopSource());
	     b3.addActionListener(new Configure());
	     b4.addActionListener(new winClose());

	}
	
	public LoginPanel(GamePanel gamebody){
		this.setTitle("Tetirs");
		this.setBounds((w-500)/2,(h-600)/2,460,350);
		this.setVisible(true);
		Container cp=getContentPane();
		 Label l2=new Label("2022-3815ICT");
		 Label l3=new Label("Group: Jiashu Li | Xiaonan Zhou");
		 JPanel p1=new JPanel();
		 JPanel p2=new JPanel();
		 JPanel p3=new JPanel();
		 JPanel p4=new JPanel();

	     b1=new JButton("Play");
	     b2=new JButton("Top Score");
	     b3=new JButton("Configure");
	     b4=new JButton("Exit");

	     p1.add(l2);
	     p1.add(l3);
	     p2.setBorder(new MatteBorder(0,0,0,0,Color.BLACK));
	     p3.add(b1);
	     p3.add(b2);
	     p3.add(b3);
	     p3.add(b4);
	     p3.setBorder(new MatteBorder(-3,-3,-3,-3,Color.CYAN));
	     p4.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	     p4.add(p1);
	     p4.add(p2);
	     p4.add(p3);
	     cp.add(p4,BorderLayout.CENTER);

	     b1.addActionListener(new Start());
	     b2.addActionListener(new TopSource());
	     b3.addActionListener(new Configure());
	     b4.addActionListener(new winClose());

	}
	
	 public static void main(String[] args) {
		 int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		 int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		 LoginPanel log = new LoginPanel();
		 log.setTitle("Tetirs");
		 log.setBounds((w-500)/2,(h-600)/2,460,350);
		 log.setVisible(true);
	 }
	 
	 public LoginPanel(ConfigurePanel confrigurePanel) {
		 xRow = Integer.valueOf(confrigurePanel.xRow);
		 yRow = Integer.valueOf(confrigurePanel.yRow);
		 levelNumber = confrigurePanel.chooseLevel + 1;
		 normalModel = confrigurePanel.normalMode;
		 playerModel = confrigurePanel.playerMode;
		 this.setTitle("Tetirs");
		 this.setBounds((w-500)/2,(h-600)/2,460,350);
		 this.setVisible(true);
		 Container cp=getContentPane();
		 Label l2=new Label("2022-3815ICT");
		 Label l3=new Label("Group: Jiashu Li | Xiaonan Zhou");
		 JPanel p1=new JPanel();
		 JPanel p2=new JPanel();
		 JPanel p3=new JPanel();
		 JPanel p4=new JPanel();


	     b1=new JButton("Start");
	     b2=new JButton("Top Score");
	     b3=new JButton("Configure");
	     b4=new JButton("Exit");

	     p1.add(l2);
	     p1.add(l3);
	     p3.add(b1);
	     p3.add(b2);
	     p3.add(b3);
	     p3.add(b4);
	     p3.setBorder(new MatteBorder(-3,-3,-3,-3,Color.CYAN));
	     p4.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	     p4.add(p1);
	     p4.add(p2);
	     p4.add(p3);
	     cp.add(p4,BorderLayout.CENTER);

	     b1.addActionListener(new Start());
	     b2.addActionListener(new TopSource());
	     b3.addActionListener(new Configure());
	     b4.addActionListener(new winClose());
	 }
	 
	 class Start implements ActionListener{
		  public void actionPerformed(ActionEvent e)
		  {  

            	  GamePanel gamepanel = new GamePanel(log);
            	  dispose();
		  }
		 }
	 
		 class TopSource implements ActionListener{
			 public void actionPerformed(ActionEvent e)
			 {
				TopScorePanel topscorepanel = new TopScorePanel(); 				    
				dispose();
			 }
		 }
		 
		 class Configure implements ActionListener{
			 public void actionPerformed(ActionEvent e)
			 {
				 dispose();
				 ConfigurePanel configurepanel = new ConfigurePanel(); 	
			 }
		 }
		 
		 class winClose implements ActionListener
		 {
			 public void actionPerformed(ActionEvent e)
			 {

				 System.exit(0);
			 }
		 }
}