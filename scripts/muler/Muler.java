package scripts.muler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.Players;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Trading;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.script.Script;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.muler.logic.AcceptTrade;
import scripts.muler.logic.Banking;
import scripts.muler.logic.GEHandler;
import scripts.muler.logic.Teleport;
import scripts.muler.logic.TradeMule;
import scripts.muler.logic.WalkToBank;
import scripts.muler.logic.WalkToGE;
import scripts.muler.utilities.MuleGUI;
import scripts.muler.utilities.Variables;

public class Muler extends Script implements Painting, MessageListening07 {

	public static boolean waitGUI = true;
	public static boolean stop = false;
	public static String action = null;
	
	@Override
	public void clanMessageReceived(String arg0, String arg1) {
	}

	@Override
	public void duelRequestReceived(String arg0, String arg1) {

	}

	@Override
	public void personalMessageReceived(String arg0, String arg1) {

	}

	@Override
	public void playerMessageReceived(String arg0, String arg1) {

	}

	@Override
	public void serverMessageReceived(String arg0) {

	}

	@Override
	public void tradeRequestReceived(String name) {
		if (Variables.IS_MULE) {
			RSPlayer[] playerToTrade = Players.findNearest(Filters.Players.nameEquals(name));
			if (playerToTrade.length > 0) {
				if (playerToTrade[0].isOnScreen() && playerToTrade[0].isClickable()) {
					if (playerToTrade[0].click("Trade with " + name)) {
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Trading.getWindowState() != null;
							}
						}, General.random(5000, 10000));
					}
				}
			}
		}
	}

	@Override
	public void run() {

		
		MuleGUI gui = new MuleGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		while (waitGUI)
			sleep(100);
		gui.setVisible(false);
		
		TaskSet tasks = new TaskSet(new WalkToBank(), new Banking(), new Teleport(), new WalkToGE(), new TradeMule(), new AcceptTrade(), new GEHandler());

		tasks.setStopCondition(() -> stop);

		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);
			Task task = tasks.getValidTask();
			if (task != null) {
				action = task.action();
				task.execute();
			}
			sleep(180, 400);
		}

		Login.logout();

	}

	Font font = new Font("Verdana", Font.BOLD, 14);

	@Override
	public void onPaint(Graphics g) {

		g.setFont(font);
		g.setColor(new Color(0, 255, 0));
		g.drawString("Action: " + action, 20, 90);

	}

}
