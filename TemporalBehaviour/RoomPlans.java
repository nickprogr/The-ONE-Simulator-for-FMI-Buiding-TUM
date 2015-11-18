package TemporalBehaviour;

import core.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthias on 18.11.2015.
 */
public class RoomPlans {

    Map<Coord, RoomPlan> roomPlans = new HashMap<>();

    public RoomPlans(){

    }
    public void addLecture(Lecture lecture){
        RoomPlan roomPlan = roomPlans.get(lecture.getCoord());
        if(roomPlan == null){
            roomPlan = new RoomPlan();
            roomPlans.put(lecture.getCoord(),roomPlan);
        }
        roomPlan.addLecture(lecture);
    }
    public ArrayList<Lecture> getAllLecturesAtTime(double time){
        ArrayList<Lecture>lecturesAtTime = new ArrayList<>();
        for(RoomPlan roomPlan : roomPlans.values()){
            for(Lecture lecture : roomPlan.getLectures()){
                if(lecture.lectureTakesPlace(time))
                    lecturesAtTime.add(lecture);
            }
        }
        return lecturesAtTime;
    }



}
