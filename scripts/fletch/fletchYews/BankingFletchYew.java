package scripts.fletch.fletchYews;

import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletch.Fletch;
import scripts.fletcher_v2.logic.AfterSeventy;
import scripts.fletcher_v2.logic.GEHandler.GEHandler;
import scripts.fletcher_v2.utilities.Conditions;
import scripts.fletcher_v2.utilities.MouseFletch;
import scripts.fletcher_v2.utilities.Utils;
import scripts.fletcher_v2.utilities.Variables;
import scripts.others.DynamicWaiting;
import scripts.others.MouseJoe;
import scripts.others.Screen;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class BankingFletchYew implements Task {

	private String logs = "Yew logs";
	private String bowu = "Yew longbow (u)";
	private String bow = "Yew longbow";

	@Override
	public String action() {
		return "Banking...";
	}

	@Override
	public int priority() {
		return General.random(1, 2);
	}

	@Override
	public boolean validate() {
		return (AfterSeventy.cutting
				&& (Inventory.getCount(bow) > 0 || Inventory.getCount("Knife") == 0 || Inventory.getCount(logs) == 0))
				|| (!AfterSeventy.cutting && (Inventory.getCount(bowu) == 0 || Inventory.getCount("Bow string") == 0
						|| Inventory.getCount("Yew longbow") > 0));
	}

	@Override
	public void execute() {//
		Fletch.action = "Banking...";
		RSNPC[] bankers = NPCs.find(5455, 5456);
		if (Game.isUptext(" -> ")) {
			Player.getPosition().click("");
			General.sleep(200, 3000);
		}
		if (MouseJoe.clickNPC("Bank Banker", bankers, 2, false)){
			if (DynamicWaiting.hoverWaitScreen(Conditions.bankScreenOpen, General.random(4000, 8000), Screen.ALL_SCREEN)){
				
			}
		}
	}
}
