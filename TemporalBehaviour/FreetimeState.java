package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class FreetimeState extends State {

    private double stateEnterTime = 0;

    public FreetimeState(DailyBehaviour dailyBehaviour, State state){
        super(dailyBehaviour, state);
        stateEnterTime = SimClock.getTime();
    }
    private Coord c;

    @Override
    public Coord getDestination() {
        this.dailyBehaviour.getHost().setName("Freetime");
        destinationChanged = false;
        if(c == null)
            reachedDestination();   //generate new random position
        return c;
    }

    @Override
    public void reachedDestination() {
        c = dailyBehaviour.getMovement().randomCoord();
        //ArrayList<Lecture> lectures= dailyBehaviour.getLecturesAtTime(SimClock.getTime());
        //if( lectures.size() > 0){
        //    dailyBehaviour.changeState(new LectureState(dailyBehaviour, this, lectures.get(0)));
        //}
    }

    @Override
    public void update() {
        ArrayList<Lecture> lectures= dailyBehaviour.getLecturesAtTime(SimClock.getTime());
        if( lectures.size() > 0){
            dailyBehaviour.changeState(new LectureState(dailyBehaviour, this, lectures.get(0)));
        }
    }

    public int distributionTime = 200;// 200;

    @Override
    public void initConnection(DTNHost otherHost) {
        if(core.SimClock.getTime() > stateEnterTime+distributionTime && dailyBehaviour.getHost().getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT) && otherHost.getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT)){
            //this.connectedHosts.put(otherHost, 500.0);		//Connection holds for 500s
            //dailyBehaviour.getMovement().setInactive(500);
        }

    }

    @Override
    public void removeConnection(DTNHost otherHost) {
        if(core.SimClock.getTime() > distributionTime && dailyBehaviour.getHost().getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT) && otherHost.getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT)) {
            //this.connectedHosts.remove(otherHost);
        }
    }
}
