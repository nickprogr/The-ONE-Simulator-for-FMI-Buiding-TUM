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
        double variationX = random.nextDouble()*lecture.getRoomDimensions().getX()-lecture.getRoomDimensions().getX()/2;
        double variationY = random.nextDouble()*lecture.getRoomDimensions().getY()-lecture.getRoomDimensions().getY()/2;

        lectureCoord = new Coord(lecture.getCoord().getX()+variationX,lecture.getCoord().getY()+variationY);
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
        //not all Students will stay until the end
        double earlierLeave = 0;
        if(random.nextDouble()< 0.1){
            earlierLeave = lecture.getLength()*random.nextDouble();
        }

        inactiveTime = lecture.getEndTime()-earlierLeave;

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
