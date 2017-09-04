package scripts.tabber.tabber_logic;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.tabber.utilities.Conditions;
import scripts.tabber.utilities.Constants;
import scripts.tabber.utilities.MouseTab;
import scripts.tabber.utilities.Utils;
import scripts.tabber.utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class MakeTabsInterface implements Task {

	public static int numberToFetchAt = 0;
	@Override
	public String action() {
		return "Making tabs...";
	}

	@Override
	public int priority() {
		return 1000;
	}

	@Override
	public boolean validate() {
		return (Interfaces.get(79) != null);
	}

	@Override
	public void execute() {
		
		
		if (Functions.percentageBool(Variables.CALL_BUTLER_AT_ZERO)){
			numberToFetchAt = 0;
		} else {
			numberToFetchAt = General.randomSD(1,9,1);
		}
		if (clickMakeAll()) {
			RSNPC[] butler = NPCs.find(227);
			RSModel theButler = null;
			if (butler.length>0){
				theButler = butler[0].getModel();
			}
			if (Utils.isButlerOut()) {
				DynamicWaiting.hoverWaitTabs(Conditions.butlerPasOut, General.random(100000, 120000), Screen.ALL_SCREEN, 5000, theButler);
				DynamicWaiting.hoverWaitScreen(Conditions.inventorySoftClay, General.random(2000, 4000), Screen.ALL_SCREEN);
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else {
				DynamicWaiting.hoverWaitTabs(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						if (Inventory.getCount(Constants.SOFT_CLAY) <= numberToFetchAt || NPCChat.getMessage()!=null){
							return true;
						}
						return false;
					}
				}, General.random(100000, 120000), Screen.ALL_SCREEN, 10000, theButler);
			}
		} else {
			General.random(200, 500);
		}
	}

	public static boolean clickMakeAll(){
		RSInterface inter = Interfaces.get(79).getChild(17);
		if (inter!=null){
			if (Functions.percentageBool(Variables.PERCENT_CLICK_MAKE_ALL_JOE)){
			TBox box = new TBox(inter.getAbsoluteBounds());
			MouseTab.humanMouseMove(box, Variables.SPEED_MOUSE);
			MouseMoveJoe.fastClick(3, Variables.SPEED_FAST_CLICK);
			CustomFlaxAB.randomSleep();
			Point pt = new Point((int) Mouse.getPos().getX(), (int) (Mouse.getPos().getY() + Variables.CLICK_OFFSET));
			SleepJoe.sleepHumanDelay(Variables.SLEEP_MODIF_RIGHT_CLICK, 0, 600);
			Mouse.hop(pt);
			CustomFlaxAB.randomSleep();
			SleepJoe.sleepHumanDelay(Variables.SLEEP_MODIF_RIGHT_CLICK, 0, 600);
			MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			long t = Timing.currentTimeMillis();
			while ((Timing.currentTimeMillis() - t)<1000) {
				if (Interfaces.get(79) == null){
					return true;
				}
				General.sleep(10,20);
			}
			} else {
				return inter.click("Make-All");
			}
		}
		return false;
	}
	
	
}
