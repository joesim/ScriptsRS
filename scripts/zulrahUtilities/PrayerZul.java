package scripts.zulrahUtilities;

import org.tribot.api2007.GameTab;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.Prayer.PRAYERS;
import org.tribot.api2007.Skills;

import scripts.utilities.Functions;
import scripts.utilities.TBox;

public final class PrayerZul {

	private static PRAYERS rang = Prayer.PRAYERS.PROTECT_FROM_MISSILES;
	private static PRAYERS tri = Prayer.PRAYERS.MYSTIC_MIGHT;
	private static PRAYERS mage = Prayer.PRAYERS.PROTECT_FROM_MAGIC;
	private static PRAYERS eagle = Prayer.PRAYERS.EAGLE_EYE;

	private static final TBox PROTECT_FROM_MISSILES = new TBox(628, 330, 654, 349);
	private static final TBox MYSTIC_MIGHT = new TBox(555, 369, 583, 389);
	private static final TBox PROTECT_MAGIC = new TBox(588, 332, 620, 351);
	private static final TBox EAGLE_EYE = new TBox(701, 329, 729, 350);

	public static void checkRangedTri() {

		if (!rang.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(PROTECT_FROM_MISSILES);
		}

		if (!tri.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(MYSTIC_MIGHT);
		}

	}

	public static void checkMagicEagle() {

		if (!mage.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(PROTECT_MAGIC);
		}

		if (!eagle.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(EAGLE_EYE);
		}

	}

	public static void checkMage() {

		PRAYERS mage = Prayer.PRAYERS.PROTECT_FROM_MAGIC;
		if (!mage.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(PROTECT_MAGIC);
		}

	}

	public static void checkRanged() {

		PRAYERS rang = Prayer.PRAYERS.PROTECT_FROM_MISSILES;
		if (!rang.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(PROTECT_FROM_MISSILES);
		}
	}

	public static void checkMystic() {

		PRAYERS tri = Prayer.PRAYERS.MYSTIC_MIGHT;
		if (!tri.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(MYSTIC_MIGHT);
		}

	}

	public static void checkEagle() {

		PRAYERS eagle = Prayer.PRAYERS.EAGLE_EYE;
		if (!eagle.isEnabled() && Skills.SKILLS.PRAYER.getCurrentLevel() != 0) {
			changePrayer(EAGLE_EYE);
		}

	}

	public static void prayOff() {
		Prayer.disable(Prayer.getCurrentPrayers());
	}

	private static void changePrayer(TBox prayer) {
		if (!GameTab.TABS.PRAYERS.isOpen()) {
			if (Functions.percentageBool(UniqueZulrah.PERCENT_OPEN_PRAYER_TAB)) {
				Functions.FTAB(116, UniqueZulrah.TABS_SLEEP_MULTIPLIER);
			} else {
				GameTab.open(GameTab.TABS.PRAYERS);
			}
		}
		MoveMouseZul.humanMouseMove(prayer, UniqueZulrah.SPEED_PRAYER);
		MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
	}
}
