package SocialBehaviour;

import TemporalBehaviour.DailyBehaviour;
import TemporalBehaviour.GroupWork;
import TemporalBehaviour.Lecture;
import core.Coord;
import core.DTNHost;
import movement.MyProhibitedPolygonRwp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Matthias on 27.11.2015.
 */
public class SocialCliques {

    public static SocialCliques socialCliques = new SocialCliques();
    public void SocialCliques(){

    }

    private int averageCliqueAmount = 3;//3;    //(0-4) in average
    private int averageCliqueSize = 3;//3;      //When use always times two to have half of it as expected value
    private HashMap<Integer, ArrayList<DTNHost>> groups = new HashMap<>();
    public void initCliques(List<DTNHost> hosts){
        int countHost = hosts.size();


        Random rand = new Random();

        for(DTNHost host : hosts){
            //Define amount of groups
            //int amount_groups = rand.nextInt(averageCliqueAmount*2);//TODO: Add
            int amount_groups = 1;
            for( ;amount_groups > 0; amount_groups--){
                int group_no = 0;
                do {
                    group_no = rand.nextInt(hosts.size() * averageCliqueAmount * 2 / (averageCliqueSize * 2));
                }while(host.isInGroup(group_no));
                ArrayList<DTNHost> list = groups.get(group_no);
                if(list == null)
                    list = new ArrayList();
                list.add(host);
                groups.put(group_no, list);
                //host.addGroup(group_no);  //TODO: Add
                host.addGroup(1);
            }
        }
        addGroupWorkDate();
        //printGroups(hosts);
    }

    private void addGroupWorkDate() {
        Random random = new Random();
        for(int i: groups.keySet()) {
            if (random.nextDouble() < 0.4) {
                //Select Block
                int startTime = random.nextInt(12*60*60)+8*60*60;//From 8am to 8pm
                double time = random.nextDouble()*90;

                for (DTNHost host : groups.get(i)) {
                    host.getDailyBehaviour().addDate(new GroupWork(startTime, time, getGroupWorkPlace(), i));
                }
                //System.out.println("hosts: "+groups.keySet().toString());
            }
        }
    }
    private Coord getGroupWorkPlace(){
        return new Coord(15,57); //Library
    }

    public boolean haveSharedGroup(DTNHost host1, DTNHost host2) {
        List<Integer> groups = host2.getGroups();
        for (int i : host1.getGroups()) {
            if (groups.contains(i)) {
                return true;
            }
        }
        return false;
    }
    private void printGroups(List<DTNHost> hosts){
        Random rand = new Random();
        int allCount = 0;
        for(int i =  hosts.size()*averageCliqueAmount*2/(averageCliqueSize*2); i > 0; i--){
            int count = 0;
            for(DTNHost host : hosts){
                if(host.isInGroup(i)){
                    count++;
                }
            }
            allCount += count;
            System.out.println(i+" : count: "+count);
        }
        System.out.println("Hosts "+hosts.size());
        System.out.println("Average: "+allCount/(hosts.size()*averageCliqueAmount*2/(averageCliqueSize*2)));
    }
}
