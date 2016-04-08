package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renaud on 11/11/2015.
 */
public class Case {

    private int x;//Coordonnées
    private int y;//
    private String[] biomes; //tableau listant les biomes possiblement disponibles sur la case
    private List<Biomes.Ressource> ressourcesExploited = new ArrayList<>();

    public Case()
    {
        x = 0;
        y = 0;
    }

    public Case(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Case(int x, int y, String[] biomes)
    {
        this.x = x;
        this.y = y;
        this.biomes = biomes;
    }

    /**
     * Get X position of the square
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Get Y position of the square
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Get biomes present of this square
     * @return
     */
    public String[] getBiomes() {
        return biomes;
    }

    /**
     * Set the exploited resources on this square
     * @param ressource
     */
    public void setRessourcesExploited(Biomes.Ressource ressource){
        if(!ressourcesExploited.contains(ressource))
            ressourcesExploited.add(ressource);
    }

    /**
     * Get all exploited resources on this square
     * @param ressource
     * @return
     */
    public boolean hasRessourceBeenExploited(Biomes.Ressource ressource){
        if(ressourcesExploited.contains(ressource))
            return true;
        return false;
    }

    /**
     * Get distance between two square
     * @param c
     * @return
     */
    public int distanceTo(Case c)
    {
        return (Math.abs(c.getX() - x) + Math.abs(c.getY() - y));
    }


    public boolean equals(Case c)
    {
        return (x==c.x && y==c.y);
    }
}
