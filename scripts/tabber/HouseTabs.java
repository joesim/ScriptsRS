package scripts.tabber;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.tabber.tabber_logic.CallServant;
import scripts.tabber.tabber_logic.ClickButler;
import scripts.tabber.tabber_logic.ClickLectern;
import scripts.tabber.tabber_logic.EnterHouse;
import scripts.tabber.tabber_logic.GoToLecternSpot;
import scripts.tabber.tabber_logic.MakeTabsInterface;
import scripts.tabber.tabber_logic.MoveAround;
import scripts.tabber.tabber_logic.RefillHandler;
import scripts.tabber.utilities.MouseTab;
import scripts.tabber.utilities.Utils;
import scripts.tabber.utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.webwalker_logic.WebWalker;

/**
 * house options teleport inside off!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * @author joel_
 *
 */
public class HouseTabs extends Script implements Painting, Starting, Ending {

	public static RSTile spot = null;
	public static boolean stop = false;
	public static String action = null;

	public static long startDay = System.currentTimeMillis();
	
	@Override
	public void onEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {
		MouseTab.loadDataNormal();
		Mouse.setSpeed(Variables.MOUSE_SPEED_TRIBOT);
		HoverBox.load();
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
	}

	RSModel butler = null;

	@Override
	public void run() {
		TaskSet tasks = new TaskSet(new CallServant(), new ClickButler(), new ClickLectern(), new EnterHouse(),
				new GoToLecternSpot(), new MakeTabsInterface(), new MoveAround(), new RefillHandler());

		tasks.setStopCondition(() -> stop);
//		butler = Objects.find(5,13643)[0].getModel();
//		while (!stop){
//		println(MouseTab.clickButler(butler, "Study"));
//		sleep(300,400);
//		}
//		sleep(20000000);
//		
		long goToSleepAfter = General.random(46800000, 54000000);
		General.println("Going to sleep after: " + Timing.msToString(goToSleepAfter));
		long sleepDuring = 86400000 - goToSleepAfter + General.random(-1500000, 1500000);
		long startAfterBreak = System.currentTimeMillis();
		long breakAt = General.random(2400000, 7200000); // 20 to 120 min
		General.println("Going to break after: " + Timing.msToString(breakAt));
		long breakDuring = General.random(60000, 800000); // 2 to 25min
		boolean checked = false;
		
		while (!tasks.isStopConditionMet()) {
			Utils.randomSleep();
			
			if (Login.getLoginState().equals(Login.STATE.INGAME) && !checked) {
				startXP = Skills.getXP(Skills.SKILLS.MAGIC);
				checked = true;
			}
			
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				action = task.action();
				task.execute();
			}
			sleep(180, 400);
			
			if ((Timing.currentTimeMillis() - startDay) > goToSleepAfter) {
				General.println("Sleeping for : " + Timing.msToString(sleepDuring));
				setLoginBotState(false);
				if (Functions.percentageBool(Variables.PERCENT_LOGOUT_BUTTON)) {
					Login.logout();
				}
				Mouse.leaveGame();
				sleep(sleepDuring);
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
					breakDuring = General.random(60000, 800000);
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

		Login.logout();
	}

	
	Font font = new Font("Verdana", Font.BOLD, 14);
	private long startTime = System.currentTimeMillis();
	private long startXP = 0;
	
	@Override
	public void onPaint(Graphics g) {
		
		Graphics2D graphics = (Graphics2D) g;
		long timeRan = (System.currentTimeMillis() - startTime) / 1000;
		long expGained = Skills.getXP(Skills.SKILLS.MAGIC) - startXP;
		long bowStringDone = (expGained / 35);
		long bowHour = Math.round(Double.valueOf(bowStringDone) / Double.valueOf(Double.valueOf(timeRan) / 3600));
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		g.drawString("Temps: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)),
				20, 70);
		g.drawString("Action: " + action, 20, 90);
		g.drawString("Ns: " + bowStringDone, 20, 110);
		g.drawString("S/h: " + bowHour, 20, 130);
		
		RSModel t = butler;
		Rectangle rec = new Rectangle((int)t.getCentrePoint().getX()-8,(int)t.getCentrePoint().getY()-8, 17, 24);
		graphics.draw(rec);

	}
}
