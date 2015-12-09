package TemporalBehaviour;

import core.Coord;
import core.DTNHost;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public abstract class State {

    protected static int state_id = 0;
    protected Coord destination;
    protected double speed;
    protected boolean destinationChanged = true;
    protected Random random = new Random();
    protected State state;
    protected boolean isActive = true;
    protected int id;

    public State(){
        state_id++;
        id = state_id;
    }
    public int getID(){
       return id;
    }

    public abstract Coord getDestination();
    public abstract void reachedDestination();
    public State getState(){
        if(state == null){
            System.out.println("State is null! Please initialize state = this in constructor");
        }
        return state;
    }
    public boolean isActive(){
        return isActive;
    }

    public abstract void update();


    public abstract void addConnection(DTNHost socialKnownHost);
    public abstract void removeConnection(DTNHost host);

    public double getSpeed() {
        speed = 1;
        return speed;
    }

    public  boolean destinationChanged(){
        //Only when changed since else a recalculation will be done regarding the movement path
        return destinationChanged;
    }
    public abstract boolean enableConnections();
}
