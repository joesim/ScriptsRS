package scripts.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.ScriptManifest;

import scripts.Zulrah;

@ScriptManifest(authors = { "Crimson" }, category = "Tools", name = "Bank Evaluator")

public class CheckInventoryValue implements Runnable {

	public static final Pattern overallPattern = Pattern.compile("(?:\"overall\":)([0-9]+)");
	private boolean checkFullInventory = false;

	public CheckInventoryValue(boolean checkFullInv) {
		checkFullInventory = checkFullInv;
	}

	public static int getOverallPrice(int id) throws MalformedURLException, IOException {
		String url = "http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new URL(String.format(url, id)).openConnection().getInputStream()));
		String line;
		while ((line = reader.readLine()) != null)

		{
			Matcher matcher = overallPattern.matcher(line);
			if (matcher.find() && matcher.groupCount() > 0) {
				int overallPrice = Integer.parseInt(matcher.group(1));
				if (overallPrice == 0) {
					overallPrice = 1;
				}
				return overallPrice;
			}
		}
		return 1;
	}

	@Override
	public void run() {
		RSItem[] items;
		items = Functions.concat(Inventory.getAll(), Equipment.getItems());
		double value = 0;
		for (RSItem it : items) {
			try {
				if (it.getDefinition().isNoted()){
					value += it.getStack()*getOverallPrice(it.getID()-1);
				} else {
					value += it.getStack()*getOverallPrice(it.getID());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (checkFullInventory) {
			Zulrah.cashInventory = value + 62000;
		} else if (Zulrah.cashInventory != 0) {
			Zulrah.cashMade += (value - Zulrah.cashInventory);
		}
	}
	
}
