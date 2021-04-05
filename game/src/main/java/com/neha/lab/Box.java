package com.neha.lab;

import java.awt.Color;

public class Box {
    private final int x; // x-coordinate of the left upper corner of the box
    private final int y; // y-coordinate of the left upper corner of the box
    private final int size; // size of side of square box
    private final int index; // index of the box
    private Box transitionIndex; // index to which player will transit to when snake/ladder present, otherwise null when no snake or ladder from box
    private final Color color; // color of the box

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public Color getColor() {
        return color;
    }

    public Box getTransitionIndex() {
        return transitionIndex;
    }

    public void setTransitionIndex(Box transitionIndex) {
        this.transitionIndex = transitionIndex;
    }

    public Box(int x, int y, int size, int index) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.index = index;

        if (index % 4 == 1) {
            this.color = Color.RED;
        } else if (index % 4 == 2) {
            this.color = Color.GREEN;
        } else if (index % 4 == 3) {
            this.color = Color.BLUE;
        } else {
            this.color = Color.YELLOW;
        }
    }


}
