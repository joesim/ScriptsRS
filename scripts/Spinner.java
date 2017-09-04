package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.others.MouseJoe;
import scripts.spin_logic.BankingHandler;
import scripts.spin_logic.ClickMMBank;
import scripts.spin_logic.ClickMMStairs;
import scripts.spin_logic.ClickMiddleStairs;
import scripts.spin_logic.ClickSpinner;
import scripts.spin_logic.ClickStairsBot;
import scripts.spin_logic.CloseInterfaces;
import scripts.spin_logic.GEHandler;
import scripts.spin_logic.GettingTenCraft;
import scripts.spin_logic.InterfaceHandler;
import scripts.spin_logic.ReportClickExit;
import scripts.spin_logic.Reposition;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;

public class Spinner extends Script implements Starting, Painting, Ending, Arguments {

	public static boolean stop = false;
	public static String action = "";
	public static long startDay = System.currentTimeMillis();
	public static boolean tradedTheMule = false;
	public int numberClient = 0;
	public int numberAccount = 0;
	public static String NAME_TRADE = "";
	public static int WORLD = 0;
	public static int priceBond = 0;
	
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
		General.println(numberAccount);
		this.setLoginBotState(false);

		try (BufferedReader br = new BufferedReader(new FileReader("./bin/scripts/configspinner.txt"))) {
			String sCurrentLine;
			int i = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				if (i == 1) {
					NAME_TRADE = sCurrentLine;
				} else if (i == 2) {
					WORLD = Integer.valueOf(sCurrentLine);
				} else if (i == 3) {
					priceBond = Integer.valueOf(sCurrentLine);
				}
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String user;
		String pass;
		try (BufferedReader br = new BufferedReader(new FileReader("./bin/scripts/accounts.txt"))) {
			String sCurrentLine;
			int i = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				if (numberAccount==0){
					this.setLoginBotState(true);
					break;
				}
				if (i == numberAccount) {
					String[] parts = sCurrentLine.split(":");
					user = parts[0];
					pass = parts[1];
					while (!Login.login(user, pass)) {
						General.sleep(10000, 20000);
					}
					General.sleep(1000, 2000);
					while (!Login.login(user, pass)) {
						General.sleep(10000, 20000);
					}
					General.sleep(1000, 2000);
					while (!Login.login(user, pass)) {
						General.sleep(10000, 20000);
					}
					General.sleep(1000, 2000);
					while (!Login.login(user, pass)) {
						General.sleep(10000, 20000);
					}
					this.setLoginBotState(true);
					break;
				}
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		TaskSet tasks = new TaskSet(new ClickMiddleStairs(), new ClickMMBank(), new BankingHandler(),
				new ClickSpinner(), new InterfaceHandler(), new ClickMMStairs(), new ReportClickExit(),
				new ClickStairsBot(), new GettingTenCraft(), new GEHandler(), new CloseInterfaces(), new Reposition());

		// Tests
		// GEHandler.shouldRefill=true;
		// Task tk = new GEHandler();
		// tk.execute();
		//
		tasks.setStopCondition(() -> stop);

		long startAfterBreak = System.currentTimeMillis();
		long breakAt = General.random(2400000, 7200000); // 20 to 120 min
		General.println("Going to break after: " + Timing.msToString(breakAt));
		long breakDuring = General.random(120000, 1500000); // 2 to 25min
		boolean checked = false;
		while (!tasks.isStopConditionMet()) {
			if (Login.getLoginState().equals(Login.STATE.INGAME) && !checked) {
				startXP = Skills.getXP(Skills.SKILLS.CRAFTING);
				checked = true;
			}
			Camera.setCameraAngle(100);

			if (Camera.getCameraRotation() < 75 || Camera.getCameraRotation() > 105) {
				Camera.setCameraRotation(General.random(80, 100));
			}
			Task task = tasks.getValidTask();
			if (task != null) {
				action = task.action();
				if (task.action().equals("Getting 10 crafting...")) {
					setLoginBotState(false);
					task.execute();
					setLoginBotState(true);
				} else {
					task.execute();
				}
			}
			sleep(10, 50);

			
			if ((Timing.currentTimeMillis() - startAfterBreak) > breakAt) {
				General.println("Taking break for: " + Timing.msToString(breakDuring));
				setLoginBotState(false);
				if (Functions.percentageBool(Variables.PERCENT_LOGOUT_BUTTON)) {
					Login.logout();
				}
				Mouse.leaveGame();
				sleep(breakDuring);
				numberClient++;

				if (numberClient > 3) {
					// Restarter.restartClient();
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

		Login.logout();
	}

	@Override
	public void onEnd() {
		// TaskSet tasks = new TaskSet(new ClickMiddleStairs(), new
		// ClickMMBank(), new BankingHandler(),
		// new ClickSpinner(), new InterfaceHandler(), new ClickMMStairs(), new
		// ReportClickExit(),
		// new ClickStairsBot());
		//
		// tasks.setStopCondition(() -> stop);
		// long time = Timing.currentTimeMillis();
		// long stopAt = General.random(0, 1800000);
		// General.println("Stopping in : " + Timing.msToString(stopAt));
		// while (!tasks.isStopConditionMet()) {
		// Camera.setCameraAngle(100);
		// Camera.setCameraRotation(90);
		// Task task = tasks.getValidTask();
		// if (task != null) {
		// action = task.action();
		// task.execute();
		// }
		// sleep(10, 50);
		// if ((Timing.currentTimeMillis() - time) > stopAt) {
		// stop = true;
		// }
		// }
		//
		// Login.logout();
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private long startTime = System.currentTimeMillis();
	private long startXP = 0;

	@Override
	public void onPaint(Graphics g) {

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

		// if (Zulrah.zulrah != null) {
		// g.drawString("Zulrah animation: " + Zulrah.zulrah.getAnimation(),
		// 300, 370);
		// }
		// g.drawString("CheckDeath player : " + CheckDeath.playerDead + " zul:
		// " + CheckDeath.zulrahDead, 200, 390);
		// g.drawString("Player interacting index: " +
		// Player.getRSPlayer().getInteractingIndex(), 200, 410);
		// g.drawString("CheckRed shouldMove " + CheckRed.shouldMove, 200, 430);
		// g.drawString("CheckJad " + CheckJad.shouldSwitch, 200, 450);

	}

	@Override
	public void passArguments(HashMap<String, String> arguments) {
		String args = arguments.get("custom_input");
		if (arguments.get("custom_input") == null) {
			args = arguments.get("client_starter");
			if (arguments.get("client_starter") == null) {
				args = arguments.get("autostart");
			}
		}
		numberAccount = Integer.valueOf(args);
	}
}
