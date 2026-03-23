public interface PricingStrategy {
    double calculateFee(ParkingTicket ticket, long durationMinutes);
}
