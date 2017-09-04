package scripts;

import java.awt.Point;

import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import scripts.utilities.Condition;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.utilities.Timer;


@ScriptManifest(authors = { "Joe" }, category = "Ranged", name = "JoeChins")
public class ChinningJoe extends Script {

	static MouseMoveJoe moveMM = new MouseMoveJoe();
	SleepJoe sleepJoe = new SleepJoe();
	static ABCUtil util = new ABCUtil();
	public static int eatPercentage = 50;
	public static int prayerPercentage = 20;
	public static int rangedPercentage = 80;
	Timer tmMove = new Timer(1000000);
	RSTile standSpot1 = new RSTile(2704, 9116, 0);
	RSTile standSpot2 = new RSTile(2709, 9115, 0);
	public final static int SKEL = 5237;

	@Override
	public void run() {

		setAIAntibanState(false);
		eatPercentage = util.generateEatAtHP();
		prayerPercentage = Functions.generateRandomInt(8, 30);
		boolean chinning = true;

		while (chinning) {
			moveMM.hoverMouse(new TBox(0, 0, 740, 500), new Condition(16, 0, 0, 0));
			checkHealth();
			checkPrayer();
			if (!checkAttackingEnough()) {
				println("Checking if attacking enough is not enough");
				if (Functions.generateRandomInt(1, 10) > 3) {
					checkAttackingRightOne(); // move inside this function.
				} else {
					move();
					sleepJoe.sleepHumanDelay(4, 0, 10000);
				}
			}
			if (checkIfShouldMove()) {
				lureAgain();
			}
			checkIfShouldTele(); // banking dans cette fonction.
			if (checkOnTheseTiles()) {
				move();
			}
			checkRanged();
			checkPoison();
		}

	}

	private void lureAgain() {
		checkPrayer();
		int range = Functions.generateRandomInt(-5, 5);
		int rangeY = Functions.generateRandomInt(0, 1);
		RSTile[] goingPath = Walking.generateStraightPath(new RSTile(2732 + range, 9106 + rangeY, 0));
		Walking.walkPath(goingPath);
		sleep(3000, 6000);
		Walking.walkPath(Walking.invertPath(goingPath));
		while (!Functions.playerOnTiles(Player.getPosition(), new RSTile(2703,9114,0), new RSTile(2709,9117,0))){
			int x = Functions.generateRandomInt(-2, 2);
			int y = Functions.generateRandomInt(-1, 1);
			Walking.walkPath(Walking.generateStraightPath(new RSTile(2705+x,9115+y,0)));
		}
		sleepJoe.sleepHumanDelay(1, 0, 10000);

	}

	private void move() {

		if (Functions.playerOnTile(standSpot1, 1)) {
			Point point = Projection.tileToScreen(standSpot2, 0);
			moveMM.humanMouseMove(new TBox(point, 15, 7));
			moveMM.spamClick(new TBox(point, 10, 5), 2, 1);
			sleepJoe.sleepHumanDelay(0.2, 0, 600);
		} else {
			Point point = Projection.tileToScreen(standSpot1, 0);
			moveMM.humanMouseMove(new TBox(point, 15, 7));
			moveMM.spamClick(new TBox(point, 10, 5), 2, 1);
			sleepJoe.sleepHumanDelay(0.2, 0, 600);
		}

	}

	public boolean checkOnTheseTiles() {
		if (Functions.playerOnTile(new RSTile(2704,9113,0), 0) || Functions.playerOnTile(new RSTile(2707,9113,0), 0) || Functions.playerOnTile(new RSTile(2710,9114,0), 0)) {
			println("We are on dangerous tiles");
			return true;
		}
		return false;

	}

	public boolean checkAttackingEnough() {
		RSNPC[] npcs = NPCs.findNearest(SKEL);
		int attacking = 0;
		for (RSNPC skel : npcs) {
			if (skel != null) {
				if (skel.isInCombat() && Functions.playerOnTiles(skel.getPosition(), new RSTile(2703,9114,0), new RSTile(2709,9117,0))) {
					attacking++;
				}
			}
		}
		println(attacking);
		if (attacking >= 5) {
			return true;
		} else
			return false;
	}

