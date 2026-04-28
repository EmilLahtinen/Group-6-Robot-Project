package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Motors {
    
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

    public void forward(){
        leftMotor.setSpeed(360);   // Set to 360 for real deal
        rightMotor.setSpeed(360);   // Set to 360 for real deal
        leftMotor.forward();
        rightMotor.forward();
    }
    public void stop(){
        leftMotor.stop();
        rightMotor.stop();
    }
    public void obstacleTurnLeft(){
        rightMotor.setSpeed(90);
        leftMotor.setSpeed(90);
        rightMotor.forward();
        leftMotor.backward();
    }
    public void obstacleTurnRight(){
        leftMotor.setSpeed(90);
        rightMotor.setSpeed(90);
        rightMotor.backward();
        leftMotor.forward();
    }
    public void turnLeft(){
        rightMotor.setSpeed(360);   // Set to 360 for real deal
        leftMotor.setSpeed(135);    // Set to 135 for real deal
        rightMotor.forward();
        leftMotor.forward();
    }
    public void turnRight(){
        leftMotor.setSpeed(360);    // Set to 360 for real deal
        rightMotor.setSpeed(135);   // Set to 135 for real deal
        rightMotor.forward();
        leftMotor.forward();
    }
}
