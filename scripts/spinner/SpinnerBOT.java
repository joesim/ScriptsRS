package scripts.spinner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.Restarter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSModel;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.spin_logic.BankingHandler;
import scripts.spin_logic.ClickMMBank;
import scripts.spin_logic.ClickMMStairs;
import scripts.spin_logic.ClickMiddleStairs;
import scripts.spin_logic.ClickSpinner;
import scripts.spin_logic.ClickStairsBot;
import scripts.spin_logic.GettingTenCraft;
import scripts.spin_logic.InterfaceHandler;
import scripts.spin_logic.ReportClickExit;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;

public class SpinnerBOT extends Script implements Starting, Painting {

	public static boolean stop = false;
	public static String action = "";
	public static long startDay = System.currentTimeMillis();
	public int numberClient = 0;
	
	
	private long goToSleepAfter = General.random(39600000, 50400000);
	private long sleepDuring = 86400000 - goToSleepAfter + General.random(-1500000, 1500000);
	private long startAfterBreak = System.currentTimeMillis();
	private long breakAt = General.random(2400000, 7200000);
	private long breakDuring = General.random(120000, 1500000);
	
	@Override
	public void onStart() {
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
		TaskSet tasks = new TaskSet();
		
		tasks.setStopCondition(() -> stop);
		
		
		
		boolean checked = false;
		while (!tasks.isStopConditionMet()) {
			if (Login.getLoginState().equals(Login.STATE.INGAME) && !checked) {
				startXP = Skills.getXP(Skills.SKILLS.CRAFTING);
				checked = true;
			}
			Camera.setCameraAngle(100);
			
			if (Camera.getCameraRotation()< 75 || Camera.getCameraRotation()>105){
			//Camera.setCameraRotation(General.random(80, 100));
			}
			Task task = tasks.getValidTask();
			if (task != null) {
				action = task.action();
				task.execute();
			}
			sleep(10, 50);
			// General.println(Timing.msToString((Timing.currentTimeMillis() -
			// startDay)) + " et "+ Timing.msToString(goToSleepAfter) +" et " +
			// Timing.msToString(sleepDuring));
			checkBreakdAndSleep();
		}

		Login.logout();
	}

	private void checkBreakdAndSleep() {
		if ((Timing.currentTimeMillis() - startDay) > goToSleepAfter) {
			General.println("Sleeping for : " + Timing.msToString(sleepDuring));
			setLoginBotState(false);
			if (Functions.percentageBool(Variables.PERCENT_LOGOUT_BUTTON)) {
				Login.logout();
			}
			Mouse.leaveGame();
			sleep(sleepDuring);
			Restarter.restartClient();
			setLoginBotState(true);
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

		}
		if ((Timing.currentTimeMillis() - startAfterBreak) > breakAt) {
			General.println("Taking break for: " + Timing.msToString(breakDuring));
			setLoginBotState(false);
			if (Functions.percentageBool(Variables.PERCENT_LOGOUT_BUTTON)) {
				Login.logout();
			}
			Mouse.leaveGame();
			sleep(breakDuring);
			numberClient++;
			
			if (numberClient>3){
				Restarter.restartClient();
				numberClient = 0;
			}
			setLoginBotState(true);
			startAfterBreak = System.currentTimeMillis();
			breakAt = General.random(2400000, 7200000);
			General.println("Playing for: " + Timing.msToString(breakAt));
			if (Functions.percentageBool(5)) {
				breakDuring = General.random(2700000, 4500000); // 45 to 75
			} else {
				breakDuring = General.random(120000, 1500000);
			}
		}
	}
	
	
	
	Font font = new Font("Verdana", Font.BOLD, 14);
	private long startTime = System.currentTimeMillis();
	private long startXP = 0;

	@Override
	public void onPaint(Graphics g) {

		Graphics2D graphics = (Graphics2D) g;
		long timeRan = (System.currentTimeMillis() - startTime) / 1000;
		long expGained = Skills.getXP(Skills.SKILLS.CRAFTING) - startXP;
		long bowStringDone = (expGained / 15);
		long bowHour = Math.round(Double.valueOf(bowStringDone) / Double.valueOf(Double.valueOf(timeRan) / 3600));
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		g.drawString("Temps: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)),
				20, 70);
		g.drawString("Action: " + action, 20, 90);
		g.drawString("Ns: " + bowStringDone, 20, 110);
		g.drawString("S/h: " + bowHour, 20, 130);

		RSModel t = Objects.find(15, 1543)[0].getModel();
	
		graphics.draw(t.getEnclosedArea().getBounds());
		
	}

}
