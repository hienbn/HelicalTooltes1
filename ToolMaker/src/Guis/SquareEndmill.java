package Guis;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import GeometryPackage.*;
import HAlgorithm.HFile;
import machineKinematic.CMachine_Axes;
import machineKinematic.CinverseKinematic;

import java.lang.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;

public class SquareEndmill implements FocusListener {

	
	
	// Pull Requrest
	// Constant
    // Add Comments		
	// Geometrical class:
	// One more change
	CBlank   theBlank; //
	CFluting theFlute;
	CRecess  theRecess;
	CWheel1V1 the1V1wheel;
		
	// Hello 
	public  final  static double PI = Math.PI;	 
	double flute_setting_angle = 32*PI/180;
	
	// Fluting Geometry 
	private JFrame frmToolmakersem;          // Main window
	private JTextField tf_numofTeeth;        //[1] Number of Teeth
	private JTextField tf_WorkRadius;        //[2] Workpiece Radius 
	private JTextField tf_HelixAngle;        //[3] Helix angle
	private JTextField tf_RakeAngle;         //[4] Rake angle
	private JTextField tf_CoreRatio;         //[5] Coreradius
	private JTextField tf_cutLen;            //[6] Cutting Length 
	
	// Recess Geometry
	private JTextField tf_Core2Ratio;        //[7] Core2_Ratio
	private JTextField tf_RecessRatio;       //[8] Recess Ratio 
	private JTextField tf_MarginThick;       // [9] Margin THickness
	
	//Clearance Geometry
	private JTextField tf_1st_Clear_Ang;     // [10] First Clearance Ang.
	private JTextField tf_Clear_Width;       //[11] Clearance Width
	private JTextField tf_2nd_Clear_Ang;     //[12] Second Clearance
	
	// Fluting Panel
	JPanel panel = new JPanel();
	
	// Heel Panel
	JPanel panel_1 = new JPanel();
	
	// Clearance Panel
	JPanel panel_2 = new JPanel();
	
	// Text File
	JTextArea ta_NCfile = new JTextArea();
	
	JScrollPane scPane_NCfile = new JScrollPane(ta_NCfile);
	
	/* 4 Major buttons   */
	JButton btn_Gashing = new JButton("Gashing Geometry");
	JButton btn_Save = new JButton("Save Data");
	JButton btn_NCGeneration = new JButton("Generate NC Code");
	JButton btndSimulation = new JButton("3D Simulation");
	
	// Representive Image
	JLabel lblImage = new JLabel();
	ImageIcon iCon;
	
	//View Panel
	JPanel panel_3 = new JPanel();
	JCheckBox cb_ViewCross_Section = new JCheckBox("View Profile");
	
	// Machine setting Panel
	JPanel panel_4 = new JPanel();
	
	// Panel 5: Process Selection
	JPanel panel_5 = new JPanel();
	
	// Status
	JLabel lbStatus = new JLabel("Ready");
	private JLabel lblTooth;
	private JLabel lblNumberOf;
	private JLabel lblWorkRadius;
	private JLabel lblHelixAngle;
	private JLabel lblRakeAngle;
	private JLabel lblCoreratio;
	private JLabel lblCuttingLength;
	private JLabel lblMm;
	private JLabel lblDegree_1;
	private JLabel lblDegree;
	private JLabel lblMm_1;
	private JLabel label;
	private JLabel lblCoreRatio;
	private JLabel lblRecessRatio;
	private JLabel lblMarginThickness;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel lblDegree_2;
	private JLabel lblFirstClear;
	private JLabel lblClearanceWidth;
	private JLabel lblndClearAng;
	private JLabel lblDegree_4;
	private JLabel lblMm_2;
	private JLabel lblDegree_3;
	private JButton bt_Wheel_Edition;
	private JButton btn_MachineSet;
	private JButton btn_Feedrate;
	private JCheckBox cb_Fluting;
	private JCheckBox cbRecess;
	private JCheckBox cbGashing;
	private JCheckBox cb1stClearance;
	private JCheckBox cb2ndClearance;
	private JCheckBox cb1stEndTeeth;
	private JCheckBox cb_2ndEndTeeth;
	

	// Menu
	JMenuBar menuBar = new JMenuBar();
	JMenu mnFile = new JMenu("File");
	JMenuItem mntmOpen = new JMenuItem("New");
	
