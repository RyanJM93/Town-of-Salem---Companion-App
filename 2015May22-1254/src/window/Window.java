package window;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
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
import javax.swing.KeyStroke;
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
import java.util.HashMap;
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

	// Global Variables
	private static final int MAX_GAMEMODES = 10;
	private static final int NUM_ROLES = 30;
	private static final String notEnoughRoles = "You don't have enough roles selected! Add some roles or decrease the number of players!";
	private static final String tooManyRoles = "You have too many roles selected! Remove some roles or increase the number of players!";
	private static final String[] allNightRoles = {
		"Medium",
		"Amnesiac",
		"Transporter",
		"Jester",
		"Witch",
		"Survivor",
		"Bodyguard",
		"Veteran",
		"Escort",
		"Consort",
		"Doctor",
		"Jailor",
		"Godfather",
		"Mafioso",
		"Disguiser",
		"Janitor",
		"Framer",
		"Blackmailer",
		"Consigliere",
		"Spy",
		"Serial Killer",
		"Arsonist",
		"Vigilante",
		"Werewolf",
		"Investigator",
		"Sheriff",
		"Retributionist",
		"Lookout"
	};
	private static final String[] allDayRoles = {
		"Executioner",
		"Mayor"
	};
	
	private static final String[] allRoles = {
		"Amnesiac",
		"Arsonist",
		"Blackmailer",
		"Bodyguard",
		"Consigliere",
		"Consort",
		"Disguiser",
		"Doctor",
		"Escort",
		"Executioner",
		"Framer",
		"Godfather",
		"Investigator",
		"Jailor",
		"Janitor",
		"Jester",
		"Lookout",
		"Mafioso",
		"Mayor",
		"Medium",
		"Retributionist",
		"Serial Killer",
		"Sheriff",
		"Spy",
		"Survivor",
		"Transporter",
		"Veteran",
		"Vigilante",
		"Werewolf",
		"Witch"
	};

	// Declarations
	Boolean maxThree = false;
	Boolean anonymousRoles = false;
	
	private JFrame frame;
	JFrame roleFrame = new JFrame("Role Selection");
	JPanel roleFrameSelectionPanel = new JPanel();
	JScrollPane roleSelectionScrollPane = new JScrollPane();
	
	private JTable townTable;
	private JTable mafiaTable;
	private JTable neutralTable;
	
	Color dayColor = new Color(214,217,223);
	Color nightColor = new Color(192,192,192);

	List<List<String>> activeRoles = new ArrayList<List<String>>();
	Integer activeTown = 0;
	Integer activeMafia = 0;
	Integer activeNeutral = 0;
	
	ArrayList<String> deadRoles;
	
	Object[] nightActionSequence = null;
	
	// HashMaps
	HashMap<String, String> roleDescriptionMap = new HashMap<String, String>();
	HashMap<String, String> roleIconMap = new HashMap<String, String>();
	HashMap<String, Integer> roleAmount = new HashMap<String, Integer>();
	HashMap<String, Integer> roleCount = new HashMap<String, Integer>();
	HashMap<String, JLabel> activeRoleJLabels = new HashMap<String, JLabel>();
	HashMap<JLabel, JLabel> playerMap = new HashMap<JLabel, JLabel>();
	
	Integer numActiveNightRoles = 0;
	Integer numActiveDayRoles = 0;

	JLabel nightNumber = new JLabel("1");
	JLabel roleActionLabel = new JLabel(":");
	JLabel roleActionIcon = new JLabel("");
	JTextPane roleInstructionText = new JTextPane();
	int roleSequenceNumber = 0;
	int nightRoleNumber = 0;
	int dayRoleNumber = 0;

	JPanel mainViewportPanel = new JPanel();
	JLabel roleInfoSheet = new JLabel("");	

	JTabbedPane rolesTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	JTabbedPane iconTabbedPane = new JTabbedPane(JTabbedPane.TOP);

	@SuppressWarnings("rawtypes")
	JComboBox gameModeComboBox = new JComboBox();
	JTextPane gameModeDescriptionText = new JTextPane();
	JScrollPane gameModeDescriptionScrollPane = new JScrollPane();
	@SuppressWarnings("rawtypes")
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
	
	// Player Roles
	JLabel jacobRole = null;
	Boolean jacobFlag = false;
	String jacobRoleName = "";
	Boolean jacobAltDeath = false;
	
	JLabel jamieRole = null;
	Boolean jamieFlag = false;
	String jamieRoleName = "";
	Boolean jamieAltDeath = false;
	
	JLabel brettRole = null;
	Boolean brettFlag = false;
	String brettRoleName = "";
	Boolean brettAltDeath = false;
	
	JLabel calebRole = null;
	Boolean calebFlag = false;
	String calebRoleName = "";
	Boolean calebAltDeath = false;
	
	JLabel jeremyRole = null;
	Boolean jeremyFlag = false;
	String jeremyRoleName = "";
	Boolean jeremyAltDeath = false;
	
	JLabel dylanRole = null;
	Boolean dylanFlag = false;
	String dylanRoleName = "";
	Boolean dylanAltDeath = false;
	
	JLabel benRole = null;
	Boolean benFlag = false;
	String benRoleName = "";
	Boolean benAltDeath = false;
	
	JLabel ryanRole = null;
	Boolean ryanFlag = false;
	String ryanRoleName = "";
	Boolean ryanAltDeath = false;
	
	Boolean enoughRoles = true;
	Boolean inSession = false;
	Boolean isNight = false;

	Boolean isJester = false;
	Boolean jesterFlag = false;
	
	JButton clearAllRolesButton = new JButton("Clear All Roles");

	JScrollPane inSessionPane = new JScrollPane();

	JCheckBox maxThreeCheckbox = new JCheckBox("Role maximum of 3");
	
	List<String> tempNightRoles = new ArrayList<String>();
	Object[] nightRoles = tempNightRoles.toArray();
	Object[][] nightRolesArray = new Object[nightRoles.length][1];
	

	JButton endGameButton = new JButton("End Game");
	
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
	private final JLabel optionsLabel = new JLabel("Options");
	private final JPanel mainInfoPanel = new JPanel();
	private final JSplitPane mainInfoSplitPane = new JSplitPane();
	private final JLabel mainLabel = new JLabel("");
	private final JPanel mainInfoGameInfoPanel = new JPanel();
	private final JLabel gameInfoLabel = new JLabel("");
	private JTable nightRolesTable;
	private JTable dayRolesTable;
	final JButton nextRoleButton = new JButton("\u2192");
	private final JButton nightPhaseButton = new JButton("Night Phase");
	private final JPanel activeRolesPanel = new JPanel();
	private final JPanel activePlayersPanel = new JPanel();
	private final JLabel jacobIcon = new JLabel("");
	private final JLabel jamieIcon = new JLabel("");
	private final JLabel brettIcon = new JLabel("");
	private final JLabel calebIcon = new JLabel("");
	private final JLabel jeremyIcon = new JLabel("");
	private final JLabel dylanIcon = new JLabel("");
	private final JLabel benIcon = new JLabel("");
	private final JLabel ryanIcon = new JLabel("");
	

	final JPanel inSessionDataPanel = new JPanel();
	final JPanel headerPanel = new JPanel();
	final JPanel rolesPanel = new JPanel();
	final JPanel iconPanel = new JPanel();
	final JScrollPane mainInfoPane = new JScrollPane();

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
		
		// Main JFrame initialization
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		frame = new JFrame();
		frame.setBackground(UIManager.getColor("Button.background"));
		frame.setTitle("Town of Salem - Companion App");
		frame.setResizable(false);
		frame.setBounds(50, 50, 1810, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Put Count Maps
		roleCount.put("Medium", 0);
		roleCount.put("Amnesiac", 0);
		roleCount.put("Transporter", 0);
		roleCount.put("Jester", 0);
		roleCount.put("Witch", 0);
		roleCount.put("Survivor", 0);
		roleCount.put("Bodyguard", 0);
		roleCount.put("Veteran", 0);
		
		roleCount.put("Escort", 0);
		roleCount.put("Consort", 0);
		roleCount.put("Doctor", 0);
		roleCount.put("Jailor", 0);
		
		roleCount.put("Godfather", 0);
		roleCount.put("Mafioso", 0);
		roleCount.put("Disguiser", 0);
		roleCount.put("Janitor", 0);
		roleCount.put("Framer", 0);
		roleCount.put("Blackmailer", 0);
		roleCount.put("Consigliere", 0);
		roleCount.put("Spy", 0);
		
		roleCount.put("Serial Killer", 0);
		roleCount.put("Arsonist", 0);
		roleCount.put("Vigilante", 0);
		roleCount.put("Werewolf", 0);
		
		roleCount.put("Investigator", 0);
		roleCount.put("Sheriff", 0);
		roleCount.put("Retributionist", 0);
		roleCount.put("Lookout", 0);
		
		roleCount.put("Executioner", 0);
		roleCount.put("Mayor", 0);
				
		// Put Amount Maps
		roleAmount.put("Medium", 0);
		roleAmount.put("Amnesiac", 0);
		roleAmount.put("Transporter", 0);
		roleAmount.put("Jester", 0);
		roleAmount.put("Witch", 0);
		roleAmount.put("Survivor", 0);
		roleAmount.put("Bodyguard", 0);
		roleAmount.put("Veteran", 0);
		
		roleAmount.put("Escort", 0);
		roleAmount.put("Consort", 0);
		roleAmount.put("Doctor", 0);
		roleAmount.put("Jailor", 0);
		
		roleAmount.put("Godfather", 0);
		roleAmount.put("Mafioso", 0);
		roleAmount.put("Disguiser", 0);
		roleAmount.put("Janitor", 0);
		roleAmount.put("Framer", 0);
		roleAmount.put("Blackmailer", 0);
		roleAmount.put("Consigliere", 0);
		roleAmount.put("Spy", 0);
		
		roleAmount.put("Serial Killer", 0);
		roleAmount.put("Arsonist", 0);
		roleAmount.put("Vigilante", 0);
		roleAmount.put("Werewolf", 0);
		
		roleAmount.put("Investigator", 0);
		roleAmount.put("Sheriff", 0);
		roleAmount.put("Retributionist", 0);
		roleAmount.put("Lookout", 0);
		
		roleAmount.put("Executioner", 0);
		roleAmount.put("Mayor", 0);
		
		// Put Description Maps
		roleDescriptionMap.put("Medium", "Wake up.\n\nRead what the dead have written.\n\nErase their tellings once finished.");
		roleDescriptionMap.put("Amnesiac","Wake up.\n\nIf you remember who you are, select someone from the graveyard and become their role.\n\nYou may not become a Jailor, Mayor, Retributionist, Veteran, Godfather, Mafioso, or Werewolf.");
		roleDescriptionMap.put("Transporter", "Wake up.\n\nYou may swap the playmat positions of two players.\n\nTap the two players as you transport their roles.");
		roleDescriptionMap.put("Jester", "If you were lynched today, wake up.\n\nTown members that voted guilty, put your thumbs up.\n\nJester, haunt one of those guilty voters.");
		roleDescriptionMap.put("Witch", "Wake up.\n\nBewitch one player, and hex the target of your bewitchment.\n\nThe bewitched player must do your bidding on the hexed target when they awake.");
		roleDescriptionMap.put("Survivor", "Wake up.\n\nYou may choose to don your bullet proof vest tonight.");
		roleDescriptionMap.put("Bodyguard", "Wake up.\n\nYou may choose to protect someone from death tonight.\n\nIf your target is attacked, both you and your attacker will die instead.\n\nIf you successfullly protect someone, you can still be healed.");
		roleDescriptionMap.put("Veteran", "Wake up.\n\nDecide if you want to go on alert tonight.\n\nWhile on alert you cannot be killed.\n\nIf anyone visits you while you are on alert they will be shot.");

		roleDescriptionMap.put("Escort", "Wake up.\n\nChoose a player to distract tonight.\n\nYou will prevent them from performing their night action.");
		roleDescriptionMap.put("Consort", "Wake up.\n\nChoose a player to distract tonight.\n\nYou will prevent them from performing their night action.");
		roleDescriptionMap.put("Doctor", "Wake up.\n\nHeal one person tonight to prevent them from dying.\n\nYou may heal yourself tonight if you have not done so during a previous night.");
		roleDescriptionMap.put("Jailor", "Wake up.\n\nYou may choose to execute your prisoner.");

		roleDescriptionMap.put("Godfather", "Wake up the entire Mafia.\n\nChoose a target to be killed at Midnight.\n\nDiscuss with the Mafia.");
		roleDescriptionMap.put("Mafioso", "If there is no Godfather, you become the Godfather and must choose a target to be killed at Midnight.\n\nElse, you must kill the Godfather's target at Midnight.");
		roleDescriptionMap.put("Disguiser", "If there are no killing Mafia roles alive, you become a Mafioso and must choose a target to be killed at Midnight.\n\nElse, you may choose a player to try to disguise as at Midnight.");
		roleDescriptionMap.put("Janitor", "If there are no killing Mafia roles alive, you become a Mafioso and must choose a target to be killed at Midnight.\n\nElse, you may choose a player to try to clean at Midnight.");
		roleDescriptionMap.put("Framer", "If there are no killing Mafia roles alive, you become a Mafioso and must choose a target to be killed at Midnight.\n\nElse, you may choose a player to frame as Mafia at Midnight.");
		roleDescriptionMap.put("Blackmailer", "If there are no killing Mafia roles alive, you become a Mafioso and must choose a target to be killed at Midnight.\n\nElse, you may choose a player to try to blackmail at Midnight.");
		roleDescriptionMap.put("Consigliere", "If there are no killing Mafia roles alive, you become a Mafioso and must choose a target to be killed at Midnight.\n\nElse, you may choose a player to view their role at Midnight.");
		roleDescriptionMap.put("Spy", "Wake up.\n\nSee who the Mafia has chosen as their targets.");

		roleDescriptionMap.put("Serial Killer", "Wake up.\n\nChoose a player to kill tonight.");
		roleDescriptionMap.put("Arsonist", "Wake up.\n\nDouse a new victim in gas or ignite all your currently doused victims, killing them. You may also clean yourself of gas instead of targeting someone.");
		roleDescriptionMap.put("Vigilante", "Wake up.\n\nChoose to take justice into your own hands and shoot someone.\n\nIf you kill another town member you will commit suicide over the guilt.");
		roleDescriptionMap.put("Werewolf", "Wake up.\n\nOn a Full Moon, you may choose to pillage a person's house, killing them and their visitors, or stay home and kill all of your visitors.");

		roleDescriptionMap.put("Investigator", "Wake up.\n\nInvestigate one person for a clue to their role.");
		roleDescriptionMap.put("Sheriff", "Wake up.\n\nCheck one person tonight for suspicious activity.");
		roleDescriptionMap.put("Retributionist", "Wake up.\n\nYou may revive a dead Town member, if you have not already done so on a previous night.");
		roleDescriptionMap.put("Lookout", "Wake up.\n\nSelect one person to see who visited them over the course of the night.");
		
		// Put Icon Maps
		roleIconMap.put("Medium", "/window/mediumIcon.PNG");
		roleIconMap.put("Amnesiac", "/window/amnesiacIcon.PNG");
		roleIconMap.put("Transporter", "/window/transporterIcon.PNG");
		roleIconMap.put("Jester", "/window/jesterIcon.PNG");
		roleIconMap.put("Witch", "/window/witchIcon.PNG");
		roleIconMap.put("Survivor", "/window/survivorIcon.PNG");
		roleIconMap.put("Bodyguard", "/window/bodyguardIcon.PNG");
		roleIconMap.put("Veteran", "/window/veteranIcon.PNG");

		roleIconMap.put("Escort", "/window/escortIcon.PNG");
		roleIconMap.put("Consort", "/window/consortIcon.PNG");
		roleIconMap.put("Doctor", "/window/doctorIcon.PNG");
		roleIconMap.put("Jailor", "/window/jailorIcon.PNG");

		roleIconMap.put("Godfather", "/window/godfatherIcon.PNG");
		roleIconMap.put("Mafioso", "/window/mafiosoIcon.PNG");
		roleIconMap.put("Disguiser", "/window/disguiserIcon.PNG");
		roleIconMap.put("Janitor", "/window/janitorIcon.PNG");
		roleIconMap.put("Framer", "/window/framerIcon.PNG");
		roleIconMap.put("Blackmailer", "/window/blackmailerIcon.PNG");
		roleIconMap.put("Consigliere", "/window/consigliereIcon.PNG");
		roleIconMap.put("Spy", "/window/spyIcon.PNG");

		roleIconMap.put("Serial Killer", "/window/serialKillerIcon.PNG");
		roleIconMap.put("Arsonist", "/window/arsonistIcon.PNG");
		roleIconMap.put("Vigilante", "/window/vigilanteIcon.PNG");
		roleIconMap.put("Werewolf", "/window/werewolfIcon.PNG");

		roleIconMap.put("Investigator", "/window/investigatorIcon.PNG");
		roleIconMap.put("Sheriff", "/window/sheriffIcon.PNG");
		roleIconMap.put("Retributionist", "/window/retributionistIcon.PNG");
		roleIconMap.put("Lookout", "/window/lookoutIcon.PNG");

		roleIconMap.put("Executioner", "/window/executionerIcon.PNG");
		roleIconMap.put("Mayor", "/window/mayorIcon.PNG");
		
		// Town Icons
		bodyguardIcon.setIcon(new ImageIcon(Window.class.getResource("/window/bodyguardIcon.png")));
		bodyguardIcon.setEnabled(false);
		bodyguardIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
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
		
		
		
		frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
		mainViewportPanel.setBackground(UIManager.getColor("Button.background"));
		
		frame.getContentPane().add(mainViewportPanel, BorderLayout.CENTER);
		mainViewportPanel.setLayout(null);
		
		rolesPanel.setBackground(UIManager.getColor("Button.background"));
		rolesPanel.setBounds(10, 11, 380, 295);
		mainViewportPanel.add(rolesPanel);
		rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.X_AXIS));
		rolesPanel.setPreferredSize(new Dimension(380,295));
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
				
				if (cellValue == Boolean.TRUE && !inSession && iconTabbedPane.getComponents().length == 3){
					switch(celly){
		    		case 0:
		    			bodyguardIcon.setEnabled(true);
		    			bodyguardCount.setText("1");
		    			roleCount.put("Bodyguard", 1);
		    			break;
		    		case 1:
		    			doctorIcon.setEnabled(true);
		    			doctorCount.setText("1");
		    			roleCount.put("Doctor", 1);
		    			break;
		    		case 2:
		    			escortIcon.setEnabled(true);
		    			escortCount.setText("1");
		    			roleCount.put("Escort", 1);
		    			break;
		    		case 3:
		    			investigatorIcon.setEnabled(true);
		    			investigatorCount.setText("1");
		    			roleCount.put("Investigator", 1);
		    			break;
		    		case 4:
		    			jailorIcon.setEnabled(true);
		    			jailorCount.setText("1");
		    			roleCount.put("Jailor", 1);
		    			break;
		    		case 5:
		    			lookoutIcon.setEnabled(true);
		    			lookoutCount.setText("1");
		    			roleCount.put("Lookout", 1);
		    			break;
		    		case 6:
		    			mayorIcon.setEnabled(true);
		    			mayorCount.setText("1");
		    			roleCount.put("Mayor", 1);
		    			break;
		    		case 7:
		    			mediumIcon.setEnabled(true);
		    			mediumCount.setText("1");
		    			roleCount.put("Medium", 1);
		    			break;
		    		case 8:
		    			retributionistIcon.setEnabled(true);
		    			retributionistCount.setText("1");
		    			roleCount.put("Retributionist", 1);
		    			break;
		    		case 9:
		    			sheriffIcon.setEnabled(true);
		    			sheriffCount.setText("1");
		    			roleCount.put("Sheriff", 1);
		    			break;
		    		case 10:
		    			spyIcon.setEnabled(true);
		    			spyCount.setText("1");
		    			roleCount.put("Spy", 1);
		    			break;
		    		case 11:
		    			transporterIcon.setEnabled(true);
		    			transporterCount.setText("1");
		    			roleCount.put("Transporter", 1);
		    			break;
		    		case 12:
		    			veteranIcon.setEnabled(true);
		    			veteranCount.setText("1");
		    			roleCount.put("Veteran", 1);
		    			break;
		    		case 13:
		    			vigilanteIcon.setEnabled(true);
		    			vigilanteCount.setText("1");
		    			roleCount.put("Vigilante", 1);
		    			break;
		    		default:
		    			break;
					}
				} else if (cellValue == Boolean.FALSE && !inSession && iconTabbedPane.getComponents().length == 3){
					switch(celly){
					case 0:
		    			bodyguardIcon.setEnabled(false);
		    			bodyguardCount.setText("0");
		    			roleCount.put("Bodyguard", 0);
		    			break;
		    		case 1:
		    			doctorIcon.setEnabled(false);
		    			doctorCount.setText("0");
		    			roleCount.put("Doctor", 0);
		    			break;
		    		case 2:
		    			escortIcon.setEnabled(false);
		    			escortCount.setText("0");
		    			roleCount.put("Escort", 0);
		    			break;
		    		case 3:
		    			investigatorIcon.setEnabled(false);
		    			investigatorCount.setText("0");
		    			roleCount.put("Investigator", 0);
		    			break;
		    		case 4:
		    			jailorIcon.setEnabled(false);
		    			jailorCount.setText("0");
		    			roleCount.put("Jailor", 0);
		    			break;
		    		case 5:
		    			lookoutIcon.setEnabled(false);
		    			lookoutCount.setText("0");
		    			roleCount.put("Lookout", 0);
		    			break;
		    		case 6:
		    			mayorIcon.setEnabled(false);
		    			mayorCount.setText("0");
		    			roleCount.put("Mayor", 0);
		    			break;
		    		case 7:
		    			mediumIcon.setEnabled(false);
		    			mediumCount.setText("0");
		    			roleCount.put("Medium", 0);
		    			break;
		    		case 8:
		    			retributionistIcon.setEnabled(false);
		    			retributionistCount.setText("0");
		    			roleCount.put("Retributionist", 0);
		    			break;
		    		case 9:
		    			sheriffIcon.setEnabled(false);
		    			sheriffCount.setText("0");
		    			roleCount.put("Sheriff", 0);
		    			break;
		    		case 10:
		    			spyIcon.setEnabled(false);
		    			spyCount.setText("0");
		    			roleCount.put("Spy", 0);
		    			break;
		    		case 11:
		    			transporterIcon.setEnabled(false);
		    			transporterCount.setText("0");
		    			roleCount.put("Transporter", 0);
		    			break;
		    		case 12:
		    			veteranIcon.setEnabled(false);
		    			veteranCount.setText("0");
		    			roleCount.put("Veteran", 0);
		    			break;
		    		case 13:
		    			vigilanteIcon.setEnabled(false);
		    			vigilanteCount.setText("0");
		    			roleCount.put("Vigilante", 0);
		    			break;
		    		default:
		    			break;
					}
				}
				calculateEnoughRoles();
			}
	    });
		
		townTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1 && !inSession && iconTabbedPane.getComponents().length == 3) {
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
				
				if (cellValue == Boolean.TRUE && !inSession && iconTabbedPane.getComponents().length == 3){
					switch(celly){
		    		case 0:
		    			blackmailerIcon.setEnabled(true);
		    			blackmailerCount.setText("1");
		    			roleCount.put("Blackmailer", 1);
		    			break;
		    		case 1:
		    			consigliereIcon.setEnabled(true);
		    			consigliereCount.setText("1");
		    			roleCount.put("Consigliere", 1);
		    			break;
		    		case 2:
		    			consortIcon.setEnabled(true);
		    			consortCount.setText("1");
		    			roleCount.put("Consort", 1);
		    			break;
		    		case 3:
		    			disguiserIcon.setEnabled(true);
		    			disguiserCount.setText("1");
		    			roleCount.put("Disguiser", 1);
		    			break;
		    		case 4:
		    			framerIcon.setEnabled(true);
		    			framerCount.setText("1");
		    			roleCount.put("Framer", 1);
		    			break;
		    		case 5:
		    			godfatherIcon.setEnabled(true);
		    			godfatherCount.setText("1");
		    			roleCount.put("Godfather", 1);
		    			break;
		    		case 6:
		    			janitorIcon.setEnabled(true);
		    			janitorCount.setText("1");
		    			roleCount.put("Janitor", 1);
		    			break;
		    		case 7:
		    			mafiosoIcon.setEnabled(true);
		    			mafiosoCount.setText("1");
		    			roleCount.put("Mafioso", 1);
		    			break;
		    		default:
		    			break;
					}
				} else if (cellValue == Boolean.FALSE && !inSession && iconTabbedPane.getComponents().length == 3){
					switch(celly){
					case 0:
		    			blackmailerIcon.setEnabled(false);
		    			blackmailerCount.setText("0");
		    			roleCount.put("Blackmailer", 0);
		    			break;
		    		case 1:
		    			consigliereIcon.setEnabled(false);
		    			consigliereCount.setText("0");
		    			roleCount.put("Consigliere", 0);
		    			break;
		    		case 2:
		    			consortIcon.setEnabled(false);
		    			consortCount.setText("0");
		    			roleCount.put("Consort", 0);
		    			break;
		    		case 3:
		    			disguiserIcon.setEnabled(false);
		    			disguiserCount.setText("0");
		    			roleCount.put("Disguiser", 0);
		    			break;
		    		case 4:
		    			framerIcon.setEnabled(false);
		    			framerCount.setText("0");
		    			roleCount.put("Framer", 0);
		    			break;
		    		case 5:
		    			godfatherIcon.setEnabled(false);
		    			godfatherCount.setText("0");
		    			roleCount.put("Godfather", 0);
		    			break;
		    		case 6:
		    			janitorIcon.setEnabled(false);
		    			janitorCount.setText("0");
		    			roleCount.put("Janitor", 0);
		    			break;
		    		case 7:
		    			mafiosoIcon.setEnabled(false);
		    			mafiosoCount.setText("0");
		    			roleCount.put("Mafioso", 0);
		    			break;
		    		default:
		    			break;
					}
				}
				calculateEnoughRoles();
			}
	    });
		
		mafiaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1 && !inSession && iconTabbedPane.getComponents().length == 3) {
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
				
				if (cellValue == Boolean.TRUE && !inSession && iconTabbedPane.getComponents().length == 3){
					switch(celly){
		    		case 0:
		    			amnesiacIcon.setEnabled(true);
		    			amnesiacCount.setText("1");
		    			roleCount.put("Amnesiac", 1);
		    			break;
		    		case 1:
		    			arsonistIcon.setEnabled(true);
		    			arsonistCount.setText("1");
		    			roleCount.put("Arsonist", 1);
		    			break;
		    		case 2:
		    			executionerIcon.setEnabled(true);
		    			executionerCount.setText("1");
		    			roleCount.put("Executioner", 1);
		    			break;
		    		case 3:
		    			jesterIcon.setEnabled(true);
		    			jesterCount.setText("1");
		    			roleCount.put("Jester", 1);
		    			break;
		    		case 4:
		    			serialKillerIcon.setEnabled(true);
		    			serialKillerCount.setText("1");
		    			roleCount.put("Serial Killer", 1);
		    			break;
		    		case 5:
		    			survivorIcon.setEnabled(true);
		    			survivorCount.setText("1");
		    			roleCount.put("Survivor", 1);
		    			break;
		    		case 6:
		    			witchIcon.setEnabled(true);
		    			witchCount.setText("1");
		    			roleCount.put("Witch", 1);
		    			break;
		    		case 7:
		    			werewolfIcon.setEnabled(true);
		    			werewolfCount.setText("1");
		    			roleCount.put("Werewolf", 1);
		    			break;
		    		default:
		    			break;
					}
				} else if (cellValue == Boolean.FALSE && !inSession && iconTabbedPane.getComponents().length == 3){
					switch(celly){
					case 0:
		    			amnesiacIcon.setEnabled(false);
		    			amnesiacCount.setText("0");
		    			roleCount.put("Amnesiac", 0);
		    			break;
		    		case 1:
		    			arsonistIcon.setEnabled(false);
		    			arsonistCount.setText("0");
		    			roleCount.put("Arsonist", 0);
		    			break;
		    		case 2:
		    			executionerIcon.setEnabled(false);
		    			executionerCount.setText("0");
		    			roleCount.put("Executioner", 0);
		    			break;
		    		case 3:
		    			jesterIcon.setEnabled(false);
		    			jesterCount.setText("0");
		    			roleCount.put("Jester", 0);
		    			break;
		    		case 4:
		    			serialKillerIcon.setEnabled(false);
		    			serialKillerCount.setText("0");
		    			roleCount.put("Serial Killer", 0);
		    			break;
		    		case 5:
		    			survivorIcon.setEnabled(false);
		    			survivorCount.setText("0");
		    			roleCount.put("Survivor", 0);
		    			break;
		    		case 6:
		    			witchIcon.setEnabled(false);
		    			witchCount.setText("0");
		    			roleCount.put("Witch", 0);
		    			break;
		    		case 7:
		    			werewolfIcon.setEnabled(false);
		    			werewolfCount.setText("0");
		    			roleCount.put("Werewolf", 0);
		    			break;
		    		default:
		    			break;
					}
				}
				calculateEnoughRoles();
			}
	    });
		
		neutralTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1 && !inSession && iconTabbedPane.getComponents().length == 3) {
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
		
		iconPanel.setBackground(UIManager.getColor("Button.background"));
		iconPanel.setBounds(1288, 11, 500, 890);
		mainViewportPanel.add(iconPanel);
		iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
		
		iconPanel.add(iconTabbedPane);

		// Change Listeners
		
		ChangeListener rolesTabListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if(!inSession && iconTabbedPane.getComponents().length == 3){
					if(sourceTabbedPane.getTitleAt(index).equals("Town")){
						iconTabbedPane.setSelectedIndex(index);
					} else if(sourceTabbedPane.getTitleAt(index).equals("Mafia")){
						iconTabbedPane.setSelectedIndex(index);
					} else if(sourceTabbedPane.getTitleAt(index).equals("Neutral")){
						iconTabbedPane.setSelectedIndex(index);
					}
				}
			}
		};
		
		rolesTabbedPane.addChangeListener(rolesTabListener);
		
		ChangeListener iconTabListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if(!inSession && iconTabbedPane.getComponents().length == 3){
					if(sourceTabbedPane.getTitleAt(index).equals("Town")){
						rolesTabbedPane.setSelectedIndex(index);
					} else if(sourceTabbedPane.getTitleAt(index).equals("Mafia")){
						rolesTabbedPane.setSelectedIndex(index);
					} else if(sourceTabbedPane.getTitleAt(index).equals("Neutral")){
						rolesTabbedPane.setSelectedIndex(index);
					}
				}
			}
		};
		
		iconTabbedPane.addChangeListener(iconTabListener);
		townIconPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
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
		mafiaIconPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
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
		neutralIconPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
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
		activePlayersPanel.setPreferredSize(new Dimension(480, 960));
		activePlayersPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		activePlayersPanel.setBackground(new Color(238, 232, 170));
		
		iconTabbedPane.addTab("Active Players", null, activePlayersPanel, null);
		activePlayersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Player Icons
		
		jacobIcon.setToolTipText("Jacob");
		jacobIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && jacobIcon.isEnabled() && !jacobFlag){
					String[] choices = allRoles;
					jacobRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(jacobRoleName != null){
						jacobRole = activeRoleJLabels.get(jacobRoleName);
						
						playerMap.put(jacobIcon, jacobRole);
						
						jacobIcon.setEnabled(true);
						
						jacobFlag = true;
					}
				} else if(inSession && !jacobIcon.isEnabled()){
					jacobIcon.setEnabled(true);
					jacobRole.dispatchEvent(arg0);
				} else if(inSession && jacobIcon.isEnabled()){
					if(jacobRoleName.equals("Executioner") || jacobRoleName.equals("Amnesiac")){
						jacobAltDeath = true;
					}
					jacobIcon.setEnabled(false);
					jacobRole.dispatchEvent(arg0);
				} else if(!inSession && !jacobIcon.isEnabled()){
					jacobIcon.setEnabled(true);
				} else if(!inSession && jacobIcon.isEnabled()){
					jacobIcon.setEnabled(false);
				}
			}
		});
		jacobIcon.setIcon(new ImageIcon(Window.class.getResource("/window/jacobIcon.png")));
		jacobIcon.setEnabled(false);
		
		activePlayersPanel.add(jacobIcon);
		jamieIcon.setToolTipText("Jamie");
		jamieIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && jamieIcon.isEnabled() && !jamieFlag){
					String[] choices = allRoles;
					jamieRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(jamieRoleName != null){
						jamieRole = activeRoleJLabels.get(jamieRoleName);
						
						playerMap.put(jamieIcon, jamieRole);
						
						jamieIcon.setEnabled(true);
						
						jamieFlag = true;
					}
				} else if(inSession && !jamieIcon.isEnabled()){
					jamieIcon.setEnabled(true);
					jamieRole.dispatchEvent(arg0);
				} else if(inSession && jamieIcon.isEnabled()){
					if(jamieRoleName.equals("Executioner") || jamieRoleName.equals("Amnesiac")){
						jamieAltDeath = true;
					}
					jamieIcon.setEnabled(false);
					jamieRole.dispatchEvent(arg0);
				} else if(!inSession && !jamieIcon.isEnabled()){
					jamieIcon.setEnabled(true);
				} else if(!inSession && jamieIcon.isEnabled()){
					jamieIcon.setEnabled(false);
				}
			}
		});
		jamieIcon.setIcon(new ImageIcon(Window.class.getResource("/window/jamieIcon.png")));
		jamieIcon.setEnabled(false);
		
		activePlayersPanel.add(jamieIcon);
		brettIcon.setToolTipText("Brett");
		brettIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && brettIcon.isEnabled() && !brettFlag){
					String[] choices = allRoles;
					String brettRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(brettRoleName != null){
						brettRole = activeRoleJLabels.get(brettRoleName);
						
						playerMap.put(brettIcon, brettRole);
						
						brettIcon.setEnabled(true);
						
						brettFlag = true;
					}
				} else if(inSession && !brettIcon.isEnabled()){
					brettIcon.setEnabled(true);
					brettRole.dispatchEvent(arg0);
				} else if(inSession && brettIcon.isEnabled()){
					if(brettRoleName.equals("Executioner") || brettRoleName.equals("Amnesiac")){
						brettAltDeath = true;
					}
					brettIcon.setEnabled(false);
					brettRole.dispatchEvent(arg0);
				} else if(!inSession && !brettIcon.isEnabled()){
					brettIcon.setEnabled(true);
				} else if(!inSession && brettIcon.isEnabled()){
					brettIcon.setEnabled(false);
				}
			}
		});
		brettIcon.setIcon(new ImageIcon(Window.class.getResource("/window/brettIcon.png")));
		brettIcon.setEnabled(false);
		
		activePlayersPanel.add(brettIcon);
		calebIcon.setToolTipText("Caleb");
		calebIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && calebIcon.isEnabled() && !calebFlag){
					String[] choices = allRoles;
					String calebRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(calebRoleName != null){
						calebRole = activeRoleJLabels.get(calebRoleName);
						
						playerMap.put(calebIcon, calebRole);
						
						calebIcon.setEnabled(true);
						
						calebFlag = true;
					}
				} else if(inSession && !calebIcon.isEnabled()){
					calebIcon.setEnabled(true);
					calebRole.dispatchEvent(arg0);
				} else if(inSession && calebIcon.isEnabled()){
					if(calebRoleName.equals("Executioner") || calebRoleName.equals("Amnesiac")){
						calebAltDeath = true;
					}
					calebIcon.setEnabled(false);
					calebRole.dispatchEvent(arg0);
				} else if(!inSession && !calebIcon.isEnabled()){
					calebIcon.setEnabled(true);
				} else if(!inSession && calebIcon.isEnabled()){
					calebIcon.setEnabled(false);
				}
			}
		});
		calebIcon.setIcon(new ImageIcon(Window.class.getResource("/window/calebIcon.png")));
		calebIcon.setEnabled(false);
		
		activePlayersPanel.add(calebIcon);
		jeremyIcon.setToolTipText("Jeremy");
		jeremyIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && jeremyIcon.isEnabled() && !jeremyFlag){
					String[] choices = allRoles;
					jeremyRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(jeremyRoleName != null){
						jeremyRole = activeRoleJLabels.get(jeremyRoleName);
						
						playerMap.put(jeremyIcon, jeremyRole);
						
						jeremyIcon.setEnabled(true);
						
						jeremyFlag = true;
					}
				} else if(inSession && !jeremyIcon.isEnabled()){
					jeremyIcon.setEnabled(true);
					jeremyRole.dispatchEvent(arg0);
				} else if(inSession && jeremyIcon.isEnabled()){
					if(jeremyRoleName.equals("Executioner") || jeremyRoleName.equals("Amnesiac")){
						jeremyAltDeath = true;
					}
					jeremyIcon.setEnabled(false);
					jeremyRole.dispatchEvent(arg0);
				} else if(!inSession && !jeremyIcon.isEnabled()){
					jeremyIcon.setEnabled(true);
				} else if(!inSession && jeremyIcon.isEnabled()){
					jeremyIcon.setEnabled(false);
				}
			}
		});
		jeremyIcon.setIcon(new ImageIcon(Window.class.getResource("/window/amnesiacIcon.png")));
		jeremyIcon.setEnabled(false);
		
		activePlayersPanel.add(jeremyIcon);
		dylanIcon.setToolTipText("Dylan");
		dylanIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && dylanIcon.isEnabled() && !dylanFlag){
					String[] choices = allRoles;
					dylanRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(dylanRoleName != null){
						dylanRole = activeRoleJLabels.get(dylanRoleName);
						
						playerMap.put(dylanIcon, dylanRole);
						
						dylanIcon.setEnabled(true);
						
						dylanFlag = true;
					}
				} else if(inSession && !dylanIcon.isEnabled()){
					dylanIcon.setEnabled(true);
					dylanRole.dispatchEvent(arg0);
				} else if(inSession && dylanIcon.isEnabled()){
					if(dylanRoleName.equals("Executioner") || dylanRoleName.equals("Amnesiac")){
						dylanAltDeath = true;
					}
					dylanIcon.setEnabled(false);
					dylanRole.dispatchEvent(arg0);
				} else if(!inSession && !dylanIcon.isEnabled()){
					dylanIcon.setEnabled(true);
				} else if(!inSession && dylanIcon.isEnabled()){
					dylanIcon.setEnabled(false);
				}
			}
		});
		dylanIcon.setIcon(new ImageIcon(Window.class.getResource("/window/dylanIcon.png")));
		dylanIcon.setEnabled(false);
		
		activePlayersPanel.add(dylanIcon);
		benIcon.setToolTipText("Ben");
		benIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && benIcon.isEnabled() && !benFlag){
					String[] choices = allRoles;
					benRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(benRoleName != null){
						benRole = activeRoleJLabels.get(benRoleName);
						
						playerMap.put(benIcon, benRole);
						
						benIcon.setEnabled(true);
						
						benFlag = true;
					}
				} else if(inSession && !benIcon.isEnabled()){
					benIcon.setEnabled(true);
					benRole.dispatchEvent(arg0);
				} else if(inSession && benIcon.isEnabled()){
					if(benRoleName.equals("Executioner") || benRoleName.equals("Amnesiac")){
						benAltDeath = true;
					}
					benIcon.setEnabled(false);
					benRole.dispatchEvent(arg0);
				} else if(!inSession && !benIcon.isEnabled()){
					benIcon.setEnabled(true);
				} else if(!inSession && benIcon.isEnabled()){
					benIcon.setEnabled(false);
				}
			}
		});
		benIcon.setIcon(new ImageIcon(Window.class.getResource("/window/amnesiacIcon.png")));
		benIcon.setEnabled(false);
		
		activePlayersPanel.add(benIcon);
		ryanIcon.setToolTipText("Ryan");
		ryanIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(inSession && ryanIcon.isEnabled() && !ryanFlag){
					String[] choices = allRoles;
					ryanRoleName = (String)JOptionPane.showInputDialog(frame, "Which role did you get?",
							"ComboBox Dialog", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
					if(ryanRoleName != null){
						ryanRole = activeRoleJLabels.get(ryanRoleName);
						
						playerMap.put(ryanIcon, ryanRole);
						
						ryanIcon.setEnabled(true);
						
						ryanFlag = true;
					}
				} else if(inSession && !ryanIcon.isEnabled()){
					ryanIcon.setEnabled(true);
					ryanRole.dispatchEvent(arg0);
				} else if(inSession && ryanIcon.isEnabled()){
					if(ryanRoleName.equals("Executioner") || ryanRoleName.equals("Amnesiac")){
						ryanAltDeath = true;
					}
					ryanIcon.setEnabled(false);
					ryanRole.dispatchEvent(arg0);
				} else if(!inSession && !ryanIcon.isEnabled()){
					ryanIcon.setEnabled(true);
				} else if(!inSession && ryanIcon.isEnabled()){
					ryanIcon.setEnabled(false);
				}
			}
		});
		ryanIcon.setIcon(new ImageIcon(Window.class.getResource("/window/ryanIcon.png")));
		ryanIcon.setEnabled(false);
		
		activePlayersPanel.add(ryanIcon);
		activeRolesPanel.setPreferredSize(new Dimension(480, 960));
		activeRolesPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		activeRolesPanel.setBackground(UIManager.getColor("Button.background"));

		activeRolesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		mainTabbedPane.setBounds(402, 6, 874, 900);
		
		mainViewportPanel.add(mainTabbedPane);
		
		mainTabbedPane.addTab("Main", null, mainInfoPane, null);
		mainInfoPanel.setBackground(UIManager.getColor("Button.background"));
		mainInfoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		mainInfoPane.setViewportView(mainInfoPanel);
		mainInfoPanel.setLayout(null);
		mainInfoSplitPane.setEnabled(false);
		mainInfoSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainInfoSplitPane.setBounds(24, 24, 820, 818);
		
		mainInfoPanel.add(mainInfoSplitPane);
		mainLabel.setIcon(new ImageIcon(Window.class.getResource("/window/townOfSalemTitle.png")));
		
		mainInfoSplitPane.setLeftComponent(mainLabel);
		mainInfoGameInfoPanel.setBackground(new Color(102, 51, 0));
		mainInfoGameInfoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		mainInfoSplitPane.setRightComponent(mainInfoGameInfoPanel);
		mainInfoGameInfoPanel.setLayout(new BoxLayout(mainInfoGameInfoPanel, BoxLayout.Y_AXIS));
		gameInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		gameInfoLabel.setIcon(new ImageIcon(Window.class.getResource("/window/infoScreenScaled.png")));
		
		mainInfoGameInfoPanel.add(gameInfoLabel);
		
		mainTabbedPane.addTab("Game Setup", null, gameSetupPane, null);
		
		JPanel setupPanel = new JPanel();
		setupPanel.setBackground(UIManager.getColor("Button.background"));
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
		
		// Game Setup Buttons
		
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
		
		numPlayersComboBox.setModel(new DefaultComboBoxModel(new String[] {"7", "8", "9", "10", "11", "12", "13", "14", "15"}));
		numPlayersComboBox.setMaximumRowCount(15);
		numPlayersComboBox.setBounds(166, 209, 47, 26);
		numPlayersComboBox.setSelectedItem("8");
		numPlayersComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				String currentGameMode = gameModeComboBox.getSelectedItem().toString();
				if(currentGameMode.equals("All Any") || currentGameMode.equals("Classic") || currentGameMode.equals("Custom") || currentGameMode.equals("Vigilantics")){
					setupGameMode(currentGameMode);
				}
				calculateEnoughRoles();
			}
		});
		setupDataPanel.add(numPlayersComboBox);
		
		numPlayersLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		numPlayersLabel.setBounds(32, 209, 122, 26);
		setupDataPanel.add(numPlayersLabel);
		separator_1.setBounds(32, 281, 748, 2);
		
		setupDataPanel.add(separator_1);
		
		// Town Counts
		
		bodyguardCount = new JTextField();
		bodyguardCount.setText("0");
		bodyguardCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bodyguardCount.getText().equals("")){
					bodyguardCount.setText("0");
				} else if(Integer.parseInt(bodyguardCount.getText()) > 3 && maxThree){
					bodyguardCount.setText("3");
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
				} else if(Integer.parseInt(bodyguardCount.getText()) > 3 && maxThree){
					bodyguardCount.setText("3");
				}
				int amount = Integer.parseInt(bodyguardCount.getText().toString());
				enableRole(0, amount);
			}
		});
		bodyguardCount.setColumns(10);
		bodyguardCount.setBounds(134, 344, 20, 28);
		setupDataPanel.add(bodyguardCount);
		
		doctorCount = new JTextField();
		doctorCount.setText("0");
		doctorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(doctorCount.getText().equals("")){
					doctorCount.setText("0");
				} else if(Integer.parseInt(doctorCount.getText()) > 3 && maxThree){
					doctorCount.setText("3");
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
				} else if(Integer.parseInt(doctorCount.getText()) > 3 && maxThree){
					doctorCount.setText("3");
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
				} else if(Integer.parseInt(escortCount.getText()) > 3 && maxThree){
					escortCount.setText("3");
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
				} else if(Integer.parseInt(escortCount.getText()) > 3 && maxThree){
					escortCount.setText("3");
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
				} else if(Integer.parseInt(investigatorCount.getText()) > 3 && maxThree){
					investigatorCount.setText("3");
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
				} else if(Integer.parseInt(investigatorCount.getText()) > 3 && maxThree){
					investigatorCount.setText("3");
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
				} else if(Integer.parseInt(jailorCount.getText()) > 3 && maxThree){
					jailorCount.setText("3");
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
				} else if(Integer.parseInt(jailorCount.getText()) > 3 && maxThree){
					jailorCount.setText("3");
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
				} else if(Integer.parseInt(lookoutCount.getText()) > 3 && maxThree){
					lookoutCount.setText("3");
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
				} else if(Integer.parseInt(lookoutCount.getText()) > 3 && maxThree){
					lookoutCount.setText("3");
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
				} else if(Integer.parseInt(mayorCount.getText()) > 3 && maxThree){
					mayorCount.setText("3");
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
				} else if(Integer.parseInt(mayorCount.getText()) > 3 && maxThree){
					mayorCount.setText("3");
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
				} else if(Integer.parseInt(mediumCount.getText()) > 3 && maxThree){
					mediumCount.setText("3");
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
				} else if(Integer.parseInt(mediumCount.getText()) > 3 && maxThree){
					mediumCount.setText("3");
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
				} else if(Integer.parseInt(retributionistCount.getText()) > 3 && maxThree){
					retributionistCount.setText("3");
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
				} else if(Integer.parseInt(retributionistCount.getText()) > 3 && maxThree){
					retributionistCount.setText("3");
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
				} else if(Integer.parseInt(sheriffCount.getText()) > 3 && maxThree){
					sheriffCount.setText("3");
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
				} else if(Integer.parseInt(sheriffCount.getText()) > 3 && maxThree){
					sheriffCount.setText("3");
				}
				int amount = Integer.parseInt(sheriffCount.getText().toString());
				enableRole(9, amount);
			}
		});
		sheriffCount.setColumns(10);
		sheriffCount.setBounds(134, 686, 20, 28);
		
		setupDataPanel.add(sheriffCount);
		spyCount.setText("0");
		spyCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(spyCount.getText().equals("")){
					spyCount.setText("0");
				} else if(Integer.parseInt(spyCount.getText()) > 3 && maxThree){
					spyCount.setText("3");
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
				} else if(Integer.parseInt(spyCount.getText()) > 3 && maxThree){
					spyCount.setText("3");
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
				} else if(Integer.parseInt(transporterCount.getText()) > 3 && maxThree){
					transporterCount.setText("3");
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
				} else if(Integer.parseInt(transporterCount.getText()) > 3 && maxThree){
					transporterCount.setText("3");
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
				} else if(Integer.parseInt(veteranCount.getText()) > 3 && maxThree){
					veteranCount.setText("3");
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
				} else if(Integer.parseInt(veteranCount.getText()) > 3 && maxThree){
					veteranCount.setText("3");
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
				} else if(Integer.parseInt(vigilanteCount.getText()) > 3 && maxThree){
					vigilanteCount.setText("3");
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
				} else if(Integer.parseInt(vigilanteCount.getText()) > 3 && maxThree){
					vigilanteCount.setText("3");
				}
				int amount = Integer.parseInt(vigilanteCount.getText().toString());
				enableRole(13, amount);
			}
		});
		vigilanteCount.setColumns(10);
		vigilanteCount.setBounds(267, 457, 20, 28);
		
		setupDataPanel.add(vigilanteCount);
		
		// Town Labels
		
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
		
		// Mafia Counts
		
		blackmailerCount.setText("0");
		blackmailerCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(blackmailerCount.getText().equals("")){
					blackmailerCount.setText("0");
				} else if(Integer.parseInt(blackmailerCount.getText()) > 3 && maxThree){
					blackmailerCount.setText("3");
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
				} else if(Integer.parseInt(blackmailerCount.getText()) > 3 && maxThree){
					blackmailerCount.setText("3");
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
				} else if(Integer.parseInt(consigliereCount.getText()) > 3 && maxThree){
					consigliereCount.setText("3");
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
				} else if(Integer.parseInt(consigliereCount.getText()) > 3 && maxThree){
					consigliereCount.setText("3");
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
				} else if(Integer.parseInt(consortCount.getText()) > 3 && maxThree){
					consortCount.setText("3");
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
				} else if(Integer.parseInt(consortCount.getText()) > 3 && maxThree){
					consortCount.setText("3");
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
				} else if(Integer.parseInt(disguiserCount.getText()) > 3 && maxThree){
					disguiserCount.setText("3");
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
				} else if(Integer.parseInt(disguiserCount.getText()) > 3 && maxThree){
					disguiserCount.setText("3");
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
				} else if(Integer.parseInt(framerCount.getText()) > 3 && maxThree){
					framerCount.setText("3");
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
				} else if(Integer.parseInt(framerCount.getText()) > 3 && maxThree){
					framerCount.setText("3");
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
				} else if(Integer.parseInt(godfatherCount.getText()) > 3 && maxThree){
					godfatherCount.setText("3");
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
				} else if(Integer.parseInt(godfatherCount.getText()) > 3 && maxThree){
					godfatherCount.setText("3");
				}
				int amount = Integer.parseInt(godfatherCount.getText().toString());
				enableRole(19, amount);
			}
		});
		godfatherCount.setColumns(10);
		godfatherCount.setBounds(267, 685, 20, 28);
		
		setupDataPanel.add(godfatherCount);
		janitorCount.setText("0");
		janitorCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(janitorCount.getText().equals("")){
					janitorCount.setText("0");
				} else if(Integer.parseInt(janitorCount.getText()) > 3 && maxThree){
					janitorCount.setText("3");
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
				} else if(Integer.parseInt(janitorCount.getText()) > 3 && maxThree){
					janitorCount.setText("3");
				}
				int amount = Integer.parseInt(janitorCount.getText().toString());
				enableRole(20, amount);
			}
		});
		janitorCount.setColumns(10);
		janitorCount.setBounds(402, 344, 20, 28);
		
		setupDataPanel.add(janitorCount);
		mafiosoCount.setText("0");
		mafiosoCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mafiosoCount.getText().equals("")){
					mafiosoCount.setText("0");
				} else if(Integer.parseInt(mafiosoCount.getText()) > 3 && maxThree){
					mafiosoCount.setText("3");
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
				} else if(Integer.parseInt(mafiosoCount.getText()) > 3 && maxThree){
					mafiosoCount.setText("3");
				}
				int amount = Integer.parseInt(mafiosoCount.getText().toString());
				enableRole(21, amount);
			}
		});
		mafiosoCount.setColumns(10);
		mafiosoCount.setBounds(402, 382, 20, 28);
		
		setupDataPanel.add(mafiosoCount);
		
		// Mafia Labels
		
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
		janitorLabel.setForeground(new Color(128, 0, 0));
		janitorLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		janitorLabel.setBounds(302, 344, 108, 26);
		
		setupDataPanel.add(janitorLabel);
		mafiosoLabel.setForeground(new Color(128, 0, 0));
		mafiosoLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		mafiosoLabel.setBounds(302, 382, 108, 26);
		
		setupDataPanel.add(mafiosoLabel);
		
		// Neutral Counts
		
		amnesiacCount.setText("0");
		amnesiacCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(amnesiacCount.getText().equals("")){
					amnesiacCount.setText("0");
				} else if(Integer.parseInt(amnesiacCount.getText()) > 3 && maxThree){
					amnesiacCount.setText("3");
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
				} else if(Integer.parseInt(amnesiacCount.getText()) > 3 && maxThree){
					amnesiacCount.setText("3");
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
				} else if(Integer.parseInt(arsonistCount.getText()) > 3 && maxThree){
					arsonistCount.setText("3");
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
				} else if(Integer.parseInt(arsonistCount.getText()) > 3 && maxThree){
					arsonistCount.setText("3");
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
				} else if(Integer.parseInt(executionerCount.getText()) > 3 && maxThree){
					executionerCount.setText("3");
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
				} else if(Integer.parseInt(executionerCount.getText()) > 3 && maxThree){
					executionerCount.setText("3");
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
				} else if(Integer.parseInt(jesterCount.getText()) > 3 && maxThree){
					jesterCount.setText("3");
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
				} else if(Integer.parseInt(jesterCount.getText()) > 3 && maxThree){
					jesterCount.setText("3");
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
				} else if(Integer.parseInt(serialKillerCount.getText()) > 3 && maxThree){
					serialKillerCount.setText("3");
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
				} else if(Integer.parseInt(serialKillerCount.getText()) > 3 && maxThree){
					serialKillerCount.setText("3");
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
				} else if(Integer.parseInt(survivorCount.getText()) > 3 && maxThree){
					survivorCount.setText("3");
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
				} else if(Integer.parseInt(survivorCount.getText()) > 3 && maxThree){
					survivorCount.setText("3");
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
				} else if(Integer.parseInt(witchCount.getText()) > 3 && maxThree){
					witchCount.setText("3");
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
				} else if(Integer.parseInt(witchCount.getText()) > 3 && maxThree){
					witchCount.setText("3");
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
				} else if(Integer.parseInt(werewolfCount.getText()) > 3 && maxThree){
					werewolfCount.setText("3");
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
				} else if(Integer.parseInt(werewolfCount.getText()) > 3 && maxThree){
					werewolfCount.setText("3");
				}
				int amount = Integer.parseInt(werewolfCount.getText().toString());
				enableRole(29, amount);
			}
		});
		werewolfCount.setColumns(10);
		werewolfCount.setBounds(402, 685, 20, 28);
		
		setupDataPanel.add(werewolfCount);
		
		// Neutral Labels
		
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
		inSessionPanel.setBackground(UIManager.getColor("Button.background"));
		
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
		
		inSessionDataPanel.setBackground(UIManager.getColor("Button.background"));
		inSessionDataPanel.setLayout(null);
		inSessionDataPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		inSessionSplitPane.setRightComponent(inSessionDataPanel);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.BLACK);
		separator_5.setBackground(UIManager.getColor("Button.background"));
		separator_5.setBounds(32, 67, 748, 2);
		inSessionDataPanel.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.BLACK);
		separator_6.setBackground(UIManager.getColor("Button.background"));
		separator_6.setBounds(32, 281, 748, 2);
		inSessionDataPanel.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.BLACK);
		separator_7.setBackground(UIManager.getColor("Button.background"));
		separator_7.setBounds(32, 330, 748, 2);
		inSessionDataPanel.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setForeground(Color.BLACK);
		separator_8.setBackground(UIManager.getColor("Button.background"));
		separator_8.setOrientation(SwingConstants.VERTICAL);
		separator_8.setBounds(434, 345, 2, 368);
		inSessionDataPanel.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setForeground(Color.BLACK);
		separator_9.setBackground(UIManager.getColor("Button.background"));
		separator_9.setOrientation(SwingConstants.VERTICAL);
		separator_9.setBounds(434, 295, 2, 23);
		inSessionDataPanel.add(separator_9);
		
		endGameButton.setForeground(new Color(139, 69, 19));
		
		endGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	Object[] options = { "End Game", "Cancel" };
            	int choice = JOptionPane.showOptionDialog(frame, "Are you sure you want to end this game session?", "Warning",
            	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            	null, options, options[0]);
            	
            	if(choice == 0){
            		endGame(); // TODO endgame
            	}
			}
		});
		endGameButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 24));
		endGameButton.setEnabled(true);
		endGameButton.setBounds(592, 652, 188, 61);
		inSessionDataPanel.add(endGameButton);
		
				
		JScrollPane nightRolesScrollPane = new JScrollPane();
		nightRolesScrollPane.setBounds(32, 344, 188, 368);
		inSessionDataPanel.add(nightRolesScrollPane);
		
		nightRolesTable = new JTable();
		nightRolesTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Night Roles"
			}
		));
		nightRolesScrollPane.setViewportView(nightRolesTable);
		
		JScrollPane dayRolesScrollPane = new JScrollPane();
		dayRolesScrollPane.setBounds(234, 344, 188, 368);
		inSessionDataPanel.add(dayRolesScrollPane);
		
		dayRolesTable = new JTable();
		dayRolesTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Day Roles"
			}
		));
		dayRolesScrollPane.setViewportView(dayRolesTable);
		
		JLabel rolesActionSequenceLabel = new JLabel("Roles Action Sequence");
		rolesActionSequenceLabel.setForeground(new Color(139, 69, 19));
		rolesActionSequenceLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		rolesActionSequenceLabel.setBounds(32, 295, 203, 23);
		inSessionDataPanel.add(rolesActionSequenceLabel);
		
		final JLabel nightNumberLabel = new JLabel("Night:");
		nightNumberLabel.setForeground(new Color(139, 69, 19));
		nightNumberLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		nightNumberLabel.setBounds(32, 32, 55, 23);
		inSessionDataPanel.add(nightNumberLabel);
		
		nightNumber.setForeground(new Color(210, 105, 30));
		nightNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
		nightNumber.setBounds(91, 32, 55, 23);
		inSessionDataPanel.add(nightNumber);
		
		roleActionLabel.setForeground(new Color(139, 69, 19));
		roleActionLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		roleActionLabel.setBounds(32, 81, 188, 23);
		inSessionDataPanel.add(roleActionLabel);
		nightPhaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nightRoleNumber = 0;
				isNight = true;
				nextRoleButton.setEnabled(true);
				nextRoleButton.setForeground(new Color(139, 69, 19));
				nextRole(nightRoleNumber, roleSequenceNumber);
				nextRoleButton.requestFocus();
				nightPhaseButton.setVisible(false);
				nightPhaseButton.setEnabled(false);
				nightNumberLabel.setText("Night:");
				mainViewportPanel.setBackground(nightColor);
				inSessionPanel.setBackground(nightColor);
				inSessionDataPanel.setBackground(nightColor);
				headerPanel.setBackground(nightColor);
				rolesPanel.setBackground(nightColor);
				iconPanel.setBackground(nightColor);
				activeRolesPanel.setBackground(nightColor);
			}
		});
		nightPhaseButton.setForeground(new Color(139, 69, 19));
		nightPhaseButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		nightPhaseButton.setBounds(655, 152, 125, 84);
		nightPhaseButton.getInputMap(2).put(KeyStroke.getKeyStroke("SPACE"),
                "pressed");
		nightPhaseButton.setVisible(false);
		nightPhaseButton.setEnabled(false);
		inSessionDataPanel.add(nightPhaseButton);
		roleActionIcon.setBounds(655, 81, 125, 125);
		inSessionDataPanel.add(roleActionIcon);
		
		roleInstructionText.setBackground(new Color(214, 217, 223));
		roleInstructionText.setEditable(false);
		roleInstructionText.setFont(new Font("Verdana", Font.PLAIN, 14));
		roleInstructionText.setBounds(32, 116, 611, 153);
		inSessionDataPanel.add(roleInstructionText);
		
		final JLabel nextRoleLabel = new JLabel("Next Role:");
		nextRoleLabel.setForeground(new Color(139, 69, 19));
		nextRoleLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		nextRoleLabel.setBounds(448, 295, 203, 23);
		inSessionDataPanel.add(nextRoleLabel);
		
		
		gameModeComboBox.setSelectedItem("Classic");
		startGameButton.setForeground(new Color(139, 69, 19));
		
		startGameButton.addActionListener(new ActionListener() {
			// Start Game Button pressed
			public void actionPerformed(ActionEvent e) {
				
				// Set the mode to in-session and hide Info and Setup tabs
				inSession = true;
				mainTabbedPane.addTab("In session", null, inSessionPane, null);
				mainTabbedPane.setSelectedComponent(inSessionPane);
				mainTabbedPane.remove(mainInfoPane);
				mainTabbedPane.remove(gameSetupPane);
				
				// Disable editing and selecting in the role tables
				townTable.setEnabled(false);
				mafiaTable.setEnabled(false);
				neutralTable.setEnabled(false);
				
				// Setup display text and contextual numbers
				nextRoleLabel.setText("Next Role:");
				roleSequenceNumber = 0;
				nightNumberLabel.setText("Night:");
				nightNumber.setText("1");
				
				// Remove role setup panels from the icon selection pane
				iconTabbedPane.remove(townIconPanel);
				iconTabbedPane.remove(mafiaIconPanel);
				iconTabbedPane.remove(neutralIconPanel);
				
				// If Anonymous Roles option is not selected, add the active roles panel
				if(!anonymousRoles){
					iconTabbedPane.addTab("Active Roles", null, activeRolesPanel, null);
				}
				// Clear the active roles panel of all roles
				activeRolesPanel.removeAll();
				
				// Call setupSequence to repopulate the active roles panel with roles times the amount of each that must be added
				activeRoles = setupSequence();
				
				// Setup the arrays of active night roles
				tempNightRoles = activeRoles.get(0);
				nightRoles = tempNightRoles.toArray();
				nightRolesArray = new Object[nightRoles.length][1];
				
				numActiveNightRoles = nightRoles.length;
				
				// Initialize an empty list for dead roles				
				deadRoles = new ArrayList<String>();
				
				// Create the necessary roles into the active roles panel
				for(int i=0; i < nightRoles.length; i++){
					nightRolesArray[i] = new Object[]{(Object)nightRoles[i]};
					// Setup Roles
					if(nightRoles[i].equals("Medium")){
						for(int j=0; j < Integer.parseInt(mediumCount.getText()); j++){
							createActiveRole("Medium", "medium");
						}
					}
					if(nightRoles[i].equals("Amnesiac")){
						for(int j=0; j < Integer.parseInt(amnesiacCount.getText()); j++){
							createActiveRole("Amnesiac", "amnesiac");
						}
					}
					if(nightRoles[i].equals("Transporter")){
						for(int j=0; j < Integer.parseInt(transporterCount.getText()); j++){
							createActiveRole("Transporter", "transporter");
						}
					}
					if(nightRoles[i].equals("Jester")){
						for(int j=0; j < Integer.parseInt(jesterCount.getText()); j++){
							createActiveRole("Jester", "jester");
						}
					}
					if(nightRoles[i].equals("Witch")){
						for(int j=0; j < Integer.parseInt(witchCount.getText()); j++){
							createActiveRole("Witch", "witch");
						}
					}
					if(nightRoles[i].equals("Survivor")){
						for(int j=0; j < Integer.parseInt(survivorCount.getText()); j++){
							createActiveRole("Survivor", "survivor");
						}
					}
					if(nightRoles[i].equals("Bodyguard")){
						for(int j=0; j < Integer.parseInt(bodyguardCount.getText()); j++){
							createActiveRole("Bodyguard", "bodyguard");
						}
					}
					if(nightRoles[i].equals("Veteran")){
						for(int j=0; j < Integer.parseInt(veteranCount.getText()); j++){
							createActiveRole("Veteran", "veteran");
						}
					}
					// Preventative Roles
					if(nightRoles[i].equals("Escort")){
						for(int j=0; j < Integer.parseInt(escortCount.getText()); j++){
							createActiveRole("Escort", "escort");
						}
					}
					if(nightRoles[i].equals("Consort")){
						for(int j=0; j < Integer.parseInt(consortCount.getText()); j++){
							createActiveRole("Consort", "consort");
						}
					}
					if(nightRoles[i].equals("Doctor")){
						for(int j=0; j < Integer.parseInt(doctorCount.getText()); j++){
							createActiveRole("Doctor", "doctor");
						}
					}
					if(nightRoles[i].equals("Jailor")){
						for(int j=0; j < Integer.parseInt(jailorCount.getText()); j++){
							createActiveRole("Jailor", "jailor");
						}
					}
					// Mafia Operation Roles
					if(nightRoles[i].equals("Godfather")){
						for(int j=0; j < Integer.parseInt(godfatherCount.getText()); j++){
							createActiveRole("Godfather", "godfather");
						}
					}
					if(nightRoles[i].equals("Mafioso")){
						for(int j=0; j < Integer.parseInt(mafiosoCount.getText()); j++){
							createActiveRole("Mafioso", "mafioso");
						}
					}
					if(nightRoles[i].equals("Disguiser")){
						for(int j=0; j < Integer.parseInt(disguiserCount.getText()); j++){
							createActiveRole("Disguiser", "disguiser");
						}
					}
					if(nightRoles[i].equals("Janitor")){
						for(int j=0; j < Integer.parseInt(janitorCount.getText()); j++){
							createActiveRole("Janitor", "janitor");
						}
					}
					if(nightRoles[i].equals("Framer")){
						for(int j=0; j < Integer.parseInt(framerCount.getText()); j++){
							createActiveRole("Framer", "framer");
						}
					}
					if(nightRoles[i].equals("Blackmailer")){
						for(int j=0; j < Integer.parseInt(blackmailerCount.getText()); j++){
							createActiveRole("Blackmailer", "blackmailer");
						}
					}
					if(nightRoles[i].equals("Consigliere")){
						for(int j=0; j < Integer.parseInt(consigliereCount.getText()); j++){
							createActiveRole("Consigliere", "consigliere");
						}
					}
					if(nightRoles[i].equals("Spy")){
						for(int j=0; j < Integer.parseInt(spyCount.getText()); j++){
							createActiveRole("Spy", "spy");
						}
					}
					// Rogue Killing Roles
					if(nightRoles[i].equals("Serial Killer")){
						for(int j=0; j < Integer.parseInt(serialKillerCount.getText()); j++){
							createActiveRole("Serial Killer", "serialKiller");
						}
					}
					if(nightRoles[i].equals("Arsonist")){
						for(int j=0; j < Integer.parseInt(arsonistCount.getText()); j++){
							createActiveRole("Arsonist", "arsonist");
						}
					}
					if(nightRoles[i].equals("Vigilante")){
						for(int j=0; j < Integer.parseInt(vigilanteCount.getText()); j++){
							createActiveRole("Vigilante", "vigilante");
						}
					}
					if(nightRoles[i].equals("Werewolf")){
						for(int j=0; j < Integer.parseInt(werewolfCount.getText()); j++){
							createActiveRole("Werewolf", "werewolf");
						}
					}
					// Town Restorative Roles
					if(nightRoles[i].equals("Investigator")){
						for(int j=0; j < Integer.parseInt(investigatorCount.getText()); j++){
							createActiveRole("Investigator", "investigator");
						}
					}
					if(nightRoles[i].equals("Sheriff")){
						for(int j=0; j < Integer.parseInt(sheriffCount.getText()); j++){
							createActiveRole("Sheriff", "sheriff");
						}
					}
					if(nightRoles[i].equals("Retributionist")){
						for(int j=0; j < Integer.parseInt(retributionistCount.getText()); j++){
							createActiveRole("Retributionist", "retributionist");
						}
					}
					if(nightRoles[i].equals("Lookout")){
						for(int j=0; j < Integer.parseInt(lookoutCount.getText()); j++){
							createActiveRole("Lookout", "lookout");
						}
					}
				}
				
				// Define the night action sequence based on the array
				nightActionSequence = nightRoles;
				
				// Setup day role arrays
				List<String> tempDayRoles = activeRoles.get(1);
				Object[] dayRoles = tempDayRoles.toArray();
				Object[][] dayRolesArray = new Object[dayRoles.length][1];
				
				numActiveDayRoles = dayRoles.length;
				
				// Create the necessary roles into the active roles panel
				for(int i=0; i < dayRoles.length; i++){
					dayRolesArray[i] = new Object[]{(Object)dayRoles[i]};
					// Day Roles
					if(dayRoles[i].equals("Executioner")){
						for(int j=0; j < Integer.parseInt(executionerCount.getText()); j++){
							createActiveRole("Executioner", "executioner");
						}
					}
					if(dayRoles[i].equals("Mayor")){
						for(int j=0; j < Integer.parseInt(mayorCount.getText()); j++){
							createActiveRole("Mayor", "mayor");
						}
					}
				}
				
				// Setup the two in-session tables of active roles
				
				nightRolesTable.setModel(new DefaultTableModel(
						nightRolesArray,
						new String[] {
							"Night Action Roles"
						}
					) {
						Class[] columnTypes = new Class[] {
							String.class
						};
						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
						boolean[] columnEditables = new boolean[] {
							false
						};
						public boolean isCellEditable(int row, int column) {
							return columnEditables[column];
						}
					});
				
				dayRolesTable.setModel(new DefaultTableModel(
						dayRolesArray,
						new String[] {
							"No Night Action Roles"
						}
					) {
						Class[] columnTypes = new Class[] {
							String.class
						};
						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
						boolean[] columnEditables = new boolean[] {
							false
						};
						public boolean isCellEditable(int row, int column) {
							return columnEditables[column];
						}
					});
				

				// Setup more of the in-session text and contextual numbers
				nextRoleLabel.setText("Day Phase:");
				roleSequenceNumber = 0;
				nextRoleButton.setEnabled(false);
				nextRoleButton.setForeground(new Color(200, 200, 200));
				roleActionLabel.setText("Day Time:");
				roleActionIcon.setIcon(null);
				roleInstructionText.setText("Today is the first day. On the following days, you will vote as a town who to lynch.\n\nMany abilities cannot be used on the first night.");
				nightNumber.setText("1");
				nightNumberLabel.setText("Day:");
				nightPhaseButton.setVisible(true);
				nightPhaseButton.setEnabled(true);
				
				// Remove jester from the night roles array, because a jester can only have a night action the night following the day of his death
				if(isJester){
					tempNightRoles.remove("Jester");
					nightRoles = tempNightRoles.toArray();
	
					numActiveNightRoles--;
					nightActionSequence = nightRoles;
				}
				
				// Enable all inactive players from the active players panel
				
				if(!jacobIcon.isEnabled()){
					activePlayersPanel.remove(jacobIcon);
				}
				if(!jamieIcon.isEnabled()){
					activePlayersPanel.remove(jamieIcon);
				}
				if(!brettIcon.isEnabled()){
					activePlayersPanel.remove(brettIcon);
				}
				if(!calebIcon.isEnabled()){
					activePlayersPanel.remove(calebIcon);
				}
				if(!jeremyIcon.isEnabled()){
					activePlayersPanel.remove(jeremyIcon);
				}
				if(!dylanIcon.isEnabled()){
					activePlayersPanel.remove(dylanIcon);
				}
				if(!benIcon.isEnabled()){
					activePlayersPanel.remove(benIcon);
				}
				if(!ryanIcon.isEnabled()){
					activePlayersPanel.remove(ryanIcon);
				}
			}
		});
		startGameButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 24));
		startGameButton.setBounds(592, 652, 188, 61);
		startGameButton.setEnabled(true);
		setupDataPanel.add(startGameButton);
		
		nextRoleButton.addActionListener(new ActionListener() {
			// Next Role Button pressed
			public void actionPerformed(ActionEvent arg0) {
				// Next Role Button performs function of iterating through the night roles until it reaches the end of the list
				// As long as the current position (active night role) is less than the length of the list, increase the sequence number and call nextRole
				if(roleSequenceNumber < (numActiveNightRoles-1)){
					roleSequenceNumber++;
					nextRole(nightRoleNumber, roleSequenceNumber);
				}
				// If the sequence number is just one short of the end of the list, increase the sequence number and prepare the session for the day phase
				if(roleSequenceNumber == (numActiveNightRoles-1)){
					roleSequenceNumber++;
					nextRoleLabel.setText("End Night Phase:");
				} else if(roleSequenceNumber >= (numActiveNightRoles)){ // The sequence number has surpassed the length of the list (all roles have been sequenced)
					// Set isNight to false (day phase starts)
					isNight = false;
					
					// If a jester was active during the night, remove that jester from any consecutive nights
					if (jesterFlag) {
						tempNightRoles = activeRoles.get(0);
						Boolean jesterRemoved = tempNightRoles.remove("Jester");
						if(jesterRemoved){
							nightRoles = tempNightRoles.toArray();
							numActiveNightRoles--;
						}
						nightActionSequence = nightRoles;
						jesterFlag = false;
					}
					// Setup in-session panel for day phase
					nextRoleLabel.setText("Day Phase:");
					roleSequenceNumber = 0;
					nightRoleNumber = 0;
					nextRoleButton.setEnabled(false);
					nextRoleButton.setForeground(new Color(200, 200, 200));
					roleActionLabel.setText("Day Time:");
					roleActionIcon.setIcon(null);
					roleInstructionText.setText("Vote as a town who to lynch today.\n\nIf the vote ties, vote again or continue into the night phase.");
					Integer tempNum = Integer.parseInt(nightNumber.getText()) + 1;
					nightNumber.setText(tempNum.toString());
					nightNumberLabel.setText("Day:");
					nightPhaseButton.setVisible(true);
					nightPhaseButton.setEnabled(true);
					
					// Return to day colors
					mainViewportPanel.setBackground(dayColor);
					inSessionPanel.setBackground(dayColor);
					inSessionDataPanel.setBackground(dayColor);
					headerPanel.setBackground(dayColor);
					rolesPanel.setBackground(dayColor);
					iconPanel.setBackground(dayColor);
					activeRolesPanel.setBackground(dayColor);
				}
			}
		});
		nextRoleButton.setForeground(new Color(139, 69, 19));
		nextRoleButton.setFont(new Font("SansSerif", Font.BOLD, 36));
		nextRoleButton.setBounds(647, 295, 90, 28);
		nextRoleButton.getInputMap(2).put(KeyStroke.getKeyStroke("SPACE"),
                "pressed");
		nextRoleButton.setEnabled(false);
		inSessionDataPanel.add(nextRoleButton);
		
		// Setup session options
		optionsLabel.setForeground(new Color(139, 69, 19));
		optionsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		optionsLabel.setBounds(448, 295, 122, 23);
		
		setupDataPanel.add(optionsLabel);
		maxThreeCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(maxThree){
					maxThree = false;
				} else {
					maxThree = true;
				}
			}
		});
		
		maxThreeCheckbox.setBounds(448, 349, 174, 18);
		setupDataPanel.add(maxThreeCheckbox);
		
		JCheckBox anonymousRolesCheckbox = new JCheckBox("Roles In-Game Anonymous");
		anonymousRolesCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(anonymousRoles){
					anonymousRoles = false;
					iconTabbedPane.remove(activePlayersPanel);
				} else {
					anonymousRoles = true;
					iconTabbedPane.add("Active Players", activePlayersPanel);
				}
			}
		});
		anonymousRolesCheckbox.setBounds(448, 379, 174, 18);
		setupDataPanel.add(anonymousRolesCheckbox);
		
		setupSequence();
	}
	
	// Performs the actions required to end the game session and return the state to the setup menu
	protected void endGame() {
		// No longer in-session
		inSession = false;
		
		// Reactivate main and game setup tabs and return focus to game setup tab, remove in-session tab
		mainTabbedPane.addTab("Main", null, mainInfoPane, null);
		mainTabbedPane.addTab("Game Setup", null, gameSetupPane, null);
		mainTabbedPane.setSelectedComponent(gameSetupPane);
		mainTabbedPane.remove(inSessionPane);
		
		// Re-enable the roles tables for editing and selection
		townTable.setEnabled(true);
		mafiaTable.setEnabled(true);
		neutralTable.setEnabled(true);
		
		// Re-enable the icon pane (right-hand side of window)
		iconTabbedPane.setEnabled(true);
		
		// Clear active roles and remove active roles panel
		activeRolesPanel.removeAll();
		iconTabbedPane.remove(activeRolesPanel);
		
		// Add role icon pane tabs back into icon tabbed pane
		iconTabbedPane.addTab("Town", null, townIconPanel, null);
		iconTabbedPane.addTab("Mafia", null, mafiaIconPanel, null);
		iconTabbedPane.addTab("Neutral", null, neutralIconPanel, null);
		
		// Return to day colors
		mainViewportPanel.setBackground(dayColor);
		inSessionPanel.setBackground(dayColor);
		inSessionDataPanel.setBackground(dayColor);
		headerPanel.setBackground(dayColor);
		rolesPanel.setBackground(dayColor);
		iconPanel.setBackground(dayColor);
		activeRolesPanel.setBackground(dayColor);
		
		// Set active team counts to 0
		activeTown = 0;
		activeMafia = 0;
		activeNeutral = 0;
    	
		// Add any missing players back into player selection panel
		if(!jacobIcon.isEnabled()){
			activePlayersPanel.add(jacobIcon);
		}
		if(!jamieIcon.isEnabled()){
			activePlayersPanel.add(jamieIcon);
		}
		if(!brettIcon.isEnabled()){
			activePlayersPanel.add(brettIcon);
		}
		if(!calebIcon.isEnabled()){
			activePlayersPanel.add(calebIcon);
		}
		if(!jeremyIcon.isEnabled()){
			activePlayersPanel.add(jeremyIcon);
		}
		if(!dylanIcon.isEnabled()){
			activePlayersPanel.add(dylanIcon);
		}
		if(!benIcon.isEnabled()){
			activePlayersPanel.add(benIcon);
		}
		if(!ryanIcon.isEnabled()){
			activePlayersPanel.add(ryanIcon);
		}
		
		// Reset player flags to false
		jacobFlag = false;
		jamieFlag = false;
		brettFlag = false;
		calebFlag = false;
		jeremyFlag = false;
		dylanFlag = false;
		benFlag = false;
		ryanFlag = false;
	}

	// Adds a role to the active roles panel by establishing a temporary role and setting its attributes according to the role required, assigning it to a player if necessary
	protected JLabel createActiveRole(final String roleName, final String iconName) {
		// New temporary role to be defined
		final JLabel tempRole = new JLabel("");
		tempRole.setIcon(new ImageIcon(Window.class.getResource("/window/" + iconName + "Icon.png")));
		tempRole.setEnabled(true);
		
		// Setup listeners for this new role
		tempRole.addMouseListener(new MouseAdapter() {
			@Override
			// If this role is clicked, perform the following
			public void mousePressed(MouseEvent e) {
				if(tempRole.isEnabled() && !isNight){
					// Setup an array of options to be giving to the player to detail the death of the role, specific to certain roles
					Object[] options = {};
					if(roleName.equals("Executioner")){
		            	options = new Object[]{ "Arsonist", "Bodyguard", "Jailor", "Jester", "Mafia", "Serial Killer", "Veteran", "Vigilante", "Werewolf", "Suicide", "They were lynched!", "They didn't, their target died at night!" };
					} else if(roleName.equals("Amnesiac")){
						options = new Object[]{ "Arsonist", "Bodyguard", "Jailor", "Jester", "Mafia", "Serial Killer", "Veteran", "Vigilante", "Werewolf", "Suicide", "They were lynched!", "They selected a role!" };
					} else {
						options = new Object[]{ "Arsonist", "Bodyguard", "Jailor", "Jester", "Mafia", "Serial Killer", "Veteran", "Vigilante", "Werewolf", "Suicide", "They were lynched!" };
					}
					
					// Choice is an integer that will represent the selected value from above
	            	int choice = JOptionPane.showOptionDialog(frame, "How did this role die?", "Warning",
	            	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
	            	null, options, options[0]);
	            	
	            	// As long as a choice was made, perform the following based on the selected means of death
	            	if (options != null) {
						if (options[choice].equals("Arsonist")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByArsonist.png")));
						}
						if (options[choice].equals("Bodyguard")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByBodyguard.png")));
						}
						if (options[choice].equals("Jailor")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByJailor.png")));
						}
						if (options[choice].equals("Jester")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByJester.png")));
						}
						if (options[choice].equals("Mafia")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByMafia.png")));
						}
						if (options[choice].equals("Serial Killer")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedBySerialKiller.png")));
						}
						if (options[choice].equals("Veteran")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByVeteran.png")));
						}
						if (options[choice].equals("Vigilante")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByVigilante.png")));
						}
						if (options[choice].equals("Werewolf")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByWerewolf.png")));
						}
						if (options[choice].equals("Suicide")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByTown.png")));
						}
						if (options[choice].equals("They were lynched!")) {
							//final JLabel temp = new JLabel("");
							tempRole.setIcon(new ImageIcon(Window.class
									.getResource("/window/" + iconName
											+ "killedByTown.png")));

							// If this role was lynched and was a jester, they get to perform their night action the following night
							if (roleName.equals("Jester")) {
								if (!jesterFlag) {
									jesterFlag = true;

									deadRoles.add(roleName);
									numActiveNightRoles++;
									
									activeTown--;

									tempNightRoles.add(0, "Jester");
									nightRoles = tempNightRoles.toArray();

									nightActionSequence = nightRoles;
									roleAmount.put(roleName,
											roleAmount.get(roleName) - 1);
								} else {
									roleAmount.put(roleName,
											roleAmount.get(roleName) - 1);
								}
							}

						}
						if (options[choice].equals("They didn't, their target died at night!")) {
							// If this role was an executioner and didn't die, but rather had their target killed by means other than lynching, they become a jester
							roleAmount.put("Jester", roleAmount.get("Jester") + 1);
							isJester = true;
							
							activeRoleJLabels.put("Jester", tempRole);

							JLabel tempJester = createActiveRole("Jester", "jester");
							
							// If this executioner role was attached to a player, attach the new jester to that player instead
							if(jacobAltDeath){
								jacobAltDeath = false;
								jacobIcon.setEnabled(true);
								jacobRoleName = "Jester";
								playerMap.put(jacobIcon, tempJester);
								jacobRole = tempJester;
							} else if(jamieAltDeath){
								jamieAltDeath = false;
								jamieIcon.setEnabled(true);
								jamieRoleName = "Jester";
								playerMap.put(jamieIcon, tempJester);
								jamieRole = tempJester;
							} else if(brettAltDeath){
								brettAltDeath = false;
								brettIcon.setEnabled(true);
								brettRoleName = "Jester";
								playerMap.put(brettIcon, tempJester);
								brettRole = tempJester;
							} else if(calebAltDeath){
								calebAltDeath = false;
								calebIcon.setEnabled(true);
								calebRoleName = "Jester";
								playerMap.put(calebIcon, tempJester);
								calebRole = tempJester;
							} else if(jeremyAltDeath){
								jeremyAltDeath = false;
								jeremyIcon.setEnabled(true);
								jeremyRoleName = "Jester";
								playerMap.put(jeremyIcon, tempJester);
								jeremyRole = tempJester;
							} else if(dylanAltDeath){
								dylanAltDeath = false;
								dylanIcon.setEnabled(true);
								dylanRoleName = "Jester";
								playerMap.put(dylanIcon, tempJester);
								dylanRole = tempJester;
							} else if(benAltDeath){
								benAltDeath = false;
								benIcon.setEnabled(true);
								benRoleName = "Jester";
								playerMap.put(benIcon, tempJester);
								benRole = tempJester;
							} else if(ryanAltDeath){
								ryanAltDeath = false;
								ryanIcon.setEnabled(true);
								ryanRoleName = "Jester";
								playerMap.put(ryanIcon, tempJester);
								ryanRole = tempJester;
							}
							activeNeutral++;
						}
						if (options[choice].equals("They selected a role!")) {
							// This role was an amnesiac that has selected a role during the night
							Object[] roleOptions = deadRoles.toArray();
							int roleChoice = JOptionPane.showOptionDialog(
									frame, "Which role did they become?",
									"Warning", JOptionPane.DEFAULT_OPTION,
									JOptionPane.WARNING_MESSAGE, null,
									roleOptions, roleOptions[0]);
							
							// If the amnesiac role was attached to a player, place that player in a faux-death state so they may be reassigned the new role
							if(jacobRoleName.equals("Amnesiac") && tempRole.equals(jacobRole)){
								jacobAltDeath = true;
							} else if(jamieRoleName.equals("Amnesiac") && tempRole.equals(jamieRole)){
								jamieAltDeath = true;
							} else if(brettRoleName.equals("Amnesiac") && tempRole.equals(brettRole)){
								brettAltDeath = true;
							} else if(calebRoleName.equals("Amnesiac") && tempRole.equals(calebRole)){
								calebAltDeath = true;
							} else if(jeremyRoleName.equals("Amnesiac") && tempRole.equals(jeremyRole)){
								jeremyAltDeath = true;
							} else if(dylanRoleName.equals("Amnesiac") && tempRole.equals(dylanRole)){
								dylanAltDeath = true;
							} else if(benRoleName.equals("Amnesiac") && tempRole.equals(benRole)){
								benAltDeath = true;
							} else if(ryanRoleName.equals("Amnesiac") && tempRole.equals(ryanRole)){
								ryanAltDeath = true;
							}

							// Insert the new role into the game based on the choice made by the amnesiac
							if (roleOptions[roleChoice].equals("Bodyguard")) {
								insertNight("Bodyguard", "bodyguard");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Doctor")) {
								insertNight("Doctor", "doctor");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Escort")) {
								insertNight("Escort", "escort");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Investigator")) {
								insertNight("Investigator", "investigator");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Lookout")) {
								insertNight("Lookout", "lookout");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Medium")) {
								insertNight("Medium", "medium");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Sheriff")) {
								insertNight("Sheriff", "sheriff");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Spy")) {
								insertNight("Spy", "spy");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Transporter")) {
								insertNight("Transporter", "transporter");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Vigilante")) {
								insertNight("Vigilante", "vigilante");
								activeTown++;
							}
							if (roleOptions[roleChoice].equals("Blackmailer")) {
								insertNight("Blackmailer", "blackmailer");
								activeMafia++;
							}
							if (roleOptions[roleChoice].equals("Consigliere")) {
								insertNight("Consigliere", "consigliere");
								activeMafia++;
							}
							if (roleOptions[roleChoice].equals("Consort")) {
								insertNight("Consort", "consort");
								activeMafia++;
							}
							if (roleOptions[roleChoice].equals("Disguiser")) {
								insertNight("Disguiser", "disguiser");
								activeMafia++;
							}
							if (roleOptions[roleChoice].equals("Framer")) {
								insertNight("Framer", "framer");
								activeMafia++;
							}
							if (roleOptions[roleChoice].equals("Janitor")) {
								insertNight("Janitor", "janitor");
								activeMafia++;
							}
							if (roleOptions[roleChoice].equals("Arsonist")) {
								insertNight("Arsonist", "arsonist");
								activeNeutral++;
							}
							if (roleOptions[roleChoice].equals("Executioner")) {
								insertNight("Executioner", "executioner");
								activeNeutral++;
							}
							if (roleOptions[roleChoice].equals("Jester")) {
								insertNight("Jester", "jester");
								activeNeutral++;
							}
							if (roleOptions[roleChoice].equals("Serial Killer")) {
								insertNight("Serial Killer", "serialKiller");
								activeNeutral++;
							}
							if (roleOptions[roleChoice].equals("Survivor")) {
								insertNight("Survivor", "survivor");
								activeNeutral++;
							}
							if (roleOptions[roleChoice].equals("Witch")) {
								insertNight("Witch", "witch");
								activeNeutral++;
							}
							
							// Remove the old amnesiac icon from the active roles
							activeRolesPanel.remove(tempRole);
						}
						// Disable this role icon
						tempRole.setEnabled(false);
						
						// If the dying role was a role without a night action, decrease the number of day roles
						if (roleName.equals("Mayor")
								|| roleName.equals("Executioner")) {
							numActiveDayRoles--;
						}
						
						// If the dying role was neither a Jester or role without a night action, and it was the last of its type to die
						if (!roleName.equals("Jester")
								&& !roleName.equals("Mayor")
								&& !roleName.equals("Executioner")
								&& roleAmount.get(roleName) <= 1) {
							
							// Assume it is the first of its kind to die
							Boolean roleIsDead = false;
							
							// Check if it has already been added to the list of dead roles for an amnesiac to select from
							for (String role : deadRoles) {
								if (role.equals(roleName)) {
									// If this role already exists in the list, it was not the first of its kind to die
									roleIsDead = true;
								}
							}
							// In which case, the following won't be performed
							
							// If the role is the first of its kind to die, add it to the dead roles list
							if (!roleIsDead) {
								deadRoles.add(roleName);
							}
							
							// Decrease the amount of active roles left on the team corresponding to the dying role

							if(roleName.equals("Bodyguard")
									|| roleName.equals("Doctor")
									|| roleName.equals("Escort")
									|| roleName.equals("Investigator")
									|| roleName.equals("Jailor")
									|| roleName.equals("Lookout")
									|| roleName.equals("Mayor")
									|| roleName.equals("Medium")
									|| roleName.equals("Retributionist")
									|| roleName.equals("Sheriff")
									|| roleName.equals("Spy")
									|| roleName.equals("Transporter")
									|| roleName.equals("Veteran")
									|| roleName.equals("Vigilante")){
								activeTown--;
							} else if(roleName.equals("Blackmailer")
									|| roleName.equals("Consigliere")
									|| roleName.equals("Consort")
									|| roleName.equals("Disguiser")
									|| roleName.equals("Framer")
									|| roleName.equals("Godfather")
									|| roleName.equals("Janitor")
									|| roleName.equals("Mafioso")){
								activeMafia--;
							} else if(roleName.equals("Amnesiac")
									|| roleName.equals("Arsonist")
									|| roleName.equals("Executioner")
									|| roleName.equals("Jester")
									|| roleName.equals("Serial Killers")
									|| roleName.equals("Survivor")
									|| roleName.equals("Werewolf")
									|| roleName.equals("Witch")){
								activeNeutral--;
							}
							
							// If the dying role is a Godfather
							Godfather:
							if(roleName.equals("Godfather")){
								// For every available night role, see if one of them is a Mafioso
								for (int i = 0; i < nightRoles.length; i++) {
									// If there is a Mafioso, promote that Mafioso to Godfather, and update any attached player
									if (nightRoles[i].equals("Mafioso")) {
										JLabel oldMafioso = activeRoleJLabels.get("Mafioso");
										MouseEvent me = new MouseEvent(oldMafioso, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafioso.dispatchEvent(me);
										if(jacobRoleName.equals("Mafioso")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Mafioso")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Mafioso")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Mafioso")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Mafioso")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Mafioso")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Mafioso")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Mafioso")){
											ryanAltDeath = true;
										}
										insertNight("Godfather", "godfather");
										break Godfather;
									}
								}
								// This is reached if no Mafioso is found, now must cycle through other Mafia roles and promote on to a Mafioso
								for (int i = 0; i < nightRoles.length; i++) {
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Consigliere")) {
										System.out.println("Promoting Consigliere");
										JLabel oldMafia = activeRoleJLabels.get("Consigliere");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Consigliere")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Consigliere")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Consigliere")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Consigliere")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Consigliere")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Consigliere")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Consigliere")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Consigliere")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Blackmailer")) {
										System.out.println("Promoting Blackmailer");
										JLabel oldMafia = activeRoleJLabels.get("Blackmailer");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Blackmailer")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Blackmailer")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Blackmailer")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Blackmailer")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Blackmailer")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Blackmailer")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Blackmailer")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Blackmailer")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Framer")) {
										System.out.println("Promoting Framer");
										JLabel oldMafia = activeRoleJLabels.get("Framer");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Framer")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Framer")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Framer")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Framer")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Framer")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Framer")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Framer")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Framer")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso"); // TODO
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Janitor")) {
										System.out.println("Promoting Janitor");
										JLabel oldMafia = activeRoleJLabels.get("Janitor");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Janitor")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Janitor")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Janitor")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Janitor")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Janitor")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Janitor")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Janitor")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Janitor")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Disguiser")) {
										System.out.println("Promoting Disguiser");
										JLabel oldMafia = activeRoleJLabels.get("Disguiser");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Disguiser")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Disguiser")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Disguiser")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Disguiser")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Disguiser")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Disguiser")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Disguiser")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Disguiser")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Consort")) {
										System.out.println("Promoting Consort");
										JLabel oldMafia = activeRoleJLabels.get("Consort");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Consort")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Consort")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Consort")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Consort")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Consort")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Consort")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Consort")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Consort")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
							// Else if the dying role is a Mafioso
							} else if(roleName.equals("Mafioso")){
								// For every available night role, see if one of them is a Godfather
								for (int i = 0; i < nightRoles.length; i++) {
									// If there is a Godfather, no new role needs to be created
									if (nightRoles[i].equals("Godfather")) {
										System.out.println("Broke mafioso");
										break Godfather; 
									}
								}
								// This is reached if no Godfather is found, now must cycle through other Mafia roles and promote on to a Mafioso
								for (int i = 0; i < nightRoles.length; i++) {
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Consigliere")) {
										System.out.println("Promoting Consigliere");
										JLabel oldMafia = activeRoleJLabels.get("Consigliere");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Consigliere")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Consigliere")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Consigliere")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Consigliere")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Consigliere")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Consigliere")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Consigliere")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Consigliere")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Godfather, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Blackmailer")) {
										System.out.println("Promoting Blackmailer");
										JLabel oldMafia = activeRoleJLabels.get("Blackmailer");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Blackmailer")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Blackmailer")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Blackmailer")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Blackmailer")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Blackmailer")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Blackmailer")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Blackmailer")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Blackmailer")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Framer")) {
										System.out.println("Promoting Framer");
										JLabel oldMafia = activeRoleJLabels.get("Framer");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Framer")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Framer")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Framer")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Framer")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Framer")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Framer")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Framer")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Framer")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Janitor")) {
										System.out.println("Promoting Janitor");
										JLabel oldMafia = activeRoleJLabels.get("Janitor");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Janitor")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Janitor")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Janitor")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Janitor")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Janitor")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Janitor")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Janitor")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Janitor")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Disguiser")) {
										System.out.println("Promoting Disguiser");
										JLabel oldMafia = activeRoleJLabels.get("Disguiser");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Disguiser")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Disguiser")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Disguiser")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Disguiser")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Disguiser")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Disguiser")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Disguiser")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Disguiser")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
								for (int i = 0; i < nightRoles.length; i++){
									// If there is no Mafioso, promote this role to Mafioso, and update any attached player
									if (nightRoles[i].equals("Consort")) {
										System.out.println("Promoting Consort");
										JLabel oldMafia = activeRoleJLabels.get("Consort");
										MouseEvent me = new MouseEvent(oldMafia, MouseEvent.MOUSE_PRESSED, (long) 0.1, 0, 0, 0, 1, false);
										oldMafia.dispatchEvent(me);
										if(jacobRoleName.equals("Consort")){
											jacobAltDeath = true;
										} else if(jamieRoleName.equals("Consort")){
											jamieAltDeath = true;
										} else if(brettRoleName.equals("Consort")){
											brettAltDeath = true;
										} else if(calebRoleName.equals("Consort")){
											calebAltDeath = true;
										} else if(jeremyRoleName.equals("Consort")){
											jeremyAltDeath = true;
										} else if(dylanRoleName.equals("Consort")){
											dylanAltDeath = true;
										} else if(benRoleName.equals("Consort")){
											benAltDeath = true;
										} else if(ryanRoleName.equals("Consort")){
											ryanAltDeath = true;
										}
										insertNight("Mafioso", "mafioso");
										break Godfather;
									}
								}
							}
							
							numActiveNightRoles--;

							tempNightRoles.remove(roleName);
							nightRoles = tempNightRoles.toArray();

							nightActionSequence = nightRoles;
							roleAmount.put(roleName,
									roleAmount.get(roleName) - 1);
						} else {
							// Assume it is the first of its kind to die (expected to not be the case at this point)
							Boolean roleIsDead = false;
							
							// Check if it has already been added to the list of dead roles for an amnesiac to select from
							for (String role : deadRoles) {
								if (role.equals(roleName)) {
									// If this role already exists in the list, it was not the first of its kind to die
									roleIsDead = true;
								}
							}
							// In which case, the following won't be performed
							
							
							if (!roleIsDead) { //TODO active role counts for win condition
								deadRoles.add(roleName);
							}
							
							// Decrease the amount of active roles left on the team corresponding to the dying role
							if(roleName.equals("Bodyguard")
									|| roleName.equals("Doctor")
									|| roleName.equals("Escort")
									|| roleName.equals("Investigator")
									|| roleName.equals("Jailor")
									|| roleName.equals("Lookout")
									|| roleName.equals("Mayor")
									|| roleName.equals("Medium")
									|| roleName.equals("Retributionist")
									|| roleName.equals("Sheriff")
									|| roleName.equals("Spy")
									|| roleName.equals("Transporter")
									|| roleName.equals("Veteran")
									|| roleName.equals("Vigilante")){
								activeTown--;
							} else if(roleName.equals("Blackmailer")
									|| roleName.equals("Consigliere")
									|| roleName.equals("Consort")
									|| roleName.equals("Disguiser")
									|| roleName.equals("Framer")
									|| roleName.equals("Godfather")
									|| roleName.equals("Janitor")
									|| roleName.equals("Mafioso")){
								activeMafia--;
							} else if(roleName.equals("Amnesiac")
									|| roleName.equals("Arsonist")
									|| roleName.equals("Executioner")
									|| roleName.equals("Jester")
									|| roleName.equals("Serial Killers")
									|| roleName.equals("Survivor")
									|| roleName.equals("Werewolf")
									|| roleName.equals("Witch")){
								activeNeutral--;
							}
							
							roleAmount.put(roleName, roleAmount.get(roleName) - 1);
						}
						System.out.println("Town: " + activeTown + "	Mafia: " + activeMafia + "	Neutral: " + activeNeutral);
						
						// If this role's death decreases the amount of active town members to 0, the town loses the game
						if(activeTown <= 0){
							Object[] loseOptions = new Object[]{ "OK" };
							
			            	int loseChoice = JOptionPane.showOptionDialog(frame, "The Town has lost!", "Warning",
			            	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			            	null, loseOptions, loseOptions[0]);
			            	
			            	if (loseOptions != null) {
			            		endGame();
			            	}
						}
					}
					// If the dying role is a Jester and it is disabled and the current phase is the day phase (the jester is being reactivated)
				} else if(roleName.equals("Jester") && !tempRole.isEnabled() && !isNight){
					tempRole.setIcon(new ImageIcon(Window.class.getResource("/window/jesterIcon.png")));
					tempRole.setEnabled(true);
					if(roleAmount.get(roleName) < 1){
						if(jesterFlag){
							deadRoles.remove(roleName);
							numActiveNightRoles--;
							jesterFlag = false;
						}
						
						nightActionSequence = nightRoles;
						roleAmount.put(roleName, roleAmount.get(roleName)+1);
					} else if(roleAmount.get(roleName) < Integer.parseInt(jesterCount.getText())){
						roleAmount.put(roleName, roleAmount.get(roleName)+1);
					}
					// If the dying role is any other role that is disabled and the current phase is the day phase (it is being reactivated)
				} else if(!tempRole.isEnabled() && !isNight){
					tempRole.setIcon(new ImageIcon(Window.class.getResource("/window/" + iconName + "Icon.png")));
					tempRole.setEnabled(true);
					
					// Update all necessary night role arrays if the dying role is a role with a night action, and the only one of its kind to be alive
					if(!roleName.equals("Mayor") && !roleName.equals("Executioner") && roleAmount.get(roleName) < 1){
						deadRoles.remove(roleName);
						numActiveNightRoles++;
						
						Integer index = 0;
						
						AllNightRoles:
						for(int i = 0; i < allNightRoles.length; i++){
							for (int j = 0; j < nightRoles.length; j++) {
								if (roleName.equals(allNightRoles[i])) {
									break AllNightRoles;
								}
								if(allNightRoles[i].equals(nightRoles[j])){
									index++;
								}
							}
						}
						
						tempNightRoles.add(index, roleName);
						nightRoles = tempNightRoles.toArray();
						
						nightActionSequence = nightRoles;
						roleAmount.put(roleName, roleAmount.get(roleName)+1);
						
						// The dying role is a role with a night action, and is not the only one of its kind to be alive
					} else if(!roleName.equals("Mayor") && !roleName.equals("Executioner") && roleAmount.get(roleName) < roleCount.get(roleName)){
						roleAmount.put(roleName, roleAmount.get(roleName)+1);
					}
					
					if(roleName.equals("Bodyguard")
							|| roleName.equals("Doctor")
							|| roleName.equals("Escort")
							|| roleName.equals("Investigator")
							|| roleName.equals("Jailor")
							|| roleName.equals("Lookout")
							|| roleName.equals("Mayor")
							|| roleName.equals("Medium")
							|| roleName.equals("Retributionist")
							|| roleName.equals("Sheriff")
							|| roleName.equals("Spy")
							|| roleName.equals("Transporter")
							|| roleName.equals("Veteran")
							|| roleName.equals("Vigilante")){
						activeTown++;
					} else if(roleName.equals("Blackmailer")
							|| roleName.equals("Consigliere")
							|| roleName.equals("Consort")
							|| roleName.equals("Disguiser")
							|| roleName.equals("Framer")
							|| roleName.equals("Godfather")
							|| roleName.equals("Janitor")
							|| roleName.equals("Mafioso")){
						activeMafia++;
					} else if(roleName.equals("Amnesiac")
							|| roleName.equals("Arsonist")
							|| roleName.equals("Executioner")
							|| roleName.equals("Jester")
							|| roleName.equals("Serial Killers")
							|| roleName.equals("Survivor")
							|| roleName.equals("Werewolf")
							|| roleName.equals("Witch")){
						activeNeutral++;
					}
				}
			}
		});
		activeRolesPanel.add(tempRole);
		activeRoleJLabels.put(roleName, tempRole);
		return tempRole;
	}
	
	void insertNight(String roleName, String iconName){
		Integer index = 0;
		
		AllNightRoles:
		for(int i = 0; i < allNightRoles.length; i++){
			for (int j = 0; j < nightRoles.length; j++) {
				if (roleName.equals(allNightRoles[i])) {
					break AllNightRoles;
				}
				if(allNightRoles[i].equals(nightRoles[j])){
					index++;
				}
			}
		}
		
		roleAmount.put(roleName, roleAmount.get(roleName)+1);
		//createActiveRole(roleName, iconName);
		
		JLabel tempRoleSelection = createActiveRole(roleName, iconName);

		if(jacobAltDeath){
			jacobAltDeath = false;
			jacobIcon.setEnabled(true);
			jacobRoleName = roleName;
			playerMap.put(jacobIcon, tempRoleSelection);
			jacobRole = tempRoleSelection;
		} else if(jamieAltDeath){
			jamieAltDeath = false;
			jamieIcon.setEnabled(true);
			jamieRoleName = roleName;
			playerMap.put(jamieIcon, tempRoleSelection);
			jamieRole = tempRoleSelection;
		} else if(brettAltDeath){
			brettAltDeath = false;
			brettIcon.setEnabled(true);
			brettRoleName = roleName;
			playerMap.put(brettIcon, tempRoleSelection);
			brettRole = tempRoleSelection;
		} else if(calebAltDeath){
			calebAltDeath = false;
			calebIcon.setEnabled(true);
			calebRoleName = roleName;
			playerMap.put(calebIcon, tempRoleSelection);
			calebRole = tempRoleSelection;
		} else if(jeremyAltDeath){
			jeremyAltDeath = false;
			jeremyIcon.setEnabled(true);
			jeremyRoleName = roleName;
			playerMap.put(jeremyIcon, tempRoleSelection);
			jeremyRole = tempRoleSelection;
		} else if(dylanAltDeath){
			dylanAltDeath = false;
			dylanIcon.setEnabled(true);
			dylanRoleName = roleName;
			playerMap.put(dylanIcon, tempRoleSelection);
			dylanRole = tempRoleSelection;
		} else if(benAltDeath){
			benAltDeath = false;
			benIcon.setEnabled(true);
			benRoleName = roleName;
			playerMap.put(benIcon, tempRoleSelection);
			benRole = tempRoleSelection;
		} else if(ryanAltDeath){
			ryanAltDeath = false;
			ryanIcon.setEnabled(true);
			ryanRoleName = roleName;
			playerMap.put(ryanIcon, tempRoleSelection);
			ryanRole = tempRoleSelection;
		}
		
		Integer tempCount = roleCount.get(roleName)+1;
		roleCount.put(roleName, tempCount);
		
		if(!roleName.equals("Jester")){
			numActiveNightRoles++;
			tempNightRoles.add(index, roleName);
			nightRoles = tempNightRoles.toArray();
			nightActionSequence = nightRoles;
		}

	}

	private void nextRole(int roleNum, int sequenceNum) {
		String role = allNightRoles[roleNum];
		//System.out.print("\n" + role);
		String sequenceRole = (String)nightActionSequence[sequenceNum];
		if(role.equals(sequenceRole)){
			//System.out.print(" = " + sequenceRole);
			roleActionLabel.setText(role + ":");
			roleInstructionText.setText(roleDescriptionMap.get(role));
			roleActionIcon.setIcon(new ImageIcon(Window.class.getResource(roleIconMap.get(role))));
			nightRoleNumber++;
		} else {
			//System.out.print(" /= " + sequenceRole);
			if(nightRoleNumber < allNightRoles.length - 1){
				nightRoleNumber++;
			} else {
				nightRoleNumber = 0;
			}
			nextRole(nightRoleNumber, sequenceNum);
		}
	}
	
	List<List<String>> setupSequence() {
		List<List<String>> activeRoleLists = new ArrayList<List<String>>();
		List<String> activeNightRoles = new ArrayList<String>();
		List<String> activeDayRoles = new ArrayList<String>();
		
		activeTown = 0;
		activeMafia = 0;
		activeNeutral = 0;
		
		// Night
		// Setup Roles
		if(Integer.parseInt(mediumCount.getText()) > 0){
			activeNightRoles.add("Medium");
			activeTown += Integer.parseInt(mediumCount.getText());
			roleAmount.put("Medium", Integer.parseInt(mediumCount.getText()));
			roleCount.put("Medium", Integer.parseInt(mediumCount.getText()));
		}
		if(Integer.parseInt(amnesiacCount.getText()) > 0){
			activeNightRoles.add("Amnesiac");
			activeNeutral += Integer.parseInt(amnesiacCount.getText());
			roleAmount.put("Amnesiac", Integer.parseInt(amnesiacCount.getText()));
			roleCount.put("Amnesiac", Integer.parseInt(amnesiacCount.getText()));
		}
		if(Integer.parseInt(transporterCount.getText()) > 0){
			activeNightRoles.add("Transporter");
			activeTown += Integer.parseInt(transporterCount.getText());
			roleAmount.put("Transporter", Integer.parseInt(transporterCount.getText()));
			roleCount.put("Transporter", Integer.parseInt(transporterCount.getText()));
		}
		if(Integer.parseInt(jesterCount.getText()) > 0){
			activeNightRoles.add("Jester");
			activeNeutral += Integer.parseInt(jesterCount.getText());
			roleAmount.put("Jester", Integer.parseInt(jesterCount.getText()));
			roleCount.put("Jester", Integer.parseInt(jesterCount.getText()));
			isJester = true;
		}
		if(Integer.parseInt(witchCount.getText()) > 0){
			activeNightRoles.add("Witch");
			activeNeutral += Integer.parseInt(witchCount.getText());
			roleAmount.put("Witch", Integer.parseInt(witchCount.getText()));
			roleCount.put("Witch", Integer.parseInt(witchCount.getText()));
		}
		if(Integer.parseInt(survivorCount.getText()) > 0){
			activeNightRoles.add("Survivor");
			activeNeutral += Integer.parseInt(survivorCount.getText());
			roleAmount.put("Survivor", Integer.parseInt(survivorCount.getText()));
			roleCount.put("Survivor", Integer.parseInt(survivorCount.getText()));
		}
		if(Integer.parseInt(bodyguardCount.getText()) > 0){
			activeNightRoles.add("Bodyguard");
			activeTown += Integer.parseInt(bodyguardCount.getText());
			roleAmount.put("Bodyguard", Integer.parseInt(bodyguardCount.getText()));
			roleCount.put("Bodyguard", Integer.parseInt(bodyguardCount.getText()));
		}
		if(Integer.parseInt(veteranCount.getText()) > 0){
			activeNightRoles.add("Veteran");
			activeTown += Integer.parseInt(veteranCount.getText());
			roleAmount.put("Veteran", Integer.parseInt(veteranCount.getText()));
			roleCount.put("Veteran", Integer.parseInt(veteranCount.getText()));
		}
		
		// Preventative Roles
		if(Integer.parseInt(escortCount.getText()) > 0){
			activeNightRoles.add("Escort");
			activeTown += Integer.parseInt(escortCount.getText());
			roleAmount.put("Escort", Integer.parseInt(escortCount.getText()));
			roleCount.put("Escort", Integer.parseInt(escortCount.getText()));
		}
		if(Integer.parseInt(consortCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0 && Integer.parseInt(mafiosoCount.getText()) == 0 && Integer.parseInt(disguiserCount.getText()) == 0 && Integer.parseInt(janitorCount.getText()) == 0 && Integer.parseInt(framerCount.getText()) == 0 && Integer.parseInt(blackmailerCount.getText()) == 0 && Integer.parseInt(consigliereCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				activeNightRoles.add("Mafioso");
				roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
				roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			}
			activeNightRoles.add("Consort");
			activeMafia += Integer.parseInt(consortCount.getText());
			roleAmount.put("Consort", Integer.parseInt(consortCount.getText()));
			roleCount.put("Consort", Integer.parseInt(consortCount.getText()));
		}
		if(Integer.parseInt(doctorCount.getText()) > 0){
			activeNightRoles.add("Doctor");
			activeTown += Integer.parseInt(doctorCount.getText());
			roleAmount.put("Doctor", Integer.parseInt(doctorCount.getText()));
			roleCount.put("Doctor", Integer.parseInt(doctorCount.getText()));
		}
		if(Integer.parseInt(jailorCount.getText()) > 0){
			activeNightRoles.add("Jailor");
			activeTown += Integer.parseInt(jailorCount.getText());
			roleAmount.put("Jailor", Integer.parseInt(jailorCount.getText()));
			roleCount.put("Jailor", Integer.parseInt(jailorCount.getText()));
		}
		
		// Mafia Operation Roles
		if(Integer.parseInt(godfatherCount.getText()) > 0){
			activeNightRoles.add("Godfather");
			activeMafia += Integer.parseInt(godfatherCount.getText());
			roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
			roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
		}
		if(Integer.parseInt(mafiosoCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
			}
			activeNightRoles.add("Mafioso");
			activeMafia += Integer.parseInt(mafiosoCount.getText());
			roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
		}
		if(Integer.parseInt(disguiserCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0 && Integer.parseInt(mafiosoCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				activeNightRoles.add("Mafioso");
				roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
				roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			}
			activeNightRoles.add("Disguiser");
			activeMafia += Integer.parseInt(disguiserCount.getText());
			roleAmount.put("Disguiser", Integer.parseInt(disguiserCount.getText()));
			roleCount.put("Disguiser", Integer.parseInt(disguiserCount.getText()));
		}
		if(Integer.parseInt(janitorCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0 && Integer.parseInt(mafiosoCount.getText()) == 0 && Integer.parseInt(disguiserCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				activeNightRoles.add("Mafioso");
				roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
				roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			}
			activeNightRoles.add("Janitor");
			activeMafia += Integer.parseInt(janitorCount.getText());
			roleAmount.put("Janitor", Integer.parseInt(janitorCount.getText()));
			roleCount.put("Janitor", Integer.parseInt(janitorCount.getText()));
		}
		if(Integer.parseInt(framerCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0 && Integer.parseInt(mafiosoCount.getText()) == 0 && Integer.parseInt(disguiserCount.getText()) == 0 && Integer.parseInt(janitorCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				activeNightRoles.add("Mafioso");
				roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
				roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			}
			activeNightRoles.add("Framer");
			activeMafia += Integer.parseInt(framerCount.getText());
			roleAmount.put("Framer", Integer.parseInt(framerCount.getText()));
			roleCount.put("Framer", Integer.parseInt(framerCount.getText()));
		}
		if(Integer.parseInt(blackmailerCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0 && Integer.parseInt(mafiosoCount.getText()) == 0 && Integer.parseInt(disguiserCount.getText()) == 0 && Integer.parseInt(janitorCount.getText()) == 0 && Integer.parseInt(framerCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				activeNightRoles.add("Mafioso");
				roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
				roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			}
			activeNightRoles.add("Blackmailer");
			activeMafia += Integer.parseInt(blackmailerCount.getText());
			roleAmount.put("Blackmailer", Integer.parseInt(blackmailerCount.getText()));
			roleCount.put("Blackmailer", Integer.parseInt(blackmailerCount.getText()));
		}
		if(Integer.parseInt(consigliereCount.getText()) > 0){
			if(Integer.parseInt(godfatherCount.getText()) == 0 && Integer.parseInt(mafiosoCount.getText()) == 0 && Integer.parseInt(disguiserCount.getText()) == 0 && Integer.parseInt(janitorCount.getText()) == 0 && Integer.parseInt(framerCount.getText()) == 0 && Integer.parseInt(blackmailerCount.getText()) == 0){
				activeNightRoles.add("Godfather");
				roleAmount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				roleCount.put("Godfather", Integer.parseInt(godfatherCount.getText()));
				activeNightRoles.add("Mafioso");
				roleAmount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
				roleCount.put("Mafioso", Integer.parseInt(mafiosoCount.getText()));
			}
			activeNightRoles.add("Consigliere");
			activeMafia += Integer.parseInt(consigliereCount.getText());
			roleAmount.put("Consigliere", Integer.parseInt(consigliereCount.getText()));
			roleCount.put("Consigliere", Integer.parseInt(consigliereCount.getText()));
		}
		if(Integer.parseInt(spyCount.getText()) > 0){
			activeNightRoles.add("Spy");
			activeTown += Integer.parseInt(spyCount.getText());
			roleAmount.put("Spy", Integer.parseInt(spyCount.getText()));
			roleCount.put("Spy", Integer.parseInt(spyCount.getText()));
		}
		
		// Rogue Killing Roles
		if(Integer.parseInt(serialKillerCount.getText()) > 0){
			activeNightRoles.add("Serial Killer");
			activeNeutral += Integer.parseInt(serialKillerCount.getText());
			roleAmount.put("Serial Killer", Integer.parseInt(serialKillerCount.getText()));
			roleCount.put("Serial Killer", Integer.parseInt(serialKillerCount.getText()));
		}
		if(Integer.parseInt(arsonistCount.getText()) > 0){
			activeNightRoles.add("Arsonist");
			activeNeutral += Integer.parseInt(arsonistCount.getText());
			roleAmount.put("Arsonist", Integer.parseInt(arsonistCount.getText()));
			roleCount.put("Arsonist", Integer.parseInt(arsonistCount.getText()));
		}
		if(Integer.parseInt(vigilanteCount.getText()) > 0){
			activeNightRoles.add("Vigilante");
			activeTown += Integer.parseInt(vigilanteCount.getText());
			roleAmount.put("Vigilante", Integer.parseInt(vigilanteCount.getText()));
			roleCount.put("Vigilante", Integer.parseInt(vigilanteCount.getText()));
		}
		if(Integer.parseInt(werewolfCount.getText()) > 0){
			activeNightRoles.add("Werewolf");
			activeNeutral += Integer.parseInt(werewolfCount.getText());
			roleAmount.put("Werewolf", Integer.parseInt(werewolfCount.getText()));
			roleCount.put("Werewolf", Integer.parseInt(werewolfCount.getText()));
		}
		
		// Town Restorative Roles
		if(Integer.parseInt(investigatorCount.getText()) > 0){
			activeNightRoles.add("Investigator");
			activeTown += Integer.parseInt(investigatorCount.getText());
			roleAmount.put("Investigator", Integer.parseInt(investigatorCount.getText()));
			roleCount.put("Investigator", Integer.parseInt(investigatorCount.getText()));
		}
		if(Integer.parseInt(sheriffCount.getText()) > 0){
			activeNightRoles.add("Sheriff");
			activeTown += Integer.parseInt(sheriffCount.getText());
			roleAmount.put("Sheriff", Integer.parseInt(sheriffCount.getText()));
			roleCount.put("Sheriff", Integer.parseInt(sheriffCount.getText()));
		}
		if(Integer.parseInt(retributionistCount.getText()) > 0){
			activeNightRoles.add("Retributionist");
			activeTown += Integer.parseInt(retributionistCount.getText());
			roleAmount.put("Retributionist", Integer.parseInt(retributionistCount.getText()));
			roleCount.put("Retributionist", Integer.parseInt(retributionistCount.getText()));
		}
		if(Integer.parseInt(lookoutCount.getText()) > 0){
			activeNightRoles.add("Lookout");
			activeTown += Integer.parseInt(lookoutCount.getText());
			roleAmount.put("Lookout", Integer.parseInt(lookoutCount.getText()));
			roleCount.put("Lookout", Integer.parseInt(lookoutCount.getText()));
		}

		
		// Day
		if(Integer.parseInt(executionerCount.getText()) > 0){
			activeDayRoles.add("Executioner");
			activeNeutral += Integer.parseInt(executionerCount.getText());
			roleAmount.put("Executioner", Integer.parseInt(executionerCount.getText()));
			roleCount.put("Executioner", Integer.parseInt(executionerCount.getText()));
		}
		if(Integer.parseInt(mayorCount.getText()) > 0){
			activeDayRoles.add("Mayor");
			activeTown += Integer.parseInt(mayorCount.getText());
			roleAmount.put("Mayor", Integer.parseInt(mayorCount.getText()));
			roleCount.put("Mayor", Integer.parseInt(mayorCount.getText()));
		}
		
		activeRoleLists.add(activeNightRoles);
		activeRoleLists.add(activeDayRoles);

		System.out.println("Town: " + activeTown + "	Mafia: " + activeMafia + "	Neutral: " + activeNeutral);
		
		return activeRoleLists;
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
			setupSequence();
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
				roleCount.put("Bodyguard", 0);
				break;
			case 1: // Doctor
				townTable.setValueAt(Boolean.FALSE, 1, 2);
				doctorIcon.setEnabled(false);
				doctorCount.setText("0");
				roleCount.put("Doctor", 0);
				break;
			case 2: // Escort
				townTable.setValueAt(Boolean.FALSE, 2, 2);
				escortIcon.setEnabled(false);
				escortCount.setText("0");
				roleCount.put("Escort", 0);
				break;
			case 3: // Investigator
				townTable.setValueAt(Boolean.FALSE, 3, 2);
				investigatorIcon.setEnabled(false);
				investigatorCount.setText("0");
				roleCount.put("Investigator", 0);
				break;
			case 4: // Jailor
				townTable.setValueAt(Boolean.FALSE, 4, 2);
				jailorIcon.setEnabled(false);
				jailorCount.setText("0");
				roleCount.put("Jailor", 0);
				break;
			case 5: // Lookout
				townTable.setValueAt(Boolean.FALSE, 5, 2);
				lookoutIcon.setEnabled(false);
				lookoutCount.setText("0");
				roleCount.put("Lookout", 0);
				break;
			case 6: // Mayor
				townTable.setValueAt(Boolean.FALSE, 6, 2);
				mayorIcon.setEnabled(false);
				mayorCount.setText("0");
				roleCount.put("Mayor", 0);
				break;
			case 7: // Medium
				townTable.setValueAt(Boolean.FALSE, 7, 2);
				mediumIcon.setEnabled(false);
				mediumCount.setText("0");
				roleCount.put("Medium", 0);
				break;
			case 8: // Retributionist
				townTable.setValueAt(Boolean.FALSE, 8, 2);
				retributionistIcon.setEnabled(false);
				retributionistCount.setText("0");
				roleCount.put("Retributionist", 0);
				break;
			case 9: // Sheriff
				townTable.setValueAt(Boolean.FALSE, 9, 2);
				sheriffIcon.setEnabled(false);
				sheriffCount.setText("0");
				roleCount.put("Sheriff", 0);
				break;
			case 10: // Spy
				townTable.setValueAt(Boolean.FALSE, 10, 2);
				spyIcon.setEnabled(false);
				spyCount.setText("0");
				roleCount.put("Spy", 0);
				break;
			case 11: // Transporter
				townTable.setValueAt(Boolean.FALSE, 11, 2);
				transporterIcon.setEnabled(false);
				transporterCount.setText("0");
				roleCount.put("Transporter", 0);
				break;
			case 12: // Veteran
				townTable.setValueAt(Boolean.FALSE, 12, 2);
				veteranIcon.setEnabled(false);
				veteranCount.setText("0");
				roleCount.put("Veteran", 0);
				break;
			case 13: // Vigilante
				townTable.setValueAt(Boolean.FALSE, 13, 2);
				vigilanteIcon.setEnabled(false);
				vigilanteCount.setText("0");
				roleCount.put("Vigilante", 0);
				break;

			// Mafia Roles
			case 14: // Blackmailer
				mafiaTable.setValueAt(Boolean.FALSE, 0, 2);
				blackmailerIcon.setEnabled(false);
				blackmailerCount.setText("0");
				roleCount.put("Blackmailer", 0);
				break;
			case 15: // Consigliere
				mafiaTable.setValueAt(Boolean.FALSE, 1, 2);
				consigliereIcon.setEnabled(false);
				consigliereCount.setText("0");
				roleCount.put("Consigliere", 0);
				break;
			case 16: // Consort
				mafiaTable.setValueAt(Boolean.FALSE, 2, 2);
				consortIcon.setEnabled(false);
				consortCount.setText("0");
				roleCount.put("Consort", 0);
				break;
			case 17: // Disguiser
				mafiaTable.setValueAt(Boolean.FALSE, 3, 2);
				disguiserIcon.setEnabled(false);
				disguiserCount.setText("0");
				roleCount.put("Disguiser", 0);
				break;
			case 18: // Framer
				mafiaTable.setValueAt(Boolean.FALSE, 4, 2);
				framerIcon.setEnabled(false);
				framerCount.setText("0");
				roleCount.put("Framer", 0);
				break;
			case 19: // Godfather
				mafiaTable.setValueAt(Boolean.FALSE, 5, 2);
				godfatherIcon.setEnabled(false);
				godfatherCount.setText("0");
				roleCount.put("Godfather", 0);
				break;
			case 20: // Janitor
				mafiaTable.setValueAt(Boolean.FALSE, 6, 2);
				janitorIcon.setEnabled(false);
				janitorCount.setText("0");
				roleCount.put("Janitor", 0);
				break;
			case 21: // Mafioso
				mafiaTable.setValueAt(Boolean.FALSE, 7, 2);
				mafiosoIcon.setEnabled(false);
				mafiosoCount.setText("0");
				roleCount.put("Mafioso", 0);
				break;

			// Neutral Roles
			case 22: // Amnesiac
				neutralTable.setValueAt(Boolean.FALSE, 0, 2);
				amnesiacIcon.setEnabled(false);
				amnesiacCount.setText("0");
				roleCount.put("Amnesiac", 0);
				break;
			case 23: // Arsonist
				neutralTable.setValueAt(Boolean.FALSE, 1, 2);
				arsonistIcon.setEnabled(false);
				arsonistCount.setText("0");
				roleCount.put("Arsonist", 0);
				break;
			case 24: // Executioner
				neutralTable.setValueAt(Boolean.FALSE, 2, 2);
				executionerIcon.setEnabled(false);
				executionerCount.setText("0");
				roleCount.put("Executioner", 0);
				break;
			case 25: // Jester
				neutralTable.setValueAt(Boolean.FALSE, 3, 2);
				jesterIcon.setEnabled(false);
				jesterCount.setText("0");
				roleCount.put("Jester", 0);
				break;
			case 26: // Serial Killer
				neutralTable.setValueAt(Boolean.FALSE, 4, 2);
				serialKillerIcon.setEnabled(false);
				serialKillerCount.setText("0");
				roleCount.put("Serial Killer", 0);
				break;
			case 27: // Survivor
				neutralTable.setValueAt(Boolean.FALSE, 5, 2);
				survivorIcon.setEnabled(false);
				survivorCount.setText("0");
				roleCount.put("Survivor", 0);
				break;
			case 28: // Witch
				neutralTable.setValueAt(Boolean.FALSE, 6, 2);
				witchIcon.setEnabled(false);
				witchCount.setText("0");
				roleCount.put("Witch", 0);
				break;
			case 29: // Werewolf
				neutralTable.setValueAt(Boolean.FALSE, 7, 2);
				werewolfIcon.setEnabled(false);
				werewolfCount.setText("0");
				roleCount.put("Werewolf", 0);
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
				roleCount.put("Bodyguard", amount);
				break;
			case 1: // Doctor
				townTable.setValueAt(Boolean.TRUE, 1, 2);
				doctorIcon.setEnabled(true);
				doctorCount.setText(amount.toString());
				roleCount.put("Doctor", amount);
				break;
			case 2: // Escort
				townTable.setValueAt(Boolean.TRUE, 2, 2);
				escortIcon.setEnabled(true);
				escortCount.setText(amount.toString());
				roleCount.put("Escort", amount);
				break;
			case 3: // Investigator
				townTable.setValueAt(Boolean.TRUE, 3, 2);
				investigatorIcon.setEnabled(true);
				investigatorCount.setText(amount.toString());
				roleCount.put("Investigator", amount);
				break;
			case 4: // Jailor
				townTable.setValueAt(Boolean.TRUE, 4, 2);
				jailorIcon.setEnabled(true);
				jailorCount.setText(amount.toString());
				roleCount.put("Jailor", amount);
				break;
			case 5: // Lookout
				townTable.setValueAt(Boolean.TRUE, 5, 2);
				lookoutIcon.setEnabled(true);
				lookoutCount.setText(amount.toString());
				roleCount.put("Lookout", amount);
				break;
			case 6: // Mayor
				townTable.setValueAt(Boolean.TRUE, 6, 2);
				mayorIcon.setEnabled(true);
				mayorCount.setText(amount.toString());
				roleCount.put("Mayor", amount);
				break;
			case 7: // Medium
				townTable.setValueAt(Boolean.TRUE, 7, 2);
				mediumIcon.setEnabled(true);
				mediumCount.setText(amount.toString());
				roleCount.put("Medium", amount);
				break;
			case 8: // Retributionist
				townTable.setValueAt(Boolean.TRUE, 8, 2);
				retributionistIcon.setEnabled(true);
				retributionistCount.setText(amount.toString());
				roleCount.put("Retributionist", amount);
				break;
			case 9: // Sheriff
				townTable.setValueAt(Boolean.TRUE, 9, 2);
				sheriffIcon.setEnabled(true);
				sheriffCount.setText(amount.toString());
				roleCount.put("Sheriff", amount);
				break;
			case 10: // Spy
				townTable.setValueAt(Boolean.TRUE, 10, 2);
				spyIcon.setEnabled(true);
				spyCount.setText(amount.toString());
				roleCount.put("Spy", amount);
				break;
			case 11: // Transporter
				townTable.setValueAt(Boolean.TRUE, 11, 2);
				transporterIcon.setEnabled(true);
				transporterCount.setText(amount.toString());
				roleCount.put("Transporter", amount);
				break;
			case 12: // Veteran
				townTable.setValueAt(Boolean.TRUE, 12, 2);
				veteranIcon.setEnabled(true);
				veteranCount.setText(amount.toString());
				roleCount.put("Veteran", amount);
				break;
			case 13: // Vigilante
				townTable.setValueAt(Boolean.TRUE, 13, 2);
				vigilanteIcon.setEnabled(true);
				vigilanteCount.setText(amount.toString());
				roleCount.put("Vigilante", amount);
				break;

			// Mafia Roles
			case 14: // Blackmailer
				mafiaTable.setValueAt(Boolean.TRUE, 0, 2);
				blackmailerIcon.setEnabled(true);
				blackmailerCount.setText(amount.toString());
				roleCount.put("Blackmailer", amount);
				break;
			case 15: // Consigliere
				mafiaTable.setValueAt(Boolean.TRUE, 1, 2);
				consigliereIcon.setEnabled(true);
				consigliereCount.setText(amount.toString());
				roleCount.put("Consigliere", amount);
				break;
			case 16: // Consort
				mafiaTable.setValueAt(Boolean.TRUE, 2, 2);
				consortIcon.setEnabled(true);
				consortCount.setText(amount.toString());
				roleCount.put("Consort", amount);
				break;
			case 17: // Disguiser
				mafiaTable.setValueAt(Boolean.TRUE, 3, 2);
				disguiserIcon.setEnabled(true);
				disguiserCount.setText(amount.toString());
				roleCount.put("Disguiser", amount);
				break;
			case 18: // Framer
				mafiaTable.setValueAt(Boolean.TRUE, 4, 2);
				framerIcon.setEnabled(true);
				framerCount.setText(amount.toString());
				roleCount.put("Framer", amount);
				break;
			case 19: // Godfather
				mafiaTable.setValueAt(Boolean.TRUE, 5, 2);
				godfatherIcon.setEnabled(true);
				godfatherCount.setText(amount.toString());
				roleCount.put("Godfather", amount);
				break;
			case 20: // Janitor
				mafiaTable.setValueAt(Boolean.TRUE, 6, 2);
				janitorIcon.setEnabled(true);
				janitorCount.setText(amount.toString());
				roleCount.put("Janitor", amount);
				break;
			case 21: // Mafioso
				mafiaTable.setValueAt(Boolean.TRUE, 7, 2);
				mafiosoIcon.setEnabled(true);
				mafiosoCount.setText(amount.toString());
				roleCount.put("Mafioso", amount);
				break;

			// Neutral Roles
			case 22: // Amnesiac
				neutralTable.setValueAt(Boolean.TRUE, 0, 2);
				amnesiacIcon.setEnabled(true);
				amnesiacCount.setText(amount.toString());
				roleCount.put("Amnesiac", amount);
				break;
			case 23: // Arsonist
				neutralTable.setValueAt(Boolean.TRUE, 1, 2);
				arsonistIcon.setEnabled(true);
				arsonistCount.setText(amount.toString());
				roleCount.put("Arsonist", amount);
				break;
			case 24: // Executioner
				neutralTable.setValueAt(Boolean.TRUE, 2, 2);
				executionerIcon.setEnabled(true);
				executionerCount.setText(amount.toString());
				roleCount.put("Executioner", amount);
				break;
			case 25: // Jester
				neutralTable.setValueAt(Boolean.TRUE, 3, 2);
				jesterIcon.setEnabled(true);
				jesterCount.setText(amount.toString());
				roleCount.put("Jester", amount);
				break;
			case 26: // Serial Killer
				neutralTable.setValueAt(Boolean.TRUE, 4, 2);
				serialKillerIcon.setEnabled(true);
				serialKillerCount.setText(amount.toString());
				roleCount.put("Serial Killer", amount);
				break;
			case 27: // Survivor
				neutralTable.setValueAt(Boolean.TRUE, 5, 2);
				survivorIcon.setEnabled(true);
				survivorCount.setText(amount.toString());
				roleCount.put("Survivor", amount);
				break;
			case 28: // Witch
				neutralTable.setValueAt(Boolean.TRUE, 6, 2);
				witchIcon.setEnabled(true);
				witchCount.setText(amount.toString());
				roleCount.put("Witch", amount);
				break;
			case 29: // Werewolf
				neutralTable.setValueAt(Boolean.TRUE, 7, 2);
				werewolfIcon.setEnabled(true);
				werewolfCount.setText(amount.toString());
				roleCount.put("Werewolf", amount);
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
			deleteModeButton.setEnabled(true);
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
				setupSavedRoles(Integer.parseInt(numPlayersComboBox.getSelectedItem().toString()), mode);
		 
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
	
	void setupSavedRoles(int numPlayers, String mode) {
		disableAllRoles();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(mode + ".properties");
			prop.load(input);
			if(prop.getProperty("bodyguard").equals("enabled")){
				enableRole(0, 1);
				bodyguardCount.setText(prop.getProperty("bodyguardCount"));
				roleCount.put("Bodyguard", Integer.parseInt(prop.getProperty("bodyguardCount")));
			}
			if(prop.getProperty("doctor").equals("enabled")){
				enableRole(1, 1);
				doctorCount.setText(prop.getProperty("doctorCount"));
				roleCount.put("Doctor", Integer.parseInt(prop.getProperty("doctorCount")));
			}
			if(prop.getProperty("escort").equals("enabled")){
				enableRole(2, 1);
				escortCount.setText(prop.getProperty("escortCount"));
				roleCount.put("Escort", Integer.parseInt(prop.getProperty("escortCount")));
			}
			if(prop.getProperty("investigator").equals("enabled")){
				enableRole(3, 1);
				investigatorCount.setText(prop.getProperty("investigatorCount"));
				roleCount.put("Investigator", Integer.parseInt(prop.getProperty("investigatorCount")));
			}
			if(prop.getProperty("jailor").equals("enabled")){
				enableRole(4, 1);
				jailorCount.setText(prop.getProperty("jailorCount"));
				roleCount.put("Jailor", Integer.parseInt(prop.getProperty("jailorCount")));
			}
			if(prop.getProperty("lookout").equals("enabled")){
				enableRole(5, 1);
				lookoutCount.setText(prop.getProperty("lookoutCount"));
				roleCount.put("Lookout", Integer.parseInt(prop.getProperty("lookoutCount")));
			}
			if(prop.getProperty("mayor").equals("enabled")){
				enableRole(6, 1);
				mayorCount.setText(prop.getProperty("mayorCount"));
				roleCount.put("Mayor", Integer.parseInt(prop.getProperty("mayorCount")));
			}
			if(prop.getProperty("medium").equals("enabled")){
				enableRole(7, 1);
				mediumCount.setText(prop.getProperty("mediumCount"));
				roleCount.put("Medium", Integer.parseInt(prop.getProperty("mediumCount")));
			}
			if(prop.getProperty("retributionist").equals("enabled")){
				enableRole(8, 1);
				retributionistCount.setText(prop.getProperty("retributionistCount"));
				roleCount.put("Retibutionist", Integer.parseInt(prop.getProperty("retributionistCount")));
			}
			if(prop.getProperty("sheriff").equals("enabled")){
				enableRole(9, 1);
				sheriffCount.setText(prop.getProperty("sheriffCount"));
				roleCount.put("Sheriff", Integer.parseInt(prop.getProperty("sheriffCount")));
			}
			if(prop.getProperty("spy").equals("enabled")){
				enableRole(10, 1);
				spyCount.setText(prop.getProperty("spyCount"));
				roleCount.put("Spy", Integer.parseInt(prop.getProperty("spyCount")));
			}
			if(prop.getProperty("transporter").equals("enabled")){
				enableRole(11, 1);
				transporterCount.setText(prop.getProperty("transporterCount"));
				roleCount.put("Transporter", Integer.parseInt(prop.getProperty("transporterCount")));
			}
			if(prop.getProperty("veteran").equals("enabled")){
				enableRole(12, 1);
				veteranCount.setText(prop.getProperty("veteranCount"));
				roleCount.put("Veteran", Integer.parseInt(prop.getProperty("veteranCount")));
			}
			if(prop.getProperty("vigilante").equals("enabled")){
				enableRole(13, 1);
				vigilanteCount.setText(prop.getProperty("vigilanteCount"));
				roleCount.put("Vigilante", Integer.parseInt(prop.getProperty("vigilanteCount")));
			}

			if(prop.getProperty("blackmailer").equals("enabled")){
				enableRole(14, 1);
				blackmailerCount.setText(prop.getProperty("blackmailerCount"));
				roleCount.put("Blackmailer", Integer.parseInt(prop.getProperty("blackmailerCount")));
			}
			if(prop.getProperty("consigliere").equals("enabled")){
				enableRole(15, 1);
				consigliereCount.setText(prop.getProperty("consigliereCount"));
				roleCount.put("Consigliere", Integer.parseInt(prop.getProperty("consigliereCount")));
			}
			if(prop.getProperty("consort").equals("enabled")){
				enableRole(16, 1);
				consortCount.setText(prop.getProperty("consortCount"));
				roleCount.put("Consort", Integer.parseInt(prop.getProperty("consortCount")));
			}
			if(prop.getProperty("disguiser").equals("enabled")){
				enableRole(17, 1);
				disguiserCount.setText(prop.getProperty("disguiserCount"));
				roleCount.put("Disguiser", Integer.parseInt(prop.getProperty("disguiserCount")));
			}
			if(prop.getProperty("framer").equals("enabled")){
				enableRole(18, 1);
				framerCount.setText(prop.getProperty("framerCount"));
				roleCount.put("Framer", Integer.parseInt(prop.getProperty("framerCount")));
			}
			if(prop.getProperty("godfather").equals("enabled")){
				enableRole(19, 1);
				godfatherCount.setText(prop.getProperty("godfatherCount"));
				roleCount.put("Godfather", Integer.parseInt(prop.getProperty("godfatherCount")));
			}
			if(prop.getProperty("janitor").equals("enabled")){
				enableRole(20, 1);
				janitorCount.setText(prop.getProperty("janitorCount"));
				roleCount.put("Janitor", Integer.parseInt(prop.getProperty("janitorCount")));
			}
			if(prop.getProperty("mafioso").equals("enabled")){
				enableRole(21, 1);
				mafiosoCount.setText(prop.getProperty("mafiosoCount"));
				roleCount.put("Mafioso", Integer.parseInt(prop.getProperty("mafiosoCount")));
			}

			if(prop.getProperty("amnesiac").equals("enabled")){
				enableRole(22, 1);
				amnesiacCount.setText(prop.getProperty("amnesiacCount"));
				roleCount.put("Amnesiac", Integer.parseInt(prop.getProperty("amnesiacCount")));
			}
			if(prop.getProperty("arsonist").equals("enabled")){
				enableRole(23, 1);
				arsonistCount.setText(prop.getProperty("arsonistCount"));
				roleCount.put("Arsonist", Integer.parseInt(prop.getProperty("arsonistCount")));
			}
			if(prop.getProperty("executioner").equals("enabled")){
				enableRole(24, 1);
				executionerCount.setText(prop.getProperty("executionerCount"));
				roleCount.put("Executioner", Integer.parseInt(prop.getProperty("executionerCount")));
			}
			if(prop.getProperty("jester").equals("enabled")){
				enableRole(25, 1);
				jesterCount.setText(prop.getProperty("jesterCount"));
				roleCount.put("Jester", Integer.parseInt(prop.getProperty("jesterCount")));
			}
			if(prop.getProperty("serialKiller").equals("enabled")){
				enableRole(26, 1);
				serialKillerCount.setText(prop.getProperty("serialKillerCount"));
				roleCount.put("Serial Killer", Integer.parseInt(prop.getProperty("serialKillerCount")));
			}
			if(prop.getProperty("survivor").equals("enabled")){
				enableRole(27, 1);
				survivorCount.setText(prop.getProperty("survivorCount"));
				roleCount.put("Survivor", Integer.parseInt(prop.getProperty("survivorCount")));
			}
			if(prop.getProperty("witch").equals("enabled")){
				enableRole(28, 1);
				witchCount.setText(prop.getProperty("witchCount"));
				roleCount.put("Witch", Integer.parseInt(prop.getProperty("witchCount")));
			}
			if(prop.getProperty("werewolf").equals("enabled")){
				enableRole(29, 1);
				werewolfCount.setText(prop.getProperty("werewolfCount"));
				roleCount.put("Werewolf", Integer.parseInt(prop.getProperty("werewolfCount")));
			}
		} catch (IOException e) {
			e.printStackTrace();
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
					if (godfatherFlag || mafiosoFlag){
						amount = Integer.parseInt(blackmailerCount.getText());
						amount++;
						enableRole(14, amount);
					} else {
						i--;
					}
					break;
				case 16:
					if (godfatherFlag || mafiosoFlag) {
						amount = Integer.parseInt(consigliereCount.getText());
						amount++;
						enableRole(15, amount);
					} else {
						i--;
					}
					break;
				case 17:
					if (godfatherFlag || mafiosoFlag){
						amount = Integer.parseInt(consortCount.getText());
						amount++;
						enableRole(16, amount);
					} else {
						i--;
					}
					break;
				case 18:
					if (godfatherFlag || mafiosoFlag){
						amount = Integer.parseInt(disguiserCount.getText());
						amount++;
						enableRole(17, amount);
					} else {
						i--;
					}
					break;
				case 19:
					if (godfatherFlag || mafiosoFlag){
						amount = Integer.parseInt(framerCount.getText());
						amount++;
						enableRole(18, amount);
					} else {
						i--;
					}
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
					if (godfatherFlag || mafiosoFlag){
						amount = Integer.parseInt(janitorCount.getText());
						amount++;
						enableRole(20, amount);
					} else {
						i--;
					}
					break;
				case 22: // Unique
					if(!mafiosoFlag && godfatherFlag){
						mafiosoFlag = true;
						enableRole(21, 1);
					} else {
						i--;
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
				amount = Integer.parseInt(jesterCount.getText());
				amount++;
				enableRole(25, amount);
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
			prop.setProperty("bodyguardCount", bodyguardCount.getText());
			
			if(doctorIcon.isEnabled()){
				prop.setProperty("doctor", "enabled");
			} else {
				prop.setProperty("doctor", "disabled");
			}
			prop.setProperty("doctorCount", doctorCount.getText());
			
			if(escortIcon.isEnabled()){
				prop.setProperty("escort", "enabled");
			} else {
				prop.setProperty("escort", "disabled");
			}
			prop.setProperty("escortCount", escortCount.getText());
			
			if(investigatorIcon.isEnabled()){
				prop.setProperty("investigator", "enabled");
			} else {
				prop.setProperty("investigator", "disabled");
			}
			prop.setProperty("investigatorCount", investigatorCount.getText());
			
			if(jailorIcon.isEnabled()){
				prop.setProperty("jailor", "enabled");
			} else {
				prop.setProperty("jailor", "disabled");
			}
			prop.setProperty("jailorCount", jailorCount.getText());
			
			if(lookoutIcon.isEnabled()){
				prop.setProperty("lookout", "enabled");
			} else {
				prop.setProperty("lookout", "disabled");
			}
			prop.setProperty("lookoutCount", lookoutCount.getText());
			
			if(mayorIcon.isEnabled()){
				prop.setProperty("mayor", "enabled");
			} else {
				prop.setProperty("mayor", "disabled");
			}
			prop.setProperty("mayorCount", mayorCount.getText());
			
			if(mediumIcon.isEnabled()){
				prop.setProperty("medium", "enabled");
			} else {
				prop.setProperty("medium", "disabled");
			}
			prop.setProperty("mediumCount", mediumCount.getText());
			
			if(retributionistIcon.isEnabled()){
				prop.setProperty("retributionist", "enabled");
			} else {
				prop.setProperty("retributionist", "disabled");
			}
			prop.setProperty("retributionistCount", retributionistCount.getText());
			
			if(sheriffIcon.isEnabled()){
				prop.setProperty("sheriff", "enabled");
			} else {
				prop.setProperty("sheriff", "disabled");
			}
			prop.setProperty("sheriffCount", sheriffCount.getText());
			
			if(spyIcon.isEnabled()){
				prop.setProperty("spy", "enabled");
			} else {
				prop.setProperty("spy", "disabled");
			}
			prop.setProperty("spyCount", spyCount.getText());
			
			if(transporterIcon.isEnabled()){
				prop.setProperty("transporter", "enabled");
			} else {
				prop.setProperty("transporter", "disabled");
			}
			prop.setProperty("transporterCount", transporterCount.getText());
			
			if(veteranIcon.isEnabled()){
				prop.setProperty("veteran", "enabled");
			} else {
				prop.setProperty("veteran", "disabled");
			}
			prop.setProperty("veteranCount", veteranCount.getText());
			
			if(vigilanteIcon.isEnabled()){
				prop.setProperty("vigilante", "enabled");
			} else {
				prop.setProperty("vigilante", "disabled");
			}
			prop.setProperty("vigilanteCount", vigilanteCount.getText());
			
			
			if(blackmailerIcon.isEnabled()){			
				prop.setProperty("blackmailer", "enabled");
			} else {
				prop.setProperty("blackmailer", "disabled");
			}
			prop.setProperty("blackmailerCount", blackmailerCount.getText());
			
			if(consigliereIcon.isEnabled()){
				prop.setProperty("consigliere", "enabled");
			} else {
				prop.setProperty("consigliere", "disabled");
			}
			prop.setProperty("consigliereCount", consigliereCount.getText());
			
			if(consortIcon.isEnabled()){
				prop.setProperty("consort", "enabled");
			} else {
				prop.setProperty("consort", "disabled");
			}
			prop.setProperty("consortCount", consortCount.getText());
			
			if(disguiserIcon.isEnabled()){
				prop.setProperty("disguiser", "enabled");
			} else {
				prop.setProperty("disguiser", "disabled");
			}
			prop.setProperty("disguiserCount", disguiserCount.getText());
			
			if(framerIcon.isEnabled()){
				prop.setProperty("framer", "enabled");
			} else {
				prop.setProperty("framer", "disabled");
			}
			prop.setProperty("framerCount", framerCount.getText());
			
			if(godfatherIcon.isEnabled()){
				prop.setProperty("godfather", "enabled");
			} else {
				prop.setProperty("godfather", "disabled");
			}
			prop.setProperty("godfatherCount", godfatherCount.getText());
			
			if(janitorIcon.isEnabled()){
				prop.setProperty("janitor", "enabled");
			} else {
				prop.setProperty("janitor", "disabled");
			}
			prop.setProperty("janitorCount", janitorCount.getText());
			
			if(mafiosoIcon.isEnabled()){
				prop.setProperty("mafioso", "enabled");
			} else {
				prop.setProperty("mafioso", "disabled");
			}
			prop.setProperty("mafiosoCount", mafiosoCount.getText());
			
			
			if(amnesiacIcon.isEnabled()){			
				prop.setProperty("amnesiac", "enabled");
			} else {
				prop.setProperty("amnesiac", "disabled");
			}
			prop.setProperty("amnesiacCount", amnesiacCount.getText());
			
			if(arsonistIcon.isEnabled()){
				prop.setProperty("arsonist", "enabled");
			} else {
				prop.setProperty("arsonist", "disabled");
			}
			prop.setProperty("arsonistCount", arsonistCount.getText());
			
			if(executionerIcon.isEnabled()){
				prop.setProperty("executioner", "enabled");
			} else {
				prop.setProperty("executioner", "disabled");
			}
			prop.setProperty("executionerCount", executionerCount.getText());
			
			if(jesterIcon.isEnabled()){
				prop.setProperty("jester", "enabled");
			} else {
				prop.setProperty("jester", "disabled");
			}
			prop.setProperty("jesterCount", jesterCount.getText());
			
			if(serialKillerIcon.isEnabled()){
				prop.setProperty("serialKiller", "enabled");
			} else {
				prop.setProperty("serialKiller", "disabled");
			}
			prop.setProperty("serialKillerCount", serialKillerCount.getText());
			
			if(survivorIcon.isEnabled()){
				prop.setProperty("survivor", "enabled");
			} else {
				prop.setProperty("survivor", "disabled");
			}
			prop.setProperty("survivorCount", survivorCount.getText());
			
			if(witchIcon.isEnabled()){
				prop.setProperty("witch", "enabled");
			} else {
				prop.setProperty("witch", "disabled");
			}
			prop.setProperty("witchCount", witchCount.getText());
			
			if(werewolfIcon.isEnabled()){
				prop.setProperty("werewolf", "enabled");
			} else {
				prop.setProperty("werewolf", "disabled");
			}
			prop.setProperty("werewolfCount", werewolfCount.getText());
	 	 
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
