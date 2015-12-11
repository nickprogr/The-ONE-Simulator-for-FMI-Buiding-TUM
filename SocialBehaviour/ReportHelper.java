package SocialBehaviour;

import core.DTNHost;

/**
 * Created by Matthias on 11.12.2015.
 */
public class ReportHelper {

    private  int countAll_Connections = 0;
    private  int countAll_Social_Connections = 0;
    private  int countAll__Connectable_Social_Connections = 0;
    private int countAll__Connectable_Social_Connections_Connected = 0;
    private int connections_All_Removed = 0;
    private int connections_Group_Connected_Removed = 0;
    private int social_Connections_Removed = 0;

    public void addAllConnection(){
        countAll_Connections += 1;
    }

    public void addAllSocialConnections(){
        countAll_Social_Connections += 1;
    }

    public void addAllConnectableSocialConnections(){
        countAll__Connectable_Social_Connections += 1;
    }

    public void addAllConnectableSocialConnectionsConnected(){
        countAll__Connectable_Social_Connections_Connected += 1;
    }

    public void removeConnection() {
        connections_All_Removed += 1;
    }
    public void removeSocialConnections(){
       social_Connections_Removed += 1;
    }
    public void removeSocialConnectdConnection() {
        connections_Group_Connected_Removed += 1;
        //printReport();
    }

    public void printReport(){
        System.out.println("All Connections: "+countAll_Connections);
        System.out.println("All Removed Connections: "+connections_All_Removed);
        System.out.println("All Social Connections: "+countAll_Social_Connections);
        System.out.println("All Social Connections Removed: "+social_Connections_Removed);
        System.out.println("All Connectable Social Connections: "+countAll__Connectable_Social_Connections);
        System.out.println("All Connectable Social Connections Connected: "+countAll__Connectable_Social_Connections_Connected);
        System.out.println("Group Social Connections Removed Connections: "+connections_Group_Connected_Removed);

    }

}
