package TemporalBehaviour;

import TemporalBehaviour.DailyBehaviour;
import TemporalBehaviour.FreetimeState;
import TemporalBehaviour.Lecture;
import TemporalBehaviour.State;
import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class LectureState extends State {

    private Lecture lecture;

    public LectureState(Lecture lecture){
        super();
        state = this;
        this.lecture = lecture;
        destinationReached = false;
        System.out.println("LectureState");
    }

    @Override
    public Coord getDestination() {
        //if(isActive) {
            destinationChanged = false;
            return lecture.getCoord();
        //}
        //return null;
    }

    private double inactiveTime  = 0;
    private boolean destinationReached = false;
    @Override
    public void reachedDestination() {
        isActive = false;
        destinationReached = true;
        inactiveTime = lecture.getEndTime();

        //Goal reached
        //dailyBehaviour.getMovement().setInactive(lecture.getEndTime()-SimClock.getTime());
        //state = this;
    }

    public  boolean destinationChanged() {
        //Only when changed since else a recalculation will be done regarding the movement path
        return destinationChanged;
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
}
