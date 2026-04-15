package src;

import lejos.utility.Delay;

public class OwnMotorTest {

    public void main(String[] args) {

        //EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        //EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

        Motors motors = new Motors();

        motors.forward();
        Delay.msDelay(2000);
        motors.turnLeft();
        Delay.msDelay(2000);
        motors.turnRight();
        Delay.msDelay(2000);
        motors.forward();
        motors.stop();
        
        //leftMotor.close();
        //rightMotor.close();
    }
}
