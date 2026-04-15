//this code is written for test purpose only
package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorTest {
    public static void main(String[] args) {
        //  Create motor objects and connect them to EV3 ports
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C); //MotorPort.C = motor connected to port C
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);//MotorPort.D = motor connected to port D

        System.out.println("motor test starting..."); 

        // Set speed for both motors
        //move motors forward
        leftMotor.setSpeed(360);   // degrees per second
        rightMotor.setSpeed(360);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(2000);       //  Keep moving forward for 2 seconds

        // Move motors backward
        leftMotor.backward();
        rightMotor.backward();
        Delay.msDelay(2000);       //Keep moving backward for 2 seconds 

        // Stop motors
        leftMotor.stop();
        rightMotor.stop();

        // Close motors
        leftMotor.close();
        rightMotor.close();

        System.out.println("test completed. Motors shoulve been working"); //final message after test completes
    }
}
