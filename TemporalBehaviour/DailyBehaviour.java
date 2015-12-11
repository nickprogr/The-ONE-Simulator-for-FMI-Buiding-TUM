package TemporalBehaviour;

import SocialBehaviour.Group;
//import TemporalBehaviour.states.UBahnDepartureState;
import SocialBehaviour.ReportHelper;
import SocialBehaviour.SocialCliques;
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

    public static double START_BLOCK1 = 200;// 2*60*60;// 8*60*60;//200;
    public static double MAX_LECTURE_DELAY = 30*60;

    public static final int ARRIVAL_UBAHN_NORTH = 0;
    public static final int ARRIVAL_ELSE_NORTH = 1;
    public static final int ARRIVAL_EAST = 2;
    public static final int ARRIVAL_WEST = 3;

    private Coord location = new Coord(0,0);
    private Coord destination;
    private double speed;
    private boolean skipLecture = false;

    private double arrivalTime = 0;
    private double departureTime = 0;

    public ReportHelper reportHelper = new ReportHelper();

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
        if (this.state == null || this.state.getID() != state.getID()) {
            this.state = state;
            //System.out.println("state "+state);
            this.destination = null;        //To stop current movement
            this.path = null;
            skipLecture = false;

        }
        if(state instanceof  ToiletState && ((ToiletState) state).getToilet() == null ){
            ((ToiletState) state).selectToilet(this.location);
        }
    }

