import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Graph {

    private Map<Vertex, List<Vertex>> vertices;
    private int countOfVertex;
    private List<Integer> usedColors = new ArrayList<>();

    protected List<Integer> getUsedColors() {
        return usedColors;
    }

    protected int getCountOfVertex() {
        return countOfVertex;
    }

    public Graph() {
        countOfVertex = 0;
        vertices = new HashMap<>();
    }

    public Graph clone() {
        Graph newGraph = new Graph();

        for (Integer i : usedColors) {
            newGraph.usedColors.add(i);
        }
        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            newGraph.vertices.put(entry.getKey(), entry.getValue());
        }
        newGraph.countOfVertex = countOfVertex;
        return newGraph;
    }

    public void fillGraph() {
        for (int i = 0; i < 150; i++) {
            addVertex();
        }
        for (int i = 0; i < 150; i++) {

            int rand = (int) ((Math.random() * 30) + 1); // [0;20) -> [1;21)
            List<Integer> listOfIds = new ArrayList<>();
            for (int j = 0; j < rand; j++) {

                int id = (int) (Math.random() * 150); // [0;200)
                while (id == i || listOfIds.contains(id)) {
                    id = (int) (Math.random() * 150); // [0;200)
                }
                listOfIds.add(id);
                addEdge(i, id);

            }
        }
    }

    public void addVertex() {
        vertices.putIfAbsent(new Vertex(countOfVertex), new ArrayList<>());
        countOfVertex++;
    }

    public void removeVertex(int id) {
        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            if (entry.getKey().getId() == id) {
                Vertex key = entry.getKey();
                vertices.remove(key);
                countOfVertex--;
                return;
            }
        }
    }

    public void addEdge(int id1, int id2) {

        Vertex vertex1 = null;
        Vertex vertex2 = null;

        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            if (entry.getKey().getId() == id1) {
                vertex1 = entry.getKey();
            }
            if (entry.getKey().getId() == id2) {
                vertex2 = entry.getKey();
            }

        }
        if (vertex1 == null || vertex2 == null) {
            System.out.println("Íåò òàêèõ óçåëêîâ");
            return;
        } else {
            vertices.get(vertex1).add(vertex2);
            vertices.get(vertex2).add(vertex1);
        }

    }

    public void removeEdge(int id1, int id2) {
        Vertex vertex1 = null;
        Vertex vertex2 = null;

        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            if (entry.getKey().getId() == id1) {
                vertex1 = entry.getKey();
            }
            if (entry.getKey().getId() == id2) {
                vertex2 = entry.getKey();
            }

        }
        if (vertex1 == null || vertex2 == null) {
            System.out.println("Íåò òàêèõ óçåëêîâ");
            return;
        } else {
            vertices.get(vertex1).remove(vertex2);
            vertices.get(vertex2).remove(vertex1);
        }

    }

    List<Vertex> getNearestVertices(int id) {
        Vertex key = null;
        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            if (entry.getKey().getId() == id) {
                key = entry.getKey();
                break;
            }
        }
        return vertices.get(key);
    }

    Set<Integer> breadthFirstTraversal(int idRoot) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(idRoot);
        visited.add(idRoot);
        while (!queue.isEmpty()) {
            int idVertex = queue.poll();
            Vertex vertex = new Vertex(idVertex);

            for (Vertex v : vertices.get(vertex)) {

                if (!visited.contains(v.getId())) {
                    visited.add(v.getId());
                    queue.add(v.getId());
                }
            }
        }
        return visited;
    }

    private int addUsedColor() {
        usedColors.add(usedColors.size() + 1);
        return usedColors.size() + 1;
    }

    private boolean isCorrect() {
        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            if (entry.getKey().getColor() == 0) {
                return false;
            }
        }

        return true;
    }

    int abcAlgo(int scoutBees, int foragerBees) {

        int tempForBees = foragerBees;
        int iteration = 0;

        while (!isCorrect()) {
            for (int i = 0; i < scoutBees; i++) {
                iteration++;
                Vertex vertex = getRandomVertex();
                if (vertex.isVisited) {
                    vertex = getRandomVertex();
                }
                vertex.isVisited = true;
                List<Vertex> getNearestVertices = vertices.get(vertex);

                for (Vertex vert : getNearestVertices) {
                    if (tempForBees == 0) {
                        break;
                    }
                    if (vert.getColor() != 0) {
                        continue;
                    }
                    tryToColorVertex(vert);
                    tempForBees--;
                }

                tryToColorVertex(vertex);

            }

            tempForBees = foragerBees;
        }
        return usedColors.size();
    }

    private void tryToColorVertex(Vertex vertex) {
        List<Vertex> nearestVertices = vertices.get(vertex);
        Set<Integer> usedColorsVertices = new TreeSet<>();

        for (Vertex vertexy : nearestVertices) {
            usedColorsVertices.add(vertexy.getColor()); // çàïèñûâàåì âñå öâåòà âåðøèí

        }

        for (Integer i : usedColors) {
            boolean hasColor = false;
            for (Integer j : usedColorsVertices) {
                if (j == 0) {
                    continue;
                }
                if (i == j) {
                    hasColor = true;
                    break;
                }

            }
            if (!hasColor) {
                vertex.setColor(i);
                return;
            }

        }
        vertex.setColor(addUsedColor());

    }

    private Vertex getRandomVertex() {
        int rand = (int) (Math.random() * 150);
        int counter = 0;
        for (Entry<Vertex, List<Vertex>> entry : vertices.entrySet()) {
            if (rand == counter) {
                return entry.getKey();
            }

            counter++;
        }
        return null;

    }

}
// public List<Graph> BeesAlgo(int iterations, Graph graph, int scoutBees , int
// foragerBees)
// {
//
// List<Graph> newGraphs = new ArrayList<Graph>();
// for (int i = 0; i < iterations; i++)
// {
//
//
// for (int j = 0; j < scoutBees; j++)
// {
// Graph graphClone;
//
//
//
// graphClone = graph.clone();
//
//
// for (int k = 0; k < graphClone.countOfVertexes; k++)
// {
//
// Vertex vertex = new Vertex(k);
//
// int count = foragerBees / scoutBees;
// for (int l = 0; l < count && l < graphClone.vertices.get(vertex).size(); l++)
// {
// //Try to swap each vertex not more times than foragerbees
// int tempColor = vertex.getColor();
// int tempColor2 = graphClone.vertices.get(vertex).get(l).getColor();
// //Swap with vertex.connections[i]
// for (int m = 0; m < graphClone.vertices.get(vertex).size(); m++)
// {
// if (graphClone.vertices.get(vertex).get(l).getColor() == tempColor2)
// {
// //leave circle
// l = graphClone.vertices.get(vertex).size() + 1;
// break;
// }
// }
// graphs = graphs.OrderBy(graph => graph.UsedColors.Count).ToList();
// if (l == vertex.connections.Count + 1)
// break;
// List<int> otherUsedColors = new List<int>();
//
// //Check other vertex connections
// for (int m = 0; m < vertex.connections[l].connections.Count; m++)
// {
// otherUsedColors.Add((int)vertex.connections[l].connections[m].Color);
// if (vertex.connections[l].connections[m].Color == tempColor)
// {
// //leave circle
// l = vertex.connections.Count + 1;
// }
// }
//
// otherUsedColors = otherUsedColors.Distinct().ToList();
// if (l == vertex.connections.Count + 1 || otherUsedColors.Count >=
// graphClone.UsedColors.Count-1)
// break;
//
// List<Integer> s = graphClone.UsedColors.Except(otherUsedColors).ToList();
//
// for (int m = 0; m < s.Count; m++)
// {
// if (s[m] != tempColor2)
// {
// vertex.connections[l].Color = s[m];
// graphClone.UsedColors.Remove(tempColor);
// vertex.Color = tempColor2;
// break;
// }
// }
//
// }
//
// }
// graphs.Insert(0,graphClone);
//
// }
// }
//
// return graphs;
// }