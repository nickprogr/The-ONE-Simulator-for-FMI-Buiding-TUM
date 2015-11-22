package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

/**
 * Created by Matthias on 22.11.2015.
 */
public class UBahnArrivalState extends State {

    public UBahnArrivalState(DailyBehaviour dailyBehaviour, State state){
        super(dailyBehaviour, state);
        //stateEnterTime = SimClock.getTime();
        System.out.println("Just for Nick... ;)");
    }

    @Override
    public Coord getDestination() {
        return null;
    }

    @Override
    public void reachedDestination() {

    }

    @Override
    public void initConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }
}
