public class ParkingSlot {
    public final int id;
    public final SlotType type;
    public final int floor;
    public final int position;
    private boolean occupied;

    public ParkingSlot(int id, SlotType type, int floor, int position) {
        this.id = id;
        this.type = type;
        this.floor = floor;
        this.position = position;
        this.occupied = false;
    }

    public boolean isFree() { return !occupied; }
    public void markOccupied() { occupied = true; }
    public void markFree() { occupied = false; }

    public int distanceFrom(int gatePos) {
        return Math.abs(floor * 100 + position - gatePos);
    }

    @Override
    public String toString() {
        return "Slot-" + id + "(" + type + ",F" + floor + ")";
    }
}
