package scripts.zulrahUtilities;

import java.awt.Point;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class PositionZul {
	public int pos = 0;
	public RSTile tile1 = null;
	public RSTile tile2 = null;

	private RSTile debut = null;
	private RSTile tileSafe1 = null;
	
	public PositionZul(RSTile debut, int pos) {
		this.debut = debut;
		tileSafe1 = new RSTile(debut.getX()+6,debut.getY()+9,0);
		if (pos == 1) {
			tile1 = new RSTile(debut.getX() + 4, debut.getY() + 9, 0);
			tile2 = new RSTile(debut.getX() + 6, debut.getY() + 10, 0);
		}

		if (pos == 2) {
			tile1 = new RSTile(debut.getX() + 4, debut.getY() + 3, 0);
			tile2 = new RSTile(debut.getX() + 6, debut.getY() + 5, 0);
		}

		if (pos == 3) {
			tile1 = new RSTile(debut.getX() + 1, debut.getY() + 0, 0);
			tile2 = new RSTile(debut.getX() + 3, debut.getY() + 2, 0);
		}

		if (pos == 4) {
			tile1 = new RSTile(debut.getX() - 3, debut.getY() + 0, 0);
			tile2 = new RSTile(debut.getX() - 1, debut.getY() + 2, 0);
		}

		if (pos == 5) {
			tile1 = new RSTile(debut.getX() - 6, debut.getY() + 4, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 6, 0);
		}

		if (pos == 6) {
			tile1 = new RSTile(debut.getX() - 6, debut.getY() + 9, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 10, 0);
		}

		if (pos == 7) {
			tile1 = new RSTile(debut.getX() - 6, debut.getY() + 7, 0);
			tile2 = new RSTile(debut.getX() - 3, debut.getY() + 10, 0);
		}
		if (pos == 8) {
			tile1 = new RSTile(debut.getX() + 4, debut.getY() + 10, 0);
			tile2 = new RSTile(debut.getX() + 4, debut.getY() + 10, 0);
		}
		if (pos == 9) {
			tile1 = new RSTile(debut.getX() - 4, debut.getY() + 10, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 10, 0);
		}
		if (pos == 10) {
			tile1 = new RSTile(debut.getX() - 4, debut.getY() + 4, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 4, 0);
		}
		if (pos == 11) {
			tile1 = new RSTile(debut.getX() + 3, debut.getY() + 2, 0);
			tile2 = new RSTile(debut.getX() + 6, debut.getY() + 10, 0);
		}
		if (pos == 12) {
			tile1 = new RSTile(debut.getX() - 6, debut.getY() + 1, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 10, 0);
		}
		if (pos == 13) {
			tile1 = new RSTile(debut.getX() - 3, debut.getY(), 0);
			tile2 = new RSTile(debut.getX() + 3, debut.getY() + 2, 0);
		}
		if (pos == 14) {
			tile1 = new RSTile(debut.getX() + 4, debut.getY() + 3, 0);
			tile2 = new RSTile(debut.getX() + 4, debut.getY() + 3, 0);
		}
		if (pos == 15) {
			tile1 = new RSTile(debut.getX() - 6, debut.getY() + 4, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 5, 0);
		}
		if (pos == 16) {
			tile1 = new RSTile(debut.getX() - 6, debut.getY() + 1, 0);
			tile2 = new RSTile(debut.getX() - 4, debut.getY() + 3, 0);
		}
		if (pos == 17) {
			tile1 = new RSTile(debut.getX()+6,debut.getY()+9,0);
			tile2 = new RSTile(debut.getX()+6,debut.getY()+9,0);
		}
		this.pos = 1000 + pos;
	}

	public boolean checkPlayerOk() {
		boolean bool = Functions.playerOnTiles(Player.getPosition(), tile1, tile2);
		return bool;
	}
	
	public RSTile getMiddleTile(){
		return Functions.returnMiddleTile(tile1, tile2);
	}

	public void moveCorrectPos() {
		if ((!checkTileSafeRed() || pos!=1008) && !checkPlayerOk() && (Game.getDestination()== null || !Functions.playerOnTiles(Game.getDestination(), tile1, tile2))) {
			Point point = Projection.tileToScreen(Functions.returnMiddleTile(tile1, tile2), 0);
			if (point.x > 3 && point.y > 3 && point.y < 315 && point.x < 490) {
				DynamicClicking.clickRSTile(Functions.returnMiddleTile(tile1, tile2), "Walk here");
				SleepJoe.sleepHumanDelay(0.2, 0, 600);
			} else {
				RSTile t = Functions.returnMiddleTile(tile1, tile2);
				RSTile[] goingPath = Walking.generateStraightPath(new RSTile(t.getX(), t.getY(), 0));
				Walking.walkPath(goingPath);
			}
		}

	}

	public boolean checkTileGoodPosition(RSTile t) {
		if (t == null) {
			return false;
		}
		boolean bool = Functions.playerOnTiles(t, tile1, tile2);
		return bool;
	}
	
	public boolean checkTileSafeRed(){
		if (Functions.playerOnTiles(Player.getPosition(), tileSafe1,tileSafe1)){
			return true;
		}
		return false;
	}
	
	public void moveRedCoin(boolean left, boolean originalPos) {
		Point point = null;
		RSTile tile = null;
		int randomX = General.random(-1, 1);
		int randomY = General.random(-1, 1);
		if (left && !originalPos && Functions.playerOnTiles(Player.getPosition(), tileSafe1,tileSafe1)){
			return;
		} 
		
		if (left && !originalPos) {
			tile = new RSTile(debut.getX() + 9 + randomX, debut.getY() + 11+ randomY, 0);
			point = Projection.tileToScreen(tile, 0);
		} else if (left && originalPos) {
			tile = new RSTile(debut.getX() + 2+ randomX, debut.getY() + 11+ randomY, 0);
			point = Projection.tileToScreen(tile, 0);
		} else if (!left && !originalPos) {
			tile = new RSTile(debut.getX() - 9+ randomX, debut.getY() + 9+ randomY, 0);
			point = Projection.tileToScreen(tile, 0);
		} else if (!left && originalPos) {
			tile = new RSTile(debut.getX() - 2+ randomX, debut.getY() + 11+ randomY, 0);
			point = Projection.tileToScreen(tile, 0);
		}
		if (Functions.isOnScreen(point)) {
			MoveMouseZul.humanMouseMove(new TBox(point, 30, 30), UniqueZulrah.SPEED_POSITION);
			MoveMouseZul.fastClick(1, 1);
			MoveMouseZul.fastClick(1, 1);
			SleepJoe.sleepHumanDelay(0.2, 0, 100);
		} else {
			RSTile[] goingPath = Walking.generateStraightPath(tile);
			Walking.walkPath(goingPath);
		}
	}

}
