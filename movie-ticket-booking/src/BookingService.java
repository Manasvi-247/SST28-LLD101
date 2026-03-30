import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookingService {
    private final Map<String, Theatre> theatres = new ConcurrentHashMap<>();
    private final Map<String, Show> showIndex = new ConcurrentHashMap<>();
    private final Map<String, MovieTicket> tickets = new ConcurrentHashMap<>();
    private final RefundStrategy refundStrategy;

    private static final Map<SeatType, Double> SEAT_PRICES = Map.of(
            SeatType.SILVER, 150.0,
            SeatType.GOLD, 250.0,
            SeatType.PLATINUM, 400.0
    );

    public BookingService(RefundStrategy refundStrategy) {
        this.refundStrategy = refundStrategy;
    }

    public void registerTheatre(Theatre theatre) {
        theatres.put(theatre.getId(), theatre);
    }

    public void registerShow(Show show) {
        showIndex.put(show.getId(), show);
    }

    public List<Theatre> showTheatres(City city) {
        List<Theatre> result = new ArrayList<>();
        for (Theatre theatre : theatres.values()) {
            if (theatre.getCity() == city) result.add(theatre);
        }
        return result;
    }

    public List<Movie> showMovies(City city) {
        Set<String> seen = new HashSet<>();
        List<Movie> result = new ArrayList<>();
        for (Theatre theatre : theatres.values()) {
            if (theatre.getCity() != city) continue;
            for (Screen screen : theatre.getScreens()) {
                for (Show show : screen.getShows()) {
                    if (seen.add(show.getMovie().getId())) {
                        result.add(show.getMovie());
                    }
                }
            }
        }
        return result;
    }

    public MovieTicket bookTickets(String showId, List<String> seatIds) {
        Show show = showIndex.get(showId);
        if (show == null) throw new IllegalArgumentException("Show not found: " + showId);

        show.lockSeats(seatIds);

        double totalPrice = 0;
        List<Seat> seats = new ArrayList<>();
        for (String seatId : seatIds) {
            Seat seat = show.getSeat(seatId);
            seats.add(seat);
            totalPrice += SEAT_PRICES.getOrDefault(seat.getType(), 150.0);
        }

        show.confirmSeats(seatIds);

        var ticket = new MovieTicket(show, seats, totalPrice);
        tickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public double cancelTicket(String ticketId) {
        MovieTicket ticket = tickets.get(ticketId);
        if (ticket == null) throw new IllegalArgumentException("Ticket not found: " + ticketId);

        ticket.cancel();

        List<String> seatIds = new ArrayList<>();
        for (Seat seat : ticket.getBookedSeats()) {
            seatIds.add(seat.getId());
        }
        ticket.getShow().releaseSeats(seatIds);

        return refundStrategy.calculateRefund(ticket);
    }
}
