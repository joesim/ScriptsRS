package scripts.flaxUtilities;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;

public class CheckSpinning implements Runnable {

	public static volatile boolean notSpinning = false;
	public volatile boolean finished = false;
	
	public void stopMe(){
		finished = true;
		notSpinning = false;
	}
	
	@Override
	public void run() {
		while (!finished){
			
			int numberFlax = Inventory.find(1779).length;
			General.sleep(5000,7000);
			int numberFlaxTwo = Inventory.find(1779).length;
			if (numberFlax == numberFlaxTwo && !finished){
				notSpinning = true;
			} else {
				notSpinning = false;
			}
			
			
		}
	}

}
