package TemporalBehaviour;

import java.util.ArrayList;

/**
 * Created by Matthias on 18.11.2015.
 */
public class RoomPlan {
    private ArrayList<Lecture> lectures = new ArrayList<>();

    public RoomPlan(){

    }


    public void addLecture(Lecture lecture){
        lectures.add(lecture);
    }
    public void removeLecture(Lecture lecture){
        lectures.remove(lecture);
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void printPlan() {
        if(this.lectures.size() > 0) {
            System.out.println("");
            System.out.println("Room coord: " + this.lectures.get(0).getCoord());
            for(Lecture lecture: lectures){
                System.out.println("\t- "+lecture.print());
            }
        }
    }
}
