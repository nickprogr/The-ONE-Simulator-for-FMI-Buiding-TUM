package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class StudyState extends State {

    private Coord studyLocation = null;

    public StudyState(){
        super();
        state = this;
        destinationReached = false;
        studyLocation = selectStudyLocation();
        //System.out.println("LectureState");
    }

    private Coord selectStudyLocation() {
        double r = random.nextDouble();
        if(r < .25){
            return new Coord(16,50);        //Library
        }else if(r < .5){
            return new Coord(81,29);        //Rechnerhalle
        }else if(r < .5+1*0.0715){
            return new Coord(75,52);        //LearnSpot7
        }else if(r < .5+2*0.0715){
            return new Coord(60,50);        //LearnSpot6
        }else if(r < .5+3*0.0715){
            return new Coord(88,37);        //LearnSpot5
        }else if(r < .5+4*0.0715){
            return new Coord(76,36);        //LearnSpot4
        }else if(r < .5+5*0.0715){
            return new Coord(53,35);        //LearnSpot3
        }else if(r < .5+6*0.0715){
            return new Coord(44,35);        //LearnSpot2
        }else{
            return new Coord(33,33);        //LearnSpot1
        }
    }

    @Override
    public Coord getDestination() {
        //if(isActive) {
            destinationChanged = false;
            return studyLocation;
        //}
        //return null;
    }

    private double inactiveTime  = 0;
    private boolean destinationReached = false;
    @Override
    public void reachedDestination() {
        isActive = false;
        destinationReached = true;
        double studyTime = random.nextDouble()*4*60*60;
        inactiveTime = studyTime+SimClock.getTime();

        //Goal reached
        //dailyBehaviour.getMovement().setInactive(lecture.getEndTime()-SimClock.getTime());
        //state = this;
    }

    public  boolean destinationChanged() {
        //Only when changed since else a recalculation will be done regarding the movement path
        return destinationChanged;
    }

    @Override
    public boolean enableConnections() {
        return false;
    }

    @Override
    public void update() {
        if (inactiveTime >= SimClock.getTime()) {
            isActive = false;
        } else {
            isActive = true;
            if(destinationReached) {
                Random random = new Random();
                double rand = random.nextDouble();
                State newState;
                if(rand < 0.10)
                    newState = new ToiletState();
                else if(rand < .5 && rand < .1) {
                    newState = new WaitState();
                }else{
                    newState = new FreetimeState();
                }
                this.state = newState;
            }
        }
    }

    @Override
    public void addConnection(DTNHost host) {

    }

    @Override
    public void removeConnection(DTNHost host) {

    }
}
