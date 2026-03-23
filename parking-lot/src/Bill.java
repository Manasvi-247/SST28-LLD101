import java.time.LocalDateTime;
import java.time.Duration;

public class Bill {
    public final ParkingTicket ticket;
    public final LocalDateTime exitTime;
    public final long hours;
    public final double amount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime, PricingStrategy pricing) {
        this.ticket = ticket;
        this.exitTime = exitTime;
        long mins = Duration.between(ticket.entryTime, exitTime).toMinutes();
        this.hours = (mins + 59) / 60;
        this.amount = pricing.calculateFee(ticket, mins);
    }

    @Override
    public String toString() {
        return "Bill[" + ticket.ticketId + " | " + hours + "h | Rs." + String.format("%.0f", amount) + "]";
    }
}
