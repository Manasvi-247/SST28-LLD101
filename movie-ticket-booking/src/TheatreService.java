public class TheatreService {
    private final BookingService bookingService;

    public TheatreService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public synchronized void addShow(Theatre theatre, String screenId, Show show) {
        for (Screen screen : theatre.getScreens()) {
            if (screen.getId().equals(screenId)) {
                screen.addShow(show);
                bookingService.registerShow(show);
                return;
            }
        }
        throw new IllegalArgumentException("Screen " + screenId + " not found in theatre " + theatre.getName());
    }
}
