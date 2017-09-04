package scripts.powermining;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.potato_picker.logic.BankPotato;
import scripts.potato_picker.logic.PickPotato;
import scripts.potato_picker.logic.WalkToBank;
import scripts.potato_picker.logic.WalkToPotato;
import scripts.powermining.logic.DropOre;
import scripts.powermining.logic.MineRock;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.PaintHandler;

public class IronOreMining extends Script implements Painting, Starting {

	public static boolean stop = false;
	public static String action = null;
	
	@Override
	public void onStart() {
		setLoginBotState(false);
		int wait = General.random(0,1200000);
		General.println(wait);
		sleep(wait);
		setLoginBotState(true);
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

		TaskSet tasks = new TaskSet(new DropOre(), new MineRock());
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

	PaintHandler paintHandler = new PaintHandler();
	
	@Override
	public void onPaint(Graphics g) {  
		Graphics2D graphics = (Graphics2D) g; 
		Rectangle rec = new Rectangle(100,100);
		graphics.draw(rec);
		
	}

}
