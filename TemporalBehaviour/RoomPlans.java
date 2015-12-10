package TemporalBehaviour;

import core.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class RoomPlans {

    Map<Coord, RoomPlan> roomPlansMap = new HashMap<>();

    private static RoomPlans roomPlans = null;
    public static final Coord HOERSAAL1 = new Coord(118,42);
    public static final Coord ROOMFINGER11 = new Coord(83,65);
    public static final Coord ROOMFINGER8 = new Coord(28,15);
    public static final Coord HOERSAAL2 = new Coord(91,58);
    public static final Coord HOERSAAL3 = new Coord(111,59);
    public static final Coord SEMINARROOM = new Coord(15,33);

    public static RoomPlans getRoomPlans(){
        if(roomPlans == null){
            roomPlans = new RoomPlans();
            roomPlans.initRooms();
            //roomPlans.printRoomPlans();
        }
        return roomPlans;
    }

    public RoomPlans(){

    }
    public static void initRooms(){
        //HOERSAAL1
        initRoom(HOERSAAL1,-1, new Coord(5,5));
        //ROOMFINGER11
        initRoom(ROOMFINGER11,80,new Coord(2,2));
        //ROOMFINGER8
        initRoom(ROOMFINGER8,40,new Coord(2,2));
        //HOERSAAL2
        initRoom(HOERSAAL2,-1,new Coord(2,2));
        //HOERSAAL3
        initRoom(HOERSAAL3,-1,new Coord(2,2));
        //SEMINARROOM
        initRoom(SEMINARROOM,100,new Coord(5,2));
    }
    public void printRoomPlans(){
        for(Coord coord : roomPlansMap.keySet()){
            RoomPlan roomPlan = roomPlansMap.get(coord);
            roomPlan.printPlan();
        }
    }

    private static void initRoom(Coord roomCoord, int seats, Coord roomDimension) {
        for(int i = 0; i<=4;i++) {                  //5 Blocks per day

            Random random = new Random();
            int startTime = random.nextInt(3)+1;
            switch (startTime) {
                case 1:
                    startTime = 0;
                    break;
                case 2:
                    startTime = 15*60;
                    break;
                case 3:
                    startTime = 30*60;
                    break;
            }
            double duration;
            //if(random.nextDouble()<0.2)
            //      duration = 60*60;
                    duration = 90*60+random.nextDouble()*5*60-2.5*60;
            //}
            roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK1+DailyBehaviour.BLOCK_LENGTH*i+startTime, duration, roomCoord, seats, roomDimension));

        }
    }

    public void addLecture(Lecture lecture){
        RoomPlan roomPlan = roomPlansMap.get(lecture.getCoord());
        if(roomPlan == null){
            roomPlan = new RoomPlan();
            roomPlansMap.put(lecture.getCoord(),roomPlan);
        }
        roomPlan.addLecture(lecture);
    }
    public ArrayList<Lecture> getAllLecturesAtTime(double time){
        ArrayList<Lecture>lecturesAtTime = new ArrayList<>();
        for(RoomPlan roomPlan : roomPlansMap.values()){
            for(Lecture lecture : roomPlan.getLectures()){
                if(lecture.lectureTakesPlace(time) && !lecture.isFull())
                    lecturesAtTime.add(lecture);
            }
        }
        return lecturesAtTime;
    }



}
