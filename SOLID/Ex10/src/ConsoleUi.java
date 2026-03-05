interface DistanceCalculable {
    double km(GeoPoint a, GeoPoint b);
}

interface DriverAllocatable {
    String allocate(String studentId);
}

interface PaymentChargeable {
    String charge(String studentId, double amount);
}
