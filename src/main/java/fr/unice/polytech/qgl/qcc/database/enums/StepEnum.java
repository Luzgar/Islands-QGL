package fr.unice.polytech.qgl.qcc.database.enums;

/**
 * Enum contenant le noms des différentes stratégie qui seront utilisées successivement.
 * Created by renaud on 20/11/2015.
 */
public enum StepEnum {

    FINDCREEKS("CreeksFinder"),
    MOVEEXTRIMITYISLAND("MoveExtrimityIsland"),
    MOVETODIRECTIONOFISLAND("MoveToDirectionOfIsland"),
    LANDONISLAND("LandOnIsland"),
    EXPLOREISLAND("ExploreIsland");

    String step;

    StepEnum(String step)
    {
        this.step = step;
    }

    public StepEnum next(){
        switch(this){
            case MOVETODIRECTIONOFISLAND:
                return MOVEEXTRIMITYISLAND;
            case MOVEEXTRIMITYISLAND:
                return FINDCREEKS;
            case FINDCREEKS:
                return  LANDONISLAND;
            case LANDONISLAND:
                return EXPLOREISLAND;
            default:
                return null;
        }
    }

}
