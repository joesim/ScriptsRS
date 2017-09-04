package scripts.utilities;

import java.awt.Point;
 
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
 
public class MiddleMouseCamera {
        /*
         * @Author: VBsec
         *
         * @Description: 3 self-explanatory methods for moving camera with middle
         * mouse at custom speeds.
         *
         * @Instructions: In Eclipse: Type "MiddleMouseCamera." in your script and
         * press ctrl+space and pick one of the 3 methods from the drop-down menu.
         * Input then the required angle and/or rotation and speed. The parameter
         * speed sets the mouse speed at the given value and caps it at 300. No
         * initial angle or rotation checks are required.
         */
        private static int DIRECTION = 1, PIXELS_TO_MOVE, RANDOM_ADDITION = General
                        .random(-2, 2), POINT_X, POINT_Y, START_POINT_X, START_POINT_Y,
                        END_POINT_X, END_POINT_Y, SUBTRACTION, DEGREES_TO_MOVE = 0,
                        CURRENT_ANGLE, CURRENT_ROTATION, Y_DIRECTION, X_DIRECTION,
                        X_TRANSITION, Y_TRANSITION;
        private static Point CLICK_POINT, RELEASE_POINT;
 
        private static boolean isBetween(int value, int min, int max) {
                return ((value > min) && (value < max));
        }
 
        private static void moveMouse() {
                Mouse.move(CLICK_POINT);
                General.sleep(50, 80);
                Mouse.sendPress(CLICK_POINT, 2);
                General.sleep(180, 210);
                Mouse.move(RELEASE_POINT);
                General.sleep(50, 80);
                Mouse.sendRelease(RELEASE_POINT, 2);
                General.sleep(180, 210);
        }
 
        public static void setCamera(int angle, int rotation, int speed) {
                CURRENT_ANGLE = Camera.getCameraAngle();
                CURRENT_ROTATION = Camera.getCameraRotation();
                if (speed > 300)
                        speed = 300;
                Mouse.setSpeed(speed + RANDOM_ADDITION);
                int failCount = 0;
                while (!isBetween(CURRENT_ANGLE, angle - 7, angle + 7) && failCount < 3
                                || !isBetween(CURRENT_ROTATION, rotation - 7, rotation + 7)
                                && failCount < 3) {
                        getAngle(angle);
                        getRotation(rotation);
                        if (X_DIRECTION == 1) {
                                START_POINT_X = 755 + RANDOM_ADDITION;
                                END_POINT_X = START_POINT_X - X_TRANSITION;
                        } else {
                                START_POINT_X = 10 + RANDOM_ADDITION;
                                END_POINT_X = START_POINT_X + X_TRANSITION;
                        }
                        if (Y_DIRECTION == 1) {
                                START_POINT_Y = 400 + RANDOM_ADDITION;
                                END_POINT_Y = START_POINT_Y - Y_TRANSITION;
                        } else {
                                START_POINT_Y = 50 + RANDOM_ADDITION;
                                END_POINT_Y = START_POINT_Y + Y_TRANSITION;
                        }
 
                        CLICK_POINT = new Point(START_POINT_X, START_POINT_Y);
                        RELEASE_POINT = new Point(END_POINT_X, END_POINT_Y);
                        moveMouse();
                        CURRENT_ANGLE = Camera.getCameraAngle();
                        CURRENT_ROTATION = Camera.getCameraRotation();
                        failCount++;
                }
        }
 
