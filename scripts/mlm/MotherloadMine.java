package scripts.mlm;

import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.webwalker_logic.WebWalker;

public class MotherloadMine extends Script {

	public static int numberWay = 1;
	
	@Override
	public void run() {
		WebWalking.walkTo(new RSTile(3748,5673,0));
	}

}
