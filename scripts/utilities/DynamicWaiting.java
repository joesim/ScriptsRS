package scripts.utilities;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;

public class DynamicWaiting {

	private static int PERCENT_MOVING = 99;
	private static Double RECTANGLE_MULTIPLIER = 0.1;
	private static int SPEED_HOVER = General.random(0, 10);
	private static int MIN_WAIT = 100;
	private static int MAX_WAIT = 3000;

	public static int getPercentMoving() {
		return PERCENT_MOVING;
	}

	public static void setPercentMoving(int perMove) {
		PERCENT_MOVING = perMove;
	}

	public static Double getRectangleMultiplier() {
		return RECTANGLE_MULTIPLIER;
	}

	public static void setRectangleMultiplier(Double recMult) {
		RECTANGLE_MULTIPLIER = recMult;
	}

	public static int getSpeedHover() {
		return SPEED_HOVER;
	}

	public static void setSpeedHover(int speedHover) {
		SPEED_HOVER = speedHover;
	}

	private static boolean hoverWait(org.tribot.api.types.generic.Condition c, long timeOut, Screen screenRec,
			Positionable pos, Rectangle rectangle, RSNPC npc) {
		Integer mSpeed = Mouse.getSpeed();
		Mouse.setSpeed(SPEED_HOVER);
		long t = Timing.currentTimeMillis();
		while (!c.active() && (Timing.currentTimeMillis() - t) < timeOut) {
			if (General.random(0, 100) > PERCENT_MOVING) {
				Timing.waitCondition(c, General.random(MIN_WAIT, MAX_WAIT));
			} else {
				Point pt = null;
				if (npc!=null){
					RSModel model = npc.getModel();
					pt = choosePointRelative(toRectangle(model.getCentrePoint(), model.getHeight(),model.getHeight()), RECTANGLE_MULTIPLIER);
				} else if (rectangle!=null){
					pt = choosePointRelative(rectangle, RECTANGLE_MULTIPLIER);
				} else if (pos != null) {
					Point centerPoint = pos.getPosition().getHumanHoverPoint();
					pt = choosePointRelative(
							new Rectangle(pos.getPosition().getHumanHoverPoint(), new Dimension(20, 20)),
							RECTANGLE_MULTIPLIER);
				} else {
					pt = choosePointRelative(screenRec.getRectangle(), RECTANGLE_MULTIPLIER);
				}
				Mouse.move(pt);
			}
			AntiBan.timedActions();
		}
		Mouse.setSpeed(mSpeed);
		return false;
	}

	public static boolean hoverWaitRectangle(org.tribot.api.types.generic.Condition c, long timeOut, Rectangle rec) {
		return hoverWait(c, timeOut, null, null, rec,null);
	}
	
	public static boolean hoverWaitScreen(org.tribot.api.types.generic.Condition c, long timeOut, Screen screen) {
		return hoverWait(c, timeOut, screen, null, null,null);
	}
	
	public static boolean hoverWaitNPC(org.tribot.api.types.generic.Condition c, long timeOut, RSNPC npc) {
		return hoverWait(c, timeOut, null, null, null, npc);
	}
	
	public static Rectangle toRectangle(Point middlePoint, Integer width, Integer height){	
		Point upperLeftCorner = new Point(middlePoint.x-width/2, middlePoint.y-height/2);
		return new Rectangle(upperLeftCorner, new Dimension(width,height));
		
	}

	private static Point choosePointRelative(Rectangle rec, Double relative) {

		Point goTo;
		if (rec.contains(Mouse.getPos())) {
			Integer xMargin = (int) (rec.width * relative);
			Integer yMargin = (int) (rec.height * relative);
			Point current = Mouse.getPos();

			Integer xMin = (int) (current.x - xMargin < rec.getMinX() ? rec.getMinX() : current.x - xMargin);
			Integer xMax = (int) (current.x + xMargin > rec.getMaxX() ? rec.getMaxX() : current.x + xMargin);
			Integer yMin = (int) (current.y - yMargin < rec.getMinY() ? rec.getMinY() : current.y - yMargin);
			Integer yMax = (int) (current.y + yMargin > rec.getMaxY() ? rec.getMaxY() : current.y + yMargin);

			goTo = new Point(General.random(xMin, xMax), General.random(yMin, yMax));
			General.println(goTo);
		} else {
			goTo = new Point((int) General.randomDouble(rec.getMinX(), rec.getMaxX()),
					(int) General.randomDouble(rec.getMinY(), rec.getMaxY()));
		}
		return goTo;
	}

}
