package scripts.utilities;

import org.tribot.api2007.Game;

import scripts.others.MoveMouseFarm;

public class FastClickThread implements Runnable {

	MoveMouseFarm ms = new MoveMouseFarm();

	@Override
	public void run() {

		while (true) {
			if (Game.isUptext("Pickpocket")) {
				ms.sleepJoe.sleepHumanDelay(0.8, 1, 1000);
				ms.fastClick(1, 0.7);
			}
		}
	}

}
