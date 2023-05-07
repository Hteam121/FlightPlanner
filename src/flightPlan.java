import java.io.*;
import java.util.*;

public class flightPlan {
    public static void main(String[] args) {

        //checks to see if the arguments are correct within the command line
        if (args.length != 3) {
            System.out.println("Usage: java flightPlan <FlightDataFile> <PathsToCalculateFile> <OutputFile>");
            System.exit(1);
        }

        //stores to corresponding variables based on given arguments
        String flightDataFile = args[0];
        String pathsToCalculateFile = args[1];
        String outputFile = args[2];

        //Create a new Graph to store data
        Graph graph = new Graph();

        // Read the flight data file
        try (BufferedReader br = new BufferedReader(new FileReader(flightDataFile))) {
            int numberOfFlights = Integer.parseInt(br.readLine());

            for (int i = 0; i < numberOfFlights; i++) {
                String[] splitArray = br.readLine().split("\\|");
                String start = splitArray[0];
                String end = splitArray[1];

                double cost = Double.parseDouble(splitArray[2]);
                int time = Integer.parseInt(splitArray[3]);

                graph.addFlight(new Flight(start, end, cost, time));
            }
        } catch (IOException e) { //in case file not found, or line errors
            e.printStackTrace();
        }

        // Read the paths to calculate file
        List<String> flightPlans = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathsToCalculateFile))) {
            int numberOfPaths = Integer.parseInt(br.readLine());

            for (int i = 0; i < numberOfPaths; i++) {
                String[] splitArray = br.readLine().split("\\|");
                String start = splitArray[0];
                String end = splitArray[1];
                char flag = splitArray[2].charAt(0);

                flightPlans.addAll(graph.getFlightPlans(start, end, flag));
            }
        } catch (IOException e) {  //in case file not found, or line errors
            e.printStackTrace();
        }

        // Write the output to a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String plan : flightPlans) {
                bw.write(plan);
                bw.newLine();
            }
        } catch (IOException e) {  //in case file not found, or line errors
            e.printStackTrace();
        }
    }
}
