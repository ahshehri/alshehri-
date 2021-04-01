package pkg324phase2;

import java.util.Scanner;

public class phase2 {

    // make the graph static variable to can acsses the graph in any part of the code and can aplay any algorithim on it 
    static Graph graph;

    public static void main(String[] args) {

        printMniue();

    }

    public static void printMniue() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println(" ");
            System.out.println("......................................................");
            System.out.println("                Wellcom to graph system  ");
            System.out.println("......................................................");
            System.out.println(" ");
            System.out.println("choose the number of nodes and edges you need to implemnt ");
            System.out.println("1. 1000 nodes and 10000 edges ");
            System.out.println("2. 1000 nodes and 15000 edges ");
            System.out.println("3. 1000 nodes and 25000 edges ");
            System.out.println("4. 5000 nodes and 15000 edges ");
            System.out.println("5. 5000 nodes and 25000 edges ");
            System.out.println("6. 10000 nodes and 15000 edges ");
            System.out.println("7. 10000 nodes and 25000 edges ");
            System.out.println("8. 20000 nodes and 200000 edges ");
            System.out.println("9. 20000 nodes and 20000 edges ");
            System.out.println("10. 50000 nodes and 1,000,000 edges ");
            System.out.println("11. Exit ");
            System.out.print("your choice is: ");
            int user_choice = input.nextInt();
            switch (user_choice) {

                case 1:
                    // if user choose 1000 nodes and 10000 edges
                    makeGraph(1000, 10000);
                    choicesAlgorithim();
                    break;

                case 2:
                    // if user choose 1000 nodes and 15000 edges
                    makeGraph(1000, 15000);
                    choicesAlgorithim();
                    break;

                case 3:

                    // if user choose 1000 nodes and 25000 edges
                    makeGraph(1000, 25000);
                    choicesAlgorithim();
                    break;
                case 4:

                    // if user choose 5000 nodes and 15000 edges
                    makeGraph(5000, 15000);
                    choicesAlgorithim();
                    break;

                case 5:
                    // if user choose 5000 nodes and 25000 edges
                    makeGraph(5000, 25000);
                    choicesAlgorithim();
                    break;

                case 6:
                    // if user choose 10000 nodes and 15000 edges
                    makeGraph(10000, 15000);
                    choicesAlgorithim();
                    break;
                case 7:
                    // if user choose 10000 nodes and 25000 edges
                    makeGraph(10000, 25000);
                    choicesAlgorithim();
                    break;

                case 8:
                    // if user choose 20000 nodes and 200000 edges
                    makeGraph(20000, 200000);
                    choicesAlgorithim();
                    break;

                case 9:
                    // if user choose 20000 nodes and 200000 edges
                    makeGraph(20000, 20000);
                    choicesAlgorithim();
                    break;

                case 10:
                    // if user choose 50000 nodes and 1,000,000 edges
                    makeGraph(50000, 1000000);
                    choicesAlgorithim();

                    break;
                case 11:

                    System.out.println("......................................................");
                    System.out.println("                thank you ");
                    System.exit(0);

                default:
                    System.exit(0);
            }

        }

    }

    public static void makeGraph(int vertices, int edges) {
        // method make ghraph to implement the gragh with any number of vertix and edges randomly 
        graph = new Graph(vertices);
        int i = 0;
        int source;
        int destination;
        int weight;
        while (i < edges) {
            source = (int) (Math.random() * (vertices));
            destination = (int) (Math.random() * (vertices));
            weight = (int) (1 + (Math.random() * 10));
            graph.addEgde(source, destination, weight);
            i++;
        }

    }

    public static void choicesAlgorithim() {
        Scanner input = new Scanner(System.in);
        // user will chose algoritim to implement graph 
        while (true) {
            System.out.println("......................................................");
            System.out.println("chose one of these algorithim to implement your gragh or create new graph ");
            System.out.println("1.Kruskal");
            System.out.println("2.priority-queue (pq) based Prim ");
            System.out.println("3.Prim’s algorithm using min-heap ");
            System.out.println("4.create new graph ");
            int choosenAlgorithim = input.nextInt();
            // print the ghraph 

            switch (choosenAlgorithim) {

                case 1:
                    //if user select kruskal

                    graph.kruskalMST();
                    break;

                case 2:

                    //if user select priority-queue (pq) based Prim 
                    graph.primMST();
                    break;

                case 3:
                    // if user select Prim’s algorithm using min-heap

                    graph.HeapMST();
                    break;
                case 4:
                    // if user will create new graph
                    // it will be a new ghraph 
                    printMniue();
                    break;
                default:
                    System.exit(0);
            }

        }
    }
}
