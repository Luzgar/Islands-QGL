package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.database.Contract;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import fr.unice.polytech.qgl.qcc.strategy.actions.Action;
import fr.unice.polytech.qgl.qcc.strategy.actions.Transform;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kevin on 28/01/2016.
 */
public class TransformRessources implements Strategy {

    private Craftables craftables;
    private boolean changedStep = false;
    private int amountToCraft;
    private ArrayList<Biomes.Ressource> neededPrimaryResources = new ArrayList<>();

    public TransformRessources(Contract contract, Craftables craftables){
        this.craftables = craftables;
        this.amountToCraft = contract.getCraftable().get(craftables.getRecipe());

        for(Biomes.Ressource r : craftables.getRecipe().getIngredients()){
            neededPrimaryResources.add(r);
        }
    }

    /**
     * Method who take the action for transforming primary resources to craft resources
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {

        Action action;
        if(neededPrimaryResources.size() == 1){
            int dosage = (amountToCraft*craftables.getRecipe().getDosage(neededPrimaryResources.get(0))) / craftables.getRecipe().getQuantity();
            dosage += dosage*craftables.getRecipe().getFailRatio();
            String resource = neededPrimaryResources.get(0).toString();

            action = new Transform(resource, dosage);
        }
        else {
            int dosage1 = (amountToCraft*craftables.getRecipe().getDosage(neededPrimaryResources.get(0))) / craftables.getRecipe().getQuantity();
            int dosage2 = (amountToCraft*craftables.getRecipe().getDosage(neededPrimaryResources.get(1))) / craftables.getRecipe().getQuantity();
            dosage1 += dosage1*craftables.getRecipe().getFailRatio();
            dosage2 += dosage2*craftables.getRecipe().getFailRatio();
            String resource1 = neededPrimaryResources.get(0).toString();
            String resource2 = neededPrimaryResources.get(1).toString();
            action = new Transform(resource1, resource2, dosage1, dosage2);
        }

        changedStep = true;
        return action.act();
    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }
}
