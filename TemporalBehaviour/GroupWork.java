package TemporalBehaviour;

import core.Coord;

/**
 * Created by Matthias on 09.12.2015.
 */
public class GroupWork extends Lecture {
    private int groupId;
    public GroupWork(double startTime, double length, Coord room, int groupId) {
        super(startTime, length, room, -1);
        this.groupId = groupId;
    }
}
