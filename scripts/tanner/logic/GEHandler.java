package scripts.tanner.logic;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.muler.logic.AcceptTrade;
import scripts.muler.logic.Banking;
import scripts.muler.logic.TradeMule;
import scripts.muler.logic.WalkToBank;
import scripts.muler.utilities.MuleGUI;
import scripts.tanner.logic.GEUtilities.BuyHide;
import scripts.tanner.logic.GEUtilities.GoBack;
import scripts.tanner.logic.GEUtilities.SellLeather;
import scripts.tanner.logic.GEUtilities.Teleport;
import scripts.tanner.logic.GEUtilities.WalkToGE;

public class GEHandler implements Task {

	public static boolean stop = false;
	public static boolean shouldRefill = false;
	public static boolean soldLeather = false;
	public static boolean boughtHide = false;
	
	@Override
	public String action() {
		return "GE Handler...";
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public boolean validate() {
		return shouldRefill;
	}

	@Override
	public void execute() {
		
		TaskSet tasks = new TaskSet(new GoBack(), new BuyHide(), new SellLeather(), new Teleport(), new WalkToGE());

		tasks.setStopCondition(() -> stop);

		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				task.execute();
			}
			General.sleep(180, 400);
		}
		
		soldLeather = false;
		boughtHide = false;
		shouldRefill = false;
		stop = false;
	}

}
