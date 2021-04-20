/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author s
 */
// Arup Guha
// 8/24/2011
// Uses Edmunds-Karp Algorithm to calculate Maximum Flow in a Flow Network.
import java.util.*;
import java.util.LinkedList; 
import java.util.Queue; 


class Edge {

    private int capacity;
    private int flow;

    public Edge(int cap) {
        capacity = cap;
        flow = 0;
    }

    public int maxPushForward() {
        return capacity - flow;
    }

    public int maxPushBackward() {
        return flow;
    }

    public boolean pushForward(int moreflow) {

        // We can't push through this much flow.
        if (flow + moreflow > capacity) {
            return false;
        }

        // Push through.
        flow += moreflow;
        return true;
    }

    public boolean pushBack(int lessflow) {

        // Not enough to push back on.
        if (flow < lessflow) {
            return false;
        }

        flow -= lessflow;
        return true;
    }
}

class direction {

    public int prev;
    public boolean forward;

    public direction(int node, boolean dir) {
        prev = node;
        forward = dir;
    }

    public String toString() {
        if (forward) {
            return "" + prev + " --> ";
        } else {
            return "" + prev + " <-- ";
        }
    }
}

public class Networkflow {

    private final static boolean DEBUG = false;
    private final static boolean PRINTPATH = true;

    private Edge[][] adjMat;
    private int source;
    private int dest;

    // All positive entries in flows should represent valid flows
    // between vertices. All other entries must be 0 or negative.
    public Networkflow(int[][] flows, int start, int end) {

        source = start;
        dest = end;
        adjMat = new Edge[flows.length][flows.length];

        for (int i = 0; i < flows.length; i++) {
            for (int j = 0; j < flows[i].length; j++) {

                // Fill in this flow.
                if (flows[i][j] > 0) {
                    adjMat[i][j] = new Edge(flows[i][j]);
                } else {
                    adjMat[i][j] = null;
                }
            }
        }
    }

    public ArrayList<direction> findAugmentingPath() {

        // This will store the previous node visited in the BFS.
        direction[] prev = new direction[adjMat.length];
        boolean[] inQueue = new boolean[adjMat.length];
        for (int i = 0; i < inQueue.length; i++) {
            inQueue[i] = false;
        }

        // The source has no previous node.
        prev[source] = new direction(-1, true);

        LinkedList<Integer> bfs_queue = new LinkedList<Integer>();
        bfs_queue.offer(new Integer(source));
        inQueue[source] = true;

        // Our BFS will go until we clear out the queue.
        while (bfs_queue.size() > 0) {

            // Add all the new neighbors of the current node.
            Integer next = bfs_queue.poll();
            if (DEBUG) {
                System.out.println("Searching " + next);
            }

            // Find all neighbors and add into the queue. These are forward edges.
            for (int i = 0; i < adjMat.length; i++) {
                if (!inQueue[i] && adjMat[next][i] != null && adjMat[next][i].maxPushForward() > 0) {
                    bfs_queue.offer(new Integer(i));
                    inQueue[i] = true;
                    prev[i] = new direction(next, true);
                }
            }

            // Now look for back edges.
            for (int i = 0; i < adjMat.length; i++) {
                if (!inQueue[i] && adjMat[i][next] != null && adjMat[i][next].maxPushBackward() > 0) {
                    bfs_queue.offer(new Integer(i));
                    inQueue[i] = true;
                    prev[i] = new direction(next, false);
                }
            }
        }

        // No augmenting path found.
        if (!inQueue[dest]) {
            return null;
        }

        ArrayList<direction> path = new ArrayList<direction>();

        direction place = prev[dest];

        direction dummy = new direction(dest, true);
        path.add(dummy);

        // Build the path backwards.
        while (place.prev != -1) {
            path.add(place);
            place = prev[place.prev];
        }

        // Reverse it now.
        Collections.reverse(path);

        return path;
    }

