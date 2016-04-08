package fr.unice.polytech.qgl.qcc;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.qgl.qcc.strategy.actions.Stop;
import org.json.*;

/**
 * Created by Kevin on 09/11/2015.
 */
public class Explorer implements IExplorerRaid {



    GameEngine gameEngine;
    boolean error = false;

    public void initialize(String context) {
        try {
            JSONObject jsonInit = new JSONObject(context);
            gameEngine = new GameEngine(jsonInit);
        }
        catch (Exception e)
        {
            error = true;
        }
    }

    public String takeDecision() {
        if(error)
            return new Stop().act().toString();
        try {
            return gameEngine.takeDecision().toString();
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            return new Stop().act().toString();
        }
    }

    public void acknowledgeResults(String results) {

        if(!error){
            try {
                gameEngine.acknowledgeResults(new JSONObject(results));
            }
            catch (Exception e){
                error = true;
            }
        }

    }

}