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
import javax.swing.JOptionPane;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

import javax.swing.JSplitPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.util.Properties;

import javax.swing.JTextField;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@SuppressWarnings("unused")
public class Window {

	private static final int MAX_GAMEMODES = 10;
	private static final int NUM_ROLES = 30;
	private static final String notEnoughRoles = "You don't have enough roles selected! Add some roles or decrease the number of players!";
	private static final String tooManyRoles = "You have too many roles selected! Remove some roles or increase the number of players!";
	
	private JFrame frame;
	JFrame roleFrame = new JFrame("Role Selection");
	JPanel roleFrameSelectionPanel = new JPanel();
	JScrollPane roleSelectionScrollPane = new JScrollPane();
	
	private JTable townTable;
	private JTable mafiaTable;
	private JTable neutralTable;


	JPanel mainViewportPanel = new JPanel();
	JLabel roleInfoSheet = new JLabel("");	

	JTabbedPane rolesTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	JTabbedPane iconTabbedPane = new JTabbedPane(JTabbedPane.TOP);

	JComboBox gameModeComboBox = new JComboBox();
	JTextPane gameModeDescriptionText = new JTextPane();
	JScrollPane gameModeDescriptionScrollPane = new JScrollPane();
	JComboBox numPlayersComboBox = new JComboBox();

	JButton editModeButton = new JButton("Edit Mode");
	JButton saveModeButton = new JButton("Save Mode As...");
	JButton deleteModeButton = new JButton("Delete Mode");

	JPanel townIconPanel = new JPanel();
	JPanel mafiaIconPanel = new JPanel();
	JPanel neutralIconPanel = new JPanel();
	
	// Town Role Icons
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
	
	// Mafia Role Icons	
	JLabel blackmailerIcon = new JLabel("");
	JLabel consigliereIcon = new JLabel("");
	JLabel consortIcon = new JLabel("");
	JLabel disguiserIcon = new JLabel("");
	JLabel framerIcon = new JLabel("");
	JLabel godfatherIcon = new JLabel("");
	JLabel janitorIcon = new JLabel("");
	JLabel mafiosoIcon = new JLabel("");
	
	// Neutral Role Icons
	JLabel amnesiacIcon = new JLabel("");
	JLabel arsonistIcon = new JLabel("");
	JLabel executionerIcon = new JLabel("");
	JLabel jesterIcon = new JLabel("");
	JLabel serialKillerIcon = new JLabel("");
	JLabel survivorIcon = new JLabel("");
	JLabel witchIcon = new JLabel("");
	JLabel werewolfIcon = new JLabel("");
	
	Boolean enoughRoles = true;
	Boolean inSession = false;
	
	JButton clearAllRolesButton = new JButton("Clear All Roles");

	JScrollPane inSessionPane = new JScrollPane();
	
	@SuppressWarnings("serial")
	DefaultTableModel townTableModel = new DefaultTableModel(
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
			};
			
	@SuppressWarnings("serial")
	DefaultTableModel mafiaTableModel = new DefaultTableModel(
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
			};
	
	@SuppressWarnings("serial")
	DefaultTableModel neutralTableModel = new DefaultTableModel(
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
			};
	
	DefaultTableModel inSessionTownTable = new DefaultTableModel(
			new Object[][] {
				{"Bodyguard", "Town Protective"},
				{"Doctor", "Town Protective"},
				{"Escort", "Town Support"},
				{"Investigator", "Town Investigative"},
				{"Jailor", "Town Killing"},
				{"Lookout", "Town Investigative"},
				{"Mayor", "Town Support"},
				{"Medium", "Town Support"},
				{"Retributionist", "Town Support"},
				{"Sheriff", "Town Investigative"},
				{"Spy", "Town Investigative"},
				{"Transporter", "Town Support"},
				{"Veteran", "Town Killing"},
				{"Vigilante", "Town Killing"},
			},
			new String[] {
				"Town Roles", "Type"
			}
		);
	
	DefaultTableModel inSessionMafiaTable = new DefaultTableModel(
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
		);
	
	DefaultTableModel inSessionNeutralTable = new DefaultTableModel(
			new Object[][] {
					{"Amnesiac", "Neutral Benign"},
					{"Arsonist", "Neutral Killing"},
					{"Executioner", "Neutral Evil"},
					{"Jester", "Neutral Evil"},
					{"Serial Killer", "Neutral Killing"},
					{"Survivor", "Neutral Benign"},
					{"Witch", "Neutral Evil"},
					{"Werewolf", "Neutral Killing"},
				},
				new String[] {
					"Neutral Roles", "Type"
				}
			);
	
