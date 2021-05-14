package org.matsim.run.GroupA.PlanA.PlanA_withoutPopulationmpdifier;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

import java.io.IOException;

public class RunEventsHandler {

    public static void main(String[] args) throws IOException {

        String inputFile = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct_baseCase/berlin-v5.5-1pct.output_events.xml.gz";
        String outputFile = "/Users/haowu/Workspace/fixedRepository/matsim-berlin-dz/scenarios/berlin-v5.5-1pct/input/TxtFile/LinkIDs_InNoCarZone.txt";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        // SimpleEventHandler eventHandler = new SimpleEventHandler();
        AffectedAgentHandler eventHandler = new AffectedAgentHandler(outputFile);
        eventsManager.addHandler(eventHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        eventHandler.printResults();
    }
}