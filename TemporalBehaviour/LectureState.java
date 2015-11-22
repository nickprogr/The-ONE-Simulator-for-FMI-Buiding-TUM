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

    private boolean toldDestination = false;

    @Override
    public Coord getDestination() {
        Coord c;
        if (!toldDestination){
            toldDestination = true;
            c = lecture.getCoord();
        }else{
            //Goal reached
            dailyBehaviour.getMovement().setInactive(lecture.getEndTime()-SimClock.getTime());
            Random random = new Random();
            double rand = random.nextDouble();
            State state;
            if(rand < 0.40)
                state = new CafeteriaState(dailyBehaviour, this);
            else
                state = new FreetimeState(dailyBehaviour, this);
            c = state.getDestination();
            dailyBehaviour.changeState(state);
        }
        return c;
    }

    @Override
    public void reachedDestination() {
        //System.out.println("Reached Lecture: "+host.getName());
        //host.getMovement().setInactive(lecture.getLength());
        //host.isMovementActive();
        //host.changeState(new FreetimeState(host));
    }

    @Override
    public void initConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }
}
