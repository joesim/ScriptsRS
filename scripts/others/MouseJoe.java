package scripts.others;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSMenuNode;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.utilities.Timer;

public final class MouseJoe {

	public static ArrayList<List<java.awt.Point>> listeP = new ArrayList<List<java.awt.Point>>();
	public static ArrayList<List<java.awt.Point>> listePHover = new ArrayList<List<java.awt.Point>>();
	public SleepJoe sleepJoe = new SleepJoe();
	public final static int NB_FILES = 13;
	private static final int PERCENT_TRIBOT_DEPOSITALL = General.random(0, 7);
	public long last = 0;
	private static boolean outHover = false;

	/** Offsets en x (-50 a 50) **/
	static int[] positionX = { 17, 1, 5, 6, -4, -16, -8, 0, 1, 5, 14, 15, 1, -13, -6, -9, 9, 11, 11, -1, -13, -14, -11,
			0, -1, -18, -14, -9, -6, 8, 8, 8, -2, 10, 13, 0, -10, -16, -18, -24, -24, -14, -9, -3, -3, -3, 5, 12, 11,
			-2, -7, -9, -9, 4, 9, 8, -3, -10, -16, -27, -34, -25, -14, -8, -10, -31, -33, -33, -28, -16, -1, 13, 15, 24,
			28, 38, 37, 33, 29, 16, -4, -20, 18, 24, 38, 37, 21, 5, -10, -16, -2, 20, 27, 27, 22, 22, 25, 28, 33, 29,
			15, 10, 14, 7, -7, -2, 1, -21, -29, -35, -36, -37, -38, -36, -32, -29, -10, -31, -45, -43, 35, 11, -8, 11,
			25, 32, 23, 15, 10, 2, -12, -20, -32, -41, -41, -41, -41, -41, -39, -15, -16, -16, -16, -12, 12, -11, -11,
			12, 15, 13, 13, 21, 22, 22, 22, 25, 23, 21, 14, 12, 3, -10, -19, -21, -26, -24, -17, -13, -3, 0, -7, -10,
			-12, -6, 6, 7, 10, 11, -4, -1, 1, 0, 4, 6, 8, 10, 2, 1, -2, 9, 12, 12, 10, 2, -7, -13, -17, -8, 4, 10, 2,
			-3, 0, 0, 0, 0, 0, 0, 0, -2, 12, -3, -7, 1, 6, 16, 5, -15, -18, -8, 1, 15, 23, 11, -6, -1, -9, -15, -15, -8,
			-8, -19, 1, 6, 10, -7, -8, 6, 4, 3, 1, -9, -21, -24, -28, -28, -37, -37, -27, -34, -29, -19, -31, -20, -25,
			-27, -33, -12, -21, -27, -22, -11, 13, 23, 23, 27, 29, 31, 33, 43, 42, 41, 38, 38, 27, 31, 35, 35, 35, 34,
			30, 37, 40, 40, 40, 33, 31, 35, 37, 44, 12, -11, -19, -27, -34, -41, -20, 0, 12, 18, 5, 0, 10, 18, 18, 14,
			-11, -16, -18, -27, -25, -25, -21, -23, -18, -19, -13, -5, -21, -20, 10, 21, 11, 12, 20, 12, 14, 15, 10, -3,
			-4, 7, 19, 23, 11, 2, 2, 10, 11, -8, -12, -11, -3, 14, 13, -6, -22, -7, -2, -5, 13, -3, 9, -2, 3, -2, 6, 0,
			4, 8, 4, 4, 2, 3, 3, 0, 3, 4, 4, 6, 6, 10, 3, -4, -19, -21, -14, -9, -9, -18, -11, -9, -7, -5, -15, -16,
			-13, -3, 6, 7, -3, -9, -7, 5, -21, -37, -44, -33, -36, -32, -32, -28, -19, -17, -11, -18, -18, -11, -11,
			-22, -15, -6, -1, 5, 17, 26, 31, 22, 9, 13, 17, 22, 33, 35, 33, 35, 40, 42, 35, 28, 22, 8, 0, -16, -16, -2,
			4, 15, 30, 36, 37, 32, 9, -1, -17, -13, 6, 12, 12, 6, 3, 0, 6, 11, 11, 13, 23, 26, 19, 25, 25, 21, 21, 29,
			36, 30, 30, 31, 32, 34, 37, 30, 14, -5, -17, -19, -24, -30, -31, -25, -28, -29, -30, -33, -38, -42, -42,
			-36, -35, -34, -29, -30, -30, -29, -30, -33, -36, -37, -38, -38, -37, -35, -31, -25, -8, 6, 9, 23, 24, 17,
			10, 10, 14, 14, 17, 21, 19, 19, 27, 30, 34, 37, 37, 38, 33, 27, 27, 40, 40, 38, 3, 2, 2, 2, -23, -13, -2,
			20, 19, 19, 19, 20, 15, 7, -3, -1, 8, 11, 16, 16, 9, 9, 0, -4, -12, -16, -17, -15, -4, -3, -9, -9, -13, -14,
			-17, -17, -17, -17, -17, -17, -29, -9, -6, 4, 4, 8, 1, 0, -11, -13, -20, -20, -20, -22, -19, -14, -16, -17,
			-10, -6, -9, 8, 12, 13, 17, 19, 19, 19, 19, 21, 21, 21, 21, 22, 21, 19, 18, 17, 15, 15, 15, 12, 3, 2, -4,
			-4, -2, 0, -1, -3, -2, 1, 1, -2, -9, -18, -30, -32, -32, -27, -16, -2, -9, -11, -11, -15, -15, -8, 3, 4, 8,
			8, 11, 7, 8, 8, 8, 3, 3, 2, 8, 10, 10, -11, -6, 2, 3, -6, -9, -13, -4, 3, 19, 12, 12, -4, -4, -2, -6, -18,
			-5, 7, 30, 23, 14, -1, -12, -13, -18, -21, -14, 4, 11, 23, 10, 1, -13, -16, -3, 27, 18, 3, -2, -9, -7, -5,
			-24, -22, -3, 10, 8, -1, -2, 16, 13, 10, 1, 2, 2, -5, -16, -4, 5, -1, 0, 0, -2, -2, -8, 15, -11, -3, 2, 2,
			4, 3, -7, 3, 8, 17, 12, 5, 4, -12, -16, -29, -27, -19, -16, -29, -30, -36, -32, -31, -31, -15, -11, -29,
			-46, -46, -36, 40, 40, 43, 46, 43, 37, 44, 21, 15, 9, -3, -4, 6, 11, 17, 7, -2, 15, 9, 23, 24, 22, 15, -2,
			-12, -38, -36, -31, -28, -23, -13, 3, 12, -4, -17, -3, 12, 13, -5, -14, -15, 7, 12, -7, -6, -15, -21, -33,
			-27, -21, -24, -37, -33, -26, -26, -24, -25, -18, -21, -21, -21, -24, -22, -22, -22, -22, -22, -12, -12,
			-19, -24, -17, -14, -14, -15, -1, -2, -7, -7, -7, -5, -4, -3, -2, -5, -5, -2, -2, -6, -6, -14, -17, -26,
			-24, -11, -1, 13, 26, 21, 20, 8, 8, 1, 0, 4, 4, 5, 5, 7, 18, 12, 12, 21, 22, 18, 18, 18, 18, 15, 16, 12, 24,
			19, 13, 17, 17, 7, 6, -1, -18, -22, -9, 5, 2, -18, -10, -2, -9, -8, -29, -6, -8, -6, -21, -22, -21, -9, -25,
			-20, -13, -5, -40, -21, -11, -30, -17, -19, -12, -1, 10, 22, 34, 31, 26, 15, 25, 25, 30, 30, 14, 14, 6, 2,
			-5, -12, -22, -30, -39, -39, -30, -29, -29, -29, -1, -1, 2, 2, 12, 15, 15, 9, -3, -3, -3, 13, 5, 0, -14,
			-20, -17, -19, -39, -39, -37, -37, -28, -28, -31, -36, -36, -27, -15, 5, 23, 31, 32, 34, 36, 33, 29, 19, 6,
			10, 18, 41, 41, 41, 41, 41, 39, 31, 35, 36, 36, 27, 23, 28, 10, 4, 3, 14, 18, 14, 5, 3, 4, 4, 7, 12, 8, -4,
			-13, -13, -9, -9, -11, -15, -33, -30, -23, -28, -24, -22, -23, -19, -19, -21, -22, -26, -26, -32, -32, -37,
			-37, -42, -39, -38, -37, -36, -33, -28, -28, -28, -28, -28, 8, 10, 16, 16, 16, 16, 21, 21, 22, 24, 21, 15,
			14, -8, -12, -1, -1, -7, 11, 11, -2, -10, -13, -1, 3, 11, 11, -8, -8, -3, 3, 3, 3, 11, 17, 4, 1, 1, -16,
			-16, -13, -10, -10, -10, 12, 10, -4, -4, -10, -8, 10, 10, -48, 5, -2, -2, 10, 12, 17, 26, 16, 14, 14, -6,
			-7, -11, 4, 15, 25, 27, 19, 16, 17, 12, 12, 3, 3, 7, 6, 6, 1, 6, 6, 0, -1, 15, 14, -10, 1, -4, -7, 8, 20,
			13, 8, 4, 8, 19, 18, 6, 2, 6, 7, 10, 4, -5, -10, -9, 4, 10, 10, 5, 2, -6, 8, 16, 18, 10, 4, -4, 9, 2, -9,
			-15, -30, -24, -16, -9, -2, -2, -1, 6, 9, 6, 4, -4, -10, -13, -17, -16, -5, 0, -8, 20, 8, 1, 4, 7, 6, 8, 1,
			7, 4, 15, 9, 7, 5, 0, 3, -3, -5, -8, -4, -6, -11, 4, 5, 7, 12, 9, 5, -4, -7, 2, 9, 2, 0, 9, 6, 1, 1, 1, -1,
			-4, -11, -17, -23, -17, -1, 15, 7, 3, -2, -9, 3, 16, 16, 7, 2, 1, 2, 8, 20, 23, 20, 18, 12, 3, -8, -22, -36,
			-38, -35, -36, -31, -14, 0, 19, 27, 30, 32, 32, 32, 36, 41, 42, 37, 25, 5, -16, -19, -30, -42, -42, -38,
			-26, -16, -15, -17, -15, -14, -7, 1, 12, 12, 12, 12, 12, 8, 2, -2, -1, -1, -1, -10, -8, 4, 8, 5, 6, -11,
			-23, -26, -9, 0, 5, 17, 17, 19, 8, -3, -14, -11, 26, 28, 15, -4, -9, -9, 5, 10, 11, 7, -15, -18, -15, 3, 9,
			4, 2, -12, 3, 8, 11, 7, -8, 0, 2, 13, 9, 4, -6, -5, 4, 4, 3, -3, -2, 4, 4, 3, 1, 19, 8, 7, 3, 3, 6, 6, 6, 6,
			4, 4, -8, 1, 2, -18, -13, -15, -32, -21, -6, -5, 0, 2, 4, 13, 12, 16, 21, 32, 23, 19, 21, 28, 30, 35, 36,
			32, 22, 42, 39, 28, 29, 42, 21, 21, 4, 12, -22, -17, 3, -8, -19, -48, -32, -35, -46, -29, -20, -18, -15,
			-12, 6, 1, -6, 0, 1, 2, -2, -3, 1, 9, 4, 3, 8, -8, -9, -9, -9, -16, -11, -8, -4, -8, -24, -35, -21, -2, 6,
			4, -9, -20, -20, -11, -9, 3, 17, 8, 1, -3, -5, 0, 7, 7, -14, -10, 4, 13, 13, 0, -1, -1, -1, -1, -12, -27,
			-35, -35, -25, 2, 7, 15, 22, 28, 28, 10, 0, 11, 42, 37, 37, 36, 23, 3, -1, -20, -33, -33, -45, -37, -33,
			-13, -2, 5, 16, 24, 33, 38, 40, 40, 39, 30, 16, -17, 3, -1, -4, 12, 15, -7, -4, 43, 48, 44, 12, -2, -7, 17,
			25, 35, 35, 30, 12, 2, 0, -7, -17, -15, -13, -10, -10, -10, -28, -25, -15, 0, 0, 6, 6, -5, -5, -5, -3, 4,
			13, 14, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, -10, -15, -14, 11, 21, 14, 14, 13, 10, 16, 17, 23, 25, 28, 28, 25, 26,
			26, 4, -6, -25, -32, -29, -14, -14, 3, 8, -4, -11, -11, -1, 0, 4, 4, 18, 18, 14, 14, 3, 1, -2, -14, -16,
			-16, -2, 9, 13, 8, 1, 19, 18, 18, 10, -1, 11, 26, 26, 13, -5, 2, 0, 0, -3, -18, -21, -21, -21, -14, 0, 14,
			25, 28, 11, 0, 10, 11, 19, 29, 40, 30, 22, 24, 12, -1, -12, -27, -30, -35, -39, -40, -28, -34, -11, -5, 4,
			2, -3, 2, 11, 18, 22, 31, 34, 34, 30, 21, 7, 1, -2, -8, -8, -8, 10, -10, -12, -12, -16, -23, -39, -33, -25,
			-25, -33, -41, -42, -43, -41, -33, -16, 2, 17, 28, 35, 37, 37, 24, 9, 5, 5, 20, 2, 14, 22, 5, -5, 0, 1, 1 };

