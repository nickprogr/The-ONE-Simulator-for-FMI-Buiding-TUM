package TemporalBehaviour;

import TemporalBehaviour.DailyBehaviour;
import TemporalBehaviour.IdleState;
import TemporalBehaviour.State;
import core.Coord;
import core.DTNHost;
import movement.MyProhibitedPolygonRwp;

/**
 * Created by Nikolas on 24.11.2015.
 */
public class UBahnDepartureState extends State {

    public UBahnDepartureState(){
        super();
        state = this;
    }

    @Override
    public Coord getDestination() {
        destinationChanged = false;
        return MyProhibitedPolygonRwp.UBAHN_COORDS;
    }

    @Override
    public void reachedDestination() {
        state = new FinalState();//IdleState();
        /*if(dailyBehaviour.getLocation().equals(UBAHN_COORDS))
            dailyBehaviour.changeState(new IdleState(dailyBehaviour, this));
        else
            dailyBehaviour.changeState(new UBahnDepartureState(dailyBehaviour, this));
            */
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
