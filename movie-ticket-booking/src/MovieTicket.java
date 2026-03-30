import java.util.*;

public class MovieTicket {
    private static int counter = 0;

    private final String ticketId;
    private final Show show;
    private final List<Seat> bookedSeats;
    private final double totalPrice;
    private BookingStatus status;

    public MovieTicket(Show show, List<Seat> bookedSeats, double totalPrice) {
        this.ticketId = "MTKT-" + (++counter);
        this.show = show;
        this.bookedSeats = Collections.unmodifiableList(new ArrayList<>(bookedSeats));
        this.totalPrice = totalPrice;
        this.status = BookingStatus.CONFIRMED;
    }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<Seat> getBookedSeats() { return bookedSeats; }
    public double getTotalPrice() { return totalPrice; }
    public BookingStatus getStatus() { return status; }

    public void cancel() {
        if (status == BookingStatus.CANCELLED) throw new IllegalStateException("Ticket already cancelled");
        status = BookingStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return ticketId + " | " + show.getMovie().getTitle() + " | Seats: " + bookedSeats.size()
                + " | Rs." + String.format("%.0f", totalPrice) + " | " + status;
    }
}
