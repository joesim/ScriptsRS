package scripts.others;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSModel;

import scripts.tabber.utilities.MouseTab;
import scripts.tabber.utilities.Utils;
import scripts.tabber.utilities.Variables;
import scripts.utilities.AntiBan;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

/**
 * Dynamic waiting
 * 
 * @author volcom3d
 *
 */
public class DynamicWaiting {

	/** Percentage chance the mouse move each iteration **/
	protected static int PERCENT_MOVING = 35;

	/**
	 * The max distance the mouse will move relative to the rectangle (must be
	 * between 0 and 1)
	 **/
	protected static Double RECTANGLE_MULTIPLIER = 0.4;

	/** Speed mouse during iteration **/
	protected static int SPEED_HOVER = General.random(0, 5);

	/** Percentage move ALL_SCREEN no matter what **/
	protected static int PERCENT_MOVE_ALL_SCREEN = General.random(0, 20);

	/** Minimum wait **/
	protected static int MIN_WAIT = 50;

	/** Maximum wait **/
	protected static int MAX_WAIT = 2000;

	/**
	 * Hovers the mouse in the specified rectangle while the condition is false
	 */
	public static boolean hoverWaitRectangle(Condition c, long timeOut, Rectangle rec, int speed) {
		return hoverWait(c, timeOut, null, rec, null, speed);
	}
	
	/**
	 * Hovers the mouse in the specified rectangle while the condition is false
	 */
	public static boolean hoverWaitRectangle(Condition c, long timeOut, Rectangle rec) {
		return hoverWait(c, timeOut, null, rec, null, SPEED_HOVER);
	}

	/**
	 * Hovers the mouse in the specified screen rectangle while the condition is
	 * false
	 */
	public static boolean hoverWaitScreen(Condition c, long timeOut, Screen screen, int speed) {
		return hoverWait(c, timeOut, screen, null, null, speed);
	}
	
	/**
	 * Hovers the mouse in the specified screen rectangle while the condition is
	 * false
	 */
	public static boolean hoverWaitScreen(Condition c, long timeOut, Screen screen) {
		return hoverWait(c, timeOut, screen, null, null, SPEED_HOVER);
	}

	/**
	 * Hovers the mouse while the condition is false
	 */
	private static boolean hoverWait(Condition c, long timeOut, Screen screenRec, Rectangle rectangle, RSModel model, int speed) {
		boolean moveAllScreen = Functions.percentageBool(PERCENT_MOVE_ALL_SCREEN);
		Integer mSpeed = Mouse.getSpeed();
		Mouse.setSpeed(speed);
		long t = Timing.currentTimeMillis();
		while (!c.active() && (Timing.currentTimeMillis() - t) < timeOut) {
			if (General.random(0, 100) > PERCENT_MOVING) {
				Timing.waitCondition(c, General.random(MIN_WAIT, MAX_WAIT));
			} else {
				Point pt;
				if (moveAllScreen) {
					pt = choosePointRelative(Screen.ALL_SCREEN.getRectangle(), RECTANGLE_MULTIPLIER);
				} else {
					
					if (rectangle != null) {
						pt = choosePointRelative(rectangle, RECTANGLE_MULTIPLIER);
					} else if (model != null) {
						pt = choosePointRelative(model.getEnclosedArea().getBounds(), RECTANGLE_MULTIPLIER);
						Mouse.move(pt);
					} else {
						pt = choosePointRelative(screenRec.getRectangle(), RECTANGLE_MULTIPLIER);
					}
				}
				MouseJoe.humanMouseMove(new TBox(pt, 0), speed,c);
			}
			AntiBan.timedActions();
		}
		Mouse.setSpeed(mSpeed);
		return c.active();
	}

	/**
	 * Hovers the mouse while the condition is false
	 */
	public static boolean hoverWaitModel(Condition c, long timeOut, RSModel model) {
		return hoverWait(c, timeOut, null, null, model, SPEED_HOVER);
	}

