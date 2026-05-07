package org.example;
import java.util.*;

public class Main {

        // Task1
    /*
    Task 1: DFS Trace from source node A

    Graph Adjacency Lists:
    A: C B D
    B: A C E G
    C: A B D
    D: C A
    E: G F B
    F: G E
    G: F B

    Expected DFS Order (from manual trace):
    Starting from A, visiting neighbors in order given:
    A -> C -> B -> E -> G -> F -> D

    Detailed Trace:
    1. Visit A, mark as visited
    2. From A, visit C (first neighbor)
    3. From C, visit B (first unvisited neighbor)
    4. From B, visit E (first unvisited neighbor)
    5. From E, visit G (first unvisited neighbor)
    6. From G, visit F (first unvisited neighbor)
    7. From F, no unvisited neighbors, backtrack
    8. From G, no unvisited neighbors, backtrack
    9. From E, no unvisited neighbors, backtrack
    10. From B, check C (visited), check E (visited), check G (visited)
    11. From C, check A (visited), check B (visited), visit D
    12. From D, all neighbors visited, done
    Referencing to the exactly book method:
    dfs(A)
      mark[A] = true
      neighbors of A: C, B, D
      dfs(C)
        mark[C] = true
        neighbors of C: A, B, D
        dfs(A) -> mark[A] is true, return
        dfs(B)
          mark[B] = true
          neighbors of B: A, C, E, G
          dfs(A) -> marked, return
          dfs(C) -> marked, return
          dfs(E)
            mark[E] = true
            neighbors of E: G, F, B
            dfs(G)
              mark[G] = true
              neighbors of G: F, B
              dfs(F)
                mark[F] = true
                neighbors of F: G, E
                dfs(G) -> marked, return
                dfs(E) -> marked, return
              return from dfs(F)
              dfs(B) -> marked, return
            return from dfs(G)
            dfs(F) -> marked, return
            dfs(B) -> marked, return
          return from dfs(E)
          dfs(G) -> marked, return
        return from dfs(B)
        dfs(D)
          mark[D] = true
          neighbors of D: C, A
          dfs(C) -> marked, return
          dfs(A) -> marked, return
        return from dfs(D)
      return from dfs(C)
      dfs(B) -> marked, return
      dfs(D) -> marked, return
    return from dfs(A)

    Final DFS Order: A, C, B, E, G, F, D
    */

        // TASK2
    /*
    Task 2: BFS Trace from source node A

    Graph Adjacency Lists:
    A: C B D
    B: A C E G
    C: A B D
    D: C A
    E: G F B
    F: G E
    G: F B

    Expected BFS Order (from manual trace):
    Using a queue, level by level:

    Detailed Trace:
    1. Start with A, queue: [A]
    2. Dequeue A, visit A, enqueue neighbors C, B, D
       Queue: [C, B, D], Visited: A
    3. Dequeue C, visit C, enqueue unvisited neighbors (none - A,B,D already in queue or visited)
       Queue: [B, D], Visited: A, C
    4. Dequeue B, visit B, enqueue unvisited neighbors E, G
       Queue: [D, E, G], Visited: A, C, B
    5. Dequeue D, visit D, no unvisited neighbors
       Queue: [E, G], Visited: A, C, B, D
    6. Dequeue E, visit E, enqueue unvisited neighbor F
       Queue: [G, F], Visited: A, C, B, D, E
    7. Dequeue G, visit G, no unvisited neighbors
       Queue: [F], Visited: A, C, B, D, E, G
    8. Dequeue F, visit F, no unvisited neighbors
       Queue: [], Visited: A, C, B, D, E, G, F

    Final BFS Order: A, C, B, D, E, G, F
    */

