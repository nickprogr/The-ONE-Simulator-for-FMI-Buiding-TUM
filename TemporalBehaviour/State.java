package TemporalBehaviour;

import core.Coord;
import core.DTNHost;

/**
 * Created by Matthias on 18.11.2015.
 */
public abstract class State {

    protected DTNHost host;
    protected Coord destination;

    public State(DTNHost host){
        this.host = host;
    }



    public abstract Coord getDestination();
    public abstract void reachedDestination();

    public abstract void initConnection(DTNHost host);
    public abstract void removeConnection(DTNHost host);
}
