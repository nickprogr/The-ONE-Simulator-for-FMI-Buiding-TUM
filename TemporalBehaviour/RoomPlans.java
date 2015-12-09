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

    public static RoomPlans getRoomPlans(){
        if(roomPlans == null){
            roomPlans = new RoomPlans();
            roomPlans.initRooms();
        }
        return roomPlans;
    }

    public RoomPlans(){

    }
    public static void initRooms(){
        //HOERSAAL1
        initRoom(HOERSAAL1);
        //ROOMFINGER11
        initRoom(ROOMFINGER11);
        //ROOMFINGER8
        initRoom(ROOMFINGER8);
    }

    private static void initRoom(Coord roomCoord) {
        for(int i = 0; i<=4;i++) {                  //5 Blocks per day

            Random random = new Random();
            //if(random.nextDouble() < 3/5) {         //3 blocks should be held per room
            int startTime = random.nextInt(3);
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
            int duration = random.nextInt(2);
            switch (duration) {
                case 0:
                    duration = 60*60;
                    break;
                case 1:
                    duration = 90*60;
                    break;
               //}
            }
            roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK1+DailyBehaviour.BLOCK_LENGTH*i+startTime, duration, roomCoord));

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
                if(lecture.lectureTakesPlace(time))
                    lecturesAtTime.add(lecture);
            }
        }
        return lecturesAtTime;
    }



}
