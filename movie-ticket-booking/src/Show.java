import java.time.LocalDateTime;
import java.util.*;

public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final Map<String, Seat> seatMap;

    public Show(String id, Movie movie, Screen screen, LocalDateTime startTime) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.seatMap = new LinkedHashMap<>();
        for (Seat seat : screen.getSeats()) {
            seatMap.put(seat.getId(), new Seat(seat));
        }
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public LocalDateTime getStartTime() { return startTime; }

    public synchronized List<Seat> getAvailableSeats() {
        List<Seat> available = new ArrayList<>();
        for (Seat seat : seatMap.values()) {
            if (seat.isAvailable()) available.add(seat);
        }
        return available;
    }

    public synchronized void lockSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            Seat seat = seatMap.get(seatId);
            if (seat == null) throw new IllegalArgumentException("Seat " + seatId + " does not exist in this show");
            if (!seat.isAvailable()) throw new IllegalStateException("Seat " + seatId + " is not available");
        }
        for (String seatId : seatIds) {
            seatMap.get(seatId).lock();
        }
    }

    public synchronized void confirmSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            seatMap.get(seatId).book();
        }
    }

    public synchronized void releaseSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            seatMap.get(seatId).release();
        }
    }

    public Seat getSeat(String seatId) {
        return seatMap.get(seatId);
    }

    @Override
    public String toString() {
        return movie.getTitle() + " @ " + startTime + " [Show: " + id + "]";
    }
}
