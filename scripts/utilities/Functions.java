package scripts.utilities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import com.sun.xml.internal.bind.v2.model.util.ArrayInfoUtil;

import sun.reflect.generics.tree.ArrayTypeSignature;

public final class Functions {

	/**
	 * Fonction qui genere un int dans l'intervalle
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generateRandomInt(int min, int max) {
		Random ran = new Random();
		return (ran.nextInt(max - min + 1) + min);
	}

	public static int generateRandomIntCloserToMin(int min, int max) {
		int rand = General.random(min, max);
		if ((rand - min) != 0) {
			for (int i = 0; i < (rand - min); i++) {
				if (percentageBool(30)) {
					rand--;
				}
			}
		}
		return rand;
	}

	/**
	 * Find nearest object id dans un area de tiles
	 * 
	 * @param distance
	 * @param tile1x
	 * @param tile1y
	 * @param tile2x
	 * @param tile2y
	 * @param ids
	 * @return
	 */
	public static RSObject findNearest(int distance, int tile1x, int tile1y, int tile2x, int tile2y, int... ids) {
		RSObject[] objects = Objects.findNearest(distance, ids);
		for (RSObject object : objects) {
			int theX = object.getPosition().getX();
			int theY = object.getPosition().getY();
			if ((object != null) && (theX >= tile1x) && (theY >= tile1y) && (theX <= tile2x) && (theY <= tile2y)) {
				return object;
			}
		}
		return null;
	}

	/**
	 * Find le nearest object selon id
	 * 
	 * @param distance
	 * @param ids
	 * @return
	 */
	public static RSObject findNearestId(int distance, int ids) {
		RSObject[] objects = Objects.findNearest(distance, ids);
		for (RSObject object : objects) {
			if ((object != null)) {
				return object;
			}
		}
		return null;
	}

	/**
	 * Find le nearest object selon id
	 * 
	 * @param distance
	 * @param ids
	 * @return
	 */
	public static RSItem findNearestItemId(int... ids) {
		RSItem[] objects = Inventory.find(ids);
		int distance = 1000000;
		RSItem item = null;
		for (RSItem object : objects) {
			if (Functions.distanceFromPoint(new TBox(object.getArea())) < distance) {
				distance = Functions.distanceFromPoint(new TBox(object.getArea()));
				item = object;
			}
		}
		return item;
	}

	/**
	 * 
	 * determine si les points sont proches selon la distance donnee
	 * 
	 * @param pt1
	 * @param pt2
	 * @return
	 */
	public static boolean pointsProches(java.awt.Point pt1, java.awt.Point pt2, int dist) {
		if (pt1 == null || pt2 == null) {
			return false;
		}
		if (Math.hypot(Math.abs(pt1.x - pt2.x), Math.abs(pt1.y - pt2.y)) <= dist) {
			return true;
		}
		return false;
	}

	public static int distanceFromPoint(TBox b) {
		Point mouse = Mouse.getPos();
		Point go = b.middlePoint();
		return (int) (Math.hypot(Math.abs(go.x - mouse.x), Math.abs(go.y - mouse.y)));
	}

	/**
	 * Angle entre deux vecteurs
	 * 
	 * @param A1
	 * @param A2
	 * @param B1
	 * @param B2
	 * @return
	 */
	public static double angleBetween2Lines(Point A1, Point A2, Point B1, Point B2) {
		double angle1 = (double) Math.atan2(A2.y - A1.y, A1.x - A2.x);
		double angle2 = (double) Math.atan2(B2.y - B1.y, B1.x - B2.x);
		double calculatedAngle = (double) Math.toDegrees(angle1 - angle2);
		if (calculatedAngle < 0)
			calculatedAngle += 360;
		return calculatedAngle;
	}

	/**
	 * Rotation du point (2ieme param) selon langle considerant que le point de
	 * base est 0,0
	 * 
	 * @param pointInFile
	 * @param pointStartNew
	 * @param angle
	 * @return
	 */
	public static Point rotate(Point pointInFile, Point prevPointFile, double angle) {
		int x1 = (int) Math.round((pointInFile.x - prevPointFile.x) * Math.cos(Math.toRadians(angle))
				- (pointInFile.y - prevPointFile.y) * Math.sin(Math.toRadians(angle)));
		int y1 = (int) Math.round((pointInFile.x - prevPointFile.x) * Math.sin(Math.toRadians(angle))
				+ (pointInFile.y - prevPointFile.y) * Math.cos(Math.toRadians(angle)));
		return new Point(x1, y1);
	}

	public static Point rotate(Point pointInFile, double angle) {
		int x1 = (int) (pointInFile.x * Math.cos(Math.toRadians(angle))
				- pointInFile.y * Math.sin(Math.toRadians(angle)));
		int y1 = (int) (pointInFile.x * Math.sin(Math.toRadians(angle))
				+ pointInFile.y * Math.cos(Math.toRadians(angle)));

		return new Point(x1, y1);

	}