	private final JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JScrollPane gameSetupPane = new JScrollPane();
	private final JLabel gameModeLabel = new JLabel("Game Mode");
	private final JLabel numPlayersLabel = new JLabel("Number of Players");
	private final JSeparator separator_1 = new JSeparator();
	private JTextField bodyguardCount;
	private JTextField doctorCount;
	private JTextField escortCount;
	private JTextField investigatorCount;
	private final JTextField jailorCount = new JTextField();
	private final JTextField lookoutCount = new JTextField();
	private final JTextField mayorCount = new JTextField();
	private final JTextField mediumCount = new JTextField();
	private final JTextField retributionistCount = new JTextField();
	private final JTextField sheriffCount = new JTextField();
	private final JLabel spyLabel = new JLabel("Spy: x");
	private final JLabel transporterLabel = new JLabel("Transporter: x");
	private final JLabel veteranLabel = new JLabel("Veteran: x");
	private final JLabel vigilanteLabel = new JLabel("Vigilante: x");
	private final JLabel blackmailerLabel = new JLabel("Blackmailer: x");
	private final JLabel consigliereLabel = new JLabel("Consigliere: x");
	private final JLabel consortLabel = new JLabel("Consort: x");
	private final JLabel disguiserLabel = new JLabel("Disguiser: x");
	private final JLabel framerLabel = new JLabel("Framer: x");
	private final JLabel godfatherLabel = new JLabel("Godfather: x");
	private final JTextField spyCount = new JTextField();
	private final JTextField transporterCount = new JTextField();
	private final JTextField veteranCount = new JTextField();
	private final JTextField vigilanteCount = new JTextField();
	private final JTextField blackmailerCount = new JTextField();
	private final JTextField consigliereCount = new JTextField();
	private final JTextField consortCount = new JTextField();
	private final JTextField disguiserCount = new JTextField();
	private final JTextField framerCount = new JTextField();
	private final JTextField godfatherCount = new JTextField();
	private final JTextField amnesiacCount = new JTextField();
	private final JTextField arsonistCount = new JTextField();
	private final JTextField executionerCount = new JTextField();
	private final JTextField jesterCount = new JTextField();
	private final JTextField serialKillerCount = new JTextField();
	private final JTextField survivorCount = new JTextField();
	private final JTextField witchCount = new JTextField();
	private final JTextField werewolfCount = new JTextField();
	private final JTextField mafiosoCount = new JTextField();
	private final JTextField janitorCount = new JTextField();
	private final JLabel janitorLabel = new JLabel("Janitor: x");
	private final JLabel mafiosoLabel = new JLabel("Mafioso: x");
	private final JLabel amnesiacLabel = new JLabel("Amnesiac: x");
	private final JLabel arsonistLabel = new JLabel("Arsonist: x");
	private final JLabel executionerLabel = new JLabel("Executioner: x");
	private final JLabel jesterLabel = new JLabel("Jester: x");
	private final JLabel werewolfLabel = new JLabel("Werewolf: x");
	private final JLabel serialKillerLabel = new JLabel("Serial Killer: x");
	private final JLabel survivorLabel = new JLabel("Survivor: x");
	private final JLabel witchLabel = new JLabel("Witch: x");
	private final JSeparator separator_2 = new JSeparator();
	private final JSeparator separator_3 = new JSeparator();
	private final JLabel roleCountsLabel = new JLabel("Roles Counts");
	private final JLabel enoughRolesWarningLabel = new JLabel("*none*");
	private final JButton startGameButton = new JButton("Start Game");
	private final JPanel inSessionPanel = new JPanel();

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
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		frame = new JFrame();
		frame.setTitle("Town of Salem - Companion App");
		frame.setResizable(false);
		frame.setBounds(50, 50, 1810, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Town Icons
		bodyguardIcon.setIcon(new ImageIcon(Window.class.getResource("/window/bodyguardIcon.png")));
		bodyguardIcon.setEnabled(false);
		bodyguardIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(bodyguardIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(0);
				} else if(!bodyguardIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(0, 1);
				}
			}
		});		
		
		doctorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/doctorIcon.png")));
		doctorIcon.setEnabled(false);
		doctorIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(doctorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(1);
				} else if(!doctorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(1, 1);
				}
			}
		});
		
		escortIcon.setIcon(new ImageIcon(Window.class.getResource("/window/escortIcon.png")));
		escortIcon.setEnabled(false);
		escortIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(escortIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(2);
				} else if(!escortIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(2, 1);
				}
			}
		});
		
		investigatorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/investigatorIcon.png")));
		investigatorIcon.setEnabled(false);
		investigatorIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(investigatorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(3);
				} else if(!investigatorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(3, 1);
				}
			}
		});
		
		jailorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/jailorIcon.png")));
		jailorIcon.setEnabled(false);
		jailorIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(jailorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(4);
				} else if(!jailorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(4, 1);
				}
			}
		});
		
		lookoutIcon.setIcon(new ImageIcon(Window.class.getResource("/window/lookoutIcon.png")));
		lookoutIcon.setEnabled(false);
		lookoutIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(lookoutIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(5);
				} else if(!lookoutIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(5, 1);
				}
			}
		});
		
		mayorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/mayorIcon.png")));
		mayorIcon.setEnabled(false);
		mayorIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(mayorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(6);
				} else if(!mayorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(6, 1);
				}
			}
		});
		
		mediumIcon.setIcon(new ImageIcon(Window.class.getResource("/window/mediumIcon.png")));
		mediumIcon.setEnabled(false);
		mediumIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(mediumIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(7);
				} else if(!mediumIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(7, 1);
				}
			}
		});
		
		retributionistIcon.setIcon(new ImageIcon(Window.class.getResource("/window/retributionistIcon.png")));
		retributionistIcon.setEnabled(false);
		retributionistIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(retributionistIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(8);
				} else if(!retributionistIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(8, 1);
				}
			}
		});
		
		sheriffIcon.setIcon(new ImageIcon(Window.class.getResource("/window/sheriffIcon.png")));
		sheriffIcon.setEnabled(false);
		sheriffIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(sheriffIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(9);
				} else if(!sheriffIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(9, 1);
				}
			}
		});
		
		spyIcon.setIcon(new ImageIcon(Window.class.getResource("/window/spyIcon.png")));
		spyIcon.setEnabled(false);
		spyIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(spyIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(10);
				} else if(!spyIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(10, 1);
				}
			}
		});
		
		transporterIcon.setIcon(new ImageIcon(Window.class.getResource("/window/transporterIcon.png")));
		transporterIcon.setEnabled(false);
		transporterIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(transporterIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(11);
				} else if(!transporterIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(11, 1);
				}
			}
		});
		
		veteranIcon.setIcon(new ImageIcon(Window.class.getResource("/window/veteranIcon.png")));
		veteranIcon.setEnabled(false);
		veteranIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(veteranIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(12);
				} else if(!veteranIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(12, 1);
				}
			}
		});
		
		vigilanteIcon.setIcon(new ImageIcon(Window.class.getResource("/window/vigilanteIcon.png")));
		vigilanteIcon.setEnabled(false);
		vigilanteIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(vigilanteIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					disableRole(13);
				} else if(!vigilanteIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(0);
					enableRole(13, 1);
				}
			}
		});
		
		
		// Mafia Icons
		blackmailerIcon.setIcon(new ImageIcon(Window.class.getResource("/window/blackmailerIcon.png")));
		blackmailerIcon.setEnabled(false);
		blackmailerIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(blackmailerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(14);
				} else if(!blackmailerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(14, 1);
				}
			}
		});
		
		consigliereIcon.setIcon(new ImageIcon(Window.class.getResource("/window/consigliereIcon.png")));
		consigliereIcon.setEnabled(false);
		consigliereIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(consigliereIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(15);
				} else if(!consigliereIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(15, 1);
				}
			}
		});
		
		consortIcon.setIcon(new ImageIcon(Window.class.getResource("/window/consortIcon.png")));
		consortIcon.setEnabled(false);
		consortIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(consortIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(16);
				} else if(!consortIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(16, 1);
				}
			}
		});
		
		disguiserIcon.setIcon(new ImageIcon(Window.class.getResource("/window/disguiserIcon.png")));
		disguiserIcon.setEnabled(false);
		disguiserIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(disguiserIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(17);
				} else if(!disguiserIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(17, 1);
				}
			}
		});
		
		framerIcon.setIcon(new ImageIcon(Window.class.getResource("/window/framerIcon.png")));
		framerIcon.setEnabled(false);
		framerIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(framerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(18);
				} else if(!framerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(18, 1);
				}
			}
		});
		
		godfatherIcon.setIcon(new ImageIcon(Window.class.getResource("/window/godfatherIcon.png")));
		godfatherIcon.setEnabled(false);
		godfatherIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(godfatherIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(19);
				} else if(!godfatherIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(19, 1);
				}
			}
		});
		
		janitorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/janitorIcon.png")));
		janitorIcon.setEnabled(false);
		janitorIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(janitorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(20);
				} else if(!janitorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(20, 1);
				}
			}
		});
		
		mafiosoIcon.setIcon(new ImageIcon(Window.class.getResource("/window/mafiosoIcon.png")));
		mafiosoIcon.setEnabled(false);
		mafiosoIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(mafiosoIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					disableRole(21);
				} else if(!mafiosoIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(1);
					enableRole(21, 1);
				}
			}
		});
		
		
		// Neutral Icons
		amnesiacIcon.setIcon(new ImageIcon(Window.class.getResource("/window/amnesiacIcon.png")));
		amnesiacIcon.setEnabled(false);
		amnesiacIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(amnesiacIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(22);
				} else if(!amnesiacIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(22, 1);
				}
			}
		});
		
		arsonistIcon.setIcon(new ImageIcon(Window.class.getResource("/window/arsonistIcon.png")));
		arsonistIcon.setEnabled(false);
		arsonistIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(arsonistIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(23);
				} else if(!arsonistIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(23, 1);
				}
			}
		});
		
		executionerIcon.setIcon(new ImageIcon(Window.class.getResource("/window/executionerIcon.png")));
		executionerIcon.setEnabled(false);
		executionerIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(executionerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(24);
				} else if(!executionerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(24, 1);
				}
			}
		});
		
		jesterIcon.setIcon(new ImageIcon(Window.class.getResource("/window/jesterIcon.png")));
		jesterIcon.setEnabled(false);
		jesterIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(jesterIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(25);
				} else if(!jesterIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(25, 1);
				}
			}
		});
		
		serialKillerIcon.setIcon(new ImageIcon(Window.class.getResource("/window/serialKillerIcon.png")));
		serialKillerIcon.setEnabled(false);
		serialKillerIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(serialKillerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(26);
				} else if(!serialKillerIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(26, 1);
				}
			}
		});
		
		survivorIcon.setIcon(new ImageIcon(Window.class.getResource("/window/survivorIcon.png")));
		survivorIcon.setEnabled(false);
		survivorIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(survivorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(27);
				} else if(!survivorIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(27, 1);
				}
			}
		});
		
		witchIcon.setIcon(new ImageIcon(Window.class.getResource("/window/witchIcon.png")));
		witchIcon.setEnabled(false);
		witchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(witchIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(28);
				} else if(!witchIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(28, 1);
				}
			}
		});
		
		werewolfIcon.setIcon(new ImageIcon(Window.class.getResource("/window/werewolfIcon.png")));
		werewolfIcon.setEnabled(false);
		werewolfIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(werewolfIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					disableRole(29);
				} else if(!werewolfIcon.isEnabled()){
					rolesTabbedPane.setSelectedIndex(2);
					enableRole(29, 1);
				}
			}
		});
		
		
		
		JPanel headerPanel = new JPanel();
		frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
		
		frame.getContentPane().add(mainViewportPanel, BorderLayout.CENTER);
		mainViewportPanel.setLayout(null);
		
		JPanel rolesPanel = new JPanel();
		rolesPanel.setBounds(10, 11, 380, 295);
		mainViewportPanel.add(rolesPanel);
		rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.X_AXIS));
		
		rolesPanel.add(rolesTabbedPane);
		
		roleInfoSheet.setHorizontalAlignment(SwingConstants.CENTER);
		roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
		roleSelectionScrollPane.setViewportView(roleInfoSheet);
		
		
		// Town Table
		
		JScrollPane townScrollPane = new JScrollPane();
		townScrollPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				iconTabbedPane.setSelectedIndex(2);
			}
		});
		rolesTabbedPane.addTab("Town", null, townScrollPane, null);
		
		townTable = new JTable();
		townTable.setBackground(new Color(143, 188, 143));
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
			Class[] columnTypes = new Class[] {
				String.class, String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
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
				iconTabbedPane.setSelectedIndex(0);
				
				if (cellValue == Boolean.TRUE){
					switch(celly){
		    		case 0:
		    			bodyguardIcon.setEnabled(true);
		    			break;
		    		case 1:
		    			doctorIcon.setEnabled(true);
		    			break;
		    		case 2:
		    			escortIcon.setEnabled(true);
		    			break;
		    		case 3:
		    			investigatorIcon.setEnabled(true);
		    			break;
		    		case 4:
		    			jailorIcon.setEnabled(true);
		    			break;
		    		case 5:
		    			lookoutIcon.setEnabled(true);
		    			break;
		    		case 6:
		    			mayorIcon.setEnabled(true);
		    			break;
		    		case 7:
		    			mediumIcon.setEnabled(true);
		    			break;
		    		case 8:
		    			retributionistIcon.setEnabled(true);
		    			break;
		    		case 9:
		    			sheriffIcon.setEnabled(true);
		    			break;
		    		case 10:
		    			spyIcon.setEnabled(true);
		    			break;
		    		case 11:
		    			transporterIcon.setEnabled(true);
		    			break;
		    		case 12:
		    			veteranIcon.setEnabled(true);
		    			break;
		    		case 13:
		    			vigilanteIcon.setEnabled(true);
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
		    			escortIcon.setEnabled(false);
		    			break;
		    		case 3:
		    			investigatorIcon.setEnabled(false);
		    			break;
		    		case 4:
		    			jailorIcon.setEnabled(false);
		    			break;
		    		case 5:
		    			lookoutIcon.setEnabled(false);
		    			break;
		    		case 6:
		    			mayorIcon.setEnabled(false);
		    			break;
		    		case 7:
		    			mediumIcon.setEnabled(false);
		    			break;
		    		case 8:
		    			retributionistIcon.setEnabled(false);
		    			break;
		    		case 9:
		    			sheriffIcon.setEnabled(false);
		    			break;
		    		case 10:
		    			spyIcon.setEnabled(false);
		    			break;
		    		case 11:
		    			transporterIcon.setEnabled(false);
		    			break;
		    		case 12:
		    			veteranIcon.setEnabled(false);
		    			break;
		    		case 13:
		    			vigilanteIcon.setEnabled(false);
		    			break;
		    		default:
		    			break;
					}
				}
			}
	    });
		
		townTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
			    	int column = target.getSelectedColumn();
			    	iconTabbedPane.setSelectedIndex(0);
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
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
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
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
					    			break;
				    		}
				    		break;
				    }
			    }
			}
		});
		
		// Mafia Table
		
		JScrollPane mafiaScrollPane = new JScrollPane();
		mafiaScrollPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				iconTabbedPane.setSelectedIndex(1);
			}
		});
		rolesTabbedPane.addTab("Mafia", null, mafiaScrollPane, null);
		
		mafiaTable = new JTable();
		mafiaTable.setBackground(new Color(188, 143, 143));
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
			Class[] columnTypes = new Class[] {
				String.class, String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		mafiaTable.getColumnModel().getColumn(0).setResizable(false);
		mafiaTable.getColumnModel().getColumn(1).setResizable(false);
		mafiaTable.getColumnModel().getColumn(1).setPreferredWidth(95);
		mafiaTable.getColumnModel().getColumn(2).setResizable(false);
		mafiaScrollPane.setViewportView(mafiaTable);
		
		mafiaTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int cellx = e.getColumn();
				int celly = e.getFirstRow();
				Boolean cellValue = (Boolean) mafiaTable.getValueAt(celly, cellx);
				iconTabbedPane.setSelectedIndex(1);
				
				if (cellValue == Boolean.TRUE){
					switch(celly){
		    		case 0:
		    			blackmailerIcon.setEnabled(true);
		    			break;
		    		case 1:
		    			consigliereIcon.setEnabled(true);
		    			break;
		    		case 2:
		    			consortIcon.setEnabled(true);
		    			break;
		    		case 3:
		    			disguiserIcon.setEnabled(true);
		    			break;
		    		case 4:
		    			framerIcon.setEnabled(true);
		    			break;
		    		case 5:
		    			godfatherIcon.setEnabled(true);
		    			break;
		    		case 6:
		    			janitorIcon.setEnabled(true);
		    			break;
		    		case 7:
		    			mafiosoIcon.setEnabled(true);
		    			break;
		    		default:
		    			break;
					}
				} else if (cellValue == Boolean.FALSE){
					switch(celly){
					case 0:
		    			blackmailerIcon.setEnabled(false);
		    			break;
		    		case 1:
		    			consigliereIcon.setEnabled(false);
		    			break;
		    		case 2:
		    			consortIcon.setEnabled(false);
		    			break;
		    		case 3:
		    			disguiserIcon.setEnabled(false);
		    			break;
		    		case 4:
		    			framerIcon.setEnabled(false);
		    			break;
		    		case 5:
		    			godfatherIcon.setEnabled(false);
		    			break;
		    		case 6:
		    			janitorIcon.setEnabled(false);
		    			break;
		    		case 7:
		    			mafiosoIcon.setEnabled(false);
		    			break;
		    		default:
		    			break;
					}
				}
			}
	    });
		
		mafiaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
			    	int column = target.getSelectedColumn();
			    	iconTabbedPane.setSelectedIndex(1);
			    	switch(column){
				    	case 0:
				    		switch(row){
					    		case 0:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaBlackmailer.PNG")));
					    			break;
					    		case 1:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaConsigliere.PNG")));
					    			break;
					    		case 2:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaConsort.PNG")));
					    			break;
					    		case 3:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaDisguiser.PNG")));
					    			break;
					    		case 4:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaFramer.PNG")));
					    			break;
					    		case 5:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaGodfather.PNG")));
					    			break;
					    		case 6:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaJanitor.PNG")));
					    			break;
					    		case 7:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaMafioso.PNG")));
					    			break;
					    		default:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
					    			break;
				    		}
				    		break;
				    		
				    	case 1:
				    		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				    		switch(row){
				    		case 0:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaBlackmailer.PNG")));
				    			break;
				    		case 1:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaConsigliere.PNG")));
				    			break;
				    		case 2:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaConsort.PNG")));
				    			break;
				    		case 3:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaDisguiser.PNG")));
				    			break;
				    		case 4:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaFramer.PNG")));
				    			break;
				    		case 5:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaGodfather.PNG")));
				    			break;
				    		case 6:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaJanitor.PNG")));
				    			break;
				    		case 7:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/mafiaMafioso.PNG")));
				    			break;
				    		default:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
				    			break;
				    		}
				    		break;
				    }
			    }
			}
		});
		
		// Neutral Table

		JScrollPane neutralScrollPane = new JScrollPane();
		neutralScrollPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				iconTabbedPane.setSelectedIndex(2);
			}
		});
		rolesTabbedPane.addTab("Neutral", null, neutralScrollPane, null);
		
		neutralTable = new JTable();
		neutralTable.setBackground(new Color(169, 169, 169));
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
			Class[] columnTypes = new Class[] {
				String.class, String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		neutralTable.getColumnModel().getColumn(0).setResizable(false);
		neutralTable.getColumnModel().getColumn(1).setResizable(false);
		neutralTable.getColumnModel().getColumn(1).setPreferredWidth(95);
		neutralTable.getColumnModel().getColumn(2).setResizable(false);
		neutralScrollPane.setViewportView(neutralTable);
		
		neutralTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int cellx = e.getColumn();
				int celly = e.getFirstRow();
				Boolean cellValue = (Boolean) neutralTable.getValueAt(celly, cellx);
				iconTabbedPane.setSelectedIndex(2);
				
				if (cellValue == Boolean.TRUE){
					switch(celly){
		    		case 0:
		    			amnesiacIcon.setEnabled(true);
		    			break;
		    		case 1:
		    			arsonistIcon.setEnabled(true);
		    			break;
		    		case 2:
		    			executionerIcon.setEnabled(true);
		    			break;
		    		case 3:
		    			jesterIcon.setEnabled(true);
		    			break;
		    		case 4:
		    			serialKillerIcon.setEnabled(true);
		    			break;
		    		case 5:
		    			survivorIcon.setEnabled(true);
		    			break;
		    		case 6:
		    			witchIcon.setEnabled(true);
		    			break;
		    		case 7:
		    			werewolfIcon.setEnabled(true);
		    			break;
		    		default:
		    			break;
					}
				} else if (cellValue == Boolean.FALSE){
					switch(celly){
					case 0:
		    			amnesiacIcon.setEnabled(false);
		    			break;
		    		case 1:
		    			arsonistIcon.setEnabled(false);
		    			break;
		    		case 2:
		    			executionerIcon.setEnabled(false);
		    			break;
		    		case 3:
		    			jesterIcon.setEnabled(false);
		    			break;
		    		case 4:
		    			serialKillerIcon.setEnabled(false);
		    			break;
		    		case 5:
		    			survivorIcon.setEnabled(false);
		    			break;
		    		case 6:
		    			witchIcon.setEnabled(false);
		    			break;
		    		case 7:
		    			werewolfIcon.setEnabled(false);
		    			break;
		    		default:
		    			break;
					}
				}
			}
	    });
		
		neutralTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
			    	int column = target.getSelectedColumn();
			    	iconTabbedPane.setSelectedIndex(2);
			    	switch(column){
				    	case 0:
				    		switch(row){
					    		case 0:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralAmnesiac.PNG")));
					    			break;
					    		case 1:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralArsonist.PNG")));
					    			break;
					    		case 2:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralExecutioner.PNG")));
					    			break;
					    		case 3:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralJester.PNG")));
					    			break;
					    		case 4:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralSerialKiller.PNG")));
					    			break;
					    		case 5:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralSurvivor.PNG")));
					    			break;
					    		case 6:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralWitch.PNG")));
					    			break;
					    		case 7:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralWerewolf.PNG")));
					    			break;
					    		default:
					    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
					    			break;
				    		}
				    		break;
				    		
				    	case 1:
				    		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				    		switch(row){
				    		case 0:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralAmnesiac.PNG")));
				    			break;
				    		case 1:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralArsonist.PNG")));
				    			break;
				    		case 2:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralExecutioner.PNG")));
				    			break;
				    		case 3:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralJester.PNG")));
				    			break;
				    		case 4:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralSerialKiller.PNG")));
				    			break;
				    		case 5:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralSurvivor.PNG")));
				    			break;
				    		case 6:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralWitch.PNG")));
				    			break;
				    		case 7:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/neutralWerewolf.PNG")));
				    			break;
				    		default:
				    			roleInfoSheet.setIcon(new ImageIcon(Window.class.getResource("/window/CosmeTura.jpg")));
				    			break;
				    		}
				    		break;
				    }
			    }
			}
		});
		
		JPanel roleSelectionPanel = new JPanel();
		roleSelectionPanel.setBounds(10, 318, 380, 583);
		mainViewportPanel.add(roleSelectionPanel);
		roleSelectionPanel.setLayout(new BoxLayout(roleSelectionPanel, BoxLayout.X_AXIS));
		
		roleSelectionScrollPane.setViewportBorder(null);
		roleSelectionPanel.add(roleSelectionScrollPane);
		
		JPanel iconPanel = new JPanel();
		iconPanel.setBounds(1288, 11, 500, 890);
		mainViewportPanel.add(iconPanel);
		iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
		
		iconPanel.add(iconTabbedPane);

		ChangeListener rolesTabListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if(sourceTabbedPane.getTitleAt(index).equals("Town")){
					iconTabbedPane.setSelectedIndex(index);
				} else if(sourceTabbedPane.getTitleAt(index).equals("Mafia")){
					iconTabbedPane.setSelectedIndex(index);
				} else if(sourceTabbedPane.getTitleAt(index).equals("Neutral")){
					iconTabbedPane.setSelectedIndex(index);
				}
			}
		};
		
		rolesTabbedPane.addChangeListener(rolesTabListener);
		
		ChangeListener iconTabListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if(sourceTabbedPane.getTitleAt(index).equals("Town")){
					rolesTabbedPane.setSelectedIndex(index);
				} else if(sourceTabbedPane.getTitleAt(index).equals("Mafia")){
					rolesTabbedPane.setSelectedIndex(index);
				} else if(sourceTabbedPane.getTitleAt(index).equals("Neutral")){
					rolesTabbedPane.setSelectedIndex(index);
				}
			}
		};
		
		iconTabbedPane.addChangeListener(iconTabListener);
		townIconPanel.setBackground(new Color(143, 188, 143));
		
		iconTabbedPane.addTab("Town", null, townIconPanel, null);
		townIconPanel.setPreferredSize(new Dimension(480,960));
		townIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		townIconPanel.add(bodyguardIcon);
		townIconPanel.add(doctorIcon);
		townIconPanel.add(escortIcon);
		townIconPanel.add(investigatorIcon);
		townIconPanel.add(jailorIcon);
		townIconPanel.add(lookoutIcon);
		townIconPanel.add(mayorIcon);
		townIconPanel.add(mediumIcon);
		townIconPanel.add(retributionistIcon);
		townIconPanel.add(sheriffIcon);
		townIconPanel.add(spyIcon);
		townIconPanel.add(transporterIcon);
		townIconPanel.add(veteranIcon);
		townIconPanel.add(vigilanteIcon);
		mafiaIconPanel.setBackground(new Color(188, 143, 143));
		
		iconTabbedPane.addTab("Mafia", null, mafiaIconPanel, null);
		mafiaIconPanel.setPreferredSize(new Dimension(480,960));
		mafiaIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		mafiaIconPanel.add(blackmailerIcon);
		mafiaIconPanel.add(consigliereIcon);
		mafiaIconPanel.add(consortIcon);
		mafiaIconPanel.add(disguiserIcon);
		mafiaIconPanel.add(framerIcon);
		mafiaIconPanel.add(godfatherIcon);
		mafiaIconPanel.add(janitorIcon);
		mafiaIconPanel.add(mafiosoIcon);
		neutralIconPanel.setBackground(new Color(169, 169, 169));
		
		iconTabbedPane.addTab("Neutral", null, neutralIconPanel, null);
		neutralIconPanel.setPreferredSize(new Dimension(480,960));
		neutralIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		neutralIconPanel.add(amnesiacIcon);
		neutralIconPanel.add(arsonistIcon);
		neutralIconPanel.add(executionerIcon);
		neutralIconPanel.add(jesterIcon);
		neutralIconPanel.add(serialKillerIcon);
		neutralIconPanel.add(survivorIcon);
		neutralIconPanel.add(witchIcon);
		neutralIconPanel.add(werewolfIcon);
		mainTabbedPane.setBounds(402, 6, 874, 900);
		
		mainViewportPanel.add(mainTabbedPane);
		
		final JScrollPane mainInfoPane = new JScrollPane();
		mainTabbedPane.addTab("Main", null, mainInfoPane, null);
		
		JLabel viewportMain = new JLabel("");
		viewportMain.setHorizontalAlignment(SwingConstants.CENTER);
		viewportMain.setIcon(new ImageIcon(Window.class.getResource("/window/infoScreen.PNG")));
		mainInfoPane.setViewportView(viewportMain);
		
		mainTabbedPane.addTab("Game Setup", null, gameSetupPane, null);
		
		JPanel setupPanel = new JPanel();
		gameSetupPane.setViewportView(setupPanel);
		setupPanel.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setBounds(24, 24, 820, 818);
		setupPanel.add(splitPane);
		splitPane.setEnabled(false);
		
		JLabel setupTitleLabel = new JLabel(" Game Setup");
		splitPane.setLeftComponent(setupTitleLabel);
		setupTitleLabel.setForeground(new Color(139, 69, 19));
		setupTitleLabel.setIcon(new ImageIcon(Window.class.getResource("/window/townOfSalemTitle.png")));
		setupTitleLabel.setBackground(new Color(128, 128, 128));
		setupTitleLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 38));
		
		JPanel setupDataPanel = new JPanel();
		setupDataPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane.setRightComponent(setupDataPanel);
		setupDataPanel.setLayout(null);
		gameModeLabel.setBounds(32, 32, 108, 23);
		gameModeLabel.setForeground(new Color(139, 69, 19));
		gameModeLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		setupDataPanel.add(gameModeLabel);
		
		gameModeComboBox.setMaximumRowCount(100);
		List<String> gamemodes = new ArrayList<String>();
		String[] gameModeComboBoxModel = new String[]{};
				
		Properties gamemodesprop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("gamemodes.properties");
	 
			// load a properties file
			gamemodesprop.load(input);
	 
			// Get the attributes properties
			for(Integer i = 0; i <= MAX_GAMEMODES; i++){
				String propertyValue = null;
				propertyValue = gamemodesprop.getProperty(i.toString());
				
				if(propertyValue != null){
					gamemodes.add(propertyValue);
				}
			}
			
			gameModeComboBoxModel = gamemodes.toArray(new String[gamemodes.size()]);
			
