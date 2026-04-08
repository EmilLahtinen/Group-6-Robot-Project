package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorld {
    public static void main(String[] args) {

        LCD.clear();
        LCD.drawString("Welcome", 0, 0);
        Delay.msDelay(1000);

        LCD.clear();
        LCD.drawString("This is my 1st", 0, 0);
        LCD.drawString("LEGO code.", 0, 1);
        Delay.msDelay(2000);

        LCD.clear();
        LCD.drawString("Make me", 0, 0);
        LCD.drawString("autonomous", 0, 1);

        LCD.drawString("Press any btn", 0, 3);

        Button.waitForAnyPress();
    }
}
