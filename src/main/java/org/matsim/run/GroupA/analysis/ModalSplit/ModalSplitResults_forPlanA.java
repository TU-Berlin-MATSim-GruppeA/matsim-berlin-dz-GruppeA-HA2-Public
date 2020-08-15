package org.matsim.run.GroupA.analysis.ModalSplit;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author dziemke
 */
public class ModalSplitResults_forPlanA {

    public static void main(String[] args) throws IOException {

        //-------------For Input and Output Files-------------
        // BaseCase
        //String InputFile_planFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct_baseCase_100Iterations/berlin-v5.5-1pct.output_plans.xml.gz";
        //String OutputFile_Results = "scenarios/berlin-v5.5-1pct/input/Analysis/ModalSplit/BaseCase/Results_BaseCase.txt";

        // PlanA
        //String InputFile_planFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct-subpop_PlanA_Version1_secondSuccessfulRun_100Iterations/berlin-v5.5-1pct.output_plans.xml.gz";
        //String OutputFile_Results = "scenarios/berlin-v5.5-1pct/input/Analysis/ModalSplit/PlanA/Results_PlanA.txt";

        // PlanB 50Iterations
        //String InputFile_planFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct_PlanB_Network2_Version1_firstSuccessfulRun_50Iterations/berlin-v5.5-1pct.output_plans.xml.gz";
        //String OutputFile_Results = "scenarios/berlin-v5.5-1pct/input/Analysis/ModalSplit/PlanB/Results_PlanB_50Iterations.txt";
        // PlanB 100Iterations
        String InputFile_planFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct_PlanB_Network2_Version1_secondSuccessfulRun_100Iterations/berlin-v5.5-1pct.output_plans.xml.gz";
        String OutputFile_Results = "scenarios/berlin-v5.5-1pct/input/Analysis/ModalSplit/PlanB/Results_PlanB_100Iterations.txt";

        // PlanC
        //String InputFile_planFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct_PlanC_firstSuccessfulRun_100Iterations/berlin-v5.5-1pct.output_plans.xml.gz";
        //String OutputFile_Results = "scenarios/berlin-v5.5-1pct/input/Analysis/ModalSplit/PlanC/Results_PlanC.txt";
        //-------------For Input and Output Files-------------//

        //-------------for print out AgentsLists Txt file-------------
        // BaseCase
/*        String InputFile_planFile = "scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.plans.xml.gz";
        String OutputFile_Results = "scenarios/berlin-v5.5-1pct/input/Analysis/uselessFile.txt";

        String outputFile_AffectedResidentsIDsList = "scenarios/berlin-v5.5-1pct/input/Analysis/affectedResidentsIDsList.txt";
        String outputFile_NoneResidentsAffectedAgentsIDsList = "scenarios/berlin-v5.5-1pct/input/Analysis/noneResidentsAffectedAgentsIDsList.txt";
        String outputFile_AffectedAgentsIDsList = "scenarios/berlin-v5.5-1pct/input/Analysis/affectedAgentsIDsList.txt";
        String outputFile_OtherAgentsIDsList = "scenarios/berlin-v5.5-1pct/input/Analysis/otherAgentsIDsList.txt";*/
        //-------------for print out AgentsLists Txt file-------------//




        String PersonInternalIDs = "scenarios/berlin-v5.5-1pct/input/PlanA/personInternalIDsList.txt";
        //******changes in PlanA_Version1******
        //——————Input——————
        String RedStreets = "/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_RedStreets.txt";
        String RedStreets_Add_Lane = "/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_RedStreets_Add_Lane.txt";
        String GreenStreets = "/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_GreenStreets.txt";
        String YellowStreets = "/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_YellowStreets.txt";
        String InternalStreets = "/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_InternalStreets.txt";
        String ElongationPedestrianZone = "/Users/haowu/Documents/Planung_und_Betrieb_im_Verkehrswesen/SS2020/Multi-agent_transport_simulation_SoSe_2020/HA/HA2/LinkIDs/LinkIDsList_withoutFormatProblem/LinkIDs_WilmersdorferModifiedZone.txt";
        //——————Input——————//


        //——————Input As List——————
        // RedStreets
        BufferedReader bfrRedStreets = new BufferedReader(new FileReader(RedStreets));
        ArrayList<String> RedStreetsList = new ArrayList<>();
        while (true){
            String s = bfrRedStreets.readLine();
            if(s==null){
                break;
            }
            RedStreetsList.add(s);
        }
        bfrRedStreets.close();
        System.out.println(RedStreetsList);
        System.out.println(RedStreetsList.size());

        // RedStreets_Add_Lane
        BufferedReader bfrRedStreets_Add_Lane = new BufferedReader(new FileReader(RedStreets_Add_Lane));
        ArrayList<String> RedStreets_Add_LaneList = new ArrayList<>();
        while (true){
            String s = bfrRedStreets_Add_Lane.readLine();
            if(s==null){
                break;
            }
            RedStreets_Add_LaneList.add(s);
        }
        bfrRedStreets_Add_Lane.close();
        System.out.println(RedStreets_Add_LaneList);
        System.out.println(RedStreets_Add_LaneList.size());

        // GreenStreets
        BufferedReader bfrGreenStreets = new BufferedReader(new FileReader(GreenStreets));
        ArrayList<String> GreenStreetsList = new ArrayList<>();
        while (true){
            String s = bfrGreenStreets.readLine();
            if(s==null){
                break;
            }
            GreenStreetsList.add(s);
        }
        bfrGreenStreets.close();
        System.out.println(GreenStreetsList);
        System.out.println(GreenStreetsList.size());

        // YellowStreets
        BufferedReader bfrYellowStreets = new BufferedReader(new FileReader(YellowStreets));
        ArrayList<String> YellowStreetsList = new ArrayList<>();
        while (true){
            String s = bfrYellowStreets.readLine();
            if(s==null){
                break;
            }
            YellowStreetsList.add(s);
        }
        bfrYellowStreets.close();
        System.out.println(YellowStreetsList);
        System.out.println(YellowStreetsList.size());

        // InternalStreets
        BufferedReader bfrInternalStreets = new BufferedReader(new FileReader(InternalStreets));
        ArrayList<String> InternalStreetsList = new ArrayList<>();
        while (true){
            String s = bfrInternalStreets.readLine();
            if(s==null){
                break;
            }
            InternalStreetsList.add(s);
        }
        bfrInternalStreets.close();
        System.out.println(InternalStreetsList);
        System.out.println(InternalStreetsList.size());

        // ElongationPedestrianZone
        BufferedReader bfrElongationPedestrianZone = new BufferedReader(new FileReader(ElongationPedestrianZone));
        ArrayList<String> ElongationPedestrianZoneList = new ArrayList<>();
        while (true){
            String s = bfrElongationPedestrianZone.readLine();
            if(s==null){
                break;
            }
            ElongationPedestrianZoneList.add(s);
        }
        bfrElongationPedestrianZone.close();
        System.out.println(ElongationPedestrianZoneList);
        System.out.println(ElongationPedestrianZoneList.size());


        //combine and clean LinksLists for those not having car mode in No Car Zone
        List<String> personInternalLinkList = new ArrayList<String>();
        personInternalLinkList.addAll(InternalStreetsList);
        personInternalLinkList.addAll(YellowStreetsList);
        personInternalLinkList.addAll(RedStreetsList);
        personInternalLinkList = new ArrayList<String>(new LinkedHashSet<>(personInternalLinkList));
        System.out.println(personInternalLinkList);
        System.out.println(personInternalLinkList.size());




        // ResindentsIDs
        BufferedReader bfrpersonInternalIDs = new BufferedReader(new FileReader(PersonInternalIDs));
        ArrayList<String> personInternalIDsList = new ArrayList<>();
        while (true){
            String s = bfrpersonInternalIDs.readLine();
            if(s==null){
                break;
            }
            personInternalIDsList.add(s);
        }
        bfrpersonInternalIDs.close();
        System.out.println(personInternalIDsList);
        System.out.println(personInternalIDsList.size());
        //——————Input As List——————//




        //************************for AffectedAgentHandler
        //for PlanA
        String inputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct_baseCase_1Iterations/berlin-v5.5-1pct.output_events.xml.gz";
        String outputFile1 = "scenarios/berlin-v5.5-1pct/input/Analysis/resident_and_worker_Agents_HA2.txt";
        String outputFile2 = "scenarios/berlin-v5.5-1pct/input/Analysis/affected_Vehicles_HA2.txt";




        EventsManager eventsManager = EventsUtils.createEventsManager();

        // SimpleEventHandler eventHandler = new SimpleEventHandler();
        AffectedAgentHandler eventHandler = new AffectedAgentHandler(personInternalLinkList);
        eventsManager.addHandler(eventHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        eventHandler.printResults(outputFile1, outputFile2);
        //************************
        // AffectedAgentsIDs
        BufferedReader bfrAffectedAgentsIDs = new BufferedReader(new FileReader(outputFile2));
        ArrayList<String> AffectedAgentsIDsList = new ArrayList<>();
        while (true){
            String s = bfrAffectedAgentsIDs.readLine();
            if(s==null){
                break;
            }
            AffectedAgentsIDsList.add(s);
        }
        bfrAffectedAgentsIDs.close();
        System.out.println(AffectedAgentsIDsList);
        System.out.println(AffectedAgentsIDsList.size());




/*        // Store relevant area of city as geometry
        Collection<SimpleFeature> features = (new ShapeFileReader()).readFileAndInitialize(areaShapeFile);
        Map<String, Geometry> zoneGeometries = new HashMap<>();
        for (SimpleFeature feature : features) {
            zoneGeometries.put((String) feature.getAttribute("Name"), (Geometry) feature.getDefaultGeometry());
        }
        Geometry areaGeometry = zoneGeometries.get("NoCarZone");*/

        //******changes in PlanA_Version1******//




        // Get population
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(InputFile_planFile);




        //Counter for AffectedResidents
        double carCounter_AffectedResidents = 0;
        double carInternalCounter_AffectedResidents = 0;
        double freightCounter_AffectedResidents = 0;
        double rideCounter_AffectedResidents = 0;
        double bicycleCounter_AffectedResidents = 0;
        double walkCounter_AffectedResidents = 0;
        double ptCounter_AffectedResidents = 0;
        //Counter for Residents
        double carCounter_Residents = 0;
        double carInternalCounter_Residents = 0;
        double freightCounter_Residents = 0;
        double rideCounter_Residents = 0;
        double bicycleCounter_Residents = 0;
        double walkCounter_Residents = 0;
        double ptCounter_Residents = 0;
        //Counter for NoneResidentsAffectedAgents
        double carCounter_NoneResidentsAffectedAgents = 0;
        double carInternalCounter_NoneResidentsAffectedAgents = 0;
        double freightCounter_NoneResidentsAffectedAgents = 0;
        double rideCounter_NoneResidentsAffectedAgents = 0;
        double bicycleCounter_NoneResidentsAffectedAgents = 0;
        double walkCounter_NoneResidentsAffectedAgents = 0;
        double ptCounter_NoneResidentsAffectedAgents = 0;
        //Counter for AffectedAgents
        double carCounter_AffectedAgents = 0;
        double carInternalCounter_AffectedAgents = 0;
        double freightCounter_AffectedAgents = 0;
        double rideCounter_AffectedAgents = 0;
        double bicycleCounter_AffectedAgents = 0;
        double walkCounter_AffectedAgents = 0;
        double ptCounter_AffectedAgents = 0;
        //Counter for OtherAgents
        double carCounter_OtherAgents = 0;
        double carInternalCounter_OtherAgents = 0;
        double freightCounter_OtherAgents = 0;
        double rideCounter_OtherAgents = 0;
        double bicycleCounter_OtherAgents = 0;
        double walkCounter_OtherAgents = 0;
        double ptCounter_OtherAgents = 0;

        double AffectedResidentsCounter = 0;
        ArrayList<String> AffectedResidentsIDsList = new ArrayList<>();
        double ResidentsCounter = 0;
        //ArrayList<String> ResidentsIDsList = new ArrayList<>();
        double NoneResidentsAffectedAgentsCounter = 0;
        ArrayList<String> NoneResidentsAffectedAgentsIDsList = new ArrayList<>();
        double AffectedAgentsCounter = 0;
        //ArrayList<String> AffectedAgentsIDsList = new ArrayList<>();
        double OtherAgentsCounter = 0;
        ArrayList<String> OtherAgentsIDsList = new ArrayList<>();




        // Substitute car mode by carInternal mode for people inside relevant area
        for (Person person : scenario.getPopulation().getPersons().values()) {

            //******Backup******
/*            int a = person.getPlans().size();
            int b;
            for (b = 0; b < a; b++) {
                int n = person.getPlans().get(b).getPlanElements().size();
                int i;
                for (i = 0; i < n; i++) {
                    if (i % 2 == 1) {
                        Leg leg = (Leg) person.getPlans().get(b).getPlanElements().get(i);
                        leg.getMode();
                    } else {
                        throw new RuntimeException("Plan element is not leg.");
                    }
                }
            }*/
            //******Backup******//

            // for only Residents
            //Activity homeActivity = (Activity) person.getPlans().get(0).getPlanElements().get(0);

            //******changes in PlanA_Version1******
            //Point homeActAsPoint = MGC.xy2Point(homeActivity.getCoord().getX(), homeActivity.getCoord().getY());
            //String homeActAsLinkID = homeActivity.getLinkId().toString();


            //not include freightAgent Person !
            //if (personInternalLinkList.contains(homeActAsLinkID)&&(!person.getId().toString().contains("freight"))) {
            if (personInternalIDsList.contains(person.getId().toString()) && (!AffectedAgentsIDsList.contains(person.getId().toString())) /* && (!person.getId().toString().contains("freight"))*/ ) {
                //not include freightAgent Person !//
                //******changes in PlanA_Version1******//

                //person.getAttributes().putAttribute("subpopulation", "personInternal");



                    for(Plan pa : person.getPlans()) {
                        for (PlanElement pe : pa.getPlanElements()) {
                            if (pe instanceof Leg) {
                                Leg leg = (Leg) pe;
                                if (leg.getMode().equals(TransportMode.car)) {
                                    carCounter_Residents++;
                                    carCounter_AffectedAgents++;
                                } else if (leg.getMode().equals("carInternal")) {
                                    carInternalCounter_Residents++;
                                    carInternalCounter_AffectedAgents++;
                                } else if (leg.getMode().equals("freight")) {
                                    freightCounter_Residents++;
                                    freightCounter_AffectedAgents++;
                                } else if (leg.getMode().equals(TransportMode.ride)) {
                                    rideCounter_Residents++;
                                    rideCounter_AffectedAgents++;
                                } else if (leg.getMode().equals("bicycle")) {
                                    bicycleCounter_Residents++;
                                    bicycleCounter_AffectedAgents++;
                                } else if (leg.getMode().equals(TransportMode.walk)) {
                                    walkCounter_Residents++;
                                    walkCounter_AffectedAgents++;
                                } else if (leg.getMode().equals(TransportMode.pt)) {
                                    ptCounter_Residents++;
                                    ptCounter_AffectedAgents++;
                                } else {
                                    throw new RuntimeException("there are not only mode 'car, carInternal, freight, ride, bicycle, walk, pt', but also something else mode! ");
                                }

                            } else if (pe instanceof Activity) {

                            } else {
                                throw new RuntimeException("Plan element can either be activity or leg.");
                            }
                        }
                    }

                    ResidentsCounter++;
                    AffectedAgentsCounter++;

            } else if (personInternalIDsList.contains(person.getId().toString()) && (AffectedAgentsIDsList.contains(person.getId().toString()))) {

                for(Plan pa : person.getPlans()) {
                    for (PlanElement pe : pa.getPlanElements()) {
                        if (pe instanceof Leg) {
                            Leg leg = (Leg) pe;
                            if (leg.getMode().equals(TransportMode.car)) {
                                carCounter_AffectedResidents++;
                                carCounter_Residents++;
                                carCounter_AffectedAgents++;
                            } else if (leg.getMode().equals("carInternal")) {
                                carInternalCounter_AffectedResidents++;
                                carInternalCounter_Residents++;
                                carInternalCounter_AffectedAgents++;
                            } else if (leg.getMode().equals("freight")) {
                                freightCounter_AffectedResidents++;
                                freightCounter_Residents++;
                                freightCounter_AffectedAgents++;
                            } else if (leg.getMode().equals(TransportMode.ride)) {
                                rideCounter_AffectedResidents++;
                                rideCounter_Residents++;
                                rideCounter_AffectedAgents++;
                            } else if (leg.getMode().equals("bicycle")) {
                                bicycleCounter_AffectedResidents++;
                                bicycleCounter_Residents++;
                                bicycleCounter_AffectedAgents++;
                            } else if (leg.getMode().equals(TransportMode.walk)) {
                                walkCounter_AffectedResidents++;
                                walkCounter_Residents++;
                                walkCounter_AffectedAgents++;
                            } else if (leg.getMode().equals(TransportMode.pt)) {
                                ptCounter_AffectedResidents++;
                                ptCounter_Residents++;
                                ptCounter_AffectedAgents++;
                            } else {
                                throw new RuntimeException("there are not only mode 'car, carInternal, freight, ride, bicycle, walk, pt', but also something else mode! ");
                            }

                        } else if (pe instanceof Activity) {

                        } else {
                            throw new RuntimeException("Plan element can either be activity or leg.");
                        }
                    }
                }

                AffectedResidentsCounter++;
                AffectedResidentsIDsList.add(person.getId().toString());

                ResidentsCounter++;
                AffectedAgentsCounter++;

            } else if((!personInternalIDsList.contains(person.getId().toString())) && AffectedAgentsIDsList.contains(person.getId().toString())) {

                for(Plan pa : person.getPlans()) {
                    for (PlanElement pe : pa.getPlanElements()) {
                        if (pe instanceof Leg) {
                            Leg leg = (Leg) pe;
                            if (leg.getMode().equals(TransportMode.car)) {
                                carCounter_NoneResidentsAffectedAgents++;
                                carCounter_AffectedAgents++;
                            } else if (leg.getMode().equals("carInternal")) {
                                carInternalCounter_NoneResidentsAffectedAgents++;
                                carInternalCounter_AffectedAgents++;
                            } else if (leg.getMode().equals("freight")) {
                                freightCounter_NoneResidentsAffectedAgents++;
                                freightCounter_AffectedAgents++;
                            } else if (leg.getMode().equals(TransportMode.ride)) {
                                rideCounter_NoneResidentsAffectedAgents++;
                                rideCounter_AffectedAgents++;
                            } else if (leg.getMode().equals("bicycle")) {
                                bicycleCounter_NoneResidentsAffectedAgents++;
                                bicycleCounter_AffectedAgents++;
                            } else if (leg.getMode().equals(TransportMode.walk)) {
                                walkCounter_NoneResidentsAffectedAgents++;
                                walkCounter_AffectedAgents++;
                            } else if (leg.getMode().equals(TransportMode.pt)) {
                                ptCounter_NoneResidentsAffectedAgents++;
                                ptCounter_AffectedAgents++;
                            } else {
                                throw new RuntimeException("there are not only mode 'car, carInternal, freight, ride, bicycle, walk, pt', but also something else mode! ");
                            }

                        } else if (pe instanceof Activity) {

                        } else {
                            throw new RuntimeException("Plan element can either be activity or leg.");
                        }
                    }
                }

                NoneResidentsAffectedAgentsCounter++;
                NoneResidentsAffectedAgentsIDsList.add(person.getId().toString());

                AffectedAgentsCounter++;

            } else {

                for(Plan pa : person.getPlans()) {
                    for (PlanElement pe : pa.getPlanElements()) {
                        if (pe instanceof Leg) {
                            Leg leg = (Leg) pe;
                            if (leg.getMode().equals(TransportMode.car)) {
                                carCounter_OtherAgents++;
                            } else if (leg.getMode().equals("carInternal")) {
                                carInternalCounter_OtherAgents++;
                            } else if (leg.getMode().equals("freight")) {
                                freightCounter_OtherAgents++;
                            } else if (leg.getMode().equals(TransportMode.ride)) {
                                rideCounter_OtherAgents++;
                            } else if (leg.getMode().equals("bicycle")) {
                                bicycleCounter_OtherAgents++;
                            } else if (leg.getMode().equals(TransportMode.walk)) {
                                walkCounter_OtherAgents++;
                            } else if (leg.getMode().equals(TransportMode.pt)) {
                                ptCounter_OtherAgents++;
                            } else {
                                throw new RuntimeException("there are not only mode 'car, carInternal, freight, ride, bicycle, walk, pt', but also something else mode! ");
                            }

                        } else if (pe instanceof Activity) {

                        } else {
                            throw new RuntimeException("Plan element can either be activity or leg.");
                        }
                    }
                }

                OtherAgentsCounter++;
                OtherAgentsIDsList.add(person.getId().toString());

            }
        }




        //prepare
        //Counter for OtherAgents
        double carCounter = 0;
        carCounter = carCounter_Residents + carCounter_NoneResidentsAffectedAgents + carCounter_OtherAgents;
        System.out.println(carCounter + ", " + carCounter_Residents + ", " + carCounter_NoneResidentsAffectedAgents + ", " + carCounter_OtherAgents);
        double carInternalCounter = 0;
        carInternalCounter = carInternalCounter_Residents + carInternalCounter_NoneResidentsAffectedAgents + carInternalCounter_OtherAgents;
        System.out.println(carInternalCounter + ", " + carInternalCounter_Residents + ", " + carInternalCounter_NoneResidentsAffectedAgents + ", " + carInternalCounter_OtherAgents);
        double freightCounter = 0;
        freightCounter = freightCounter_Residents + freightCounter_NoneResidentsAffectedAgents + freightCounter_OtherAgents;
        System.out.println(freightCounter + ", " + freightCounter_Residents + ", " + freightCounter_NoneResidentsAffectedAgents + ", " + freightCounter_OtherAgents);
        double rideCounter = 0;
        rideCounter = rideCounter_Residents + rideCounter_NoneResidentsAffectedAgents + rideCounter_OtherAgents;
        System.out.println(rideCounter + ", " + rideCounter_Residents + ", " + rideCounter_NoneResidentsAffectedAgents + ", " + rideCounter_OtherAgents);
        double bicycleCounter = 0;
        bicycleCounter = bicycleCounter_Residents + bicycleCounter_NoneResidentsAffectedAgents + bicycleCounter_OtherAgents;
        System.out.println(bicycleCounter + ", " + bicycleCounter_Residents + ", " + bicycleCounter_NoneResidentsAffectedAgents + ", " + bicycleCounter_OtherAgents);
        double walkCounter = 0;
        walkCounter = walkCounter_Residents + walkCounter_NoneResidentsAffectedAgents + walkCounter_OtherAgents;
        System.out.println(walkCounter + ", " + walkCounter_Residents + ", " + walkCounter_NoneResidentsAffectedAgents + ", " + walkCounter_OtherAgents);
        double ptCounter = 0;
        ptCounter = ptCounter_Residents + ptCounter_NoneResidentsAffectedAgents + ptCounter_OtherAgents;
        System.out.println(ptCounter + ", " + ptCounter_Residents + ", " + ptCounter_NoneResidentsAffectedAgents + ", " + ptCounter_OtherAgents);


        double AllAgentsCounter = 0;
        AllAgentsCounter = ResidentsCounter + NoneResidentsAffectedAgentsCounter + OtherAgentsCounter;
        System.out.println(AllAgentsCounter + ", " + ResidentsCounter + ", " + NoneResidentsAffectedAgentsCounter + ", " + OtherAgentsCounter);




        double carProportion_AffectedResidents = 0;
        double carProportion_Residents = 0;
        double carProportion_NoneResidentsAffectedAgents = 0;
        double carProportion_AffectedAgents = 0;
        double carProportion_OtherAgents = 0;
        carProportion_AffectedResidents = carCounter_AffectedResidents / carCounter * 100;
        carProportion_Residents = carCounter_Residents / carCounter * 100;
        carProportion_NoneResidentsAffectedAgents = carCounter_NoneResidentsAffectedAgents / carCounter * 100;
        carProportion_AffectedAgents = carCounter_AffectedAgents / carCounter * 100;
        carProportion_OtherAgents = carCounter_OtherAgents / carCounter * 100;

        double carInternalProportion_AffectedResidents = 0;
        double carInternalProportion_Residents = 0;
        double carInternalProportion_NoneResidentsAffectedAgents = 0;
        double carInternalProportion_AffectedAgents = 0;
        double carInternalProportion_OtherAgents = 0;
        carInternalProportion_AffectedResidents = carInternalCounter_AffectedResidents / carInternalCounter * 100;
        carInternalProportion_Residents = carInternalCounter_Residents / carInternalCounter * 100;
        carInternalProportion_NoneResidentsAffectedAgents = carInternalCounter_NoneResidentsAffectedAgents / carInternalCounter * 100;
        carInternalProportion_AffectedAgents = carInternalCounter_AffectedAgents / carInternalCounter * 100;
        carInternalProportion_OtherAgents = carInternalCounter_OtherAgents / carInternalCounter * 100;

        double freightProportion_AffectedResidents = 0;
        double freightProportion_Residents = 0;
        double freightProportion_NoneResidentsAffectedAgents = 0;
        double freightProportion_AffectedAgents = 0;
        double freightProportion_OtherAgents = 0;
        freightProportion_AffectedResidents = freightCounter_AffectedResidents / freightCounter * 100;
        freightProportion_Residents = freightCounter_Residents / freightCounter * 100;
        freightProportion_NoneResidentsAffectedAgents = freightCounter_NoneResidentsAffectedAgents / freightCounter * 100;
        freightProportion_AffectedAgents = freightCounter_AffectedAgents / freightCounter * 100;
        freightProportion_OtherAgents = freightCounter_OtherAgents / freightCounter * 100;

        double rideProportion_AffectedResidents = 0;
        double rideProportion_Residents = 0;
        double rideProportion_NoneResidentsAffectedAgents = 0;
        double rideProportion_AffectedAgents = 0;
        double rideProportion_OtherAgents = 0;
        rideProportion_AffectedResidents = rideCounter_AffectedResidents / rideCounter * 100;
        rideProportion_Residents = rideCounter_Residents / rideCounter * 100;
        rideProportion_NoneResidentsAffectedAgents = rideCounter_NoneResidentsAffectedAgents / rideCounter * 100;
        rideProportion_AffectedAgents = rideCounter_AffectedAgents / rideCounter * 100;
        rideProportion_OtherAgents = rideCounter_OtherAgents / rideCounter * 100;

        double bicycleProportion_AffectedResidents = 0;
        double bicycleProportion_Residents = 0;
        double bicycleProportion_NoneResidentsAffectedAgents = 0;
        double bicycleProportion_AffectedAgents = 0;
        double bicycleProportion_OtherAgents = 0;
        bicycleProportion_AffectedResidents = bicycleCounter_AffectedResidents / bicycleCounter * 100;
        bicycleProportion_Residents = bicycleCounter_Residents / bicycleCounter * 100;
        bicycleProportion_NoneResidentsAffectedAgents = bicycleCounter_NoneResidentsAffectedAgents / bicycleCounter * 100;
        bicycleProportion_AffectedAgents = bicycleCounter_AffectedAgents / bicycleCounter * 100;
        bicycleProportion_OtherAgents = bicycleCounter_OtherAgents / bicycleCounter * 100;

        double walkProportion_AffectedResidents = 0;
        double walkProportion_Residents = 0;
        double walkProportion_NoneResidentsAffectedAgents = 0;
        double walkProportion_AffectedAgents = 0;
        double walkProportion_OtherAgents = 0;
        walkProportion_AffectedResidents = walkCounter_AffectedResidents / walkCounter * 100;
        walkProportion_Residents = walkCounter_Residents / walkCounter * 100;
        walkProportion_NoneResidentsAffectedAgents = walkCounter_NoneResidentsAffectedAgents / walkCounter * 100;
        walkProportion_AffectedAgents = walkCounter_AffectedAgents / walkCounter * 100;
        walkProportion_OtherAgents = walkCounter_OtherAgents / walkCounter * 100;

        double ptProportion_AffectedResidents = 0;
        double ptProportion_Residents = 0;
        double ptProportion_NoneResidentsAffectedAgents = 0;
        double ptProportion_AffectedAgents = 0;
        double ptProportion_OtherAgents = 0;
        ptProportion_AffectedResidents = ptCounter_AffectedResidents / ptCounter * 100;
        ptProportion_Residents = ptCounter_Residents / ptCounter * 100;
        ptProportion_NoneResidentsAffectedAgents = ptCounter_NoneResidentsAffectedAgents / ptCounter * 100;
        ptProportion_AffectedAgents = ptCounter_AffectedAgents / ptCounter * 100;
        ptProportion_OtherAgents = ptCounter_OtherAgents / ptCounter * 100;


        //double Proportion_AffectedResidents = 0;
        double Proportion_Residents = 0;
        double Proportion_NoneResidentsAffectedAgents = 0;
        //double Proportion_AffectedAgents = 0;
        double Proportion_OtherAgents = 0;
        //Proportion_AffectedResidents = AffectedResidentsCounter / AllAgentsCounter * 100;
        Proportion_Residents = ResidentsCounter / AllAgentsCounter * 100;
        Proportion_NoneResidentsAffectedAgents = NoneResidentsAffectedAgentsCounter / AllAgentsCounter * 100;
        //Proportion_AffectedAgents = AffectedAgentsCounter / AllAgentsCounter * 100;
        Proportion_OtherAgents = OtherAgentsCounter / AllAgentsCounter * 100;

        double Proportion_AllAgents = 0;
        Proportion_AllAgents = Proportion_Residents + Proportion_NoneResidentsAffectedAgents + Proportion_OtherAgents;




        //print Txt
        // Insert Path to Output File Here!
        BufferedWriter ms = new BufferedWriter(new FileWriter(OutputFile_Results));
        // traverses the collection
        //for (String s : personInternalIDsList) {
            // write data
            ms.write("In this Txt File: !!! Residents means |those Agents|, who are residents and not used going through the restricted zone!");
            ms.newLine();
            ms.flush();
            ms.write("In this Txt File: !!! AffectedResidents means |those Agents|, who are residents and used going through the restricted zone!");
            ms.newLine();
            ms.flush();
            ms.write("In this Txt File: !!! AffectedAgents means |those Agents|, who are affected by our policy!(In orther word, who either lives in our restricted zone, or make activities in our restricted zone!)");
            ms.newLine();
            ms.flush();
            ms.write("In this Txt File: !!! NoneResidentsAffectedAgents means |those Agents|, who are affected by our policy but not the Residents!(In orther word, who only make activities in our restricted zone!)");
            ms.newLine();
            ms.newLine();
            ms.flush();
            ms.write("In this Txt File: !!! The Unit of Percent are %");
            ms.newLine();
            ms.newLine();
            ms.flush();


            ms.write("***************************************************************************************");
            ms.newLine();
            ms.flush();
            ms.write("the residents counted in initial Plan: " + personInternalIDsList.size() + "; the residents counted in output Plan: " + ResidentsCounter);
            ms.newLine();
            ms.newLine();
            ms.flush();


            ms.write("—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
            ms.newLine();
            ms.flush();
            ms.write("AllAgents Number: " + AllAgentsCounter + "; Residents Number: " + ResidentsCounter + "; NoneResidentsAffectedAgents Number: " + NoneResidentsAffectedAgentsCounter + "; OtherAgents Number: " + OtherAgentsCounter);
            ms.newLine();
            ms.flush();
            ms.write("AllAgents Percent: " + Proportion_AllAgents + "; Residents Percent: " + Proportion_Residents + "; NoneResidentsAffectedAgents Percent: " + Proportion_NoneResidentsAffectedAgents + "; OtherAgents Percent: " + Proportion_OtherAgents);
            ms.newLine();
            ms.newLine();
            ms.flush();

            ms.newLine();
            ms.write("!!! the follow block is only for AffectedResidents !!!");
            ms.newLine();
            ms.write("AffectedResidents Number: " + AffectedResidentsCounter);
            ms.newLine();
            ms.flush();

            ms.newLine();
            ms.write("!!! the follow block is only for AffectedAgents !!!");
            ms.newLine();
            ms.write("AffectedAgents Number: " + AffectedAgentsCounter);
            ms.newLine();
            ms.newLine();
            ms.flush();


            ms.write("—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||car: " + "Residents Number: " + carCounter_Residents + "; NoneResidentsAffectedAgents Number: " + carCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + carCounter_OtherAgents + "; Sum Number: " + carCounter);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||carInternal: " + "Residents Number: " + carInternalCounter_Residents + "; NoneResidentsAffectedAgents Number: " + carInternalCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + carInternalCounter_OtherAgents + "; Sum Number: " + carInternalCounter);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||freight: " + "Residents Number: " + freightCounter_Residents + "; NoneResidentsAffectedAgents Number: " + freightCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + freightCounter_OtherAgents + "; Sum Number: " + freightCounter);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||ride: " + "Residents Number: " + rideCounter_Residents + "; NoneResidentsAffectedAgents Number: " + rideCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + rideCounter_OtherAgents + "; Sum Number: " + rideCounter);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||bicycle: " + "Residents Number: " + bicycleCounter_Residents + "; NoneResidentsAffectedAgents Number: " + bicycleCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + bicycleCounter_OtherAgents + "; Sum Number: " + bicycleCounter);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||walk: " + "Residents Number: " + walkCounter_Residents + "; NoneResidentsAffectedAgents Number: " + walkCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + walkCounter_OtherAgents + "; Sum Number: " + walkCounter);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||pt: " + "Residents Number: " + ptCounter_Residents + "; NoneResidentsAffectedAgents Number: " + ptCounter_NoneResidentsAffectedAgents + "; OtherAgents Number: " + ptCounter_OtherAgents + "; Sum Number: " + ptCounter);
            ms.newLine();
            ms.flush();

