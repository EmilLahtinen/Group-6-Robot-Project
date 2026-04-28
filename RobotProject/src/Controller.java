package src;

import lejos.hardware.Button;
import lejos.utility.Delay;

public class Controller {
    public static void main(String[] args) {

        Motors motors = new Motors();
        LightSensor ls = new LightSensor();
        UltrasonicSensor uss = new UltrasonicSensor();
        boolean avoiding = false;
        boolean avoidingLeft = false;
        boolean avoidingRight = false;
        boolean measuringLeft = false;
        boolean measuringRight = false;
        boolean measuringStartRight = false;
        boolean measuringComplete = false;
        boolean firstCheck = false;
        boolean maxTimeStarted = false;
        boolean abort = false;
        int avoidStep = 0;

        long measureStart = 0;
        long obstacleTimeLeft = 0;
        long obstacleTimeRight = 0;
        long maxTime = 0;

        Thread lsThread = new Thread(ls);
        Thread usThread = new Thread(uss);

        lsThread.setPriority(8);
        usThread.setPriority(3);
        lsThread.start();
        usThread.start();

        //Start delay. Needed or the robot will start moving before the sensors activate
        Delay.msDelay(8000);
        motors.forward();
        
        while (!Button.ESCAPE.isDown()){
            //Obstacle detection
            if(SharedData.distance <= 0.15 && avoiding == false){SharedData.obstacle = true; measuringLeft = true;}
            else{SharedData.obstacle = false;}

            //Obstacle avoidance: measure left
           if(avoiding == false && measuringLeft == true){
                avoiding = true;
                usThread.setPriority(8);
                lsThread.setPriority(2);
                motors.obstacleTurnLeft();
                measureStart = System.currentTimeMillis();
           }

           if(SharedData.distance >= 0.6 && measuringLeft == true && firstCheck == false){
            firstCheck = true;
            Delay.msDelay(10);
           }
           if(SharedData.distance < 0.6 && measuringLeft == true && firstCheck == true){
            firstCheck = false;
           }

           if(SharedData.distance >= 0.6 && measuringLeft == true && firstCheck == true){
                    obstacleTimeLeft = System.currentTimeMillis() - measureStart;
                    measuringStartRight = true;
                    measuringLeft = false;
                    firstCheck = false;
            }
            
            //Measure right
            if(measuringStartRight == true && avoiding == true){
                motors.obstacleTurnRight();
                measuringStartRight = false;
                Delay.msDelay(obstacleTimeLeft);
                measureStart = System.currentTimeMillis();
                measuringRight = true;
                }
                    
            if(SharedData.distance >= 0.6 && measuringRight == true && measuringStartRight == false && firstCheck == false){
                firstCheck = true;
                Delay.msDelay(10);
            }
            if(SharedData.distance < 0.6 && measuringRight == true && measuringStartRight == false && firstCheck == true){
                firstCheck = false;
            }

            if(SharedData.distance >= 0.6 && measuringRight == true && measuringStartRight == false && firstCheck == true){
                obstacleTimeRight = System.currentTimeMillis() - measureStart;
                measuringRight = false;
                measuringLeft = false;
                measuringComplete = true;
                firstCheck = false;
            }
                        
            //Choose avoidance direction
            if(avoiding == true && measuringComplete == true && avoidStep == 0 && obstacleTimeLeft >= obstacleTimeRight){
                avoidingRight = true;
                avoidStep = 1;
            }   
            else if(avoiding == true && measuringComplete == true && avoidStep == 0 && obstacleTimeLeft < obstacleTimeRight){
                avoidingLeft = true;
                avoidStep = 1;
            }
            //Going right around obstacle, step 1
            if(avoidingRight == true && avoidStep == 1){
                motors.obstacleTurnRight();
                Delay.msDelay(500);
                motors.forward();
                Delay.msDelay(2000);
                motors.obstacleTurnLeft();
                Delay.msDelay(obstacleTimeRight + 500);
                avoidStep = 2; 
                if(maxTimeStarted == false){
                        maxTimeStarted = true;
                        maxTime = System.currentTimeMillis() + 1000;
                    }
            }
            //Going right around obstacle, step 2
            if(avoidingRight == true && avoidStep == 2){
                if(SharedData.distance < 0.7 && firstCheck == false){
                    firstCheck = true;
                    Delay.msDelay(10);
                }
                if(SharedData.distance > 0.7 && firstCheck == true){
                    firstCheck = false;
                }
                if((SharedData.distance < 0.7 && firstCheck == true) || (System.currentTimeMillis()>=maxTime && maxTimeStarted == true)){
                    avoidStep = 3;
                    maxTimeStarted = false;
                    firstCheck = false;
                }
                
            }
            //Going right around obstacle, step 3
            if(avoidingRight == true && avoidStep ==3){
                motors.obstacleTurnRight();
                if(SharedData.distance > 0.8){
                    Delay.msDelay(500);
                    motors.forward();
                    for (int i = 0; i < 200; i++) {
                        if (SharedData.lineDetected == true) {
                            abort = true;
                            break;
                        }
                            Delay.msDelay(10);
                            if(SharedData.intensity < 0.08){  //Set intensity to 0.015? for the real deal
                                SharedData.lineDetected = true;
                            }
                    }
                    motors.obstacleTurnLeft();
                    if(maxTimeStarted == false){
                        maxTimeStarted = true;
                        maxTime = System.currentTimeMillis() + 3000;
                    }
                avoidStep = 2;
                }
            }
            //Line detection abort
            if (abort == true && avoidingRight == true) {
                avoiding = false;
                avoidingLeft = false;
                avoidStep = 0;
                SharedData.lineDetected = false;
                motors.obstacleTurnRight();
                if(SharedData.intensity < 0.08){ //Make intensities 0.015? for the real deal
                    firstCheck = true;
                }
                if(SharedData.intensity >= 0.08 && abort == true && firstCheck == true){
                    abort = false;
                    avoidingRight = false;
                    avoiding = false;
                    avoidingLeft = false;
                    avoidStep = 0;
                    obstacleTimeLeft = 0;
                    obstacleTimeRight = 0;
                    measuringComplete = false;
                    continue;
                }
            }

            //Going left around obstacle, step 1
            if(avoidingLeft == true && avoidStep == 1){
                motors.obstacleTurnLeft();
                Delay.msDelay(obstacleTimeRight + obstacleTimeLeft + 500);
                motors.forward();
                Delay.msDelay(2000);
                motors.obstacleTurnRight();
                Delay.msDelay(obstacleTimeLeft + 500);
                avoidStep = 2;
                if(maxTimeStarted == false){
                        maxTimeStarted = true;
                        maxTime = System.currentTimeMillis() + 1000;
                    }
            }
            //Going left around obstacle, step 2
            if(avoidingLeft == true && avoidStep == 2){
                if(SharedData.distance < 0.7 && firstCheck == false){
                    firstCheck = true;
                    Delay.msDelay(10);
                }
                if(SharedData.distance > 0.7 && firstCheck == true){
                    firstCheck = false;
                }
                if((SharedData.distance < 0.7 && firstCheck == true) || (System.currentTimeMillis()>=maxTime && maxTimeStarted == true)){
                    avoidStep = 3;
                    maxTimeStarted = false;
                    firstCheck = false;
                }
            }
            //Going left around obstacle, step 3
            if(avoidingLeft == true && avoidStep ==3){
                motors.obstacleTurnLeft();
                if(SharedData.distance > 0.8){
                    Delay.msDelay(500);
                    motors.forward();
                    for (int i = 0; i < 200; i++) {
                        if (SharedData.lineDetected == true) {
                            abort = true;
                            break;
                        }
                            Delay.msDelay(10);
                            if(SharedData.intensity < 0.08){  //Set intensity to 0.015? for the real deal
                                SharedData.lineDetected = true;
                            }
                    }
                    motors.obstacleTurnRight();
                    if(maxTimeStarted == false){
                        maxTimeStarted = true;
                        maxTime = System.currentTimeMillis() + 3000;
                    }
                avoidStep = 2;
                }
            }
            //Line detection abort
            if (abort == true && avoidingLeft == true) {
                avoiding = false;
                avoidingRight = false;
                avoidStep = 0;
                SharedData.lineDetected = false;
                motors.obstacleTurnLeft();
                if(SharedData.intensity < 0.08 && abort == true){ //Make intensity 0.015? for the real deal
                    abort = false;
                    avoidingLeft = false;
                    avoiding = false;
                    avoidingRight = false;
                    avoidStep = 0;
                    obstacleTimeLeft = 0;
                    obstacleTimeRight = 0;
                    measuringComplete = false;
                    continue;
                }
            }

            //Line following
            if(avoiding == false && abort == false){ 
            usThread.setPriority(3);
            lsThread.setPriority(8);
            }
            //Make intensities 0.015? for real deal
            if(SharedData.intensity >= 0.08 && avoiding == false && abort == false){
                motors.turnLeft();
            }
            if ((SharedData.intensity < 0.08 && avoiding == false && abort == false)){
                motors.turnRight();
            }
        }
    }
}

    
