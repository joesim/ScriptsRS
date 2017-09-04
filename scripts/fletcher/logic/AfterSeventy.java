package scripts.fletcher.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Skills;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.fletcher.Fletcher;
import scripts.fletcher.logic.GEHandler.GEHandler;
import scripts.fletcher.logic.fletchYew.BankingCutting;
import scripts.fletcher.logic.fletchYew.CheckThingsFirst;
import scripts.fletcher.logic.fletchYew.CuttingLogs;
import scripts.fletcher.logic.fletchYew.StringYews;
import scripts.fletcher.utilities.Utils;
import scripts.utilities.Functions;

public class AfterSeventy implements Task {
	public static boolean stop = false;
	public static boolean openBankFirst = true;
	public static boolean cutting = true;
	public static int numberInv = General.randomSD(1, 100, 50, 3);
	public static String action = null;
	public static long startDay = System.currentTimeMillis();
	public int numberClient = 0;
	
	@Override
	public String action() {
		return action;
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		int level = Skills.getCurrentLevel(Skills.SKILLS.FLETCHING);
		return (level >= 70);
	}

	@Override
	public void execute() {
		TaskSet tasks = new TaskSet(new CheckThingsFirst(), new BankingCutting(), new CuttingLogs(), new GEHandler(), new StringYews());

		tasks.setStopCondition(() -> stop);

		long goToSleepAfter = General.random(39600000, 50400000);
		General.println("Going to sleep after: " + Timing.msToString(goToSleepAfter));
		long sleepDuring = 86400000 - goToSleepAfter + General.random(-1500000, 1500000);
		long startAfterBreak = System.currentTimeMillis();
		long breakAt = General.random(2400000, 7200000); // 20 to 120 min
		General.println("Going to break after: " + Timing.msToString(breakAt));
		long breakDuring = General.random(120000, 1500000); // 2 to 25min
		
		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				action = task.action();
				task.execute();
			}
			General.sleep(20, 40);
			Utils.randomSleep();
			
			if ((Timing.currentTimeMillis() - startDay) > goToSleepAfter) {
				General.println("Sleeping for : " + Timing.msToString(sleepDuring));
				Fletcher.timeSleep = sleepDuring;
				Fletcher.shouldSleep = true;
				startDay = startDay + 86400000;
				if (Functions.percentageBool(15)) {
					goToSleepAfter = General.random(10800000, 18000000);
				} else {
					goToSleepAfter = General.random(46800000, 54000000);
				}
				General.println("Sleep after: " + Timing.msToString(goToSleepAfter));
				sleepDuring = 86400000 - goToSleepAfter + General.random(-1500000, 1500000);
				startAfterBreak = System.currentTimeMillis();
				breakAt = General.random(2400000, 7200000);
				if (Functions.percentageBool(5)) {
					breakDuring = General.random(2700000, 4500000); // 45 to 75
				} else {
					breakDuring = General.random(120000, 1500000);
				}
				break;
			}
			if ((Timing.currentTimeMillis() - startAfterBreak) > breakAt) {
				General.println("Taking break for: " + Timing.msToString(breakDuring));
				Fletcher.timeSleep = breakDuring;
				Fletcher.shouldSleep = true;

				startAfterBreak = System.currentTimeMillis();
				breakAt = General.random(2400000, 7200000);
				General.println("Playing for: " + Timing.msToString(breakAt));
				if (Functions.percentageBool(5)) {
					breakDuring = General.random(2700000, 4500000); // 45 to 75
				} else {
					breakDuring = General.random(120000, 1500000);
				}
				break;
			}
		}

	}

}