    // Run the Max Flow Algorithm here.
    public int getMaxFlow() {

        int flow = 0;

        ArrayList<direction> nextpath = findAugmentingPath();

        if (DEBUG || PRINTPATH) {
            System.out.println("Augmenting path ");
            for (int i = 0; i < nextpath.size(); i++) {
                System.out.print(nextpath.get(i) + " ");
            }
            System.out.println();
        }

        // Loop until there are no more augmenting paths.
        while (nextpath != null) {

            // Check what the best flow through this path is.
            int this_flow = Integer.MAX_VALUE;
            for (int i = 0; i < nextpath.size() - 1; i++) {

                if (nextpath.get(i).forward) {
                    this_flow = Math.min(this_flow, adjMat[nextpath.get(i).prev][nextpath.get(i + 1).prev].maxPushForward());
                } else {
                    this_flow = Math.min(this_flow, adjMat[nextpath.get(i + 1).prev][nextpath.get(i).prev].maxPushBackward());
                }
            }

            // Now, put this flow through.
            for (int i = 0; i < nextpath.size() - 1; i++) {

                if (nextpath.get(i).forward) {
                    adjMat[nextpath.get(i).prev][nextpath.get(i + 1).prev].pushForward(this_flow);
                } else {
                    adjMat[nextpath.get(i + 1).prev][nextpath.get(i).prev].pushBack(this_flow);
                }
            }

            // Add this flow in and then get the next path.
            if (DEBUG || PRINTPATH) {
                System.out.println("Adding " + this_flow);
            }
            flow += this_flow;
            System.out.println("flow = " + flow);
            nextpath = findAugmentingPath();
            if (nextpath != null && (DEBUG || PRINTPATH)) {

                System.out.println("Augmenting path ");
                for (int i = 0; i < nextpath.size(); i++) {
                    System.out.print(nextpath.get(i) + " ");
                }
                System.out.println();
            }

        }

        return flow;

    }

  
          
    // Returns true if there is a path 
    // from source 's' to sink 't' in residual  
    // graph. Also fills parent[] to store the path  
    private static boolean bfs(int[][] rGraph, int s, 
                                int t, int[] parent) { 
          
        // Create a visited array and mark  
        // all vertices as not visited      
        boolean[] visited = new boolean[rGraph.length]; 
          
        // Create a queue, enqueue source vertex 
        // and mark source vertex as visited      
        Queue<Integer> q = new LinkedList<Integer>(); 
        q.add(s); 
        visited[s] = true; 
        parent[s] = -1; 
          
        // Standard BFS Loop      
        while (!q.isEmpty()) { 
            int v = q.poll(); 
            for (int i = 0; i < rGraph.length; i++) { 
                if (rGraph[v][i] > 0 && !visited[i]) { 
                    q.offer(i); 
                    visited[i] = true; 
                    parent[i] = v; 
                } 
            } 
        } 
          
        // If we reached sink in BFS starting  
        // from source, then return true, else false      
        return (visited[t] == true); 
    } 
      
    // A DFS based function to find all reachable  
    // vertices from s. The function marks visited[i]  
    // as true if i is reachable from s. The initial  
    // values in visited[] must be false. We can also  
    // use BFS to find reachable vertices 
    private static void dfs(int[][] rGraph, int s, 
                                boolean[] visited) { 
        visited[s] = true; 
        for (int i = 0; i < rGraph.length; i++) { 
                if (rGraph[s][i] > 0 && !visited[i]) { 
                    dfs(rGraph, i, visited); 
                } 
        } 
    } 
  
    // Prints the minimum s-t cut 
    private static void minCut(int[][] graph, int s, int t) { 
        int u,v; 
          
        // Create a residual graph and fill the residual  
        // graph with given capacities in the original  
        // graph as residual capacities in residual graph 
        // rGraph[i][j] indicates residual capacity of edge i-j 
        int[][] rGraph = new int[graph.length][graph.length];  
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                rGraph[i][j] = graph[i][j]; 
            } 
        } 
  
        // This array is filled by BFS and to store path 
        int[] parent = new int[graph.length];  
          
        // Augment the flow while tere is path from source to sink      
        while (bfs(rGraph, s, t, parent)) { 
              
            // Find minimum residual capacity of the edhes  
            // along the path filled by BFS. Or we can say  
            // find the maximum flow through the path found. 
            int pathFlow = Integer.MAX_VALUE;          
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                pathFlow = Math.min(pathFlow, rGraph[u][v]); 
            } 
              
            // update residual capacities of the edges and  
            // reverse edges along the path 
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                rGraph[u][v] = rGraph[u][v] - pathFlow; 
                rGraph[v][u] = rGraph[v][u] + pathFlow; 
            } 
        } 
          
        // Flow is maximum now, find vertices reachable from s      
        boolean[] isVisited = new boolean[graph.length];      
        dfs(rGraph, s, isVisited); 
          
        // Print all edges that are from a reachable vertex to 
        // non-reachable vertex in the original graph    
        System.out.println("min-cut :- ");
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) { 
                    System.out.println("("+i + " - " + j+")"); 
                } 
            } 
       } 
    }
public static void main(String[] args) {

        int[][] graph = new int[7][7];
        graph[1][2] = 2;
        graph[1][3] = 7;
        graph[2][4] = 3;
        graph[2][5] = 4;
        graph[3][4] = 4;
        graph[3][5] = 2;
        graph[4][6] = 1;
        graph[5][6] = 5;

        Networkflow mine = new Networkflow(graph, 1, 6);
        

        int answer = mine.getMaxFlow();

        System.out.println("The max flow is = " + answer);
        minCut(graph,1,6);

    }

}
