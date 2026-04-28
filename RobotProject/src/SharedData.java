package src;
// This class is used as a shared memory space between different threads
// It stores sensor data and flags that can be accessed globally
public class SharedData {
    public static volatile float distance = 0; //  volatile ensures all threads always read the latest updated value
    public static volatile float intensity = 0;// Stores light intensity value from the light sensor and volatile ensures real time updates across threads
    public static volatile boolean obstacle = false; // Used for decision-making in robot movement logic
    public static volatile boolean lineDetected = false;   // Used in line following behavior
}
//this code is done by emil
