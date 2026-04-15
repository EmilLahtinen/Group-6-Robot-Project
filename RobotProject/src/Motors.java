package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Motors {
    
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

    public void forward(){
        leftMotor.setSpeed(360);   // degrees per second
        rightMotor.setSpeed(360);
        leftMotor.forward();
        rightMotor.forward();
    }
    public void stop(){
        leftMotor.stop();
        rightMotor.stop();
    }
    public void turnLeft(){
        rightMotor.forward();
        leftMotor.forward();
        rightMotor.setSpeed(360);
        leftMotor.stop();
    }
    public void turnRight(){
        rightMotor.forward();
        leftMotor.forward();
        leftMotor.setSpeed(360);
        rightMotor.stop();
    }
}
