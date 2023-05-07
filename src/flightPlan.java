import java.io.*;
import java.util.*;

public class flightPlan {
    private static final String FLIGHT_DATA_FILE = "/Users/hatim/Downloads/CS/Assignments/AVL Trees/FlightPlanner/src/input.txt";
    private static final String PATHS_TO_CALCULATE_FILE = "/Users/hatim/Downloads/CS/Assignments/AVL Trees/FlightPlanner/src/PathsToCalculateFile.txt";
    private static final String OUTPUT_FILE = "/Users/hatim/Downloads/CS/Assignments/AVL Trees/FlightPlanner/src/output.txt";

    public static void main(String[] args) {

        //Read the flight data file
        ArrayList<Flight> flights = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FLIGHT_DATA_FILE))) {
            int numberOfFlights = Integer.parseInt(br.readLine());

            for (int i = 0; i < numberOfFlights; i++) {
                String[] splitArray = br.readLine().split("\\|");
                String start = splitArray[0];
                String end = splitArray[1];

                double cost = Double.parseDouble(splitArray[2]);
                int time = Integer.parseInt(splitArray[3]);

                flights.add(new Flight(start, end, cost, time));
            }
        } catch (IOException e) { //in case file not found, or line errors
            e.printStackTrace();
        }

        //Read the paths to calculate file
        ArrayList<String> flightPlans = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATHS_TO_CALCULATE_FILE))) {
            int numOfFlights = Integer.parseInt(br.readLine());

            for (int i = 0; i < numOfFlights; i++) {
                Graph graph = new Graph(); //Create a new graph instance for each flight plan

                //Add flights to the graph
                for(int j = 0; j < flights.size(); j++) {
                    graph.addFlight(flights.get(j));
                }

                String[] splitArray = br.readLine().split("\\|");
                String start = splitArray[0];
                String end = splitArray[1];
                char flag = splitArray[2].charAt(0);

                flightPlans.addAll(graph.getFlightPlans(start, end, flag, i + 1));
//                System.out.println(flightPlans);
            }
        } catch (IOException e) {  //in case file not found, or line errors
            e.printStackTrace();
        }

        // Write the output to a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
//            System.out.println("Generated flight plans: " + flightPlans); //writes onto the console for extra checking

            if (!flightPlans.isEmpty()) {
                int numOfFlights = flights.size(); //assuming the same number of flights as in the FLIGHT_DATA_FILE
                int counter = 0;

                for (int i = 0; i < flightPlans.size(); i++) {
                    if (i > 0 && (i + 1) % numOfFlights == 0 && i != flightPlans.size() - 1) {
                        bw.newLine(); //Add an empty line between flight plans
                        System.out.println();
                    }

                    bw.write(flightPlans.get(i));
                    System.out.println(flightPlans.get(i)); //writes onto the console for extra checking

                    bw.newLine();
                }
            } else {
                bw.write("NO FLIGHT PLANS FOUND, SORRY");
                bw.newLine();
            }

        } catch (IOException e) {  //in case file not found, or line errors
            e.printStackTrace();
        }
    }
}
