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

    private ArrayList<DTNHost> members = new ArrayList<DTNHost>();
   // State state = new InitState(null); //First Member of Group guides
   // Coord coord;

    public Group(DTNHost member){
        members.add(member);
      //  coord = member.getLocation();
    }

    public void addMember(DTNHost member){
        for(DTNHost host: members){
            host.getDailyBehaviour().reportHelper.addAllConnectableSocialConnectionsConnected();
            host.getDailyBehaviour().reportHelper.addAllConnectableSocialConnections();
            host.getDailyBehaviour().reportHelper.addAllSocialConnections();
            host.getDailyBehaviour().reportHelper.addAllConnection();

            member.getDailyBehaviour().reportHelper.addAllConnectableSocialConnectionsConnected();
            member.getDailyBehaviour().reportHelper.addAllConnectableSocialConnections();
            member.getDailyBehaviour().reportHelper.addAllSocialConnections();
            member.getDailyBehaviour().reportHelper.addAllConnection();
        }
        members.add(member);
    }
    public void removeMember(DTNHost member){
        members.remove(member);
        for(DTNHost host: members){
            host.getDailyBehaviour().reportHelper.removeSocialConnectdConnection();
            host.getDailyBehaviour().reportHelper.removeConnection();
            host.getDailyBehaviour().reportHelper.removeSocialConnections();

            member.getDailyBehaviour().reportHelper.removeSocialConnectdConnection();
            member.getDailyBehaviour().reportHelper.removeConnection();
            member.getDailyBehaviour().reportHelper.removeSocialConnections();
        }
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
        /*if(members.size()>2){
            System.out.println("Members.size(): "+members.size());
        }*/
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
    public ArrayList<DTNHost> getMembers(){
        return members;
    }

}
