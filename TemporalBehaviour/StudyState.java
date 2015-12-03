package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;

/**
 * Created by Matthias on 27.11.2015.
 */
public class StudyState extends State {
    private double stateEnterTime;

    public StudyState(DailyBehaviour dailyBehaviour, State state) {

        super(dailyBehaviour, state);
        stateEnterTime = SimClock.getTime();
    }
    private Coord c;

    @Override
    public Coord getDestination() {
        this.dailyBehaviour.getHost().setName("Studying");
        destinationChanged = false;
        if(c == null)
            c = selectPlaceToStudy();   //generate new random position
        return c;


    }
    private Coord selectPlaceToStudy(){
        if(random.nextDouble()<0.5){
            return new Coord(15,57);    //Library
        }
        return new Coord(80,27);        //Rechnerhalle
    }

    @Override
    public void reachedDestination() {
        //Goal reached
        dailyBehaviour.getMovement().setInactive(1500);
        State state = new FreetimeState(dailyBehaviour, this);
        dailyBehaviour.changeState(state);
    }

    @Override
    public void update() {
        System.out.println("update");
        ArrayList<Lecture> lectures= dailyBehaviour.getLecturesAtTime(SimClock.getTime());
        if( lectures.size() > 0){
            System.out.println("\t\tupdate lecture!!");
            this.dailyBehaviour.getMovement().setActive(true);
            dailyBehaviour.changeState(new LectureState(dailyBehaviour, this, lectures.get(0)));
        }
    }


    @Override
    public void addConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }
}
