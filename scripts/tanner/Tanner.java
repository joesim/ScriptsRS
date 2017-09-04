package scripts.tanner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSModel;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.tanner.logic.BankingHandler;
import scripts.tanner.logic.GEHandler;
import scripts.tanner.logic.TanInferfaceHandler;
import scripts.tanner.logic.WalkToBank;
import scripts.tanner.logic.WalkToTanner;

public class Tanner extends Script implements Painting{

	private static boolean stop = false;
	private static String action = null;
	
	@Override
	public void run() {
		TaskSet tasks = new TaskSet(new TanInferfaceHandler(), new WalkToBank(), new WalkToTanner(), new BankingHandler(), new GEHandler());
		tasks.setStopCondition(() -> stop);

		while (!tasks.isStopConditionMet()) {
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

		
	}

}
