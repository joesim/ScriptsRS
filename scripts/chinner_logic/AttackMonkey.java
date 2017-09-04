package scripts.chinner_logic;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.chinner_utilities.Constants;
import scripts.chinner_utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.TBox;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class AttackMonkey implements Task {

	@Override
	public String action() {
		return "Attacking monkeys...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return ((Player.getRSPlayer().getInteractingIndex() == -1 || !Utils.isAttackingBestMonkey())
				&& !Utils.monkeyStopAttacking() && !Utils.shouldHeal() && !Utils.shouldTeleOut()
				&& !Utils.shouldSpreadMonkeys());
	}

	@Override
	public void execute() {
		RSNPC monkeyToAttack = findBestMonkey();
		if (monkeyToAttack!=null){
			
			if (monkeyToAttack.isOnScreen()){
				if (AccurateMouse.click(monkeyToAttack, "Attack")){
					DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {	
							return false;
						}
					}, General.random(300000, 500000), Screen.ALL_SCREEN);
				} else {
					General.sleep(300, 1000);
				}
			} else {
				Camera.turnToTile(monkeyToAttack.getPosition());
			}
			
		}
	}

	private RSNPC findBestMonkey(){
		
		Filter<RSNPC> filterID = Filters.NPCs.idEquals(Constants.SKELETAL_MONKEY);
		Filter<RSNPC> filterMonkey = filterID.combine(Filters.NPCs.inArea(Constants.chinningArea), false);
		
		RSNPC[] monkeys = NPCs.findNearest(filterMonkey);
		
		//Mapping monkeys.
		RSNPC bestMonkey = null;
		Integer bestNumberMonkeys = 0;
		for (RSNPC monkey: monkeys){
			RSArea areaMonkey = new RSArea(monkey.getPosition(), 1);
			Integer numberMonkeys = 0;
			for (RSNPC monkey2: monkeys){
				if (areaMonkey.contains(monkey2.getPosition())){
					numberMonkeys++;
				}
			}
			if (numberMonkeys>bestNumberMonkeys){
				bestNumberMonkeys = numberMonkeys;
				bestMonkey = monkey;
			}
		}
		return bestMonkey;
	}
	
}