	/**
	 * Hovers the mouse while the condition is false
	 */
	public static boolean hoverWaitTabs(Condition c, long timeOut, Screen screenRec, int maxOut, RSModel model) {

		boolean mouseOut = false;
		if (Functions.percentageBool(Variables.CHANCE_MOUSE_OUT)) {
			mouseOut = true;
		}

		if (Functions.percentageBool100000(Variables.MOUSE_OUT_GOOD_TIME)) {
			General.println("Out of game for a good amount of time");
			Mouse.leaveGame();
			General.sleep(60000, 250000);
		}

		Integer mSpeed = Mouse.getSpeed();
		Mouse.setSpeed(SPEED_HOVER);
		long t = Timing.currentTimeMillis();
		while (!c.active() && (Timing.currentTimeMillis() - t) < timeOut) {

			if (Functions.percentageBool100000(Variables.PERCENT_RIGHT_CLICK) && model != null) {
				MouseTab.clickButler(model, 3);
				if (Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return ChooseOption.isOpen();
					}
				}, General.random(1000, 2000))) {
					if (Functions.percentageBool(Variables.STAY_IN_CHOOSEOPTION)) {
						if (Functions.percentageBool(1)) {
							int perc = PERCENT_MOVING;
							PERCENT_MOVING = 1;
							hoverWaitRectangle(c, timeOut, ChooseOption.getArea());
							PERCENT_MOVING = perc;
						} else {
							hoverWaitRectangle(c, timeOut, ChooseOption.getArea());
						}
					} else {
						hoverWaitRectangle(c, General.random(500, 5000), ChooseOption.getArea());
					}
				}
			}

			if (Functions.percentageBool(10) && !Utils.isButlerOut()
					&& (NPCs.find(227).length == 0 || !NPCs.find(227)[0].isOnScreen())) {
				GameTab.open(GameTab.TABS.OPTIONS);
			}
			if (Functions.percentageBool(10)) {
				Inventory.open();
			}
			if (mouseOut && Functions.percentageBool(Variables.CHANCE_MOUSE_OUT_IT)) {
				Mouse.leaveGame();
				if (Functions.percentageBool(Variables.CHANCE_LOOKING_CLIENT)) {
					Timing.waitCondition(c, General.random(10000, 60000));
				} else {
					General.sleep(1000, maxOut);
					SleepJoe.sleepHumanDelay(10, 1, 20000);
				}
			} else if (General.random(0, 100) > PERCENT_MOVING) {
				Timing.waitCondition(c, General.random(MIN_WAIT, MAX_WAIT));
			} else {
				Point pt;
				pt = choosePointRelative(screenRec.getRectangle(), RECTANGLE_MULTIPLIER);
				Mouse.move(pt);
			}
			AntiBan.timedActions();
		}
		Mouse.setSpeed(mSpeed);
		return c.active();
	}

	/**
	 * Creates a rectangle with the specified parameters
	 */
	public static Rectangle toRectangle(Point middlePoint, Integer width, Integer height) {
		Point upperLeftCorner = new Point(middlePoint.x - width / 2, middlePoint.y - height / 2);
		return new Rectangle(upperLeftCorner, new Dimension(width, height));

	}

	/**
	 * Chooses a point in the relative rectangle
	 */
	protected static Point choosePointRelative(Rectangle rec, Double relative) {

		Point goTo;
		if (rec.contains(Mouse.getPos())) {
			Integer xMargin = (int) (Double.valueOf(rec.width) * relative);
			Integer yMargin = (int) (Double.valueOf(rec.height) * relative);
			Point current = Mouse.getPos();
			Integer xMin = (int) ((current.x - xMargin) < rec.getMinX() ? rec.getMinX() : current.x - xMargin);
			Integer xMax = (int) ((current.x + xMargin) > rec.getMaxX() ? rec.getMaxX() : current.x + xMargin);
			Integer yMin = (int) ((current.y - yMargin) < rec.getMinY() ? rec.getMinY() : current.y - yMargin);
			Integer yMax = (int) ((current.y + yMargin) > rec.getMaxY() ? rec.getMaxY() : current.y + yMargin);

			goTo = new Point(General.random(xMin, xMax), General.random(yMin, yMax));

		} else {
			goTo = new Point((int) General.randomDouble(rec.getMinX(), rec.getMaxX()),
					(int) General.randomDouble(rec.getMinY(), rec.getMaxY()));
		}
		return goTo;
	}

	/**
	 * Set min wait when the mouse stands still
	 * 
	 * @param minWait
	 */
	public static void setMinWait(int minWait) {
		MIN_WAIT = minWait;
	}

	/**
	 * Set max wait when the mouse stands still
	 * 
	 * @param maxWait
	 */
	public static void setMaxWait(int maxWait) {
		MAX_WAIT = maxWait;
	}

	/**
	 * Set the pchance the mouse will move for each iteration.
	 * 
	 * @param perMove
	 */
	public static void setPercentMoving(int perMove) {
		PERCENT_MOVING = perMove;
	}

	/**
	 * Set the rectangle multiplier between 0 and 1.
	 * 
	 * @param recMult
	 */
	public static void setRectangleMultiplier(Double recMult) {
		RECTANGLE_MULTIPLIER = recMult;
	}

	/**
	 * Set the speed of the mouse while hovering
	 * 
	 * @param speedHover
	 */
	public static void setSpeedHover(int speedHover) {
		SPEED_HOVER = speedHover;
	}

	public static boolean hoverWaitFletching(Condition c, int timeOut, Screen screen, int maxOut, RSModel model) {

		boolean mouseOut = false;
		if (Functions.percentageBool(Variables.CHANCE_MOUSE_OUT)) {
			mouseOut = true;
		}

		if (Functions.percentageBool100000(Variables.MOUSE_OUT_GOOD_TIME)) {
			General.println("Out of game for a good amount of time");
			Mouse.leaveGame();
			General.sleep(60000, 250000);
		}

		Integer mSpeed = Mouse.getSpeed();
		Mouse.setSpeed(SPEED_HOVER);
		long t = Timing.currentTimeMillis();
		while (!c.active() && (Timing.currentTimeMillis() - t) < timeOut) {

			if (Functions.percentageBool100000(Variables.PERCENT_RIGHT_CLICK) && model != null) {
				MouseTab.clickButler(model, 3);
				if (Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return ChooseOption.isOpen();
					}
				}, General.random(1000, 2000))) {
					if (Functions.percentageBool(Variables.STAY_IN_CHOOSEOPTION)) {
						if (Functions.percentageBool(1)) {
							int perc = PERCENT_MOVING;
							PERCENT_MOVING = 1;
							hoverWaitRectangle(c, timeOut, ChooseOption.getArea());
							PERCENT_MOVING = perc;
						} else {
							hoverWaitRectangle(c, timeOut, ChooseOption.getArea());
						}
					} else {
						hoverWaitRectangle(c, General.random(500, 5000), ChooseOption.getArea());
					}
				}
			}

			if (Functions.percentageBool(10) && !Utils.isButlerOut()
					&& (NPCs.find(227).length == 0 || !NPCs.find(227)[0].isOnScreen())) {
				GameTab.open(GameTab.TABS.OPTIONS);
			}
			if (Functions.percentageBool(10)) {
				Inventory.open();
			}
			if (mouseOut && Functions.percentageBool(Variables.CHANCE_MOUSE_OUT_IT)) {
				Mouse.leaveGame();
				if (Functions.percentageBool(Variables.CHANCE_LOOKING_CLIENT)) {
					Timing.waitCondition(c, General.random(10000, 60000));
				} else {
					General.sleep(1000, maxOut);
					SleepJoe.sleepHumanDelay(10, 1, 20000);
				}
			} else if (General.random(0, 100) > PERCENT_MOVING) {
				Timing.waitCondition(c, General.random(MIN_WAIT, MAX_WAIT));
			} else {
				Point pt;
				pt = choosePointRelative(screen.getRectangle(), RECTANGLE_MULTIPLIER);
				Mouse.move(pt);
			}
			AntiBan.timedActions();
		}
		Mouse.setSpeed(mSpeed);
		return c.active();
		
	}

}
