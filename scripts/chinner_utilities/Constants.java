package scripts.chinner_utilities;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public final class Constants {

	// Potions
	public static final int[] PRAYER_POT = {2434,139,141,143};
	public static final int[] RANGING_POT = {2444,169,171,173};
	public static final int[] SUPER_ANTIPOISON_POT = {2448,181,183,185};
	public static final int[] ANTIDOTEPP_POT = {5952,5954,5956,5958};
	public static final int[] ANTIPOISON_POT = {2448,175,177,179};
	
	//Food
	public static final int MONKFISH = 7946;
	public static final int SHARK = 385;
	public static final int LOBSTER = 379;
	public static final int TROUT = 333;
	public static final int TUNA_POTATO = 7060;
	public static final int ANGLERFISH = 13441;
	public static final int DARK_CRAB = 11936;
	
	//Chinchompas
	public static final int RED_CHINCHOMPA = 10034;
	public static final int GRAY_CHINCHOMPA = 10033;
	public static final int BLACK_CHINCHOMPA = 11959;
	
	//Skeletal monkey
	public static final int SKELETAL_MONKEY = 5237;
	
	//Monkey greegree
	public static final int MONKEY_GREEGREE = 4031;
	
	//Ring of dueling
	public static final int[] RING_OF_DUELING = {2552,2554,2556,2558,2560,2562,2564};
	
	//Areas
	public static final RSArea combatArea = new RSArea(new RSTile(2700,9100,0), new RSTile(2714,9119,0));
	public static final RSArea chinningArea = new RSArea(new RSTile(2703,9111,0), new RSTile(2711,911,0));
	public static final RSArea alkharidArea = new RSArea(new RSTile(3306,3229,0), new RSTile(3321,3239,0));
	public static final RSArea gnomeTreeArea = new RSArea(new RSTile(2464,3500,3), new RSTile(2466,3502,3));
	public static final RSArea waydarArea = new RSArea(new RSTile(2641,4505,0), new RSTile(2658,4528,0));
	public static final RSArea lumdoArea = new RSArea(new RSTile(2887,2717,0), new RSTile(2902,2734,0));
	public static final RSArea apeAtollArea = new RSArea(new RSTile(2602,2699,0), new RSTile(2811,2712,0));
	public static final RSArea dungeonArea = new RSArea(new RSTile(2762,9102,0), new RSTile(2765,9104,0));
	public static final RSArea clanWarsArea = new RSArea(new RSTile(3382,3154,0), new RSTile(3393,3167,0));
	
	//Tiles
	public static final RSTile tileGlider = new RSTile(3284, 3213, 0);
	
	
}
