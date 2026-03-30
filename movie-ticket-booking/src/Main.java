import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var bookingService = new BookingService(new FullRefundStrategy());
        var theatreService = new TheatreService(bookingService);

        var movie1 = new Movie("M1", "Inception", 148);
        var movie2 = new Movie("M2", "Interstellar", 169);

        List<Seat> screen1Seats = List.of(
                new Seat("S1", SeatType.SILVER, 1, 1),
                new Seat("S2", SeatType.SILVER, 1, 2),
                new Seat("S3", SeatType.GOLD, 2, 1),
                new Seat("S4", SeatType.GOLD, 2, 2),
                new Seat("S5", SeatType.PLATINUM, 3, 1)
        );

        List<Seat> screen2Seats = List.of(
                new Seat("A1", SeatType.SILVER, 1, 1),
                new Seat("A2", SeatType.GOLD, 1, 2),
                new Seat("A3", SeatType.PLATINUM, 1, 3)
        );

        var screen1 = new Screen("SCR1", screen1Seats);
        var screen2 = new Screen("SCR2", screen2Seats);

        var pvr = new Theatre("T1", "PVR Cinemas", City.BANGALORE, List.of(screen1, screen2));
        var inox = new Theatre("T2", "INOX", City.MUMBAI, List.of(
                new Screen("SCR3", List.of(
                        new Seat("X1", SeatType.GOLD, 1, 1),
                        new Seat("X2", SeatType.PLATINUM, 1, 2)
                ))
        ));

        bookingService.registerTheatre(pvr);
        bookingService.registerTheatre(inox);

        var show1 = new Show("SH1", movie1, screen1, LocalDateTime.of(2026, 3, 30, 18, 0));
        var show2 = new Show("SH2", movie2, screen2, LocalDateTime.of(2026, 3, 30, 20, 0));
        var show3 = new Show("SH3", movie1, inox.getScreens().get(0), LocalDateTime.of(2026, 3, 30, 19, 0));

        theatreService.addShow(pvr, "SCR1", show1);
        theatreService.addShow(pvr, "SCR2", show2);
        theatreService.addShow(inox, "SCR3", show3);

        System.out.println("=== Theatres in Bangalore ===");
        for (var t : bookingService.showTheatres(City.BANGALORE)) {
            System.out.println("  " + t);
        }

        System.out.println("\n=== Movies in Bangalore ===");
        for (var m : bookingService.showMovies(City.BANGALORE)) {
            System.out.println("  " + m);
        }

        System.out.println("\n=== Available Seats for Show: " + show1 + " ===");
        for (var s : show1.getAvailableSeats()) {
            System.out.println("  " + s);
        }

        System.out.println("\n=== Booking 2 tickets ===");
        var ticket = bookingService.bookTickets("SH1", List.of("S1", "S3"));
        System.out.println("  Booked: " + ticket);

        System.out.println("\n=== Available Seats After Booking ===");
        for (var s : show1.getAvailableSeats()) {
            System.out.println("  " + s);
        }

        System.out.println("\n=== Concurrent Booking Test (2 threads, same seats) ===");
        var latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable bookingTask = () -> {
            try {
                latch.await();
                var t = bookingService.bookTickets("SH1", List.of("S2", "S4"));
                System.out.println("  " + Thread.currentThread().getName() + " SUCCESS: " + t);
            } catch (Exception e) {
                System.out.println("  " + Thread.currentThread().getName() + " FAILED: " + e.getMessage());
            }
        };

        executor.submit(bookingTask);
        executor.submit(bookingTask);
        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\n=== Cancelling Ticket: " + ticket.getTicketId() + " ===");
        double refund = bookingService.cancelTicket(ticket.getTicketId());
        System.out.println("  Refund: Rs." + String.format("%.0f", refund));
        System.out.println("  Ticket after cancel: " + ticket);

        System.out.println("\n=== Available Seats After Cancellation ===");
        for (var s : show1.getAvailableSeats()) {
            System.out.println("  " + s);
        }
    }
}