	private JMenuItem mntmOpen_1;
	private JLabel lblTime;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
										
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
					
					
					SquareEndmill window = new SquareEndmill();
					window.frmToolmakersem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SquareEndmill() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Initialize main Frame
		frmToolmakersem = new JFrame();
		frmToolmakersem.setIconImage(Toolkit.getDefaultToolkit().getImage(SquareEndmill.class.getResource("/Figures/machine_image1.jpg")));
		frmToolmakersem.setTitle("ToolMaker-SEM");
		frmToolmakersem.setResizable(false);
		frmToolmakersem.setBounds(100, 100, 1090, 785);
		frmToolmakersem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToolmakersem.getContentPane().setLayout(null);
		
		
		frmToolmakersem.setJMenuBar(menuBar);
		
		
		
		
		// Creat Panel1: Fluting
		create_Fluting_Panel();
		
		// Creat Panel2: Heel
		create_Heel_Panel();
		
		// Creat Panel3: Clearance
		create_Clearance_Panel();
		
		//Create View Panel:
		create_View_Panel();
		
		// Create Setting Panel
		create_Setting_Panel();
		
		//create ProcessSelection Panel()
		create_ProcessSelection_Panel();
		
		//create_Action_Panel
		create_Action_Panel();
		
		// Creat NC File Holder
		creat_NCfileHolder();
		
		// Creat Menubar
		creatMenuBar();
		
		
		//
		creat_ImageHolder();
		setImage("Teeth_num");// initially show num of teeths
		
