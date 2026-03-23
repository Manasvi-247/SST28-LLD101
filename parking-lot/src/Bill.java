import java.time.LocalDateTime;
import java.time.Duration;

public class Bill {
    public final ParkingTicket ticket;
    public final LocalDateTime exitTime;
    public final long hours;
    public final int amount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime) {
        this.ticket = ticket;
        this.exitTime = exitTime;
        long mins = Duration.between(ticket.entryTime, exitTime).toMinutes();
        this.hours = (mins + 59) / 60;
        this.amount = (int) (hours * ticket.allocatedSlot.type.getRatePerHour());
    }

    @Override
    public String toString() {
        return "Bill[" + ticket.ticketId + " | " + hours + "h | Rs." + amount + "]";
    }
}
