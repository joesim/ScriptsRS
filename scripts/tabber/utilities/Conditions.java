package scripts.tabber.utilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;

public class Conditions {

	public static Condition butlerPasOut = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return !Utils.isButlerOut();
		}
	};
	public static Condition inventorySoftClay = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return Inventory.getAll().length > 10;
		}
	};
	public static Condition callServantInterface = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return Interfaces.get(370) != null && Interfaces.get(370).getChild(15) != null;
		}
	};
	public static Condition messageOuOptions = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return (NPCChat.getMessage() != null || NPCChat.getOptions() != null);
		}
	};
	public static Condition clickContinueInterface = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return NPCChat.getClickContinueInterface() != null;
		}
	};

}
