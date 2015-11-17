package core;

/**
 * Created by Development on 17/11/2015.
 */
public class ImplConnListener implements ConnectionListener {

    public void hostsConnected(DTNHost host1, DTNHost host2){
        System.out.println("Within");

        host1.peopleWithinRange = host2.peopleWithinRange = true;
    }

    public void hostsDisconnected(DTNHost host1, DTNHost host2){
        System.out.println("Without");

        host1.peopleWithinRange = host2.peopleWithinRange = false;
    }
}
