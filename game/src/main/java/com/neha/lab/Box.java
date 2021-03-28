package com.neha.lab;

import java.awt.Color;

public class Box {
    private int x;
    private int y;
    private int size;
    private int index;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getIndex() {
        return index;
    }

    public Color getColor() {
        return color;
    }

    private Color color;


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
