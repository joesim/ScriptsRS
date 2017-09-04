package scripts.mlm.mlmLogic;

import java.util.ArrayList;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.mlm.mlmUtilities.CamThread;
import scripts.mlm.mlmUtilities.Variables;
import scripts.utilities.AntiBan;
import scripts.utilities.Functions;

public class MineRock implements Task {

	public static int[] VEINS = { 26661, 26662, 26663, 26664 };

	@Override
	public String action() {
		return "Mining a vein...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		if (Functions.percentageBool(Variables.PERCENT_CLICK_VEIN_FULL) && Inventory.isFull()) {
			return true;
		} else if (!Inventory.isFull()) {
			return true;
		}
		return false;
	}

	@Override
	public void execute() {
		if (Functions.percentageBool(Variables.PERCENT_CAMERA_MOVE_RIGHT)) {
			CamThread c = new CamThread();
			c.setCamera(Variables.currentArea.rotation, 100);
		}

		RSObject vein = findNextVein();
		//Go to vein and click.
		
		//Wait until done.
		

	}

	private RSObject findNextVein() {
		RSObject[] veins = Objects.findNearest(15, "Ore vein");
		RSObject[] foundVein = null;
		ArrayList<RSObject> listeVeins = new ArrayList<RSObject>();
		for (RSObject vein: veins){
			if (Variables.currentArea.area.contains(vein)){
				listeVeins.add(vein);
			}
		}
		foundVein = listeVeins.toArray(foundVein);
		return AntiBan.selectNextTarget(foundVein);
	}

}
