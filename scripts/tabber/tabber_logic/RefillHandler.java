package scripts.tabber.tabber_logic;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.tabber.tabber_logic.refillUtilities.SellTabs;
import scripts.tabber.tabber_logic.refillUtilities.WalkToGE;

public class RefillHandler implements Task {

	public static int REFILLAT = General.random(10, 50);
	public static boolean stop = false;
	@Override
	public String action() {
		return "Refill handler...";
	}

	@Override
	public int priority() {
		return 100000000;
	}

	@Override
	public boolean validate() {
		return Inventory.find(557).length==0 || Inventory.find(563).length==0 || Inventory.find(1762).length==0 || Inventory.find(1762)[0].getStack()<REFILLAT;
	}

	@Override
	public void execute() {
		TaskSet tasks = new TaskSet(new WalkToGE(), new SellTabs());

		tasks.setStopCondition(() -> stop);
		
		
		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				task.execute();
			}
			General.sleep(180, 400);
		}

		
	}

}
