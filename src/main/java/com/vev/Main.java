package com.vev;

public class Main {
    public static void main(String[] args) {
        ParkingSys parking = new ParkingSys(false);
        System.out.println(parking.enter());
        try {
            // Wait for 5 seconds (5000 milliseconds)
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Handle the exception if the sleep is interrupted
            System.err.println("Sleep was interrupted: " + e.getMessage());
        }
        System.out.println(parking.leave());
    }
}