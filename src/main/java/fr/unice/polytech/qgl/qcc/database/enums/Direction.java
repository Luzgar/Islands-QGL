package fr.unice.polytech.qgl.qcc.database.enums;

/**
 * Created by user on 09/11/15.
 */
public enum Direction {
    N("N"),
    S("S"),
    E("E"),
    W("W");

    private String dir = "";
    //Permet d'avoir un attribut, de manière à initialiser plus facilement

    //------------CONSTRUCTEUR---------------
    Direction(String dir) {
        this.dir = dir;
    }
    //---------------------------------------

    //Permet de récupérer la direction sous forme de String, afin d'être manipulée dans certains cas
    @Override
    public String toString() {
        return dir;
    }

    /*
    *
    * Cette méthode permet de tourner en rond. Si on fait face au Sud et qu'il n'y a rien, on se tourne vers
    * l'Ouest, de l'Ouest on passe au Nord si on a toujours rien trouvé etc...
    */

    public Direction clockwise() {
        Direction direction;
        switch(this) {
            case S: direction = W; break;
            case N: direction = E; break;
            case E: direction = S; break;
            case W: direction = N; break;
            default: direction = N;
        }
        return direction;
    }

    public Direction anticlockwise() {
        Direction direction;
        switch(this) {
            case S: direction = E; break;
            case N: direction = W; break;
            case E: direction = N; break;
            case W: direction = S; break;
            default: direction = N;
        }
        return direction;
    }
}