	/** Offsets en y (-50 a 50) **/
	static int[] positionY = { 6, -6, 5, 23, 14, 8, 0, -12, 1, 12, 3, -3, -15, -2, 16, 10, -8, -8, -6, 2, -4, -8, -13,
			-14, 12, 17, 14, -2, -7, 4, 9, 10, -12, -14, -14, 4, 14, 15, 5, -6, -11, -19, -19, -17, -17, -17, -22, -11,
			3, 11, 15, 5, 5, -1, 2, 13, 20, 19, 16, 36, 31, 27, 20, 11, -2, -20, -23, -23, -24, -26, -28, -32, -32, -33,
			-33, -15, 0, 18, 24, 29, 30, 36, 37, 37, 38, 28, 33, 34, 35, 35, 28, 26, 16, 2, -8, -19, -16, -16, 1, 8, 12,
			9, -6, -21, -11, 4, 5, -4, 8, 22, 13, -2, -7, -18, -30, -31, -42, -38, -43, -33, -32, -41, -37, -21, -6, 18,
			29, 38, 37, 37, 38, 38, 35, 38, 33, 19, 10, 2, -1, 21, 24, 24, 6, 5, 2, 10, 10, 7, 7, 2, -5, -14, -15, -15,
			-15, -4, 4, 9, 15, 16, 20, 15, 12, 7, 1, -2, -7, -8, 4, 7, 11, 8, 3, -1, 11, 25, 2, -4, -11, 6, -3, -5, -5,
			-5, -5, -5, -18, -19, -13, -4, -11, -11, -13, -19, -18, -6, 10, 12, 12, 8, -3, 2, 4, 4, 4, 4, 4, 4, 4, -7,
			15, 17, -4, -5, 29, 14, 3, 3, 29, 10, -3, 17, -3, -12, 3, -8, -12, -13, -12, -23, -23, -27, -21, -21, -29,
			-34, -34, -27, -27, -36, -36, -35, -32, -31, -19, -4, 11, 19, 28, 13, 14, 22, 13, 16, 21, 18, 5, -21, -36,
			-24, -25, -18, -17, -25, -26, -24, -13, 0, 5, -6, -11, -24, -34, -37, -21, -11, -5, -20, -20, -7, 8, 26, 20,
			13, 10, 4, 9, 12, 6, -3, 36, 35, 36, 38, 42, 37, 12, 7, 0, -13, -19, -7, 2, 0, 0, 14, 12, 0, -9, -19, -7, 1,
			7, 12, -5, -12, -19, -17, 7, 8, 28, 11, 9, 10, 21, 12, 4, -2, -13, -3, 6, 15, -1, -11, -8, 15, 24, -3, -26,
			-11, 6, 9, 6, 24, 22, 21, 6, -15, -16, 22, -2, 10, -13, 24, -7, -14, -4, -17, -11, -9, -11, -11, -19, -12,
			-11, -7, -10, -10, -17, -16, -16, -17, -13, -10, -10, -19, -8, 3, 14, 16, -7, -6, 3, 5, -16, -19, -13, 1, 3,
			-8, -14, -3, 12, 22, 4, -12, -20, -7, -23, -25, -31, -40, -40, -29, -22, -31, -36, -29, -28, -35, -35, -34,
			-39, -39, -37, -39, -40, -40, -36, -44, -44, -42, -33, -21, -2, 12, 30, 31, 38, 40, 38, 37, 38, 42, 37, 40,
			43, 42, 45, 40, 22, 15, 2, -2, 17, 21, 31, 30, 17, 13, 12, 14, 22, 26, 27, 29, 23, 23, 27, 22, 14, -2, 27,
			16, 19, 31, 33, 33, 40, 30, 23, 24, 30, 30, 31, 35, 36, 28, 28, 25, 22, 22, 28, 31, 34, 26, 25, 23, 23, 18,
			0, -8, -14, -16, -20, -29, -34, -34, -29, -18, -4, 7, -10, -18, -26, -28, -28, -23, -20, -17, -26, -26, -25,
			-27, -26, -22, -22, -12, -18, -30, -31, -28, -19, -13, -16, -29, -28, -7, -5, 2, 16, 12, 12, 12, 4, 5, 12,
			23, 2, -1, -1, 12, 27, 21, 6, 6, 13, 13, 20, 20, 15, 15, 19, 18, 14, 18, 21, 18, 20, 20, -3, -3, 0, 0, 0,
			-1, -1, -14, -14, -14, 9, -7, -7, -22, -22, -26, -21, -20, -19, -20, -8, -6, 0, 2, -6, -11, -7, -1, -3, 4,
			31, 30, 27, 23, 15, 13, 9, 8, 15, 12, -4, -9, -9, 7, 0, -4, -5, -15, -18, -18, -3, 5, 9, 9, 3, 3, 2, 2, 2,
			0, 0, -2, -11, -11, -8, 6, 17, 5, -14, -22, -19, -8, 14, -4, -12, 1, -5, -11, -7, -7, 8, 11, -4, -4, 1, 1,
			4, 11, 1, -6, -11, 1, 13, -4, -8, -4, 9, 22, 20, 8, 0, -6, 16, 3, 3, 15, 16, 12, 3, 13, 21, 7, -17, -19,
			-27, -24, -21, -28, -29, -28, 23, 17, -5, -40, -1, -5, -19, -32, -20, -15, -14, 24, 13, -6, 2, 39, 14, 1,
			-22, -17, 18, 8, 8, -2, 31, 32, 30, 24, 6, 17, -12, 6, -17, 13, -11, -6, 3, 3, -5, -10, 25, 2, -6, 20, 4,
			24, -13, 12, 27, 13, -2, 15, 16, 5, 31, 15, 2, 0, 14, 25, 25, 31, 24, 33, 33, 37, 47, 46, 45, -9, -42, -43,
			-33, -34, -39, -25, 45, 41, 3, 0, 3, 13, 12, 18, 21, 35, 41, 48, 48, 42, 42, 42, 32, 32, 33, 32, 45, 26, 18,
			2, -14, 4, 18, -1, 4, 10, -2, 21, 21, 25, 22, 38, 21, 3, 15, 5, 26, 27, 13, 5, -11, -16, -10, 4, 7, -11,
			-11, 0, -9, -19, -19, -17, -14, -11, -11, -11, -11, -11, -16, -17, -26, -8, 2, 0, -2, -8, -10, -9, -6, -5,
			-5, -3, -5, -5, -5, -8, -8, -18, -13, -5, -4, 0, -4, -14, -14, -13, 7, 18, 19, 8, 8, -1, -1, -3, -3, -1, -1,
			1, 8, 5, -1, -3, 10, 18, 18, 14, 9, 9, 13, 14, 9, 9, 13, 8, 12, 21, 21, 7, 7, 14, 23, 30, 29, 30, 19, 30,
			28, 24, 30, 30, 43, 24, 28, 22, 25, 26, 26, -2, 20, 24, 22, 15, 52, 28, 21, 26, 28, 39, 27, 23, 18, 11, -4,
			-8, -14, -3, 1, 1, 14, 14, -21, -21, -22, -27, -32, -33, -26, -19, -6, -1, 19, 20, 20, 20, -3, -3, 1, 1, -2,
			-1, -1, 4, -5, -5, -5, -35, -38, -36, -24, -22, -28, -35, -27, -14, 8, 9, 5, 1, 0, 4, 4, 18, 26, 27, 21, 10,
			5, -5, -15, -22, -24, -24, -16, 6, 16, 25, 24, 20, 11, 0, -4, -3, 4, 2, -7, -18, -22, -30, -28, -3, 16, 17,
			5, -2, 20, 26, 25, 18, 1, -11, -9, 3, 22, 17, 6, 24, 27, 25, 3, 9, 14, 22, 17, 26, 20, 29, 22, 17, 17, 19,
			20, -6, -6, 5, 6, 13, 15, 13, 6, -6, -9, -14, -10, -10, -10, -10, 9, 1, -6, -6, -7, -7, 7, 13, -6, -14, -10,
			0, 3, 7, -1, 5, 7, 20, 12, 11, -5, -1, 1, 13, 11, -1, -1, 6, 7, 2, -1, -1, -1, -4, -4, -6, -5, -5, -36, -36,
			-23, -16, 5, 15, 2, -3, -12, -13, 16, 17, 0, 0, -6, -1, 12, 12, 12, 12, 19, 5, 3, 23, 23, 35, 28, 30, 36,
			37, 34, 22, 14, 3, -4, -14, -14, 0, 0, -22, -12, -11, -5, -9, -9, -1, 31, 19, 3, 22, 15, -9, 5, -2, -9, -15,
			-11, 8, 22, 8, 4, 1, 2, 8, 8, -4, -5, -8, 1, 15, 9, 1, -6, -12, -9, 13, 8, -11, -14, -20, -18, -9, -37, -28,
			-21, -17, 4, 14, 15, 18, 14, 13, 9, 2, 1, -10, -10, -4, -6, -7, -3, 22, 7, 6, 21, 14, -6, 1, 8, 4, 1, -1, 6,
			1, 2, -6, -6, -7, -5, -1, -3, -12, -9, -13, -19, -19, -17, -16, -5, -5, 2, 4, 8, 17, 27, 24, 19, 10, 10, 11,
			16, 10, 15, 16, 20, 21, 20, 12, 7, 5, 6, 17, 1, 4, 4, 5, 3, 8, 4, -1, -8, -9, -15, -13, -14, -17, -23, -28,
			-31, -37, -37, -35, -29, -21, -8, 8, 17, 28, 36, 36, 27, 23, 8, 0, 0, 17, 27, 35, 36, 37, 36, 35, 38, 32, 3,
			-8, -11, -19, -19, -8, 1, -5, 6, 10, 0, 3, 4, 4, 4, -4, -8, -14, -11, -9, -9, -9, 14, 12, 13, 14, 2, -12,
			-9, 3, 16, 20, 16, 16, 6, 4, -7, -11, -12, -7, 6, 14, 4, -12, -9, -3, 3, 10, 6, -2, -8, -5, 12, 12, 11, 3,
			-4, -4, 7, 12, 8, -3, -8, -5, 7, 11, 4, 1, -2, 7, 10, 1, 1, 1, 7, 3, -4, -7, -11, -8, 3, 9, 10, 16, 10, 10,
			10, 10, 14, 3, 3, 4, -9, 4, 16, 10, 0, -10, -15, -9, 6, 4, -19, -19, -21, -33, -28, -27, -38, -24, -12, -4,
			-5, -21, -30, -30, -14, -3, -23, -25, -12, -9, -23, -15, -14, -3, -25, -19, -4, 0, 19, 28, 37, 23, 12, 9,
			10, -8, -23, -15, -5, -12, -22, -15, 2, 8, 4, 3, 12, 18, 15, 13, 2, 3, 9, 9, 15, 15, 24, 19, 16, 0, -4, -12,
			-2, 16, 30, 6, -6, -19, -29, -29, -32, -34, -40, -26, -11, 1, 5, 13, 6, -11, -11, 16, 15, 6, 2, 2, 9, 9, 9,
			9, 9, 19, 30, 18, -17, -26, -5, 14, 9, -17, -27, -28, -38, -38, -30, -32, -28, -14, 9, 27, 43, 39, 41, 31,
			31, 19, 8, 8, 39, 36, 36, 37, 24, 11, -5, -16, -31, -38, -38, -34, -48, -32, -23, -12, 14, 16, -45, -44,
			-14, 5, 22, -1, 9, 19, 38, 40, 34, 33, 20, 9, 15, 18, 3, 18, 21, 14, 8, -5, -5, -6, -5, 17, 20, 20, 0, 0, 4,
			4, 4, 8, -2, -12, -12, -4, -4, -4, -4, -4, -2, -2, -2, -2, -2, 4, 24, 23, 21, 20, 11, 11, -3, 4, 21, 19, 5,
			5, 8, 8, 13, -3, -3, 11, -9, -23, 0, 5, 2, 2, 5, -26, -10, 5, 5, 3, 2, -2, -2, -8, -9, -11, -11, -12, -12,
			-10, -3, -3, -3, 18, 23, 18, 17, 7, 4, 16, 23, 8, 15, 26, 18, 18, 26, 23, 13, 6, 0, -6, -12, -23, -25, 14,
			22, -7, 1, -10, -24, -19, -29, -28, -21, -8, -4, 15, 9, 23, 45, 36, 38, 41, 36, 21, 8, -19, -30, -37, -37,
			-6, -1, 12, -8, -10, -3, 6, -18, -27, -36, -37, -21, -10, -7, 9, 15, 15, 7, 7, 6, -2, -29, -20, 4, 10, 19,
			7, 4, -1, -1, -20, -21, -25, 2, 14, 29, 35, 38, 37, 30, 3, -14, -28, -43, -37, -11, -3, -10, -7, 2, -23,
			-21, 2, 7, 5, 5 };

