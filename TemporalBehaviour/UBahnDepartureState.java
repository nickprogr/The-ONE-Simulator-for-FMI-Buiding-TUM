package TemporalBehaviour;

import TemporalBehaviour.DailyBehaviour;
import TemporalBehaviour.IdleState;
import TemporalBehaviour.State;
import core.Coord;
import core.DTNHost;

/**
 * Created by Nikolas on 24.11.2015.
 */
public class UBahnDepartureState extends State {

    public static Coord UBAHN_COORDS = new Coord(140, 0);//150,0  //new Coord(190.0, -93);

    public UBahnDepartureState(){
        super();
        state = this;
    }

    @Override
    public Coord getDestination() {
        destinationChanged = false;
        return UBAHN_COORDS;
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
