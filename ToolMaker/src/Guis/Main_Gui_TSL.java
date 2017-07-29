package Guis;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Main_Gui_TSL implements ActionListener{

	private JFrame frmToolmaker;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	// Image
	JLabel lblImageshow = new JLabel();
	ImageIcon Icon;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					// Look and Feel of Windows
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					    	
					    	//System.out.println(info.getName());
					        if ("Windows".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }
					} catch (Exception e) {
					    // If Nimbus is not available, you can set the GUI to another look and feel.
						 //UIManager.setLookAndFeel(LookAndFeelInfo.);
					}
					
					
					Main_Gui_TSL window = new Main_Gui_TSL();
					window.frmToolmaker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_Gui_TSL() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmToolmaker = new JFrame();
		frmToolmaker.setIconImage(Toolkit.getDefaultToolkit().getImage(Main_Gui_TSL.class.getResource("/Figures/machine_image1.jpg")));
		frmToolmaker.setResizable(false);
		frmToolmaker.setTitle("ToolMaker");
		frmToolmaker.setBounds(100, 100, 917, 612);
		frmToolmaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToolmaker.getContentPane().setLayout(null);
		
		//Image
		
		//Icon = new ImageIcon("src/Figures/mainmenu/Endmill_Image.jpg");
		setImage("Endmill_Image");
		lblImageshow.setBounds(41, 105, 343, 248);
		frmToolmaker.getContentPane().add(lblImageshow);
		
		Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Tool Selection", TitledBorder.LEADING, TitledBorder.TOP, titleFont, Color.BLACK));
		panel.setBounds(443, 98, 357, 256);
		
		
		frmToolmaker.getContentPane().add(panel);
		panel.setLayout(null);
		
		JRadioButton rdb_SEM = new JRadioButton("Square End Mill");
		rdb_SEM.setSelected(true);
		buttonGroup.add(rdb_SEM);
		rdb_SEM.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdb_SEM.setBounds(23, 45, 169, 25);
		rdb_SEM.setActionCommand("Endmill_Image");// Set action command to Square Endmill
		rdb_SEM.addActionListener(this);    // Add actionlisteners
		panel.add(rdb_SEM);
		
		
		JRadioButton rdb_BEM = new JRadioButton("Ball End Mill");
		buttonGroup.add(rdb_BEM);
		rdb_BEM.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdb_BEM.setBounds(23, 115, 169, 25);
		rdb_BEM.setActionCommand("BallEndmill_image");  // Set action command to Ballendmill
		rdb_BEM.addActionListener(this);      // Show corresponding picture
		panel.add(rdb_BEM);
		
		JRadioButton rdb_DRILL = new JRadioButton("Drill");
		buttonGroup.add(rdb_DRILL);
		rdb_DRILL.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdb_DRILL.setBounds(23, 185, 169, 25);
		rdb_DRILL.setActionCommand("Drill_Image");
		rdb_DRILL.addActionListener(this);      // Show corresponding picture
		panel.add(rdb_DRILL);
		
		JButton bt_NEXT = new JButton("Next");
		bt_NEXT.setBounds(467, 496, 97, 39);
		frmToolmaker.getContentPane().add(bt_NEXT);
		
		JButton bt_BACK = new JButton("Back");
		bt_BACK.setEnabled(false);
		bt_BACK.setBounds(358, 496, 97, 39);
		frmToolmaker.getContentPane().add(bt_BACK);
		
		JButton bt_FINISH = new JButton("Finish");
		bt_FINISH.setEnabled(false);
		bt_FINISH.setBounds(587, 496, 97, 39);
		frmToolmaker.getContentPane().add(bt_FINISH);
		
		JButton bt_CANCEL = new JButton("Cancel");
		bt_CANCEL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		bt_CANCEL.setBounds(703, 496, 97, 39);
		frmToolmaker.getContentPane().add(bt_CANCEL);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String acCommand = e.getActionCommand();
		
		// Check for three cases happen: Square end mill, Ball end mill, Drill
		setImage(acCommand);
		
			
		
	}
	
	/**
	 * Set Image function
	 * 
	 */
	
	public void setImage(String imagePath)
	{
        String ImagePath = "src/Figures/mainmenu/" +imagePath +".jpg";		
		Icon =  new ImageIcon(new ImageIcon(ImagePath).getImage().getScaledInstance(350, 200, Image.SCALE_DEFAULT));
		
		//lblImageshow = new JLabel();
		lblImageshow.setIcon(Icon);
		
	}
	
}
