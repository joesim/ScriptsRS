package scripts.mlm.mlmLogic;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.mlm.mlmUtilities.CamThread;
import scripts.mlm.mlmUtilities.Constants;
import scripts.mlm.mlmUtilities.Variables;
import scripts.utilities.Functions;

public class ClickLadderTop implements Task {

	@Override
	public String action() {
		return "Clicking ladder from top...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return Inventory.isFull() && Game.getPlane()==1;
	}

	@Override
	public void execute() {
		RSObject[] ladder = Objects.find(20, Constants.LADDER_TOP);
		if (ladder.length>0){
			if (ladder[0].isOnScreen()){
				//Click it
			} else {
				if (Functions.percentageBool(Variables.PERCENT_MM)){
					//Walk minimap
				} else if (Functions.percentageBool(Variables.PERCENT_TURN_TO_LAD)) {
					CamThread c = new CamThread();
					c.turnToTile(ladder[0].getPosition());
					if (Functions.percentageBool(Variables.PERCENT_MOUSE_CAM_TURN)){
						//Mouse try to follow vein
					}
				} else {
					//Walk screen
				}
				//wait on screen
				
				//click ladder
				
				//wait game plane = 0
				while (true){
					//hover where it gonna be box.
					RSObject[] deposit = Objects.find(40, Constants.DEPOSIT);
				}
			}
		}
	}

}
