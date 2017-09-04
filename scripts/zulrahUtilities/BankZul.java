package scripts.zulrahUtilities;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters.GroundItems;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.Zulrah;
import scripts.utilities.ACamera;
import scripts.utilities.CheckInventoryValue;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.SleepJoe;
import scripts.utilities.StaticTimer;
import scripts.utilities.TBox;

public class BankZul {

	private static RSItem ror = null;
	private static RSItem rod = null;
	private static RSItem rang = null;
	private static RSItem mag = null;
	private static RSItem tele = null;

	private ACamera cam = null;

	public BankZul(Script s) {
		cam = new ACamera(s);
	}

	public void bank() {
		SleepJoe.sleepHumanDelay(2, 1, 10000);

		CheckInventoryValue c = new CheckInventoryValue(false);
		Thread t = new Thread(c);
		t.start();
		
		Zulrah.bankState = BankingState.TELEPORTING;

		int whenSwitch = Functions.generateRandomInt(1, 3);

		while (Zulrah.state == ZulrahState.BANKING) {
			CustomAntiban.antiban();
			switch (Zulrah.bankState) {
			case TELEPORTING:
				if (!Inventory.open()) {
					Functions.FTAB(27, 1);
				}

				if (whenSwitch == 1) {
					Switching.switchGearMagic();
					SleepJoe.sleepHumanDelay(1, 1, 800);
				}

				General.println("Teleporting");

				do {
					rod = Functions.findNearestItemId(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566);
					if (rod != null) {
						if (!Inventory.open()) {
							Functions.FTAB(27, 1);
						}
						MoveMouseZul.humanMouseMove(new TBox(rod.getArea()), Zulrah.SPEED);
						rod.click("Rub");
						MoveMouseZul.humanMouseMove(new TBox(182, 418, 452, 433), 1.5);
						while (NPCChat.getOptions() == null) {
							General.sleep(400);
						}
						MoveMouseZul.fastClick(1, 1);
						SleepJoe.sleepHumanDelay(0.2, 1, 300);

						StaticTimer.reset();
						MoveMouseZul.hoverMouse(HoverBox.get(4, 80, 1), Zulrah.c17);
					}
				} while (!Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1cw, Zulrah.tile2cw));
				if (whenSwitch == 2) {
					Switching.switchGearMagic();
					SleepJoe.sleepHumanDelay(1, 1, 800);
				}
				Zulrah.bankState = BankingState.TAKEOUTROR;
				break;
			case TAKEOUTROR:
				boolean breakOrMessage = false;
				boolean invWasFull = false;
				if (Inventory.getAll().length==28){
					Inventory.find(InventoryZulrah.MAIN_FOOD)[0].click("Drop");
				}
				
				SleepJoe.sleepHumanDelay(1, 1, 1000);
				Functions.FTAB(115, 1);
				SleepJoe.sleepHumanDelay(0.7, 1, 1000);

				// Retirer ror
				ror = Functions.findNearestItemId(2550);
				if (ror != null) {
					ror.click("Break");
					MoveMouseZul.humanMouseMove(new TBox(207, 394, 349, 410), 1.4);
					while (NPCChat.getOptions() == null && NPCChat.getMessage() == null) {
						General.sleep(400);
					}
					if (NPCChat.getOptions() != null) {
						MoveMouseZul.fastClick(1, 1);
						breakOrMessage = true;
					} else if (NPCChat.getMessage() != null) {
						breakOrMessage = true;
					}
				}

				ror = Functions.findNearestEquipSlot(2550);
				if (ror != null) {
					ror.click("Remove");
					Inventory.open();
					SleepJoe.sleepHumanDelay(0.5, 1, 1000);
					do {
						ror = Functions.findNearestItemId(2550);
						if (ror == null) {
							SleepJoe.sleepHumanDelay(1, 1, 700);
						}
					} while (ror == null);
					ror.click("Break");
					MoveMouseZul.humanMouseMove(new TBox(207, 394, 349, 410), 1.4);
					while (NPCChat.getOptions() == null && NPCChat.getMessage() == null) {
						General.sleep(200);
					}
					if (NPCChat.getOptions() != null) {
						MoveMouseZul.fastClick(1, 1);
						breakOrMessage = true;
					} else if (NPCChat.getMessage() != null) {
						breakOrMessage = true;
					}
				} else {
					Zulrah.bankState = BankingState.WALKTOBANK;
					break;
				}
				if (breakOrMessage) {
					Zulrah.bankState = BankingState.WALKTOBANK;
				}
				if (invWasFull){
					org.tribot.api2007.GroundItems.find(InventoryZulrah.MAIN_FOOD)[0].click("Take");
				}
				break;
			case WALKTOBANK:
				// Walking bank + open
				General.println("Walking + open Bank");
				Zulrah.bankClanWars = Functions.findNearestId(100, 26707);
				boolean whencamera = false;
				if (Functions.percentageBool(80)) {
					cam.turnToTile(Zulrah.tileBank);
					SleepJoe.sleepHumanDelay(0.5, 1, 300);
					MoveMouseZul.hoverMouse(HoverBox.get(4), new ConditionTime(SleepJoe.sleepHumanDelayOut(4, 1, 10000)),
							Zulrah.SPEED_HOVER, false);
					whencamera = true;
				}

				do {
					RSTile[] goingPath = Walking.generateStraightPath(Zulrah.tileBank);
					Walking.walkPath(goingPath);
				} while (!Zulrah.bankClanWars.isOnScreen());

				if (!whencamera) {
					cam.turnToTile(Zulrah.tileBank);
					SleepJoe.sleepHumanDelay(0.5, 1, 300);
				}

				if (whenSwitch == 3) {
					Switching.switchGearMagic();
					SleepJoe.sleepHumanDelay(1, 1, 800);
				}
				Zulrah.bankState = BankingState.OPENBANK1;
				break;
			case OPENBANK1:
				// Attendre bank visible
				if (!Zulrah.bankClanWars.isOnScreen()) {
					cam.turnToTile(Zulrah.bankClanWars.getPosition());
					StaticTimer.reset();
					MoveMouseZul.hoverMouse(HoverBox.get(7), Zulrah.c16);
					if (StaticTimer.getElapsed() > 5000) {
						Zulrah.bankState = BankingState.WALKTOBANK;
						break;
					}
				}
				do {
					DynamicClicking.clickRSModel(Zulrah.bankClanWars.getModel(), "Bank");
					SleepJoe.sleepHumanDelay(0.05, 1, 100);
				} while (!(Game.getCrosshairState() == 2));

				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(15), Zulrah.c20);
				if (StaticTimer.getElapsed() > 8000) {
					break;
				}

				if (!InventoryZulrah.checkMageGearOn()) {
					Functions.FTAB(27, 1);
					MoveMouseZul.hoverMouse(HoverBox.get(4), Zulrah.c31, 2, false);
					Inventory.open();
					Switching.switchGearMagic();
					break;
				}
				Zulrah.bankState = BankingState.PUTROR;
				break;
			case PUTROR:
				MoveMouseZul.humanMouseMove(new TBox(431, 300, 456, 325), 0.7);
				MoveMouseZul.fastClick(1, 1);
				ror = Functions.findNearestBankItem(2550);
				MoveMouseZul.humanMouseMove(new TBox(ror.getArea()), 0.7);
				MoveMouseZul.fastClickBank(1, 1);
				Functions.FTAB(27, 1);
				MoveMouseZul.hoverMouse(HoverBox.get(27), Zulrah.c31, 2, false);
				Inventory.open();
				int cnt = 0;
				do {
					ror = Functions.findNearestItemId(2550);
					if (ror == null) {
						SleepJoe.sleepHumanDelay(1, 1, 700);
					}
					if (cnt > 30) {
						Zulrah.bankState = BankingState.OPENBANK1;
						break;
					}
					cnt++;
				} while (ror == null);
				if (Zulrah.bankState == BankingState.OPENBANK1) {
					break;
				}
				MoveMouseZul.humanMouseMove(new TBox(ror.getArea()), 0.7);
				MoveMouseZul.fastClick(1, 1);
				Zulrah.bankState = BankingState.OPENBANK2;
				break;
			case OPENBANK2:
				if (!Zulrah.bankClanWars.isOnScreen()) {
					cam.turnToTile(Zulrah.bankClanWars.getPosition());
					StaticTimer.reset();
					MoveMouseZul.hoverMouse(HoverBox.get(7), Zulrah.c16);
					if (StaticTimer.getElapsed() > 5000) {
						Zulrah.bankState = BankingState.WALKTOBANK;
						break;
					}
				}
				do {
					DynamicClicking.clickRSModel(Zulrah.bankClanWars.getModel(), "Bank");
					SleepJoe.sleepHumanDelay(0.1, 1, 100);
				} while (!(Game.getCrosshairState() == 2));
				MoveMouseZul.hoverMouse(HoverBox.get(6), Zulrah.c20);
				while (Functions.findNearestBankItem(19927) == null) {
					SleepJoe.sleepHumanDelay(1, 1, 1000);
				}
				Zulrah.bankState = BankingState.WITHDRAWITEMS;
				break;
			case WITHDRAWITEMS:

				InventoryZulrah.bank();
				Functions.FTAB(27, 1);
				if (Functions.percentageBool(70)) {
					SleepJoe.sleepHumanDelay(2, 1, 2000);
					if (!InventoryZulrah.checkOk()) {
						Zulrah.bankState = BankingState.OPENBANK2;
						break;
					}
				}
				Zulrah.bankState = BankingState.WALKTOPORT;
				break;
			case WALKTOPORT:
				whencamera = false;
				if (Functions.percentageBool(80)) {
					cam.turnToTile(Zulrah.tilePortal);
					SleepJoe.sleepHumanDelay(0.2, 1, 300);
					whencamera = true;
				}
				RSTile[] goingPathPort = Walking.generateStraightPath(Zulrah.tilePortal);
				Walking.walkPath(goingPathPort);
				if (!InventoryZulrah.checkOk()) {
					Zulrah.bankState = BankingState.OPENBANK2;
					break;
				}
				if (!whencamera) {
					cam.turnToTile(Zulrah.tilePortal);
					SleepJoe.sleepHumanDelay(0.2, 1, 300);
				}
				Zulrah.portal = Functions.findNearestId(20, 26645);
				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(7), Zulrah.c21);
				if (StaticTimer.getElapsed() > 8000) {
					break;
				}

