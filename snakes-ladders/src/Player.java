public class Player {
    public final String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public int getPosition() { return position; }
    public void moveTo(int pos) { this.position = pos; }

    @Override
    public String toString() {
        return name + "@" + position;
    }
}
