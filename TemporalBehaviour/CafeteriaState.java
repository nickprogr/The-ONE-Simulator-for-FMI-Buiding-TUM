package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;

/**
 * Created by Matthias on 18.11.2015.
 */
public class CafeteriaState extends State {
    private double stateEnterTime = 0;
    private boolean toldDestination = false;

    public CafeteriaState(DailyBehaviour dailyBehaviour, State state){
        super(dailyBehaviour, state);
        stateEnterTime = SimClock.getTime();
    }

    @Override
    public Coord getDestination() {
        Coord c;
        if (!toldDestination){
            toldDestination = true;
            //c = new Coord(240,130);
            c = new Coord(450.0,350);
        }else {
            //Goal reached
            dailyBehaviour.getMovement().setInactive(800);
            State state = new FreetimeState(dailyBehaviour, this);
            c = state.getDestination();
            dailyBehaviour.changeState(state);
        }
        return c;
    }

    @Override
    public void reachedDestination() {
        ArrayList<Lecture> lectures= dailyBehaviour.getLecturesAtTime(SimClock.getTime());
        if( lectures.size() > 0){
            dailyBehaviour.changeState(new LectureState(dailyBehaviour, this, lectures.get(0)));
        }
    }

    public int distributionTime = 200;

    @Override
    public void initConnection(DTNHost otherHost) {
        if(core.SimClock.getTime() > stateEnterTime+distributionTime && dailyBehaviour.getHost().getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT) && otherHost.getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT)){
            this.connectedHosts.put(otherHost,500.0);		//Connection holds for 500s
            dailyBehaviour.getMovement().setInactive(500);
        }

    }

    @Override
    public void removeConnection(DTNHost otherHost) {
        if(core.SimClock.getTime() > distributionTime && dailyBehaviour.getHost().getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT) && otherHost.getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT)) {
            this.connectedHosts.remove(otherHost);
        }
    }
}
