/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstracities;

/**
 *
 * @author s
 */
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DijkstraCities {

    /* Dijkstra Algorithm
 * 
 *
     */
    public static void computePaths(Node source) {
        source.shortestDistance = 0;

        //implement a priority queue
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(source);

        while (!queue.isEmpty()) {
            // This method returns the element at the front of the container or the head of the Queue. It returns null when the Queue is empty.
            Node u = queue.poll();

            /*visit the adjacencies, starting from 
			the nearest node(smallest shortestDistance)*/
            for (Edge e : u.adjacencies) {

                Node v = e.target;
                double weight = e.weight;

                //relax(u,v,weight)
                double distanceFromU = u.shortestDistance + weight;
                if (distanceFromU < v.shortestDistance) {

                    /*remove v from queue for updating 
					the shortestDistance value*/
                    queue.remove(v);
                    v.shortestDistance = distanceFromU;
                    v.parent = u;
                    queue.add(v);

                }
            }
        }
    }

    public static List<Node> getShortestPathTo(Node target) {

        //trace path from target to source
        List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        //reverse the order such that it will be from source to target
        Collections.reverse(path);

        return path;
    }

    public static void main(String[] args) {

        //initialize the graph base on the Romania map
        Node n1 = new Node("Jeddah");
        Node n2 = new Node("Makkah");
        Node n3 = new Node("Madinah");
        Node n4 = new Node("Riyadh");
        Node n5 = new Node("Dammam");
        Node n6 = new Node("Taif");
        Node n7 = new Node("Abha");
        Node n8 = new Node("Tabuk");
        Node n9 = new Node("Qasim");
        Node n10 = new Node("Hail");
        Node n11 = new Node("Jizan");
        Node n12 = new Node("Najran");

        //initialize the edges
        n1.adjacencies = new Edge[]{
            new Edge(n2, 79),
            new Edge(n3, 420),
            new Edge(n4, 949),
            new Edge(n5, 1343),
            new Edge(n6, 167),
            new Edge(n7, 625),
            new Edge(n8, 1024),
            new Edge(n9, 863),
            new Edge(n10, 777),
            new Edge(n11, 710),
            new Edge(n12, 905)
        };

        n2.adjacencies = new Edge[]{
            new Edge(n1, 79),
            new Edge(n3, 358),
            new Edge(n4, 870),
            new Edge(n5, 1265),
            new Edge(n6, 88),
            new Edge(n7, 627),
            new Edge(n8, 1037),
            new Edge(n9, 876),
            new Edge(n10, 790),
            new Edge(n11, 685),
            new Edge(n12, 912)
        };

        n3.adjacencies = new Edge[]{
            new Edge(n1, 420),
            new Edge(n2, 358),
            new Edge(n4, 848),
            new Edge(n5, 1343),
            new Edge(n6, 446),
            new Edge(n7, 985),
            new Edge(n8, 679),
            new Edge(n9, 518),
            new Edge(n10, 432),
            new Edge(n11, 1043),
            new Edge(n12, 1270)
        };

        n4.adjacencies = new Edge[]{
            new Edge(n1, 949),
            new Edge(n2, 870),
            new Edge(n3, 848),
            new Edge(n5, 395),
            new Edge(n6, 782),
            new Edge(n7, 1064),
            new Edge(n8, 1304),
            new Edge(n9, 330),
            new Edge(n10, 640),
            new Edge(n11, 1272),
            new Edge(n12, 950)
        };

        n5.adjacencies = new Edge[]{
            new Edge(n1, 1343),
            new Edge(n2, 1265),
            new Edge(n3, 1343),
            new Edge(n4, 395),
            new Edge(n6, 1177),
            new Edge(n7, 1459),
            new Edge(n8, 1729),
            new Edge(n9, 725),
            new Edge(n10, 1035),
            new Edge(n11, 1667),
            new Edge(n12, 1345)
        };

        n6.adjacencies = new Edge[]{
            new Edge(n1, 167),
            new Edge(n2, 88),
            new Edge(n3, 446),
            new Edge(n4, 782),
            new Edge(n5, 1177),
            new Edge(n7, 561),
            new Edge(n8, 1204),
            new Edge(n9, 936),
            new Edge(n10,957),
            new Edge(n11, 763),
            new Edge(n12, 864)
        };

        n7.adjacencies = new Edge[]{
            new Edge(n1, 625),
            new Edge(n2, 627),
            new Edge(n3, 985),
            new Edge(n4, 1064),
            new Edge(n5, 1495),
            new Edge(n6, 561),
            new Edge(n8, 1649),
            new Edge(n9, 1488),
            new Edge(n10,1402),
            new Edge(n11, 202),
            new Edge(n12, 280)
        };

        n8.adjacencies = new Edge[]{
            new Edge(n1, 1024),
            new Edge(n2, 1037),
            new Edge(n3, 679),
            new Edge(n4, 1304),
            new Edge(n5, 1729),
            new Edge(n6, 1204),
            new Edge(n7, 1649),
            new Edge(n9, 974),
            new Edge(n10,664),
            new Edge(n11, 1722),
            new Edge(n12, 1929)
        };

        n9.adjacencies = new Edge[]{
            new Edge(n1, 863),
            new Edge(n2, 876),
            new Edge(n3, 518),
            new Edge(n4, 330),
            new Edge(n5, 725),
            new Edge(n6, 936),
            new Edge(n7, 1488),
            new Edge(n8, 974),
            new Edge(n10,974),
            new Edge(n11, 1561),
            new Edge(n12, 1280)
        };

        n10.adjacencies = new Edge[]{
            new Edge(n1, 777),
            new Edge(n2, 790),
            new Edge(n3, 432),
            new Edge(n4, 640),
            new Edge(n5, 1035),
            new Edge(n6, 957),
            new Edge(n7, 1402),
            new Edge(n8, 664),
            new Edge(n9,310),
            new Edge(n11, 1475),
            new Edge(n12, 1590)
        };

        n11.adjacencies = new Edge[]{
            new Edge(n1, 710),
            new Edge(n2, 685),
            new Edge(n3, 1043),
            new Edge(n4, 1272),
            new Edge(n5, 1667),
            new Edge(n6, 763),
            new Edge(n7, 202),
            new Edge(n8, 1722),
            new Edge(n9,1561),
            new Edge(n10, 1475),
            new Edge(n12, 482)
        };

        n12.adjacencies = new Edge[]{
            new Edge(n1, 905),
            new Edge(n2, 912),
            new Edge(n3, 1270),
            new Edge(n4, 950),
            new Edge(n5, 1345),
            new Edge(n6, 864),
            new Edge(n7, 280),
            new Edge(n8, 1929),
            new Edge(n9,1280),
            new Edge(n10, 1590),
            new Edge(n11, 482)
        };


        Node[] nodes = {n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12};

        //compute paths
        computePaths(n1);

        //print shortest paths
                System.out.println("The shortest path from Jeddah to every other city: \n");
		for(Node n: nodes){
			System.out.println("Distance from "+ n1 +" to "+ n +" "+(int)n.shortestDistance);
    		List<Node> path = getShortestPathTo(n);
    		//System.out.println("Path: " + path);
		}
        //List<Node> path = getShortestPathTo(n3);
        //System.out.println("Path: " + path);

    }

}

//define Node
class Node implements Comparable<Node> {

    public final String value;
    public Edge[] adjacencies;
    public double shortestDistance = Double.POSITIVE_INFINITY;
    public Node parent;

    public Node(String val) {
        value = val;
    }

    public String toString() {
        return value;
    }

    public int compareTo(Node other) {
        return Double.compare(shortestDistance, other.shortestDistance);
    }

}

//define Edge
class Edge {

    public final Node target;
    public final double weight;

    public Edge(Node targetNode, double weightVal) {
        target = targetNode;
        weight = weightVal;
    }
}
