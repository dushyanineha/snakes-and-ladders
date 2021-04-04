package com.neha.lab;

public class Utility {
    // generate a random number
    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

    public static int getRandomEvenInteger(int maximum, int minimum){
        return (((int) (Math.random()*(maximum - minimum))) + minimum) * 2;
    }
}
