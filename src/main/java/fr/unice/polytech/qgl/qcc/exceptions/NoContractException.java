package fr.unice.polytech.qgl.qcc.exceptions;

/**
 * Created by renaud on 07/12/2015.
 */
public class NoContractException extends Exception {
    public NoContractException()
    {
        super("No contract has been given");
    }
}
