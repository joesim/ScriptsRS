package scripts.zulrahUtilities;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.Zulrah;
import scripts.utilities.ACamera;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.SleepJoe;
import scripts.utilities.StaticTimer;
import scripts.utilities.TBox;

public class DeadZul {


	private ACamera cam = null;
	
	private RSItem tele = null;
	
	public DeadZul(Script s){
		cam = new ACamera(s);
	}
	
	public void returnToZul(){
		Zulrah.deadState = DeadState.WALKTOSTAIRS;
		MoveMouseZul.hoverMouse(HoverBox.get(1), new ConditionTime(SleepJoe.sleepHumanDelayOut(20, 1, 20000)),
				Zulrah.SPEED_HOVER, false);
		while (Zulrah.state == ZulrahState.DEAD) {
			switch (Zulrah.deadState) {
			case WALKTOSTAIRS:
				// Hover jusqua lumbridge
				General.println("On est mort");
				MoveMouseZul.hoverMouse(HoverBox.get(1), Zulrah.c32);
				SleepJoe.sleepHumanDelay(0.5, 1, 300);

				// Going to stairs
				General.println("Going to stairs");
				boolean whencamera = false;
				if (Functions.percentageBool(80)) {
					cam.setCameraAngle(100);
					cam.setCameraRotation(Functions.generateRandomInt(80, 100));
					whencamera = true;
				}
				do {
					RSTile[] goingPathStairs = Walking.generateStraightPath(Zulrah.tileStairs);
					Walking.walkPath(goingPathStairs);
				} while (!Player.isMoving());

				if (!whencamera) {
					cam.setCameraAngle(100);
					cam.setCameraRotation(Functions.generateRandomInt(80, 100));
				}

				// Pray off si on
				General.println("Mettre les prayers a off");
				PrayerZul.prayOff();

				// Hover jusqua stairs vu
				Zulrah.stairsLumb = Functions.findNearestId(100, 16671);
				Zulrah.deadState = DeadState.CLICKSTAIRS1;
				break;
			case CLICKSTAIRS1:
				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(15), Zulrah.c27);
				if (StaticTimer.getElapsed() > 5000) {
					do {
						DynamicClicking.clickRSTile(Zulrah.stairsLumb.getPosition(), "Climb-up");
					} while (!(Game.getCrosshairState() == 2));
				} else {
					do {
						DynamicClicking.clickRSModel(Zulrah.stairsLumb.getModel(), "Climb-up");
						SleepJoe.sleepHumanDelay(0.1, 1, 100);
					} while (!(Game.getCrosshairState() == 2));
				}
				// Hover jusqua plane ==1
				MoveMouseZul.hoverMouse(HoverBox.get(14), Zulrah.c28);
				Zulrah.deadState = DeadState.CLICKSTAIRS2;
				break;
			case CLICKSTAIRS2:
				// Find le stairs 2 et click
				General.println("Find stairs 2 et click");
				RSObject sta = Functions.findNearestId(10, 16672);
				while (sta == null || !DynamicClicking.clickRSModel(sta.getModel(), "Climb-up")) {
					General.sleep(100, 200);
					if (sta == null) {
						sta = Functions.findNearestId(10, 16672);
					}
				}
				General.println("Calisse");
				// Hover jusqua plane==2
				MoveMouseZul.hoverMouse(HoverBox.get(22), Zulrah.c29);
				Zulrah.deadState = DeadState.WALKTOBANK;
				break;
			case WALKTOBANK:
				RSTile[] goingPathLumbBank = Walking.generateStraightPath(Zulrah.tileLumbBank);
				Walking.walkPath(goingPathLumbBank);

				// Hover jusqua bank vu
				Zulrah.bankLumby = Functions.findNearestId(20, 18491);
				MoveMouseZul.hoverMouse(HoverBox.get(9), Zulrah.c30);
				Zulrah.deadState = DeadState.CLICKBANK;
				break;
			case CLICKBANK:
				General.println("Clique bank");
				do {
					DynamicClicking.clickRSModel(Zulrah.bankLumby.getModel(), "Bank Bank booth");
					SleepJoe.sleepHumanDelay(0.1, 1, 100);
				} while (!(Game.getCrosshairState() == 2));

				// Hover bank screen open
				General.println("Hover bank screen open");
				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(8), Zulrah.c20);
				if (StaticTimer.getElapsed() > 5000) {
					break;
				}
				Zulrah.deadState = DeadState.WITHDRAWTELE;
				break;
			case WITHDRAWTELE:
				// Trouver un tele scroll
				General.println("Find tele in bank");
				tele = Functions.findNearestBankItem(12938);
				MoveMouseZul.humanMouseMove(new TBox(tele.getArea()), 0.7);
				MoveMouseZul.fastClickBank(1, 1);

				// Close bank
				Functions.FTAB(27, 1);

				// Hover bank screen !open
				MoveMouseZul.hoverMouse(HoverBox.get(4),Zulrah.c31);
				if (Functions.findNearestItemId(12938) == null) {
					Zulrah.deadState = DeadState.CLICKBANK;
					break;
				}
				Zulrah.deadState = DeadState.TELEPORTZUL;
				break;
			case TELEPORTZUL:
				Inventory.open();
				SleepJoe.sleepHumanDelay(0.3, 1, 300);

				// Clicker sur le scroll
				General.println("Clique sur scroll");
				tele = Functions.findNearestItemId(12938);
				MoveMouseZul.humanMouseMove(new TBox(tele.getArea()), 0.8);
				MoveMouseZul.spamClick(new TBox(0, 0, 10, 10), 2, 1);

				// Hover jusqua Zul-Andra
				General.println("Hover jusqua Zul-Andra");
				StaticTimer.reset();
				MoveMouseZul.hoverMouse(HoverBox.get(2), Zulrah.c23);
				if (StaticTimer.getElapsed() > 5000) {
					break;
				}
				SleepJoe.sleepHumanDelay(0.2, 1, 300);

				// Walk to boat
				General.println("Walk to boat");
				RSTile[] goingPathBoat = Walking.generateStraightPath(Zulrah.tileBoat);
				Walking.walkPath(goingPathBoat);

				// Hover jusqua see the boat
				Zulrah.boatZulrah = Functions.findNearestId(20, 10068);
				MoveMouseZul.hoverMouse(HoverBox.get(9), Zulrah.c24);
				Zulrah.state = ZulrahState.COLLECTITEMS;
				break;

			}
		}
	}
	
	public void collectItems(){
		General.println("Collect items");
		RSNPC madame = Functions.findNearestNPC(2124);
		do {
			DynamicClicking.clickRSNPC(madame, "Collect");
			SleepJoe.sleepHumanDelay(6, 200, 6000);
		} while (NPCChat.getMessage() == null);
		if (NPCChat.getMessage().toLowerCase().contains("afraid")) {
			RSItem[] equipement = Inventory.find(4874, 4875, 4876, 4877, 4868, 4869, 4870, 4871, 6920, 12899,
					3842, 2413, 11972, 4856, 4857, 4858, 4859, 6585);
			for (RSItem it : equipement) {
				MoveMouseZul.humanMouseMove(new TBox(it.getArea()), Zulrah.SPEED_COLLECT_ITEMS);
				MoveMouseZul.fastClick(1, 1);
				SleepJoe.sleepHumanDelay(0.2, 1, 200);
			}
			Zulrah.state = ZulrahState.BANKING;
		} else {
			RSItem[] equipement = Inventory.find(4874, 4875, 4876, 4877, 4868, 4869, 4870, 4871, 6920, 12899,
					3842, 2413, 11972, 4856, 4857, 4858, 4859, 6585, 2550);
			General.println(equipement.length);
			if (equipement.length <= 0) {
				RSItem sh1 = Functions.findNearestItemId(385);
				RSItem sh2 = Functions.findNearestItemId(385);
				MoveMouseZul.humanMouseMove(new TBox(sh1.getArea()), Zulrah.SPEED_COLLECT_ITEMS);
				MoveMouseZul.fastClick(1, 1);
				SleepJoe.sleepHumanDelay(0.2, 1, 200);
				MoveMouseZul.humanMouseMove(new TBox(sh2.getArea()), Zulrah.SPEED_COLLECT_ITEMS);
				MoveMouseZul.fastClick(1, 1);
				SleepJoe.sleepHumanDelay(0.2, 1, 200);
			} else {
				for (RSItem it : equipement) {
					MoveMouseZul.humanMouseMove(new TBox(it.getArea()), Zulrah.SPEED_COLLECT_ITEMS);
					MoveMouseZul.fastClick(1, 1);
					SleepJoe.sleepHumanDelay(0.2, 1, 200);
				}
			}
		}
	}
	
}
