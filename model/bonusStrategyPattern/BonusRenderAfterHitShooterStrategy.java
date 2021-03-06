package model.bonusStrategyPattern;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import model.BonusBullet;
import model.BonusDropper;
import model.BonusLightning;
import model.images.ImageStore;

public class BonusRenderAfterHitShooterStrategy implements BonusRenderStrategy {

    private BonusDropper bonusDropper;

    public BonusRenderAfterHitShooterStrategy (BonusDropper bonusDropper) {
        this.bonusDropper = bonusDropper;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {

        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.BOLD, 10));

        for (var bn: bonusDropper.getBonusDropper()) {
            if (bn instanceof BonusLightning) {
                g2.drawImage(ImageStore.lightning_icon, null, bn.x, bn.y);
                g2.drawString("+" + BonusDropper.BONUS_LIGHTNING, bn.x + 20, bn.y + 5);
            } 
            else if (bn instanceof BonusBullet) {
                g2.drawImage(ImageStore.bullet_icon, null, bn.x, bn.y);
                g2.drawString("+" + BonusDropper.BONUS_BULLET, bn.x + 20, bn.y + 5);
            }
        }
    }
    
}