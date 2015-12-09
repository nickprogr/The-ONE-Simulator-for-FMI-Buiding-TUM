package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nikolas on 24.11.2015.
 */
public class IdleState extends State {

    public IdleState(){
        super();
        state = this;
        isActive = false;
    }

    @Override
    public Coord getDestination() {
        return null;//dailyBehaviour.getMovement().randomCoord();
    }

    @Override
    public void reachedDestination() {
        state = new UBahnArrivalState();
    }

    @Override
    public void update() {

    }

    @Override
    public void addConnection(DTNHost otherHost) { /* DO NOTHING */}

    @Override
    public void removeConnection(DTNHost otherHost) { /* DO NOTHING */}

}
