public class ParkingSlot {
    private final String id;
    private final SlotType type;
    private final int floor;
    private final double x;
    private final double y;
    private boolean occupied;

    public ParkingSlot(String id, SlotType type, int floor, double x, double y) {
        this.id = id;
        this.type = type;
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.occupied = false;
    }

    public String getId() { return id; }
    public SlotType getType() { return type; }
    public int getFloor() { return floor; }
    public boolean isFree() { return !occupied; }
    public void markOccupied() { occupied = true; }
    public void markFree() { occupied = false; }

    public double distanceTo(Gate gate) {
        double dx = this.x - gate.getX();
        double dy = this.y - gate.getY();
        double dz = (this.floor - gate.getFloor()) * 10.0;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public String toString() {
        return "Slot-" + id + "(" + type + ",F" + floor + ")";
    }
}
