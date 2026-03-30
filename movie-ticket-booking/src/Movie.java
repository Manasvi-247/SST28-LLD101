public class Movie {
    private final String id;
    private final String title;
    private final int durationMinutes;

    public Movie(String id, String title, int durationMinutes) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be blank");
        if (durationMinutes <= 0) throw new IllegalArgumentException("Duration must be positive");
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }

    @Override
    public String toString() {
        return title + " (" + durationMinutes + " min)";
    }
}
