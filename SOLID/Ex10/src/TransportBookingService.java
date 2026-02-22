public class TransportBookingService {
    // DIP violation – high-level module creates concrete low-level modules directly
    public void book(TripRequest req) {
        DistanceCalculator distCalc = new DistanceCalculator();
        double km = distCalc.km(req.from, req.to);
        System.out.println("DistanceKm=" + km);

        DriverAllocator allocator = new DriverAllocator();
        String driver = allocator.allocate(req.studentId);
        System.out.println("Driver=" + driver);

        double fare = 50.0 + km * 6.6666666667;
        fare = Math.round(fare * 100.0) / 100.0;

        PaymentGateway gateway = new PaymentGateway();
        String txn = gateway.charge(req.studentId, fare);
        System.out.println("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        System.out.println("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}
