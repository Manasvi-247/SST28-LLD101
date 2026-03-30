import java.util.*;

public class Screen {
    private final String id;
    private final List<Seat> seats;
    private final List<Show> shows;

    public Screen(String id, List<Seat> seats) {
        this.id = id;
        this.seats = new ArrayList<>(seats);
        this.shows = new ArrayList<>();
    }

    public String getId() { return id; }
    public List<Seat> getSeats() { return Collections.unmodifiableList(seats); }
    public List<Show> getShows() { return Collections.unmodifiableList(shows); }

    void addShow(Show show) {
        shows.add(show);
    }

    @Override
    public String toString() {
        return "Screen " + id + " (" + seats.size() + " seats, " + shows.size() + " shows)";
    }
}
