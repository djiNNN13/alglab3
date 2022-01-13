import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Graph graph = new Graph();
        graph.fillGraph();
        System.out.println(graph.getCountOfVertex());
        Set<Integer> travel = graph.breadthFirstTraversal(0);

        for (Integer i : travel) {
            System.out.print(i + " ");
        }
        System.out.println("");
        int chromeStep = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {

            int tempStep = graph.abcAlgo(3, 22);
            if (tempStep < chromeStep) {
                chromeStep = tempStep;
            }
        }
        System.out.println("USED COLORS : " + chromeStep);

    }
}