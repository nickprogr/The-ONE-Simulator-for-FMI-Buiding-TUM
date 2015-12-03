package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class LectureState extends State {

    private Lecture lecture;

    public LectureState(DailyBehaviour dailyBehaviour, State state, Lecture lecture){
        super(dailyBehaviour, state);
        this.lecture = lecture;
    }

    @Override
    public Coord getDestination() {
            this.dailyBehaviour.getHost().setName("Lecture");
            destinationChanged = false;
            return lecture.getCoord();
    }

    @Override
    public void reachedDestination() {
        dailyBehaviour.getMovement().setActive(false);
        dailyBehaviour.getMovement().setInactive(lecture.getEndTime()-SimClock.getTime());
        //Goal reached
        //dailyBehaviour.getMovement().setInactive(lecture.getEndTime()-SimClock.getTime());
        Random random = new Random();
        double rand = random.nextDouble();
        State state;
        if(rand < 0.40)
            state = new CafeteriaState(dailyBehaviour, this);
        else
            state = new FreetimeState(dailyBehaviour, this);
        dailyBehaviour.changeState(state);
    }

    public  boolean destinationChanged() {
        //Only when changed since else a recalculation will be done regarding the movement path
        return destinationChanged;
    }

    @Override
    public void update() {

    }

    @Override
    public void addConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }
}
