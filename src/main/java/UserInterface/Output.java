package UserInterface;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

//import org.graalvm.compiler.loop.InductionVariable;
 class Output {

    /**
     * gives some navigation instructions for the route on the run console

     *
     */
    void output(String origin, String destination, DirectionsRoute naviList) {

        System.out.println("Distance of the Route: " + (double) Math.round(naviList.distance()/10)/100 + " kilometers");
        System.out.println("Duration of the Route: " + Math.round(naviList.duration()/3600) + " Hours " + Math.round(naviList.duration()%3600/60) + " Minutes");
        // print in which direction the user should drive on the first street
        System.out.println("navigation from " + origin + " to " + destination + ": \n" + naviList.legs().get(0).steps().get(0).maneuver().instruction());

        // print how long the user should stay on the actual street and which street should be used next
        for (int j = 0; j < naviList.legs().size(); j++) {

            for (int i = 0; i < naviList.legs().get(j).steps().size() - 1; i++) {
                int instructionSize = naviList.legs().get(j).steps().get(i).voiceInstructions().size();
                double distance = naviList.legs().get(j).steps().get(i).distance();
                if (distance < 1000) {
                    System.out.println("stay on the street for " + Math.round(distance / 10) * 10 + " meters");
                } else {
                    System.out.println("stay on the street for " + (double) Math.round(distance / 100) / 10 + " kilometers");
                }

                System.out.println(naviList.legs().get(j).steps().get(i).voiceInstructions().get(instructionSize - 1).announcement());
                /*
                try {
                    Thread.sleep(500);
                } catch(Exception e) {

                }
                */

            }



        }
    }
}
