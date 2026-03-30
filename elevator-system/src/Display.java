public class Display {
    private int currentFloor;
    private Direction direction;

    public Display(int initialFloor) {
        this.currentFloor = initialFloor;
        this.direction = null;
    }

    public synchronized void update(int floor, Direction direction) {
        this.currentFloor = floor;
        this.direction = direction;
    }

    public synchronized String show() {
        String dir = direction == null ? "IDLE" : direction.toString();
        return "Floor " + currentFloor + " | " + dir;
    }

    @Override
    public String toString() {
        return show();
    }
}
