package scripts.spinner_tribot.spinner_utilities;

import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.utilities.Timer;

public class Variables {

	public static int FLAX_ID = 1779;
	public static int BOWSTRING_ID = 1777;
	
	//Unique variables.
	public static final double SPEED_OPEN_DOOR = 1.5;
	public static final double SPEED_FAST_CLICK = 1;
	public static final int P_CLIMBUP_RIGHT = 95;
	public static final int P_CLICK_ONETIME_STAIRS2 = 80;
	public static final int P_HOVERMM = 80;
	public static final int P_MULTIPLE_CLICKS_MM = 15;
	public static final int P_MOUSE_OUT_ALL = 90;
	public static final int P_CHANCE_OUT = 10;
	public static final int P_MOUSE_HOVER_DEPOSIT = 90;
	public static final int P_DEPOSIT = 99;
	public static final int P_CLOSE_BANK = 97;
	public static final int P_CLOSE_TAB = 70;
	public static final int P_FAST_WITHDRAW = 96;
	public static final int P_USEMM = 95;
	public static final int MIN_BREAK = 600000;
	public static final int MAX_BREAK = 1800000;
	public static final double P_MOUSE_LEAVE = 0.3;
	public static final double P_LOGOUT = 0.08; // 0.08 = 2 hours avg.
	public static final double P_RANDOMSLEEP = 0.1;
	public static final int MAX_OUT_GAME_HOVER = 10000;
	public static final Double SPEED_CLICK_STAIRS_MIN = 1.0;
	public static final Double SPEED_CLICK_STAIRS_MAX = 2.0;
	public static final int P_CLICK_FIRST_BANK = 90;
	public static final int P_CHANCE_MOVE_WHEN_MOUSE_MM = 90;
	public static final double P_RIGHT_CLICK_STAIRS_HOVER = 0.1;
	public static final double P_WAIT_LONGER_DEPOSIT = 0.5;
	public static final double SLEEP_MODIFIER_CLICK_SPIN = 0.4;

	

	public static RSObject bankLumb;
	public static RSObject stairsTopLumb;
	public static RSTile tile1spin = new RSTile(3208, 3212, 1);
	public static RSTile tile2spin = new RSTile(3212, 3217, 1);
	public static RSTile tile1spinner = new RSTile(3209, 3213, 1);
	public static RSTile tile2spinner = new RSTile(3209, 3213, 1);
	public static ConditionFlax c1 = new ConditionFlax(1);
	public static ConditionFlax c2c13c14c17 = new ConditionFlax(2, 13, 14, 17);
	public static ConditionFlax c3c8 = new ConditionFlax(3, 8);
	public static ConditionFlax c4c12 = new ConditionFlax(4, 12);
	public static ConditionFlax c5 = new ConditionFlax(5);
	public static ConditionFlax c6c14 = new ConditionFlax(6, 14);
	public static ConditionFlax c7 = new ConditionFlax(7);
	public static ConditionFlax c9c12 = new ConditionFlax(9, 12);
	public static ConditionFlax c10 = new ConditionFlax(10);
	public static ConditionFlax c11 = new ConditionFlax(11);
	public static ConditionFlax c12 = new ConditionFlax(12);
	public static ConditionFlax c13 = new ConditionFlax(13);
	public static ConditionFlax c14 = new ConditionFlax(14);
	public static ConditionFlax c15 = new ConditionFlax(15);
	public static ConditionFlax c16 = new ConditionFlax(16);
	public static ConditionFlax c17 = new ConditionFlax(17);
	public static ConditionFlax c18 = new ConditionFlax(18);
	public static Timer timerMouseOut = new Timer(1000000000);
	public static Timer timerLogOut = new Timer(1000000000);
	
}
