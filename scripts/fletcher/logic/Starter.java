package scripts.fletcher.logic;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.fletcher.logic.starter.BuyBond;
import scripts.fletcher.logic.starter.FletcherGUI;
import scripts.fletcher.logic.starter.TradeMule;
import scripts.fletcher.logic.starter.WalkToGE;
import scripts.muler.utilities.Constants;

public class Starter implements Task {

	public static boolean stop = false;
	public static boolean waitGUI = true;
	public static String MULE_NAME = "";
	
	@Override
	public String action() {
		return "Cutting logs (after 70)...";
	}

	@Override
	public int priority() {
		return 100;
	}

	@Override
	public boolean validate() {
		int level = Skills.getCurrentLevel(Skills.SKILLS.FLETCHING);
		return (level <= 1) && !Constants.grand_exchange.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		
		TaskSet tasks = new TaskSet(new WalkToGE(), new TradeMule(), new BuyBond());

		tasks.setStopCondition(() -> stop);

		FletcherGUI gui = new FletcherGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		while (waitGUI)
			General.sleep(100);
		gui.setVisible(false);
		
		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				task.execute();
			}
			General.sleep(20, 40);
		}

	}
	
}
