package scripts.zulrahUtilities;

import org.tribot.api.General;

import scripts.Zulrah;

public class CheckJad implements Runnable {

	// Condition arret
	public volatile boolean finished = false;

	private int numberJadAttacks = 0;
	private int last = 0;
	public static volatile boolean shouldSwitch = false;
	public volatile boolean prayMage = false;
	public int numberAttacks = 0;

	/**
	 * Stop le thread
	 */
	public void stopMe() {
		finished = true;
	}

	public void switched() {
		shouldSwitch = false;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param ID
	 *            Zulrah attendu.
	 */
	public CheckJad(int numberJadAttacks, boolean prayMage) {
		this.numberJadAttacks = numberJadAttacks;
		this.prayMage = prayMage;
	}

	@Override
	public void run() {

		while (!finished) {
			if (last != 5069 && Zulrah.zulrah.getAnimation() == 5069) {
				last = 5069;
				if (numberAttacks <= numberJadAttacks) {
					prayMage = !prayMage;
					shouldSwitch = true;
				}
				numberAttacks++;
			} else if (Zulrah.zulrah.getAnimation() == -1) {
				last = -1;
			}
			General.sleep(20);
		}

	}

}
