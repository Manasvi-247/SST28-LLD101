public class Floor {
    private final int floorNumber;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() { return floorNumber; }

    @Override
    public String toString() {
        return "Floor " + floorNumber;
    }
}