//    public void addConnection(DTNHost otherHost) {
//        //Only forward connection if they really know each other
//        //if(!(state instanceof IdleState) && !(host.getDailyBehaviour().getState() instanceof IdleState)) {
//        if (state.enableConnections() && otherHost.getDailyBehaviour().getState().enableConnections()) {
//            //       if(state instanceof FreetimeState) {
//
//            if (SocialCliques.socialCliques.haveSharedGroup(otherHost, this.host)) {
//                Random random = new Random();
//
//                if (this.group.getSize() <= 1 && host.getDailyBehaviour().group.getSize() < 6) {//If not already in a group and other group has not more than already 5 members
//
//                    if (random.nextDouble() < 0.1) {
//                        //System.out.println("-- addGroup");
//                        otherHost.getDailyBehaviour().group.addMember(this.host);
//                        this.group = otherHost.getDailyBehaviour().group;
//                        group.setInactive(50);
//                    }
//                }
//            }
//        }
//    }

    public Group getGroup(){
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
        //if(group.getMembers().size() > 1) {
        //    System.out.println("Groupsize " + group.getMembers().size());
        //}
    }

    public void removeConnection(DTNHost host){
        state.removeConnection(host);
    }

    public DailyBehaviour(DTNHost host, MovementModel movement, List<MovementListener> movLs) {
        //Settings s = new Settings(SimScenario.SCENARIO_NS);
        //this.movementModel = new MyProhibitedPolygonRwp(s);
        this.movementModel = movement.replicate();
        this.movementModel.setHost(host);


        //if(counter < 50) {
        //    this.state = new FreetimeState(this, new InitState(this, null));
        //}
        //else {
        setState(new IdleState());
        //}


        //counter++;  //hardoced bullshit <nick>


        this.host = host;

        this.roomPlans = RoomPlans.getRoomPlans();
        this.chooseLectures();        //Select Lectures taken through out the day
        //addDate(new Lecture(50, 200, new Coord(28,15)));
        //addDate(new Lecture(500, 200, new Coord(118,42)));
        this.printLectures();


        setInitialLocation();

        setGroup(new Group(host));
    }

    public DTNHost getHost(){
        return host;
    }

    public void chooseLectures() {
        Random random = new Random();

        //block1
        Lecture lecture;
        ArrayList<Lecture> lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+MAX_LECTURE_DELAY);
        if(lectureList.size() > 0 && random.nextDouble()<0.5) {
            lecture = lectureList.get(random.nextInt(lectureList.size()));
            selectedLectures.add(lecture);
            lecture.addStudent();
        }
        //block2
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+2*60*60+MAX_LECTURE_DELAY);
        if(lectureList.size() > 0 && random.nextDouble()<0.8){
            lecture = lectureList.get(random.nextInt(lectureList.size()));
            selectedLectures.add(lecture);
            lecture.addStudent();
        }
        //block3
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+2*2*60*60+MAX_LECTURE_DELAY);
        if(lectureList.size() > 0 && random.nextDouble()<0.8){
            lecture = lectureList.get(random.nextInt(lectureList.size()));
            selectedLectures.add(lecture);
            lecture.addStudent();
        }
        //block4
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+3*2*60*60+MAX_LECTURE_DELAY);
        if(lectureList.size() > 0 && random.nextDouble()<0.8){
            lecture = lectureList.get(random.nextInt(lectureList.size()));
            selectedLectures.add(lecture);
            lecture.addStudent();
        }
        //block5
        lectureList = roomPlans.getAllLecturesAtTime(START_BLOCK1+4*2*60*60+MAX_LECTURE_DELAY);
        if(lectureList.size() > 0 && random.nextDouble()<0.5){
            lecture = lectureList.get(random.nextInt(lectureList.size()));
            selectedLectures.add(lecture);
            lecture.addStudent();
        }
    }


    private boolean firstUpdate = true;

    public void update() {
        if (firstUpdate) {
            firstUpdate = false;
            //Calculate Arrival and Departure Time
            calculateArrivalAndDepartureTimes();
        }

        //this.printLectures();

        State newState;
        if (group.isLeader(this.host)) {
            newState = state.getState();
        } else {
            newState = group.getState();
        }
        //System.out.println("c "+group.getSize());

        if (state.getID() != newState.getID() && group.getSize() <= 1) { //&& group.getSize() > 1){
            //Consider to change group
            Random rand = new Random();
            if(rand.nextDouble()<0.7) {             //Remove from group and change state and make a new group
                group.removeMember(this.host);
                setState(state.switchState(30,0,5,15,40,10));
                setGroup(new Group(this.host));
            }
        }else{
            this.setState(newState);
        }


        if(!(state instanceof LectureState)&&!(state instanceof IdleState)&&!(state instanceof ArrivalState)&&!(state instanceof DepartureState)) {
            ArrayList<Lecture> lectures = this.getLecturesAtTime(SimClock.getTime());

            if (lectures.size() > 0 && !skipLecture) {
                Random random = new Random();
                if(random.nextDouble() < 0.4){
                    skipLecture = true;
                }else {
                    group.removeMember(this.host);
                    this.setState(new LectureState(lectures.get(lectures.size() - 1)));       //Always take lecture from behind since first priority are workGroups and afterwards Lectures
                    group = new Group(this.host);
                }
            }
        }

        //In normal conditions, all nodes will start from idle state
        if(state instanceof IdleState && arrivalTime <= SimClock.getTime() && departureTime > SimClock.getTime())  {
            this.location.setLocation( new ArrivalState(arrivalType).getStartPosition());
            setState(new ArrivalState(arrivalType));      //state.reachedDestination();

            //this.location = state.getStartPosition();
            this.movementModel.setActive(true);
        }

        //if departureTime -> depart
        if(departureTime <= SimClock.getTime() && !(state instanceof DepartureState)&&!(state instanceof IdleState)) {
            group.removeMember(this.host);
            setState(new DepartureState(arrivalType));
            group = new Group(this.host);
        }
        state.update();
    }

    private void calculateArrivalAndDepartureTimes() {
        //Random random = new Random();
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
        Random random = new Random();
        double earlier = random.nextDouble()*120*60;
        double later = random.nextDouble()*120*60;
        arrivalTime = firstLecture-earlier;//200;//
        departureTime = lastLecture+later;//200000;//
        //printLectures();
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

        double possibleMovement;
        double distance;
        double dx, dy;

        if (!this.isMovementActive() || SimClock.getTime() < this.nextPathAvailable()) {
            return;
        }
        //if(SimClock.getTime()>=700)
            //System.out.println("700");

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

        if(state.getDestination().equals(this.location)){// || (distanceExceedsNextDestinationn && (path == null || path.getCoords().size() == 0))){
            state.reachedDestination();
            if(group.isLeader(this.host)) {
                setState(state.getState());
               // if(state instanceof IdleState)
               //     return false;
            }else {
                setState(group.getState());
                return false;
            }
            if(!state.isActive)
                return false;
            distanceExceedsNextDestinationn = false;
        }

        if (path == null || !path.hasNext()) {
            Coord destination = state.getDestination();
            double speed = state.getSpeed();
            //state.reachedDestination();
            if(destination != null) {
                path = movementModel.getPath(this.location, destination, speed);
                //System.out.println("Path");
                //System.out.println(path);

            }else
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
        //if(state instanceof LectureState)
        //    System.out.println(host.getName()+" state Active: "+(state.isActive()));
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
