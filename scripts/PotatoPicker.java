package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.potato_picker.logic.BankPotato;
import scripts.potato_picker.logic.PickPotato;
import scripts.potato_picker.logic.WalkToBank;
import scripts.potato_picker.logic.WalkToPotato;

@ScriptManifest(authors = { "volcom3d" }, category = "Money Making", name = "Volcom3d Potato Picker")
public class PotatoPicker extends Script implements Painting {

	//********** VARIABLES **********
	private static boolean stop = false;
	private static int numberPotatoes = 0;
	private Double version = 1.0;
	private static String action;
	
	//********** FUNCTIONS **********
	
	
	@Override
	public void run() {
		TaskSet tasks = new TaskSet(new PickPotato(), new WalkToBank(), new WalkToPotato(), new BankPotato());

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
	

	public static void stopBot() {
		stop = true;
	}

	public static void incrementPotato() {
		numberPotatoes++;
	}

	//********** PAINTING **********
	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	private final Image img = getImage("http://i.imgur.com/tHXqQN3.png");
	private static final long startTime = System.currentTimeMillis();
	Font font = new Font("Times New Roman", Font.BOLD, 18);

	@Override
	public void onPaint(Graphics g) {

		Graphics2D gg = (Graphics2D) g;
		gg.drawImage(img, 9, 345, 503, 130, null);

		long timeRan = System.currentTimeMillis() - startTime;
		double multiplier = timeRan / 3600000D;

		g.setFont(font);
		g.setColor(new Color(230,220,19));
		g.drawString("Volcom3d's Potato Picker V" + version, 14, 360);
		g.drawString("Action: " + action, 14, 400);
		g.drawString("Time running: " + Timing.msToString(timeRan), 14, 420);
		g.drawString("Potatoes collected: " + numberPotatoes, 14, 440);
		g.drawString("Potatoes p/h: " + (int) (numberPotatoes / multiplier) + " p/h", 14, 460);
	}

}
