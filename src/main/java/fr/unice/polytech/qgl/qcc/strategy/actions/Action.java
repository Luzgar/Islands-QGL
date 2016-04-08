package fr.unice.polytech.qgl.qcc.strategy.actions;


import org.json.JSONObject;

/**
 * Created by renaud on 11/11/2015.
 */
public abstract class Action {
    //Stocke l'action à executer au tour d'après
    /*protected Carte map;
    protected Contract contract;
    protected StrategyInfo stratInfo;*/

    protected JSONObject action;

    /*
    * Construit pour chaque action l'objet JSON à renvoyer au moteur de l'ile, et le retourne sous forme de String
     */
    public abstract JSONObject act();
}
