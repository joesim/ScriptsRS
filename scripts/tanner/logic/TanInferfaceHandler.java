package scripts.tanner.logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tanner.utilities.Constants;
import scripts.tanner.utilities.Variables;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class TanInferfaceHandler implements Task {

	@Override
	public String action() {
		return "Tanning hides...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		RSNPC[] tanner = NPCs.find(Constants.TANNER_ID);
		return (Inventory.getCount(Variables.SELECTED_HIDE) > 0 && tanner.length > 0 && tanner[0].isOnScreen());
	}

	@Override
	public void execute() {
		RSNPC[] tanner = NPCs.find(Constants.TANNER_ID);
		if (tanner.length > 0) {
			if (AccurateMouse.click(tanner[0], "Trade")) {
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Interfaces.get(324) != null;
					}
				}, General.random(8000, 15000), Screen.MAIN_SCREEN,60);
				RSInterface interfaceTan = Interfaces.get(324);
				if (interfaceTan != null && interfaceTan.getChild(104) != null) {
					if (interfaceTan.getChild(104).click("Tan All")){
						DynamicWaiting.hoverWaitScreen(new Condition() {
							@Override
							public boolean active() {
								General.sleep(50, 100);
								return Inventory.getCount(Variables.SELECTED_HIDE)==0;
							}
						}, General.random(2000, 4000), Screen.MINIMAP,100);
					}
				} else {
					return;
				}
			} else {
				General.sleep(General.randomSD(200, 800, 1));
				RSInterface interfaceTan = Interfaces.get(324);
				if (interfaceTan != null && interfaceTan.getChild(104) != null) {
					if (interfaceTan.getChild(104).click("Tan All")){
						DynamicWaiting.hoverWaitScreen(new Condition() {
							@Override
							public boolean active() {
								General.sleep(50, 100);
								return Inventory.getCount(Variables.SELECTED_HIDE)==0;
							}
						}, General.random(2000, 4000), Screen.MINIMAP,100);
					}
				} else {
					return;
				}
			}
		}
	}

}
