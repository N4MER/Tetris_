package game;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea gameArea;
    private GameForm gameForm;
    private int score;
    private int difficulty = 1;
    private int scoreForLevel = 3;
    private int speed = 1000;
    private int speedPerDifficulty = 10;

    public GameThread(GameArea gameArea, GameForm gameForm) {
        this.gameArea = gameArea;
        this.gameForm = gameForm;
    }

    @Override
    public void run() {
        while (true) {
            gameArea.spawnTetromino();
            while (!gameArea.moveBlockDown()) {
                try {
                    if (speed > 0) {
                        Thread.sleep(speed);
                    }else {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (gameArea.isOutOfBounds()) {
                System.out.println("GAME OVER");
                break;
            }
            gameArea.moveToBackground();
            score += gameArea.clearLines();
            gameForm.setScoreText(score);
            int diff = score / scoreForLevel + 1;
            if (diff > difficulty) {
                difficulty = diff;
                gameForm.setDifficultyText(difficulty);
            }
            speed -= speedPerDifficulty;

        }
    }


}
