import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Felipe Bergerman
 * @version 1.0
 * @userid fbergerman3
 * @GTID 903785770
 * <p>
 * Collaborators: N/A
 * <p>
 * Resources: N/A
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex must be in the graph");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        LinkedList<Vertex<T>> queue = new LinkedList<>();
        ArrayList<Vertex<T>> res = new ArrayList<>();
        queue.addLast(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> currVert = queue.removeFirst();
            res.add(currVert);
            List<VertexDistance<T>> adj = graph.getAdjList().get(currVert);
            for (VertexDistance<T> tVertexDistance : adj) {
                Vertex<T> adjVert = tVertexDistance.getVertex();
                if (!visited.contains(adjVert)) {
                    queue.addLast(adjVert);
                    visited.add(adjVert);
                }
            }
        }
        return res;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex must be in the graph");
        }
        return dfsHelper(start, graph, new HashSet<Vertex<T>>(), new ArrayList<Vertex<T>>());
    }

    /**
     * Private helper method that performs a depth first search.
     *
     * @param curr the current node being analyzed
     * @param graph the graph being searched through
     * @param visited a set representing visited nodes
     * @param res the resulting list of the nodes visited in the dfs
     * @return list of vertices in visited order
     * @param <T> the generic type held by the verticies in the graphs
     */
    private static <T> List<Vertex<T>> dfsHelper(Vertex<T> curr, Graph<T> graph,
                                                 Set<Vertex<T>> visited, List<Vertex<T>> res) {
        res.add(curr);
        visited.add(curr);
        List<VertexDistance<T>> adjDist = graph.getAdjList().get(curr);
        for (VertexDistance<T> d : adjDist) {
            Vertex<T> adjVertex = d.getVertex();
            if (!visited.contains(adjVertex)) {
                visited.add(adjVertex);
                dfsHelper(adjVertex, graph, visited, res);
            }
        }
        return res;
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex must be in graph");
        }

        Map<Vertex<T>, Integer> res = new HashMap<>();
        Set<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();

        for (Vertex<T> v : graph.getVertices()) {
            res.put(v, Integer.MAX_VALUE);
        }

        priorityQueue.add(new VertexDistance<>(start, 0));
        res.put(start, 0);

        while (!priorityQueue.isEmpty() && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> currVD = priorityQueue.remove();
            Vertex<T> currV = currVD.getVertex();
            int currD = currVD.getDistance();
            if (!visited.contains(currV)) {
                visited.add(currV);
                List<VertexDistance<T>> adj = graph.getAdjList().get(currV);
                for (VertexDistance<T> adjVertDistance : adj) {
                    if (!visited.contains(adjVertDistance.getVertex())) {
                        res.put(adjVertDistance.getVertex(), Integer.min(adjVertDistance.getDistance() + currD,
                                res.get(adjVertDistance.getVertex())));
                        priorityQueue.add(new VertexDistance<>(adjVertDistance.getVertex(),
                                adjVertDistance.getDistance() + currD));
                    }
                }
            }
        }
        return res;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * An MST should NOT have self-loops or parallel edges.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        Set<Edge<T>> res = new HashSet<>();
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>(graph.getEdges()); //CHECK EFFICIENCY

        for (Vertex<T> vert : graph.getVertices()) {
            disjointSet.find(vert);
        }

        while (!priorityQueue.isEmpty() && res.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> currEdge = priorityQueue.remove();
            if (!disjointSet.find(currEdge.getV()).equals(disjointSet.find(currEdge.getU()))) {
                res.add(currEdge);
                res.add(new Edge<>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                disjointSet.union(currEdge.getU(), currEdge.getV());
            }
        }
        if (res.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return res;
    }
}