        // TASK 4
    /*
    Task 4: Shortest Path from Edinburgh to Dundee using Dijkstra's Algorithm


    Edges:
    Glasgow-Stirling: 50
    Glasgow-Edinburgh: 70
    Stirling-Perth: 40
    Stirling-Edinburgh: 50
    Perth-Dundee: 60
    Perth-Edinburgh: 100

    Finding shortest path from Edinburgh to Dundee:

    Using Dijkstra's Algorithm:
    1. Start at Edinburgh (distance 0)
    2. Update neighbors:
       - Glasgow: 70
       - Stirling: 50
       - Perth: 100
    3. Visit Stirling (distance 50) - closest unvisited
       - Update Glasgow: min(70, 50+50) = 70 (no change)
       - Update Perth: min(100, 50+40) = 90
    4. Visit Glasgow (distance 70) - closest unvisited
       - No improvements
    5. Visit Perth (distance 90) - closest unvisited
       - Update Dundee: 90+60 = 150
    6. Visit Dundee (distance 150) - destination reached

    Shortest Path: Edinburgh -> Stirling -> Perth -> Dundee
    Total Distance: 50 + 40 + 60 = 150
    */

        //TASK 3: DFS AND BFS IMPLEMENTATION

        //Graph class using adjacency list representation
        static class AdjacencyListGraph {
            private Map<Character, List<Character>> adjacencyList;

            public AdjacencyListGraph() {
                adjacencyList = new HashMap<>();
            }
            //Adds a directed edge from source to destination
            public void addEdge(char source, char destination) {
                adjacencyList.putIfAbsent(source, new ArrayList<>());
                adjacencyList.get(source).add(destination);
            }


            //Adds an undirected edge between source and destination
            public void addUndirectedEdge(char source, char destination) {
                addEdge(source, destination);
                addEdge(destination, source);
            }


             //Gets neighbors of a vertex
            public List<Character> getNeighbors(char vertex) {
                return adjacencyList.getOrDefault(vertex, new ArrayList<>());
            }
        }


         // DFS Implementation using recursion
        public static List<Character> performDepthFirstSearch(AdjacencyListGraph graph, char source) {
            List<Character> dfsOrder = new ArrayList<>();
            Set<Character> visited = new HashSet<>();

            depthFirstSearchRecursive(graph, source, visited, dfsOrder);

            return dfsOrder;
        }

          //Recursive helper method for DFS
        private static void depthFirstSearchRecursive(AdjacencyListGraph graph,
                                                      char currentVertex,
                                                      Set<Character> visited,
                                                      List<Character> dfsOrder) {
            // Mark current vertex as visited and add to result
            visited.add(currentVertex);
            dfsOrder.add(currentVertex);

            // Visit all unvisited neighbors
            for (char neighbor : graph.getNeighbors(currentVertex)) {
                if (!visited.contains(neighbor)) {
                    depthFirstSearchRecursive(graph, neighbor, visited, dfsOrder);
                }
            }
        }

