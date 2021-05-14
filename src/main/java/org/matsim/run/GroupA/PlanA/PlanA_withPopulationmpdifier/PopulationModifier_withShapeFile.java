package org.matsim.run.GroupA.PlanA.PlanA_withPopulationmpdifier;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.population.io.PopulationWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dziemke
 */
public class PopulationModifier_withShapeFile {

    public static void main(String[] args) throws IOException {
        // Input and output files
        String plansInputFile = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.plans.xml.gz";
        String plansOutputFile = "scenarios/berlin-v5.5-1pct/input/plans-modified-carInternal.xml.gz";
        String areaShapeFile = "/Users/haowu/Workspace/QGIS/MATSim_HA2/ColoredStreets/NoCarZone_final.shp";

        // Store relevant area of city as geometry
        Collection<SimpleFeature> features = (new ShapeFileReader()).readFileAndInitialize(areaShapeFile);
        Map<String, Geometry> zoneGeometries = new HashMap<>();
        for (SimpleFeature feature : features) {
            zoneGeometries.put((String) feature.getAttribute("Name"), (Geometry) feature.getDefaultGeometry());
        }
        Geometry areaGeometry = zoneGeometries.get("NoCarZone");

        // Get population
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(plansInputFile);

        //准备List
        ArrayList<String> personInternalIDsList = new ArrayList<>();
        //结束

        // Substitute car mode by carInternal mode for people inside relevant area
        for (Person person : scenario.getPopulation().getPersons().values()) {
            // 以下for only Residents
            Activity homeActivity = (Activity) person.getPlans().get(0).getPlanElements().get(0);
            Point homeActAsPoint = MGC.xy2Point(homeActivity.getCoord().getX(), homeActivity.getCoord().getY());

            //去掉 freightAgent !
            if (areaGeometry.contains(homeActAsPoint)&&(!person.getId().toString().contains("freight"))) {
                //结束

                person.getAttributes().putAttribute("subpopulation", "personInternal");

                //打印 print AgentID to List
                personInternalIDsList.add(person.getId().toString());

                for (PlanElement pe : person.getPlans().get(0).getPlanElements()) {
                    if (pe instanceof Leg) {
                        Leg leg = (Leg) pe;
                        if (leg.getMode().equals(TransportMode.car)) {
                            leg.setMode("carInternal");
                            leg.getAttributes().putAttribute("routingMode", "carInternal");
                        }
                        if (leg.getMode().equals(TransportMode.walk) && leg.getAttributes().getAttribute("routingMode").equals(TransportMode.car)) {
                            leg.getAttributes().putAttribute("routingMode", "carInternal");
                        }
                    } else if (pe instanceof Activity) {
                        Activity activity = (Activity) pe;
                        if (activity.getType().equals("car interaction")) {
                            activity.setType("carInternal interaction");
                        }
                    }
                }
            }
        }

        //输出List到Txt
        //print the AgentIDs of No Car Zone
        // Insert Path to Output File Here!
        BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/PlanA/Txt_output/personInternalIDsList_InNoCarZone.txt"));
        // traverses the collection
        for (String s : personInternalIDsList) {
            // write data
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        // release resource
        bw.close();
        //结束

        // Write modified population to file
        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
        populationWriter.write(plansOutputFile);
    }
}