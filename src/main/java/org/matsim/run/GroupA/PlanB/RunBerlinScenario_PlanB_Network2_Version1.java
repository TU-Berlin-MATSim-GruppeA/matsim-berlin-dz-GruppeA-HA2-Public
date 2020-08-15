/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.run.GroupA.PlanB;

import ch.sbb.matsim.routing.pt.raptor.RaptorIntermodalAccessEgress;
import ch.sbb.matsim.routing.pt.raptor.SwissRailRaptorModule;
import org.apache.log4j.Logger;
import org.matsim.analysis.RunPersonTripAnalysis;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.*;
import org.matsim.contrib.drt.routing.DrtRoute;
import org.matsim.contrib.drt.routing.DrtRouteFactory;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigGroup;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.config.groups.QSimConfigGroup.TrafficDynamics;
import org.matsim.core.config.groups.VspExperimentalConfigGroup;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.controler.OutputDirectoryLogging;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.population.routes.RouteFactories;
import org.matsim.core.router.AnalysisMainModeIdentifier;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.run.BerlinExperimentalConfigGroup;
import org.matsim.run.BerlinRaptorIntermodalAccessEgress;
import org.matsim.run.drt.OpenBerlinIntermodalPtDrtRouterModeIdentifier;
import org.matsim.run.drt.RunDrtOpenBerlinScenario;
import org.matsim.run.singleTripStrategies.ChangeSingleTripModeAndRoute;
import org.matsim.run.singleTripStrategies.RandomSingleTripReRoute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.matsim.core.config.groups.ControlerConfigGroup.RoutingAlgorithmType.FastAStarLandmarks;

/**
* @author ikaddoura
*/

public final class RunBerlinScenario_PlanB_Network2_Version1 {

	private static final Logger log = Logger.getLogger(RunBerlinScenario_PlanB_Network2_Version1.class );

	public static void main(String[] args) throws IOException {
		
		for (String arg : args) {
			log.info( arg );
		}
		
		if ( args.length==0 ) {
			//args = new String[] {"scenarios/berlin-v5.5-10pct/input/berlin-v5.5-10pct.config.xml"}  ;

			//******changes in PlanB_Network2_Version1******
			// 1% case
			//args = new String[] {"/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.config.xml"}  ;


			// PlanB_Network2_Version1
			args = new String[] {"scenarios/berlin-v5.5-1pct/input/PlanB/berlin-v5.5-1pct.config_PlanB_Network2_Version1.xml"}  ;
			//******changes in PlanB_Network2_Version1******//

		}

		Config config = prepareConfig( args ) ;

		//set LastIteration
		config.controler().setLastIteration(100);

		//******changes in PlanB_Network2_Version1******
		config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
		//******changes in PlanB_Network2_Version1******//

		Scenario scenario = prepareScenario( config ) ;




		//******changes in PlanB_Network2_Version1******
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
		//——————Input As List——————//




		// delete initial routes only for Non-residents
		for (Person person : scenario.getPopulation().getPersons().values()) {

			//******changes in PlanB_Network2_Version1******
			Activity homeActivity = (Activity) person.getPlans().get(0).getPlanElements().get(0);
			String homeActAsLink = homeActivity.getLinkId().toString();
			if (!personInternalLinkList.contains(homeActAsLink)) {
				//******changes in PlanB_Network2_Version1******//

				for (Plan plan : person.getPlans()) {
					for (PlanElement pe : plan.getPlanElements()) {
						if (pe instanceof Activity) {
							((Activity) pe).setLinkId(null);
						} else if (pe instanceof Leg) {
							((Leg) pe).setRoute(null);
						} else {
							throw new RuntimeException("Plan element can either be activity or leg.");
						}
					}
				}
		    }
		}
		//******changes in PlanB_Network2_Version1******//

		Controler controler = prepareControler( scenario ) ;
		controler.run() ;

	}

