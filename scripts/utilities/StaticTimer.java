package scripts.utilities;

public class StaticTimer {

	public static Timer timer = new Timer(100000000);
	
	public StaticTimer(int i) {
		timer = new Timer(i);
	}

	public static void reset(){
		timer = new Timer(1000000000);
	}
	
	public static int getElapsed() {
		return (int) timer.getElapsed();
	}

}