            ms.newLine();
            ms.write("!!! the follow block is only for AffectedResidents !!!");
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||car: " + "AffectedResidents Number: " + carCounter_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||carInternal: " + "AffectedResidents Number: " + carInternalCounter_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||freight: " + "AffectedResidents Number: " + freightCounter_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||ride: " + "AffectedResidents Number: " + rideCounter_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||bicycle: " + "AffectedResidents Number: " + bicycleCounter_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||walk: " + "AffectedResidents Number: " + walkCounter_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||pt: " + "AffectedResidents Number: " + ptCounter_AffectedResidents);
            ms.newLine();
            ms.newLine();
            ms.flush();

            ms.newLine();
            ms.write("!!! the follow block is only for AffectedAgents !!!");
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||car: " + "AffectedAgents Number: " + carCounter_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||carInternal: " + "AffectedAgents Number: " + carInternalCounter_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||freight: " + "AffectedAgents Number: " + freightCounter_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||ride: " + "AffectedAgents Number: " + rideCounter_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||bicycle: " + "AffectedAgents Number: " + bicycleCounter_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||walk: " + "AffectedAgents Number: " + walkCounter_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("Legs Number||pt: " + "AffectedAgents Number: " + ptCounter_AffectedAgents);
            ms.newLine();
            ms.newLine();
            ms.flush();


