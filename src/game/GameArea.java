package game;

import tetrisBlocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameArea extends JPanel {

    private int gridRows;
    private int gridColumns;
    private int gridSize;
    private Tetrominos tetromino;
    private Color[][] background;
    private Tetrominos[] tetrominos;

    public GameArea(int columns) {
        setBounds(175, 100, 400, 600);
        setBackground(Color.GRAY);
        setOpaque(true);
        setLayout(null);
        gridColumns = columns;
        gridSize = this.getWidth() / gridColumns;
        gridRows = this.getHeight() / gridSize;
        background = new Color[gridRows][gridColumns];
        tetrominos = new Tetrominos[]{new LShape(), new JShape(), new SShape(), new ZShape(), new SquareShape(), new IShape(), new TShape()};
    }

    public boolean moveBlockDown() {
        if (touchedBottom()) {
            return true;
        } else {
            tetromino.moveDown();
            repaint();
            return false;
        }
    }


    private boolean touchedBottom() {
        if (tetromino.getHeight() + tetromino.getY() == gridRows) {
            return true;
        } else {
            int shape[][] = tetromino.getShape();
            for (int i = 0; i < tetromino.getWidth(); i++) {
                for (int j = tetromino.getHeight() - 1; j >= 0; j--) {
                    if (shape[j][i] != 0) {
                        int x = i + tetromino.getX();
                        int y = j + tetromino.getY() + 1;
                        if (y < 0) {
                            break;
                        }
                        if (background[y][x] != null) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    public boolean touchedRightBorder() {
        if (tetromino.getRightBorder() == gridColumns) {
            return true;
        } else {
            int shape[][] = tetromino.getShape();
            for (int j = 0; j < tetromino.getHeight(); j++) {
                for (int i = tetromino.getWidth() - 1; i >= 0; i--) {
                    if (shape[j][i] != 0) {
                        int x = i + tetromino.getX() + 1;
                        int y = j + tetromino.getY();
                        if (y < 0) {
                            break;
                        }
                        if (background[y][x] != null) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    public boolean touchedLeftBorder() {
        if (tetromino.getLeftBorder() == 0) {
            return true;
        } else {
            int shape[][] = tetromino.getShape();
            for (int j = 0; j < tetromino.getHeight(); j++) {
                for (int i = 0; i < tetromino.getWidth(); i++) {
                    if (shape[j][i] != 0) {
                        int x = i + tetromino.getX() - 1;
                        int y = j + tetromino.getY();
                        if (y < 0) {
                            break;
                        }
                        if (background[y][x] != null) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }


    private void drawBlock(Graphics g) {
        for (int i = 0; i < tetromino.getHeight(); i++) {
            for (int j = 0; j < tetromino.getWidth(); j++) {
                if (tetromino.getShape()[i][j] == 1) {
                    int x = (tetromino.getX() + j) * gridSize;
                    int y = (tetromino.getY() + i) * gridSize;
                    drawSquare(g, tetromino.getTetrominoColor(), x, y);
                }
            }
        }

    }

    public void spawnTetromino() {
        Random r = new Random();
        tetromino = tetrominos[r.nextInt(tetrominos.length)];
        tetromino.spawn(gridColumns);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBlock(g);
    }

    private void drawBackground(Graphics g) {
        Color color;
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                color = background[i][j];
                if (color != null) {
                    int x = j * gridSize;
                    int y = i * gridSize;
                    drawSquare(g, color, x, y);
                }
            }
        }
    }

    public void drawSquare(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, gridSize, gridSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, gridSize, gridSize);
    }

    public void moveToBackground() {
        for (int i = 0; i < tetromino.getHeight(); i++) {
            for (int j = 0; j < tetromino.getWidth(); j++) {
                if (tetromino.getShape()[i][j] == 1) {
                    background[i + tetromino.getY()][j + tetromino.getX()] = tetromino.getTetrominoColor();

                }
            }
        }
    }

    public boolean isOutOfBounds() {
        if (tetromino.getY() < 0) {
            tetromino = null;
            return true;
        } else {
            return false;
        }
    }

    public void moveRight() {
        if (tetromino == null) {
            return;
        }
        if (touchedRightBorder()) {
            return;
        }
        tetromino.moveRight();
        repaint();
    }

    public void moveLeft() {
        if (tetromino == null) {
            return;
        }
        if (touchedLeftBorder()) {
            return;
        }
        tetromino.moveLeft();
        repaint();
    }

    public void drop() {
        if (tetromino == null) {
            return;
        }
        while (!touchedBottom()) {
            moveBlockDown();
        }
        repaint();
    }

    public void rotate() {
        if (touchedBottom()){
            return;
        }
        if (tetromino == null) {
            return;
        }
        if (tetromino.getLeftBorder() < 0) {
            tetromino.setX(0);
        }
        if (tetromino.getRightBorder() >= gridColumns) {
            tetromino.setX(gridColumns - tetromino.getWidth());
        }
        if (tetromino.getBottomBorder() >= gridRows) {
            tetromino.setY(gridRows - tetromino.getHeight());
        }
        tetromino.rotate();
        repaint();

    }

    public int clearLines() {
        boolean fullLine;
        int clearedLines = 0;
        for (int i = gridRows - 1; i >= 0; i--) {
            fullLine = true;
            for (int j = 0; j < gridColumns; j++) {
                if (background[i][j] == null) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                clearedLines++;
                clearLine(i);
                shiftDown(i);
                clearLine(0);
                i++;
                repaint();
            }
        }
        return clearedLines;
    }

    public void clearLine(int row) {
        for (int i = 0; i < gridColumns; i++) {
            background[row][i] = null;
        }

    }

    private void shiftDown(int row) {
        for (int i = row; i > 0; i--) {
            for (int j = 0; j < gridColumns; j++) {
                background[i][j] = background[i - 1][j];
            }
        }
    }


}

