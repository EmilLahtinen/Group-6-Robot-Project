package src;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class LightSensor implements Runnable{



    @Override
    public void run(){
            
        // Initialize the EV3 color sensor on sensor port S3
            EV3ColorSensor colorSensor  = new EV3ColorSensor(SensorPort.S3);
            SampleProvider light        = colorSensor.getAmbientMode(); // Set the sensor to ambient light mode (measures reflected/ambient light intensity)
        
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
                    //// Small delay to prevent CPU overload and stabilize readings
                    Thread.sleep(10);
            }catch (InterruptedException e) {
                    // // Print error if thread sleep is interrupted
                    e.printStackTrace();
                }
            }
        colorSensor.close(); // Close the sensor properly to free hardware resources
    }

} //this code is done by imalka modified by Emil
