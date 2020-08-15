package org.matsim.run.GroupA.BaseCase;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.algorithms.NetworkCleaner;
import org.matsim.core.network.algorithms.TransportModeNetworkFilter;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author dziemke
 */
public class NetworkModifier_BaseCase_cleanedNetwork {
    private static final Logger LOG = Logger.getLogger(NetworkModifier_BaseCase_cleanedNetwork.class);

    public static void main (String[] args) {
        // Input and output files
        String networkInputFile = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network.xml.gz";
        String networkOutputFile = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/BaseCase/network-modified-carInternal.xml.gz";

        //******changes BaseCase******
/*        String areaShapeFile = "../../shared-svn/studies/countries/de/open_berlin_scenario/input/shapefiles/berlin_hundekopf/berlin_hundekopf.shp";

        // Store relevant area of city as geometry
        Collection<SimpleFeature> features = (new ShapeFileReader()).readFileAndInitialize(areaShapeFile);
        Map<String, Geometry> zoneGeometries = new HashMap<>();
        for (SimpleFeature feature : features) {
            zoneGeometries.put((String) feature.getAttribute("SCHLUESSEL"), (Geometry) feature.getDefaultGeometry());
        }
        Geometry areaGeometry = zoneGeometries.get("Hundekopf");*/
        //******changes BaseCase******//

        // Get network
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        MatsimNetworkReader reader = new MatsimNetworkReader(scenario.getNetwork());
        reader.readFile(networkInputFile);

        //******changes BaseCase******//
        // Get network of PlanA
        String networkInputFile_PlanA = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/PlanA/network-modified-carInternal.xml.gz";
        Scenario PlanAscenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        MatsimNetworkReader readerPlanA = new MatsimNetworkReader(PlanAscenario.getNetwork());
        readerPlanA.readFile(networkInputFile_PlanA);
        // Store link links of Plan A
        List<Id<Link>> PlanALinkList = new ArrayList<>();
        for (Link link : PlanAscenario.getNetwork().getLinks().values()) {
            PlanALinkList.add(link.getId());
        }

        // delete corresponding links in scenario
        for (Link link : scenario.getNetwork().getLinks().values()){
            if(!PlanALinkList.contains(link.getId())){
                scenario.getNetwork().removeLink(link.getId());
            }
        }
        //******changes BaseCase******

        // Get pt subnetwork
        Scenario ptScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        TransportModeNetworkFilter transportModeNetworkFilterPt = new TransportModeNetworkFilter(scenario.getNetwork());
        transportModeNetworkFilterPt.filter(ptScenario.getNetwork(), new HashSet<>(Arrays.asList(TransportMode.pt)));

        //******changes BaseCase******
        /*// Modify the car network
        for (Link link : scenario.getNetwork().getLinks().values()) {
            Set<String> allowedModesBefore = link.getAllowedModes();
            Set<String> allowedModesAfter = new HashSet<>();

            Point linkCenterAsPoint = MGC.xy2Point(link.getCoord().getX(), link.getCoord().getY());

            for (String mode : allowedModesBefore) {
                if (mode.equals(TransportMode.car)) {
                    allowedModesAfter.add("carInternal");
                    if (!areaGeometry.contains(linkCenterAsPoint)) {
                        allowedModesAfter.add(TransportMode.car);
                    }
                } else {
                    allowedModesAfter.add(mode);
                }
            }
            link.setAllowedModes(allowedModesAfter);
        }
        LOG.info("Finished modifying car vs. carInternal network");*/
        //******changes BaseCase******//

        // Get car subnetwork and clean it
        Scenario carScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        TransportModeNetworkFilter transportModeNetworkFilterCar = new TransportModeNetworkFilter(scenario.getNetwork());
        transportModeNetworkFilterCar.filter(carScenario.getNetwork(), new HashSet<>(Arrays.asList(TransportMode.car)));
        (new NetworkCleaner()).run(carScenario.getNetwork());
        LOG.info("Finished creating and cleaning car subnetwork");

        // Store remaining car links after cleaning in list
        List<Id<Link>> remainingCarlinksAfterCleaning = new ArrayList<>();
        for (Link link : carScenario.getNetwork().getLinks().values()) {
            remainingCarlinksAfterCleaning.add(link.getId());
        }
        LOG.info("There are " + remainingCarlinksAfterCleaning.size() + " car links left.");

        //******changes BaseCase******
        // Get carInternal subnetwork and clean it
/*        Scenario carInternalScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        TransportModeNetworkFilter transportModeNetworkFilterCarInternal = new TransportModeNetworkFilter(scenario.getNetwork());
        transportModeNetworkFilterCarInternal.filter(carInternalScenario.getNetwork(), new HashSet<>(Arrays.asList("carInternal")));
        (new NetworkCleaner()).run(carInternalScenario.getNetwork());
        LOG.info("Finished creating and cleaning carInternal subnetwork");

        // Add car mode to all links where appropriate
        int counter = 0;
        int divisor = 1;
        for (Link link : carInternalScenario.getNetwork().getLinks().values()) {
            Set<String> allowedModesAfter = new HashSet<>();
            for (String mode : link.getAllowedModes()) { //这行需要吗？？？
                allowedModesAfter.add("carInternal"); // This is the carInternal list, so carInternal needs to be re-added
                allowedModesAfter.add(TransportMode.ride); // Checked: All (previous) car links were also ride links before, so re-add this mode
                allowedModesAfter.add("freight"); // Checked: All (previous) car links were also freight links before, so re-add this mode
            }
            if (remainingCarlinksAfterCleaning.contains(link.getId())) {
                allowedModesAfter.add(TransportMode.car);
            }
            link.setAllowedModes(allowedModesAfter);
            counter++;
            if (counter % divisor == 0) {
                LOG.info(counter + " links handled.");
                divisor = divisor * 2;
            }
        }
        LOG.info("Finished adding car back to links where required");*/
        //******changes BaseCase******//

        //******changes BaseCase******
        // Add pt back into the other network
        // *** Note: Customized attributes are not considered here ***
        NetworkFactory factory = carScenario.getNetwork().getFactory();
        for (Node node : ptScenario.getNetwork().getNodes().values()) {
            Node node2 = factory.createNode(node.getId(), node.getCoord());
            carScenario.getNetwork().addNode(node2);
        }
        for (Link link : ptScenario.getNetwork().getLinks().values()) {
            Node fromNode = carScenario.getNetwork().getNodes().get(link.getFromNode().getId());
            Node toNode = carScenario.getNetwork().getNodes().get(link.getToNode().getId());
            Link link2 = factory.createLink(link.getId(), fromNode, toNode);
            link2.setAllowedModes(link.getAllowedModes());
            link2.setCapacity(link.getCapacity());
            link2.setFreespeed(link.getFreespeed());
            link2.setLength(link.getLength());
            link2.setNumberOfLanes(link.getNumberOfLanes());
            carScenario.getNetwork().addLink(link2);
        }
        LOG.info("Finished merging pt network layer back into network");

        // Write modified network to file
        NetworkWriter writer = new NetworkWriter(carScenario.getNetwork());
        //******changes BaseCase******//

        writer.write(networkOutputFile);
    }
}