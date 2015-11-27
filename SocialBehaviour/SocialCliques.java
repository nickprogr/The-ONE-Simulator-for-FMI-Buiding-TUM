package SocialBehaviour;

import core.DTNHost;

import java.util.List;
import java.util.Random;

/**
 * Created by Matthias on 27.11.2015.
 */
public class SocialCliques {
    public void SocialCliques(){

    }


    public void initCliques(List<DTNHost> hosts){
        int countHost = hosts.size();
        int averageCliqueAmount = 5;
        int averageCliqueSize = 10;
        int positionsInClique = averageCliqueAmount*countHost;


        //Define in how many cliques the person will be


        Random rand = new Random();
        int cliqueCount = rand.nextInt(7);
        int cliqueSize = rand.nextInt(10);

    }
}