//			String name = gamemodesprop.getProperty("name");
//			String description = gamemodesprop.getProperty("description");
//			gameModeDescriptionText.setText(description);
//			String numPlayers = gamemodesprop.getProperty("numPlayers");
//			numPlayersComboBox.setSelectedItem(numPlayers);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(gamemodes == null){
			gameModeComboBox.setModel(new DefaultComboBoxModel(new String[] {"All Any", "Classic", "Custom", "Vigilantics"}));
		} else {
			gameModeComboBox.setModel(new DefaultComboBoxModel(gameModeComboBoxModel));
		}
		
		gameModeComboBox.setBounds(152, 33, 131, 26);
//		gameModeComboBox.setSelectedItem("Classic");
		gameModeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				String mode = gameModeComboBox.getSelectedItem().toString();
				setupGameMode(mode);
			}
		});		
		setupDataPanel.add(gameModeComboBox);
		
		
		
		Properties sortprop = new Properties();
		OutputStream sortoutput = null;
			
		try {
			 
			sortoutput = new FileOutputStream("temp.properties");
			
			for(Integer i = 0; i < gameModeComboBoxModel.length; i++){
				String propertyValue = null;
				propertyValue = gameModeComboBoxModel[i];
				
				if(propertyValue != null){
					sortprop.setProperty(i.toString(), propertyValue);
				}
			}
	 	 
			// save properties to project root folder
			sortprop.store(sortoutput, null);
	 
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (sortoutput != null) {
				try {
					sortoutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
		
		File oldFile = new File("gamemodes.properties");
		File newFile = new File("temp.properties");
		
		try {
			copyFileUsingStream(newFile, oldFile);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		newFile.delete();
		
		
		
		JButton editModeButton = new JButton("Edit Mode");
		editModeButton.setBounds(295, 32, 90, 28);
		editModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	gameModeComboBox.setSelectedItem("Custom");
            	clearAllRolesButton.setEnabled(true);
            }
        });
		setupDataPanel.add(editModeButton);
		
		
		saveModeButton.setBounds(497, 32, 114, 28);
		saveModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String gameModeName = JOptionPane.showInputDialog(frame, "Name your game mode:", null);
				String gameModeDescription = JOptionPane.showInputDialog(frame, "Describe your game mode:", null);
				String numPlayers = numPlayersComboBox.getSelectedItem().toString();
				if(gameModeName != null)
					try {
						saveGameMode(gameModeName, gameModeDescription, numPlayers);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		setupDataPanel.add(saveModeButton);
		
		deleteModeButton.setBounds(623, 32, 97, 28);
		deleteModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	Object[] options = { "Delete", "Cancel" };
            	int choice = JOptionPane.showOptionDialog(frame, "Are you sure you want to delete this Game Mode?", "Warning",
            	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            	null, options, options[0]);
            	
            	if(choice == 0){
	            	try {
						deleteGameMode(gameModeComboBox.getSelectedItem().toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
            	}
            }
        });
		setupDataPanel.add(deleteModeButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 67, 748, 2);
		setupDataPanel.add(separator);
		
		gameModeDescriptionScrollPane.setBounds(32, 81, 748, 116);
		setupDataPanel.add(gameModeDescriptionScrollPane);
		
		gameModeDescriptionScrollPane.setViewportView(gameModeDescriptionText);
		gameModeDescriptionText.setBackground(new Color(192, 192, 192));
		gameModeDescriptionText.setEditable(false);
		gameModeDescriptionText.setFont(new Font("Verdana", Font.PLAIN, 12));
		gameModeDescriptionText.setText("Classic is the simplest form of the game; the roles are predetermined. This mode is geared towards newer players so they can learn roles without them changing every other game.\r\n\r\nThe amount of roles depend on the number of players. The corresponding number will be the first roles that are used. For example: if there are 10 players only the first 10 roles will be used.");
		
		numPlayersComboBox.setModel(new DefaultComboBoxModel(new String[] {"8", "9", "10", "11", "12", "13", "14", "15"}));
		numPlayersComboBox.setMaximumRowCount(15);
		numPlayersComboBox.setBounds(166, 209, 47, 26);
		numPlayersComboBox.setSelectedIndex(2);
		numPlayersComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				//String numPlayersString = numPlayersComboBox.getSelectedItem().toString();
				//int numPlayers = Integer.parseInt(numPlayersString); 
				setupGameMode(gameModeComboBox.getSelectedItem().toString());
				calculateEnoughRoles();
			}
		});
		setupDataPanel.add(numPlayersComboBox);
		
		numPlayersLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		numPlayersLabel.setBounds(32, 209, 122, 26);
		setupDataPanel.add(numPlayersLabel);
		separator_1.setBounds(32, 281, 748, 2);
		
		setupDataPanel.add(separator_1);
		
		bodyguardCount = new JTextField();
		bodyguardCount.setText("0");
		bodyguardCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bodyguardCount.getText().equals("")){
					bodyguardCount.setText("0");
				}
				Integer amount = Integer.parseInt(bodyguardCount.getText().toString());
				enableRole(0, amount);
			}
		});
		bodyguardCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(bodyguardCount.getText().equals("")){
					bodyguardCount.setText("0");
				}
				int amount = Integer.parseInt(bodyguardCount.getText().toString());
				enableRole(0, amount);
			}
		});
		bodyguardCount.setBounds(134, 344, 20, 28);
		setupDataPanel.add(bodyguardCount);
		bodyguardCount.setColumns(10);
		
		JLabel bodyguardLabel = new JLabel("Bodyguard: x");
		bodyguardLabel.setForeground(new Color(0, 128, 0));
		bodyguardLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		bodyguardLabel.setBounds(32, 344, 90, 26);
		setupDataPanel.add(bodyguardLabel);
		
		JLabel doctorLabel = new JLabel("Doctor: x");
		doctorLabel.setForeground(new Color(0, 128, 0));
		doctorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		doctorLabel.setBounds(32, 382, 90, 26);
		setupDataPanel.add(doctorLabel);
		
		JLabel escortLabel = new JLabel("Escort: x");
		escortLabel.setForeground(new Color(0, 128, 0));
		escortLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		escortLabel.setBounds(32, 420, 90, 26);
		setupDataPanel.add(escortLabel);
		
		JLabel investigatorLabel = new JLabel("Investigator: x");
		investigatorLabel.setForeground(new Color(0, 128, 0));
		investigatorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		investigatorLabel.setBounds(32, 458, 97, 26);
		setupDataPanel.add(investigatorLabel);
		
		JLabel jailorLabel = new JLabel("Jailor: x");
		jailorLabel.setForeground(new Color(0, 128, 0));
		jailorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		jailorLabel.setBounds(32, 496, 90, 26);
		setupDataPanel.add(jailorLabel);
		
		JLabel lookoutLabel = new JLabel("Lookout: x");
		lookoutLabel.setForeground(new Color(0, 128, 0));
		lookoutLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		lookoutLabel.setBounds(32, 534, 90, 26);
		setupDataPanel.add(lookoutLabel);
		
		JLabel mayorLabel = new JLabel("Mayor: x");
		mayorLabel.setForeground(new Color(0, 128, 0));
		mayorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		mayorLabel.setBounds(32, 572, 90, 26);
		setupDataPanel.add(mayorLabel);
		
		JLabel mediumLabel = new JLabel("Medium: x");
		mediumLabel.setForeground(new Color(0, 128, 0));
		mediumLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		mediumLabel.setBounds(32, 610, 90, 26);
		setupDataPanel.add(mediumLabel);
		
		JLabel retributionistLabel = new JLabel("Retributionist: x");
		retributionistLabel.setForeground(new Color(0, 128, 0));
		retributionistLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		retributionistLabel.setBounds(32, 648, 108, 26);
		setupDataPanel.add(retributionistLabel);
		
		JLabel sheriffLabel = new JLabel("Sheriff: x");
		sheriffLabel.setForeground(new Color(0, 128, 0));
		sheriffLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		sheriffLabel.setBounds(32, 686, 90, 26);
		setupDataPanel.add(sheriffLabel);
		
		doctorCount = new JTextField();
		doctorCount.setText("0");
		doctorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(doctorCount.getText().equals("")){
					doctorCount.setText("0");
				}
				Integer amount = Integer.parseInt(doctorCount.getText().toString());
				enableRole(1, amount);
			}
		});
		doctorCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(doctorCount.getText().equals("")){
					doctorCount.setText("0");
				}
				int amount = Integer.parseInt(doctorCount.getText().toString());
				enableRole(1, amount);
			}
		});
		doctorCount.setColumns(10);
		doctorCount.setBounds(134, 382, 20, 28);
		setupDataPanel.add(doctorCount);
		
		escortCount = new JTextField();
		escortCount.setText("0");
		escortCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(escortCount.getText().equals("")){
					escortCount.setText("0");
				}
				Integer amount = Integer.parseInt(escortCount.getText().toString());
				enableRole(2, amount);
			}
		});
		escortCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(escortCount.getText().equals("")){
					escortCount.setText("0");
				}
				int amount = Integer.parseInt(escortCount.getText().toString());
				enableRole(2, amount);
			}
		});
		escortCount.setColumns(10);
		escortCount.setBounds(134, 420, 20, 28);
		setupDataPanel.add(escortCount);
		
		investigatorCount = new JTextField();
		investigatorCount.setText("0");
		investigatorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(investigatorCount.getText().equals("")){
					investigatorCount.setText("0");
				}
				Integer amount = Integer.parseInt(investigatorCount.getText().toString());
				enableRole(3, amount);
			}
		});
		investigatorCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(investigatorCount.getText().equals("")){
					investigatorCount.setText("0");
				}
				int amount = Integer.parseInt(investigatorCount.getText().toString());
				enableRole(3, amount);
			}
		});
		investigatorCount.setColumns(10);
		investigatorCount.setBounds(134, 458, 20, 28);
		setupDataPanel.add(investigatorCount);
		jailorCount.setText("0");
		jailorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jailorCount.getText().equals("")){
					jailorCount.setText("0");
				}
				Integer amount = Integer.parseInt(jailorCount.getText().toString());
				enableRole(4, amount);
			}
		});
		jailorCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(jailorCount.getText().equals("")){
					jailorCount.setText("0");
				}
				int amount = Integer.parseInt(jailorCount.getText().toString());
				enableRole(4, amount);
			}
		});
		jailorCount.setColumns(10);
		jailorCount.setBounds(134, 496, 20, 28);
		
		setupDataPanel.add(jailorCount);
		lookoutCount.setText("0");
		lookoutCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lookoutCount.getText().equals("")){
					lookoutCount.setText("0");
				}
				Integer amount = Integer.parseInt(lookoutCount.getText().toString());
				enableRole(5, amount);
			}
		});
		lookoutCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(lookoutCount.getText().equals("")){
					lookoutCount.setText("0");
				}
				int amount = Integer.parseInt(lookoutCount.getText().toString());
				enableRole(5, amount);
			}
		});
		lookoutCount.setColumns(10);
		lookoutCount.setBounds(134, 534, 20, 28);
		
		setupDataPanel.add(lookoutCount);
		mayorCount.setText("0");
		mayorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mayorCount.getText().equals("")){
					mayorCount.setText("0");
				}
				Integer amount = Integer.parseInt(mayorCount.getText().toString());
				enableRole(6, amount);
			}
		});
		mayorCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(mayorCount.getText().equals("")){
					mayorCount.setText("0");
				}
				int amount = Integer.parseInt(mayorCount.getText().toString());
				enableRole(6, amount);
			}
		});
		mayorCount.setColumns(10);
		mayorCount.setBounds(134, 572, 20, 28);
		
		setupDataPanel.add(mayorCount);
		mediumCount.setText("0");
		mediumCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mediumCount.getText().equals("")){
					mediumCount.setText("0");
				}
				Integer amount = Integer.parseInt(mediumCount.getText().toString());
				enableRole(7, amount);
			}
		});
		mediumCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(mediumCount.getText().equals("")){
					mediumCount.setText("0");
				}
				int amount = Integer.parseInt(mediumCount.getText().toString());
				enableRole(7, amount);
			}
		});
		mediumCount.setColumns(10);
		mediumCount.setBounds(134, 610, 20, 28);
		
		setupDataPanel.add(mediumCount);
		retributionistCount.setText("0");
		retributionistCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(retributionistCount.getText().equals("")){
					retributionistCount.setText("0");
				}
				Integer amount = Integer.parseInt(retributionistCount.getText().toString());
				enableRole(8, amount);
			}
		});
		retributionistCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(retributionistCount.getText().equals("")){
					retributionistCount.setText("0");
				}
				int amount = Integer.parseInt(retributionistCount.getText().toString());
				enableRole(8, amount);
			}
		});
		retributionistCount.setColumns(10);
		retributionistCount.setBounds(134, 648, 20, 28);
		
		setupDataPanel.add(retributionistCount);
		sheriffCount.setText("0");
		sheriffCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sheriffCount.getText().equals("")){
					sheriffCount.setText("0");
				}
				Integer amount = Integer.parseInt(sheriffCount.getText().toString());
				enableRole(9, amount);
			}
		});
		sheriffCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(sheriffCount.getText().equals("")){
					sheriffCount.setText("0");
				}
				int amount = Integer.parseInt(sheriffCount.getText().toString());
				enableRole(9, amount);
			}
		});
		sheriffCount.setColumns(10);
		sheriffCount.setBounds(134, 686, 20, 28);
		
		setupDataPanel.add(sheriffCount);
		spyLabel.setForeground(new Color(0, 128, 0));
		spyLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		spyLabel.setBounds(166, 343, 108, 26);
		
		setupDataPanel.add(spyLabel);
		transporterLabel.setForeground(new Color(0, 128, 0));
		transporterLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		transporterLabel.setBounds(166, 382, 108, 26);
		
		setupDataPanel.add(transporterLabel);
		veteranLabel.setForeground(new Color(0, 128, 0));
		veteranLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		veteranLabel.setBounds(166, 420, 108, 26);
		
		setupDataPanel.add(veteranLabel);
		vigilanteLabel.setForeground(new Color(0, 128, 0));
		vigilanteLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		vigilanteLabel.setBounds(166, 458, 108, 26);
		
		setupDataPanel.add(vigilanteLabel);
		blackmailerLabel.setForeground(new Color(128, 0, 0));
		blackmailerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		blackmailerLabel.setBounds(166, 496, 108, 26);
		
		setupDataPanel.add(blackmailerLabel);
		consigliereLabel.setForeground(new Color(128, 0, 0));
		consigliereLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		consigliereLabel.setBounds(166, 534, 108, 26);
		
		setupDataPanel.add(consigliereLabel);
		consortLabel.setForeground(new Color(128, 0, 0));
		consortLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		consortLabel.setBounds(166, 572, 108, 26);
		
		setupDataPanel.add(consortLabel);
		disguiserLabel.setForeground(new Color(128, 0, 0));
		disguiserLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		disguiserLabel.setBounds(166, 610, 108, 26);
		
		setupDataPanel.add(disguiserLabel);
		framerLabel.setForeground(new Color(128, 0, 0));
		framerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		framerLabel.setBounds(166, 648, 108, 26);
		
		setupDataPanel.add(framerLabel);
		godfatherLabel.setForeground(new Color(128, 0, 0));
		godfatherLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		godfatherLabel.setBounds(166, 686, 108, 26);
		
		setupDataPanel.add(godfatherLabel);
		spyCount.setText("0");
		spyCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(spyCount.getText().equals("")){
					spyCount.setText("0");
				}
				Integer amount = Integer.parseInt(spyCount.getText().toString());
				enableRole(10, amount);
			}
		});
		spyCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(spyCount.getText().equals("")){
					spyCount.setText("0");
				}
				int amount = Integer.parseInt(spyCount.getText().toString());
				enableRole(10, amount);
			}
		});
		spyCount.setColumns(10);
		spyCount.setBounds(267, 344, 20, 28);
		
		setupDataPanel.add(spyCount);
		transporterCount.setText("0");
		transporterCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(transporterCount.getText().equals("")){
					transporterCount.setText("0");
				}
				Integer amount = Integer.parseInt(transporterCount.getText().toString());
				enableRole(11, amount);
			}
		});
		transporterCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(transporterCount.getText().equals("")){
					transporterCount.setText("0");
				}
				int amount = Integer.parseInt(transporterCount.getText().toString());
				enableRole(11, amount);
			}
		});
		transporterCount.setColumns(10);
		transporterCount.setBounds(267, 382, 20, 28);
		
		setupDataPanel.add(transporterCount);
		veteranCount.setText("0");
		veteranCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(veteranCount.getText().equals("")){
					veteranCount.setText("0");
				}
				Integer amount = Integer.parseInt(veteranCount.getText().toString());
				enableRole(12, amount);
			}
		});
		veteranCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(veteranCount.getText().equals("")){
					veteranCount.setText("0");
				}
				int amount = Integer.parseInt(veteranCount.getText().toString());
				enableRole(12, amount);
			}
		});
		veteranCount.setColumns(10);
		veteranCount.setBounds(267, 419, 20, 28);
		
		setupDataPanel.add(veteranCount);
		vigilanteCount.setText("0");
		vigilanteCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(vigilanteCount.getText().equals("")){
					vigilanteCount.setText("0");
				}
				Integer amount = Integer.parseInt(vigilanteCount.getText().toString());
				enableRole(13, amount);
			}
		});
		vigilanteCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(vigilanteCount.getText().equals("")){
					vigilanteCount.setText("0");
				}
				int amount = Integer.parseInt(vigilanteCount.getText().toString());
				enableRole(13, amount);
			}
		});
		vigilanteCount.setColumns(10);
		vigilanteCount.setBounds(267, 457, 20, 28);
		
		setupDataPanel.add(vigilanteCount);
		blackmailerCount.setText("0");
		blackmailerCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(blackmailerCount.getText().equals("")){
					blackmailerCount.setText("0");
				}
				Integer amount = Integer.parseInt(blackmailerCount.getText().toString());
				enableRole(14, amount);
			}
		});
		blackmailerCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(blackmailerCount.getText().equals("")){
					blackmailerCount.setText("0");
				}
				int amount = Integer.parseInt(blackmailerCount.getText().toString());
				enableRole(14, amount);
			}
		});
		blackmailerCount.setColumns(10);
		blackmailerCount.setBounds(267, 495, 20, 28);
		
		setupDataPanel.add(blackmailerCount);
		consigliereCount.setText("0");
		consigliereCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(consigliereCount.getText().equals("")){
					consigliereCount.setText("0");
				}
				Integer amount = Integer.parseInt(consigliereCount.getText().toString());
				enableRole(15, amount);
			}
		});
		consigliereCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(consigliereCount.getText().equals("")){
					consigliereCount.setText("0");
				}
				int amount = Integer.parseInt(consigliereCount.getText().toString());
				enableRole(15, amount);
			}
		});
		consigliereCount.setColumns(10);
		consigliereCount.setBounds(267, 533, 20, 28);
		
		setupDataPanel.add(consigliereCount);
		consortCount.setText("0");
		consortCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(consortCount.getText().equals("")){
					consortCount.setText("0");
				}
				Integer amount = Integer.parseInt(consortCount.getText().toString());
				enableRole(16, amount);
			}
		});
		consortCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(consortCount.getText().equals("")){
					consortCount.setText("0");
				}
				int amount = Integer.parseInt(consortCount.getText().toString());
				enableRole(16, amount);
			}
		});
		consortCount.setColumns(10);
		consortCount.setBounds(267, 571, 20, 28);
		
		setupDataPanel.add(consortCount);
		disguiserCount.setText("0");
		disguiserCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(disguiserCount.getText().equals("")){
					disguiserCount.setText("0");
				}
				Integer amount = Integer.parseInt(disguiserCount.getText().toString());
				enableRole(17, amount);
			}
		});
		disguiserCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(disguiserCount.getText().equals("")){
					disguiserCount.setText("0");
				}
				int amount = Integer.parseInt(disguiserCount.getText().toString());
				enableRole(17, amount);
			}
		});
		disguiserCount.setColumns(10);
		disguiserCount.setBounds(267, 609, 20, 28);
		
		setupDataPanel.add(disguiserCount);
		framerCount.setText("0");
		framerCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(framerCount.getText().equals("")){
					framerCount.setText("0");
				}
				Integer amount = Integer.parseInt(framerCount.getText().toString());
				enableRole(18, amount);
			}
		});
		framerCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(framerCount.getText().equals("")){
					framerCount.setText("0");
				}
				int amount = Integer.parseInt(framerCount.getText().toString());
				enableRole(18, amount);
			}
		});
		framerCount.setColumns(10);
		framerCount.setBounds(267, 647, 20, 28);
		
		setupDataPanel.add(framerCount);
		godfatherCount.setText("0");
		godfatherCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(godfatherCount.getText().equals("")){
					godfatherCount.setText("0");
				}
				Integer amount = Integer.parseInt(godfatherCount.getText().toString());
				enableRole(19, amount);
			}
		});
		godfatherCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(godfatherCount.getText().equals("")){
					godfatherCount.setText("0");
				}
				int amount = Integer.parseInt(godfatherCount.getText().toString());
				enableRole(19, amount);
			}
		});
		godfatherCount.setColumns(10);
		godfatherCount.setBounds(267, 685, 20, 28);
		
		setupDataPanel.add(godfatherCount);
		amnesiacCount.setText("0");
		amnesiacCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(amnesiacCount.getText().equals("")){
					amnesiacCount.setText("0");
				}
				Integer amount = Integer.parseInt(amnesiacCount.getText().toString());
				enableRole(22, amount);
			}
		});
		amnesiacCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(amnesiacCount.getText().equals("")){
					amnesiacCount.setText("0");
				}
				int amount = Integer.parseInt(amnesiacCount.getText().toString());
				enableRole(22, amount);
			}
		});
		amnesiacCount.setColumns(10);
		amnesiacCount.setBounds(402, 419, 20, 28);
		
		setupDataPanel.add(amnesiacCount);
		arsonistCount.setText("0");
		arsonistCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arsonistCount.getText().equals("")){
					arsonistCount.setText("0");
				}
				Integer amount = Integer.parseInt(arsonistCount.getText().toString());
				enableRole(23, amount);
			}
		});
		arsonistCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(arsonistCount.getText().equals("")){
					arsonistCount.setText("0");
				}
				int amount = Integer.parseInt(arsonistCount.getText().toString());
				enableRole(23, amount);
			}
		});
		arsonistCount.setColumns(10);
		arsonistCount.setBounds(402, 457, 20, 28);
		
		setupDataPanel.add(arsonistCount);
		executionerCount.setText("0");
		executionerCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(executionerCount.getText().equals("")){
					executionerCount.setText("0");
				}
				Integer amount = Integer.parseInt(executionerCount.getText().toString());
				enableRole(24, amount);
			}
		});
		executionerCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(executionerCount.getText().equals("")){
					executionerCount.setText("0");
				}
				int amount = Integer.parseInt(executionerCount.getText().toString());
				enableRole(24, amount);
			}
		});
		executionerCount.setColumns(10);
		executionerCount.setBounds(402, 495, 20, 28);
		
		setupDataPanel.add(executionerCount);
		jesterCount.setText("0");
		jesterCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jesterCount.getText().equals("")){
					jesterCount.setText("0");
				}
				Integer amount = Integer.parseInt(jesterCount.getText().toString());
				enableRole(25, amount);
			}
		});
		jesterCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(jesterCount.getText().equals("")){
					jesterCount.setText("0");
				}
				int amount = Integer.parseInt(jesterCount.getText().toString());
				enableRole(25, amount);
			}
		});
		jesterCount.setColumns(10);
		jesterCount.setBounds(402, 533, 20, 28);
		
		setupDataPanel.add(jesterCount);
		serialKillerCount.setText("0");
		serialKillerCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(serialKillerCount.getText().equals("")){
					serialKillerCount.setText("0");
				}
				Integer amount = Integer.parseInt(serialKillerCount.getText().toString());
				enableRole(26, amount);
			}
		});
		serialKillerCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(serialKillerCount.getText().equals("")){
					serialKillerCount.setText("0");
				}
				int amount = Integer.parseInt(serialKillerCount.getText().toString());
				enableRole(26, amount);
			}
		});
		serialKillerCount.setColumns(10);
		serialKillerCount.setBounds(402, 571, 20, 28);
		
		setupDataPanel.add(serialKillerCount);
		survivorCount.setText("0");
		survivorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(survivorCount.getText().equals("")){
					survivorCount.setText("0");
				}
				Integer amount = Integer.parseInt(survivorCount.getText().toString());
				enableRole(27, amount);
			}
		});
		survivorCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(survivorCount.getText().equals("")){
					survivorCount.setText("0");
				}
				int amount = Integer.parseInt(survivorCount.getText().toString());
				enableRole(27, amount);
			}
		});
		survivorCount.setColumns(10);
		survivorCount.setBounds(402, 609, 20, 28);
		
		setupDataPanel.add(survivorCount);
		witchCount.setText("0");
		witchCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(witchCount.getText().equals("")){
					witchCount.setText("0");
				}
				Integer amount = Integer.parseInt(witchCount.getText().toString());
				enableRole(28, amount);
			}
		});
		witchCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(witchCount.getText().equals("")){
					witchCount.setText("0");
				}
				int amount = Integer.parseInt(witchCount.getText().toString());
				enableRole(28, amount);
			}
		});
		witchCount.setColumns(10);
		witchCount.setBounds(402, 647, 20, 28);
		
		setupDataPanel.add(witchCount);
		werewolfCount.setText("0");
		werewolfCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(werewolfCount.getText().equals("")){
					werewolfCount.setText("0");
				}
				Integer amount = Integer.parseInt(werewolfCount.getText().toString());
				enableRole(29, amount);
			}
		});
		werewolfCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(werewolfCount.getText().equals("")){
					werewolfCount.setText("0");
				}
				int amount = Integer.parseInt(werewolfCount.getText().toString());
				enableRole(29, amount);
			}
		});
		werewolfCount.setColumns(10);
		werewolfCount.setBounds(402, 685, 20, 28);
		
		setupDataPanel.add(werewolfCount);
		mafiosoCount.setText("0");
		mafiosoCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mafiosoCount.getText().equals("")){
					mafiosoCount.setText("0");
				}
				Integer amount = Integer.parseInt(mafiosoCount.getText().toString());
				enableRole(21, amount);
			}
		});
		mafiosoCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(mafiosoCount.getText().equals("")){
					mafiosoCount.setText("0");
				}
				int amount = Integer.parseInt(mafiosoCount.getText().toString());
				enableRole(21, amount);
			}
		});
		mafiosoCount.setColumns(10);
		mafiosoCount.setBounds(402, 382, 20, 28);
		
		setupDataPanel.add(mafiosoCount);
		janitorCount.setText("0");
		janitorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(janitorCount.getText().equals("")){
					janitorCount.setText("0");
				}
				Integer amount = Integer.parseInt(janitorCount.getText().toString());
				enableRole(20, amount);
			}
		});
		janitorCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(janitorCount.getText().equals("")){
					janitorCount.setText("0");
				}
				int amount = Integer.parseInt(janitorCount.getText().toString());
				enableRole(20, amount);
			}
		});
		janitorCount.setColumns(10);
		janitorCount.setBounds(402, 344, 20, 28);
		
		setupDataPanel.add(janitorCount);
		janitorLabel.setForeground(new Color(128, 0, 0));
		janitorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		janitorLabel.setBounds(302, 344, 108, 26);
		
		setupDataPanel.add(janitorLabel);
		mafiosoLabel.setForeground(new Color(128, 0, 0));
		mafiosoLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		mafiosoLabel.setBounds(302, 382, 108, 26);
		
		setupDataPanel.add(mafiosoLabel);
		amnesiacLabel.setForeground(new Color(128, 128, 128));
		amnesiacLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		amnesiacLabel.setBounds(302, 421, 108, 26);
		
		setupDataPanel.add(amnesiacLabel);
		arsonistLabel.setForeground(Color.GRAY);
		arsonistLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		arsonistLabel.setBounds(302, 459, 108, 26);
		
		setupDataPanel.add(arsonistLabel);
		executionerLabel.setForeground(Color.GRAY);
		executionerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		executionerLabel.setBounds(302, 497, 108, 26);
		
		setupDataPanel.add(executionerLabel);
		jesterLabel.setForeground(Color.GRAY);
		jesterLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		jesterLabel.setBounds(302, 535, 108, 26);
		
		setupDataPanel.add(jesterLabel);
		werewolfLabel.setForeground(Color.GRAY);
		werewolfLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		werewolfLabel.setBounds(302, 687, 108, 26);
		
		setupDataPanel.add(werewolfLabel);
		serialKillerLabel.setForeground(Color.GRAY);
		serialKillerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		serialKillerLabel.setBounds(302, 573, 108, 26);
		
		setupDataPanel.add(serialKillerLabel);
		survivorLabel.setForeground(Color.GRAY);
		survivorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		survivorLabel.setBounds(302, 611, 108, 26);
		
		setupDataPanel.add(survivorLabel);
		witchLabel.setForeground(Color.GRAY);
		witchLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		witchLabel.setBounds(302, 649, 108, 26);
		
		setupDataPanel.add(witchLabel);
		separator_2.setBounds(32, 330, 748, 2);
		
		setupDataPanel.add(separator_2);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(434, 345, 2, 368);
		
		setupDataPanel.add(separator_3);
		roleCountsLabel.setForeground(new Color(139, 69, 19));
		roleCountsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		roleCountsLabel.setBounds(32, 295, 122, 23);
		
		setupDataPanel.add(roleCountsLabel);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(434, 295, 2, 23);
		setupDataPanel.add(separator_4);
		
		
		enoughRolesWarningLabel.setForeground(new Color(255, 0, 0));
		enoughRolesWarningLabel.setFont(new Font("Verdana", Font.ITALIC, 12));
		enoughRolesWarningLabel.setBounds(225, 214, 555, 16);
		enoughRolesWarningLabel.setText("");
		
		setupDataPanel.add(enoughRolesWarningLabel);
		
		
		clearAllRolesButton.setEnabled(false);
		clearAllRolesButton.setBounds(32, 241, 122, 28);
		clearAllRolesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableAllRoles();
			}
		});
		setupDataPanel.add(clearAllRolesButton);
				
		mainTabbedPane.addTab("In session", null, inSessionPane, null);
		mainTabbedPane.remove(inSessionPane);
		
		inSessionPane.setViewportView(inSessionPanel);
		inSessionPanel.setLayout(null);
		
		JSplitPane inSessionSplitPane = new JSplitPane();
		inSessionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		inSessionSplitPane.setEnabled(false);
		inSessionSplitPane.setBounds(24, 24, 820, 818);
		inSessionPanel.add(inSessionSplitPane);
		
		JLabel lblInSession = new JLabel(" In Session");
		lblInSession.setIcon(new ImageIcon(Window.class.getResource("/window/townOfSalemTitle.png")));
		lblInSession.setForeground(new Color(139, 69, 19));
		lblInSession.setFont(new Font("Tempus Sans ITC", Font.BOLD, 38));
		lblInSession.setBackground(Color.GRAY);
		inSessionSplitPane.setLeftComponent(lblInSession);
		
		JPanel inSessionDataPanel = new JPanel();
		inSessionDataPanel.setLayout(null);
		inSessionDataPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		inSessionSplitPane.setRightComponent(inSessionDataPanel);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(32, 67, 748, 2);
		inSessionDataPanel.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(32, 281, 748, 2);
		inSessionDataPanel.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(32, 330, 748, 2);
		inSessionDataPanel.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);
		separator_8.setBounds(434, 345, 2, 368);
		inSessionDataPanel.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setOrientation(SwingConstants.VERTICAL);
		separator_9.setBounds(434, 295, 2, 23);
		inSessionDataPanel.add(separator_9);
		
		JButton endGameButton = new JButton("End Game");
		endGameButton.setForeground(new Color(139, 69, 19));
		
		endGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inSession = false;
				mainTabbedPane.addTab("Main", null, mainInfoPane, null);
				mainTabbedPane.addTab("Game Setup", null, gameSetupPane, null);
				mainTabbedPane.setSelectedComponent(gameSetupPane);
				mainTabbedPane.remove(inSessionPane);
				townTable.setModel(townTableModel);
				mafiaTable.setModel(mafiaTableModel);
				neutralTable.setModel(neutralTableModel);
			}
		});
		endGameButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 24));
		endGameButton.setEnabled(true);
		endGameButton.setBounds(592, 652, 188, 61);
		inSessionDataPanel.add(endGameButton);
		
		gameModeComboBox.setSelectedItem("Classic");
		startGameButton.setForeground(new Color(139, 69, 19));
		
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inSession = true;
				mainTabbedPane.addTab("In session", null, inSessionPane, null);
				mainTabbedPane.setSelectedComponent(inSessionPane);
				mainTabbedPane.remove(mainInfoPane);
				mainTabbedPane.remove(gameSetupPane);
				townTable.setModel(inSessionTownTable);
				mafiaTable.setModel(inSessionMafiaTable);
				neutralTable.setModel(inSessionNeutralTable);
			}
		});
		startGameButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 24));
		startGameButton.setBounds(592, 652, 188, 61);
		startGameButton.setEnabled(true);
		
		setupDataPanel.add(startGameButton);
	}
	
	void calculateEnoughRoles(){
		int amount = 0;
		amount = 
				// Town Roles
				  Integer.parseInt(bodyguardCount.getText())
				+ Integer.parseInt(doctorCount.getText())
				+ Integer.parseInt(escortCount.getText())
				+ Integer.parseInt(investigatorCount.getText())
				+ Integer.parseInt(jailorCount.getText())
				+ Integer.parseInt(lookoutCount.getText())
				+ Integer.parseInt(mayorCount.getText())
				+ Integer.parseInt(mediumCount.getText())
				+ Integer.parseInt(retributionistCount.getText())
				+ Integer.parseInt(sheriffCount.getText())
				+ Integer.parseInt(spyCount.getText())
				+ Integer.parseInt(transporterCount.getText())
				+ Integer.parseInt(veteranCount.getText())
				+ Integer.parseInt(vigilanteCount.getText())
				// Mafia Roles
				+ Integer.parseInt(blackmailerCount.getText())
				+ Integer.parseInt(consigliereCount.getText())
				+ Integer.parseInt(consortCount.getText())
				+ Integer.parseInt(disguiserCount.getText())
				+ Integer.parseInt(framerCount.getText())
				+ Integer.parseInt(godfatherCount.getText())
				+ Integer.parseInt(janitorCount.getText())
				+ Integer.parseInt(mafiosoCount.getText())
				// Neutral Roles
				+ Integer.parseInt(amnesiacCount.getText())
				+ Integer.parseInt(arsonistCount.getText())
				+ Integer.parseInt(executionerCount.getText())
				+ Integer.parseInt(jesterCount.getText())
				+ Integer.parseInt(serialKillerCount.getText())
				+ Integer.parseInt(survivorCount.getText())
				+ Integer.parseInt(witchCount.getText())
				+ Integer.parseInt(werewolfCount.getText());
		
		if(amount == Integer.parseInt(numPlayersComboBox.getSelectedItem().toString())){
			enoughRoles = true;
			enoughRolesWarningLabel.setText("");
			if(gameModeComboBox.getSelectedItem().toString().equals("Custom")){
				saveModeButton.setEnabled(true);
				
			}
			startGameButton.setEnabled(true);
		} else if (amount < Integer.parseInt(numPlayersComboBox.getSelectedItem().toString())){
			enoughRoles = false;
			enoughRolesWarningLabel.setText(notEnoughRoles);
			saveModeButton.setEnabled(false);
			startGameButton.setEnabled(false);
		} else if (amount > Integer.parseInt(numPlayersComboBox.getSelectedItem().toString())){
			enoughRoles = false;
			enoughRolesWarningLabel.setText(tooManyRoles);
			saveModeButton.setEnabled(false);
			startGameButton.setEnabled(false);
		}
	}
	
	void disableRole(int id){
		if (!inSession) {
			switch (id) {
			// Town Roles
			case 0: // Bodyguard
				townTable.setValueAt(Boolean.FALSE, 0, 2);
				bodyguardIcon.setEnabled(false);
				bodyguardCount.setText("0");
				break;
			case 1: // Doctor
				townTable.setValueAt(Boolean.FALSE, 1, 2);
				doctorIcon.setEnabled(false);
				doctorCount.setText("0");
				break;
			case 2: // Escort
				townTable.setValueAt(Boolean.FALSE, 2, 2);
				escortIcon.setEnabled(false);
				escortCount.setText("0");
				break;
			case 3: // Investigator
				townTable.setValueAt(Boolean.FALSE, 3, 2);
				investigatorIcon.setEnabled(false);
				investigatorCount.setText("0");
				break;
			case 4: // Jailor
				townTable.setValueAt(Boolean.FALSE, 4, 2);
				jailorIcon.setEnabled(false);
				jailorCount.setText("0");
				break;
			case 5: // Lookout
				townTable.setValueAt(Boolean.FALSE, 5, 2);
				lookoutIcon.setEnabled(false);
				lookoutCount.setText("0");
				break;
			case 6: // Mayor
				townTable.setValueAt(Boolean.FALSE, 6, 2);
				mayorIcon.setEnabled(false);
				mayorCount.setText("0");
				break;
			case 7: // Medium
				townTable.setValueAt(Boolean.FALSE, 7, 2);
				mediumIcon.setEnabled(false);
				mediumCount.setText("0");
				break;
			case 8: // Retributionist
				townTable.setValueAt(Boolean.FALSE, 8, 2);
				retributionistIcon.setEnabled(false);
				retributionistCount.setText("0");
				break;
			case 9: // Sheriff
				townTable.setValueAt(Boolean.FALSE, 9, 2);
				sheriffIcon.setEnabled(false);
				sheriffCount.setText("0");
				break;
			case 10: // Spy
				townTable.setValueAt(Boolean.FALSE, 10, 2);
				spyIcon.setEnabled(false);
				spyCount.setText("0");
				break;
			case 11: // Transporter
				townTable.setValueAt(Boolean.FALSE, 11, 2);
				transporterIcon.setEnabled(false);
				transporterCount.setText("0");
				break;
			case 12: // Veteran
				townTable.setValueAt(Boolean.FALSE, 12, 2);
				veteranIcon.setEnabled(false);
				veteranCount.setText("0");
				break;
			case 13: // Vigilante
				townTable.setValueAt(Boolean.FALSE, 13, 2);
				vigilanteIcon.setEnabled(false);
				vigilanteCount.setText("0");
				break;

			// Mafia Roles
			case 14: // Blackmailer
				mafiaTable.setValueAt(Boolean.FALSE, 0, 2);
				blackmailerIcon.setEnabled(false);
				blackmailerCount.setText("0");
				break;
			case 15: // Consigliere
				mafiaTable.setValueAt(Boolean.FALSE, 1, 2);
				consigliereIcon.setEnabled(false);
				consigliereCount.setText("0");
				break;
			case 16: // Consort
				mafiaTable.setValueAt(Boolean.FALSE, 2, 2);
				consortIcon.setEnabled(false);
				consortCount.setText("0");
				break;
			case 17: // Disguiser
				mafiaTable.setValueAt(Boolean.FALSE, 3, 2);
				disguiserIcon.setEnabled(false);
				disguiserCount.setText("0");
				break;
			case 18: // Framer
				mafiaTable.setValueAt(Boolean.FALSE, 4, 2);
				framerIcon.setEnabled(false);
				framerCount.setText("0");
				break;
			case 19: // Godfather
				mafiaTable.setValueAt(Boolean.FALSE, 5, 2);
				godfatherIcon.setEnabled(false);
				godfatherCount.setText("0");
				break;
			case 20: // Janitor
				mafiaTable.setValueAt(Boolean.FALSE, 6, 2);
				janitorIcon.setEnabled(false);
				janitorCount.setText("0");
				break;
			case 21: // Mafioso
				mafiaTable.setValueAt(Boolean.FALSE, 7, 2);
				mafiosoIcon.setEnabled(false);
				mafiosoCount.setText("0");
				break;

			// Neutral Roles
			case 22: // Amnesiac
				neutralTable.setValueAt(Boolean.FALSE, 0, 2);
				amnesiacIcon.setEnabled(false);
				amnesiacCount.setText("0");
				break;
			case 23: // Arsonist
				neutralTable.setValueAt(Boolean.FALSE, 1, 2);
				arsonistIcon.setEnabled(false);
				arsonistCount.setText("0");
				break;
			case 24: // Executioner
				neutralTable.setValueAt(Boolean.FALSE, 2, 2);
				executionerIcon.setEnabled(false);
				executionerCount.setText("0");
				break;
			case 25: // Jester
				neutralTable.setValueAt(Boolean.FALSE, 3, 2);
				jesterIcon.setEnabled(false);
				jesterCount.setText("0");
				break;
			case 26: // Serial Killer
				neutralTable.setValueAt(Boolean.FALSE, 4, 2);
				serialKillerIcon.setEnabled(false);
				serialKillerCount.setText("0");
				break;
			case 27: // Survivor
				neutralTable.setValueAt(Boolean.FALSE, 5, 2);
				survivorIcon.setEnabled(false);
				survivorCount.setText("0");
				break;
			case 28: // Witch
				neutralTable.setValueAt(Boolean.FALSE, 6, 2);
				witchIcon.setEnabled(false);
				witchCount.setText("0");
				break;
			case 29: // Werewolf
				neutralTable.setValueAt(Boolean.FALSE, 7, 2);
				werewolfIcon.setEnabled(false);
				werewolfCount.setText("0");
				break;
			}
			calculateEnoughRoles();
		}
	}
	
	void enableRole(int id, Integer amount){
		if (!inSession) {
			if (amount == 0) {
				disableRole(id);
				return;
			}
			switch (id) {
			// Town Roles
			case 0: // Bodyguard
				townTable.setValueAt(Boolean.TRUE, 0, 2);
				bodyguardIcon.setEnabled(true);
				bodyguardCount.setText(amount.toString());
				break;
			case 1: // Doctor
				townTable.setValueAt(Boolean.TRUE, 1, 2);
				doctorIcon.setEnabled(true);
				doctorCount.setText(amount.toString());
				break;
			case 2: // Escort
				townTable.setValueAt(Boolean.TRUE, 2, 2);
				escortIcon.setEnabled(true);
				escortCount.setText(amount.toString());
				break;
			case 3: // Investigator
				townTable.setValueAt(Boolean.TRUE, 3, 2);
				investigatorIcon.setEnabled(true);
				investigatorCount.setText(amount.toString());
				break;
			case 4: // Jailor
				townTable.setValueAt(Boolean.TRUE, 4, 2);
				jailorIcon.setEnabled(true);
				jailorCount.setText(amount.toString());
				break;
			case 5: // Lookout
				townTable.setValueAt(Boolean.TRUE, 5, 2);
				lookoutIcon.setEnabled(true);
				lookoutCount.setText(amount.toString());
				break;
			case 6: // Mayor
				townTable.setValueAt(Boolean.TRUE, 6, 2);
				mayorIcon.setEnabled(true);
				mayorCount.setText(amount.toString());
				break;
			case 7: // Medium
				townTable.setValueAt(Boolean.TRUE, 7, 2);
				mediumIcon.setEnabled(true);
				mediumCount.setText(amount.toString());
				break;
			case 8: // Retributionist
				townTable.setValueAt(Boolean.TRUE, 8, 2);
				retributionistIcon.setEnabled(true);
				retributionistCount.setText(amount.toString());
				break;
			case 9: // Sheriff
				townTable.setValueAt(Boolean.TRUE, 9, 2);
				sheriffIcon.setEnabled(true);
				sheriffCount.setText(amount.toString());
				break;
			case 10: // Spy
				townTable.setValueAt(Boolean.TRUE, 10, 2);
				spyIcon.setEnabled(true);
				spyCount.setText(amount.toString());
				break;
			case 11: // Transporter
				townTable.setValueAt(Boolean.TRUE, 11, 2);
				transporterIcon.setEnabled(true);
				transporterCount.setText(amount.toString());
				break;
			case 12: // Veteran
				townTable.setValueAt(Boolean.TRUE, 12, 2);
				veteranIcon.setEnabled(true);
				veteranCount.setText(amount.toString());
				break;
			case 13: // Vigilante
				townTable.setValueAt(Boolean.TRUE, 13, 2);
				vigilanteIcon.setEnabled(true);
				vigilanteCount.setText(amount.toString());
				break;

			// Mafia Roles
			case 14: // Blackmailer
				mafiaTable.setValueAt(Boolean.TRUE, 0, 2);
				blackmailerIcon.setEnabled(true);
				blackmailerCount.setText(amount.toString());
				break;
			case 15: // Consigliere
				mafiaTable.setValueAt(Boolean.TRUE, 1, 2);
				consigliereIcon.setEnabled(true);
				consigliereCount.setText(amount.toString());
				break;
			case 16: // Consort
				mafiaTable.setValueAt(Boolean.TRUE, 2, 2);
				consortIcon.setEnabled(true);
				consortCount.setText(amount.toString());
				break;
			case 17: // Disguiser
				mafiaTable.setValueAt(Boolean.TRUE, 3, 2);
				disguiserIcon.setEnabled(true);
				disguiserCount.setText(amount.toString());
				break;
			case 18: // Framer
				mafiaTable.setValueAt(Boolean.TRUE, 4, 2);
				framerIcon.setEnabled(true);
				framerCount.setText(amount.toString());
				break;
			case 19: // Godfather
				mafiaTable.setValueAt(Boolean.TRUE, 5, 2);
				godfatherIcon.setEnabled(true);
				godfatherCount.setText(amount.toString());
				break;
			case 20: // Janitor
				mafiaTable.setValueAt(Boolean.TRUE, 6, 2);
				janitorIcon.setEnabled(true);
				janitorCount.setText(amount.toString());
				break;
			case 21: // Mafioso
				mafiaTable.setValueAt(Boolean.TRUE, 7, 2);
				mafiosoIcon.setEnabled(true);
				mafiosoCount.setText(amount.toString());
				break;

			// Neutral Roles
			case 22: // Amnesiac
				neutralTable.setValueAt(Boolean.TRUE, 0, 2);
				amnesiacIcon.setEnabled(true);
				amnesiacCount.setText(amount.toString());
				break;
			case 23: // Arsonist
				neutralTable.setValueAt(Boolean.TRUE, 1, 2);
				arsonistIcon.setEnabled(true);
				arsonistCount.setText(amount.toString());
				break;
			case 24: // Executioner
				neutralTable.setValueAt(Boolean.TRUE, 2, 2);
				executionerIcon.setEnabled(true);
				executionerCount.setText(amount.toString());
				break;
			case 25: // Jester
				neutralTable.setValueAt(Boolean.TRUE, 3, 2);
				jesterIcon.setEnabled(true);
				jesterCount.setText(amount.toString());
				break;
			case 26: // Serial Killer
				neutralTable.setValueAt(Boolean.TRUE, 4, 2);
				serialKillerIcon.setEnabled(true);
				serialKillerCount.setText(amount.toString());
				break;
			case 27: // Survivor
				neutralTable.setValueAt(Boolean.TRUE, 5, 2);
				survivorIcon.setEnabled(true);
				survivorCount.setText(amount.toString());
				break;
			case 28: // Witch
				neutralTable.setValueAt(Boolean.TRUE, 6, 2);
				witchIcon.setEnabled(true);
				witchCount.setText(amount.toString());
				break;
			case 29: // Werewolf
				neutralTable.setValueAt(Boolean.TRUE, 7, 2);
				werewolfIcon.setEnabled(true);
				werewolfCount.setText(amount.toString());
				break;
			}
			calculateEnoughRoles();
		}
	}
	
	void setupGameMode(String mode){
		if(mode.equals("All Any")){
			gameModeDescriptionText.setText("All Any mode is completely random and consists of up to 15 Any spots, which can be any of the 30 roles in the game. As with other modes, there can only be one of each Unique Role if one is selected. Games can have as many as 6 starting mafia members and although very rare as few as 0 mafia members.");
			setupAllAnyRoles(Integer.parseInt(numPlayersComboBox.getSelectedItem().toString()));
			saveModeButton.setEnabled(false);
			deleteModeButton.setEnabled(false);
			clearAllRolesButton.setEnabled(false);
		} else if(mode.equals("Classic")){
			gameModeDescriptionText.setText("Classic is the simplest form of the game; the roles are predetermined. This mode is geared towards newer players so they can learn roles without them changing every other game.\n"
											+ "\nThe amount of roles depend on the number of players. The corresponding number will be the first roles that are used. For example: if there are 10 players only the first 10 roles will be used.");
			//numPlayersComboBox.setSelectedIndex(2); // Value of 10
			setupClassicRoles(Integer.parseInt(numPlayersComboBox.getSelectedItem().toString()));
			saveModeButton.setEnabled(false);
			deleteModeButton.setEnabled(false);
			clearAllRolesButton.setEnabled(false);
		} else if(mode.equals("Custom")){
			gameModeDescriptionText.setText("Custom, unlike Classic, gives you more freedom to choose what you want to put into your game. It also opens up a variety of different roles to play, as Classic Mode/Classic is Limited.");
			saveModeButton.setEnabled(true);
			deleteModeButton.setEnabled(false);
			clearAllRolesButton.setEnabled(true);
		} else if(mode.equals("Vigilantics")){
			gameModeDescriptionText.setText("The goal is for one team, either Vigilantes or Witches, to kill the opposing team at night to win. Witches want to force Vigilantes to kill other Vigilantes, and Vigilantes try and shoot or lynch the Witches. If a Vigilante shoots another Vigilante, both will die, as the original shooter will commit suicide after murdering an innocent person.");
			saveModeButton.setEnabled(false);
			deleteModeButton.setEnabled(false);
			clearAllRolesButton.setEnabled(false);
		}  else {
			saveModeButton.setEnabled(false);
			deleteModeButton.setEnabled(false);
			clearAllRolesButton.setEnabled(false);
			
			Properties prop = new Properties();
			InputStream input = null;
		 
			try {
		 
				input = new FileInputStream(mode + ".properties");
		 
				// load a properties file
				prop.load(input);
		 
				// Get the attributes properties
				String name = prop.getProperty("name");
				String description = prop.getProperty("description");
				gameModeDescriptionText.setText(description);
				String numPlayers = prop.getProperty("numPlayers");
				numPlayersComboBox.setSelectedItem(numPlayers);
		 
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	void setupAllAnyRoles(int numPlayers){
		disableAllRoles();
		Boolean jailorFlag = false;
		Boolean mayorFlag = false;
		Boolean retributionistFlag = false;
		Boolean veteranFlag = false;
		Boolean godfatherFlag = false;
		Boolean mafiosoFlag = false;
		Boolean werewolfFlag = false;
		int amount = 0;
		for(int i = 0; i < numPlayers; i++){
			int randomChoice = randInt(1, NUM_ROLES);
			switch(randomChoice){			
				// Town Roles
				case 1:
					amount = Integer.parseInt(bodyguardCount.getText());
					amount++;
					enableRole(0, amount);
					break;
				case 2:
					amount = Integer.parseInt(doctorCount.getText());
					amount++;
					enableRole(1, amount);
					break;
				case 3:
					amount = Integer.parseInt(escortCount.getText());
					amount++;
					enableRole(2, amount);
					break;
				case 4:
					amount = Integer.parseInt(investigatorCount.getText());
					amount++;
					enableRole(3, amount);
					break;
				case 5: // Unique
					if(jailorFlag){
						i--;
					} else {
						jailorFlag = true;
						enableRole(4, 1);
					}
					break;
				case 6:
					amount = Integer.parseInt(lookoutCount.getText());
					amount++;
					enableRole(5, amount);
					break;
				case 7: // Unique
					if(mayorFlag){
						i--;
					} else {
						mayorFlag = true;
						enableRole(6, 1);
					}
					break;
				case 8:
					amount = Integer.parseInt(mediumCount.getText());
					amount++;
					enableRole(7, amount);
					break;
				case 9: // Unique
					if(retributionistFlag){
						i--;
					} else {
						retributionistFlag = true;
						enableRole(8, 1);
					}
					break;
				case 10:
					amount = Integer.parseInt(sheriffCount.getText());
					amount++;
					enableRole(9, amount);
					break;
				case 11:
					amount = Integer.parseInt(spyCount.getText());
					amount++;
					enableRole(10, amount);
					break;
				case 12:
					amount = Integer.parseInt(transporterCount.getText());
					amount++;
					enableRole(11, amount);
					break;
				case 13: // Unique
					if(veteranFlag){
						i--;
					} else {
						veteranFlag = true;
						enableRole(12, 1);
					}
					break;
				case 14:
					amount = Integer.parseInt(vigilanteCount.getText());
					amount++;
					enableRole(13, amount);
					break;
				// Mafia Roles
				case 15:
					amount = Integer.parseInt(blackmailerCount.getText());
					amount++;
					enableRole(14, amount);
					break;
				case 16:
					amount = Integer.parseInt(consigliereCount.getText());
					amount++;
					enableRole(15, amount);
					break;
				case 17:
					amount = Integer.parseInt(consortCount.getText());
					amount++;
					enableRole(16, amount);
					break;
				case 18:
					amount = Integer.parseInt(disguiserCount.getText());
					amount++;
					enableRole(17, amount);
					break;
				case 19:
					amount = Integer.parseInt(framerCount.getText());
					amount++;
					enableRole(18, amount);
					break;
				case 20: // Unique
					if(godfatherFlag){
						i--;
					} else {
						godfatherFlag = true;
						enableRole(19, 1);
					}
					break;
				case 21:
					amount = Integer.parseInt(janitorCount.getText());
					amount++;
					enableRole(20, amount);
					break;
				case 22: // Unique
					if(mafiosoFlag){
						i--;
					} else {
						mafiosoFlag = true;
						enableRole(21, 1);
					}
					break;
				// Neutral Roles
				case 23:
					amount = Integer.parseInt(amnesiacCount.getText());
					amount++;
					enableRole(22, amount);
					break;
				case 24:
					amount = Integer.parseInt(arsonistCount.getText());
					amount++;
					enableRole(23, amount);
					break;
				case 25:
					amount = Integer.parseInt(executionerCount.getText());
					amount++;
					enableRole(24, amount);
					break;
				case 26:
					amount = Integer.parseInt(jesterCount.getText());
					amount++;
					enableRole(25, amount);
					break;
				case 27:
					amount = Integer.parseInt(serialKillerCount.getText());
					amount++;
					enableRole(26, amount);
					break;
				case 28:
					amount = Integer.parseInt(survivorCount.getText());
					amount++;
					enableRole(27, amount);
					break;
				case 29:
					amount = Integer.parseInt(witchCount.getText());
					amount++;
					enableRole(28, amount);
					break;
				case 30: // Unique
					if(werewolfFlag){
						i--;
					} else {
						werewolfFlag = true;
						enableRole(29, 1);
					}
					break;
			}
		}
	}
	
	void setupClassicRoles(int numPlayers){
		disableAllRoles();
		int amount = 0;
		int townKillingChoice= randInt(1, 2);
		int randomTownChoice = randInt(1, 7);
		if (townKillingChoice == 1 && randomTownChoice == 7){
			randomTownChoice = randInt(1, 6);
		}
		switch(numPlayers){
			case 15:
				neutralTable.setValueAt(Boolean.TRUE, 3, 2);
				jesterIcon.setEnabled(true);
			case 14:
				switch(randomTownChoice){
					case 1:
						amount = Integer.parseInt(bodyguardCount.getText());
						amount++;
						enableRole(0, amount);
						break;
					case 2:
						amount = Integer.parseInt(mayorCount.getText());
						amount++;
						enableRole(6, amount);
						break;
					case 3:
						amount = Integer.parseInt(retributionistCount.getText());
						amount++;
						enableRole(8, amount);
						break;
					case 4:
						amount = Integer.parseInt(spyCount.getText());
						amount++;
						enableRole(10, amount);
						break;
					case 5:
						amount = Integer.parseInt(transporterCount.getText());
						amount++;
						enableRole(11, amount);
						break;
					case 6:
						amount = Integer.parseInt(vigilanteCount.getText());
						amount++;
						enableRole(13, amount);
						break;
					case 7:
						amount = Integer.parseInt(veteranCount.getText());
						amount++;
						enableRole(12, amount);
						break;
				}

			case 13:
				if (townKillingChoice == 1){
					amount = Integer.parseInt(veteranCount.getText());
					amount++;
					enableRole(12, amount);
				} else if (townKillingChoice == 2){
					amount = Integer.parseInt(vigilanteCount.getText());
					amount++;
					enableRole(13, amount);
				}
			case 12:
				amount = Integer.parseInt(serialKillerCount.getText());
				amount++;
				enableRole(26, amount);
			case 11:
				amount = Integer.parseInt(lookoutCount.getText());
				amount++;
				enableRole(5, amount);
			case 10:
				amount = Integer.parseInt(mafiosoCount.getText());
				amount++;
				enableRole(21, amount);
			case 9:
				amount = Integer.parseInt(escortCount.getText());
				amount++;
				enableRole(2, amount);
			case 8:
				amount = Integer.parseInt(executionerCount.getText());
				amount++;
				enableRole(24, amount);
			case 7:
				amount = Integer.parseInt(framerCount.getText());
				amount++;
				enableRole(18, amount);
			case 6:
				amount = Integer.parseInt(godfatherCount.getText());
				amount++;
				enableRole(19, amount);
			case 5:
				amount = Integer.parseInt(mediumCount.getText());
				amount++;
				enableRole(7, amount);
			case 4:
				amount = Integer.parseInt(jailorCount.getText());
				amount++;
				enableRole(4, amount);
			case 3:
				amount = Integer.parseInt(investigatorCount.getText());
				amount++;
				enableRole(3, amount);
			case 2:
				amount = Integer.parseInt(doctorCount.getText());
				amount++;
				enableRole(1, amount);
			case 1:
				amount = Integer.parseInt(sheriffCount.getText());
				amount++;
				enableRole(9, amount);
				break;
		}
	}
	
	void disableAllRoles(){
		for(int i = 0; i < NUM_ROLES; i++){
			disableRole(i);
		}
	}
	
	@SuppressWarnings("unchecked")
	void saveGameMode(String name, String description, String numPlayers) throws IOException{
		
		gameModeComboBox.addItem(name);
		
		Properties prop = new Properties();
		OutputStream output = null;
		
		Properties gamemodeprop = new Properties();
		InputStream gamemodeinput = null;
		OutputStream gamemodeoutput = null;
			
		try {
			 
			output = new FileOutputStream(name + ".properties");
			
			// Set the attributes properties
			prop.setProperty("name", name);
			prop.setProperty("description", description);
			prop.setProperty("numPlayers", numPlayers);
	 
			// Set the Icon state properties
			if(bodyguardIcon.isEnabled()){
				prop.setProperty("bodyguard", "enabled");
			} else {
				prop.setProperty("bodyguard", "disabled");
			}
			
			if(doctorIcon.isEnabled()){
				prop.setProperty("doctor", "enabled");
			} else {
				prop.setProperty("doctor", "disabled");
			}
			
			if(escortIcon.isEnabled()){
				prop.setProperty("escort", "enabled");
			} else {
				prop.setProperty("escort", "disabled");
			}
			
			if(investigatorIcon.isEnabled()){
				prop.setProperty("investigator", "enabled");
			} else {
				prop.setProperty("investigator", "disabled");
			}
			
			if(jailorIcon.isEnabled()){
				prop.setProperty("jailor", "enabled");
			} else {
				prop.setProperty("jailor", "disabled");
			}
			
			if(lookoutIcon.isEnabled()){
				prop.setProperty("lookout", "enabled");
			} else {
				prop.setProperty("lookout", "disabled");
			}
			
			if(mayorIcon.isEnabled()){
				prop.setProperty("mayor", "enabled");
			} else {
				prop.setProperty("mayor", "disabled");
			}
			
			if(mediumIcon.isEnabled()){
				prop.setProperty("medium", "enabled");
			} else {
				prop.setProperty("medium", "disabled");
			}
			
			if(retributionistIcon.isEnabled()){
				prop.setProperty("retributionist", "enabled");
			} else {
				prop.setProperty("retributionist", "disabled");
			}
			
			if(sheriffIcon.isEnabled()){
				prop.setProperty("sheriff", "enabled");
			} else {
				prop.setProperty("sheriff", "disabled");
			}
			
			if(spyIcon.isEnabled()){
				prop.setProperty("spy", "enabled");
			} else {
				prop.setProperty("spy", "disabled");
			}
			
			if(transporterIcon.isEnabled()){
				prop.setProperty("transporter", "enabled");
			} else {
				prop.setProperty("transporter", "disabled");
			}
			
			if(veteranIcon.isEnabled()){
				prop.setProperty("veteran", "enabled");
			} else {
				prop.setProperty("veteran", "disabled");
			}
			
			if(vigilanteIcon.isEnabled()){
				prop.setProperty("vigilante", "enabled");
			} else {
				prop.setProperty("vigilante", "disabled");
			}
			
			
			if(blackmailerIcon.isEnabled()){			
				prop.setProperty("blackmailer", "enabled");
			} else {
				prop.setProperty("blackmailer", "disabled");
			}
			
			if(consigliereIcon.isEnabled()){
				prop.setProperty("consigliere", "enabled");
			} else {
				prop.setProperty("bodyguard", "disabled");
			}
			
			if(consortIcon.isEnabled()){
				prop.setProperty("consort", "enabled");
			} else {
				prop.setProperty("consort", "disabled");
			}
			
			if(disguiserIcon.isEnabled()){
				prop.setProperty("disguiser", "enabled");
			} else {
				prop.setProperty("disguiser", "disabled");
			}
			
			if(framerIcon.isEnabled()){
				prop.setProperty("framer", "enabled");
			} else {
				prop.setProperty("framer", "disabled");
			}
			
			if(godfatherIcon.isEnabled()){
				prop.setProperty("godfather", "enabled");
			} else {
				prop.setProperty("godfather", "disabled");
			}
			
			if(janitorIcon.isEnabled()){
				prop.setProperty("janitor", "enabled");
			} else {
				prop.setProperty("janitor", "disabled");
			}
			
			if(mafiosoIcon.isEnabled()){
				prop.setProperty("mafioso", "enabled");
			} else {
				prop.setProperty("mafioso", "disabled");
			}
			
			
			if(amnesiacIcon.isEnabled()){			
				prop.setProperty("amnesiac", "enabled");
			} else {
				prop.setProperty("amnesiac", "disabled");
			}
			
			if(arsonistIcon.isEnabled()){
				prop.setProperty("arsonist", "enabled");
			} else {
				prop.setProperty("arsonist", "disabled");
			}
			
			if(executionerIcon.isEnabled()){
				prop.setProperty("executioner", "enabled");
			} else {
				prop.setProperty("executioner", "disabled");
			}
			
			if(jesterIcon.isEnabled()){
				prop.setProperty("jester", "enabled");
			} else {
				prop.setProperty("jester", "disabled");
			}
			
			if(serialKillerIcon.isEnabled()){
				prop.setProperty("serialKiller", "enabled");
			} else {
				prop.setProperty("serialKiller", "disabled");
			}
			
			if(survivorIcon.isEnabled()){
				prop.setProperty("survivor", "enabled");
			} else {
				prop.setProperty("survivor", "disabled");
			}
			
			if(witchIcon.isEnabled()){
				prop.setProperty("witch", "enabled");
			} else {
				prop.setProperty("witch", "disabled");
			}
			
			if(werewolfIcon.isEnabled()){
				prop.setProperty("werewolf", "enabled");
			} else {
				prop.setProperty("werewolf", "disabled");
			}
	 	 
			// save properties to project root folder
			prop.store(output, null);
	 
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
		
		try {
			gamemodeinput = new FileInputStream("gamemodes.properties");
			gamemodeoutput = new FileOutputStream("temp.properties");
			
			gamemodeprop.load(gamemodeinput);
			
			String nextMode = null;
			
			for(Integer i = 0; i <= MAX_GAMEMODES; i++){
				String propertyValue = null;
				propertyValue = gamemodeprop.getProperty(i.toString());
				
				if(propertyValue == null){
					nextMode = i.toString();
					break;
				} else {
					gamemodeprop.setProperty(i.toString(), propertyValue);
				}
			}
			
			// Set the attributes properties
			gamemodeprop.setProperty(nextMode, name);
			
			gamemodeprop.store(gamemodeoutput, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (gamemodeoutput != null) {
				try {
					gamemodeoutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (gamemodeinput != null) {
				try {
					gamemodeinput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
		
		File oldFile = new File("gamemodes.properties");
		File newFile = new File("temp.properties");
		
		copyFileUsingStream(newFile, oldFile);
		newFile.delete();
	}
	
	void deleteGameMode(String mode) throws IOException{
		
		gameModeComboBox.removeItem(mode);
		
		Properties gamemodeprop = new Properties();
		InputStream gamemodeinput = null;
		OutputStream gamemodeoutput = null;
		
		try {
			gamemodeinput = new FileInputStream("gamemodes.properties");
			gamemodeoutput = new FileOutputStream("temp.properties");
			
			gamemodeprop.load(gamemodeinput);
			
			String toRemove = null;
			
			for(Integer i = 0; i <= MAX_GAMEMODES; i++){
				String currentValue = gamemodeprop.getProperty(i.toString());
				if(mode.equals(currentValue)){
					toRemove = i.toString();
					break;
				}
			}
			
			// Set the attributes properties
			gamemodeprop.remove(toRemove);
			
			gamemodeprop.store(gamemodeoutput, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (gamemodeoutput != null) {
				try {
					gamemodeoutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (gamemodeinput != null) {
				try {
					gamemodeinput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
		
		File oldMode = new File(mode + ".properties");
		oldMode.delete();
		
		File oldFile = new File("gamemodes.properties");
		File newFile = new File("temp.properties");
		
		copyFileUsingStream(newFile, oldFile);
		newFile.delete();
	}
	
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
