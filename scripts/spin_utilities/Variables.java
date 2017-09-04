package scripts.spin_utilities;

import org.tribot.api.General;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.utilities.Timer;

public class Variables {

	public static int FLAX_ID = 1779;
	public static int BOWSTRING_ID = 1777;
	
	//Unique variables.
	public static final double SPEED_OPEN_DOOR = General.randomDouble(1.2, 1.8);
	public static final double SPEED_FAST_CLICK = General.randomDouble(0.9, 1.5);
	public static final int P_CLIMBUP_RIGHT = General.random(90, 100);
	public static final int P_CLICK_ONETIME_STAIRS2 = General.random(75, 85);
	public static final int P_HOVERMM = General.random(75, 85);
	public static final int P_MULTIPLE_CLICKS_MM = General.random(10, 40);
	public static final int P_MOUSE_OUT_ALL = General.random(80, 99);
	public static final int P_CHANCE_OUT = General.random(5, 15);
	public static final int P_MOUSE_HOVER_DEPOSIT = General.random(85, 95);
	public static final int P_DEPOSIT = General.random(93, 99);
	public static final int P_CLOSE_BANK = General.random(0,20);
	public static final int P_CLOSE_TAB = General.random(60, 80);
	public static final int P_FAST_WITHDRAW = General.random(93, 99);
	public static final int P_USEMM = General.random(90, 99);
	public static final double P_MOUSE_LEAVE = General.randomDouble(0.2, 0.5);
	public static final double P_LOGOUT = 0.08; // 0.08 = 2 hours avg.
	public static final double P_RANDOMSLEEP = General.randomDouble(0.1, 0.3);
	public static final int MAX_OUT_GAME_HOVER = 12000;
	public static final Double SPEED_CLICK_STAIRS_MIN = 1.0;
	public static final Double SPEED_CLICK_STAIRS_MAX = 2.0;
	public static final int P_CLICK_FIRST_BANK = 90;
	public static final int P_CHANCE_MOVE_WHEN_MOUSE_MM = 90;
	public static final double P_RIGHT_CLICK_STAIRS_HOVER = 0.1;
	public static final double P_WAIT_LONGER_DEPOSIT = General.randomDouble(0.1, 2);
	public static final double SLEEP_MODIFIER_CLICK_SPIN = General.randomDouble(0.2, 1);
	public static final int PERCENT_HOVER_SPINNER = General.random(50, 90);
	public static final int PERCENT_NOT_ACC_MOUSE_SPINNER = General.random(40, 90);
	public static final int MOUSE_MOVE_SPINNER_MAKEX_PERCENT = General.random(1, 20);
	public static final int PERCENT_TYPE_BEFORE = General.random(1, 5);
	public static final int PERCENT_LOGOUT_BUTTON = General.random(60, 90);
	public static final int P_WAIT_CLICKING_SPINNER = General.random(1, 30);
	public static final int P_WAIT_CLICKING_SPINNER_2 = (General.random(1, 30));
	public static final int P_M_HOVER_BANK = General.random(90, 100);
	public static final int P_M_HOVER_STAIRS = General.random(90, 100);
	public static final int P_M_HOVER_SPINNER = General.random(85, 100);
	public static final int P_BANK_1 = General.random(90, 100);
	public static final int P_TYPE_28 = General.random(10, 90);
	public static final int P_TYPE_99 = General.random(90, 110);
	public static final double CLICK_OFFSET = General.random(70, 72);
	public static final double SLEEP_MODIF_RIGHT_CLICK = General.randomDouble(1.0, 1.5);
	public static final int P_RECLICK_SPINNER = General.random(20, 90);
	public static final int P_MOUSE_CLICK_BANK = General.random(95, 100);
	public static final int P_CLICK_TOP_STAIRS = General.random(40, 100);
	public static final int PERCENT_MOUSE_JOE = General.random(65, 95);
	

	

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
	public static ConditionFlax c6c14c19 = new ConditionFlax(6, 14, 19);
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
	public static ConditionFlax c19 = new ConditionFlax(19);
	public static Timer timerMouseOut = new Timer(1000000000);
	public static Timer timerLogOut = new Timer(1000000000);
	
}
