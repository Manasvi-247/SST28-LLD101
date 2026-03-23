public class Snake {
    public final int head;
    public final int tail;

    public Snake(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public String toString() {
        return "Snake{" + head + "->" + tail + "}";
    }
}
