package scripts.utilities;

import org.tribot.api.General;
import org.tribot.api2007.types.RSTile;

import scripts.webwalker_logic.WebWalker;

public class WalkingJoe {

	public static void walkingTo(RSTile tile){
		int off = General.random(1, 4);
		WebWalker.Offset offset = null;
		switch (off){
		case 1:
			offset = WebWalker.Offset.LOW;
			break;
		case 2:
			offset = WebWalker.Offset.MEDIUM;
			break;
		case 3:
			offset = WebWalker.Offset.HIGH;
			break;
		case 4:
			offset = WebWalker.Offset.VERY_HIGH;
			break;
		}
		WebWalker.setPathOffset(offset);
		WebWalker.walkTo(tile);
	}
	
	public static void walkingBank(){
		int off = General.random(1, 4);
		WebWalker.Offset offset = null;
		switch (off){
		case 1:
			offset = WebWalker.Offset.LOW;
			break;
		case 2:
			offset = WebWalker.Offset.MEDIUM;
			break;
		case 3:
			offset = WebWalker.Offset.HIGH;
			break;
		case 4:
			offset = WebWalker.Offset.VERY_HIGH;
			break;
		}
		WebWalker.setPathOffset(offset);
		WebWalker.walkToBank();
	}
}
