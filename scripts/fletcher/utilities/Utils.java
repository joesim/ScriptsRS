package scripts.fletcher.utilities;

import org.tribot.api.General;

import scripts.utilities.Functions;

public class Utils {

	public static void randomSleep(){
		if (Functions.percentageBool100000(Variables.PERCENT_RANDOM_SLEEP)){
			General.sleep(200,7000);
		}
	}
	
}
