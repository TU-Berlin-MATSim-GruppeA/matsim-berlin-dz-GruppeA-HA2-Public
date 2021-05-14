package org.matsim.run.GroupA.NetworkModifier_fromJavier;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class networkModificationHomework2_planA {
    public static void main(String[] args) throws IOException {
        /*I have created an array list to store the link ids read from the txt file. Not an array as we may not know how many
        * we are going to read.*/

        int j = 0;
        Path inputNetwork = Paths.get(args[0]);
        Path outputNetwork = Paths.get(args[1]);
        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        for(Link link:network.getLinks().values()){
            Set<String> allowedModesBefore = link.getAllowedModes();
            Set<String> allowedModesAfter = new HashSet<>();
            for (String mode : allowedModesBefore) {
                allowedModesAfter.add(mode);
                if (mode.equals(TransportMode.car)) {
                    allowedModesAfter.add("carInternal");
                }
            }
            System.out.println("Ich bin modes: "+allowedModesAfter);
            link.setAllowedModes(allowedModesAfter);
        }

        List<Integer> yellowLinksList = new ArrayList<Integer>();
        Scanner scanner = new Scanner(new File("LinkIDs_YellowStreets.txt"));
        while(scanner.hasNextInt())
        {
            Integer id = scanner.nextInt();
            yellowLinksList.add(id);

        }
        scanner.close();
        List<Integer> redLinksList = new ArrayList<Integer>();
        Scanner scanner2 = new Scanner(new File("LinkIDs_RedStreets_Add_Lane.txt"));
        while(scanner2.hasNextInt())
        {
            Integer id = scanner2.nextInt();
            redLinksList.add(id);

        }
        scanner2.close();
        List<Integer> greenLinksList = new ArrayList<Integer>();
        Scanner scanner3 = new Scanner(new File("LinkIDs_GreenStreets.txt"));
        while(scanner3.hasNextInt())
        {
            Integer id = scanner3.nextInt();
            greenLinksList.add(id);

        }
        scanner3.close();
        List<Integer> internalLinksList = new ArrayList<Integer>();
        Scanner scanner4 = new Scanner(new File("LinkIDs_InternalStreets.txt"));
        while(scanner4.hasNextInt())
        {
            Integer id = scanner4.nextInt();
            internalLinksList.add(id);

        }
        scanner4.close();
        List<Integer> elongationPedestrianZone = new ArrayList<Integer>();
        Scanner scanner5 = new Scanner(new File("LinkIDs_WilmersdorferModifiedZone.txt"));
        while(scanner5.hasNextInt())
        {
            Integer id = scanner5.nextInt();
            elongationPedestrianZone.add(id);

        }
        scanner5.close();

        for(int i=0; i< yellowLinksList.size(); i++){
            double newLanes = network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).getNumberOfLanes()+1;
            network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).setNumberOfLanes(newLanes);
            network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).setFreespeed(5);
        }
        for(int i=0; i< redLinksList.size(); i++){
            double newLanes = network.getLinks().get(Id.createLinkId(redLinksList.get(i))).getNumberOfLanes()+1;
            network.getLinks().get(Id.createLinkId(redLinksList.get(i))).setNumberOfLanes(newLanes);
        }
        Set<String> allowedModes = new HashSet<String>();
        allowedModes.add("walk");
        allowedModes.add("bicycle");
        for(int i=0; i< greenLinksList.size(); i++){
            network.getLinks().get(Id.createLinkId(greenLinksList.get(i))).setFreespeed(1.38889);
            network.getLinks().get(Id.createLinkId(greenLinksList.get(i))).setAllowedModes(allowedModes);
        }
        for(int i=0; i< elongationPedestrianZone.size(); i++){
            network.getLinks().get(Id.createLinkId(greenLinksList.get(i))).setAllowedModes(allowedModes);
        }
        Set<String> allowedModes2 = new HashSet<String>();
        allowedModes2.add("walk");
        allowedModes2.add("bicycle");
        allowedModes2.add("carInternal");
        allowedModes2.add("freight");
        for(int i=0; i< internalLinksList.size(); i++){
            network.getLinks().get(Id.createLinkId(internalLinksList.get(i))).setFreespeed(1.38889);
            network.getLinks().get(Id.createLinkId(internalLinksList.get(i))).setAllowedModes(allowedModes2);

        }
        new NetworkWriter(network).write(outputNetwork.toString());

    }
}
