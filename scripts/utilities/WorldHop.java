package scripts.utilities;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Login;

public class WorldHop {

	public static void worldHop(int num) {
		if (Login.getLoginState().equals(Login.STATE.INGAME) && Game.getCurrentWorld() == num) {
			return;
		}
		Login.logout();
		General.sleep(3000,5000);
		Mouse.moveBox(new Rectangle(15, 470, 75, 20));
		Mouse.click(1);
		General.sleep(1000, 3000);
		ArrayList<World> list = new ArrayList<World>();
		list.add(new World(301, null, false, true));
		list.add(new World(302, null, true, true));
		list.add(new World(303, null, true, true));
		list.add(new World(304, null, true, true));
		list.add(new World(305, null, true, true));
		list.add(new World(306, null, true, true));
		list.add(new World(307, null, true, true));
		list.add(new World(308, null, false, false));
		list.add(new World(309, null, true, true));
		list.add(new World(310, null, true, true));
		list.add(new World(311, null, true, true));
		list.add(new World(312, null, true, true));
		list.add(new World(313, null, true, true));
		list.add(new World(314, null, true, true));
		list.add(new World(315, null, true, true));
		list.add(new World(316, null, false, true));
		list.add(new World(317, null, true, false));
		list.add(new World(318, null, true, true));
		list.add(new World(319, null, true, true));
		list.add(new World(320, null, true, true));
		list.add(new World(321, null, true, true));
		list.add(new World(322, null, true, true));
		list.add(new World(323, null, true, true));
		list.add(new World(324, null, true, true));
		list.add(new World(325, null, true, false));
		list.add(new World(326, null, false, true));
		list.add(new World(327, null, true, true));
		list.add(new World(328, null, true, true));
		list.add(new World(329, null, true, false));
		list.add(new World(330, null, true, true));
		list.add(new World(331, null, true, true));
		list.add(new World(332, null, true, true));
		list.add(new World(333, null, true, true));
		list.add(new World(334, null, true, true));
		list.add(new World(335, null, false, true));
		list.add(new World(336, null, true, true));
		list.add(new World(337, null, true, false));
		list.add(new World(338, null, true, true));
		list.add(new World(339, null, true, true));
		list.add(new World(340, null, true, true));
		list.add(new World(341, null, true, true));
		list.add(new World(342, null, true, true));
		list.add(new World(343, null, true, true));
		list.add(new World(344, null, true, true));
		list.add(new World(345, null, true, false));
		list.add(new World(346, null, true, true));
		list.add(new World(347, null, true, true));
		list.add(new World(348, null, true, true));
		list.add(new World(349, null, true, false));
		list.add(new World(350, null, true, true));
		list.add(new World(351, null, true, true));
		list.add(new World(352, null, true, true));
		list.add(new World(353, null, true, false));
		list.add(new World(354, null, true, true));
		list.add(new World(355, null, true, true));
		list.add(new World(356, null, true, true));
		list.add(new World(357, null, true, true));
		list.add(new World(358, null, true, true));
		list.add(new World(359, null, true, true));
		list.add(new World(360, null, true, true));
		list.add(new World(361, null, true, false));
		list.add(new World(362, null, true, true));
		list.add(new World(365, null, true, false));
		list.add(new World(366, null, true, false));
		list.add(new World(367, null, true, true));
		list.add(new World(368, null, true, true));
		list.add(new World(369, null, true, true));
		list.add(new World(370, null, true, true));
		list.add(new World(373, null, true, false));
		list.add(new World(374, null, true, true));
		list.add(new World(375, null, true, true));
		list.add(new World(376, null, true, true));
		list.add(new World(377, null, true, true));
		list.add(new World(378, null, true, true));
		list.add(new World(381, null, false, false));
		list.add(new World(382, null, false, true));
		list.add(new World(383, null, false, true));
		list.add(new World(384, null, false, true));
		list.add(new World(385, null, false, false));
		list.add(new World(386, null, true, true));
		list.add(new World(387, null, true, true));
		list.add(new World(388, null, true, true));
		list.add(new World(389, null, true, true));
		list.add(new World(390, null, true, true));
		list.add(new World(391, null, true, false));
		list.add(new World(392, null, true, false));
		list.add(new World(393, null, false, true));
		list.add(new World(394, null, false, true));

		int x[] = { 160, 250, 345, 440, 535 };
		int compteur = 0;
		for (int i = 0; i < x.length; i++) {
			for (double j = 40; j < 475; j += 23.8888) {
				if (compteur < list.size()) {
					list.get(compteur).setRec(new Rectangle(x[i], (int) j, 60, 10));
					compteur++;
				}
			}
		}

		for (World w : list) {
			if (w.world == num) {
				General.println(w.world);
				General.println(w.rec);
				Mouse.moveBox(w.rec);
				General.sleep(100, 500);
				Mouse.click(1);
				General.sleep(1000, 3000);
				return;
			}
		}

	}

