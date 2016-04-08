package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.database.Carte;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.strategy.actions.Action;
import fr.unice.polytech.qgl.qcc.strategy.actions.MoveTo;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by Kevin on 11/12/2015.
 */
public class MoveToCoord implements Strategy{

    private Carte carte;
    private int coordXToMove;
    private int coordYToMove;
    private int currentX;
    private int currentY;
    boolean changedStep = false;

    public MoveToCoord(Carte carte, int coordXToMove, int coordYToMove){
        this.carte = carte;
        this.coordXToMove = coordXToMove;
        this.coordYToMove = coordYToMove;
        this.currentX = carte.getCurrentX();
        this.currentY = carte.getCurrentY();
    }

    /**
     * Method who decide the action to take for moving to specific coordinate
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {

        Action action;
        if(currentX!= coordXToMove){
            if(currentX > coordXToMove){
                --currentX;
                action = new  MoveTo(Direction.W);
            }
            else {
                ++currentX;
                action = new  MoveTo(Direction.E);
            }
        }
        else{
            if(currentY > coordYToMove){
                --currentY;
                action = new   MoveTo(Direction.S);
            }
            else {
                ++currentY;
                action = new  MoveTo(Direction.N);
            }
        }
        if(currentX == coordXToMove && currentY == coordYToMove)
            changedStep = true;

        return action.act();
    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }
}