	private void checkIfShouldTele() {
		if (eatPercentage >= Functions.getHealthPercent()) {
			RSItem shark = Functions.findNearestItemId(385);
			if (shark==null){
				RSItem tele = Functions.findNearestItemId(8007);
				moveMM.humanMouseMove(new TBox(tele.getArea()));
				moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
			}
		} else if (prayerPercentage >= Skills.SKILLS.PRAYER.getCurrentLevel()){
			RSItem pot = Functions.findNearestItemId(2434, 139, 141, 143);
			if (pot == null){
				RSItem tele = Functions.findNearestItemId(8007);
				moveMM.humanMouseMove(new TBox(tele.getArea()));
				moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);				
			}
		}

	}

	private void checkIfAttacking() {
		// TODO Auto-generated method stub

	}

	private void checkAttackingRightOne() {
		RSNPC[] npcs = NPCs.findNearest(SKEL); // skeleton ids.
		int[][] table = new int[10][10];
		RSTile tl = new RSTile(2703, 9112, 0);
		int areaX = 7;
		int areaY = 5;
		for (int i = 0; i < areaY; i++) {
			for (int j = 0; j < areaX; j++) {
				RSTile tile = new RSTile(j + tl.getX(), i + tl.getY(), 0);
				for (RSNPC skel : npcs) {
					if (skel != null) {
						if (skel.getPosition().equals(tile)) {
							table[i][j]++;
						}
					}
				}
			}
		}
		int bestScore = 0;
		RSTile tileBest = null;
		for (int i = 1; i < areaY - 1; i++) {
			for (int j = 1; j < areaX - 1; j++) {
				int[][] subMatrix = new int[3][3];
				subMatrix = generateSubmatrix(table, i, j);
				if (sum2d(subMatrix) > bestScore) {
					bestScore = sum2d(subMatrix);
					tileBest = new RSTile(j + tl.getX(), i + tl.getY(), 0);
				}
			}
		}
		println(tileBest);
		for (RSNPC skel : npcs) {
			if (skel != null) {
				if (skel.getPosition().equals(tileBest)) {
					moveMM.humanMouseMove(new TBox(skel.getPosition().getHumanHoverPoint(), 15, 15));
					moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 1, 0);
				}
			}
		}
		sleepJoe.sleepHumanDelay(2, 0, 1000);
	}

	private int[][] generateSubmatrix(int[][] table, int i, int j) {
		int[][] subMatrix = new int[3][3];
		int countI = 0;
		int countJ = 0;
		for (int k = i - 1; k <= i + 1; k++) {
			for (int l = j - 1; l <= j + 1; l++) {
				subMatrix[countI][countJ] = table[k][l];
				countJ++;
			}
			countJ = 0;
			countI++;
		}
		return subMatrix;
	}

	private void checkPoison() {
		if (Game.getSetting(102) > 0) {
			RSItem pot = Functions.findNearestItemId(5952, 5954, 5956, 5958);
			if (pot != null) {
				moveMM.humanMouseMove(new TBox(pot.getArea()));
				moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
			}
		}
	}

	public static void checkPrayer() {
		if (prayerPercentage >= Skills.SKILLS.PRAYER.getCurrentLevel()) {
			RSItem pot = Functions.findNearestItemId(2434, 139, 141, 143);
			if (pot != null) {
				moveMM.humanMouseMove(new TBox(pot.getArea()));
				moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				prayerPercentage = Functions.generateRandomInt(8, 30);
			}
		}

	}
	
	public static void checkRanged() {
		if (rangedPercentage >= Skills.SKILLS.RANGED.getCurrentLevel()) {
			RSItem pot = Functions.findNearestItemId(2444,169,171,173);
			if (pot != null) {
				moveMM.humanMouseMove(new TBox(pot.getArea()));
				moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				prayerPercentage = Functions.generateRandomInt(75,80);
			}
		}

	}

	public static void checkHealth() {

		if (eatPercentage >= Functions.getHealthPercent()) {
			RSItem shark = Functions.findNearestItemId(385);
			if (shark != null) {
				moveMM.humanMouseMove(new TBox(shark.getArea()));
				moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 3, 2);
				eatPercentage = util.generateEatAtHP();
			}
		}

	}

	public boolean getCheckRanged() {
		if (rangedPercentage >= Skills.SKILLS.RANGED.getCurrentLevel()) {
			return true;
		}
		return false;
	}
	
	public boolean getCheckPrayer() {
		if (prayerPercentage >= Skills.SKILLS.PRAYER.getCurrentLevel()) {
				return true;
		}
		return false;

	}

	public boolean getCheckHealth() {

		if (eatPercentage >= Functions.getHealthPercent()) {
				return true;
		}
		return false;

	}
	
	public boolean getCheckTele() {

		if (eatPercentage >= Functions.getHealthPercent()) {
			RSItem shark = Functions.findNearestItemId(385);
			if (shark==null){
				return true;
			}
		} else if (prayerPercentage >= Skills.SKILLS.PRAYER.getCurrentLevel()){
			RSItem pot = Functions.findNearestItemId(2432, 139, 141, 143);
			if (pot == null){
				return true;
			}
		}
		return false;

	}

	public boolean checkIfShouldMove() {

		RSNPC[] npcs = NPCs.findNearest(SKEL);
		int noInteract = 0;
		for (RSNPC skel : npcs) {
			if (skel != null && Functions.playerOnTiles(skel.getPosition(), new RSTile(2701,9099,0), new RSTile(2715,9110,0))) {
				if (!skel.isInteractingWithMe()) {
					noInteract++;
				}
			}
		}
		if (noInteract >= 2) {
			return true;
		} else {
			return false;
		}
	}

	public static int sum2d(int[][] array2d) {
		int sum = 0;
		for (int row = 0; row < array2d.length; row++) {
			for (int col = 0; col < array2d[row].length; col++) {
				sum = sum + array2d[row][col];
			}
		}

		return sum;
	}
}