        private static void getAngle(int angle) {
                final int PIXEL_TO_ANGLE_RATIO = 2;
                CURRENT_ANGLE = Camera.getCameraAngle();
                SUBTRACTION = CURRENT_ANGLE - angle;
 
                if (SUBTRACTION < 0) {
                        DIRECTION = -1;
                        DEGREES_TO_MOVE = SUBTRACTION * -1;
                } else {
                        DIRECTION = 1;
                        DEGREES_TO_MOVE = SUBTRACTION;
                }
                PIXELS_TO_MOVE = DEGREES_TO_MOVE * PIXEL_TO_ANGLE_RATIO;
                Y_TRANSITION = PIXELS_TO_MOVE;
                if (DIRECTION == 1) {
                        START_POINT_Y = 470 + RANDOM_ADDITION;
                        END_POINT_Y = START_POINT_Y - PIXELS_TO_MOVE;
                        Y_DIRECTION = 1;
                } else {
                        START_POINT_Y = 30 + RANDOM_ADDITION;
                        END_POINT_Y = START_POINT_Y + PIXELS_TO_MOVE;
                        Y_DIRECTION = -1;
                }
        }
 
        public static void setCameraAngle(int angle, int speed) {
                CURRENT_ANGLE = Camera.getCameraAngle();
                POINT_X = General.random(50, 550);
                if (speed > 300)
                        speed = 300;
                Mouse.setSpeed(speed + RANDOM_ADDITION);
                int failCount = 0;
                while (!isBetween(CURRENT_ANGLE, angle - 7, angle + 7) && failCount < 3) {
                        getAngle(angle);
                        CLICK_POINT = new Point(POINT_X, START_POINT_Y);
                        RELEASE_POINT = new Point(POINT_X, END_POINT_Y);
                        moveMouse();
                        CURRENT_ANGLE = Camera.getCameraAngle();
                        if (CURRENT_ANGLE < 40 && DIRECTION == 1)
                                break;
                        failCount++;
                }
 
        }
 
        private static void getRotation(int rotation) {
                SUBTRACTION = CURRENT_ROTATION - rotation;
                double PIXEL_TO_ROTATION_RATIO = 2.83;
 
                if (isBetween(SUBTRACTION, -180, 0)) {
                        DEGREES_TO_MOVE = SUBTRACTION * -1;
                        DIRECTION = 1;
                } else if (isBetween(SUBTRACTION, -360, -180)) {
                        DEGREES_TO_MOVE = 360 + SUBTRACTION;
                        DIRECTION = -1;
                } else if (isBetween(SUBTRACTION, 0, 180)) {
                        DEGREES_TO_MOVE = SUBTRACTION;
                        DIRECTION = -1;
                } else if (isBetween(SUBTRACTION, 180, 360)) {
                        DEGREES_TO_MOVE = 360 - SUBTRACTION;
                        DIRECTION = 1;
                }
                PIXELS_TO_MOVE = (int) (DEGREES_TO_MOVE * PIXEL_TO_ROTATION_RATIO);
                X_TRANSITION = PIXELS_TO_MOVE;
                if (DIRECTION == 1) {
                        START_POINT_X = 735 + RANDOM_ADDITION;
                        END_POINT_X = START_POINT_X - PIXELS_TO_MOVE;
                        X_DIRECTION = 1;
                } else {
                        START_POINT_X = 30 + RANDOM_ADDITION;
                        END_POINT_X = START_POINT_X + PIXELS_TO_MOVE;
                        X_DIRECTION = -1;
                }
        }
 
        public static void setCameraRotation(int rotation, int speed) {
                CURRENT_ROTATION = Camera.getCameraRotation();
                POINT_Y = General.random(90, 259);
                if (speed > 300)
                        speed = 300;
                Mouse.setSpeed(speed + RANDOM_ADDITION);
                int failCount = 0;
                while (!isBetween(CURRENT_ROTATION, rotation - 7, rotation + 7)
                                && failCount < 3) {
                        SUBTRACTION = CURRENT_ROTATION - rotation;
                        POINT_Y = General.random(90, 259);
                        getRotation(rotation);
 
                        CLICK_POINT = new Point(START_POINT_X, POINT_Y);
                        RELEASE_POINT = new Point(END_POINT_X, POINT_Y);
                        moveMouse();
                        CURRENT_ROTATION = Camera.getCameraRotation();
                        failCount++;
                }
        }
}
