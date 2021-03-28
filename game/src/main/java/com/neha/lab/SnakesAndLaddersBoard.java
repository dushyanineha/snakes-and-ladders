package com.neha.lab;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class SnakesAndLaddersBoard extends Frame {
    private int sizeOfEachBox;
    private int totalNumberOfBoxes;
    private Box boxArray[];
    public SnakesAndLaddersBoard(){
        super("Snakes and Ladders");
        init();
        prepareGUI();
    }

    private void init() {
        this.sizeOfEachBox = Constants.CANVAS_WIDTH / Constants.NUMBER_OF_COLS;
        this.totalNumberOfBoxes = Constants.NUMBER_OF_COLS * Constants.NUMBER_OF_ROWS;
        this.boxArray = new Box[totalNumberOfBoxes];
        int x = 0;
        int y = Constants.NUMBER_OF_ROWS * sizeOfEachBox ;
        int currentDirectionOfRendering = 1;
        for (int i = 0; i < totalNumberOfBoxes; i++) {
            boxArray[i] = new Box(x, y, sizeOfEachBox, i);
            x += sizeOfEachBox * currentDirectionOfRendering;

            if (x >= Constants.CANVAS_WIDTH || x <= -sizeOfEachBox) {
                currentDirectionOfRendering *= -1;
                x += sizeOfEachBox * currentDirectionOfRendering;
                y -= sizeOfEachBox;
            }
        }
    }

    private void prepareGUI(){
        setSize(Constants.CANVAS_WIDTH+100, Constants.CANVAS_HEIGHT+100);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Font font = new Font("Serif", Font.PLAIN, 24);
        g.setFont(font);
        Arrays.stream(boxArray).forEach(b -> {
                    g.setColor(b.getColor());
                    g.fillRect(b.getX(), b.getY(), sizeOfEachBox, sizeOfEachBox);
                    g.setColor(Constants.NUMBERS_TEXT_COLOR);
                    g.drawString(String.valueOf(b.getIndex() + 1), b.getX() + 20, b.getY() + 20);
                });


    }

    public static void main(String[] args){
        SnakesAndLaddersBoard snakesAndLaddersBoard = new SnakesAndLaddersBoard();
        snakesAndLaddersBoard.setVisible(true);
    }
}
