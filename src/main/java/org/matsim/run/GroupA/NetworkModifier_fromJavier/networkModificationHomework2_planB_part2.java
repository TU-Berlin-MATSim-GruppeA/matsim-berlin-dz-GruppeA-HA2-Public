package org.matsim.run.GroupA.NetworkModifier_fromJavier;

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

public class networkModificationHomework2_planB_part2 {
    public static void main(String[] args) throws IOException {
        Path inputNetwork = Paths.get(args[0]);
        Path outputNetwork = Paths.get(args[1]);
        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        List<Integer> innerZone = new ArrayList<Integer>();
        Scanner scanner = new Scanner(new File("LinkIDs_InnerZone_Run2_PlanB.txt"));
        while (scanner.hasNextInt()) {
            Integer id = scanner.nextInt();
            innerZone.add(id);

        }
        scanner.close();

        Set<String> allowedModes2 = new HashSet<String>();
        allowedModes2.add("walk");
        allowedModes2.add("bicycle");
        allowedModes2.add("freight");
        for (int i = 0; i < innerZone.size(); i++) {
            network.getLinks().get(Id.createLinkId(innerZone.get(i))).setAllowedModes(allowedModes2);

        }
        new NetworkWriter(network).write(outputNetwork.toString());
    }
}
