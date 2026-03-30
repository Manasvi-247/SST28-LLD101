public class Seat {
    private final String id;
    private final SeatType type;
    private final int row;
    private final int number;
    private SeatStatus status;

    public Seat(String id, SeatType type, int row, int number) {
        this.id = id;
        this.type = type;
        this.row = row;
        this.number = number;
        this.status = SeatStatus.AVAILABLE;
    }

    public Seat(Seat other) {
        this.id = other.id;
        this.type = other.type;
        this.row = other.row;
        this.number = other.number;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getId() { return id; }
    public SeatType getType() { return type; }
    public int getRow() { return row; }
    public int getNumber() { return number; }
    public SeatStatus getStatus() { return status; }
    public boolean isAvailable() { return status == SeatStatus.AVAILABLE; }

    void lock() {
        if (status != SeatStatus.AVAILABLE) throw new IllegalStateException("Seat " + id + " is not available");
        status = SeatStatus.LOCKED;
    }

    void book() {
        if (status != SeatStatus.LOCKED) throw new IllegalStateException("Seat " + id + " is not locked");
        status = SeatStatus.BOOKED;
    }

    void release() {
        status = SeatStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return id + " [" + type + "] - " + status;
    }
}
