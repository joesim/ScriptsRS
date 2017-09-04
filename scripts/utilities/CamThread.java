package scripts.utilities;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

public class CamThread implements Runnable {

	public final static int PERCENTAGE = 80;
	public final static int SLEEP_MODIFIER = 80;
	
	private final int ROTATION_THRESHOLD = 20;
	private final int ANGLE_THRESHOLD = 15;

	public RSTile tile = null;
	
	public void turnToTile(RSTile t){
		tile = t;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void setCamera(int rotation, int angle) {
		if (Functions.percentageBool(PERCENTAGE)) {
			if (Math.abs(angle - Camera.getCameraAngle()) > ANGLE_THRESHOLD) {
				setCameraAngle(angle);
			}
			SleepJoe.sleepHumanDelay(SLEEP_MODIFIER, 1, 400);
			if (Math.abs(rotation - Camera.getCameraRotation()) > ROTATION_THRESHOLD) {
				setCameraRotation(rotation);
	 		}
		} else {
			if (Math.abs(rotation - Camera.getCameraRotation()) > ROTATION_THRESHOLD) {
				setCameraRotation(rotation);
			}
			SleepJoe.sleepHumanDelay(SLEEP_MODIFIER, 1, 400);
			if (Math.abs(angle - Camera.getCameraAngle()) > ANGLE_THRESHOLD) {
				setCameraAngle(angle);
			}
		}
	}

	public void setCameraAngle(int angle) {
		Angle r = new Angle(angle + General.random(-12, 12));
		Thread th = new Thread(r);
		th.start();
	}

	public void setCameraRotation(int rotation) {
		Rotation r = new Rotation(rotation + General.random(-25, 25));
		Thread th = new Thread(r);
		th.start();
	}
	
	@Override
	public void run() {
		int optimalAngle = adjustAngleToTile(tile);
		int optimalRotation = Camera.getTileAngle(tile);

		if (Functions.percentageBool(PERCENTAGE)) {
			if (Math.abs(optimalAngle - Camera.getCameraAngle()) > ANGLE_THRESHOLD) {
				Angle r = new Angle(optimalAngle + General.random(-12, 12));
				Thread th = new Thread(r);
				th.start();
			}
			SleepJoe.sleepHumanDelay(SLEEP_MODIFIER, 1, 400);
			if (Math.abs(optimalRotation - Camera.getCameraRotation()) > ROTATION_THRESHOLD) {
				Rotation r = new Rotation(optimalRotation + General.random(-25, 25));
				Thread th = new Thread(r);
				th.start();
	 		}
		} else {
			if (Math.abs(optimalRotation - Camera.getCameraRotation()) > ROTATION_THRESHOLD) {
				Rotation r = new Rotation(optimalRotation + General.random(-25, 25));
				Thread th = new Thread(r);
				th.start();
			}
			SleepJoe.sleepHumanDelay(SLEEP_MODIFIER, 1, 400);
			if (Math.abs(optimalAngle - Camera.getCameraAngle()) > ANGLE_THRESHOLD) {
				Angle r = new Angle(optimalAngle + General.random(-12, 12));
				Thread th = new Thread(r);
				th.start();
			}
		}

	}

	public int adjustAngleToTile(Positionable tile) {
		int distance = Player.getPosition().distanceTo(tile);
		int angle = 100 - (distance * 6);

		return angle;
	}

	class Rotation implements Runnable {

		private int rot;

		public Rotation(int ang) {
			this.rot = ang;
		}

		@Override
		public void run() {
			Camera.setCameraRotation(rot);
		}

	}

	class Angle implements Runnable {

		private int angle;

		public Angle(int ang) {
			this.angle = ang;
		}

		@Override
		public void run() {
			Camera.setCameraAngle(angle);
		}

	}
}
