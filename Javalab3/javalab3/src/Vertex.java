public class Vertex {

    private int color;
    private int id;
    boolean isVisited = false;

    protected int getId() {
        return id;
    }

    public Vertex(int id) {
        setColor(0);
        this.id = id;
    }

    public Vertex() {
        setColor(0);
        id = 0;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        return id * 31;
    }

    @Override
    public boolean equals(Object obj) {
        Vertex vertex = (Vertex) obj;

        return id == vertex.id;
    }

}