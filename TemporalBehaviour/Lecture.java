package TemporalBehaviour;

/**
 * Created by Matthias on 18.11.2015.
 */
    import core.Coord;

public class Lecture {


    private double startTime;
    private double endTime;
    private double length;
    private Coord room;
    private int lectureID;
    public static int current_id_count = 0;
    public int seats;
    public Coord roomDimensions;
    //private Lecturer

    public Lecture(double startTime, double length, Coord room, int seats, Coord roomDimensions){
        current_id_count = current_id_count + 1;
        setLectureID(current_id_count);
        getCoord(room);
        setStartTime(startTime);
        setLength(length);
        setEndTime(startTime + length);
        setSeats(seats);
        setRoomDimensions(roomDimensions);

    }
    public Coord getRoomDimensions(){
        //When room (Coord) is the center
        return roomDimensions;

    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public Coord getCoord() {
        return room;
    }

    public void getCoord(Coord room) {
        this.room = room;
    }
    public int getLectureID() {
        return lectureID;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean lectureTakesPlace(double time){
        if(startTime <= time && endTime >= time){
            return true;
        }
        return false;
    }

    public String print() {
        String retString = this.getLectureID()+" room: "+getCoord()+" start: "+getStartTime()+" end: "+getEndTime();
        return retString;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    public void addStudent(){
        this.seats -= 1;
    }

    public boolean isFull() {
        return (seats == 0);        //also return -1 and less because these are rooms with infinite size
    }

    public void setRoomDimensions(Coord roomDimensions) {
        this.roomDimensions = roomDimensions;
    }
}
