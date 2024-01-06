package game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame {

    public GameArea gameArea;
    JLabel score;
    JLabel difficulty;
    public GameForm() {
        score = new JLabel("score: 0");
        difficulty = new JLabel("difficulty: 1");
        gameArea = new GameArea(10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 900);
        score.setBounds(585,100,100,150);
        difficulty.setBounds(585,400,200,300);
        setLayout(null);
        setResizable(false);
        add(gameArea);
        add(score);
        add(difficulty);
        setLocationRelativeTo(null);
        setVisible(true );
        initControls();
        startGame();
    }

    public void startGame() {
        new GameThread(gameArea,this).start();
    }

    private void initControls() {
        InputMap inputMap = this.getRootPane().getInputMap();
        ActionMap actionMap = this.getRootPane().getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");


        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveRight();
            }
        });
        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveLeft();
            }
        });
        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.drop();
            }
        });
        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.rotate();
            }
        });

    }

    public void setScoreText(int s){
        score.setText("score: "+s);
    }

    public void setDifficultyText(int d){
        difficulty.setText("difficulty: "+d);
    }

}
