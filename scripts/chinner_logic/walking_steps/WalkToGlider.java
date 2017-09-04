package scripts.chinner_logic.walking_steps;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;

import scripts.api.Task;
import scripts.chinner_utilities.Conditions;
import scripts.chinner_utilities.Constants;
import scripts.chinner_utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.webwalker_logic.local.walker_engine.navigation_utils.GnomeGlider;

public class WalkToGlider implements Task {

	@Override
	public String action() {
		return "Going to glider...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return (Utils.isFullyReady());
	}

	@Override
	public void execute() {

		// Teleporting to al kharid
		RSItem[] rod = Inventory.find(Constants.RING_OF_DUELING);
		if (rod.length > 0) {
			if (rod[0].click("Rub")) {
				if (DynamicWaiting.hoverWaitScreen(Conditions.NPCChatOptions(), General.random(4000, 5000),
						Screen.CHAT_BOX)) {
					NPCChat.selectOption("Al Kharid Duel Arena", true);
				}
			}
		}

		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				return (Constants.alkharidArea.contains(Player.getPosition()));
			}
		}, General.random(4000, 5000), Screen.MINIMAP);

		// Walking to glider
		WebWalking.walkTo(Constants.tileGlider);
		long t = Timing.currentTimeMillis();
		while (!GnomeGlider.to(GnomeGlider.Location.TA_QUIR_PRIW) && (Timing.currentTimeMillis()-t)<10000) {
			General.sleep(100,200);
		}

		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				return (Constants.gnomeTreeArea.contains(Player.getPosition()));
			}
		}, General.random(4000, 5000), Screen.MAIN_SCREEN);
		
		General.sleep(1000, 2000);
	}

}
