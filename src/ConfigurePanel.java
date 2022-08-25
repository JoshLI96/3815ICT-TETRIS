import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ConfigurePanel extends JDialog {
	ConfigurePanel conf = this;
	private final int controlCount = 2;
	private final String controlIndexKey = "INDEX";
	private boolean[] choose;
	public int chooseLevel;
	public String xRow;
	public String yRow;
	public int levelNumber;
	public boolean normalMode;
	public boolean playerMode;
	int w=Toolkit.getDefaultToolkit().getScreenSize().width;
	int h=Toolkit.getDefaultToolkit().getScreenSize().height;
	public ConfigurePanel() {
		
		// 设置窗口标题, 窗口尺寸
		setTitle("Configure");
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds((w-500)/2,(h-600)/2,240, 295);
		setVisible(true);
		
		// 增加监听器处理窗口关闭事件
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		
		
		//size of the game
		final Label l=new Label("Size of the game:");
		l.setBounds(10, 1, 500, 20);
		getContentPane().add(l);
		final JTextField xrow = new JTextField();
		xrow.setText("13");
		xrow.setHorizontalAlignment(SwingConstants.CENTER);
		xrow.setBounds(50, 22, 40, 23);
		getContentPane().add(xrow);
		final JTextField yrow = new JTextField();
		yrow.setText("23");
		yrow.setHorizontalAlignment(SwingConstants.CENTER);
		yrow.setBounds(160, 22, 40, 23);
		getContentPane().add(yrow);
		final Label l1=new Label("X  row:");
		final Label l2=new Label("Y  row:");
	    l1.setBounds(10, 22, 40, 23);
	    l2.setBounds(120, 22, 40, 23);
	    getContentPane().add(l1);
	    getContentPane().add(l2);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(150, controlCount * 23));
		
		final JSlider slider = new JSlider();


		// 级别集选择框
		// 提示标签的文本
		final String[] labelText = {"Normal or Extended", "Player or AI"};
		// 提示标签
		final JLabel[] labels = new JLabel[controlCount];
		// 选择框
		ButtonGroup[] buttonGroup = new ButtonGroup[controlCount];
		// 肯定方的选择框
		final JRadioButton[] rdoTrue = new JRadioButton[controlCount];
		// 否定方的选择框
		final JRadioButton[] rdoFalse = new JRadioButton[controlCount];
		// 肯定方的处理器
		final ChooseHandler handlerFalse = new ChooseHandler(false);
		final ChooseHandler handlerTrue = new ChooseHandler(true);
		choose = new boolean[controlCount];
		choose[0] = true;
		choose[1] = true;
		for(int i = 0; i < controlCount; i++) {
					// 设置提示标签
					labels[i] = new JLabel();
					labels[i].setText(labelText[i]);
					labels[i].setBounds(10, 50 + i * 36, 210, 15);
					getContentPane().add(labels[i]);
					
					// 设置选择框
					rdoTrue[i] = new JRadioButton();
					rdoTrue[i].setText(i < 1 ? "Normal" : "Player");
					rdoTrue[i].setBounds(30, 65 + i * 36, 85, 19);
					rdoTrue[i].putClientProperty(controlIndexKey, new Integer(i));
					rdoTrue[i].addActionListener(handlerTrue);
					getContentPane().add(rdoTrue[i]);

					rdoFalse[i] = new JRadioButton();
					rdoFalse[i].setText(i < 1 ? "Extend" : "AI");
					rdoFalse[i].setBounds(126, 65 + i * 36, 85, 19);
					rdoFalse[i].putClientProperty(controlIndexKey, new Integer(i));
					rdoFalse[i].addActionListener(handlerFalse);
					getContentPane().add(rdoFalse[i]);

					// 同一序号的肯定方选择框与否定方选择框为一组
					buttonGroup[i] = new ButtonGroup();
					buttonGroup[i].add(rdoTrue[i]);
					buttonGroup[i].add(rdoFalse[i]);
					// 初始选择状态
					if(choose[i])
						rdoTrue[i].setSelected(true);
					else
						rdoFalse[i].setSelected(true);
				}
				

		// 带滚动条的面板(在级别集很多的情况下有用)
		final JScrollPane scrollPane = new JScrollPane(panel);
		getContentPane().add(panel);

		final JLabel label = new JLabel();
		label.setText("Level Select:");
		label.setBounds(10, 129, 150, 15);
		getContentPane().add(label);

		// 级别选择时的文本提示
		final JTextField textField = new JTextField();
		textField.setText("" + 1);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(30, 152, 30, 23);
		textField.setFocusable(false);
		getContentPane().add(textField);
		
		
		// 级别选择滚动条
		slider.setBounds(66, 156, 158, 35);
		slider.setMaximum(5 - 1);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				textField.setText("" + (value + 1));
				chooseLevel = value;
			}
		});
		getContentPane().add(slider);
	     

	     
		// "立即应用"按钮
		final JButton btnApply = new JButton();
		btnApply.setText("Enter");
		btnApply.setBounds(5, 205, 100, 30);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xRow = xrow.getText();
				yRow = yrow.getText();
				levelNumber = chooseLevel;
				if(choose[0] == true) {
					normalMode = true;
				}else {
					normalMode = false;
				}
				
				if(choose[1] == true) {
					playerMode = true;
				}else {
					playerMode = false;
				}

				if(Integer.valueOf(xRow)<12) {
					JOptionPane.showMessageDialog(null, "Please enter valid values for x row (greater than 12)");
				}else if(Integer.valueOf(yRow)<19) {
					JOptionPane.showMessageDialog(null, "Please enter valid values for y row (greater than 19)");
				}else {
					dispose();
					LoginPanel login = new LoginPanel(conf);
				}
			}
		});
		getContentPane().add(btnApply);

		// "取消"按钮
		final JButton btnCancel = new JButton();
		btnCancel.setText("Cancel");
		btnCancel.setBounds(115, 205, 100, 30);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginPanel log = new LoginPanel();
			}
		});
		getContentPane().add(btnCancel);
		
	}
	
	class ChooseHandler implements ActionListener {
		/**
		 * 该处理器是支持肯定方还是否定方的标志
		 */
		private boolean choice;
		
		public ChooseHandler(boolean choice) {
			this.choice = choice;
		}
		
		/**
		 * 用户选择时的处理
		 */
		public void actionPerformed(ActionEvent e) {
			// 根据选择框的序号, 更新对应的记录用户选择的变量值
			JComponent source = (JComponent)e.getSource();
			int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
			choose[index] = choice;
		}
	}
}
