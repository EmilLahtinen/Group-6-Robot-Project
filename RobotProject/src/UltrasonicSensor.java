//this part has done by imalka
package src;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;   // allows the sensor to return the samples or data
                                        // e.g., for getting distance data from sonic sensor etc

public class UltrasonicSensor implements Runnable {
//we use runnable because the US must continuely keep cheking the distance while robot is moving and follow the line avoiding obstcles
  //with out runnable programe might get stuck


    @Override
    public void run(){

        // Creating an instance of US sensor at port 2

        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        
        // Get the distance sample provider
        SampleProvider distance = ultrasonicSensor.getDistanceMode();
        
        // Create a sample array to hold the distance value
        // even though sonic sensor gives distance as an o/p, but since other sensors, e.g., light sensor
        // can provide multiple values, therefore to keep consistency, I'm using sampleprovider
        float[] sample = new float[distance.sampleSize()];

        // Keep displaying the distance, until user presses a button
        while (!Button.ESCAPE.isDown())
        {
            // Get the curRent distnce reading from the US sensor
            distance.fetchSample(sample, 0);
            SharedData.distance = sample[0]; ///* Save distance into SharedData
             ///so Controller.java can use it
             //for obstacle detection
            
           
            
            // Display the distance on the LCD screen for debugging
            LCD.clear();
            LCD.drawString("Distance: " + SharedData.distance, 0, 1);
            
            // Refresh display every 10ms
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        
        // Close US sensor
        ultrasonicSensor.close();
    }

}
