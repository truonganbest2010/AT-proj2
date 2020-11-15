package model;

import java.awt.Color;
import java.awt.Graphics2D;

import view.GameBoard;

public class Laser extends GameElement {

    public static final int WIDTH = 2;
    public static final int UNIT_MOVE = 1;

    public Laser(int x, int y) {
        super(x, y, Color.white, true, WIDTH, GameBoard.HEIGHT-ShooterElement.SIZE);
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setColor(color);
        
        if (filled) {
            g2.fillRect(x, y, width, height);
        }
        else {
            g2.drawRect(x, y, width, height);
        }
    }

    @Override
    public void animate() {
        // TODO Auto-generated method stub
        super.y -= UNIT_MOVE;
    }
    
}