public class PaymentGateway implements PaymentChargeable {
    @Override
    public String charge(String studentId, double amount) {
        return "TXN-9001";
    }
}
