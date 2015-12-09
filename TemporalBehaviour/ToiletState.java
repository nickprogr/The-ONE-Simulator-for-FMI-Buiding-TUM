package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.*;

/**
 * Created by Matthias on 18.11.2015.
 */
public class ToiletState extends State {

    private Coord toiletCoord;

    public ToiletState(){
        super();
        state = this;
        destinationReached = false;
    }

    public void selectToilet(Coord position) {
        ArrayList<Coord> toilets = new ArrayList<>();
        toilets.add(new Coord(28,25));
        toilets.add(new Coord(79,57));
        toilets.add(new Coord(104,60));

        Coord nearestToilet = null;
        for(Coord toilet : toilets){
            if(nearestToilet == null || toilet.distance(position) < nearestToilet.distance(position)){
                nearestToilet = toilet;
            }
        }
        System.out.println("nearestToilet "+nearestToilet);
        toiletCoord = nearestToilet;
    }

    @Override
    public Coord getDestination() {
        //if(isActive) {
            destinationChanged = false;
            return toiletCoord;
        //}
        //return null;
    }

    private double inactiveTime  = 0;
    private boolean destinationReached = false;
    @Override
    public void reachedDestination() {
        isActive = false;
        destinationReached = true;

        double timeOnToilet = random.nextDouble()*9*60+60;     //between 1min and 10min
        inactiveTime = timeOnToilet+SimClock.getTime();

        //Goal reached
        //dailyBehaviour.getMovement().setInactive(lecture.getEndTime()-SimClock.getTime());
        //state = this;
    }

    public  boolean destinationChanged() {
        //Only when changed since else a recalculation will be done regarding the movement path
        return destinationChanged;
    }

    @Override
    public boolean enableConnections() {
        return false;
    }

    @Override
    public void update() {
        if (inactiveTime >= SimClock.getTime()) {
            isActive = false;
        } else {
            isActive = true;
            if(destinationReached) {
                Random random = new Random();
                double rand = random.nextDouble();
                State newState;
                //if(rand < 0.40)
                //    newState = new CafeteriaState(dailyBehaviour, this);
                //else
                newState = new FreetimeState();
                this.state = newState;
            }
        }
    }

    @Override
    public void addConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }

    public Coord getToilet() {
        return toiletCoord;
    }
}
