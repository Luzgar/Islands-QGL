package fr.unice.polytech.qgl.qcc.exceptions;

/**
 * Created by renaud on 07/12/2015.
 */
public class NoHeadingException extends Exception {
    public NoHeadingException()
    {
        super("No heading direction has been given");
    }
}
