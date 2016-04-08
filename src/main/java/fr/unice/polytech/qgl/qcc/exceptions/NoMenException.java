package fr.unice.polytech.qgl.qcc.exceptions;

/**
 * Created by renaud on 07/12/2015.
 */
public class NoMenException extends Exception {
    public NoMenException()
    {
        super("No crew has been given");
    }
}
