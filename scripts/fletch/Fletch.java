package scripts.fletch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.others.MouseJoe;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class Fletch extends Script implements Painting, Starting {

	public static boolean stop = false;
	public static String action = "";
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart() {
		MouseJoe.loadDataNormal();
		MouseMoveJoe.loadDataNormal();
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		Mouse.setSpeed(70);
		HoverBox.load();
	}
	
	@Override
	public void run() {
		
		boolean checked = false;
		
		TaskSet tasks = new TaskSet();

		tasks.setStopCondition(() -> stop);

		while (!tasks.isStopConditionMet()) {
			if (Login.getLoginState().equals(Login.STATE.INGAME) && !checked) {
				startXP = Skills.getXP(Skills.SKILLS.FLETCHING);
				checked = true;
			}
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				task.execute();
			}
			SleepJoe.sleepHumanDelay(0.5, 1, 2000);
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
		long expHour = Math.round(Double.valueOf(expGained) / Double.valueOf(Double.valueOf(timeRan) / 3600));
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		g.drawString("Temps: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)),
				20, 70);
		g.drawString("Action: " + action, 20, 90);
		g.drawString("Exp/hour: " + expHour, 20, 110);

	}
	
}
