package scripts;

import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.utilities.ACamera;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.webwalker_logic.WebWalker;
import scripts.webwalker_logic.WebWalker.Offset;
import scripts.zulrahUtilities.MoveMouseZul;

public class TestZul extends Script implements Painting {

	MoveMouseZul move = new MoveMouseZul();
	SleepJoe sleepJoe = new SleepJoe();
	private ACamera cam = new ACamera(this);

	@Override
	public void run() {
		MouseMoveJoe.loadDataNormal();
		MoveMouseZul.loadDataNormal();
		HoverBox.load();
		// Mouse.setSpeed(20);
//		RSNPC zulrah = Functions.findNearestNPC(277);
//		 move.hoverMouse(new TBox(100,100,125,125), new ConditionZul(new
//		 RSTile(0,0,0),1));
//		// RSItem[] lol = Inventory.find(3024, 3026, 3028, 3030);
//		// println("Number threads: " + Thread.getAllStackTraces().size());
//		for (Thread thread : Thread.getAllStackTraces().keySet()) {
//			if (thread.getName().contains("TimerQueue")) {
//				thread.suspend();
//			}
//			println(thread.getName());
//			sleep(100);
//		}
//		while (true){
//			 move.hoverMouse(new TBox(100,100,120,120), new ConditionZul(new
//					 RSTile(0,0,0),101));
//		}

//		CustomDebug cd = new CustomDebug();
//		Thread t = new Thread(cd);
////		t.start();
//		Timer t = new Timer(10000);
//		while (true){
//			t = new Timer(100000);
//			sleepJoe.sleepHumanDelay(1, 1, 2000);
//			println(t.getElapsed());
//		}
//		
//		InventoryZulrah.depositNotWantedItems();
//		
//		for (ItemAmount i : InventoryZulrah.bankInventory){
//			i.withdrawItem();
//		}

//		CheckInventoryValue c = new CheckInventoryValue(true);
//		Thread t = new Thread(c);
//		t.start();
//		sleep(10000);
//		println(Zulrah.cashInventory);
////		InventoryZulrah.depositNotWantedItems();
////		InventoryZulrah.withdrawItems();
//
//		//InventoryZulrah.bank();

		RSObject o = Functions.findNearestId(15, 14889);
		MouseMoveJoe.playMouseFollowObject(o, "Spin", 1, null,true);
		
		
		
	}

	public void walkingTo(RSTile tile){
		int off = General.random(1, 5);
		WebWalker.Offset offset = null;
		switch (off){
		case 1:
			offset = WebWalker.Offset.LOW;
			break;
		case 2:
			offset = WebWalker.Offset.MEDIUM;
			break;
		case 3:
			offset = Offset.HIGH;
			break;
		case 4:
			offset = Offset.VERY_HIGH;
			break;
		case 5:
			offset = Offset.NONE;
			break;
		}
		WebWalker.setPathOffset(offset);
		WebWalker.walkTo(tile);
	}
	
	Font font = new Font("Verdana", Font.BOLD, 14);
	@Override
	public void onPaint(Graphics g) {
		 g.setFont(font);
		 g.drawString("Some text here?", 300, 300);
		
	}
}