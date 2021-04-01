package pkg324phase2;

import java.util.*;
import javafx.util.Pair;
import java.lang.*;

public class Graph {

    static long elapsedTime;
    int vertices;
    LinkedList<Edge>[] adjacencylist;
    ArrayList<Edge> allEdges = new ArrayList<>();

    Graph(int vertices) {
        this.vertices = vertices;
        adjacencylist = new LinkedList[vertices];
        //initialize adjacency lists for all the vertices
        for (int i = 0; i < vertices; i++) {
            adjacencylist[i] = new LinkedList<>();
        }
    }

    public void addEgde(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencylist[source].addFirst(edge);

        edge = new Edge(destination, source, weight);
        adjacencylist[destination].addFirst(edge); //for undirected graph

        allEdges.add(edge);
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            LinkedList<Edge> list = adjacencylist[i];
            for (int j = 0; j < list.size(); j++) {
                System.out.println("vertex-" + i + " is connected to "
                        + list.get(j).destination + " with weight " + list.get(j).weight);
            }
        }
    }

    public void kruskalMST() {
        long startTime = System.currentTimeMillis();

        PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.weight));

        //add all the edges to priority queue, //sort the edges on weights
        for (int i = 0; i < allEdges.size(); i++) {
            pq.add(allEdges.get(i));
        }

        //create a parent []
        int[] parent = new int[vertices];

        //makeset
        makeSet(parent);

        ArrayList<Edge> mst = new ArrayList<>();

        //process vertices - 1 edges
        int index = 0;
        while (index < vertices - 1) {
            Edge edge = pq.remove();
            //check if adding this edge creates a cycle
            int x_set = find(parent, edge.source);
            int y_set = find(parent, edge.destination);

            if (x_set == y_set) {
                //ignore, will create cycle
            } else {
                //add it to our final result
                mst.add(edge);
                index++;
                union(parent, x_set, y_set);
            }
        }
        long stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;

        //print MST
        printGraph(mst);

    }

    public void printGraph(ArrayList<Edge> edgeList) {
        int total_min_weight = 0;
        System.out.println("Minimum Spanning Tree: ");
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            System.out.println("Edge:" + i + " - " + edge.source
                    + " weight: " + edge.weight);
            total_min_weight += edge.weight;

        }
        System.out.println("Total minimum weight: " + total_min_weight);
        System.out.print("Current Time in milliseconds = ");
        System.out.println(elapsedTime);
    }

    public void makeSet(int[] parent) {
        //Make set- creating a new element with a parent pointer to itself.
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
        }
    }

    public int find(int[] parent, int vertex) {
        //chain of parent pointers from x upwards through the tree
        // until an element is reached whose parent is itself
        if (parent[vertex] != vertex) {
            return find(parent, parent[vertex]);
        };
        return vertex;
    }

    public void union(int[] parent, int x, int y) {
        int x_set_parent = find(parent, x);
        int y_set_parent = find(parent, y);
        //make x as parent of y
        parent[y_set_parent] = x_set_parent;
    }

    public void primMST() {
        long startTime = System.currentTimeMillis();
        boolean[] mst = new boolean[vertices];
        ResultSet[] resultSet = new ResultSet[vertices];
        int[] key = new int[vertices];  //keys used to store the key to know whether priority queue update is required

        //Initialize all the keys to infinity and
        //initialize resultSet for all the vertices
        for (int i = 0; i < vertices; i++) {
            key[i] = Integer.MAX_VALUE;
            resultSet[i] = new ResultSet();
        }

        //Initialize priority queue
        //override the comparator to do the sorting based keys
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                //sort using key values
                int key1 = p1.getKey();
                int key2 = p2.getKey();
                return key1 - key2;
            }
        });

        //create the pair for for the first index, 0 key 0 index
        key[0] = 0;
        Pair<Integer, Integer> p0 = new Pair<>(key[0], 0);
        //add it to pq
        pq.offer(p0);

        resultSet[0] = new ResultSet();
        resultSet[0].parent = -1;

        //while priority queue is not empty
        while (!pq.isEmpty()) {
            //extract the min
            Pair<Integer, Integer> extractedPair = pq.poll();

            //extracted vertex
            int extractedVertex = extractedPair.getValue();
            mst[extractedVertex] = true;

            //iterate through all the adjacent vertices and update the keys
            LinkedList<Edge> list = adjacencylist[extractedVertex];
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge destination is not present in mst
                if (mst[edge.destination] == false) {
                    int destination = edge.destination;
                    int newKey = edge.weight;
                    //check if updated key < existing key, if yes, update if
                    if (key[destination] > newKey) {
                        //add it to the priority queue
                        Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                        pq.offer(p);
                        //update the resultSet for destination vertex
                        resultSet[destination].parent = extractedVertex;
                        resultSet[destination].weight = newKey;
                        //update the key[]
                        key[destination] = newKey;
                    }
                }
            }
        }
        long stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;

        //print mst
        printMST(resultSet);
    }

    public void HeapMST() {
        long startTime = System.currentTimeMillis();
        boolean[] inHeap = new boolean[vertices];
        ResultSet[] resultSet = new ResultSet[vertices];
        //keys[] used to store the key to know whether min hea update is required
        int[] key = new int[vertices];
//          //create heapNode for all the vertices
        HeapNode[] heapNodes = new HeapNode[vertices];
        for (int i = 0; i < vertices; i++) {
            heapNodes[i] = new HeapNode();
            heapNodes[i].vertex = i;
            heapNodes[i].key = Integer.MAX_VALUE;
            resultSet[i] = new ResultSet();
            resultSet[i].parent = -1;
            inHeap[i] = true;
            key[i] = Integer.MAX_VALUE;
        }

        //decrease the key for the first index
        heapNodes[0].key = 0;

        //add all the vertices to the MinHeap
        MinHeap minHeap = new MinHeap(vertices);
        //add all the vertices to priority queue
        for (int i = 0; i < vertices; i++) {
            minHeap.insert(heapNodes[i]);
        }

        //while minHeap is not empty
        while (!minHeap.isEmpty()) {
            //extract the min
            HeapNode extractedNode = minHeap.extractMin();

            //extracted vertex
            int extractedVertex = extractedNode.vertex;
            inHeap[extractedVertex] = false;

            //iterate through all the adjacent vertices
            LinkedList<Edge> list = adjacencylist[extractedVertex];
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge destination is present in heap
                if (inHeap[edge.destination]) {
                    int destination = edge.destination;
                    int newKey = edge.weight;
                    //check if updated key < existing key, if yes, update if
                    if (key[destination] > newKey) {
                        decreaseKey(minHeap, newKey, destination);
                        //update the parent node for destination
                        resultSet[destination].parent = extractedVertex;
                        resultSet[destination].weight = newKey;
                        key[destination] = newKey;
                    }
                }
            }
        }

        long stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
       
        //print mst
        printMST(resultSet);
    }

    public void decreaseKey(MinHeap minHeap, int newKey, int vertex) {

        //get the index which key's needs a decrease;
        int index = minHeap.indexes[vertex];

        //get the node and update its value
        HeapNode node = minHeap.mH[index];
        node.key = newKey;
        minHeap.bubbleUp(index);
    }

    public void printMST(ResultSet[] resultSet) {
        int total_min_weight = 0;
        System.out.println("Minimum Spanning Tree: ");
        for (int i = 1; i < vertices; i++) {
            System.out.println("Edge: " + i + " - " + resultSet[i].parent
                    + " weight: " + resultSet[i].weight);
            total_min_weight += resultSet[i].weight;
        }
        System.out.println("Total minimum weight: " + total_min_weight);
        System.out.print("Current Time in milliseconds = ");
        System.out.println(elapsedTime);

    }
}
