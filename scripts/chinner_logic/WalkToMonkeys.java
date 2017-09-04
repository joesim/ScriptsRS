package scripts.chinner_logic;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.chinner_logic.walking_steps.WalkToCombatArea;
import scripts.chinner_logic.walking_steps.WalkToDaero;
import scripts.chinner_logic.walking_steps.WalkToDungeon;
import scripts.chinner_logic.walking_steps.WalkToGlider;
import scripts.chinner_logic.walking_steps.WalkToLumbo;
import scripts.chinner_logic.walking_steps.WalkToWaydar;
import scripts.chinner_utilities.Constants;
import scripts.chinner_utilities.Utils;

public class WalkToMonkeys implements Task {

	@Override
	public String action() {
		return "Walking to monkeys...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Utils.isFullyReady());
	}

	@Override
	public void execute() {

		TaskSet tasks = new TaskSet(new WalkToGlider(), new WalkToDaero(), new WalkToWaydar(), new WalkToLumbo(),
				new WalkToDungeon(), new WalkToCombatArea());

		tasks.setStopCondition(() -> Constants.combatArea.contains(Player.getPosition()));

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
