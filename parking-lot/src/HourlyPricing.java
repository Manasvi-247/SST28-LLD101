import java.util.Map;

public class HourlyPricing implements PricingStrategy {
    private final Map<SlotType, Double> rates;

    public HourlyPricing(Map<SlotType, Double> rates) {
        this.rates = rates;
    }

    @Override
    public double calculateFee(ParkingTicket ticket, long durationMinutes) {
        long hours = (durationMinutes + 59) / 60;
        if (hours == 0) hours = 1;
        double rate = rates.getOrDefault(ticket.allocatedSlot.getType(), 10.0);
        return hours * rate;
    }
}
