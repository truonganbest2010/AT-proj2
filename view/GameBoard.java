package view;

import controller.KeyController;
import controller.TimerListener;
import model.BonusDropper;
import model.EnemyComposite;
import model.Shooter;
import model.ShooterElement;
import model.StarDrop;
import model.enemyCompositeObserverPattern.EnemyCompositeObserver;

import java.awt.*;
import javax.swing.*;

public class GameBoard {

    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final int FPS = 60;
    public static final int DELAY = 1000/FPS;
    
    private JFrame window;
    private MyCanvas canvas;

    /** model */
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private StarDrop starDrop;
    private BonusDropper bonusDropper;

    private Timer timer;
    private TimerListener timerListener;

    private JButton startBtn = new JButton("Start");
    private JButton pauseBtn = new JButton("Pause");
    private JButton quitBtn = new JButton("Quit");

    private int score = 0;
    private boolean gameOver;

    public GameBoard(JFrame window) {
        this.window = window;
    }

    public void init() {
        Container cp = window.getContentPane();


        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);

            pauseBtn.setEnabled(false);

        startBtn.setFocusable(false);
        pauseBtn.setFocusable(false);
        quitBtn.setFocusable(false);

        JPanel southPanel = new JPanel();
        southPanel.add(startBtn);
        southPanel.add(pauseBtn);
        southPanel.add(quitBtn);
        cp.add(BorderLayout.SOUTH, southPanel);

        starDrop = new StarDrop();
        canvas.getGameElements().add(starDrop);

        TextDraw t = new TextDraw("Click <Start> to Play", WIDTH/3 -30, HEIGHT/2, Color.LIGHT_GRAY, 30);
        canvas.getGameElements().add(t);
        
        timerListener = new TimerListener(this);
        timer = new Timer(DELAY, timerListener);
    
        startBtn.addActionListener(e -> {
            gameOver = false;
            pauseBtn.setEnabled(true);
            startBtn.setEnabled(false);
            shooter = new Shooter(GameBoard.WIDTH/2, GameBoard.HEIGHT-ShooterElement.SIZE);
            enemyComposite = new EnemyComposite();
            bonusDropper = new BonusDropper(this);
            canvas.getGameElements().clear();
            canvas.getGameElements().add(starDrop);
            canvas.getGameElements().add(t);
            canvas.getGameElements().add(bonusDropper);
            canvas.getGameElements().add(shooter);
            canvas.getGameElements().add(enemyComposite);
            score = 0;
            timerListener.getEventQueue().clear();
            timer.start();

            EnemyCompositeObserver observer = new EnemyCompositeObserver(this);
            enemyComposite.addEnemyListener(observer);

            if (!startBtn.isEnabled()) {
                pauseBtn.setText("Pause");
            }
            
        });

        pauseBtn.addActionListener(e -> {
            String Btnlabel = pauseBtn.getText();
            if (Btnlabel.equals("Pause")) {
                pauseBtn.setText("Resume");
                startBtn.setEnabled(true);
                startBtn.setText("New Game");
                timer.stop();
            }
            else {
                pauseBtn.setText("Pause");
                startBtn.setEnabled(false);
                timer.start();
            }
        });

        quitBtn.addActionListener(e -> {
            System.exit(0);
        });

        
    }

    public MyCanvas getCanvas() {
        return canvas;
    }
    public Timer getTimer() {
        return timer;
    }
    public TimerListener getTimerListener() {
        return timerListener;
    }
    public Shooter getShooter() {
        return shooter;
    }
    public BonusDropper getBonusDropper() {
        return bonusDropper;
    }
    public StarDrop getStarDrop() {
        return starDrop;
    }
    public EnemyComposite getEnemyComposite() {
        return enemyComposite;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public JButton getStartBtn() {
        return startBtn;
    }
    public JButton getPauseBtn() {
        return pauseBtn;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
}
