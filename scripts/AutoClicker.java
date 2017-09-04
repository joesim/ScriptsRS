package scripts;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;

import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.utilities.Timer;
import scripts.zulrahUtilities.CustomAntiban;

/**
 * TODO: GUI add field item id, name and sleepModifier.
 * TODO: 
 * 
 * @author joel_
 *
 */
public class AutoClicker extends Script {

	private TBox boxVarrock = new TBox(587, 274, 603, 286);
	private TBox boxFalador = new TBox(563, 295, 579, 310);
	private TBox boxLumb = new TBox(663, 273, 674, 284);
	private TBox boxCamelot = new TBox(682, 295, 697, 311);
	private TBox boxAlch = new TBox(704, 319, 717, 335);
	private TBox box = null;
	
	public final static int NATURE_RUNE = 0;
	public static int ALCH_ITEM_ID = 0;
	public static String ALCH_ITEM_NAME = "Magic longbow";
	public static double SLEEP_MODIFIER = 1.8;

	public static boolean waitGUI = true;

	private void onstart() {
		MouseMoveJoe.loadDataNormal();
		// Suspendre les thread antiban et fatigue
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		super.setAIAntibanState(false);

		// Mouse speed
		Mouse.setSpeed(105);

		// Load les boxes.
		HoverBox.load();
	}

	@Override
	public void run() {
		AutoClickerGUI g = new AutoClickerGUI();
		g.setVisible(true);
		while (waitGUI)
			sleep(500);
		g.setVisible(false);
		onstart();
		General.println(ALCH_ITEM_ID);
		General.println(ALCH_ITEM_NAME);
		General.println(SLEEP_MODIFIER);
		Timer timeAntiBan = new Timer(1000000000);
		Timer timeWrongTab = new Timer(1000000000);
		int timeWrong = General.random(6000, 12000);
		int antibanAt = General.random(10 * 60 * 1000, 60 * 60 * 1000);
		int experienceMagic = Skills.getXP(Skills.SKILLS.MAGIC);
		int experienceMagicPrevious = Skills.getXP(Skills.SKILLS.MAGIC);
		GameTab.open(GameTab.TABS.MAGIC);
		SleepJoe.sleepHumanDelay(2, 1, 4000);
		while (true) {
			if (box.equals(boxAlch) && (Inventory.find(ALCH_ITEM_ID) == null || Inventory.find(NATURE_RUNE)==null)){
				//TODO: buy nature rune and item in GE 
				break;
			}
			if (timeWrongTab.getElapsed() > timeWrong) {
				General.println("Click dans le vide");
				GameTab.open(GameTab.TABS.MAGIC);
				timeWrong = General.random(6000, 12000);
				timeWrongTab = new Timer(1000000000);
			}
			if (timeAntiBan.getElapsed() > antibanAt) {
				CustomAntiban.antibanTwo();
				timeAntiBan.reset();
				antibanAt = General.random(10 * 60 * 1000, 60 * 60 * 1000);
				GameTab.open(GameTab.TABS.MAGIC);
			}
			if (!box.isPointInBox(Mouse.getPos())) {
				MouseMoveJoe.humanMouseMove(box, 1);
			}
			SleepJoe.sleepHumanDelay(SLEEP_MODIFIER, 1, 5000);
			MouseMoveJoe.fastClick(1, 1);
			experienceMagicPrevious = experienceMagic;
			experienceMagic = Skills.getXP(Skills.SKILLS.MAGIC);
			if (experienceMagicPrevious != experienceMagic) {
				timeWrongTab = new Timer(1000000000);
			}
			
		}

	}

	/**
	 *
	 * @author joel_
	 */
	public class AutoClickerGUI extends javax.swing.JFrame {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
	     * Creates new form AutoClickerGUI
	     */
	    public AutoClickerGUI() {
	        initComponents();
	    }

