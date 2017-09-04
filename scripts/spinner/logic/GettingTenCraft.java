package scripts.spinner.logic;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Skills;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.muler.Muler;
import scripts.muler.utilities.MuleGUI;
import scripts.spin_logic.tenCraft.BuyShitGE;
import scripts.spin_logic.tenCraft.CraftLeather;
import scripts.spin_logic.tenCraft.TradeTheMule;
import scripts.spin_logic.tenCraft.WalkToGE;
import scripts.spin_logic.tenCraft.worldHop;

public class GettingTenCraft implements Task {

	public static boolean stop = false;
	public static boolean tradedTheMule = false;
	public static boolean boughtShit = false;
	
	@Override
	public String action() {
		return "Getting 10 crafting...";
	}

	@Override
	public int priority() {
		return 999999999;
	}

	@Override
	public boolean validate() {
		return (Skills.getActualLevel(Skills.SKILLS.CRAFTING)<10);
	}

	@Override
	public void execute() {
		
		MuleGUI gui = new MuleGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		while (Muler.waitGUI)
			General.sleep(100);
		gui.setVisible(false);
		
		TaskSet tasks = new TaskSet(new BuyShitGE(), new TradeTheMule(), new WalkToGE(), new worldHop(), new CraftLeather());

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