            ms.write("**********************************************************************************************************************************");
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||car: " + "Residents: " + carProportion_Residents + "; NoneResidentsAffectedAgents: " + carProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + carProportion_OtherAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||carInternal: " + "Residents: " + carInternalProportion_Residents + "; NoneResidentsAffectedAgents: " + carInternalProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + carInternalProportion_OtherAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||freight: " + "Residents: " + freightProportion_Residents + "; NoneResidentsAffectedAgents: " + freightProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + freightProportion_OtherAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||ride: " + "Residents: " + rideProportion_Residents + "; NoneResidentsAffectedAgents: " + rideProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + rideProportion_OtherAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||bicycle: " + "Residents: " + bicycleProportion_Residents + "; NoneResidentsAffectedAgents: " + bicycleProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + bicycleProportion_OtherAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||walk: " + "Residents: " + walkProportion_Residents + "; NoneResidentsAffectedAgents: " + walkProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + walkProportion_OtherAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||pt: " + "Residents: " + ptProportion_Residents + "; NoneResidentsAffectedAgents: " + ptProportion_NoneResidentsAffectedAgents + "; OtherAgents: " + ptProportion_OtherAgents);
            ms.newLine();
            ms.flush();

            ms.newLine();
            ms.write("!!! the follow block is only for AffectedResidents !!!");
            ms.newLine();
            ms.write("ModalSplit||car: " + "AffectedResidents: " + carProportion_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||carInternal: " + "AffectedResidents: " + carInternalProportion_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||freight: " + "AffectedResidents: " + freightProportion_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||ride: " + "AffectedResidents: " + rideProportion_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||bicycle: " + "AffectedResidents: " + bicycleProportion_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||walk: " + "AffectedResidents: " + walkProportion_AffectedResidents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||pt: " + "AffectedResidents: " + ptProportion_AffectedResidents);
            ms.newLine();
            ms.flush();

