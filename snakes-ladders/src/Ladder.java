public class Ladder {
    public final int bottom;
    public final int top;

    public Ladder(int bottom, int top) {
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    public String toString() {
        return "Ladder{" + bottom + "->" + top + "}";
    }
}
