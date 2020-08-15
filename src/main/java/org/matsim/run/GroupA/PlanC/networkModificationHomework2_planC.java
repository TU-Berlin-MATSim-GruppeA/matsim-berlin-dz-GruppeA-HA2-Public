package org.matsim.run.GroupA.PlanC;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class networkModificationHomework2_planC {
    public static void main(String[] args) throws IOException {
        /*I have created an array list to store the link ids read from the txt file. Not an array as we may not know how many
         * we are going to read.*/

        int j = 0;
        Path inputNetwork = Paths.get("/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network.xml");
        Path outputNetwork = Paths.get("/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/PlanC/berlin-v5.5-network_modified.xml.gz");
        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        List<Integer> yellowLinksList = new ArrayList<Integer>();
        Scanner scanner = new Scanner(new File("/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_YellowStreets.txt"));
        while (scanner.hasNextInt()) {
            Integer id = scanner.nextInt();
            yellowLinksList.add(id);

        }
        scanner.close();
        List<Integer> RedStreets_Add_LaneList = new ArrayList<Integer>();
        Scanner scanner2 = new Scanner(new File("/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_RedStreets_Add_Lane.txt"));
        while (scanner2.hasNextInt()) {
            Integer id = scanner2.nextInt();
            RedStreets_Add_LaneList.add(id);

        }
        scanner2.close();
        List<Integer> greenLinksList = new ArrayList<Integer>();
        Scanner scanner3 = new Scanner(new File("/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_GreenStreets.txt"));
        while (scanner3.hasNextInt()) {
            Integer id = scanner3.nextInt();
            greenLinksList.add(id);

        }
        scanner3.close();
        List<Integer> internalLinksList = new ArrayList<Integer>();
        Scanner scanner4 = new Scanner(new File("/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_InternalStreets.txt"));
        while (scanner4.hasNextInt()) {
            Integer id = scanner4.nextInt();
            internalLinksList.add(id);

        }
        scanner4.close();
        List<Integer> elongationPedestrianZone = new ArrayList<Integer>();
        Scanner scanner5 = new Scanner(new File("/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_WilmersdorferModifiedZone.txt"));
        while (scanner5.hasNextInt()) {
            Integer id = scanner5.nextInt();
            elongationPedestrianZone.add(id);

        }
        scanner5.close();

        for (int i = 0; i < yellowLinksList.size(); i++) {
            double newLanes = network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).getNumberOfLanes() + 1;
            network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).setNumberOfLanes(newLanes);
            network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).setFreespeed(5);

            double capacity_After_YellowStreets = network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).getCapacity() * (newLanes / (newLanes - 1));
            network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).setCapacity(capacity_After_YellowStreets);
            System.out.println(network.getLinks().get(Id.createLinkId(yellowLinksList.get(i))).getCapacity());
        }
        for (int i = 0; i < RedStreets_Add_LaneList.size(); i++) {
            double newLanes = network.getLinks().get(Id.createLinkId(RedStreets_Add_LaneList.get(i))).getNumberOfLanes() + 1;
            network.getLinks().get(Id.createLinkId(RedStreets_Add_LaneList.get(i))).setNumberOfLanes(newLanes);

            double capacity_After_RedStreets_Add_Lane = network.getLinks().get(Id.createLinkId(RedStreets_Add_LaneList.get(i))).getCapacity() * (newLanes / (newLanes - 1));
            network.getLinks().get(Id.createLinkId(RedStreets_Add_LaneList.get(i))).setCapacity(capacity_After_RedStreets_Add_Lane);
            System.out.println(network.getLinks().get(Id.createLinkId(RedStreets_Add_LaneList.get(i))).getCapacity());
        }
        Set<String> allowedModes = new HashSet<String>();
        allowedModes.add("walk");
        allowedModes.add("bicycle");

        for (int i = 0; i < greenLinksList.size(); i++) {
            network.getLinks().get(Id.createLinkId(greenLinksList.get(i))).setFreespeed(1.38889);

        }
        for (int i = 0; i < elongationPedestrianZone.size(); i++) {
            network.getLinks().get(Id.createLinkId(elongationPedestrianZone.get(i))).setFreespeed(1.38889);
        }
        Set<String> allowedModes2 = new HashSet<String>();
        allowedModes2.add("walk");
        allowedModes2.add("bicycle");
        allowedModes2.add("car");
        allowedModes2.add("freight");
        for (int i = 0; i < internalLinksList.size(); i++) {
            network.getLinks().get(Id.createLinkId(internalLinksList.get(i))).setFreespeed(1.38889);

        }
        new NetworkWriter(network).write(outputNetwork.toString());
    }
}
