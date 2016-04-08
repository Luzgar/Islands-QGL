package fr.unice.polytech.qgl.qcc.database;

/**
 * Created by Kevin on 03/12/2015.
 */
public class Creek {

    private String id;
    private int x;
    private int y;

    public Creek(String id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Id of the creek
     * @return
     */
    public String getId(){
        return id;
    }

    /**
     * X position of the creek
     * @return
     */
    public int getX(){
        return x;
    }

    /**
     * Y position of the creek
     * @return
     */
    public int getY(){
        return y;
    }
}
