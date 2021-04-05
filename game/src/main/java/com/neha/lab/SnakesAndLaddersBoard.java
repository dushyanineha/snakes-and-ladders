package com.neha.lab;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

// this class is for designing the snakes and ladders board
public class SnakesAndLaddersBoard extends Frame implements ActionListener {
    private int sizeOfEachBox; // size of each box
    public static int totalNumberOfBoxes; // total number of boxes in the board
    public static Box[] boxArray; // array of boxes having all information

    private BufferedImage ladderImage = null;
    public static Button rollDiceButton = null;

    private Player player1 = null;

    public Player getPlayer1() {
        return player1;
    }

    public SnakesAndLaddersBoard() {
        super("Snakes and Ladders");
        init();
        prepareGUI();
    }

    private void init() {
        this.sizeOfEachBox = Constants.CANVAS_WIDTH / Constants.NUMBER_OF_COLS;
        totalNumberOfBoxes = Constants.NUMBER_OF_COLS * Constants.NUMBER_OF_ROWS;
        boxArray = new Box[totalNumberOfBoxes];
        int x = 0; // x coordinate of boxes // initialized to 0
        int y = Constants.NUMBER_OF_ROWS * sizeOfEachBox; // y coordinate of boxes // initialized to lowest left box's y coordinate
        int currentDirectionOfRendering = 1; // variable defined to know the current direction of rendering the boxes - 1 for left to right, -1 for right to left
        // loop to initialize Box array // will be useful in preparing GUI
        for (int i = 0; i < totalNumberOfBoxes; i++) {
            boxArray[i] = new Box(x, y, sizeOfEachBox, i);
            x += sizeOfEachBox * currentDirectionOfRendering;

            if (x >= Constants.CANVAS_WIDTH || x <= -sizeOfEachBox) {
                currentDirectionOfRendering *= -1;
                x += sizeOfEachBox * currentDirectionOfRendering;
                y -= sizeOfEachBox;
            }
        }

        // initialize player1
        player1 = new Player(1);

        try {
            // initialized ladder image
            this.ladderImage = ImageIO.read(new File(Constants.LADDER_IMAGE_PATH));
            // initialized snake image
            // this.snakeImage = ImageIO.read(new File(Constants.SNAKE_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareGUI(){
        setSize(Constants.CANVAS_WIDTH + 100, Constants.CANVAS_HEIGHT + 100); // 100 is buffer height and width
        rollDiceButton = new Button("Roll Dice...");
        rollDiceButton.addActionListener(this::actionPerformed);
        setLayout(new BorderLayout());
        add(rollDiceButton, BorderLayout.EAST);
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
        // Picking and Rendering of each boxes one by one
        Arrays.stream(boxArray).forEach(b -> {
                    g.setColor(b.getColor());
                    g.fillRect(b.getX(), b.getY(), sizeOfEachBox, sizeOfEachBox);
                    g.setColor(Constants.NUMBERS_TEXT_COLOR);
                    g.drawString(String.valueOf(b.getIndex() + 1), b.getX() + 20, b.getY() + 20);
                });


        // drawing ladders
        drawLadder(g, 61, 22);
        drawLadder(g, 92, 66);
        drawLadder(g, 14, 7);

        // drawing snakes
        drawSnakes(g, 67, 35);
        drawSnakes(g, 99, 25);

        // drawing player
        drawPlayer1(g, this.player1);
    }

    // draw player on board with the assigned position
    private void drawPlayer1(Graphics g, Player player) {
        Box currentBox = player.getCurrentBox();
        int halvedSize = sizeOfEachBox / 2;
        g.drawString("P" + player.getPlayerNumber(),
                currentBox.getX() + halvedSize,
                currentBox.getY() + halvedSize);
    }

    // generic method for drawing snakes (only line representing a snake) on board
    private void drawSnakes(Graphics g, int fromNumber, int toNumber) {
        Box fromBox = boxArray[fromNumber - 1];
        Box toBox = boxArray[toNumber - 1];
        int halvedSize = sizeOfEachBox / 2;
        g.drawLine(fromBox.getX() + halvedSize, fromBox.getY() + halvedSize,
                toBox.getX() + halvedSize, toBox.getY() + halvedSize);

        // set transition for drawn snake
        fromBox.setTransitionIndex(toBox);
    }

    // generic method for drawing ladder on board
    private void drawLadder(Graphics g, int fromNumber, int toNumber) {
        Box fromBox = boxArray[fromNumber - 1];
        Box toBox = boxArray[toNumber - 1];
        int halvedSize = sizeOfEachBox / 2;
        /* logic for getting the angle of rotation  starts */
        double adjacent = Math.abs(fromBox.getX() - toBox.getX());

        double opposite = Math.abs(toBox.getY() - fromBox.getY());
        int hypotenuse = (int) opposite;
        double tanTheta, thetaInDegrees, thetaInRadians, rads = 0.0;
        if (adjacent != 0.0) {
            tanTheta = opposite / adjacent;

            thetaInRadians = Math.atan(tanTheta);

            thetaInDegrees = 180 - Math.toDegrees(thetaInRadians);

            rads = Math.toRadians(thetaInDegrees); // angle of rotation in radians

            hypotenuse = (int) Math.abs(opposite / Math.sin(Math.toDegrees(thetaInRadians)));
        }
        /* logic for getting the angle of rotation  ends */

        // scale the image
        Image resultingImage = this.ladderImage.getScaledInstance(hypotenuse, halvedSize, Image.SCALE_DEFAULT);
        int imageWidth = (int) adjacent;
        if (adjacent == 0.0) {
            imageWidth = halvedSize;
        }
        BufferedImage outputImage = new BufferedImage(imageWidth, (int) opposite, BufferedImage.TYPE_INT_ARGB);

        /* rotating the image starts */
        Graphics2D g2d = outputImage.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((imageWidth - hypotenuse) / 2.0, (opposite - halvedSize) / 2);

        int x = hypotenuse / 2;
        int y = halvedSize / 2;
        if (fromBox.getX() - toBox.getX() < 0) {
            at.rotate(-rads, x, y);
        } else if(fromBox.getX() - toBox.getX() > 0) {
            at.rotate(rads, x, y);
        } else {
            at.rotate(Math.toRadians(90), x, y);
        }
        g2d.setTransform(at);
        g2d.drawImage(resultingImage, 0, 0, this);
        g2d.dispose();
        /* rotating the image ends */

        // drawing the rotated image
        if (fromBox.getX() - toBox.getX() <= 0) {
            g.drawImage(outputImage, fromBox.getX() + halvedSize,
                    fromBox.getY() + halvedSize, this);
        } else if(fromBox.getX() - toBox.getX() > 0) {
            g.drawImage(outputImage, fromBox.getX() + halvedSize - imageWidth,
                    fromBox.getY() + halvedSize, this);
        }

        // set transition for drawn ladder
        toBox.setTransitionIndex(fromBox);
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        int randomNumber = Utility.getRandomEvenInteger(6, 1);
        boolean won = this.player1.rollDice(randomNumber); // call rollDice for particular player

        repaint(); // repaint the board

        if (won) { // when player reaches to last box, show message
            JOptionPane.showMessageDialog(null, "Player 1 won.");
            System.exit(0);
        }

        Box currentBox = player1.getCurrentBox();
        if (currentBox.getTransitionIndex() != null) {
            rollDiceButton.setEnabled(false);
            Timer timer = new Timer(2000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater( () -> {
                        player1.setCurrentBox(currentBox.getTransitionIndex());
                        repaint(); // repaint the board
                        rollDiceButton.setEnabled(true);
                    });
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static void main(String[] args){
        SnakesAndLaddersBoard snakesAndLaddersBoard = new SnakesAndLaddersBoard();
        snakesAndLaddersBoard.setVisible(true);
    }


}
