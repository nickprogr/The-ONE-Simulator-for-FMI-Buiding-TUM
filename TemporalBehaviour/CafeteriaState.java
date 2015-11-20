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

    public CafeteriaState(DTNHost host){
        super(host);
        stateEnterTime = SimClock.getTime();
    }

    @Override
    public Coord getDestination() {
        Coord c;
        if (!toldDestination){
            toldDestination = true;
            c = new Coord(240,130);
        }else {
            //Goal reached
            host.getMovement().setInactive(800);
            State state = new FreetimeState(host);
            c = state.getDestination();
            host.changeState(state);
        }
        return c;
    }

    @Override
    public void reachedDestination() {
        ArrayList<Lecture> lectures= host.getDailyPlan().getLecturesAtTime(SimClock.getTime());
        if( lectures.size() > 0){
            host.changeState(new LectureState(host, lectures.get(0)));
        }
    }

    public int distributionTime = 200;

    @Override
    public void initConnection(DTNHost otherHost) {
        if(core.SimClock.getTime() > stateEnterTime+distributionTime && host.getPersonType().equals(host.TYPE_STUDENT) && otherHost.getPersonType().equals(host.TYPE_STUDENT)){
            host.getConnectedHosts().put(otherHost,500.0);		//Connection holds for 500s
            host.getMovement().setInactive(500);
        }

    }

    @Override
    public void removeConnection(DTNHost otherHost) {
        if(core.SimClock.getTime() > distributionTime && host.getPersonType().equals(host.TYPE_STUDENT) && otherHost.getPersonType().equals(host.TYPE_STUDENT)) {
            host.getConnectedHosts().remove(otherHost);
        }
    }
}