		// Loading wheel and
		the1V1wheel = new CWheel1V1(50, 49, 49.5, 4.8, 0.2);
		
		
		// Set default grinding prosesses:
		initilizeProcesses();
		
		
	}
	
	
	// Create Fluting Panel
	private void create_Fluting_Panel()
	{
		
		panel.setBorder(new TitledBorder(null, "Fluting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 26, 288, 243);
		frmToolmakersem.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNumberOf = new JLabel("[1] Number of Teeth");
		lblNumberOf.setBounds(12, 15, 120, 22);		
		panel.add(lblNumberOf);
		
		tf_numofTeeth = new JTextField("2.0");
		tf_numofTeeth.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_numofTeeth.setToolTipText("Teeth_num");
		tf_numofTeeth.setBounds(144, 15, 76, 22);
		tf_numofTeeth.addFocusListener(this);
		panel.add(tf_numofTeeth);
		tf_numofTeeth.setColumns(10);
		
		lblWorkRadius = new JLabel("[2] Work Radius");
		lblWorkRadius.setBounds(12, 52, 120, 22);
		panel.add(lblWorkRadius);
		
		tf_WorkRadius = new JTextField("3");
		tf_WorkRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_WorkRadius.setToolTipText("WP_Radius");
		tf_WorkRadius.setColumns(10);
		tf_WorkRadius.setBounds(144, 52, 76, 22);
		tf_WorkRadius.addFocusListener(this);
		panel.add(tf_WorkRadius);
		
		tf_HelixAngle = new JTextField("30");
		tf_HelixAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_HelixAngle.setToolTipText("Helix_angle");
		tf_HelixAngle.setColumns(10);
		tf_HelixAngle.setBounds(144, 89, 76, 22);
		tf_HelixAngle.addFocusListener(this);
		panel.add(tf_HelixAngle);
		
		lblHelixAngle = new JLabel("[3] Helix Angle");
		lblHelixAngle.setBounds(12, 89, 120, 22);
		panel.add(lblHelixAngle);
		
		tf_RakeAngle = new JTextField("8.0");
		tf_RakeAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_RakeAngle.setToolTipText("Rake_angle");
		tf_RakeAngle.setColumns(10);
		tf_RakeAngle.setBounds(144, 126, 76, 22);
		tf_RakeAngle.addFocusListener(this);
		panel.add(tf_RakeAngle);
		
		lblRakeAngle = new JLabel("[4] Rake Angle");
		lblRakeAngle.setBounds(12, 126, 120, 22);
		panel.add(lblRakeAngle);
		
		tf_CoreRatio = new JTextField("0.6");
		tf_CoreRatio.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_CoreRatio.setToolTipText("Core_ratio");
		tf_CoreRatio.setColumns(10);
		tf_CoreRatio.setBounds(144, 163, 76, 22);
		tf_CoreRatio.addFocusListener(this);
		panel.add(tf_CoreRatio);
		
		lblCoreratio = new JLabel("[5] Core_ratio");
		lblCoreratio.setBounds(12, 163, 120, 22);
		panel.add(lblCoreratio);
		
		tf_cutLen = new JTextField("20.0");
		tf_cutLen.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_cutLen.setToolTipText("Cutting_length");
		tf_cutLen.setColumns(10);
		tf_cutLen.setBounds(144, 200, 76, 22);
		tf_cutLen.addFocusListener(this);
		panel.add(tf_cutLen);
		
		lblCuttingLength = new JLabel("[6] Cutting Length");
		lblCuttingLength.setBounds(12, 200, 120, 22);
		panel.add(lblCuttingLength);
		
		lblTooth = new JLabel("Tooth");
		lblTooth.setBounds(231, 21, 56, 16);
		panel.add(lblTooth);
		
		lblMm = new JLabel("mm");
		lblMm.setBounds(231, 58, 56, 16);
		panel.add(lblMm);
		
		lblDegree_1 = new JLabel("Degree");
		lblDegree_1.setBounds(232, 132, 56, 16);
		panel.add(lblDegree_1);
		
		lblDegree = new JLabel("Degree");
		lblDegree.setBounds(232, 95, 56, 16);
		panel.add(lblDegree);
		
		lblMm_1 = new JLabel("mm");
		lblMm_1.setBounds(231, 206, 56, 16);
		panel.add(lblMm_1);
		
		label = new JLabel("[0...1]");
		label.setBounds(231, 169, 56, 16);
		panel.add(label);
		
		
	}
	
	// Create Heel Panel
	private void create_Heel_Panel()
	{
		
		// Heel Panel
				panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Heel Geometry", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel_1.setBounds(12, 282, 288, 150);
				frmToolmakersem.getContentPane().add(panel_1);
				panel_1.setLayout(null);
				
				lblCoreRatio = new JLabel("[7] Core2 Ratio");
				lblCoreRatio.setBounds(12, 21, 120, 22);
				panel_1.add(lblCoreRatio);
				
				tf_Core2Ratio = new JTextField("0.8");
				tf_Core2Ratio.setHorizontalAlignment(SwingConstants.RIGHT);
				tf_Core2Ratio.setToolTipText("Core2_ratio");
				tf_Core2Ratio.setColumns(10);
				tf_Core2Ratio.setBounds(144, 21, 76, 22);
				tf_Core2Ratio.addFocusListener(this);
				panel_1.add(tf_Core2Ratio);
				
				lblRecessRatio = new JLabel("[8] Recess Ratio");
				lblRecessRatio.setBounds(12, 64, 120, 22);
				panel_1.add(lblRecessRatio);
				
				tf_RecessRatio = new JTextField("0.8");
				tf_RecessRatio.setHorizontalAlignment(SwingConstants.RIGHT);
				tf_RecessRatio.setToolTipText("Recess_ratio");
				tf_RecessRatio.setColumns(10);
				tf_RecessRatio.setBounds(144, 64, 76, 22);
				tf_RecessRatio.addFocusListener(this);
				panel_1.add(tf_RecessRatio);
				
				lblMarginThickness = new JLabel("[9] Margin Thickness");
				lblMarginThickness.setBounds(12, 107, 120, 22);
				panel_1.add(lblMarginThickness);
				
				tf_MarginThick = new JTextField("20.0");
				tf_MarginThick.setHorizontalAlignment(SwingConstants.RIGHT);
				tf_MarginThick.setToolTipText("Recess_ratio");
				tf_MarginThick.setColumns(10);
				tf_MarginThick.setBounds(144, 107, 76, 22);
				tf_MarginThick.addFocusListener(this);
				panel_1.add(tf_MarginThick);
				
				label_1 = new JLabel("[0...1]");
				label_1.setBounds(232, 66, 56, 16);
				panel_1.add(label_1);
				
				label_2 = new JLabel("[0...1]");
				label_2.setBounds(232, 25, 56, 16);
				panel_1.add(label_2);
				
				lblDegree_2 = new JLabel("Degree");
				lblDegree_2.setBounds(232, 107, 56, 16);
				panel_1.add(lblDegree_2);
		
	}
	
	// Create Clearance face panel
	private void create_Clearance_Panel()
	{
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Clearance Face", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(12, 445, 288, 150);
		frmToolmakersem.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		lblFirstClear = new JLabel("[10] 1st Clear Angle");
		lblFirstClear.setBounds(12, 21, 120, 22);
		panel_2.add(lblFirstClear);
		
		tf_1st_Clear_Ang = new JTextField("11.0");
		tf_1st_Clear_Ang.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_1st_Clear_Ang.setToolTipText("BEM_1st_clearance_ang");
		tf_1st_Clear_Ang.setColumns(10);
		tf_1st_Clear_Ang.setBounds(144, 21, 76, 22);
		
		tf_1st_Clear_Ang.addFocusListener(this);
		panel_2.add(tf_1st_Clear_Ang);
		
		lblClearanceWidth = new JLabel("[11] Clear Width");
		lblClearanceWidth.setBounds(12, 64, 120, 22);
		panel_2.add(lblClearanceWidth);
		
		tf_Clear_Width = new JTextField("0.8");
		tf_Clear_Width.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_Clear_Width.setToolTipText("BEM_1st_width");
		tf_Clear_Width.setColumns(10);
		tf_Clear_Width.setBounds(144, 64, 76, 22);
		tf_Clear_Width.addFocusListener(this);
		panel_2.add(tf_Clear_Width);
		
		lblndClearAng = new JLabel("[12]2nd Clear Ang.");
		lblndClearAng.setBounds(12, 107, 120, 22);
		panel_2.add(lblndClearAng);
		
		tf_2nd_Clear_Ang = new JTextField("17.0");
		tf_2nd_Clear_Ang.setHorizontalAlignment(SwingConstants.RIGHT);
		tf_2nd_Clear_Ang.setToolTipText("BEM_2nd_Clearance_ang");
		tf_2nd_Clear_Ang.setColumns(10);
		tf_2nd_Clear_Ang.setBounds(144, 107, 76, 22);
		tf_2nd_Clear_Ang.addFocusListener(this);
		panel_2.add(tf_2nd_Clear_Ang);
		
		lblDegree_4 = new JLabel("Degree");
		lblDegree_4.setBounds(231, 107, 56, 16);
		panel_2.add(lblDegree_4);
		
		lblMm_2 = new JLabel("mm");
		lblMm_2.setBounds(231, 66, 56, 16);
		panel_2.add(lblMm_2);
		
		lblDegree_3 = new JLabel("Degree");
		lblDegree_3.setBounds(231, 25, 56, 16);
		panel_2.add(lblDegree_3);
		
	}
	
	// view Panel
	private void create_View_Panel()
	{
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "View Profile", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(360, 356, 154, 60);
		frmToolmakersem.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		cb_ViewCross_Section.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showProfile();
				
			}
		});
		
		
		cb_ViewCross_Section.setBounds(8, 26, 138, 25);
		panel_3.add(cb_ViewCross_Section);
		
	}
	
	//Setting
	private void create_Setting_Panel()
	{
	
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Machine Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(360, 436, 154, 158);
		frmToolmakersem.getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		bt_Wheel_Edition = new JButton("Wheel Edittion");
		bt_Wheel_Edition.setBounds(12, 22, 128, 33);
		panel_4.add(bt_Wheel_Edition);
		
		btn_MachineSet = new JButton("Machine_Setting");
		btn_MachineSet.setBounds(12, 67, 128, 33);
		panel_4.add(btn_MachineSet);
		
		btn_Feedrate = new JButton("Feedrates");
		btn_Feedrate.setBounds(12, 112, 128, 33);
		panel_4.add(btn_Feedrate);
		
	}
	
	//Process selection
	private void create_ProcessSelection_Panel()
	{
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Operation Selection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(544, 356, 172, 235);
		frmToolmakersem.getContentPane().add(panel_5);
		panel_5.setLayout(null);
		
		cb_Fluting = new JCheckBox("Fluting");
		cb_Fluting.setBounds(8, 21, 113, 25);
		panel_5.add(cb_Fluting);
		
		cbRecess = new JCheckBox("Recess");
		cbRecess.setBounds(8, 51, 113, 25);
		panel_5.add(cbRecess);
		
		cbGashing = new JCheckBox("Gashing");
		cbGashing.setBounds(8, 81, 113, 25);
		panel_5.add(cbGashing);
		
		cb1stClearance = new JCheckBox("1st Clearance");
		cb1stClearance.setBounds(8, 111, 113, 25);
		panel_5.add(cb1stClearance);
		
		cb2ndClearance = new JCheckBox("2nd Clearance");
		cb2ndClearance.setBounds(8, 141, 113, 25);
		panel_5.add(cb2ndClearance);
		
		cb1stEndTeeth = new JCheckBox("1st EndTeeth");
		cb1stEndTeeth.setBounds(8, 171, 113, 25);
		panel_5.add(cb1stEndTeeth);
		
		cb_2ndEndTeeth = new JCheckBox("2nd EndTeeth");
		cb_2ndEndTeeth.setBounds(8, 201, 113, 25);
		panel_5.add(cb_2ndEndTeeth);
		
	}
	
	// Action Panel
	private void create_Action_Panel()
	{
		//Gashing
				btn_Gashing.setFont(new Font("Tahoma", Font.BOLD, 13));
				btn_Gashing.setBounds(12, 640, 164, 55);
				frmToolmakersem.getContentPane().add(btn_Gashing);
				btn_Save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// Update data from Gui 
						updateDatatoEngine();
					}
				});
				
				
				//Sava
				
				btn_Save.setFont(new Font("Tahoma", Font.BOLD, 13));
				btn_Save.setBounds(207, 640, 164, 55);
				frmToolmakersem.getContentPane().add(btn_Save);
				
				btn_NCGeneration.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						calculateToolPath();
					}
				});
				
				// NC Generation Button
				btn_NCGeneration.setFont(new Font("Tahoma", Font.BOLD, 13));
				btn_NCGeneration.setBounds(394, 640, 164, 55);
				frmToolmakersem.getContentPane().add(btn_NCGeneration);
				btndSimulation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						call_Simulation_Prog();
						
					}
				});
				
				// 3Dsimulation
				btndSimulation.setFont(new Font("Tahoma", Font.BOLD, 13));
				btndSimulation.setBounds(576, 639, 164, 55);
				frmToolmakersem.getContentPane().add(btndSimulation);
		
	}
	
	private void creat_NCfileHolder()
	{
		ta_NCfile.setFont(new Font("Dialog", Font.PLAIN, 13));
		ta_NCfile.setBorder(new LineBorder(new Color(0, 0, 0)));
		ta_NCfile.setForeground(Color.BLUE);
		ta_NCfile.setBackground(new Color(224, 255, 255));
		ta_NCfile.setLineWrap(true);
		ta_NCfile.setRows(100);
		ta_NCfile.setWrapStyleWord(true);
		ta_NCfile.setEditable(false);
		ta_NCfile.setBounds(757, 23, 299, 592);
		
		//scPane_NCfile.setSize(100, 100);
		scPane_NCfile.setBounds(757, 23, 299, 592);		
		// Add ScrollPanel to NCFile_TextArea
		frmToolmakersem.getContentPane().add(scPane_NCfile); 
		
	}
	
	private void creatMenuBar()
	{
		menuBar.add(mnFile);		
		mnFile.add(mntmOpen);		
		mntmOpen_1 = new JMenuItem("Open");
		mnFile.add(mntmOpen_1);
	}
	
	
	private void creat_ImageHolder()
	{
		lblImage.setBounds(342, 26, 398, 263);
		frmToolmakersem.getContentPane().add(lblImage);
		
		
		lbStatus.setBounds(12, 705, 73, 22);
		frmToolmakersem.getContentPane().add(lbStatus);
		
		lblTime = new JLabel("Time");
		lblTime.setBounds(112, 702, 272, 22);
		frmToolmakersem.getContentPane().add(lblTime);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Runtime.getRuntime().exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExit.setBounds(762, 639, 164, 55);
		frmToolmakersem.getContentPane().add(btnExit);
		frmToolmakersem.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblTooth, panel, lblNumberOf, tf_numofTeeth, lblWorkRadius, tf_WorkRadius, tf_HelixAngle, lblHelixAngle, tf_RakeAngle, lblRakeAngle, tf_CoreRatio, lblCoreratio, tf_cutLen, lblCuttingLength, lblMm, lblDegree_1, lblDegree, lblMm_1, label, panel_1, lblCoreRatio, tf_Core2Ratio, lblRecessRatio, tf_RecessRatio, lblMarginThickness, tf_MarginThick, label_1, label_2, lblDegree_2, panel_2, lblFirstClear, tf_1st_Clear_Ang, lblClearanceWidth, tf_Clear_Width, lblndClearAng, tf_2nd_Clear_Ang, lblDegree_4, lblMm_2, lblDegree_3, panel_3, cb_ViewCross_Section, panel_4, bt_Wheel_Edition, btn_MachineSet, btn_Feedrate, panel_5, cb_Fluting, cbRecess, cbGashing, cb1stClearance, cb2ndClearance, cb1stEndTeeth, cb_2ndEndTeeth, btn_Gashing, btn_Save, btn_NCGeneration, btndSimulation, ta_NCfile, lblImage, lbStatus}));
	}
	
	private void setImage(String imageName)
	{
		// Image
		String imagefullPath = "src/Figures/"+ imageName+ ".jpg";
        iCon =  new ImageIcon(new ImageIcon(imagefullPath).getImage().getScaledInstance(300, 230, Image.SCALE_DEFAULT));
		lblImage.setIcon(iCon);		
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() instanceof JTextField) {
			JTextField atf = (JTextField) e.getSource();

			if (e.isTemporary()) {
				// lbStatus.setText(e.getC ": Temperary");
			} else {
				setImage(atf.getToolTipText());
				atf.setBackground(Color.CYAN);

			}

		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JTextField) {
			JTextField atf = (JTextField) e.getSource();

			if (e.isTemporary()) {
				// lbStatus.setText(e.getC ": Temperary");
			} else {
				//setImage(atf.getToolTipText());
				atf.setBackground( UIManager.getColor("TextField.background"));

			}

		}
	}
	
	
	
	
	/**<h1> This functions update the input data to Calculating Engine.</h1>
	 * 
	 * Read data from the textfields and form the objects.
	 * 
	 * 
	 */
	private void updateDatatoEngine()
	{
		// Blank geometry
		//double dnumofTeeth = tf_numofTeeth.getText();
		int    numofTeeth =  (int) Math.floor(Double.parseDouble(tf_numofTeeth.getText()));
		
		double R = Double.parseDouble(tf_WorkRadius.getText());
		double helix = Double.parseDouble(tf_HelixAngle.getText())*PI/180;
		double rakeang = Double.parseDouble(tf_RakeAngle.getText())*PI/180;		
		double core = Double.parseDouble(tf_CoreRatio.getText());
		double cutLen = Double.parseDouble(tf_cutLen.getText());
		
		// Heel Geometry:
		double core2_ratio = Double.parseDouble(tf_Core2Ratio.getText());
		double recess_ratio = Double.parseDouble(tf_RecessRatio.getText());
		double margin_thick = Double.parseDouble(tf_MarginThick.getText())*PI/180;
		
		theBlank =  new CBlank(numofTeeth, R, helix, cutLen);
		theFlute =  new CFluting(theBlank ,rakeang, core);
		theRecess = new CRecess(theBlank, core2_ratio,recess_ratio, margin_thick);
		theRecess.setWheel(the1V1wheel);
		theRecess.setSettingAngle(flute_setting_angle);
		
		
	}
	
	
	/**
	 * Auto select the default machining options like: fluting (1), recess (1) 
	 */
	private void initilizeProcesses()
	{
		/* if new Endmill is generated  */
		cb_Fluting.setSelected(true);
		
		int    numofTeeth =  (int) Math.floor(Double.parseDouble(tf_numofTeeth.getText()));
		
		if(numofTeeth==2)
		{
			cbRecess.setSelected(false);
		}
		else
		{
			cbRecess.setSelected(true);
		}
		
	}
	
	
	/**<h1> Generate the corresponding Tooth path from the formed objects.</h1>
	 * 
	 * 
	 */
	public void calculateToolPath()
	{
		
				
	   // Grinding follow sequence : fluting--> Recessing--> Gashingin
	   //--> 2nd Clearance--> 1st cleareance--> endteeth.
	   
		long time_Start = System.nanoTime();
		
		Path ncFile = Paths.get("./src/Simulation_Pack/O3110");		
		BufferedWriter bw_ncFile = null;
		
		// Open file and initialize the content of the file.
		try {
			// Open file and clear the remained data in it.
			 bw_ncFile = Files.newBufferedWriter(ncFile, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
			 
			 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try 
		{
			initialWritetoNCFile(bw_ncFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CMachine_Axes wheelPoseforFlute;
		
		CMachine_Axes wheelPoseforRecess;
		
		updateDatatoEngine();
	   //=========FLUTING==========
	   if(cb_Fluting.isSelected())	   
	   {	   
		   if(theFlute.getInumofTeeth() ==2)
		   {
			   
		     wheelPoseforFlute= generatingNCfor2Flute_SEM();   
		     writeNCCodetoFile(bw_ncFile, wheelPoseforFlute, theBlank);
		     
		   }
		   else if(theFlute.getInumofTeeth() >2)
		   {
			  double  m_Aopen_ds_IP = (2*PI/theBlank.getInumofTeeth()) -theRecess.getdMarginThick();
			   CWheelSetting_out cwheelSetOut = theFlute.find_theta_BS(theFlute.getdCoreRatio(), m_Aopen_ds_IP, theBlank, the1V1wheel);
			   
			   CWheelLocation wheellct_flute = theFlute.getWheelLCT(cwheelSetOut, the1V1wheel);
			   
			   wheelPoseforFlute = CinverseKinematic.cvWheelPose_toAxes(wheellct_flute.getWheelOrientation()
                                                  , wheellct_flute.getWheelCenter());
			   
			   writeNCCodetoFile(bw_ncFile, wheelPoseforFlute, theBlank);
			   
		   }
		   
		   else
		   {
			   try {
				throw new Exception("Number of teeths is wrong");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		   }
		   
		   
	   }
	   //========RECESSING===========
	   if(cbRecess.isSelected())
	   {
		  
		   theRecess.calAndWriteNCforRecessing(theBlank, the1V1wheel, flute_setting_angle);
		   wheelPoseforRecess = theRecess.getWheelPose();	 
		   theRecess.writeNCCodetoFile(bw_ncFile, wheelPoseforRecess, theBlank);
		   
	   }
	   //=======Gashinng============
	   if(cbGashing.isSelected())
	   {
		   
	   }	   
	   //=======2ndClearance========
	   //=======Other processes=====  
	   
	   //After finishing writing the File, display it to TextArea.
	   //ta_NCfile.setText("Hello are you ok? \r\n This is other line.");
	   
	   closeNCFile( ncFile, 1);	   
	   loadNCFiletoTextArea();
	   
	   long time_Stop = System.nanoTime();
	   
	   // Convert to Miliseconds
	   long durationInMs = TimeUnit.NANOSECONDS.toMillis(time_Stop -time_Start);
	   
	   lblTime.setText(String.format("Computation Time: %.5f seconds",(double)(durationInMs/1000.0)));
	   
	   
		
	}
	
	// Generate NCCode for 2-teeths square endmill.
	public CMachine_Axes generatingNCfor2Flute_SEM()
	{
	
		//CWheelLocation WheelLCTforFirstFlute(CBlank aBlank, CWheel1V1 aWheel1V1, double settingAngle)
		
		// Obtain wheel location for the fluting operation
		CWheelLocation wheelpose_fluting =  theFlute.WheelLCTforFirstFlute( theBlank,  
				                                        the1V1wheel,  flute_setting_angle);
		
		CMachine_Axes machine_axes_fluting = CinverseKinematic.cvWheelPose_toAxes(wheelpose_fluting.getWheelOrientation()
				                                                                  , wheelpose_fluting.getWheelCenter());
				
		int a = 5;		
		return machine_axes_fluting;
		
	}
	
	public void writeNCCodetoFile(BufferedWriter bw_ncFile,CMachine_Axes wheelPoseforFlute, CBlank theBlank)
	{
						
		// Get the NC code at the three points in grinding the flute.
	 List<CMachine_Axes> NCcode3Points = CinverseKinematic.getStartnEndPositions(wheelPoseforFlute, theBlank, 2*theBlank.getdWorkRadius(),0);
	
	 // Loop through 3 points and write the NC code.
	 int numofTeeth = theBlank.getInumofTeeth();	 
	 CMachine_Axes mca_current = NCcode3Points.get(0);
	 CMachine_Axes mca_end = NCcode3Points.get(2);
	 
	 double del_A_perFlute;
	 // require the feed either
	 for(int i=0; i< numofTeeth; i++)
	 {
		 	 
		 del_A_perFlute = 360*(i)/numofTeeth;
		 // Approaching with i== 1;
		 if(i == 0)
		 {			 
			 HFile.bwriteln(bw_ncFile, "");
			 HFile.bwriteln(bw_ncFile, "(Approaching)");			 
			 String str = String.format("G90G00 X0.0 W0.0 A%.3f", mca_current.A*180/PI);			 
			 HFile.bwriteln(bw_ncFile, str);
			 
			 String str_YZW = String.format("Y%.3f Z%.3f W%.3f", mca_current.Y,mca_current.Z, -mca_current.W*180/PI);		
			 HFile.bwriteln(bw_ncFile, str_YZW);
			 
		 }
		 
		 		 
		 // Move to XZ position
		 HFile.bwriteln(bw_ncFile, "");
		 HFile.bwriteln(bw_ncFile, String.format("(>> FLUTING TEETH %d)", i+1));
		 HFile.bwriteln(bw_ncFile, String.format("G90G00 X%.3f Z%.3f A%.3f", 
				          mca_current.X, mca_current.Z, mca_current.A*180/PI + del_A_perFlute));
		 
		 // Wheel go down Y--> Y start.
		 HFile.bwriteln(bw_ncFile, "(WHEEL DOWN)");
		 HFile.bwriteln(bw_ncFile, String.format("G90G00 Y%.3f", mca_current.Y));
		 
		 HFile.bwriteln(bw_ncFile, "");
		 // Approach the starting point
		 HFile.bwriteln(bw_ncFile, String.format("G90G01 X%.3f Z%.3f A%.3f F20", 
		          mca_current.X, mca_current.Z, mca_current.A*180/PI  +del_A_perFlute));
		 
		 //Machining
		 HFile.bwriteln(bw_ncFile, "(MACHINING TO THE END OF THE FLUTE)");
		 HFile.bwriteln(bw_ncFile, String.format("G90G01 X%.3f Z%.3f A%.3f F20", 
				        mca_end.X, mca_end.Z, mca_end.A*180/PI + del_A_perFlute));
		 
		 //Wheel going up and perform the next operation.
		 HFile.bwriteln(bw_ncFile, "");
		 HFile.bwriteln(bw_ncFile, String.format("G00G91 Y%.3f", 5*theBlank.getdWorkRadius()));// Go up an amount 2R.
		 
	 }
	 
		
	}
	
	
	
	
	// Start writing to NC file.
	// %O 4000(Program name)
	// Initialize some conditions.
	public void initialWritetoNCFile(BufferedWriter bfile) throws IOException
	{
		// Using BefferWrite.
		// Created a new file
		
		String strDate = (ZonedDateTime.now()).format(DateTimeFormatter.RFC_1123_DATE_TIME);
		Charset charset =  Charset.forName("UTF-8");
		
		
		//HFile.bclear(bfile);		  
		HFile.bwriteln(bfile, "%");
		HFile.bwriteln(bfile, "O3005");
		HFile.bwriteln(bfile, "(Created: " + strDate + ")");
		HFile.bwriteln(bfile, "(Generated by Hien's application)");
		//HFile.writeln(file, "%");
		//bufWrite.write("(NC Code is generated by Hien's application)");	
		
		
	}
	
	/** 
	 * 
	 * @param file
	 * @param option  1: End with M30, 0: M99
	 */
	public static void closeNCFile(Path file, int option)
	{
		if(option ==1)
		{
		  HFile.writeln(file, "M30");
		  HFile.writeln(file, "%");
		}
		else
		{
			HFile.writeln(file, "M99");
			HFile.writeln(file, "%");
		}
		
	}
	
	public void loadNCFiletoTextArea()
	{
		//ta_NCfile;
		ta_NCfile.setText("");
		
		//Path ncFile = Paths.get("C:/data/O3011.txt");
		
		Path ncFile = Paths.get("./src/Simulation_Pack/O3110");
		
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(ncFile, charset)) 
		{
		    String line = null;
		    
		    while ((line = reader.readLine()) != null) 
		    {
		       // System.out.println(line);
		    	ta_NCfile.append(line + "\r\n");
		    }
		} 
		catch (IOException x) 
		
		{
		    System.err.format("IOException: %s%n", x);
		}
		
		// Return caret to the first position.
		ta_NCfile.setCaretPosition(0);
		
	}
	
	
	public void call_Simulation_Prog()
	{
		//Runtime.getRuntime().exec("c:\\program files\\test\\test.exe", null, new File("c:\\program files\\test\\"));
		//exec(String command, String[] envp, File dir)
		
		//C://Users//HIENBN//workspace//ToolMaker//src//Simulation_Pack//
		
		
		
		//===========Translate NC code================================/
		
		CinverseKinematic.interpolateNCCODE();
		
		/********************************/
		
		String progLct = "./src/Simulation_Pack/Simulation_IS.exe";
		String[] envp = null;
		File afile_dir = new File("./src/Simulation_Pack/");
		
		// Call simulation Program
		try {
			
			Runtime.getRuntime().exec(progLct, envp, afile_dir);
			//Runtime.getRuntime().exec("Simulation_Pack\\Simulation_IS.exe");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Cannot run Simulation_IS.exe in"  + afile_dir);
			e.printStackTrace();
		}
		
	}
	
	public void showProfile()
	{
		if(cb_ViewCross_Section.isSelected())
		{
			//Rotate the flute to standard flute profile. 44
			
			//List<List<Double>> finalCross = Profile.intersect_two_Cross(theFlute.getCalculatedCTLine().getfullProfile(),
			//		                                                    theRecess.getCalculatedCTLine().getfullProfile());
			
			CrossSection aCross;
			
			
			if(cb_Fluting.isSelected())
			{
			//theFlute.getstandarlizeFluteProfile();
				theFlute.getstandarlizeFluteProfile();
			aCross = new CrossSection(theFlute, theRecess);
			
			}
			
			if(cbRecess.isSelected())
			{
		    // theRecess.getStandardRecessProfile();		
			//aCross = new CrossSection(theRecess.getCalculatedCTLine().getfullProfile());
				theRecess.getStandardRecessProfile();	
				
			}
			
			// Reset the check of View
			cb_ViewCross_Section.setSelected(false);
			//theRecess.prinToConsole_FullProfile();
		}
		
	}
	
	
}
