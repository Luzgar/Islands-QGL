package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.exceptions.NoHeadingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renaud on 30/11/2015.
 */
public class Carte {
    int x, y; //coordonnées actuelles de l'avion
    List<Case> cases; //cases scannées ou explorées
    List<Creek> listCreeks; //Liste des id des criques découvertes
    int[] landCoordinate = new int[2];
    Direction planeDirection;

    public Carte(JSONObject jsonInit) throws Exception
    {
        x = 0;
        y = 0;
        cases = new ArrayList<>();
        listCreeks = new ArrayList<>();
        try {
            planeDirection = Direction.valueOf(jsonInit.getString("heading"));
        }
        catch (Exception e)
        {
            throw new NoHeadingException();
        }
    }

    /**
     * Get X location of the plane or explorator
     * @return
     */
    public int getCurrentX() {
        return x;
    }

    /**
     * Get Y location of the plane or explorator
     * @return
     */
    public int getCurrentY() {
        return y;
    }

    /**
     * Set the coordinate
     * @param x
     * @param y
     */
    public void setCurrentCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Add a creek to the map
     * @param creek
     */
    public void addCreeks(Creek creek){
        listCreeks.add(creek);
    }

    /**
     * Add a small square to the map
     * @param c
     */
    public void addCase(Case c) {cases.add(c);} //Ajouter une case explorée à la liste des cases repertoriées

    /**
     * Add a big square (compose with 9 small square) to the map
     * @param x
     * @param y
     * @param biomes
     */
    public void addBigCase(int x, int y, JSONArray biomes) //ajouter un carré de 9 cases à la liste des cases repertoriées (Suite a un scan)
    {
        String[] ressource = new String[biomes.length()];
        for(int i = 0; i < biomes.length(); ++i){
            ressource[i] = biomes.getString(i);
        }
        int x_tmp = x-1;
        int y_tmp = y+1;//On se place dans la case en haut a gauche du carré
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                addCase(new Case(x_tmp+j, y_tmp+i, ressource));
        }

    }

    /**
     * Get the current plane direction
     * @return
     */
    public Direction getPlaneDirection(){
        return planeDirection;
    }

    /**
     * Set the new plane direction
     * @param planeDirection
     */
    public void setPlaneDirection(Direction planeDirection) {
        this.planeDirection = planeDirection;
    }

    /**
     * Get the list of the creeks
     * @return
     */
    public List<Creek> getListCreeks() {
        return listCreeks;
    }

    /**
     * Get a square by his coordinate
     * @param x
     * @param y
     * @return
     */
    public Case getCase(int x, int y){
        for(int i = 0; i < cases.size(); ++i){
            if(cases.get(i).getX() == x && cases.get(i).getY() == y)
                return cases.get(i);
        }
        return null;
    }

    /**
     * Get the list of all square present on the map
     * @return
     */
    public List<Case> getListCases(){
        return cases;
    }

    /**
     * Extrapolation of the island. All "black zone" that we know which biome is present are filled
     * @param startDir
     */
    public void extrapolate(Direction startDir)
    {
        List<Case> casesToAdd = new ArrayList<>();
        boolean vertical = false;
        if (startDir.equals(Direction.E) || startDir.equals(Direction.W)) // On démarre horizontalement -> balayage vertical
        {
            vertical = true;
        }
        Case caseToCompare;
        int x,y;
        for (Case c : cases)
        {
            x = c.getX();
            y = c.getY();
            if(c.getBiomes().length == 1) {
                if (vertical) { //vertical
                    if (getCase(x + 1, y) == null) {
                        caseToCompare = getCase(x + 4, y);
                        if ((caseToCompare != null) && (caseToCompare.getBiomes().length == 1) && (caseToCompare.getBiomes()[0].equals(c.getBiomes()[0]))){
                            casesToAdd.add(new Case(x+1, y, c.getBiomes()));
                            casesToAdd.add(new Case(x+2, y, c.getBiomes()));
                            casesToAdd.add(new Case(x+3, y, c.getBiomes()));
                        }
                    }
                } else { //horizontal
                    if (getCase(x, y+1) == null) {
                        caseToCompare = getCase(x, y+4);
                        if ((caseToCompare != null) && (caseToCompare.getBiomes().length == 1) && (caseToCompare.getBiomes()[0].equals(c.getBiomes()[0]))){
                            casesToAdd.add(new Case(x, y+1, c.getBiomes()));
                            casesToAdd.add(new Case(x, y+2, c.getBiomes()));
                            casesToAdd.add(new Case(x, y+3, c.getBiomes()));
                        }
                    }
                }
            }
        }

        // ajouter les nouvelles cases
        for (Case c : casesToAdd)
        {
            cases.add(c);
        }

    }

    public boolean isExcluded(int n, int [] exc)
    {
        for (int i : exc)
        {
            if ((n % 9) == i)
                return true;
        }
        return false;
    }

    public void setLandCoordinate(int x, int y){
        landCoordinate[0] = x;
        landCoordinate[1] = y;
    }

    public int[] getLandCoordinate(){
        return landCoordinate;
    }
}
