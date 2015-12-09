package TemporalBehaviour;

import SocialBehaviour.Group;
//import TemporalBehaviour.states.UBahnDepartureState;
import core.*;
import movement.MovementModel;
import movement.MovementVector;
import movement.MyProhibitedPolygonRwp;
import movement.Path;

import java.util.*;

/**
 * Created by Matthias on 18.11.2015.
 */
public class DailyBehaviour {
    public static final double BLOCK_LENGTH = 2*60*60 ;
    //public static int counter = 0;              //hardcoded bullshit <nick>

    private RoomPlans roomPlans;
    private ArrayList<Lecture> selectedLectures = new ArrayList<>();
    private DTNHost host;
    private MovementModel movementModel;

    public static double START_BLOCK1 = 200;//8*60*60;
    public static double HOUR = 2000;//3200;
    public static double START_BLOCK2 = 2*HOUR+START_BLOCK1;
    public static double START_BLOCK3 = 2*HOUR+START_BLOCK2;
    public static double START_BLOCK4 = 2*HOUR+START_BLOCK3;
    public static double START_BLOCK5 = 2*HOUR+START_BLOCK4;
    public static double START_BLOCK6 = 2*HOUR+START_BLOCK5;
    public static double LECTURE_LENGHT = 1.0*HOUR;//1.5*HOUR;

    public static final int ARRIVAL_UBAHN_NORTH = 0;
    public static final int ARRIVAL_ELSE_NORTH = 1;
    public static final int ARRIVAL_EAST = 2;
    public static final int ARRIVAL_WEST = 3;

    private Coord location = new Coord(0,0);
    private Coord destination;
    private double speed;

    private double arrivalTime = 0;
    private double departureTime = 0;


    private Group group;
    private int arrivalType;

    //private List<MovementListener> movListeners;

    public void addDate(Lecture lecture){
        selectedLectures.add(lecture);
    }
    public MovementModel getMovement(){
        return movementModel;
    }

    private State state;
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public void addConnection(DTNHost host){
        //Only forward connection if they really know each other
        if(!(state instanceof IdleState) && !(host.getDailyBehaviour().getState() instanceof IdleState)) {
            //       if(state instanceof FreetimeState) {

//            if (SocialCliques.socialCliques.haveSharedGroup(host, this.host)) {   //TODO: Add
            Random random = new Random();

            if (this.group.getSize() <= 1) {//If not already in a group

                if (random.nextDouble() < 0.1) {
                    System.out.println("-- addGroup");
                    host.getDailyBehaviour().group.addMember(this.host);
                    this.group = host.getDailyBehaviour().group;
                    group.setInactive(50);
                }
            }
        }

 //               }
                //state.addConnection(host);
 //           }



    }
    public void removeConnection(DTNHost host){
        state.removeConnection(host);
    }

