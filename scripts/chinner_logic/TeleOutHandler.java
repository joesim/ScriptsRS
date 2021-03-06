package scripts.chinner_logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;

import scripts.api.Task;
import scripts.chinner_utilities.Conditions;
import scripts.chinner_utilities.Constants;
import scripts.chinner_utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;

public class TeleOutHandler implements Task {

	@Override
	public String action() {
		return "Teleporting out...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Utils.shouldTeleOut());
	}

	@Override
	public void execute() {
		// Teleporting to al kharid
		RSItem[] rod = Inventory.find(Constants.RING_OF_DUELING);
		if (rod.length > 0) {
			if (rod[0].click("Wield")) {
				GameTab.open(GameTab.TABS.EQUIPMENT);
				rod = Equipment.find(Constants.RING_OF_DUELING);
				if (rod.length > 0) {
					rod[0].click("Rub");
				}
				if (DynamicWaiting.hoverWaitScreen(Conditions.NPCChatOptions(), General.random(4000, 5000),
						Screen.CHAT_BOX)) {
					NPCChat.selectOption("Clan Wars Arena", true);
				}
			}
		}

		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				return (Constants.clanWarsArea.contains(Player.getPosition()));
			}
		}, General.random(4000, 5000), Screen.MINIMAP);
	}

}
