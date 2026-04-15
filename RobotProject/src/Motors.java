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
    public void obstacleTurnLeft(){
        rightMotor.forward();
        leftMotor.backward();
        rightMotor.setSpeed(180);
        leftMotor.setSpeed(180);
    }
    public void obstacleTurnRight(){
        rightMotor.backward();
        leftMotor.forward();
        leftMotor.setSpeed(180);
        rightMotor.setSpeed(180);
    }
    public void turnLeft(){
        rightMotor.forward();
        leftMotor.forward();
        rightMotor.setSpeed(360);
        leftMotor.setSpeed(250);
    }
    public void turnRight(){
        rightMotor.forward();
        leftMotor.forward();
        leftMotor.setSpeed(360);
        rightMotor.setSpeed(250);
    }
}
