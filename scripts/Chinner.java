package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Starting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.chinner_GUI.ChinningGUI;
import scripts.chinner_logic.AttackMonkey;
import scripts.chinner_logic.BankingHandler;
import scripts.chinner_logic.HealingHandler;
import scripts.chinner_logic.SpreadMonkeys;
import scripts.chinner_logic.TeleOutHandler;
import scripts.chinner_logic.WalkToBank;
import scripts.chinner_logic.WalkToMonkeys;
import scripts.chinner_utilities.Variables;

public class Chinner extends Script implements Starting {

	public static boolean waitGUI = true;
	private static boolean stop = false;
	private static String action = "";

	@Override
	public void onStart() {
		ChinningGUI g = new ChinningGUI();
		g.setLocationRelativeTo(null);
		g.setVisible(true);
		while (waitGUI)
			sleep(100);
		g.setVisible(false);

		Mouse.setSpeed(Variables.MOUSE_SPEED);
	}

	@Override
	public void run() {
//		TaskSet tasks = new TaskSet(new AttackMonkey(), new BankingHandler(), new HealingHandler(), new SpreadMonkeys(),
//				new TeleOutHandler(), new WalkToBank(), new WalkToMonkeys());
//
//		tasks.setStopCondition(() -> stop);
//
//		while (!tasks.isStopConditionMet()) {
//			Camera.setCameraAngle(100);
//			Task task = tasks.getValidTask();
//			if (task != null) {
//				action = task.action();
//				task.execute();
//			}
//			sleep(180, 400);
//		}

//		Login.logout();
		
		Task task = new WalkToMonkeys();
		task.execute();
		//Banking.withdraw(Variables.SELECTED_CHINS, 10);
		
	}
	
	public static void stop() {
		stop = true;
	}
	
}
