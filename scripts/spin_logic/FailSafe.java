package scripts.spin_logic;

import org.tribot.api.Timing;
import org.tribot.api.rs3.Skills;

public class FailSafe implements Runnable {

	@Override
	public void run() {
		
		int lastXP = Skills.getCurrentXP(Skills.SKILLS.CRAFTING);
		long lastT = Timing.currentTimeMillis();
		
		while (true){
			if (lastXP == Skills.getCurrentXP(Skills.SKILLS.CRAFTING)){
				if ((Timing.currentTimeMillis()-lastT)>300000){
					throw new NullPointerException();
				}
			} else {
				lastT = Timing.currentTimeMillis();
				lastXP = Skills.getCurrentXP(Skills.SKILLS.CRAFTING);
			}
		}
		
		
		
	}

}
