package fr.unice.polytech.qgl.qcc.exceptions;

/**
 * Created by renaud on 07/12/2015.
 */
public class NoBudgetException extends Exception {
    public NoBudgetException()
    {
        super("No budget has been given");
    }
}
