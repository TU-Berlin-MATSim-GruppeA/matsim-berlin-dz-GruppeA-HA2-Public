package org.matsim.run.GroupA.PlanA.PlanA_withoutPopulationmpdifier;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class NetworkCatcher {

    public static void main(String[] args) throws IOException {
        String networkInputFile = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/network-modified-carInternal.xml.gz";
        //String networkOutputFile = "/Users/haowu/Workspace/git/matsim-berlin-GruppeA-HA2/scenarios/berlin-v5.5-1pct/input/network-modified.xml.gz";


        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        MatsimNetworkReader reader = new MatsimNetworkReader(scenario.getNetwork());
        reader.readFile(networkInputFile);


        ArrayList<String> arrayList_LinkIDs = new ArrayList<>();

        for (Link link : scenario.getNetwork().getLinks().values()) {
            Set<String> allowedModesBefore = link.getAllowedModes();
            //Set<String> allowedModesAfter = new HashSet<>();

            //Point linkCenterAsPoint = MGC.xy2Point(link.getCoord().getX(), link.getCoord().getY());
            //Point linkCenterAsPoint = MGC.xy2Point(link.getFromNode().getCoord().getX(), link.getToNode().getCoord().getY());
            //Point linkCenterAsPoint = MGC.xy2Point(link.getToNode().getCoord().getX(), link.getToNode().getCoord().getY());

/*            for (String mode : allowedModesBefore) {
                if (mode.equals(TransportMode.car)) {
                    arrayList_LinkIDs.add(link.getId().toString());
                    //allowedModesAfter.add("carInternal");
*//*                    if (!areaGeometry.contains(linkCenterAsPoint)) {
                        //System.out.println("Yeah!");
                        allowedModesAfter.add(TransportMode.car);
                    }*//*
                } else {
                    //allowedModesAfter.add(mode);
                }
            }
            //link.setAllowedModes(allowedModesAfter);*/


            /*//prepare to print the LinkIDs of No Car Zone
            if (areaGeometry.contains(linkCenterAsPoint)) {
                arrayList_LinkIDs.add(link.getId().toString());
            }*/




            if ((!allowedModesBefore.contains(TransportMode.car))&&(!allowedModesBefore.contains(TransportMode.pt))){
                arrayList_LinkIDs.add(link.getId().toString());
            }
        }


        //print the LinkIDs of No Car Zone
        // Insert Path to Output File Here!
        BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/LinkIDs_InNoCarZone.txt"));
        // traverses the collection
        for (String s : arrayList_LinkIDs) {
            // write data
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        // release resource
        bw.close();

        //NetworkWriter writer = new NetworkWriter(scenario.getNetwork());
        //writer.write(networkOutputFile);
        }
    }