				while (!DynamicClicking.clickRSModel(Zulrah.portal.getModel(), "Enter")) {
					SleepJoe.sleepHumanDelay(0.1, 1, 100);
				}
				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(4), Zulrah.c22);
				if (StaticTimer.getElapsed() > 8000) {
					break;
				}
				Zulrah.bankState = BankingState.TELEZUL;
				break;
			case TELEZUL:
				Inventory.open();
				tele = Functions.findNearestItemId(12938);
				MoveMouseZul.humanMouseMove(new TBox(tele.getArea()), 0.8);
				MoveMouseZul.spamClick(new TBox(0, 0, 10, 10), 3, 2);
				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(23), Zulrah.c23);
				if (StaticTimer.getElapsed() > 5000) {
					break;
				}
				Zulrah.bankState = BankingState.GOTOZUL;
				break;
			case GOTOZUL:
				SleepJoe.sleepHumanDelay(0.2, 1, 300);
				do {
					RSTile[] goingPathBoat = Walking.generateStraightPath(Zulrah.tileBoat);
					Walking.walkPath(goingPathBoat);
				} while (!Player.isMoving());
				if (Functions.percentageBool(60)) {
					cam.turnToTile(Zulrah.tile1boat);
					SleepJoe.sleepHumanDelay(0.2, 1, 300);
				}

				// Drinking pots.
				rang = Functions.findNearestItemId(InventoryZulrah.RANGE_POT);
				mag = Functions.findNearestItemId(InventoryZulrah.MAGE_POT);
				MoveMouseZul.humanMouseMove(new TBox(rang.getArea()), 1.2);
				MoveMouseZul.spamClick(new TBox(0, 0, 10, 10), 2, 1);
				SleepJoe.sleepHumanDelay(4, 1, 4000);
				MoveMouseZul.humanMouseMove(new TBox(mag.getArea()), 1);
				MoveMouseZul.spamClick(new TBox(0, 0, 10, 10), 2, 1);
				SleepJoe.sleepHumanDelay(1, 1, 800);

				Zulrah.boatZulrah = Functions.findNearestId(20, 10068);
				MoveMouseZul.hoverMouse(HoverBox.get(9), Zulrah.c24);
				Zulrah.bankState = BankingState.CLICK_BOAT;
				break;
			case CLICK_BOAT:
				int sp = Mouse.getSpeed();
				Mouse.setSpeed(30);
				while (!DynamicClicking.clickRSModel(Zulrah.boatZulrah.getModel(), "Board")) {
					SleepJoe.sleepHumanDelay(0.1, 1, 100);
				}
				Mouse.setSpeed(sp);
				MoveMouseZul.hoverMouse(new TBox(10, 380, 460, 410), Zulrah.c25);
				MoveMouseZul.humanMouseMove(new TBox(197, 392, 338, 408), 1.4);
				while (NPCChat.getOptions() == null) {
					General.sleep(500);
				}
				MoveMouseZul.fastClick(1, 1);
				MoveMouseZul.hoverMouse(new TBox(300, 60, 460, 250), Zulrah.c26);
				if (NPCChat.getName()!=null){
					break;
				}
				SleepJoe.sleepHumanDelay(4, 500, 3000);
				Zulrah.state = ZulrahState.KILLING;
				break;
			}
		}
		CheckInventoryValue c2 = new CheckInventoryValue(true);
		Thread t2 = new Thread(c2);
		t2.start();
	}

}
