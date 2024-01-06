package game;

import java.awt.*;
import java.util.Random;

public class Tetrominos {

    private int[][] shape;
    private Color tetrominoColor;
    private int x;
    private int y;
    private int[][][] shapes;
    private int currentRotation;
    private Color[] tetrominoColors = {Color.CYAN, Color.RED, Color.GREEN, Color.YELLOW};


    //opsano
    private void initShapes() {
        shapes = new int[4][][];
        for (int i = 0; i < 4; i++) {
            int r = shape[0].length;
            int c = shape.length;
            shapes[i] = new int[r][c];
            for (int y = 0; y < r; y++) {
                for (int x = 0; x < c; x++) {
                    shapes[i][y][x] = shape[c - x - 1][y];

                }
            }
            shape = shapes[i];
        }
    }

    public void spawn(int gridWith) {
        Random r = new Random();
        currentRotation = r.nextInt(4);
        shape = shapes[currentRotation];
        y = -getHeight();
        x = (gridWith - getWidth()) / 2;
        tetrominoColor = tetrominoColors[r.nextInt(tetrominoColors.length)];
    }

    public Tetrominos(int[][] shape) {
        this.shape = shape;
        initShapes();
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getTetrominoColor() {
        return tetrominoColor;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveDown() {
        y++;
    }


    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void rotate() {
        currentRotation++;
        if (currentRotation > 3) {
            currentRotation = 0;
        }
        shape = shapes[currentRotation];
    }

    public int getLeftBorder() {
        return x;
    }

    public int getRightBorder() {
        return x + getWidth();
    }

    public int getBottomBorder() {
        return y + getHeight();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
