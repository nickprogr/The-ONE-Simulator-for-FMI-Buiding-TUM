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
            toilets.add(new Coord(91, 28));
            toilets.add(new Coord(91, 10));
            toilets.add(new Coord(68, 27));
            toilets.add(new Coord(69, 10));
            toilets.add(new Coord(50, 28));
            toilets.add(new Coord(49, 10));
            toilets.add(new Coord(28, 28));
            toilets.add(new Coord(27, 11));
            toilets.add(new Coord(6, 26));
            toilets.add(new Coord(5, 11));
            toilets.add(new Coord(120, 62));
            toilets.add(new Coord(120, 74));
            toilets.add(new Coord(99, 60));
            toilets.add(new Coord(100, 72));
            toilets.add(new Coord(83, 57));
            toilets.add(new Coord(82, 72));
            toilets.add(new Coord(62, 56));
            toilets.add(new Coord(60, 67));
            toilets.add(new Coord(40, 53));
            toilets.add(new Coord(40, 64));



        Coord nearestToilet = null;
        for(Coord toilet : toilets){
            if(nearestToilet == null || toilet.distance(position) < nearestToilet.distance(position)){
                nearestToilet = toilet;
            }
        }
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
                state = switchState(20,0,0,10,60,10);
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
