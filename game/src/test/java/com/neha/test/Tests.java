package com.neha.test;

import com.neha.lab.Box;
import com.neha.lab.Player;
import com.neha.lab.SnakesAndLaddersBoard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {

    private final static SnakesAndLaddersBoard snakesAndLaddersBoard = new SnakesAndLaddersBoard();

    @BeforeAll
    public static void drawBoard() {
        snakesAndLaddersBoard.setVisible(true);
        SnakesAndLaddersBoard.boxArray[6].setTransitionIndex(SnakesAndLaddersBoard.boxArray[13]);
    }

    // roll dice and change the position of player accordingly
    private void rollDice(int randomNumber) {
        Player player1 = snakesAndLaddersBoard.getPlayer1();
        boolean won = player1.rollDice(randomNumber);
        if (!won) {
            Box currentBox = player1.getCurrentBox();
            if (currentBox.getTransitionIndex() != null) {
                player1.setCurrentBox(currentBox.getTransitionIndex());
            }
        }
    }

    @Test
    void areFuntionalitiesWorking() {
        assertEquals(0, snakesAndLaddersBoard.getPlayer1().getCurrentBox().getIndex());
        rollDice(6);
        assertEquals(0, snakesAndLaddersBoard.getPlayer1().getCurrentBox().getIndex());
        rollDice(2);
        assertEquals(2, snakesAndLaddersBoard.getPlayer1().getCurrentBox().getIndex());
        rollDice(4);
        assertEquals(13, snakesAndLaddersBoard.getPlayer1().getCurrentBox().getIndex());
    }


}