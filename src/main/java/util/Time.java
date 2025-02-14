package util;

public class Time {
    // Saves the time when the program starts in nanoseconds
    public static float timeStarted =System.nanoTime();

    // Returns the time since the program started in seconds
    // (1E-9 = 10^-9 for nanosec to sec conversion)
    public static float getTime(){
        return (float)((System.nanoTime()-timeStarted) * 1E-9);
    }
}