	/**
	 * Met quatres integer dans une liste. 0 = null.
	 * 
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @return
	 */
	public static ArrayList<Integer> getListWithNumbers(int i, int j, int k, int l) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(i);
		list.add(j);
		list.add(k);
		list.add(l);
		return list;
	}

	public static boolean playerOnTile(RSTile tile, int range) {
		return (tile.getX() >= (Player.getPosition().getX() - range)
				&& tile.getY() >= (Player.getPosition().getY() - range)
				&& tile.getX() <= (Player.getPosition().getX() + range)
				&& tile.getY() <= (Player.getPosition().getY() + range));
	}

	public static boolean playerOnTiles(RSTile tile, RSTile tile1, RSTile tile2) {
		return (tile.getX() >= tile1.getX() && tile.getY() >= tile1.getY() && tile.getX() <= tile2.getX()
				&& tile.getY() <= tile2.getY() && tile.getPlane() == tile1.getPlane()
				&& tile.getPlane() == tile2.getPlane());
	}

	public static int getHealthPercent() {
		int k = Skills.SKILLS.HITPOINTS.getActualLevel();
		int s = 100 * (Skills.SKILLS.HITPOINTS.getCurrentLevel()) / k;
		return s;
	}

	public static int getHealth() {
		int k = Skills.SKILLS.HITPOINTS.getCurrentLevel();
		return k;
	}

	public static int getPrayer() {
		int k = Skills.SKILLS.PRAYER.getCurrentLevel();
		return k;
	}

	public static int getMaxHealth() {
		int k = Skills.SKILLS.HITPOINTS.getActualLevel();
		return k;
	}

	public static RSNPC findNearestNPC(int... ids) {
		RSNPC[] npcs = NPCs.findNearest(ids);
		for (RSNPC npc : npcs) {
			if (npc != null) {
				return npc;
			}
		}
		return null;
	}

	public static RSItem findNearestBankItem(int ids) {
		RSItem[] npcs = Banking.find(ids);
		for (RSItem npc : npcs) {
			if (npc != null) {
				return npc;
			}
		}
		return null;
	}

	public static boolean checkPoison() {
		if (Game.getSetting(102) > 0) {
			return true;
		}
		return false;
	}

	/** Fonction qui change de tab selon la cle passé en paramètre **/
	public static void FTAB(int tabNumber, double multiplier) {
		KeyboardThread kt = new KeyboardThread(tabNumber, multiplier);
		Thread t = new Thread(kt);
		t.start();
	}

	public static RSTile returnMiddleTile(RSTile t1, RSTile t2) {
		int x = ((t2.getX() - t1.getX()) / 2) + t1.getX();
		int y = ((t2.getY() - t1.getY()) / 2) + t1.getY();
		return new RSTile(x, y);
	}

	public static double distanceTiles(RSTile t1, RSTile t2) {

		double dist = Math.hypot(Math.abs(t1.getX() - t2.getX()), Math.abs(t1.getY() - t2.getY()));
		return dist;

	}

	/**
	 * NE DOIT PAS ETRE USE EN BOUCLE!!!!
	 * 
	 * @param pt
	 * @return
	 */
	public static boolean isOnScreen(Point pt) {
		if (pt != null) {
			if (pt.getX() > 0 && pt.getX() < 475 && pt.getY() > 0 && pt.getY() < 315) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPointsEqual(Point pt1, Point pt2) {
		int x = (int) pt1.getX();
		int y = (int) pt1.getY();
		int x2 = (int) pt2.getX();
		int y2 = (int) pt2.getY();

		if (x == x2 && y == y2) {
			return true;
		}
		return false;
	}

	public static RSItem findNearestEquipSlot(int i) {
		RSItem[] npcs = Equipment.find(i);
		for (RSItem npc : npcs) {
			if (npc != null) {
				return npc;
			}
		}
		return null;
	}

	public static boolean percentageBool(int percentage) {
		if (Functions.generateRandomInt(1, 100) <= percentage) {
			return true;
		}
		return false;
	}
	
	public static boolean percentageBool100000(double percentage) {
		int p = ((int) (percentage * 1000)) == 0 ? 1: ((int) (percentage * 1000));
		if (Functions.generateRandomInt(1, 100000) <= p) {
			return true;
		}
		return false;
	}

	public static RSItem[] concat(RSItem[] a, RSItem[] b) {
		int aLen = a.length;
		int bLen = b.length;
		RSItem[] c = new RSItem[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public static int randomIntMid(Integer min, Integer max){
		Double arg = Double.valueOf((max-min))/Double.valueOf(700);
		int number = SleepJoe.sleepHumanDelayOut(arg, min, max);
		return number;
	}
	
	public static int randomIntMidNeg(Integer min, Integer max){
		Double arg = Double.valueOf((max-min))/Double.valueOf(700);
		int number = SleepJoe.sleepHumanDelayOut(arg, min, max);
		return number;
	}
	
	public static double randomDouble(Double min, Double max){
		Double arg = Double.valueOf(General.random((int)(min*100), (int)(max*100)))/Double.valueOf(100);
		return arg;
	}

	public static RSItem[] inverseArray(RSItem[] yewu) {
		for(int i = 0; i < yewu.length / 2; i++)
		{
		    RSItem temp = yewu[i];
		    yewu[i] = yewu[yewu.length - i - 1];
		    yewu[yewu.length - i - 1] = temp;
		}
		return yewu;
	}

	public static int getRandomWorld() {
		int[] worlds = {303,304,305,306,307,309,310,311,312,313,314,315,319,320,321,322,323,324,338,339,340,341,377,378};
		return worlds[General.random(0, worlds.length-1)];
	}

	public static void leaveGame() {
		Mouse.move(new Point(General.random(-100, 700), General.random(-500, -100)));
	}
}
