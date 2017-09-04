package scripts.utilities;

public class ConditionTime implements Condition {

	int time = 0;
	public Timer tm = null;
	
	public ConditionTime(int i){
		time = i;
		tm = new Timer(1000000000);
	}

	public boolean checkCondition() {
		if (tm.getElapsed() > time){
			return true;
		}
		return false;
	}

}