	public static Controler prepareControler( Scenario scenario ) {
		// note that for something like signals, and presumably drt, one needs the controler object
		
		Gbl.assertNotNull(scenario);
		
		final Controler controler = new Controler( scenario );
		
		if (controler.getConfig().transit().isUseTransit()) {
			// use the sbb pt raptor router
			controler.addOverridingModule( new AbstractModule() {
				@Override
				public void install() {
					install( new SwissRailRaptorModule() );
				}
			} );
		} else {
			log.warn("Public transit will be teleported and not simulated in the mobsim! "
					+ "This will have a significant effect on pt-related parameters (travel times, modal split, and so on). "
					+ "Should only be used for testing or car-focused studies with a fixed modal split.  ");
		}
		
		
		
		// use the (congested) car travel time for the teleported ride mode
		controler.addOverridingModule( new AbstractModule() {
			@Override
			public void install() {
				addTravelTimeBinding( TransportMode.ride ).to( networkTravelTime() );
				addTravelDisutilityFactoryBinding( TransportMode.ride ).to( carTravelDisutilityFactoryKey() );
				bind(AnalysisMainModeIdentifier.class).to(OpenBerlinIntermodalPtDrtRouterModeIdentifier.class);
				
				addPlanStrategyBinding("RandomSingleTripReRoute").toProvider(RandomSingleTripReRoute.class);
				addPlanStrategyBinding("ChangeSingleTripModeAndRoute").toProvider(ChangeSingleTripModeAndRoute.class);

				bind(RaptorIntermodalAccessEgress.class).to(BerlinRaptorIntermodalAccessEgress.class);
			}
		} );

		return controler;
	}
	
	public static Scenario prepareScenario( Config config ) {
		Gbl.assertNotNull( config );
		
		// note that the path for this is different when run from GUI (path of original config) vs.
		// when run from command line/IDE (java root).  :-(    See comment in method.  kai, jul'18
		// yy Does this comment still apply?  kai, jul'19

		/*
		 * We need to set the DrtRouteFactory before loading the scenario. Otherwise DrtRoutes in input plans are loaded
		 * as GenericRouteImpls and will later cause exceptions in DrtRequestCreator. So we do this here, although this
		 * class is also used for runs without drt.
		 */
		final Scenario scenario = ScenarioUtils.createScenario( config );

		RouteFactories routeFactories = scenario.getPopulation().getFactory().getRouteFactories();
		routeFactories.setRouteFactory(DrtRoute.class, new DrtRouteFactory());
		
		ScenarioUtils.loadScenario(scenario);

		BerlinExperimentalConfigGroup berlinCfg = ConfigUtils.addOrGetModule(config, BerlinExperimentalConfigGroup.class);
		if (berlinCfg.getPopulationDownsampleFactor() != 1.0) {
			downsample(scenario.getPopulation().getPersons(), berlinCfg.getPopulationDownsampleFactor());
		}
		
		return scenario;
	}

