package TemporalBehaviour;

import core.Coord;
import core.DTNHost;
import movement.MyProhibitedPolygonRwp;

/**
 * Created by Matthias on 09.12.2015.
 */
public class DepartureState extends State {
        private Coord coordDestination;
        private int arrivalType;

        public DepartureState(int type){
            super();
            destinationChanged = true;
            arrivalType = type;
            state = this;
        }

        public Coord getEndPosition(){
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
        /*public Coord getEntrancePos(){
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
        }*/

        private boolean reachedDestination = false;
        @Override
        public Coord getDestination() {
            System.out.println("DepartureState: getDestination "+reachedDestination);
            if(reachedDestination){
                return null;
            }
            destinationChanged = false;
            if(coordDestination == null) {
                double dx = 0;
                double dy = 0;
                do {
                    double r = random.nextDouble();
                    //dx = r * 4 - 2; //+-1m
                    r = random.nextDouble();
                   // dy = r * 4 - 2; //+-1m
                } while (false);     //Minimal distance = 0.5m
                /*if(!entranceReached) {
                    coordDestination = getEntrancePos();
                }else{*/
                coordDestination = getEndPosition();
                //}
                coordDestination = new Coord(coordDestination.getX() + dx, coordDestination.getY() + dy);
            }
            return coordDestination;
            //return ENTRANCE_COORDS;
        }

        @Override
        public void reachedDestination() {
            reachedDestination = true;
            System.out.println("DepartureState: reachedDestination");

            isActive = false;
            state = new IdleState();

            //entranceReached = true;
            //destinationChanged = true;
            //dailyBehaviour.changeState(new FreetimeState(dailyBehaviour, new InitState(dailyBehaviour, null)));

            //state = new FreetimeState();

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

