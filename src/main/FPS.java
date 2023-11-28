package main;

import java.time.Duration;
import java.time.Instant;

/**
 * The FPS class is designed to assist in calculating and managing the time-related aspects of frame rendering in a Java game.
 */
public class FPS {

    /** Private constructor to prevent instantiation **/
    private FPS(){

    }

    /** Duration between the current frame and the previous frame **/
    private static Duration fpsDeltaTime = Duration.ZERO;

    /** Duration at the last frame */
    private static Duration lastTime = Duration.ZERO;

    /** Starting time of the current frame */
    private static Instant beginTime = Instant.now();

    /** Time elapsed between the current frame and the previous frame in seconds **/
    private static double deltaTime = fpsDeltaTime.toMillis() - lastTime.toMillis();

    /**
     * Resets the beginning time to the current instant, indicating the start of a new frame.
     **/
    public static void calcBeginTime(){
        beginTime = Instant.now();
        fpsDeltaTime = Duration.ZERO;
    }

    /**
     * Calculates the time elapsed between the current frame and the previous frame (delta time).
     **/
    public static void calcDeltaTime(){
        Instant currentTime = Instant.now();
        fpsDeltaTime = Duration.between(beginTime, Instant.now());
        deltaTime = (double) fpsDeltaTime.toMillis() / 1000;
        lastTime = Duration.ofDays(currentTime.toEpochMilli());
    }

    /**
     * Retrieves the delta time in seconds. This can be used for updating game elements based on time.
     *
     * @return The time elapsed between the current frame and the previous frame in seconds.
     */
    public static double getDeltaTime(){
        return deltaTime / 1000;
    }
}

/** Documentation for FPS Class:

 **Overview:
 The FPS (Frames Per Second) class is designed to assist in calculating and managing the time-related aspects of frame rendering in a Java game.
 It provides functionality to calculate the time elapsed between frames (delta time) and manage the frame rate.

 **Purpose:
 The primary purpose of this class is to facilitate smooth frame rendering by tracking the time elapsed between consecutive frames. This information is crucial for various game-related calculations, such as updating game logic, animations, and physics at a consistent rate.

 **Usage:
 The FPS class can be utilized in Java game development to manage frame timing. It should be integrated into the game loop to calculate and maintain a consistent frame rate.

 **Class Members:
 private FPS()

 Private constructor to prevent instantiation. This class should be used in a static context.
 private static Duration fpsDeltaTime = Duration.ZERO;

 Represents the duration between the current frame and the previous frame.
 private static Duration lastTime = Duration.ZERO;

 Represents the duration at the last frame.
 private static Instant beginTime = Instant.now();

 Represents the starting time of the current frame.
 private static double deltaTime = fpsDeltaTime.toMillis() - lastTime.toMillis();

 Represents the time elapsed between the current frame and the previous frame in seconds.
 **Methods:
 public static void calcBeginTime()

 Resets the beginning time to the current instant, indicating the start of a new frame.
 public static void calcDeltaTime()

 Calculates the time elapsed between the current frame and the previous frame (delta time).
 public static double getDeltaTime()

 Retrieves the delta time in seconds. This can be used for updating game elements based on time.

 **Applicability:
 This class can be applied to most Java games that utilize a game loop for continuous rendering and logic updates.
 It helps ensure a consistent frame rate, which is crucial for maintaining a smooth and predictable gaming experience.*/