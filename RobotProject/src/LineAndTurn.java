//this code for testing done by imalka
package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.lcd.LCD;

public class LineAndTurn {
   public static void main(String[] args) {
   
   EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
   EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

    System.out.println("Line and turn.");
    leftMotor.setSpeed(600);
    rightMotor.setSpeed(600);
    leftMotor.forward();
    rightMotor.forward();
    Delay.msDelay(6000L);
    leftMotor.stop(false);
    rightMotor.stop(false);
    Delay.msDelay(500L);

    //Drift correction
    leftMotor.setSpeed(360);
    leftMotor.forward();
    Delay.msDelay(500L);
    leftMotor.stop(false);

    leftMotor.setSpeed(360);
    rightMotor.setSpeed(360);
    leftMotor.forward();
    rightMotor.backward();
    Delay.msDelay(2500L);
    leftMotor.stop(false);
    rightMotor.stop(false);

    LCD.clear();
    System.out.println("Reached halfway point. Returning to base.");
   leftMotor.setSpeed(360);
    rightMotor.setSpeed(360);
    leftMotor.forward();
    rightMotor.backward();
    Delay.msDelay(1000L);
    leftMotor.stop(false);
    rightMotor.stop(false);
    Delay.msDelay(500L);

    leftMotor.setSpeed(600);
    rightMotor.setSpeed(600);
    leftMotor.forward();
    rightMotor.forward();
    Delay.msDelay(6000L);

   leftMotor.stop();
   rightMotor.stop();
   leftMotor.close();
   rightMotor.close();
   }
}
