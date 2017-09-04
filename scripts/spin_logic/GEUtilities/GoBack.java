package scripts.spin_logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Magic;

import scripts.Spinner;
import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_logic.GEHandler;

public class GoBack implements Task {

	@Override
	public String action() {
		return "Going back...";
	}

	@Override
	public int priority() {
		return 4;
	}

	@Override
	public boolean validate() {
		return GEHandler.boughtHide && GEHandler.soldLeather;
	}

	@Override
	public void execute() {
		if (GrandExchange.close()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return GrandExchange.getWindowState() == null;
				}
			}, General.random(5000, 10000));
			if (Banking.openBank()) {
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return Banking.isBankScreenOpen();
					}
				}, General.random(9000, 12000), Screen.MAIN_SCREEN);
				Banking.depositAll();
				if (Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Banking.find("Flax").length > 0;
					}
				}, General.random(5000, 10000))) {

					if (Banking.withdraw(0, "Flax")) {
						General.sleep(500, 1000);
						if (Banking.close()) {
							General.sleep(500, 1000);
							if (GameTab.open(TABS.MAGIC)) {
								General.sleep(500,1000);
								Magic.selectSpell("Lumbridge Home Teleport");
								General.sleep(200,2000);
								Mouse.leaveGame();
								General.sleep(20000, 30000);
								GEHandler.stop = true;
							}
						}
					}
				} else {
					GEHandler.stop = true;
					Spinner.stop = true;
				}

			}
		}
	}

}
