package TemporalBehaviour;

import core.*;
import movement.MovementModel;
import movement.MovementVector;
import movement.MyProhibitedPolygonRwp;

import java.util.*;

/**
 * Created by Matthias on 18.11.2015.
 */
public class DailyBehaviour {
    private RoomPlans roomPlans;
    private ArrayList<Lecture> selectedLectures = new ArrayList<>();
    private DTNHost host;
    private MovementModel movementModel;

    public static double START_BLOCK1 = 2000;//2000;
    public static double HOUR = 2000;//3200;
    public static double START_BLOCK2 = 2*HOUR+START_BLOCK1;
    public static double START_BLOCK3 = 2*HOUR+START_BLOCK2;
    public static double START_BLOCK4 = 2*HOUR+START_BLOCK3;
    public static double START_BLOCK5 = 2*HOUR+START_BLOCK4;
    public static double START_BLOCK6 = 2*HOUR+START_BLOCK5;
    public static double LECTURE_LENGHT = 1.0*HOUR;//1.5*HOUR;

    private Coord location = new Coord(0,0);
    private Coord destination;
    private double speed;

    //private List<MovementListener> movListeners;


    public MovementModel getMovement(){
        return movementModel;
    }

    private State state;
    public State getState() {
        return state;
    }

    public void addConnection(DTNHost host){
        state.initConnection(host);
    }
    public void removeConnection(DTNHost host){
        state.removeConnection(host);
    }

    public DailyBehaviour(DTNHost host, MovementModel movement, List<MovementListener> movLs){
        //Settings s = new Settings(SimScenario.SCENARIO_NS);
        //this.movementModel = new MyProhibitedPolygonRwp(s);
        this.movementModel = movement.replicate();
        this.state = new FreetimeState(this, new InitState(this, null));
        this.roomPlans = RoomPlans.getRoomPlans();
        this.host = host;
        setInitialLocation();


//        this.movListeners = movLs;
//        if (movLs != null) { // inform movement listeners about the location
//            for (MovementListener l : movLs) {
//                l.initialLocation(host, this.location);
//            }
//        }
    }
    private void setEntryLocation(){
        Random rng = new Random();
        switch (2){//rng.nextInt(3)) {
            case 0:
                this.location.setLocation(450.0, 90.0);
                break;
            case 1:
                this.location.setLocation(490.0,220.0);
                break;
            case 2:
                this.location.setLocation(60.0,200.0);
                break;
        }
    }


    public void changeState(State state){
        this.state = state;
    }
    public DTNHost getHost(){
        return host;
    }

    public void chooseLectures() {
        Random random = new Random();

        //block1
        ArrayList<Lecture> lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1);
        if(lectureList.size() > 0)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block2
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK2);
        if(lectureList.size() > 0)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block3
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK3);
        if(lectureList.size() > 0)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block4
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK4);
        if(lectureList.size() > 0)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block5
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK5);
        if(lectureList.size() > 0)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
    }

    public void update(){

    }
    public void printLectures() {
        System.out.println("Host: "+host.getName());
        for(Lecture lecture : this.selectedLectures){
            System.out.println("\t- "+lecture.print());
        }
    }
    public void move1(double timeIncrement){
        this.location.setLocation(state.getDestination());
    }

    public void move(double timeIncrement){
        double possibleMovement;
        double distance;
        double dx, dy;

        if (!this.isMovementActive() || SimClock.getTime() < this.nextPathAvailable()) {
            return;
        }
        if (this.destination == null) {
            if (!clculateNextWaypoint()) {
                return;
            }
        }


        possibleMovement = timeIncrement * state.getSpeed();
        distance = this.location.distance(this.destination);

        while (possibleMovement >= distance) {
            // node can move past its next destination
            this.location.setLocation(this.destination); // snap to destination
            //this.getState().reachedDestination();
            possibleMovement -= distance;
            if (!clculateNextWaypoint()) { // get a new waypoint
                return; // no more waypoints left
            }
            distance = this.location.distance(this.destination);
        }

        // move towards the point for possibleMovement amount
        dx = (possibleMovement/distance) * (this.destination.getX() -
                this.location.getX());
        dy = (possibleMovement/distance) * (this.destination.getY() -
                this.location.getY());

        this.location.translate(dx, dy);
    }
    /**
     * Sets the next destination and speed to correspond the next waypoint
     * on the path.
     * @return True if there was a next waypoint to set, false if node still
     * should wait
     */
    private boolean clculateNextWaypoint() {
        //if (path == null) {
        //    path = this.getPath();
        //}

        if(tempDestination == null){
            state.reachedDestination();
        }

        MovementVector vec = movementModel.getPath(state.getDestination(),state.getSpeed());
        //Check path is within building
        if(movementModel.pathIntersects(this.location,vec.coord) || tempDestination != null){
            if(!intersectionAvoidingWay1)
            //move to the center of the main hall
                vec = calculateNonIntersectingWay1(vec);
            else if(!intersectionAvoidingWay2)
                vec = calculateNonIntersectingWay2(tempDestination);
            else {
                intersectionAvoidingWay2 = false;
                vec = tempDestination;
                tempDestination = null;
            }

        }
        //System.out.println("intersect: "+movementModel.pathIntersects(new Coord(60,100),new Coord(60,200)));
        //System.out.println("intersect: "+movementModel.pathIntersects(new Coord(60,100),new Coord(450.0, 90.0)));

        if (vec == null) {
            return false;
        }

        this.destination = vec.coord;
        this.speed = vec.speed;

//        if (this.movListeners != null) {
//            for (MovementListener l : this.movListeners) {
//                l.newDestination(this, this.destination, this.speed);
//            }
//        }
        return true;
    }

    public ArrayList<Lecture> getLecturesAtTime(double time){
        ArrayList<Lecture>lecturesAtTime = new ArrayList<>();
        for(Lecture lecture : selectedLectures){
            if(lecture.lectureTakesPlace(time))
                lecturesAtTime.add(lecture);
        }
        return lecturesAtTime;

    }

    private MovementVector tempDestination = null;
    private boolean intersectionAvoidingWay1 = false;
    private boolean intersectionAvoidingWay2 = false;

    private MovementVector calculateNonIntersectingWay1(MovementVector vec){
        //save destination
        tempDestination = vec;
        intersectionAvoidingWay1 = true;
        //go to middle of main hall -> y = 310
        MovementVector middleCoord = new MovementVector((new Coord(location.getX(),310)),tempDestination.speed);
        return middleCoord;
    }

    private MovementVector calculateNonIntersectingWay2(MovementVector vec){
        intersectionAvoidingWay1 = false;
        intersectionAvoidingWay2 = true;
        if(vec != null){
            MovementVector middleCoord = new MovementVector((new Coord(vec.coord.getX(),310)),tempDestination.speed);
            return middleCoord;}
        return null;
    }

//    private Coord calculateNonIntersectingWay3(MovementVector vec){
//        intersectionAvoidingWay = true;
//        //go to middle of main hall -> y = 310
//        Coord middleCoord = new Coord(vec.coord.getX(),vec.coord.getX());
//        return middleCoord;
//    }

    public void setInitialLocation() {
        setEntryLocation();
        movementModel.setLastWayPoint(this.location);
    }

    public double nextPathAvailable() {
        return movementModel.nextPathAvailable();
    }

    public boolean isMovementActive() {
        //System.out.println("name: "+host.getName()+" isMovementActive: "+movementModel.isActive());
        return movementModel.isActive();
    }

    public Coord getLocation() {
        return location;
    }

    public void setLocation(Coord location) {
        this.location = location;
    }
}