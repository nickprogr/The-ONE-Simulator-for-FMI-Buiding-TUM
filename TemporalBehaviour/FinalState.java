package TemporalBehaviour;

import core.Coord;
import core.DTNHost;

/**
 * Created by Matthias on 09.12.2015.
 */
public class FinalState extends State {

    public FinalState(){
        state = this;
        isActive = false;
    }
    @Override
    public Coord getDestination() {
        return null;
    }

    @Override
    public void reachedDestination() {

    }

    @Override
    public void update() {

    }

    @Override
    public void addConnection(DTNHost socialKnownHost) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }

    @Override
    public boolean enableConnections() {
        return false;
    }
}
