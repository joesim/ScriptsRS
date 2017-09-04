package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;

import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.TBox;
import scripts.woodcuttingUtilities.WoodcuttingState;

public class WCWillow extends Script {

	public static int SPEED_MOUSE = 1;
	public static int SPEED_MOUSE_TRIBOT = 105;
	public final static int[] WILLOWS = {1750,1756,1760};
	public static RSObject cuttingTree = null;
	public static WoodcuttingState state = WoodcuttingState.CUTTING;
	
	private void onStart() {
		MouseMoveJoe.loadDataNormal();
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		Mouse.setSpeed(SPEED_MOUSE_TRIBOT);
		HoverBox.load();
	}

	
	
	@Override
	public void run() {
		onStart();

		switch (state){
		case BANKING:
			break;
		case CUTTING:
			RSObject[] willows = Objects.findNearest(15, WILLOWS);
			if (willows.length>0){
				println("Cutting tree.....");
				cuttingTree = willows[0];
				MouseMoveJoe.humanMouseMove(new TBox(cuttingTree.getModel().getCentrePoint(),40), SPEED_MOUSE); //TODO: human move avec condition
				if (Game.isUptext("Chop down") && !c.checkCondition){
					MouseMoveJoe.fastClick(1, 1);
				}
				//MouseMoveJoe.hoverWc(box, c, speed);
			} else {
				println("No trees found...");
				//MouseMoveJoe.hoverWc(box, c, speed);
			}
			break;
		case DROPPING:
			break;
		default:
			break;
		}
		
	}
}
