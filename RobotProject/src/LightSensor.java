package src;
 /*
     * This class runs as a separate thread.
     * Its job is to continuously read light intensity
     * from the EV3 color sensor and store it in SharedData
     * so the Controller class can use it for line following.
     */
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class LightSensor implements Runnable{



    @Override
    public void run(){

            EV3ColorSensor colorSensor  = new EV3ColorSensor(SensorPort.S3);
            SampleProvider light        = colorSensor.getAmbientMode();
        
            // Create an array to hold the sensor data
            float[] sample = new float[light.sampleSize()];

            // Continuously display the light intensity until a button is pressed
            while (!Button.ESCAPE.isDown())                 // Exit if the ESCAPE button is pressed
            {
                // Get the current light intensity reading from the sensor
                light.fetchSample(sample, 0);               // 0 is the index where data will be stored
                SharedData.intensity = (float)sample[0];

                // Display the light intensity value on the LCD screen
                LCD.clear();
                LCD.drawString("Intensity: " + (float)(SharedData.intensity), 0, 0);
            
                try 
                {
                    Thread.sleep(10); //sleep for 10s
            }catch (InterruptedException e) {
                    e.printStackTrace(); //print error if thread gets interrupted
                }
            }
        colorSensor.close();
    }

}
//this part is done by imalka and debugged emil