	    /**
	     * This method is called from within the constructor to initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is always
	     * regenerated by the Form Editor.
	     */
	    @SuppressWarnings("unchecked")
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	    private void initComponents() {

	        jLabel1 = new javax.swing.JLabel();
	        jPanel1 = new javax.swing.JPanel();
	        jLabel2 = new javax.swing.JLabel();
	        typeAutoClick = new javax.swing.JComboBox<>();
	        jLabel3 = new javax.swing.JLabel();
	        sleepModif = new javax.swing.JTextField();
	        jLabel4 = new javax.swing.JLabel();
	        jLabel5 = new javax.swing.JLabel();
	        nameAlchItem = new javax.swing.JTextField();
	        alchItemID = new javax.swing.JTextField();
	        startButton = new javax.swing.JButton();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
	        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel1.setText("Volcom AutoClicker");
	        jLabel1.setToolTipText("");
	        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

	        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

	        jLabel2.setText("What are we AutoClicking?");

	        typeAutoClick.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Varrock", "Falador", "Lumbridge", "Camelot", "High Alch" }));
	        typeAutoClick.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                typeAutoClickActionPerformed(evt);
	            }
	        });

	        jLabel3.setText("Sleep Modifier: ");

	        sleepModif.setText("2");

	        jLabel4.setText("Item to alch: ");

	        jLabel5.setText("Item to alch ID: ");

	        nameAlchItem.setText("Magic longbow");

	        alchItemID.setText("1234");

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel2)
	                    .addComponent(jLabel3)
	                    .addComponent(jLabel4)
	                    .addComponent(jLabel5))
	                .addGap(18, 18, 18)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(typeAutoClick, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(sleepModif)
	                    .addComponent(nameAlchItem)
	                    .addComponent(alchItemID))
	                .addContainerGap(106, Short.MAX_VALUE))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGap(18, 18, 18)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(typeAutoClick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(13, 13, 13)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel3)
	                    .addComponent(sleepModif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(8, 8, 8)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel4)
	                    .addComponent(nameAlchItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel5)
	                    .addComponent(alchItemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );

	        startButton.setText("Start!");
	        startButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                startButtonActionPerformed(evt);
	            }
	        });

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(57, Short.MAX_VALUE)
	                .addComponent(jLabel1)
	                .addGap(148, 148, 148))
	            .addGroup(layout.createSequentialGroup()
	                .addGap(36, 36, 36)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel1)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(6, 6, 6))
	        );

	        pack();
	    }// </editor-fold>                        

	    private void typeAutoClickActionPerformed(java.awt.event.ActionEvent evt) {                                              
	        // TODO add your handling code here:
	    }                                             

	    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
			if (typeAutoClick.getSelectedItem().toString().equals("Varrock")) {
				box = boxVarrock;
			} else if (typeAutoClick.getSelectedItem().toString().equals("Lumbridge")) {
				box = boxLumb;
			} else if (typeAutoClick.getSelectedItem().toString().equals("Falador")) {
				box = boxFalador;
			} else if (typeAutoClick.getSelectedItem().toString().equals("Camelot")) {
				box = boxCamelot;
			} else if (typeAutoClick.getSelectedItem().toString().equals("High Alch")) {
				box = boxAlch;
			}
			
			SLEEP_MODIFIER = Double.valueOf(sleepModif.getText());
			ALCH_ITEM_NAME = nameAlchItem.getText();
			ALCH_ITEM_ID = Integer.valueOf(alchItemID.getText());
			waitGUI = false;
		}                                        


	    // Variables declaration - do not modify                     
	    private javax.swing.JTextField alchItemID;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel jLabel5;
	    private javax.swing.JPanel jPanel1;
	    private javax.swing.JTextField nameAlchItem;
	    private javax.swing.JTextField sleepModif;
	    private javax.swing.JButton startButton;
	    private javax.swing.JComboBox<String> typeAutoClick;
	    // End of variables declaration                   
	}
	
}




