package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

/**
 * Created by Nikolas on 24.11.2015.
 */
public class UBahnDepartureState extends State {

    public static Coord UBAHN_COORDS = new Coord(150, 0);//new Coord(190.0, -93);

    public UBahnDepartureState(DailyBehaviour dailyBehaviour, State state){
        super(dailyBehaviour, state);
    }

    @Override
    public Coord getDestination() {
        return UBAHN_COORDS;
    }

    @Override
    public void reachedDestination() {
        if(dailyBehaviour.getLocation().equals(UBAHN_COORDS))
            dailyBehaviour.changeState(new IdleState(dailyBehaviour, this));
        else
            dailyBehaviour.changeState(new UBahnDepartureState(dailyBehaviour, this));
    }

    @Override
    public void update() {

    }


    public int distributionTime = 200;// 200;


    @Override
    public void addConnection(DTNHost otherHost) { /* DO NOTHING */}

    @Override
    public void removeConnection(DTNHost otherHost) { /* DO NOTHING */}
}