          //BFS Implementation using queue
        public static List<Character> performBreadthFirstSearch(AdjacencyListGraph graph, char source) {
            List<Character> bfsOrder = new ArrayList<>();
            Set<Character> visited = new HashSet<>();
            Queue<Character> queue = new LinkedList<>();

            // Start with source vertex
            visited.add(source);
            queue.offer(source);

            while (!queue.isEmpty()) {
                // Dequeue a vertex
                char currentVertex = queue.poll();
                bfsOrder.add(currentVertex);

                // Enqueue all unvisited neighbors
                for (char neighbor : graph.getNeighbors(currentVertex)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }

            return bfsOrder;
        }


         //graph from Task 1

        public static AdjacencyListGraph createTask1Graph() {
            AdjacencyListGraph graph = new AdjacencyListGraph();

            // Add edges as per Task 1 adjacency lists
            // A: C B D
            graph.addUndirectedEdge('A', 'C');
            graph.addUndirectedEdge('A', 'B');
            graph.addUndirectedEdge('A', 'D');

            // B: A C E G (A already added)
            graph.addUndirectedEdge('B', 'C');
            graph.addUndirectedEdge('B', 'E');
            graph.addUndirectedEdge('B', 'G');

            // C: A B D (all already added)
            graph.addUndirectedEdge('C', 'D');

            // D: C A (all already added)

            // E: G F B (B already added)
            graph.addUndirectedEdge('E', 'G');
            graph.addUndirectedEdge('E', 'F');

            // F: G E (E already added)
            graph.addUndirectedEdge('F', 'G');

            // G: F B (all already added)

            return graph;
        }

        //  TASK 5: DIJKSTRA'S ALGORITHM IMPLEMENTATION


         // Weighted Graph class for Dijkstra's algorithm

        static class WeightedGraph {
            private Map<String, List<Edge>> adjacencyList;

            static class Edge {
                String destination;
                int weight;

                public Edge(String destination, int weight) {
                    this.destination = destination;
                    this.weight = weight;
                }
            }

            public WeightedGraph() {
                adjacencyList = new HashMap<>();
            }


              //Adds an undirected weighted edge between two vertices

            public void addUndirectedEdge(String source, String destination, int weight) {
                adjacencyList.putIfAbsent(source, new ArrayList<>());
                adjacencyList.putIfAbsent(destination, new ArrayList<>());

                adjacencyList.get(source).add(new Edge(destination, weight));
                adjacencyList.get(destination).add(new Edge(source, weight));
            }


             //Gets all edges from a vertex

            public List<Edge> getEdges(String vertex) {
                return adjacencyList.getOrDefault(vertex, new ArrayList<>());
            }


             //Gets all vertices in the graph

            public Set<String> getAllVertices() {
                return adjacencyList.keySet();
            }
        }


         //Result class to store shortest path information

        static class ShortestPathResult {
            Map<String, Integer> distances;
            Map<String, String> previousVertices;

            public ShortestPathResult() {
                distances = new HashMap<>();
                previousVertices = new HashMap<>();
            }


             //Reconstructs the path from source to destination

            public List<String> getPathTo(String destination) {
                List<String> path = new ArrayList<>();
                String current = destination;

                while (current != null) {
                    path.add(current);
                    current = previousVertices.get(current);
                }

                Collections.reverse(path);
                return path;
            }
        }

        //Dijkstra's Algorithm Implementation

        public static ShortestPathResult dijkstraShortestPath(WeightedGraph graph, String source) {
            ShortestPathResult result = new ShortestPathResult();
            Set<String> visited = new HashSet<>();
            PriorityQueue<VertexDistance> priorityQueue = new PriorityQueue<>(
                    Comparator.comparingInt(vd -> vd.distance)
            );

            // Initialize distances
            for (String vertex : graph.getAllVertices()) {
                result.distances.put(vertex, Integer.MAX_VALUE);
            }
            result.distances.put(source, 0);

            // Add source to priority queue
            priorityQueue.offer(new VertexDistance(source, 0));

            while (!priorityQueue.isEmpty()) {
                // Get vertex with minimum distance
                VertexDistance current = priorityQueue.poll();
                String currentVertex = current.vertex;

                // Skip if already visited
                if (visited.contains(currentVertex)) {
                    continue;
                }

                visited.add(currentVertex);

                // Update distances to neighbors
                for (WeightedGraph.Edge edge : graph.getEdges(currentVertex)) {
                    String neighbor = edge.destination;
                    int newDistance = result.distances.get(currentVertex) + edge.weight;

                    if (newDistance < result.distances.get(neighbor)) {
                        result.distances.put(neighbor, newDistance);
                        result.previousVertices.put(neighbor, currentVertex);
                        priorityQueue.offer(new VertexDistance(neighbor, newDistance));
                    }
                }
            }

            return result;
        }


         //Helper class for priority queue in Dijkstra's algorithm

        static class VertexDistance {
            String vertex;
            int distance;

            public VertexDistance(String vertex, int distance) {
                this.vertex = vertex;
                this.distance = distance;
            }
        }


          //Creates the Scottish road network graph

        public static WeightedGraph createScottishRoadNetwork() {
            WeightedGraph graph = new WeightedGraph();

            // Add edges as per the diagram
            // Glasgow --50-- Stirling
            graph.addUndirectedEdge("Glasgow", "Stirling", 50);

            // Glasgow --70-- Edinburgh
            graph.addUndirectedEdge("Glasgow", "Edinburgh", 70);

            // Stirling --40-- Perth
            graph.addUndirectedEdge("Stirling", "Perth", 40);

            // Stirling --50-- Edinburgh
            graph.addUndirectedEdge("Stirling", "Edinburgh", 50);

            // Perth --60-- Dundee
            graph.addUndirectedEdge("Perth", "Dundee", 60);

            // Perth --100-- Edinburgh
            graph.addUndirectedEdge("Perth", "Edinburgh", 100);

            return graph;
        }



        public static void main(String[] args) {
            System.out.println("========================================");
            System.out.println("   GRAPH ALGORITHMS IMPLEMENTATION");
            System.out.println("========================================\n");


            System.out.println("TASK 3: DFS AND BFS IMPLEMENTATION");
            System.out.println("----------------------------------------\n");


            AdjacencyListGraph task1Graph = createTask1Graph();
            char sourceNode = 'A';

            //DFS
            System.out.println("Depth First Search (DFS) from node " + sourceNode + ":");
            List<Character> dfsResult = performDepthFirstSearch(task1Graph, sourceNode);
            System.out.println("DFS Order: " + dfsResult);
            System.out.println("Expected:  [A, C, B, E, G, F, D]");
            System.out.println("Match: " + dfsResult.equals(Arrays.asList('A', 'C', 'B', 'E', 'G', 'F', 'D')));

            System.out.println();

            //BFS
            System.out.println("Breadth First Search (BFS) from node " + sourceNode + ":");
            List<Character> bfsResult = performBreadthFirstSearch(task1Graph, sourceNode);
            System.out.println("BFS Order: " + bfsResult);
            System.out.println("Expected:  [A, C, B, D, E, G, F]");
            System.out.println("Match: " + bfsResult.equals(Arrays.asList('A', 'C', 'B', 'D', 'E', 'G', 'F')));

            System.out.println("\n========================================\n");


            System.out.println("TASK 5: DIJKSTRA'S SHORTEST PATH ALGORITHM");
            System.out.println("----------------------------------------\n");

            // Create Scottish road network
            WeightedGraph scottishGraph = createScottishRoadNetwork();
            String sourceCity = "Edinburgh";
            String destinationCity = "Dundee";

            // Run Dijkstra's algorithm
            System.out.println("Finding shortest path from " + sourceCity + " to " + destinationCity + "...");
            ShortestPathResult dijkstraResult = dijkstraShortestPath(scottishGraph, sourceCity);

            // Display results
            System.out.println("\nShortest Path: " + dijkstraResult.getPathTo(destinationCity));
            System.out.println("Total Distance: " + dijkstraResult.distances.get(destinationCity) + " km");

            System.out.println("\nExpected Path: [Edinburgh, Stirling, Perth, Dundee]");
            System.out.println("Expected Distance: 150 km");

            List<String> actualPath = dijkstraResult.getPathTo(destinationCity);
            boolean pathCorrect = actualPath.equals(Arrays.asList("Edinburgh", "Stirling", "Perth", "Dundee"));
            boolean distanceCorrect = dijkstraResult.distances.get(destinationCity) == 150;

            System.out.println("\nPath Correct: " + pathCorrect);
            System.out.println("Distance Correct: " + distanceCorrect);

            // Display all distances from Edinburgh
            System.out.println("\nAll shortest distances from Edinburgh:");
            for (Map.Entry<String, Integer> entry : dijkstraResult.distances.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " km");
            }

            System.out.println("----------------------------------------\n");


        }
    }
