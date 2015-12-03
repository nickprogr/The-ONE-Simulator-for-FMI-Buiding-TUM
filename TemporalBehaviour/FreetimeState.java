package TemporalBehaviour;

import SocialBehaviour.SocialCliques;
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
        if(random.nextDouble() < 0.02){
            //dailyBehaviour.changeState(new StudyState(dailyBehaviour, this));
        }
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

    public int distributionTime = 150;// 200;

    @Override
    public void addConnection(DTNHost socialKnownHost) {
        if (core.SimClock.getTime() < stateEnterTime + distributionTime)
            return;

        Random rand = new Random();
        double randDouble = rand.nextDouble();
        if (randDouble < 1) {//0.10) {
            this.connectedHosts.put(socialKnownHost, 200.0);        //Connection holds for 500s
            dailyBehaviour.getMovement().setInactive(200);
        } else {
            socialKnownHost.removeConnection(this.dailyBehaviour.getHost());
        }
    }

    @Override
    public void removeConnection(DTNHost otherHost) {
        //if(core.SimClock.getTime() > distributionTime && dailyBehaviour.getHost().getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT) && otherHost.getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT)) {
        this.connectedHosts.remove(otherHost);
        if(connectedHosts.size() == 0){
            this.dailyBehaviour.getMovement().setActive(true);
        }
        //}
    }
}