    public DailyBehaviour(DTNHost host, MovementModel movement, List<MovementListener> movLs) {
        //Settings s = new Settings(SimScenario.SCENARIO_NS);
        //this.movementModel = new MyProhibitedPolygonRwp(s);
        this.movementModel = movement.replicate();


        //if(counter < 50) {
        //    this.state = new FreetimeState(this, new InitState(this, null));
        //}
        //else {
        this.state = new IdleState();
        //}


        //counter++;  //hardoced bullshit <nick>


        this.host = host;

        this.roomPlans = RoomPlans.getRoomPlans();
        this.chooseLectures();        //Select Lectures taken through out the day
        //this.printLectures();


        setInitialLocation();

        group = new Group(host);
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
        if(lectureList.size() > 0 && random.nextDouble()<0.5)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block2
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+2*60*60);
        if(lectureList.size() > 0 && random.nextDouble()<0.8)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block3
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+2*2*60*60);
        if(lectureList.size() > 0 && random.nextDouble()<0.8)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block4
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+3*2*60*60);
        if(lectureList.size() > 0 && random.nextDouble()<0.8)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
        //block5
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+4*2*60*60);
        if(lectureList.size() > 0 && random.nextDouble()<0.5)
            selectedLectures.add(lectureList.get(random.nextInt(lectureList.size())));
    }


    private boolean firstUpdate = true;

    public void update(){
        if(firstUpdate){
            firstUpdate = false;
            //Calculate Arrival and Departure Time
            calculateArrivalAndDepartureTimes();
        }

        //this.printLectures();
        if(group.isLeader(this.host)) {
            state = state.getState(); //TODO: Newly added
        }else {
            state = group.getState();
        }
        //In normal conditions, all nodes will start from idle state
        if(state instanceof IdleState && arrivalTime <= SimClock.getTime() && departureTime > SimClock.getTime())  {
            this.location.setLocation( new ArrivalState(arrivalType).getStartPosition());
            state = new ArrivalState(arrivalType);      //state.reachedDestination();


            //this.location = state.getStartPosition();
            this.movementModel.setActive(true);

        }

        //if departureTime -> depart
        if(departureTime <= SimClock.getTime() && !(state instanceof DepartureState)&&!(state instanceof IdleState)) {
            group.removeMember(this.host);
            this.state = new DepartureState(arrivalType);
            group = new Group(this.host);
            //this.destination = this.state.getDestination();
        }
        state.update();
    }

    private void calculateArrivalAndDepartureTimes() {
        Random random = new Random();
        //arrivalTime = 0;
        double firstLecture = 0;
        double lastLecture = 0;
        for(Lecture lecture : selectedLectures){
            if(firstLecture == 0 || firstLecture > lecture.getStartTime()){
                firstLecture = lecture.getStartTime();
            }
            if(lastLecture == 0 || lastLecture < lecture.getEndTime()){
                lastLecture = lecture.getEndTime();
            }
        }
        arrivalTime = 200;//firstLecture;
        departureTime = 2000;//lastLecture;
    }


    public void printLectures() {
        System.out.println("Host: "+host.getName());
        for(Lecture lecture : this.selectedLectures){
            System.out.println("\t- "+lecture.print());
        }
    }
    private double dx = 0;
    private double dy = 0;

    public void move(double timeIncrement){
        update();
        if(state instanceof IdleState){
            //System.out.println("--> IdleState");
            this.location = new Coord(200,200);
            return;
        }
        /*if(group.isLeader(this.host)) {
            //state = state.getState();
        }else {
            //state = group.getState();
        }*/

        double possibleMovement;
        double distance;
        double dx, dy;

        if (!this.isMovementActive() || SimClock.getTime() < this.nextPathAvailable()) {
            return;
        }

        if (this.destination == null || state.destinationChanged()) {
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
            distanceExceedsNextDestinationn = true;
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
    private Path path;
    private boolean distanceExceedsNextDestinationn = false;
    private boolean clculateNextWaypoint() {

        //if(!group.isLeader(host)){
        //    return group.getDestination();
        //}

        //MovementVector vec = movementModel.getPath(destination,speed);
        if(state.getDestination().equals(this.location) || (distanceExceedsNextDestinationn && (path == null || path.getCoords().size() == 0))){
            state.reachedDestination();
            if(group.isLeader(this.host)) {
                state = state.getState(); //TODO: Newly added
                if(state instanceof IdleState)
                    return false;
            }else {
                state = group.getState();
                return false;
            }
            distanceExceedsNextDestinationn = false;
        }

        if (path == null||!path.hasNext()) {
            Coord destination = state.getDestination();
            double speed = state.getSpeed();
            //state.reachedDestination();
            if(destination != null)
                path = movementModel.getPath(this.location, destination, speed);
            else
                return false;
        }

        this.destination = path.getNextWaypoint();
        this.speed = path.getSpeed();

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

    public MovementVector getTempDestination() {
        return this.tempDestination;
    }

    public void setTempDestination(MovementVector vec) {
        this.tempDestination = vec;
    }

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

    public void setInitialLocation() {
        this.location = new Coord(200,200);     //Initial Location
        
        //Arrival Type
        Random rng = new Random();
        double r = rng.nextDouble();
        if(r < 0.2) {
            this.arrivalType = this.ARRIVAL_EAST; 
        }else if(r > 0.2 && r < 0.4) {
            this.arrivalType = this.ARRIVAL_WEST; 
        }else if(r > 0.4 && r < 0.5) {
            this.arrivalType = this.ARRIVAL_ELSE_NORTH;   
        }else {
            this.arrivalType = this.ARRIVAL_UBAHN_NORTH;
        }
        movementModel.setLastWayPoint(this.location);
    }

    public double nextPathAvailable() {
        return movementModel.nextPathAvailable();
    }

    public boolean isMovementActive() {
        //System.out.println("name: "+host.getName()+" isMovementActive: "+movementModel.isActive());
        System.out.println(host.getName()+" movementActive: "+(state.isActive() && movementModel.isActive()));
        return state.isActive() && movementModel.isActive();
        //return movementModel.isActive();
    }

    public Coord getLocation() {
        return location;
    }

    public void setLocation(Coord location) {
        this.location = location;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }
}
