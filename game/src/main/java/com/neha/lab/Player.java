package com.neha.lab;

public class Player {

    private Box currentBox; // player's current position
    private int playerNumber; // player's number
    private boolean isActive; // is player active

    Player(int playerNumber) {
        this.currentBox = SnakesAndLaddersBoard.boxArray[0];
        this.playerNumber = playerNumber;
        this.isActive = false;
    }

    public Box getCurrentBox() {
        return currentBox;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    // roll dice and change the position of player accordingly
    public boolean rollDice() {
        int randomNumber = getRandomInteger(6, 1);
        if (!this.isActive && randomNumber == 1) {
            this.isActive = true;
        }
        if (!this.isActive) return false;
        if (randomNumber <= (SnakesAndLaddersBoard.totalNumberOfBoxes - 1) - this.currentBox.getIndex()) {
            this.currentBox = SnakesAndLaddersBoard.boxArray[this.currentBox.getIndex() + randomNumber];
            return false;
        } else {
            this.currentBox = SnakesAndLaddersBoard.boxArray[SnakesAndLaddersBoard.totalNumberOfBoxes - 1];
            return true;
        }
    }

    // generate a random number
    private static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }
}
