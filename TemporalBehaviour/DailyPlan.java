package TemporalBehaviour;

import core.DTNHost;
import core.SimClock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matthias on 18.11.2015.
 */
public class DailyPlan {
    private RoomPlans roomPlans;
    private ArrayList<Lecture> selectedLectures = new ArrayList<>();
    private DTNHost host;

    public static double START_BLOCK1 = 2000;//2000;
    public static double HOUR = 2000;//3200;
    public static double START_BLOCK2 = 2*HOUR+START_BLOCK1;
    public static double START_BLOCK3 = 2*HOUR+START_BLOCK2;
    public static double START_BLOCK4 = 2*HOUR+START_BLOCK3;
    public static double START_BLOCK5 = 2*HOUR+START_BLOCK4;
    public static double START_BLOCK6 = 2*HOUR+START_BLOCK5;
    public static double LECTURE_LENGHT = 1.0*HOUR;//1.5*HOUR;

    public DailyPlan(RoomPlans roomPlans){
        this.roomPlans = roomPlans;
    }
    public void setHost(DTNHost host){
        this.host = host;
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

    public ArrayList<Lecture> getLecturesAtTime(double time){
        ArrayList<Lecture>lecturesAtTime = new ArrayList<>();
        for(Lecture lecture : selectedLectures){
            if(lecture.lectureTakesPlace(time))
                lecturesAtTime.add(lecture);
        }
        return lecturesAtTime;

    }
}
