package scripts;

import java.util.ArrayList;

import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import scripts.dropper.DropperGUI;
import scripts.utilities.ShiftDrop;

@ScriptManifest(authors = { "volcom3d" }, category = "Tools", name = "Shift dropper")
public class Dropper extends Script {

	public static boolean startDropping = false;
	public static ArrayList<Integer> ids = new ArrayList<Integer>();
	
	@Override
	public void run() {
		DropperGUI g = new DropperGUI();
		g.setLocationRelativeTo(null);
		g.setVisible(true);

		while (true) {
			while (!startDropping)
				sleep(100);
			if (ids.size() == 0){
				ShiftDrop.shiftDropAll();
			} else {
				int[] idsArray = new int[ids.size()];
				for (int i = 0; i<ids.size();i++){
					idsArray[i] = ids.get(i);
				}
				ShiftDrop.shiftDrop(idsArray);
			}
			startDropping = false;
		}
	}

}
