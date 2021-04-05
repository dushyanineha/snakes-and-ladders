package com.neha.lab;

public class Utility {
    // generate a random number
    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

    // generate random even integer
    public static int getRandomEvenInteger(int maximum, int minimum){
        return (((int) (Math.random()*(maximum/2 - minimum))) + minimum) * 2;
    }
}
