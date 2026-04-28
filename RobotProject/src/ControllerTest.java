package src;

import lejos.hardware.Button;
import lejos.utility.Delay;

public class ControllerTest {

    // Better than hardcoded values everywhere
    private static final float OBSTACLE_DISTANCE = 0.15f;   // 15 cm
    private static final float CLEAR_DISTANCE = 0.60f;      // 60 cm
    private static final float LINE_THRESHOLD = 0.02f;      // black line threshold

    public static void main(String[] args) {

        Motors motors = new Motors();
        LightSensor ls = new LightSensor();
        UltrasonicSensor uss = new UltrasonicSensor();

        Thread lsThread = new Thread(ls);
        Thread usThread = new Thread(uss);

        // Light sensor should have higher priority during line following
        lsThread.setPriority(8);
        usThread.setPriority(3);

        lsThread.start();
        usThread.start();

        // Start delay
        Delay.msDelay(8000);

        while (!Button.ESCAPE.isDown()) {

            // ---------- NORMAL LINE FOLLOWING ----------
            if (!SharedData.obstacle) {

                if (SharedData.distance <= OBSTACLE_DISTANCE) {
                    SharedData.obstacle = true;
                    motors.stop();
                    avoidObstacle(motors);
                    SharedData.obstacle = false;
                }
                else {
                    followLine(motors);
                }
            }

            Delay.msDelay(50);
        }

        motors.stop();
    }


    // =====================================================
    // LINE FOLLOWING
    // =====================================================

    public static void followLine(Motors motors) {

        if (SharedData.intensity >= LINE_THRESHOLD) {
            motors.turnLeft();
        }
        else {
            motors.turnRight();
        }
    }


    // =====================================================
    // SMART OBSTACLE AVOIDANCE
    // =====================================================

    public static void avoidObstacle(Motors motors) {

        float leftDistance;
        float rightDistance;


        // Step 2: Check LEFT side
        motors.obstacleTurnLeft();
        Delay.msDelay(500);
        motors.stop();
        Delay.msDelay(200);

        leftDistance = SharedData.distance;

        // Return center
        motors.obstacleTurnRight();
        Delay.msDelay(500);
        motors.stop();

        // Step 3: Check RIGHT side
        motors.obstacleTurnRight();
        Delay.msDelay(500);
        motors.stop();
        Delay.msDelay(200);

        rightDistance = SharedData.distance;

        // Return center
        motors.obstacleTurnLeft();
        Delay.msDelay(500);
        motors.stop();

        // Step 4: Choose better direction
        if (leftDistance > rightDistance) {
            goAroundLeft(motors);
        }
        else {
            goAroundRight(motors);
        }

        // Step 5: Smart line recovery
        findLine(motors);
    }


    // =====================================================
    // GO AROUND LEFT
    // =====================================================

    public static void goAroundLeft(Motors motors) {

        motors.obstacleTurnLeft();
        Delay.msDelay(500);

        motors.forward();
        Delay.msDelay(1500);

        motors.obstacleTurnRight();
        Delay.msDelay(700);

        motors.forward();
        Delay.msDelay(2000);

        motors.obstacleTurnRight();
        Delay.msDelay(700);

        motors.forward();
        Delay.msDelay(1500);

        motors.obstacleTurnLeft();
        Delay.msDelay(500);

        motors.stop();
    }


    // =====================================================
    // GO AROUND RIGHT
    // =====================================================

    public static void goAroundRight(Motors motors) {

        motors.obstacleTurnRight();
        Delay.msDelay(500);

        motors.forward();
        Delay.msDelay(1500);

        motors.obstacleTurnLeft();
        Delay.msDelay(700);

        motors.forward();
        Delay.msDelay(2000);

        motors.obstacleTurnLeft();
        Delay.msDelay(700);

        motors.forward();
        Delay.msDelay(1500);

        motors.obstacleTurnRight();
        Delay.msDelay(500);

        motors.stop();
    }


    // =====================================================
    // SMART LINE REACQUISITION
    // =====================================================

    public static void findLine(Motors motors) {

        while (!Button.ESCAPE.isDown()) {

            // small left sweep
            motors.obstacleTurnLeft();
            Delay.msDelay(250);

            if (isOnLine()) {
                motors.stop();
                return;
            }

            // larger right sweep
            motors.obstacleTurnRight();
            Delay.msDelay(500);

            if (isOnLine()) {
                motors.stop();
                return;
            }

            // return near center
            motors.obstacleTurnLeft();
            Delay.msDelay(250);
        }
    }


    // =====================================================
    // LINE DETECTION
    // =====================================================

    public static boolean isOnLine() {
        return SharedData.intensity < LINE_THRESHOLD;
    }
}

