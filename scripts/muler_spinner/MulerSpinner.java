package scripts.muler_spinner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.utilities.WorldHop;

@ScriptManifest(authors = { "volcom3d" }, category = "Muler", name = "Muler spinner")
public class MulerSpinner extends Script implements MessageListening07 {

	public static String MULE_NAME = "";
	public static int MULE_WORLD = 0;
	public static boolean TAKE_ALL = false;
	public static boolean waitGUI = true;
	public static boolean stop = false;
	
	
	@Override
	public void run() {
		
		try (BufferedReader br = new BufferedReader(new FileReader("./bin/scripts/configmulingspin.txt"))) {
			String sCurrentLine;
			int i = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				if (i == 1) {
					MULE_NAME = sCurrentLine;
				} else if (i == 2) {
					MULE_WORLD = Integer.valueOf(sCurrentLine);
				} else if (i == 3) {
					TAKE_ALL = Boolean.valueOf(sCurrentLine);
				}
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		setLoginBotState(false);
		WorldHop.worldHop(MULE_WORLD);
		setLoginBotState(true);
		
		Login.login();
		if (Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Login.getLoginState().equals(Login.STATE.INGAME);
			}
		}, General.random(40000, 60000))){
			General.println("Lol");
			TaskSet tasks = new TaskSet(new AcceptTrade(), new BankingMule(), new GoToBank(),new TradeMule());
			tasks.setStopCondition(() -> stop);
			while (!tasks.isStopConditionMet()) {
				Camera.setCameraAngle(100);
				Task task = tasks.getValidTask();
				if (task != null) {
					task.execute();
				}
				sleep(180, 400);
			}
		} else {
			General.println("Erreur! Compte pas dans le jeu!");
		}
		
	}


	@Override
	public void clanMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void duelRequestReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void personalMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playerMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void serverMessageReceived(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tradeRequestReceived(String name) {
		if (MULE_NAME.equals(Player.getRSPlayer().getName())) {
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

}
