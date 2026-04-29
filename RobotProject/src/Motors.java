package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Motors {
    
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);//create motors
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
    double baseSpeed = 270;//create distance both motors 270 is enough speed
    double correction = 0;//this gonna use for pid system

    public void forward(){
        leftMotor.setSpeed(360);   // Set to 360 for real deal //these were used before PID now it used for obstacle avoidence
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
        rightMotor.setSpeed(360);   // Set to 360 for real deal // we dont use these any more it used before we implemnt the PID
        leftMotor.setSpeed(135);    // Set to 135 for real deal
        rightMotor.forward();
        leftMotor.forward();
    }
    public void turnRight(){
        leftMotor.setSpeed(360);    // Set to 360 for real deal // we dont use it now
        rightMotor.setSpeed(135);   // Set to 135 for real deal
        rightMotor.forward();
        leftMotor.forward();
    }
    public void pidDrive(){
        double leftSpeed = baseSpeed + correction; //this part is done by both imalka and emil together
        double rightSpeed = baseSpeed - correction;

        leftSpeed = Math.max(0, Math.min(360, leftSpeed));
        rightSpeed = Math.max(0, Math.min(360, rightSpeed));
        
        leftMotor.setSpeed((int)leftSpeed); //this requires int value it gives errors 
        rightMotor.setSpeed((int)rightSpeed);
        leftMotor.forward();
        rightMotor.forward();
    }
}
