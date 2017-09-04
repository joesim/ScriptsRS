package scripts.chinner_utilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;

public class Conditions {

	public static Condition bankScreenOpen() {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				return Banking.isBankScreenOpen();
			}
		};
	}
	
	public static Condition bankScreenNotOpen() {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				return (!Banking.isBankScreenOpen());
			}
		};
	}
	
	public static Condition inventoryEmpty(){
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				return (Inventory.getAll().length == 0);
			}
		};
	}

	public static Condition inventoryNotEmpty() {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				return !(Inventory.getAll().length == 0);
			}
		};
	}

	public static Condition NPCChatOptions() {
		return new Condition() {
			@Override
			public boolean active() {
				return (NPCChat.getOptions()!=null);
			}
		};
	}

}
