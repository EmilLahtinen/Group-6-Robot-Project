package src;

import lejos.utility.Delay;

public class OwnMotorTest {

    public static void main(String[] args) {

        //EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        //EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

        Motors motors = new Motors();

        motors.forward();
        Delay.msDelay(1000);
        motors.obstacleTurnLeft();
        Delay.msDelay(2000);
        motors.obstacleTurnRight();
        Delay.msDelay(2000);
        motors.forward();
        Delay.msDelay(1000);
        motors.stop();
        
        //leftMotor.close();
        //rightMotor.close();
    }
}
