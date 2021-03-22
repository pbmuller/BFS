import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

public class EulerPath {
    public static void main (String[] args) {
        /*
        Input: 2 lines
        first line: Starting node
        second line: List of Vectors that are in the Graph

        Output: Distance from Starting Node to all of the

        How we will solve:
        We will be searching for the minimal distances from all nodes on the graph to the
        starting node using a simple breadth first search.
         */

        Scanner line = new Scanner(System.in);

        // 1. Get input
        String startingNode = line.nextLine();
        String vectorList = line.nextLine();

        // 2. Convert input vector list into proper vector list
        List<Vector> graph = Vector.parseInput(vectorList);

        List<String> distinctNodes = Vector.getDistinctNodes(vectorList);
        // 3. Initialize Distances List
        List<Map.Entry<String, Integer>> distances = new ArrayList<>();

        // 4. Loop:
        //    Get all available vectors (Vectors that are able to be traversed from the current nodes)
        //    If we have a distances for the end node, ignore that vector
        //    add the end node to the distances list
        //    Increment distance
        //    update available vectors

        List<String> currentNodes = new ArrayList<>();
        currentNodes.add(startingNode);
        int distance = 1;

        while (!currentNodes.isEmpty()) {
            List<Vector> availableVectors = getAvailableNodes(currentNodes, graph);
            System.out.println("Iteration" + distance);
            System.out.println("Available Vectors = " + availableVectors);

            currentNodes.clear();

            for (Vector vector : availableVectors) {
                String endNode = vector.getEnd();
                if (distancesContainsNode(distances, endNode)) continue;

                currentNodes.add(endNode);
                distances.add(new AbstractMap.SimpleEntry<>(endNode, distance));
            }
            distance++;
        }

        // 5. Print the results
        List<String> foundDistances = distances.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<String> unfoundDistances = distinctNodes.stream().filter(node -> !foundDistances.contains(node) && !node.equals(startingNode)).collect(Collectors.toList());

        for (Map.Entry<String, Integer> distancePair : distances) {
            System.out.printf("%s %s%n", distancePair.getKey(), distancePair.getValue());
        }
        for (String unfound : unfoundDistances) {
            System.out.println(unfound + " INF");
        }
    }

    private static List<Vector> getAvailableNodes(List<String> nodes, List<Vector> graph) {
        List<Vector> result = new ArrayList<>();
        for (String node : nodes) {
            result.addAll(graph.stream()
                    .filter(vector -> vector.getStart().equals(node))
                    .collect(Collectors.toList())
            );
        }
        return result;
    }

    private static boolean distancesContainsNode(List<Map.Entry<String, Integer>> distances, String node) {
        return distances.stream().anyMatch(distance -> distance.getKey().equals(node));
    }
}

// Helper class that defines a vector
class Vector {
    final String start;
    final String end;

    Vector(String start, String end) {
        this.start = start;
        this.end = end;
    }

    String getStart() {
        return start;
    }

    String getEnd() {
        return end;
    }

    static List<Vector> parseInput(String input) {
        List<Vector> result = new ArrayList<>();
        String[] groups = input.split(";");
        for (String group : groups) {
            String[] nodes = group.split(",");
            result.add(new Vector(nodes[0], nodes[1]));
        }
        return result;
    }

    static List<String> getDistinctNodes(String input) {
        List<String> result = new ArrayList<>();
        String[] nodes = input.split(";");
        for (String node : nodes) {
            String[] nodeValues = node.split(",");
            if (!result.contains(nodeValues[0])) {
                result.add(nodeValues[0]);
            }
            if (!result.contains(nodeValues[1])) {
                result.add(nodeValues[1]);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s %s]", start, end);
    }
}




