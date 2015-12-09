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

    public FreetimeState(){
        super();
        state = this;
        stateEnterTime = SimClock.getTime();

    }
    private Coord c;

    @Override
    public Coord getDestination() {
        destinationChanged = false;
        if(c == null) {
            //generate new random position
            double rand = random.nextDouble();
            if(rand < 0.3){
                c = new Coord(22,35);
            }else if(rand < 0.6) {
                c = new Coord(107, 50);
            }else{
                c = new Coord(83, 65);
            }
            double dx,dy;
            do {
                double r = random.nextDouble();
                dx = r * 2 - 1; //+-1m
                r = random.nextDouble();
                dy = r * 2 - 1; //+-1m
            } while (false);     //Minimal distance = 0.5m
            c = new Coord(c.getX() + dx, c.getY() + dy);
        }
        return c;
    }

    @Override
    public void reachedDestination() {
        //c = dailyBehaviour.getMovement().randomCoord();


        if(random.nextDouble() < 0.01){
            //dailyBehaviour.changeState(new StudyState(dailyBehaviour, this));
            state = new ToiletState();
        }else{
            state = new FreetimeState();
        }
        //ArrayList<Lecture> lectures= dailyBehaviour.getLecturesAtTime(SimClock.getTime());
        //if( lectures.size() > 0){
        //    dailyBehaviour.changeState(new LectureState(dailyBehaviour, this, lectures.get(0)));
        //}
    }

    @Override
    public void update() {

    }

    public int distributionTime = 150;// 200;

    @Override
    public void addConnection(DTNHost socialKnownHost) {
        if (core.SimClock.getTime() < stateEnterTime + distributionTime)
            return;

        /*Random rand = new Random();
        double randDouble = rand.nextDouble();
        if (randDouble < 1) {//0.10) {
            this.connectedHosts.put(socialKnownHost, 200.0);        //Connection holds for 500s
            dailyBehaviour.getMovement().setInactive(200);
        } else {
            socialKnownHost.removeConnection(this.dailyBehaviour.getHost());
        }*/
    }

    @Override
    public void removeConnection(DTNHost otherHost) {
        /*
        //if(core.SimClock.getTime() > distributionTime && dailyBehaviour.getHost().getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT) && otherHost.getPersonType().equals(dailyBehaviour.getHost().TYPE_STUDENT)) {
        this.connectedHosts.remove(otherHost);
        if(connectedHosts.size() == 0){
            this.dailyBehaviour.getMovement().setActive(true);
        }
        //}*/
    }

    @Override
    public boolean enableConnections() {
        return true;
    }
}
