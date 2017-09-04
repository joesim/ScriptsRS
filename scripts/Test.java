package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Login;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.others.MouseJoe;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.WorldHop;

@ScriptManifest(authors = { "Joe" }, category = "Test", name = "Test")
public class Test extends Script implements Starting, Painting, MessageListening07 {

	private ABCUtil abc_util = new ABCUtil();

	@Override
	public void onStart() {
		MouseMoveJoe.loadDataNormal();
		MouseJoe.loadDataNormal();
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			General.println(thread.getName());
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		Mouse.setSpeed(105);
		HoverBox.load();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// RSObject spinner = Functions.findNearestId(15, 14889);
		// MouseMoveJoe.playMouseFollowObject(spinner, "Spin", 1, null, true, 0,
		// sleep(3000);
		//

		// 0, 0, 0);
		// GrandExchange.
		// GrandExchange.offer("Leather", 1, 1, false);
		//GEUtils.placeAnOffer("Yew logs", 100, 3, false);
//		DynamicWaiting.hoverWaitScreen(new Condition() {
//			
//			@Override
//			public boolean active() {
//				General.sleep(1);
//				return false;
//			}
//		}, 10000000, Screen.ALL_SCREEN);
		this.setLoginBotState(false);
		Login.login("dalethscheva@inboxbear.com", "tablet2");
		General.sleep(60000);
		this.setLoginBotState(true);
		while (true){
			General.sleep(1200);
		}
//		while (true){
//		General.println(GrandExchange.getWindowState());
//		General.sleep(100);
//		}
		// General.println("Start");
		// println(DynamicWaiting.hoverWaitScreen(Conditions.bankScreenOpen(),
		// General.random(2000000, 4000000), Screen.ALL_SCREEN));
		// Mouse.move(new Point(700,500));
		// General.println("Hey");
	}

	public static void waitHover(Condition c, long timeOut, Rectangle rec) {
		Integer mSpeed = Mouse.getSpeed();
		Mouse.setSpeed(20);
		long t = Timing.currentTimeMillis();
		while (!c.active() && (Timing.currentTimeMillis() - t) < timeOut) {

		}
		Mouse.setSpeed(mSpeed);
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private final long startTime = System.currentTimeMillis();

	@Override
	public void onPaint(Graphics g) {
		Graphics2D gr = (Graphics2D) g;
		long timeRan = (System.currentTimeMillis() - startTime) / 1000;
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		RSNPC n = NPCs.find(2148)[0];
		gr.draw(n.getModel().getEnclosedArea().getBounds());
		
		//g.drawString("Kills: " + Player.getRSPlayer().getInteractingIndex(), 20, 90);

		// if (Zulrah.zulrah != null) {
		// g.drawString("Zulrah animation: " + Zulrah.zulrah.getAnimation(),
		// 300, 370);
		// }
		// g.drawString("CheckDeath player : " + CheckDeath.playerDead + " zul:
		// " + CheckDeath.zulrahDead, 200, 390);
		// g.drawString("Player interacting index: " +
		// Player.getRSPlayer().getInteractingIndex(), 200, 410);
		// g.drawString("CheckRed shouldMove " + CheckRed.shouldMove, 200, 430);
		// g.drawString("CheckJad " + CheckJad.shouldSwitch, 200, 450);

	}

	@Override
	public void clanMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void duelRequestReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void personalMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverMessageReceived(String arg0) {
		General.println(arg0);

	}

	@Override
	public void tradeRequestReceived(String arg0) {
		// TODO Auto-generated method stub

	}
}
