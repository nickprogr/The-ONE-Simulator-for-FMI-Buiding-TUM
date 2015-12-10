package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class LunchState extends State {

    private Coord lunchLocation = null;

    public LunchState(){
        super();
        state = this;
        destinationReached = false;
        lunchLocation = selectLunchLocation();
        //System.out.println("LectureState");
    }

    private Coord selectLunchLocation() {
        double r = random.nextDouble();
        if(r < .50){
            return new Coord(70,53);        //Cafeteria
        }else{
            return new Coord(122,-5);        //Mensa
        }
    }

    @Override
    public Coord getDestination() {
        //if(isActive) {
            destinationChanged = false;
            return lunchLocation;
        //}
        //return null;
    }

    private double inactiveTime  = 0;
    private boolean destinationReached = false;
    @Override
    public void reachedDestination() {
        isActive = false;
        destinationReached = true;
        double studyTime = random.nextDouble()*45*60+15*60;         //between 15min and 60min
        inactiveTime = studyTime+SimClock.getTime();

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
        return true;
    }

    @Override
    public void update() {
        if (inactiveTime >= SimClock.getTime()) {
            isActive = false;
        } else {
            isActive = true;
            if(destinationReached) {
                state = switchState(20,0,20,20,40,0);
            }
        }
    }

    @Override
    public void addConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }
}
