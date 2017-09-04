package scripts.mlm;

import org.tribot.api2007.Objects;
import org.tribot.script.Script;

public class MotherloadMineTest extends Script {

	@Override
	public void run() {
		println(Objects.findNearest(5, "Ore vein")[0].getOrientation());
	}

}
