package scripts.fletcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.fletcher.logic.AfterSeventy;
import scripts.fletcher.logic.GettingSeventy;
import scripts.fletcher.logic.Starter;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;

public class Fletcher extends Script implements Painting {

	public static boolean stop = false;
	public static String action = null;
	public static boolean tradedTheMule = false;
	public static boolean shouldSleep = false;
	public static long timeSleep = 0;

	@Override
	public void run() {

		TaskSet tasks = new TaskSet(new GettingSeventy(), new AfterSeventy(), new Starter());

		Login.login();

		tasks.setStopCondition(() -> stop);

		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				action = task.action();
				task.execute();
			}
			if (shouldSleep) {
				setLoginBotState(false);
				if (Functions.percentageBool(Variables.PERCENT_LOGOUT_BUTTON)) {
					Login.logout();
				}
				Mouse.leaveGame();
				sleep(timeSleep);
				setLoginBotState(true);
				shouldSleep = false;
			}
			sleep(180, 400);
		}

		Login.logout();
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private long startTime = System.currentTimeMillis();
	private long startXP = 0;

	@Override
	public void onPaint(Graphics g) {
		long timeRan = (System.currentTimeMillis() - startTime) / 1000;
		long expGained = Skills.getXP(Skills.SKILLS.FLETCHING) - startXP;
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		g.drawString("Temps: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)),
				20, 70);
		g.drawString("Action: " + action, 20, 90);
		g.drawString("Exp gained: " + expGained, 20, 110);
	}

}
