package scripts.walkAndTrade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Arguments;

import scripts.api.Task;
import scripts.api.TaskSet;

public class WalkGESpinner extends Script implements Arguments {

	public int numberAccount = 0;
	public static boolean tradedTheMule = false;
	public static boolean stop = false;
	public static int priceBond = 3000000;
	public static String NAME_TRADE = "";
	public static int WORLD = 382;

	public String user = "";
	public String pass = "";

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

		TaskSet tasks = new TaskSet(new WalkToGe(), new TradeMule(), new BuySuppliesAndBond());

		tasks.setStopCondition(() -> stop);

		while (!tasks.isStopConditionMet()) {
			Camera.setCameraAngle(100);

			Task task = tasks.getValidTask();
			if (task != null) {
				task.execute();
			}
			sleep(10, 50);
		}

		General.println("We are at GE and bought everything!");
		Login.logout();

	}

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
