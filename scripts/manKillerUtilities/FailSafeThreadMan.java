package scripts.manKillerUtilities;

import org.tribot.api.General;
import org.tribot.api2007.Skills;

public class FailSafeThreadMan implements Runnable {

	private int exp = 0;
	
	@Override
	public void run() {
		
		exp = Skills.getXP(Skills.SKILLS.HITPOINTS);
		
		while (true){
			General.sleep(100000);
			if (exp == Skills.getXP(Skills.SKILLS.HITPOINTS)){
				ConditionMan c = null;
				c.checkCondition();
			} else {
				exp = Skills.getXP(Skills.SKILLS.HITPOINTS);
			}
			
		}
		
	}

}
