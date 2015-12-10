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
        if(r < .35){
            return new Coord(15+(random.nextDouble()*24-12),55+(random.nextDouble()*18-9));        //Library
        }else if(r < .5){
            return new Coord(81+(random.nextDouble()*14-7),29+(random.nextDouble()*2-1));        //Rechnerhalle
        }else if(r < .5+1*0.0715){
            return new Coord(75+(random.nextDouble()*4-2),52+(random.nextDouble()*3-1.5));        //LearnSpot7
        }else if(r < .5+2*0.0715){
            return new Coord(60+(random.nextDouble()*4-2),50+(random.nextDouble()*3-1.5));         //LearnSpot6
        }else if(r < .5+3*0.0715){
            return new Coord(88+(random.nextDouble()*4-2),37+(random.nextDouble()*3-1.5));        //LearnSpot5
        }else if(r < .5+4*0.0715){
            return new Coord(76+(random.nextDouble()*4-2),36+(random.nextDouble()*3-1.5));        //LearnSpot4
        }else if(r < .5+5*0.0715){
            return new Coord(60+(random.nextDouble()*4-2),50+(random.nextDouble()*3-1.5));        //LearnSpot3
        }else if(r < .5+6*0.0715){
            return new Coord(44+(random.nextDouble()*4-2),35+(random.nextDouble()*3-1.5));        //LearnSpot2
        }else{
            return new Coord(33+(random.nextDouble()*4-2),33+(random.nextDouble()*3-1.5));        //LearnSpot1
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
        double r = random.nextDouble();
        double factor = 0;
        if(r < 0.8){
                factor = 20*60;      //short time period
        }else{
            factor = 4*60*60;
        }
        double studyTime = random.nextDouble()*factor;
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
                state = switchState(30,0,15,15,30,10);
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
