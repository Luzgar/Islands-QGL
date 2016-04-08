package fr.unice.polytech.qgl.qcc.strategy;

import org.json.JSONObject;

/**
 * Created by user on 09/11/15.
 */
public interface Strategy {

    /*Cette methode devra être complété pour chaque classe implementant Strategy.
    * Elle permettra de mettre concrètement en action la stratégie demandée
    */

    JSONObject takeDecision(JSONObject lastActionResult);

    boolean changedStep();
}
