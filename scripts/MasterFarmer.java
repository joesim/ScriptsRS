package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.others.MoveMouseFarm;
import scripts.utilities.FastClickThread;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.zulrahUtilities.ConditionZul;

public class MasterFarmer extends Script {

	public enum STATE {
		PICKPOCKETING, BANKING, DROPPING, HEALING
	}
	
	private MoveMouseFarm mouse = new MoveMouseFarm();
	private SleepJoe sleep = new SleepJoe();
	private boolean endLoop = false;
	public static RSNPC farmer;
	
	@Override
	public void run() {
		
		STATE state = STATE.PICKPOCKETING;
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		while (!endLoop){
			
			ConditionZul c = new ConditionZul(new RSTile(0,0,0), 101);
			
			switch (state){
			case PICKPOCKETING:
				farmer = Functions.findNearestNPC(3257);
//				while (!endLoop){
//					 mouse.hoverMouse(new TBox(100,100,120,120), new ConditionZul(new
//							 RSTile(0,0,0),101));
//				}
				FastClickThread fct = new FastClickThread();
				Thread t = new Thread(fct);
				t.start();
				do {
					mouse.playMouseFollowNPC(farmer, "Pickpocket", 4);
					mouse.hoverMouse(new TBox(Mouse.getPos(),10), c,5,false);
				} while (!endLoop);
				break;
			case BANKING:
				break;
			case DROPPING:
				break;
			case HEALING:
				break;
			default:
				break;
			}
			
		}
		
	}
	
	
	
}