            ms.newLine();
            ms.write("!!! the follow block is only for AffectedAgents !!!");
            ms.newLine();
            ms.write("ModalSplit||car: " + "AffectedAgents: " + carProportion_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||carInternal: " + "AffectedAgents: " + carInternalProportion_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||freight: " + "AffectedAgents: " + freightProportion_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||ride: " + "AffectedAgents: " + rideProportion_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||bicycle: " + "AffectedAgents: " + bicycleProportion_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||walk: " + "AffectedAgents: " + walkProportion_AffectedAgents);
            ms.newLine();
            ms.flush();
            ms.write("ModalSplit||pt: " + "AffectedAgents: " + ptProportion_AffectedAgents);
            ms.newLine();
            ms.flush();

        //}
        // release resource
        ms.close();
        //print Txt//




/*        //print List as Txt
        // Insert Path to Output File Here!
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile_AffectedResidentsIDsList));
        // traverses the collection
        for (String s : AffectedResidentsIDsList) {
            // write data
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        // release resource
        bw.close();
        //print List as Txt//

        //print List as Txt
        // Insert Path to Output File Here!
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputFile_NoneResidentsAffectedAgentsIDsList));
        // traverses the collection
        for (String s : NoneResidentsAffectedAgentsIDsList) {
            // write data
            bw2.write(s);
            bw2.newLine();
            bw2.flush();
        }
        // release resource
        bw2.close();
        //print List as Txt//

        //print List as Txt
        // Insert Path to Output File Here!
        BufferedWriter bw3 = new BufferedWriter(new FileWriter(outputFile_AffectedAgentsIDsList));
        // traverses the collection
        for (String s : AffectedAgentsIDsList) {
            // write data
            bw3.write(s);
            bw3.newLine();
            bw3.flush();
        }
        // release resource
        bw3.close();
        //print List as Txt//

        //print List as Txt
        // Insert Path to Output File Here!
        BufferedWriter bw4 = new BufferedWriter(new FileWriter(outputFile_OtherAgentsIDsList));
        // traverses the collection
        for (String s : OtherAgentsIDsList) {
            // write data
            bw4.write(s);
            bw4.newLine();
            bw4.flush();
        }
        // release resource
        bw4.close();
        //print List as Txt//*/




    }
}