	public static void worldHopMember() {
		Login.logout();
		Mouse.moveBox(new Rectangle(15, 470, 75, 20));
		Mouse.click(1);
		General.sleep(1000, 3000);
		ArrayList<World> list = new ArrayList<World>();
		list.add(new World(301, null, false, true));
		list.add(new World(302, null, true, true));
		list.add(new World(303, null, true, true));
		list.add(new World(304, null, true, true));
		list.add(new World(305, null, true, true));
		list.add(new World(306, null, true, true));
		list.add(new World(307, null, true, true));
		list.add(new World(308, null, false, false));
		list.add(new World(309, null, true, true));
		list.add(new World(310, null, true, true));
		list.add(new World(311, null, true, true));
		list.add(new World(312, null, true, true));
		list.add(new World(313, null, true, true));
		list.add(new World(314, null, true, true));
		list.add(new World(315, null, true, true));
		list.add(new World(316, null, false, true));
		list.add(new World(317, null, true, false));
		list.add(new World(318, null, true, true));
		list.add(new World(319, null, true, true));
		list.add(new World(320, null, true, true));
		list.add(new World(321, null, true, true));
		list.add(new World(322, null, true, true));
		list.add(new World(323, null, true, true));
		list.add(new World(324, null, true, true));
		list.add(new World(325, null, true, false));
		list.add(new World(326, null, false, true));
		list.add(new World(327, null, true, true));
		list.add(new World(328, null, true, true));
		list.add(new World(329, null, true, false));
		list.add(new World(330, null, true, true));
		list.add(new World(331, null, true, true));
		list.add(new World(332, null, true, true));
		list.add(new World(333, null, true, true));
		list.add(new World(334, null, true, true));
		list.add(new World(335, null, false, true));
		list.add(new World(336, null, true, true));
		list.add(new World(337, null, true, false));
		list.add(new World(338, null, true, true));
		list.add(new World(339, null, true, true));
		list.add(new World(340, null, true, true));
		list.add(new World(341, null, true, true));
		list.add(new World(342, null, true, true));
		list.add(new World(343, null, true, true));
		list.add(new World(344, null, true, true));
		list.add(new World(345, null, true, false));
		list.add(new World(346, null, true, true));
		list.add(new World(347, null, true, true));
		list.add(new World(348, null, true, true));
		list.add(new World(349, null, true, false));
		list.add(new World(350, null, true, true));
		list.add(new World(351, null, true, true));
		list.add(new World(352, null, true, true));
		list.add(new World(353, null, true, false));
		list.add(new World(354, null, true, true));
		list.add(new World(355, null, true, true));
		list.add(new World(356, null, true, true));
		list.add(new World(357, null, true, true));
		list.add(new World(358, null, true, true));
		list.add(new World(359, null, true, true));
		list.add(new World(360, null, true, true));
		list.add(new World(361, null, true, false));
		list.add(new World(362, null, true, true));
		list.add(new World(365, null, true, false));
		list.add(new World(366, null, true, false));
		list.add(new World(367, null, true, true));
		list.add(new World(368, null, true, true));
		list.add(new World(369, null, true, true));
		list.add(new World(370, null, true, true));
		list.add(new World(373, null, true, false));
		list.add(new World(374, null, true, true));
		list.add(new World(375, null, true, true));
		list.add(new World(376, null, true, true));
		list.add(new World(377, null, true, true));
		list.add(new World(378, null, true, true));
		list.add(new World(381, null, false, false));
		list.add(new World(382, null, false, true));
		list.add(new World(383, null, false, true));
		list.add(new World(384, null, false, true));
		list.add(new World(385, null, false, false));
		list.add(new World(386, null, true, true));
		list.add(new World(387, null, true, true));
		list.add(new World(388, null, true, true));
		list.add(new World(389, null, true, true));
		list.add(new World(390, null, true, true));
		list.add(new World(391, null, true, false));
		list.add(new World(392, null, true, false));
		list.add(new World(393, null, false, true));
		list.add(new World(394, null, false, true));

		int x[] = { 160, 250, 345, 440, 535 };
		int compteur = 0;
		for (int i = 0; i < x.length; i++) {
			for (double j = 40; j < 475; j += 23.8888) {
				if (compteur < list.size()) {
					list.get(compteur).setRec(new Rectangle(x[i], (int) j, 60, 10));
					compteur++;
				}
			}
		}

		do {
			int wor = General.random(301, 391);
			for (World w : list) {
				if (w.world == wor && w.member && w.functional) {
					General.println(w.world);
					General.println(w.rec);
					Mouse.moveBox(w.rec);
					General.sleep(100, 500);
					Mouse.click(1);
					General.sleep(1000, 3000);
					return;
				}
			}
		} while (true);
	}

}
