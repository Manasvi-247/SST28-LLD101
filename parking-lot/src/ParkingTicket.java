import java.time.LocalDateTime;

public class ParkingTicket {
    private static int counter = 0;

    public final String ticketId;
    public final Vehicle vehicle;
    public final ParkingSlot allocatedSlot;
    public final LocalDateTime entryTime;

    public ParkingTicket(Vehicle vehicle, ParkingSlot slot, LocalDateTime entryTime) {
        this.ticketId = "TKT-" + (++counter);
        this.vehicle = vehicle;
        this.allocatedSlot = slot;
        this.entryTime = entryTime;
    }

    @Override
    public String toString() {
        return ticketId + " | " + vehicle + " | " + allocatedSlot + " | entry=" + entryTime;
    }
}