	/**
	 * Load les data de mouse
	 */
	@SuppressWarnings("resource")
	public static void loadDataNormal() {

		for (int i = 0; i < (NB_FILES + 1); i++) {

			String filePath = "bin/scripts/Data/file" + i + ".txt";
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(filePath);
				br = new BufferedReader(fr);
				br = new BufferedReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sCurrentLine;
			listeP.add(new ArrayList<java.awt.Point>());
			try {
				while ((sCurrentLine = br.readLine()) != null) {
					java.awt.Point pt = new Point();
					pt.x = Integer.parseInt(sCurrentLine.substring(0, sCurrentLine.indexOf(",")));
					pt.y = Integer
							.parseInt(sCurrentLine.substring(sCurrentLine.indexOf(",") + 1, sCurrentLine.length()));
					listeP.get(listeP.size() - 1).add(pt);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ***************************** PLAY MOUSE *******************************
	// ************************************************************************
	public static void playMouse(Point endP, String upText, Condition c, double speed) {

		if (c != null && c.active()) {
			return;
		}

		// Courbe
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);

		// Liste
		final List<Point> listePoints = listeP.get(randomCourbe);
		int sizeListe = listePoints.size();

		Point firstFile = new Point(listePoints.get(0));
		Point lastFile = new Point(listePoints.get(sizeListe - 1));
		Point realLast = new Point(endP);
		Point realStart = Mouse.getPos();
		double ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
				/ Math.sqrt(Math.pow(lastFile.x, 2) + Math.pow(lastFile.y, 2));
		double angle = Functions.angleBetween2Lines(firstFile, lastFile, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((sizeListe) / (speed * Math.pow(ratio, 1) * sizeListe / 8));
		if (result <= 0 || result>=sizeListe) {
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point pt = null;
		Point toGo = new Point();
		for (int i = 0; i < sizeListe; i += result) {
			// Point de la liste. Moins l'offset pour avoir le vecteur (point de
			// debut).
			pt = new Point(listePoints.get(i));
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;

			// Rotation du point
			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			Mouse.hop(toGo);
			General.sleep(8);
			if (c != null && c.active()) {
				outHover = true;
				break;
			}
			if (upText != null && Game.isUptext(upText)) {
				break;
			}
		}
	}

	// ********************** HUMAN MOUSE *************************

	/** Fonction qui retourne un point dans le box **/
	public static java.awt.Point randomPoint(java.awt.Point point, int recx, int recy) {

		int randomLocation = Functions.generateRandomInt(1, 1455);
		java.awt.Point newPoint = new Point();

		int deltaX = positionX[randomLocation];
		int deltaY = positionY[randomLocation];

		deltaX = deltaX + Functions.generateRandomInt(-2, 2);
		deltaY = deltaY + Functions.generateRandomInt(-2, 2);

		newPoint.x = point.x + deltaX * recx / 50;
		newPoint.y = point.y + deltaY * recy / 50;
		return newPoint;

	}

	/**
	 * Function moves mouse dans une box passée en parametre (utilisé pour
	 * clicker sur un objet surtout)
	 **/
	public static void humanMouseMove(TBox box, double speed) {
		if (!box.isPointInBox(Mouse.getPos())) {
			java.awt.Point thePointMid = new Point();
			thePointMid.x = box.x1 + box.sizeX() / 2;
			thePointMid.y = box.y1 + box.sizeY() / 2;

			thePointMid = randomPoint(thePointMid, box.sizeX() / 2, box.sizeY() / 2);
			playMouse(thePointMid, null, null, speed);
		}
	}

	/**
	 * Function moves mouse dans une box passée en parametre (utilisé pour
	 * clicker sur un objet surtout)
	 **/
	public static void humanMouseMove(TBox box, double speed, Condition c) {
		if (!box.isPointInBox(Mouse.getPos())) {
			java.awt.Point thePointMid = new Point();
			thePointMid.x = box.x1 + box.sizeX() / 2;
			thePointMid.y = box.y1 + box.sizeY() / 2;

			thePointMid = randomPoint(thePointMid, box.sizeX() / 2, box.sizeY() / 2);
			playMouse(thePointMid, null, c, speed);
		}
	}

	/**
	 * Function moves mouse dans une box passée en parametre (utilisé pour
	 * clicker sur un objet surtout)
	 **/
	public static void humanMouseMoveNotStatic(TBox box, double speed) {
		java.awt.Point thePointMid = new Point();
		thePointMid.x = box.x1 + box.sizeX() / 2;
		thePointMid.y = box.y1 + box.sizeY() / 2;

		thePointMid = randomPoint(thePointMid, box.sizeX() / 2, box.sizeY() / 2);
		playMouse(thePointMid, null, null, speed);
	}

	// ***********************************CLICK FUNCTIONS
	// **********************************************************************
	/** Fonction qui spam click **/
	public static void spamClick(TBox box, int numberClicks, int range) {
		// Detruit uniformite des clicks.
		int begin = range;
		for (int i = 0; i < begin; i++) {
			if (Functions.generateRandomInt(1, 100) > 40) {
				range--;
			}
		}
		int numberSpam = Functions.generateRandomInt(numberClicks - range, numberClicks + range);
		for (int i = 0; i < numberSpam; i++) {
			fastClick(1, 0.7);
			SleepJoe.sleepHumanDelay(0.15, 20, 80);
		}
	}

	/** Fonction qui clique rapidement **/
	public static void fastClick(int button, double multiplier) {
		java.awt.Point point = Mouse.getPos();
		SleepJoe.sleepHumanDelay(0.02, 1, 20);
		Mouse.sendPress(point, button);
		SleepJoe.sleepHumanDelay(0.2 * multiplier, (int) (30 * multiplier), (int) (160 * multiplier));
		Mouse.sendRelease(point, button);
		SleepJoe.sleepHumanDelay(0.1, 1, 200);
	}

	/** Fonction qui clique rapidement **/
	public static void fastClickBank(int button, double multiplier) {
		java.awt.Point point = Mouse.getPos();
		SleepJoe.sleepHumanDelay(0.25, 1, 200);
		Mouse.sendPress(point, button);
		SleepJoe.sleepHumanDelay(0.2, (int) (30 * multiplier), (int) (160 * multiplier));
		Mouse.sendRelease(point, button);
		SleepJoe.sleepHumanDelay(0.25, 1, 200);
	}

	// ****************************************PLAY MOUSE FOLLOW
	// NPC******************************************************
	/**
	 * Suit une tile (tres specifique quand plusieurs meme ids)
	 * 
	 * @param tile
	 * @param tile1x
	 * @param tile1y
	 * @param tile2x
	 * @param tile2y
	 * @param ID
	 * @return
	 * @throws InterruptedException
	 */
	public static void playMouseFollowObject(RSObject npc, String uptext, double speed, Condition c,
			boolean firstCall) {
		if (c != null && c.active()) {
			return;
		}
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		final List<Point> listePts = listeP.get(randomCourbe);
		int i = 0;
		if (!firstCall) {
			i = listePts.size() / 3;
		}
		Point first = listePts.get(i);
		Point last = listePts.get(listePts.size() - 1);
		Point realLast = null;
		Point realStart = null;
		RSModel npcMod = null;
		double ratio;
		try {
			npcMod = npc.getModel();
			realLast = new Point(npcMod.getHumanHoverPoint().x + General.random(-20, 20),
					npcMod.getHumanHoverPoint().y + General.random(-20, 20));
			realStart = Mouse.getPos();

			ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
					/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
		} catch (NullPointerException e) {
			return;
		}
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((listePts.size() - i) / (speed * Math.pow(ratio, 1) * (listePts.size() - i) / 8));
		if (result == 0) {
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = first.x;
		int offY = first.y;
		Point pt = null;
		Point toGo = new Point();
		for (; i < listePts.size(); i += result) {

			if (c != null && c.active()) {
				return;
			}
			// Point de la liste. Moins l'offset pour avoir le vecteur (point de
			// debut).
			pt = new Point(listePts.get(i));
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;

			// Rotation du point
			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			Mouse.hop(toGo);
			General.sleep(8);
			if (npc != null && npc.isOnScreen() && !Functions.pointsProches(npcMod.getCentrePoint(), realLast, 10)) {
				playMouseFollowObject(npc, uptext, speed, c, false);
				break;
			}

		}
	}

	public static void playMouseFollowObject(RSObject npc, String uptext, double speed, Condition c, boolean firstCall,
			int offsetX, int offsetY, int i, int courbe, int range) {
		if (c != null && c.active()) {
			return;
		}
		int randomCourbe = courbe;
		if (courbe == 0) {
			randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		}
		final List<Point> listePts = listeP.get(randomCourbe);
		Point first = listePts.get(i);
		Point last = listePts.get(listePts.size() - 1);
		Point realLast = null;
		Point realStart = null;
		RSModel npcMod = null;
		double ratio;
		try {
			npcMod = npc.getModel();
			realLast = new Point(npcMod.getHumanHoverPoint().x + General.random(-range, range) + offsetX,
					npcMod.getHumanHoverPoint().y + General.random(-range, range) + offsetY);
			realStart = Mouse.getPos();

			ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
					/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
		} catch (NullPointerException e) {
			return;
		}
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round(1 / (speed * Math.pow(ratio, 1) / 8));
		if (result == 0) {
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = first.x;
		int offY = first.y;
		Point pt = null;
		Point toGo = new Point();
		Point centerPoint = npcMod.getCentrePoint();
		for (i = 0; i < listePts.size(); i += result) {

			if (c != null && c.active()) {
				return;
			}

			pt = new Point(listePts.get(i));
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;

			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			Mouse.hop(toGo);
			General.sleep(8);
			if (npc != null && npc.isOnScreen() && !Functions.pointsProches(npcMod.getCentrePoint(), centerPoint, 15)) {
				centerPoint = npcMod.getCentrePoint();
				realLast = new Point(npcMod.getHumanHoverPoint().x + General.random(-range, range) + offsetX,
						npcMod.getHumanHoverPoint().y + General.random(-range, range) + offsetY);
				realStart = Mouse.getPos();
				first = new Point(listePts.get(i + 1));
				offX = first.x;
				offY = first.y;
				sX = realStart.x;
				sY = realStart.y;
				angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
				ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
						/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2));
				// result = (int) Math.round(1 / (speed * Math.pow(ratio, 1) /
				// 8));
			}
			if (Functions.pointsProches(Mouse.getPos(), realLast, General.random(2, 6))) {
				break;
			}
		}
	}

	public static void playMouseFollowObjectText(RSObject npc, String uptext, double speed, Condition c,
			boolean firstCall, int offsetX, int offsetY, int i, int courbe, int range) {
		if (c != null && c.active()) {
			return;
		}
		int randomCourbe = courbe;
		if (courbe == 0) {
			randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		}
		final List<Point> listePts = listeP.get(randomCourbe);
		Point first = listePts.get(i);
		Point last = listePts.get(listePts.size() - 1);
		Point realLast = null;
		Point realStart = null;
		RSModel npcMod = null;
		double ratio;
		try {
			npcMod = npc.getModel();
			realLast = new Point(npcMod.getHumanHoverPoint().x + General.random(-range, range) + offsetX,
					npcMod.getHumanHoverPoint().y + General.random(-range, range) + offsetY);
			realStart = Mouse.getPos();

			ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
					/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
		} catch (NullPointerException e) {
			return;
		}
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round(1 / (speed * Math.pow(ratio, 0.5) / 8));
		if (result == 0) {
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = first.x;
		int offY = first.y;
		Point pt = null;
		Point toGo = new Point();
		Point centerPoint = npcMod.getCentrePoint();
		for (i = 0; i < listePts.size(); i += result) {

			if (c != null && c.active()) {
				return;
			}
			if (Game.isUptext(uptext)) {
				break;
			}

			pt = new Point(listePts.get(i));
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;

			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			Mouse.hop(toGo);
			General.sleep(8);
			if (npc != null && npc.isOnScreen() && !Functions.pointsProches(npcMod.getCentrePoint(), centerPoint, 15)) {
				centerPoint = npcMod.getCentrePoint();
				realLast = new Point(npcMod.getHumanHoverPoint().x + General.random(-range, range) + offsetX,
						npcMod.getHumanHoverPoint().y + General.random(-range, range) + offsetY);
				realStart = Mouse.getPos();
				first = new Point(listePts.get(i + 1));
				offX = first.x;
				offY = first.y;
				sX = realStart.x;
				sY = realStart.y;
				angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
				ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
						/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2));
				// result = (int) Math.round(1 / (speed * Math.pow(ratio, 1) /
				// 8));
			}
			if (Functions.pointsProches(Mouse.getPos(), realLast, General.random(2, 6))) {
				break;
			}
		}
	}

	// ****************************************PLAY MOUSE FOLLOW
	// NPC******************************************************
	/**
	 * Suit une tile (tres specifique quand plusieurs meme ids)
	 * 
	 * @param tile
	 * @param tile1x
	 * @param tile1y
	 * @param tile2x
	 * @param tile2y
	 * @param ID
	 * @return
	 * @throws InterruptedException
	 */
	public static void playMouseFollowNPC(RSNPC npc, String uptext, double speed, Condition c) {
		if (c != null && c.active()) {
			return;
		}
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		List<Point> listePts = listeP.get(randomCourbe);
		Point first = listePts.get(0);
		Point last = listePts.get(listePts.size() - 1);
		Point realLast = null;
		Point realStart = null;
		RSModel npcMod = null;
		double ratio;
		try {
			npcMod = npc.getModel();
			realLast = npcMod.getCentrePoint();
			if (!Functions.isOnScreen(realLast)) {
				realLast = npcMod.getHumanHoverPoint();
			}

			realStart = Mouse.getPos();

			ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
					/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
		} catch (NullPointerException e) {
			return;
		}
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((listePts.size()) / (speed * Math.pow(ratio, 1) * listePts.size() / 8));
		if (result == 0) {
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point pt = null;
		Point toGo = new Point();
		for (int i = 0; i < listePts.size(); i += result) {

			if (c != null && c.active()) {
				return;
			}
			// Point de la liste. Moins l'offset pour avoir le vecteur (point de
			// debut).
			pt = listePts.get(i);
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;

			// Rotation du point
			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			Mouse.hop(toGo);
			General.sleep(8);
			if (npc != null && npc.isOnScreen() && !Functions.pointsProches(npcMod.getCentrePoint(), realLast, 5)) {
				playMouseFollowNPC(npc, uptext, speed, c);
				break;
			}

		}
	}

	// ****************************************PLAY MOUSE FOLLOW
	// OBJECT******************************************************
	/**
	 * Suit une tile (tres specifique quand plusieurs meme ids)
	 * 
	 * @param tile
	 * @param tile1x
	 * @param tile1y
	 * @param tile2x
	 * @param tile2y
	 * @param ID
	 * @return
	 * @throws InterruptedException
	 */
	public static void playMouseFollowObject(RSObject npc, String uptext, double speed, Condition c) {
		if (c != null && c.active()) {
			return;
		}
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		List<Point> listePts = listeP.get(randomCourbe);
		Point first = listePts.get(0);
		Point last = listePts.get(listePts.size() - 1);
		Point realLast = null;
		Point realStart = null;
		RSModel npcMod = null;
		double ratio;
		try {
			npcMod = npc.getModel();
			realLast = npcMod.getCentrePoint();
			if (!Functions.isOnScreen(realLast)) {
				realLast = npcMod.getHumanHoverPoint();
			}

			realStart = Mouse.getPos();

			ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
					/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
		} catch (NullPointerException e) {
			return;
		}
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((listePts.size()) / (speed * Math.pow(ratio, 1) * listePts.size() / 8));
		if (result == 0) {
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point pt = null;
		Point toGo = new Point();
		for (int i = 0; i < listePts.size(); i += result) {

			if (c != null && c.active()) {
				return;
			}
			// Point de la liste. Moins l'offset pour avoir le vecteur (point de
			// debut).
			pt = listePts.get(i);
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;

			// Rotation du point
			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			Mouse.hop(toGo);
			General.sleep(8);
			if (npc != null && npc.isOnScreen() && !Functions.pointsProches(npcMod.getCentrePoint(), realLast, 5)) {
				playMouseFollowObject(npc, uptext, speed, c);
				break;
			}

		}
	}

	// Fonctions to
	// call***************************************************************************

	/** Fonction qui retourne un point dans le box **/
	public static java.awt.Point randomPoint(Rectangle rec) {

		java.awt.Point newPoint = new Point();
		if (rec != null) {
			int middleX = (int) (rec.getMinX() + rec.getWidth() / 2);
			int middleY = (int) (rec.getMinY() + rec.getHeight() / 2);

			int randomLocation = General.random(1, positionX.length);

			int deltaX = positionX[randomLocation - 1];
			int deltaY = positionY[randomLocation - 1];

			deltaX = deltaX + Functions.generateRandomInt(-2, 2);
			deltaY = deltaY + Functions.generateRandomInt(-2, 2);

			newPoint.x = (int) (middleX + deltaX * rec.getWidth() / 100);
			newPoint.y = (int) (middleY + deltaY * rec.getHeight() / 100);
		}
		return newPoint;

	}

	// 1: 20
	// 2: 40
	// 3: 60
	// etc
	public static boolean clickNPC(String option, RSNPC[] mod, int distancePixels, boolean justClick) {
		int numberTries = General.random(5, 10);
		int y = 0;
		for (int i = 0; i < numberTries; i++) {
			if (mod.length > 0) {
				int number = (int) General.randomSD(0, mod.length - 1, 0, 1);
				if (Functions.percentageBool(1)) {
					number = General.random(0, mod.length - 1);
				}
				if (mod[number] != null && mod[number].isOnScreen()) {
					Rectangle rec = mod[number].getModel().getEnclosedArea().getBounds();
					if (!rec.contains(Mouse.getPos())) {
						playMouseFollowNPC(mod[number], option, 1, null);
					}
					if (justClick || Game.isUptext(option)) {
						fastClick(1, 1);
						return true;
					} else {
						y = (int) Mouse.getPos().getY();
						fastClick(3, 1);
						long t = Timing.currentTimeMillis();
						if (Functions.percentageBool(70)) {
							Rectangle rectangle = new Rectangle((int) (Mouse.getPos().getX() - 20),
									(int) (Mouse.getPos().getY() + distancePixels * 15 + General.random(-20, 20)), 40,
									20);
							Mouse.move(randomPoint(rectangle));
						}
						while (!ChooseOption.isOpen()
								&& (Timing.currentTimeMillis() - t) < General.random(3000, 5000)) {
							SleepJoe.sleepHumanDelay(0.5, 1, 5000);
						}
						if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
							for (RSMenuNode rsmenu : ChooseOption.getMenuNodes()) {
								General.println(rsmenu.getAction());
								if (rsmenu.contains(option)) {
									if (!rsmenu.getArea().contains(Mouse.getPos())) {
										int mspeed = Mouse.getSpeed();
										Mouse.setSpeed(30);
										Mouse.move(randomPoint(rsmenu.getArea()));
										Mouse.setSpeed(mspeed);
									}
									if (rsmenu.getArea().contains(Mouse.getPos())) {
										fastClick(1, 1);
										return true;
									}
								}
							}
						}
					}
				}
			}
			Mouse.move(new Point((int) (Mouse.getPos().getX() + General.random(-40, 40)), y - General.random(20, 100)));
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		return false;
	}

	public static void rightClickNPC(String option, RSNPC[] mod) {
		if (mod.length > 0) {
			int number = (int) General.randomSD(0, mod.length - 1, 0, 1.2);
			if (Functions.percentageBool(1)) {
				number = General.random(0, mod.length - 1);
			}
			if (mod[number] != null) {
				Mouse.move(randomPoint(mod[number].getModel().getEnclosedArea().getBounds()));
				Mouse.click(3);
			}
		}
	}

	public static boolean clickObj(String option, RSObject[] mod, int distancePixels, boolean justClick) {
		int numberTries = General.random(5, 10);
		int y = 0;
		for (int i = 0; i < numberTries; i++) {
			if (mod.length > 0) {
				int number = (int) General.randomSD(0, mod.length - 1, 0, 1);
				if (Functions.percentageBool(1)) {
					number = General.random(0, mod.length - 1);
				}
				if (mod[number] != null && mod[number].isOnScreen()) {
					Rectangle rec = mod[number].getModel().getEnclosedArea().getBounds();
					if (!rec.contains(Mouse.getPos())) {
						playMouseFollowObject(mod[number], option, 1, null);
					}
					if (justClick || Game.isUptext(option)) {
						fastClick(1, 1);
						return true;
					} else {
						y = (int) Mouse.getPos().getY();
						fastClick(3, 1);
						long t = Timing.currentTimeMillis();
						if (Functions.percentageBool(70)) {
							Rectangle rectangle = new Rectangle((int) (Mouse.getPos().getX() - 20),
									(int) (Mouse.getPos().getY() + distancePixels * 15 + General.random(-20, 20)), 40,
									20);
							Mouse.move(randomPoint(rectangle));
						}
						while (!ChooseOption.isOpen()
								&& (Timing.currentTimeMillis() - t) < General.random(3000, 5000)) {
							SleepJoe.sleepHumanDelay(0.5, 1, 5000);
						}
						if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
							for (RSMenuNode rsmenu : ChooseOption.getMenuNodes()) {
								General.println(rsmenu.getAction());
								if (rsmenu.contains(option)) {
									if (!rsmenu.getArea().contains(Mouse.getPos())) {
										int mspeed = Mouse.getSpeed();
										Mouse.setSpeed(30);
										Mouse.move(randomPoint(rsmenu.getArea()));
										Mouse.setSpeed(mspeed);
									}
									if (rsmenu.getArea().contains(Mouse.getPos())) {
										fastClick(1, 1);
										return true;
									}
								}
							}
						}
					}
				}
			}
			Mouse.move(new Point((int) (Mouse.getPos().getX() + General.random(-40, 40)), y - General.random(20, 100)));
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		return false;
	}

	public static boolean clickItem(String option, RSItem[] item, int distancePixels, boolean justClick) {
		int numberTries = General.random(5, 10);
		int y = 0;
		for (int i = 0; i < numberTries; i++) {
			if (item.length > 0) {
				int number = (int) General.randomSD(0, item.length - 1, 0, 1);
				if (Functions.percentageBool(1)) {
					number = General.random(0, item.length - 1);
				}
				if (item[number] != null) {
					Rectangle rec = item[number].getArea();
					if (!rec.contains(Mouse.getPos())) {
						Mouse.move(randomPoint(rec));
					}
					if (justClick || Game.isUptext(option)) {
						fastClick(1, 1);
						return true;
					} else {
						y = (int) Mouse.getPos().getY();
						fastClick(3, 1);
						long t = Timing.currentTimeMillis();
						if (Functions.percentageBool(70)) {
							Rectangle rectangle = new Rectangle((int) (Mouse.getPos().getX() - 20),
									(int) (Mouse.getPos().getY() + distancePixels * 15 + General.random(-20, 20)), 40,
									20);
							Mouse.move(randomPoint(rectangle));
						}
						while (!ChooseOption.isOpen()
								&& (Timing.currentTimeMillis() - t) < General.random(3000, 5000)) {
							SleepJoe.sleepHumanDelay(0.5, 1, 5000);
						}
						if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
							for (RSMenuNode rsmenu : ChooseOption.getMenuNodes()) {
								General.println(rsmenu.getAction());
								if (rsmenu.contains(option)) {
									if (!rsmenu.getArea().contains(Mouse.getPos())) {
										int mspeed = Mouse.getSpeed();
										Mouse.setSpeed(30);
										Mouse.move(randomPoint(rsmenu.getArea()));
										Mouse.setSpeed(mspeed);
									}
									if (rsmenu.getArea().contains(Mouse.getPos())) {
										fastClick(1, 1);
										return true;
									}
								}
							}
						}
					}
				}
			}
			Mouse.move(new Point((int) (Mouse.getPos().getX() + General.random(-40, 40)), y - General.random(20, 100)));
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		return false;
	}

	public static void depositAll() {
		if (Functions.percentageBool(PERCENT_TRIBOT_DEPOSITALL)) {
			Banking.depositAll();
		} else {
			MouseJoe.humanMouseMove(new TBox(431, 300, 456, 325), Functions.randomDouble(1.0, 2.0));
			if (Game.isUptext("Deposit inventory")) {
				MouseJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			}
		}
	}

	public static void depositAllExcept(String... names) {
		RSItem[] items = Inventory.getAll();
		ArrayList<String> listeIDs = new ArrayList<String>();
		for (RSItem it : items) {
			if (!listeIDs.contains(it.getDefinition().getName())
					&& !stringContainsItemFromList(it.getDefinition().getName(), names)) {
				listeIDs.add(it.getDefinition().getName());
			}
		}
		for (String id : listeIDs) {
			RSItem[] inv = Inventory.find(id);
			if (inv.length > 0) {
				int number = General.randomSD(0, inv.length - 1, 0, 1);
				if (Functions.percentageBool(1)) {
					number = General.random(0, inv.length - 1);
				}
				if (inv[number] != null) {
					if (!Game.isUptext(inv[0].getDefinition().getName())) {
						Mouse.move(randomPoint(inv[number].getArea()));
					}
					inv[number].click("Deposit-All");
				}
			}
		}
	}

	public static boolean clickClickable(String option, RSInterface inter, int distancePixels, boolean justClick) {
		int numberTries = General.random(5, 10);
		int y = 0;
		for (int i = 0; i < numberTries; i++) {

			if (inter != null) {
				Rectangle rec = inter.getAbsoluteBounds();
				if (!rec.contains(Mouse.getPos())) {
					Mouse.move(randomPoint(rec));
				}
				if (justClick || Game.isUptext(option)) {
					fastClick(1, 1);
					return true;
				} else {
					y = (int) Mouse.getPos().getY();
					fastClick(3, 1);
					long t = Timing.currentTimeMillis();
					if (Functions.percentageBool(70)) {
						Rectangle rectangle = new Rectangle((int) (Mouse.getPos().getX() - 20),
								(int) (Mouse.getPos().getY() + distancePixels * 15 + General.random(-20, 20)), 40, 20);
						Mouse.move(randomPoint(rectangle));
					}
					while (!ChooseOption.isOpen() && (Timing.currentTimeMillis() - t) < General.random(3000, 5000)) {
						SleepJoe.sleepHumanDelay(0.5, 1, 5000);
					}
					if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
						for (RSMenuNode rsmenu : ChooseOption.getMenuNodes()) {
							General.println(rsmenu.getAction());
							if (rsmenu.contains(option)) {
								if (!rsmenu.getArea().contains(Mouse.getPos())) {
									int mspeed = Mouse.getSpeed();
									Mouse.setSpeed(30);
									Mouse.move(randomPoint(rsmenu.getArea()));
									Mouse.setSpeed(mspeed);
								}
								if (rsmenu.getArea().contains(Mouse.getPos())) {
									fastClick(1, 1);
									return true;
								}
							}
						}
					}

				}
			}
			Mouse.move(new Point((int) (Mouse.getPos().getX() + General.random(-40, 40)), y - General.random(20, 100)));
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		return false;
	}

	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
		return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
	}

}
