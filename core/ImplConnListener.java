package core;

/**
 * Created by Development on 17/11/2015.
 */
public class ImplConnListener implements ConnectionListener {

    public void hostsConnected(DTNHost host1, DTNHost host2){
        System.out.println("Within");
        System.out.println("Connected: "+host1.getName()+" <-> "+host2.getName());
        host1.addConnection(host2);
        host2.addConnection(host1);

    }

    public void hostsDisconnected(DTNHost host1, DTNHost host2){
        System.out.println("Without");
        System.out.println("Connected: "+host1.getName()+" <-> "+host2.getName());
        host1.removeConnection(host2);
        host2.removeConnection(host1);

    }
}
