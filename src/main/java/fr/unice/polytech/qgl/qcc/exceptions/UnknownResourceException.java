package fr.unice.polytech.qgl.qcc.exceptions;

/**
 * Created by renaud on 07/12/2015.
 */
public class UnknownResourceException extends Exception {

    public UnknownResourceException()
    {
        super("The resource is not known");
    }
}

