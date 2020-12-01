package model.enemyCompositeStatePattern;

import java.awt.Color;

import model.EnemyComposite;
import view.GameBoard;
import view.TextDraw;

public class EnemiesWave1 implements EnemyCompositeState {

    private EnemyComposite enemyComposite;

    public EnemiesWave1(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
        enemyComposite.enemyFormation(1);
        enemyComposite.getText().add(new TextDraw("First   Wave", GameBoard.WIDTH/3, -100, Color.LIGHT_GRAY, 30));
    }

    @Override
    public void nextWave(EnemyComposite context) {
        
        context.setState(new EnemiesWave2(enemyComposite));
    }
    
}