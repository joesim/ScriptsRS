package scripts.utilities;

import org.tribot.api.General;

public class SleepJoe {

	/** Delays in ms **/
	public final static int[] delays = { 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320, 340, 360,
			380, 400, 420, 440, 460, 480, 500, 520, 540, 560, 580, 600, 620, 640, 660, 680, 700, 720, 740, 760, 780,
			800, 820, 840, 860, 880, 900, 920, 940, 960, 980, 1000, 1020, 1040, 1060, 1080, 1100, 1120, 1140, 1160,
			1180, 1200, 1220, 1240, 1260, 1280, 1300, 1320, 1340, 1360, 1380, 1400, 1420, 1440, 1460, 1480, 1500, 1520,
			1540, 1560, 1580, 1600, 1620, 1640, 1660, 1680, 1700, 1720, 1740, 1760, 1780, 1800, 1820, 1840, 1860, 1880,
			1900, 1920, 1940, 1960, 1980, 2000, 2020, 2040 };
	
	/** Corresponding chances **/
	public final static int[] data = { 0, 0, 0, 2, 11, 20, 23, 25, 30, 46, 105, 256, 532, 1074, 1811, 3156, 4854, 6661, 7700, 8298,
			8533, 8655, 8728, 8786, 8828, 8866, 8898, 8932, 8952, 8985, 9010, 9030, 9049, 9060, 9071, 9083, 9086, 9096,
			9102, 9108, 9113, 9117, 9123, 9124, 9126, 9128, 9130, 9133, 9135, 9136, 9139, 9140, 9144, 9147, 9147, 9150,
			9151, 9154, 9155, 9157, 9158, 9160, 9161, 9163, 9164, 9165, 9167, 9170, 9171, 9172, 9173, 9175, 9176, 9177,
			9178, 9180, 9181, 9184, 9187, 9189, 9191, 9192, 9193, 9195, 9196, 9197, 9198, 9199, 9200, 9201, 9202, 9204,
			9205, 9206, 9207, 9208, 9209, 9210, 9211, 9212, 9215, 9225 };


	/**
	 * Delai humain de moyenne de 250-400ms
	 * @param multiplier
	 * @param min
	 * @param max
	 */
	public static void sleepHumanDelay(double multiplier, int min, int max) {
		int randomIndex = General.random(1, data[data.length-1]);
		for (int i = 1; i < data.length; i++) {
			if (randomIndex > data[i - 1] && randomIndex <= data[i]) {
				int randomRange = General.random(-10, 10);
				int sleepTime = (int) (randomRange + delays[i] * multiplier);
				if (sleepTime < min || sleepTime > max) {
					General.sleep(Functions.generateRandomInt(min, max));
					break;
				} else {
					General.sleep(sleepTime);
					break;
				}
			}
		}
	}
	
	/**
	 * Fonction qui retourne le delai a place de l'effectuer dans la fonction
	 * @param multiplier
	 * @param min
	 * @param max
	 * @return
	 */
	public static int sleepHumanDelayOut(double multiplier, int min, int max) {
		int randomIndex = General.random(1, data[data.length-1]);
		for (int i = 1; i < data.length; i++) {
			if (randomIndex > data[i - 1] && randomIndex <= data[i]) {
				int randomRange = (int) Math.round(General.random(-10, 10)*multiplier);
				int sleepTime = (int) (randomRange + delays[i] * multiplier);
				if (sleepTime < min || sleepTime > max) {
					return Functions.generateRandomInt(min, max);
				} else {
					return sleepTime;
				}
			}
		}
		return General.random(min, max);
	}
	
	/**
	 * Retourne true si le sleep ne rencontre pas la condition d'arret a chaque division de 10 du temps donner (multiplier)
	 * @param multiplier
	 * @param min
	 * @param max
	 * @param c
	 * @return
	 */
	public static boolean sleepHumanDelayCondition(double multiplier, int min, int max, Condition c) {

		int sleepTime = sleepHumanDelayOut(multiplier, min, max);
		for (int i = 0; i < 10; i++) {
			if (!c.checkCondition()) {
				General.sleep(sleepTime / 10);
			} else {
				return false;
			}
		}
		return true;
	}
	

	public static void sleepHumanDelayConditionTime(int mul, int j, int k, ConditionTime c) {
		int sleepTime = sleepHumanDelayOut(mul,j,k);
		for (int i = 0; i < 10; i++) {
			if (!c.checkCondition()) {
				General.sleep(sleepTime / 10);
			} else {
				break;
			}
		}
	}
	
}

