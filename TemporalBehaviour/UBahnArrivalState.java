package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;
import movement.MyProhibitedPolygonRwp;

import java.util.ArrayList;

/**
 * Created by Nikolas on 24.11.2015.
 */
public class UBahnArrivalState extends State {

    private Coord coordDestination;

    public UBahnArrivalState(){
        super();
        state = this;
        destinationChanged = true;
    }


    @Override
    public Coord getDestination() {
        destinationChanged = false;
        if(coordDestination == null) {
            double dx,dy;
            do {
                double r = random.nextDouble();
                dx = r * 4 - 2; //+-1m
                r = random.nextDouble();
                dy = r * 4 - 2; //+-1m
            } while (false);     //Minimal distance = 0.5m
            coordDestination = new Coord(MyProhibitedPolygonRwp.ENTRANCE_COORDS.getX() + dx, MyProhibitedPolygonRwp.ENTRANCE_COORDS.getY() + dy);
        }
        return coordDestination;
        //return ENTRANCE_COORDS;
    }

    @Override
    public void reachedDestination() {
        //dailyBehaviour.changeState(new FreetimeState(dailyBehaviour, new InitState(dailyBehaviour, null)));
        state = new FreetimeState();

        //dailyBehaviour.changeState(new StudyState(dailyBehaviour,this));
        //dailyBehaviour.changeState(new TestRoomFinger11(dailyBehaviour, this));

    }


    @Override
    public void update() {

    }


    @Override
    public void addConnection(DTNHost otherHost) { /* DO NOTHING */}

    @Override
    public void removeConnection(DTNHost otherHost) { /* DO NOTHING */}

}
