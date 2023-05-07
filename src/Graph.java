import java.util.*;

public class Graph {

    //Create a map where each starting city will link to a linked list that has all directly connected flights
    private Map<String, LinkedList<Flight>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    //Create a way to add a flight to each linked list within the map
    public void addFlight(Flight flight) {
        String start = flight.getStart();

        LinkedList<Flight> flightsFromStart = adjacencyList.get(start);
        if (flightsFromStart == null) {     //checks to see if start city exists
            flightsFromStart = new LinkedList<>();
            flightsFromStart.add(flight);
            adjacencyList.put(start, flightsFromStart);
        } else {    //adds flight if it does exist
            adjacencyList.get(start).add(flight);
        }
    }

    //Find all paths method
    private void findPaths(String start, String end, List<Flight> currentPath, List<List<Flight>> allPaths, Stack<String> sortedCities) {
        if (start.equals(end)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        LinkedList<Flight> neighbors = adjacencyList.get(start);

        //If no flights are connected to the start city then return nothing
        if (neighbors == null) {
            return;
        }

        for (int i = 0; i < neighbors.size(); i++) {
            Flight flight = neighbors.get(i);
            String nextCity = flight.getEnd();

            //Check if the next city is valid based on the order of the cities
            if (sortedCities.indexOf(start) < sortedCities.indexOf(nextCity)) {
                currentPath.add(flight);

                //Basically this will recursively search for paths from the cities linked through recursion
                findPaths(nextCity, end, currentPath, allPaths, sortedCities);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    //Topological sort method
    public Stack<String> topologicalSort() {
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        List<String> cities = new LinkedList<>(adjacencyList.keySet());

        //Iterate through all cities and visit them if they haven't been visited
        for (int i = 0; i < cities.size(); i++) {
            String city = cities.get(i);
            if (!visited.contains(city)) {
                visit(city, visited, stack);
            }
        }

        return stack;
    }

    //Visit method topological sort
    private void visit(String city, Set<String> visited, Stack<String> stack) {
        if (visited.contains(city)) {
            return;
        }

        //Mark city as visited
        visited.add(city);

        //Get neighbors (directly connected flights) for the current city
        LinkedList<Flight> neighbors = adjacencyList.get(city);

        //If neighbors are not null, visit all neighbors
        if (neighbors != null) {
            for (int i = 0; i < neighbors.size(); i++) {
                Flight flight = neighbors.get(i);
                visit(flight.getEnd(), visited, stack);
            }
        }

        //Add city to the stack after visiting all its neighbors
        stack.push(city);
    }

    public List<String> getFlightPlans(String start, String end, char flag) {
        List<String> result = new ArrayList<>();
        List<List<Flight>> allPaths = new ArrayList<>();

        //Perform the topological sort
        Stack<String> sortedCities = topologicalSort();

        //Find paths using topological sort
        findPaths(start, end, new ArrayList<>(), allPaths, sortedCities);

        //Sort the paths based on the flag input
        if (flag == 'T') {
            timeSorter(allPaths);
        } else if (flag == 'C') {
            costSorter(allPaths);
        }

        //Iterate through all paths and generate the result strings
        for (int i = 0; i < 3; i++) {
            List<Flight> path = allPaths.get(i);
            String ans = "Path: ";
            double totalCost = 0;
            int totalTime = 0;

            for (int j = 0; j < path.size(); j++) {
                Flight flight = path.get(j);
                ans += flight.getStart() + " -> " + flight.getEnd() + ", ";
                totalCost += flight.getCost();
                totalTime += flight.getTime();
            }

            ans += "Time: " + totalTime + ", Cost: " + String.format("%.2f", totalCost);
            result.add(ans);
        }

        return result;
    }

    //Time sorter, this method sorts all paths given based on time and orders them accordingly
    private void timeSorter(List<List<Flight>> allPaths) {

        for (int i = 0; i < allPaths.size() - 1; i++) {
            for (int j = 0; j < allPaths.size() - i - 1; j++) {
                int timePath1 = 0;
                int timePath2 = 0;

                for (int k = 0; k < allPaths.get(j).size(); k++) {
                    timePath1 += allPaths.get(j).get(k).getTime();
                }

                for (int k = 0; k < allPaths.get(j + 1).size(); k++) {
                    timePath2 += allPaths.get(j + 1).get(k).getTime();
                }

                if (timePath1 > timePath2) {
                    List<Flight> temp = allPaths.get(j);
                    allPaths.set(j, allPaths.get(j + 1));
                    allPaths.set(j + 1, temp);
                }
            }
        }
    }

    //Cost Sorter, this method sorts all paths given based on cost and orders them accordingly
    private void costSorter(List<List<Flight>> allPaths) {

        for (int i = 0; i < allPaths.size() - 1; i++) {
            for (int j = 0; j < allPaths.size() - i - 1; j++) {
                int costPath1 = 0;
                int costPath2 = 0;

                for (int k = 0; k < allPaths.get(j).size(); k++) {
                    costPath1 += allPaths.get(j).get(k).getCost();
                }

                for (int k = 0; k < allPaths.get(j + 1).size(); k++) {
                    costPath2 += allPaths.get(j + 1).get(k).getCost();
                }

                if (costPath1 > costPath2) {
                    List<Flight> temp = allPaths.get(j);
                    allPaths.set(j, allPaths.get(j + 1));
                    allPaths.set(j + 1, temp);
                }
            }
        }
    }
}
