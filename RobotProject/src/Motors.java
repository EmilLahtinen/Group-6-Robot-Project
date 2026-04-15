package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Motors {
    
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C); //Create motor objects connected to EV3 ports
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

    //Move both motors forward
    public void forward(){
        leftMotor.setSpeed(360);   // degrees per second
        rightMotor.setSpeed(360);
     
    //start spinning forward   
        leftMotor.forward();
        rightMotor.forward();
    }
   
    //stop both motors immidiatly
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
