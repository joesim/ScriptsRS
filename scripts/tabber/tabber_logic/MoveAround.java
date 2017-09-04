package scripts.tabber.tabber_logic;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Conditions;
import scripts.tabber.utilities.Constants;
import scripts.tabber.utilities.Variables;
import scripts.utilities.Functions;

public class MoveAround implements Task {

	private ArrayList<RSTile> tiles = new ArrayList<RSTile>();
	private RSNPC[] butler = null;

	@Override
	public String action() {
		return "Re-positioning...";
	}

	@Override
	public int priority() {
		return 20;
	}

	@Override
	public boolean validate() {
		RSObject[] lecterns = Objects.findNearest(30, Constants.LECTERN);
		if (lecterns.length > 0) {
			RSTile lec = lecterns[0].getPosition();
			int xP = lec.getX();
			int yP = lec.getY() + 1;
			RSTile spot = new RSTile(xP, yP, 0);
			if (spot.equals(Player.getPosition())) {
				butler = NPCs.find(227);
				if (butler.length > 0) {
					int x = Player.getPosition().getX();
					int y = Player.getPosition().getY();
					tiles.add(new RSTile(x - 1, y - 1, 0));
					tiles.add(new RSTile(x + 1, y - 1, 0));
					tiles.add(new RSTile(x, y + 2, 0));

					if (tiles.contains(butler[0].getPosition())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void execute() {
		int x = Player.getPosition().getX();
		int y = Player.getPosition().getY();

		if (Functions.percentageBool(Variables.PERCENT_TALK_TO_BUTLER)){
			butler[0].click("Talk");
		} else if (butler[0].getPosition().equals(new RSTile(x, y + 2))) {
			RSTile toGo = new RSTile(x - 1, y);
			toGo.click("Walk here");
		} else {
			RSTile toGo = new RSTile(x, y + 1);
			toGo.click("Walk here");
		}
		DynamicWaiting.hoverWaitScreen(Conditions.messageOuOptions, General.random(1000, 3000), Screen.ALL_SCREEN);

	}

}
