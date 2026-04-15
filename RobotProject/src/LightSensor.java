package src;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class LightSensor {

    public static void main(String[] args)
    {
        EV3ColorSensor colorSensor  = new EV3ColorSensor(SensorPort.S3); //  Create color sensor object on EV3 sensor port S3
        SampleProvider light        = colorSensor.getAmbientMode(); //Set sensor mode to measure ambient light (general brightness)
        
        // Create an array to hold the sensor data
        float[] sample = new float[light.sampleSize()];

        // Continuously display the light intensity until a button is pressed
        while (!Button.ESCAPE.isDown())                 // Exit if the ESCAPE button is pressed
        {
            // Get the current light intensity reading from the sensor
            light.fetchSample(sample, 0);               // 0 is the index where data will be stored
            SharedData.Intensity = sample[0];

            // Display the light intensity value on the LCD screen
            LCD.clear();
            LCD.drawString("Light Intensity: " + (int)(sample[0] * 100) + "%", 0, 0);  // Display as percentage

            //Small delay so values don’t update too fast
            try 
            {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        colorSensor.close();
    }
}
