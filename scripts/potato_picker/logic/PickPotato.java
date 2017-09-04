package scripts.potato_picker.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.PotatoPicker;
import scripts.api.Task;
import scripts.utilities.AntiBan;
import scripts.webwalker_logic.WebWalker;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class PickPotato implements Task {

	public static final int POTATO_PLANT_ID = 312;
	public static final RSArea potatoArea = new RSArea(new RSTile(3138, 3268, 0), new RSTile(3157, 3290, 0));

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() { //VARIABLES IN A SEPARATE CLASS
		return (potatoArea.contains(Player.getPosition()) && !Inventory.isFull());
	}

	@Override
	public void execute() {
		final Integer inventoryCount = Inventory.getAll().length;
		final RSObject[] potatoes = Objects.findNearest(20, POTATO_PLANT_ID);
		if (potatoes.length > 0) {
			final RSObject potato = AntiBan.selectNextTarget(potatoes);
			if (potato.isOnScreen()) {
				if (AccurateMouse.click(potato, "Pick")) {
					final long timeOut = General.random(3000, 4000);
					final long t = System.currentTimeMillis();
					while (Inventory.getAll().length == inventoryCount && (System.currentTimeMillis() - t) < timeOut) {
						General.sleep(200,400);
						AntiBan.timedActions();
					}
					if (Inventory.getAll().length > inventoryCount) {
						PotatoPicker.incrementPotato(); // We picked a potato
					}
				} else {
					General.sleep(500, 1000);
				}
			} else {
				WebWalker.walkTo(potato.getPosition());
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						if (potato.isOnScreen()){
							return true;
						}
						return false;
					}
				}, General.random(2000, 3000));
			}
		}
	}

	@Override
	public String action() {
		return "Picking potatoes...";
	}

}
