package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import movement.MyProhibitedPolygonRwp;

/**
 * Created by Matthias on 09.12.2015.
 */
public class ArrivalState extends State {

        private Coord coordDestination;
        private int arrivalType;

        public ArrivalState(int type){
            super();
            state = this;
            destinationChanged = true;
            arrivalType = type;
        }

        public Coord getStartPosition(){
            switch (arrivalType){
                case DailyBehaviour.ARRIVAL_UBAHN_NORTH:
                    return MyProhibitedPolygonRwp.UBAHN_COORDS;
                case DailyBehaviour.ARRIVAL_ELSE_NORTH:
                    return MyProhibitedPolygonRwp.BIKE_NORTH_COORDS;
                case DailyBehaviour.ARRIVAL_EAST:
                    return MyProhibitedPolygonRwp.BIKE_EAST_COORDS;
                case DailyBehaviour.ARRIVAL_WEST:
                    return MyProhibitedPolygonRwp.BIKE_WEST_COORDS;
            }
            return null;
        }
        public Coord getEntrancePos(){
            switch (arrivalType){
                case DailyBehaviour.ARRIVAL_UBAHN_NORTH:
                    return MyProhibitedPolygonRwp.ENTRANCE_NORTH;
                case DailyBehaviour.ARRIVAL_ELSE_NORTH:
                    return MyProhibitedPolygonRwp.ENTRANCE_NORTH;
                case DailyBehaviour.ARRIVAL_EAST:
                    return MyProhibitedPolygonRwp.ENTRANCE_EAST;
                case DailyBehaviour.ARRIVAL_WEST:
                    return MyProhibitedPolygonRwp.ENTRANCE_WEST;
            }
            return null;
        }


        @Override
        public Coord getDestination() {
            destinationChanged = false;
            if(coordDestination == null) {
                double dx = 0;
                double dy = 0;
                do {
                    double r = random.nextDouble();
                    dx = r * 2 - 1; //+-1m
                    r = random.nextDouble();
                    dy = r * 2 - 1; //+-1m
                } while (false);     //Minimal distance = 0.5m
                coordDestination = getEntrancePos();
                coordDestination = new Coord(coordDestination.getX() + dx, coordDestination.getY() + dy);
            }
            return coordDestination;
            //return ENTRANCE_COORDS;
        }

        @Override
        public void reachedDestination() {
            state = new FreetimeState();

        }


        @Override
        public void update() {

        }


        @Override
        public void addConnection(DTNHost otherHost) { /* DO NOTHING */}

        @Override
        public void removeConnection(DTNHost otherHost) { /* DO NOTHING */}

    @Override
    public boolean enableConnections() {
        return true;
    }

}
