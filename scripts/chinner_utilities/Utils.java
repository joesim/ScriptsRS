package scripts.chinner_utilities;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;

import scripts.utilities.AntiBan;

public class Utils {

	public static boolean isAttackingBestMonkey(){
		return false;
	}
	
	public static boolean selectBestMonkey(){
		return false;
	}

	public static boolean shouldSpreadMonkeys() {
		return false;
	}

	public static boolean isInNeedOfSupplies() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isPlayerAtApeAtoll() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isFullyReady() {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean haveEnoughChins() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean shouldHeal() {
		if (AntiBan.eat_at > getHealthPercent()){
			return true;
		}
		return false;
	}
	
	public static int getHealthPercent() {
		int k = Skills.SKILLS.HITPOINTS.getActualLevel();
		int s = 100 * (Skills.SKILLS.HITPOINTS.getCurrentLevel()) / k;
		return s;
	}

	public static void healWalking() {
		RSItem[] food = Inventory.find(Variables.SELECTED_FOOD);
		if (food.length>0){
			food[0].click("");
		}
	}

	public static boolean shouldDrinkPray() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isPoisoned() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean monkeyStopAttacking() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean shouldTeleOut() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean shouldReagroMonkeys() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean shouldHealHealth() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
