package util;

public class Time {
    public static float timeStarted = System.nanoTime(); // time that game starts
    public static float getTime() {return (float)((System.nanoTime() - timeStarted) * 1E-9);}; // time since game started
    }
