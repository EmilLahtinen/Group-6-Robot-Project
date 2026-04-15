package src;

import lejos.hardware.Button;

public class Controller {
    public static void main(String[] args) {

        Motors motors = new Motors();
        LightSensor ls = new LightSensor();
        //UltrasonicSensor uss = new UltrasonicSensor();

        Thread lsThread = new Thread(ls);
        //Thread usThread = new Thread(uss);

        lsThread.setPriority(8);
        lsThread.start();

        motors.forward();

        while (!Button.ESCAPE.isDown()){
            if(SharedData.Intensity >= 0.03){
                motors.turnLeft();
            }
            else{motors.forward();}
            if (SharedData.Intensity <= 0.01){
                motors.turnRight();
            }
            else{motors.forward();}
            
        }
    }
}

