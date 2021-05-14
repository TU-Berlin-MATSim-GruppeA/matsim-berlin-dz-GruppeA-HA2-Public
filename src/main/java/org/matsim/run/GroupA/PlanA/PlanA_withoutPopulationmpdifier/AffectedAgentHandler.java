package org.matsim.run.GroupA.PlanA.PlanA_withoutPopulationmpdifier;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AffectedAgentHandler implements PersonDepartureEventHandler, LinkEnterEventHandler, PersonArrivalEventHandler, ActivityEndEventHandler {
    public Set<Id> internalCarZonelinkIDlist = new HashSet<Id>();
    public Set<Id> modifiedZonelinkIDlist = new HashSet<Id>();
    public Set<Id> residentAndWorkerAgentIDlist = new HashSet<Id>();
    public Set<Id> affectedAgentIDlist = new HashSet<Id>();

    public AffectedAgentHandler(String outputFile) throws IOException{

        Scanner scanner3 = new Scanner(new File("/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/TxtFile/LinkIDs_InNoCarZone.txt"));
        while(scanner3.hasNextLine())
        {
            String id = scanner3.nextLine();
            modifiedZonelinkIDlist.add(Id.createLinkId(id));
            internalCarZonelinkIDlist.add(Id.createLinkId(id));

        }
        scanner3.close();
    }



    @Override
    public void handleEvent(PersonDepartureEvent event){
        Id person = event.getPersonId();
        if(internalCarZonelinkIDlist.contains(event.getLinkId())){

            residentAndWorkerAgentIDlist.add(person);
        }
    }

    @Override
    public void handleEvent(PersonArrivalEvent event){
        Id person = event.getPersonId();
        if(internalCarZonelinkIDlist.contains(event.getLinkId())){
            residentAndWorkerAgentIDlist.add(person);
        }
    }

    @Override
    public void handleEvent(ActivityEndEvent event){
        Id linkID = event.getLinkId();
        if (modifiedZonelinkIDlist.contains(linkID)){
            residentAndWorkerAgentIDlist.add(event.getPersonId());
        }
    }

    @Override
    public void handleEvent(LinkEnterEvent event){
        Id linkID = event.getLinkId();
        if(modifiedZonelinkIDlist.contains(linkID)){
            affectedAgentIDlist.add(event.getVehicleId());
        }
    }

    public void printResults() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/TxtFile/resident_and_worker_Agents_HA2.txt"));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/TxtFile/affected_Vehicles_HA2.txt"));
        System.out.println("******************************************");
        System.out.println("Total resident and worker agents: " + residentAndWorkerAgentIDlist.size());
        System.out.println("******************************************");
        System.out.println("******************************************");
        System.out.println("Total affected vehicles: " + affectedAgentIDlist.size());
        System.out.println("******************************************");

        for (Id person : residentAndWorkerAgentIDlist) {
            writer.write(person.toString()+ "\n");
        }
        writer.close();
        for (Id person : affectedAgentIDlist) {
            writer2.write(person.toString()+ "\n");
        }
        writer2.close();
    }

}

