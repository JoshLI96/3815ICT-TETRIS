import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class TopScorePanel extends JFrame implements ActionListener{

	private JButton jb;
	public TopScorePanel()
	{
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height;

		this.setTitle("Top Score");
		this.setBounds((w-500)/2,(h-600)/2,300, 500);
		jb=new JButton("BACK");
		jb.addActionListener(this);
		this.setVisible(true);
		JPanel p1=new JPanel(new GridLayout(12,1));
		
		JLabel l=new JLabel("Top Score");
		JLabel l1=new JLabel("Name                                          Score");
		JLabel l2=new JLabel("Josh                                            5600 ");
		JLabel l3=new JLabel("Xiaonan                                      5400");
		JLabel l4=new JLabel("Josh                                            5300 ");
		JLabel l5=new JLabel("Josh                                            5200 ");
		JLabel l6=new JLabel("BEST                                           5100 ");
		JLabel l7=new JLabel("KASI                                            5000 ");
		JLabel l8=new JLabel("MIMI                                             4700 ");
		JLabel l9=new JLabel("Wade                                           4600 ");
		JLabel l0=new JLabel("John                                            3000 ");
		l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l);
		l1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l1);
		l2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l2);
		l3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l3);
		l4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l4);
		l5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l5);
		l6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l6);
		l7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l7);
		l8.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l8);
		l9.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l9);
		l0.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.add(l0);
		p1.add(jb);
		this.add(p1);
		

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource()==jb)

		{
			this.dispose();//点击按钮时frame1销毁,new一个frame2

			new LoginPanel();

		}

	}

	}