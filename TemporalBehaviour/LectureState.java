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
    private Coord lectureCoord;

    public LectureState(Lecture lecture){
        super();
        state = this;
        this.lecture = lecture;
        destinationReached = false;
        lectureCoord = new Coord(lecture.getCoord().getX()+random.nextDouble()*1.5-0.75,lecture.getCoord().getY()+random.nextDouble()*1.5-0.75);
        //System.out.println("LectureState");
    }

    @Override
    public Coord getDestination() {
        //if(isActive) {
            destinationChanged = false;
            return lectureCoord;
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
                state = switchState(20,0,15,45,10,10);
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
