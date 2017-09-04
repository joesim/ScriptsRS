package scripts.flaxUtilities;

import org.tribot.api.General;

import scripts.utilities.CamThread;

public class CameraFlax implements Runnable {

	private CamThread cam = new CamThread();
	@Override
	public void run() {
		while (true){
			cam.setCamera(General.random(80, 100), 100);
			General.sleep(1000);
		}
		
	}
	

}
