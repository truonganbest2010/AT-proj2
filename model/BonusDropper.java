package model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.bonusStrategyPattern.BonusAfterHitShooterStrategy;
import model.bonusStrategyPattern.BonusFallForwardStrategy;
import model.bonusStrategyPattern.BonusMoveStrategy;
import model.bonusStrategyPattern.BonusRenderAfterHitShooterStrategy;
import model.bonusStrategyPattern.BonusRenderMoveForwardStrategy;
import model.bonusStrategyPattern.BonusRenderStrategy;
import view.GameBoard;

public class BonusDropper extends GameElement {

    private GameBoard gameBoard;
    private ArrayList<GameElement> bonusDropper;
    private BonusMoveStrategy moveStrategy;
    private BonusRenderStrategy renderStrategy;

    public static final int BONUS_LIGHTNING = 1;
    public static final int UNIT_MOVE = 3;

    public BonusDropper(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        bonusDropper = new ArrayList<>();
        moveStrategy = new BonusFallForwardStrategy(this);
        renderStrategy = new BonusRenderMoveForwardStrategy(this);
    }

    public void setMoveStrategy(BonusMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    public void setRenderStrategy(BonusRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }

    public ArrayList<GameElement> getBonusDropper() {
        return bonusDropper;
    }



    @Override
    public void render(Graphics2D g2) {    
       
        this.renderStrategy.renderAlgorithm(g2);

    }

    @Override
    public void animate() {
       
        this.moveStrategy.moveAlgorithm();
        
    }
    
    public void dropBonus() {
        if (bonusDropper.size() < 1) {
            Random x = new Random();
            bonusDropper.add(new BonusLightning(x.nextInt(GameBoard.WIDTH-BonusLightning.WIDTH), 0));
        }
        moveStrategy = new BonusFallForwardStrategy(this);
        renderStrategy = new BonusRenderMoveForwardStrategy(this);
        // System.out.println(bonusDropper.size());
    }

    public void removeBonusOutOfLowerBound() {
        var remove = new ArrayList<>();
        for (var bn: bonusDropper) {
            if (bn.y >= GameBoard.HEIGHT) {
                remove.add(bn);
                gameBoard.getTimerListener().setBonus_frameCounter(0);
            }
        }
        bonusDropper.removeAll(remove);
    }

    public void processCollisionWithBonus(Shooter shooter) {
        var removeBonus = new ArrayList<>();
        /** component - bonus */
        for (var c: shooter.getComponents()) {
            for (var bn: bonusDropper) {
                if (c.collideWith(bn) && removeBonus.size() < 1) {
                    removeBonus.add(bn);
                    // System.out.println(removeBonus.size());
                    shooter.setlightningShoot(shooter.getLightningShoot() + BONUS_LIGHTNING);
                    setMoveStrategy(new BonusAfterHitShooterStrategy(this));
                    setRenderStrategy(new BonusRenderAfterHitShooterStrategy(this));
                }
            }
        }
        removeBonus.clear();


    }

}