package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import core.SimClock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class TestRoomFinger8 extends State {


    public TestRoomFinger8(DailyBehaviour dailyBehaviour, State state){
        super(dailyBehaviour, state);
    }

    @Override
    public Coord getDestination() {
        return RoomPlans.ROOMFINGER8;
    }

    @Override
    public void reachedDestination() {
        dailyBehaviour.changeState(new TestRoomFinger11(dailyBehaviour, this));
    }

    @Override
    public void update() {

    }


    @Override
    public void initConnection(DTNHost otherHost) {
    }

    @Override
    public void removeConnection(DTNHost otherHost) {
    }
}
