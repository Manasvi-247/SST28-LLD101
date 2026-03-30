public class FullRefundStrategy implements RefundStrategy {
    @Override
    public double calculateRefund(MovieTicket ticket) {
        return ticket.getTotalPrice();
    }
}
