package scripts.ge_utilities;

import org.tribot.api.General;

import scripts.api.Task;
import scripts.api.TaskSet;
import scripts.utilities.SleepJoe;

public class GEJoe {

	public static boolean LOWPRICE = false;
	public static boolean HIGHPRICE = false;
	public static String NAME = null;
	public static int QUANTITY = 1;
	public static int PRICE = 1;
	public static boolean SELL = false;
	public static boolean CONFIRMED = false;
	
	
	public static boolean placeOffer(String name, int quantity, int price, boolean sell, boolean high, boolean low){
		HIGHPRICE = high;
		LOWPRICE = low;
		NAME = name;
		QUANTITY = quantity;
		PRICE = price;
		SELL = sell;
		
		TaskSet tasks = new TaskSet(new OpenGE(), new ClickBuy(), new Confirm(), new SelectItem(), new SelectPrice(), new SelectQuantity(), new ClickSell(), new SelectItemSell());

		tasks.setStopCondition(() -> CONFIRMED);

		while (!tasks.isStopConditionMet()) {
			Task task = tasks.getValidTask();
			if (task != null) {
				task.execute();
			}
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		SelectPrice.PRICE_SELECTED = false;
		CONFIRMED = false;
		General.println("Completed order");
		return false;
	}
	
}
