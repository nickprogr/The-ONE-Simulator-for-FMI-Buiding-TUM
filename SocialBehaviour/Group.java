package SocialBehaviour;

import TemporalBehaviour.FreetimeState;
import TemporalBehaviour.InitState;
import TemporalBehaviour.State;
import core.Coord;
import core.DTNHost;

import java.util.ArrayList;

/**
 * Created by Matthias on 08.12.2015.
 */
public class Group {

    ArrayList<DTNHost> members = new ArrayList<DTNHost>();
    State state = new InitState(null); //First Member of Group guides
    Coord coord;

    public Group(DTNHost member){
        members.add(member);
        coord = member.getLocation();
    }

    public void addMember(DTNHost member){
        members.add(member);
    }
    public void removeMember(DTNHost member){
        members.remove(member);
    }
    public boolean isLeader(DTNHost member){
        if(members.get(0).equals(member))
            return true;
        return false;
    }

}
