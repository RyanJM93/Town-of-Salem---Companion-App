package window;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.GridBagLayout;

import javax.swing.JLayeredPane;

import java.awt.CardLayout;

import javax.swing.JEditorPane;
import javax.swing.JToolBar;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.Timer;

import java.awt.Frame;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.Color;


@SuppressWarnings("unused")
public class Window {

	private JFrame frame;
	JFrame roleFrame = new JFrame("Role Selection");
	JPanel roleFrameSelectionPanel = new JPanel();
	
	private JTable townTable;
	private JTable mafiaTable;
	private JTable neutralTable;


	JPanel mainViewportPanel = new JPanel();
	JLabel roleInfoSheet = new JLabel("");

	JPanel rolesSelectionGrid = new JPanel();
	
	JLabel bodyguardIcon = new JLabel("");
	JLabel doctorIcon = new JLabel("");
	JLabel escortIcon = new JLabel("");
	JLabel investigatorIcon = new JLabel("");
	JLabel jailorIcon = new JLabel("");
	JLabel lookoutIcon = new JLabel("");
	JLabel mayorIcon = new JLabel("");
	JLabel mediumIcon = new JLabel("");
	JLabel retributionistIcon = new JLabel("");
	JLabel sheriffIcon = new JLabel("");
	JLabel spyIcon = new JLabel("");
	JLabel transporterIcon = new JLabel("");
	JLabel veteranIcon = new JLabel("");
	JLabel vigilanteIcon = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		frame = new JFrame();
		frame.setBounds(50, 50, 1280, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bodyguardIcon.setIcon(new ImageIcon(Window.class.getResource("/window/bodyguardIcon.png")));
		doctorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/doctorIcon.png")));
		escortIcon.setIcon(new ImageIcon(Window.class.getResource("/window/escortIcon.png")));
		investigatorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/investigatorIcon.png")));
		jailorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/jailorIcon.png")));
		lookoutIcon.setIcon(new ImageIcon(Window.class.getResource("/window/lookoutIcon.png")));
		mayorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/mayorIcon.png")));
		mediumIcon.setIcon(new ImageIcon(Window.class.getResource("/window/mediumIcon.png")));
		retributionistIcon.setIcon(new ImageIcon(Window.class.getResource("/window/retributionistIcon.png")));
		sheriffIcon.setIcon(new ImageIcon(Window.class.getResource("/window/sheriffIcon.png")));
		spyIcon.setIcon(new ImageIcon(Window.class.getResource("/window/spyIcon.png")));
		transporterIcon.setIcon(new ImageIcon(Window.class.getResource("/window/transporterIcon.png")));
		veteranIcon.setIcon(new ImageIcon(Window.class.getResource("/window/veteranIcon.png")));
		vigilanteIcon.setIcon(new ImageIcon(Window.class.getResource("/window/vigilanteIcon.png")));
		
		roleFrame.setBounds(1330, 50, 480, 960);
		roleFrame.setVisible(true);
		roleFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		roleFrame.getContentPane().add(roleFrameSelectionPanel, BoxLayout.X_AXIS);
		
		roleFrameSelectionPanel.add(bodyguardIcon);
		bodyguardIcon.setEnabled(false);
		roleFrameSelectionPanel.add(doctorIcon);
		doctorIcon.setEnabled(false);
		roleFrameSelectionPanel.add(escortIcon);
		escortIcon.setEnabled(false);
		roleFrameSelectionPanel.add(investigatorIcon);
		investigatorIcon.setEnabled(false);
		roleFrameSelectionPanel.add(jailorIcon);
		jailorIcon.setEnabled(false);
		roleFrameSelectionPanel.add(lookoutIcon);
		lookoutIcon.setEnabled(false);
		roleFrameSelectionPanel.add(mayorIcon);
		mayorIcon.setEnabled(false);
		roleFrameSelectionPanel.add(mediumIcon);
		mediumIcon.setEnabled(false);
		roleFrameSelectionPanel.add(retributionistIcon);
		retributionistIcon.setEnabled(false);
		roleFrameSelectionPanel.add(sheriffIcon);
		sheriffIcon.setEnabled(false);
		roleFrameSelectionPanel.add(spyIcon);
		spyIcon.setEnabled(false);
		roleFrameSelectionPanel.add(transporterIcon);
		transporterIcon.setEnabled(false);
		roleFrameSelectionPanel.add(veteranIcon);
		veteranIcon.setEnabled(false);
		roleFrameSelectionPanel.add(vigilanteIcon);
		vigilanteIcon.setEnabled(false);
		
		JPanel headerPanel = new JPanel();
		frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
		
		frame.getContentPane().add(mainViewportPanel, BorderLayout.CENTER);
		mainViewportPanel.setLayout(null);
		
		JPanel rolesPanel = new JPanel();
		rolesPanel.setBounds(10, 11, 303, 446);
		mainViewportPanel.add(rolesPanel);
		rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.X_AXIS));
		
		JTabbedPane rolesTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		rolesPanel.add(rolesTabbedPane);
		
		JScrollPane townScrollPane = new JScrollPane();
		rolesTabbedPane.addTab("Town", null, townScrollPane, null);

		JScrollPane roleInfoPane = new JScrollPane();
		roleInfoPane.setBounds(323, 33, 931, 868);
		mainViewportPanel.add(roleInfoPane);
		
		roleInfoSheet.setHorizontalAlignment(SwingConstants.CENTER);
		roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/infoScreen.PNG")));
		roleInfoPane.setViewportView(roleInfoSheet);
		
		townTable = new JTable();
		townTable.setToolTipText("");
		townTable.setRowSelectionAllowed(false);
		townTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Bodyguard", "Town Protective", Boolean.FALSE},
				{"Doctor", "Town Protective", Boolean.FALSE},
				{"Escort", "Town Support", Boolean.FALSE},
				{"Investigator", "Town Investigative", Boolean.FALSE},
				{"Jailor", "Town Killing", Boolean.FALSE},
				{"Lookout", "Town Investigative", Boolean.FALSE},
				{"Mayor", "Town Support", Boolean.FALSE},
				{"Medium", "Town Support", Boolean.FALSE},
				{"Retributionist", "Town Support", Boolean.FALSE},
				{"Sheriff", "Town Investigative", Boolean.FALSE},
				{"Spy", "Town Investigative", Boolean.FALSE},
				{"Transporter", "Town Support", Boolean.FALSE},
				{"Veteran", "Town Killing", Boolean.FALSE},
				{"Vigilante", "Town Killing", Boolean.FALSE},
			},
			new String[] {
				"Town Roles", "Type", "Active"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, Boolean.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		townTable.getColumnModel().getColumn(0).setResizable(false);
		townTable.getColumnModel().getColumn(1).setResizable(false);
		townTable.getColumnModel().getColumn(1).setPreferredWidth(95);
		townTable.getColumnModel().getColumn(2).setResizable(false);
		townScrollPane.setViewportView(townTable);
		
		townTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int cellx = e.getColumn();
				int celly = e.getFirstRow();
				Boolean cellValue = (Boolean) townTable.getValueAt(celly, cellx);
				
				if (cellValue == Boolean.TRUE){
					switch(celly){
		    		case 0:
		    			bodyguardIcon.setEnabled(true);
		    			break;
		    		case 1:
		    			doctorIcon.setEnabled(true);
		    			break;
		    		case 2:
		    			escortIcon .setEnabled(true);
		    			break;
		    		case 3:
		    			investigatorIcon .setEnabled(true);
		    			break;
		    		case 4:
		    			jailorIcon .setEnabled(true);
		    			break;
		    		case 5:
		    			lookoutIcon .setEnabled(true);
		    			break;
		    		case 6:
		    			mayorIcon .setEnabled(true);
		    			break;
		    		case 7:
		    			mediumIcon .setEnabled(true);
		    			break;
		    		case 8:
		    			retributionistIcon .setEnabled(true);
		    			break;
		    		case 9:
		    			sheriffIcon .setEnabled(true);
		    			break;
		    		case 10:
		    			spyIcon .setEnabled(true);
		    			break;
		    		case 11:
		    			transporterIcon .setEnabled(true);
		    			break;
		    		case 12:
		    			veteranIcon .setEnabled(true);
		    			break;
		    		case 13:
		    			vigilanteIcon .setEnabled(true);
		    			break;
		    		default:
		    			break;
					}
				} else if (cellValue == Boolean.FALSE){
					switch(celly){
					case 0:
		    			bodyguardIcon.setEnabled(false);
		    			break;
		    		case 1:
		    			doctorIcon.setEnabled(false);
		    			break;
		    		case 2:
		    			escortIcon .setEnabled(false);
		    			break;
		    		case 3:
		    			investigatorIcon .setEnabled(false);
		    			break;
		    		case 4:
		    			jailorIcon .setEnabled(false);
		    			break;
		    		case 5:
		    			lookoutIcon .setEnabled(false);
		    			break;
		    		case 6:
		    			mayorIcon .setEnabled(false);
		    			break;
		    		case 7:
		    			mediumIcon .setEnabled(false);
		    			break;
		    		case 8:
		    			retributionistIcon .setEnabled(false);
		    			break;
		    		case 9:
		    			sheriffIcon .setEnabled(false);
		    			break;
		    		case 10:
		    			spyIcon .setEnabled(false);
		    			break;
		    		case 11:
		    			transporterIcon .setEnabled(false);
		    			break;
		    		case 12:
		    			veteranIcon .setEnabled(false);
		    			break;
		    		case 13:
		    			vigilanteIcon .setEnabled(false);
		    			break;
		    		default:
		    			break;
					}
				}
			}
	    });
		
		townTable.addMouseListener(new MouseAdapter() {
//			  public void mouseClicked(MouseEvent e) {
//			    if (e.getClickCount() == 1) {
//			      JTable target = (JTable)e.getSource();
//			      int row = target.getSelectedRow();
//			      int column = target.getSelectedColumn();
//			      if (column == 0 && row == 1){
//			    	  roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townBodyguard.PNG")));
//			      }
//			    }
//			  }
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
			    	int column = target.getSelectedColumn();
			    	switch(column){
				    	case 0:
				    		switch(row){
					    		case 0:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townBodyguard.PNG")));
					    			break;
					    		case 1:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townDoctor.PNG")));
					    			break;
					    		case 2:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townEscort.PNG")));
					    			break;
					    		case 3:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townInvestigator.PNG")));
					    			break;
					    		case 4:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townJailor.PNG")));
					    			break;
					    		case 5:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townLookout.PNG")));
					    			break;
					    		case 6:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townMayor.PNG")));
					    			break;
					    		case 7:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townMedium.PNG")));
					    			break;
					    		case 8:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townRetributionist.PNG")));
					    			break;
					    		case 9:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townSheriff.PNG")));
					    			break;
					    		case 10:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townSpy.PNG")));
					    			break;
					    		case 11:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townTransporter.PNG")));
					    			break;
					    		case 12:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townVeteran.PNG")));
					    			break;
					    		case 13:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townVigilante.PNG")));
					    			break;
					    		default:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/infoScreen.PNG")));
					    			break;
				    		}
				    		break;
				    		
				    	case 1:
				    		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				    		switch(row){
					    		case 0:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townBodyguard.PNG")));
					    			break;
					    		case 1:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townDoctor.PNG")));
					    			break;
					    		case 2:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townEscort.PNG")));
					    			break;
					    		case 3:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townInvestigator.PNG")));
					    			break;
					    		case 4:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townJailor.PNG")));
					    			break;
					    		case 5:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townLookout.PNG")));
					    			break;
					    		case 6:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townMayor.PNG")));
					    			break;
					    		case 7:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townMedium.PNG")));
					    			break;
					    		case 8:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townRetributionist.PNG")));
					    			break;
					    		case 9:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townSheriff.PNG")));
					    			break;
					    		case 10:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townSpy.PNG")));
					    			break;
					    		case 11:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townTransporter.PNG")));
					    			break;
					    		case 12:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townVeteran.PNG")));
					    			break;
					    		case 13:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/townVigilante.PNG")));
					    			break;
					    		default:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/infoScreen.PNG")));
					    			break;
				    		}
				    		break;
				    }
			    }
			}
			});
		
		JScrollPane mafiaScrollPane = new JScrollPane();
		rolesTabbedPane.addTab("Mafia", null, mafiaScrollPane, null);
		
		mafiaTable = new JTable();
		mafiaTable.setRowSelectionAllowed(false);
		mafiaTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Blackmailer", "Mafia Support", Boolean.FALSE},
				{"Consigliere", "Mafia Support", Boolean.FALSE},
				{"Consort", "Mafia Support", Boolean.FALSE},
				{"Disguiser", "Mafia Deception", Boolean.FALSE},
				{"Framer", "Mafia Deception", Boolean.FALSE},
				{"Godfather", "Mafia Killing", Boolean.FALSE},
				{"Janitor", "Mafia Deception", Boolean.FALSE},
				{"Mafioso", "Mafia Killing", Boolean.FALSE},
			},
			new String[] {
				"Mafia Roles", "Type", "Active"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, Boolean.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		mafiaTable.getColumnModel().getColumn(0).setResizable(false);
		mafiaTable.getColumnModel().getColumn(1).setResizable(false);
		mafiaTable.getColumnModel().getColumn(1).setPreferredWidth(95);
		mafiaTable.getColumnModel().getColumn(2).setResizable(false);
		mafiaScrollPane.setViewportView(mafiaTable);
		
		JScrollPane neutralScrollPane = new JScrollPane();
		rolesTabbedPane.addTab("Neutral", null, neutralScrollPane, null);
		
		neutralTable = new JTable();
		neutralTable.setRowSelectionAllowed(false);
		neutralTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Amnesiac", "Neutral Benign", Boolean.FALSE},
				{"Arsonist", "Neutral Killing", Boolean.FALSE},
				{"Executioner", "Neutral Evil", Boolean.FALSE},
				{"Jester", "Neutral Evil", Boolean.FALSE},
				{"Serial Killer", "Neutral Killing", Boolean.FALSE},
				{"Survivor", "Neutral Benign", Boolean.FALSE},
				{"Witch", "Neutral Evil", Boolean.FALSE},
				{"Werewolf", "Neutral Killing", Boolean.FALSE},
			},
			new String[] {
				"Neutral Roles", "Type", "Active"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, Boolean.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		neutralTable.getColumnModel().getColumn(0).setResizable(false);
		neutralTable.getColumnModel().getColumn(1).setResizable(false);
		neutralTable.getColumnModel().getColumn(1).setPreferredWidth(95);
		neutralTable.getColumnModel().getColumn(2).setResizable(false);
		neutralScrollPane.setViewportView(neutralTable);
		
		JPanel roleSelectionPanel = new JPanel();
		roleSelectionPanel.setBounds(10, 459, 303, 442);
		mainViewportPanel.add(roleSelectionPanel);
		roleSelectionPanel.setLayout(new BoxLayout(roleSelectionPanel, BoxLayout.X_AXIS));
		
		JScrollPane roleSelectionScrollPane = new JScrollPane();
		roleSelectionScrollPane.setViewportBorder(null);
		roleSelectionPanel.add(roleSelectionScrollPane);
		
		roleSelectionScrollPane.setViewportView(rolesSelectionGrid);
		GridBagLayout gbl_rolesSelectionGrid = new GridBagLayout();
		gbl_rolesSelectionGrid.columnWidths = new int[]{0, 0, 0};
		gbl_rolesSelectionGrid.rowHeights = new int[]{0, 0};
		gbl_rolesSelectionGrid.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_rolesSelectionGrid.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		rolesSelectionGrid.setLayout(gbl_rolesSelectionGrid);
		
	}
}
