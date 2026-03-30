# Movie Ticket Booking System

## How to run
```
cd src
javac *.java
java Main
```

## APIs

- `bookTickets(showId, seatIds)` → books seats atomically, returns `MovieTicket`
- `showTheatres(city)` → lists all theatres in a city
- `showMovies(city)` → lists all movies playing across theatres in a city
- `cancelTicket(ticketId)` → cancels booking, releases seats, returns refund amount

## Classes

- **City** — enum for supported cities
- **SeatType** — enum: SILVER, GOLD, PLATINUM with different pricing
- **SeatStatus** — enum: AVAILABLE, BOOKED, LOCKED
- **BookingStatus** — enum: CONFIRMED, CANCELLED
- **Movie** — immutable model with id, title, duration
- **Seat** — tracks seat position, type, and mutable status with lock/book/release transitions
- **Screen** — holds physical seats and a list of shows
- **Show** — synchronization monitor; owns independent seat copies per show, all seat mutations go through synchronized methods
- **Theatre** — groups screens under a city
- **MovieTicket** — booking record with auto-generated id, linked seats, price, and status
- **RefundStrategy** — strategy interface for pluggable refund calculation
- **FullRefundStrategy** — returns 100% refund
- **BookingService** — main facade handling all 4 APIs, uses ConcurrentHashMap for thread-safe lookups
- **TheatreService** — admin operations; synchronized addShow prevents concurrent show conflicts

## Design

### Booking Flow
1. Look up `Show` from index
2. `show.lockSeats()` — synchronized, all-or-nothing (if any seat unavailable, none are locked)
3. Calculate price based on `SeatType` (Silver ₹150, Gold ₹250, Platinum ₹400)
4. `show.confirmSeats()` — LOCKED → BOOKED
5. Create and return `MovieTicket`

### Cancellation Flow
1. Look up `MovieTicket`, mark as CANCELLED
2. `show.releaseSeats()` — BOOKED → AVAILABLE
3. Calculate refund via `RefundStrategy` (OCP — swap in percentage/time-based strategies without changing BookingService)

### Concurrency
- **Seat booking**: `Show` is the monitor — `lockSeats`, `confirmSeats`, `releaseSeats` are all `synchronized`. Two users booking different shows run in parallel; same show requests are serialized.
- **Admin show addition**: `TheatreService.addShow()` is `synchronized` to prevent race conditions on screen modification.
- **Shared maps**: `ConcurrentHashMap` in `BookingService` for thread-safe read/write on theatres, shows, and tickets.


