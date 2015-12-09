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
   // State state = new InitState(null); //First Member of Group guides
   // Coord coord;

    public Group(DTNHost member){
        members.add(member);
      //  coord = member.getLocation();
    }

    public void addMember(DTNHost member){
        members.add(member);
    }
    public void removeMember(DTNHost member){
        members.remove(member);
//       System.out.println("size: "+members.size()+" new get(0)"+members.get(0));
//        for(DTNHost member1 : members){
//            System.out.println("name: "+member1.getName());
//        }
        //System.exit(0);

    }
    public boolean isLeader(DTNHost member){
        if(members.get(0).equals(member)) {
            return true;
        }
        return false;
    }
    public Coord getDestination() {
        return members.get(0).getDailyBehaviour().getState().getDestination();
    }

    public void reachedDestination() {
    }

    public void addConnection(DTNHost otherHost) {
        //Some checking..
        addMember(otherHost);
    }

    public int getSize() {
        return members.size();
    }

    public Coord getLocation() {
        return members.get(0).getDailyBehaviour().getLocation();
    }

    public void changeName(String name) {
        for(DTNHost member : members){
            member.setName(member.getName()+name);
        }
    }

    public State getState() {
        return members.get(0).getDailyBehaviour().getState();
    }

    public void setInactive(int inactive) {
        for(DTNHost member : members){
            member.getDailyBehaviour().getMovement().setInactive(inactive);
        }
    }

}