	public static Config prepareConfig( String [] args, ConfigGroup... customModules ){
		return prepareConfig( RunDrtOpenBerlinScenario.AdditionalInformation.none, args, customModules ) ;
	}
	public static Config prepareConfig(RunDrtOpenBerlinScenario.AdditionalInformation additionalInformation, String [] args,
                                       ConfigGroup... customModules ) {
		OutputDirectoryLogging.catchLogEntries();
		
		String[] typedArgs = Arrays.copyOfRange( args, 1, args.length );
		
		ConfigGroup[] customModulesToAdd = null ;
		if ( additionalInformation== RunDrtOpenBerlinScenario.AdditionalInformation.acceptUnknownParamsBerlinConfig ) {
			customModulesToAdd = new ConfigGroup[]{ new BerlinExperimentalConfigGroup(true) };
		} else {
			customModulesToAdd = new ConfigGroup[]{ new BerlinExperimentalConfigGroup(false) };
		}
		ConfigGroup[] customModulesAll = new ConfigGroup[customModules.length + customModulesToAdd.length];
		
		int counter = 0;
		for (ConfigGroup customModule : customModules) {
			customModulesAll[counter] = customModule;
			counter++;
		}
		
		for (ConfigGroup customModule : customModulesToAdd) {
			customModulesAll[counter] = customModule;
			counter++;
		}
		
		final Config config = ConfigUtils.loadConfig( args[ 0 ], customModulesAll );
		
		config.controler().setRoutingAlgorithmType( FastAStarLandmarks );
		
		config.subtourModeChoice().setProbaForRandomSingleTripMode( 0.5 );
		
		config.plansCalcRoute().setRoutingRandomness( 3. );
		config.plansCalcRoute().removeModeRoutingParams(TransportMode.ride);
		config.plansCalcRoute().removeModeRoutingParams(TransportMode.pt);
		config.plansCalcRoute().removeModeRoutingParams(TransportMode.bike);
		config.plansCalcRoute().removeModeRoutingParams("undefined");
		
		config.qsim().setInsertingWaitingVehiclesBeforeDrivingVehicles( true );
				
		// vsp defaults
		config.vspExperimental().setVspDefaultsCheckingLevel( VspExperimentalConfigGroup.VspDefaultsCheckingLevel.info );
		config.plansCalcRoute().setInsertingAccessEgressWalk( true );
		config.qsim().setUsingTravelTimeCheckInTeleportation( true );
		config.qsim().setTrafficDynamics( TrafficDynamics.kinematicWaves );
				
		// activities:
		for ( long ii = 600 ; ii <= 97200; ii+=600 ) {
			config.planCalcScore().addActivityParams( new ActivityParams( "home_" + ii + ".0" ).setTypicalDuration( ii ) );
			config.planCalcScore().addActivityParams( new ActivityParams( "work_" + ii + ".0" ).setTypicalDuration( ii ).setOpeningTime(6. * 3600. ).setClosingTime(20. * 3600. ) );
			config.planCalcScore().addActivityParams( new ActivityParams( "leisure_" + ii + ".0" ).setTypicalDuration( ii ).setOpeningTime(9. * 3600. ).setClosingTime(27. * 3600. ) );
			config.planCalcScore().addActivityParams( new ActivityParams( "shopping_" + ii + ".0" ).setTypicalDuration( ii ).setOpeningTime(8. * 3600. ).setClosingTime(20. * 3600. ) );
			config.planCalcScore().addActivityParams( new ActivityParams( "other_" + ii + ".0" ).setTypicalDuration( ii ) );
		}
		config.planCalcScore().addActivityParams( new ActivityParams( "freight" ).setTypicalDuration( 12.*3600. ) );

		ConfigUtils.applyCommandline( config, typedArgs ) ;

		return config ;
	}
	
	public static void runAnalysis(Controler controler) {
		Config config = controler.getConfig();
		
		String modesString = "";
		for (String mode: config.planCalcScore().getAllModes()) {
			modesString = modesString + mode + ",";
		}
		// remove last ","
		if (modesString.length() < 2) {
			log.error("no valid mode found");
			modesString = null;
		} else {
			modesString = modesString.substring(0, modesString.length() - 1);
		}
		
		String[] args = new String[] {
				config.controler().getOutputDirectory(),
				config.controler().getRunId(),
				"null", // TODO: reference run, hard to automate
				"null", // TODO: reference run, hard to automate
				config.global().getCoordinateSystem(),
				"https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/projects/avoev/shp-files/shp-bezirke/bezirke_berlin.shp",
				TransformationFactory.DHDN_GK4,
				"SCHLUESSEL",
				"home",
				"10", // TODO: scaling factor, should be 10 for 10pct scenario and 100 for 1pct scenario
				"null", // visualizationScriptInputDirectory
				modesString
		};
		
		try {
			RunPersonTripAnalysis.main(args);
		} catch (IOException e) {
			log.error(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private static void downsample( final Map<Id<Person>, ? extends Person> map, final double sample ) {
		final Random rnd = MatsimRandom.getLocalInstance();
		log.warn( "Population downsampled from " + map.size() + " agents." ) ;
		map.values().removeIf( person -> rnd.nextDouble() > sample ) ;
		log.warn( "Population downsampled to " + map.size() + " agents." ) ;
	}

}

