package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class WaitState extends State {

    double waitUntilTime = 0;
    double saveStartTime;

    public WaitState(){
        super();
        state = this;
        saveStartTime = SimClock.getTime();
        isActive = true;
    }


    private Coord c = null;
    private boolean stopMovement = false;

    @Override
    public Coord getDestination() {

        destinationChanged = false;
        if(stopMovement){
            destinationChanged = false;
            //System.out.println("WaitState: getDestination wait");
            return null;
        }
        if(c == null) {
            //System.out.println("WaitState: getDestination coord");
            //generate new random position
            double rand = random.nextDouble();
            if(rand < 0.3){
                c = new Coord(22,35);
            }else if(rand < 0.6) {
                c = new Coord(107, 50);
            }else{
                c = new Coord(83, 65);
            }
            double dx = 0;
            double dy = 0;
            do {
                double r = random.nextDouble();
                //dx = r * 2 - 1; //+-1m
                //r = random.nextDouble();
                //dy = r * 2 - 1; //+-1m
            } while (false);     //Minimal distance = 0.5m
            c = new Coord(c.getX() + dx, c.getY() + dy);
        }
        return c;
    }

    @Override
    public void reachedDestination() {
        //System.out.println("Wait: ReachedDestination");
        isActive = false;
    }

    public  boolean destinationChanged() {
        //Only when changed since else a recalculation will be done regarding the movement path
        return destinationChanged;
    }

    @Override
    public boolean enableConnections() {
        return true;
    }

    @Override
    public void update() {
        //walk 0.2sek in some direction
       // System.out.println("WaitState: update wait state");
        if(waitUntilTime == 0) {
            if (saveStartTime + 3 < SimClock.getTime()) {       //Only walk 3sek away from entrance
                double timeOnToilet = random.nextDouble() * 5 * 60;     //between 0min and 5min
                waitUntilTime = timeOnToilet + SimClock.getTime();
                isActive = false;
                destinationChanged = true;
            }
        }else{
            if (waitUntilTime >= SimClock.getTime()) {
                isActive = false;
            } else {
                isActive = true;
                Random random = new Random();
                double rand = random.nextDouble();
                State newState;
                //if(rand < 0.40)
                //    newState = new CafeteriaState(dailyBehaviour, this);
                //else
                newState = new FreetimeState();
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
