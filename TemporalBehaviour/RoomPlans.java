package TemporalBehaviour;

import core.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthias on 18.11.2015.
 */
public class RoomPlans {

    Map<Coord, RoomPlan> roomPlansMap = new HashMap<>();

    private static RoomPlans roomPlans = null;

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
        //Room1
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK1, DailyBehaviour.LECTURE_LENGHT, new Coord(450.0, 90.0)));
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK2, DailyBehaviour.LECTURE_LENGHT, new Coord(450.0, 90.0)));
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK3, DailyBehaviour.LECTURE_LENGHT, new Coord(450.0, 90.0)));
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK4, DailyBehaviour.LECTURE_LENGHT, new Coord(450.0, 90.0)));
        //Room2
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK1, DailyBehaviour.LECTURE_LENGHT, new Coord(1000.0,800.0)));
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK4, DailyBehaviour.LECTURE_LENGHT, new Coord(1000.0,800.0)));
        roomPlans.addLecture(new Lecture(DailyBehaviour.START_BLOCK5, DailyBehaviour.LECTURE_LENGHT, new Coord(1000.0,800.0)));
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
