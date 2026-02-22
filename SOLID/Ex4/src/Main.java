import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));

        RoomPricing roomPricing = new DefaultRoomPricing();
        AddOnPricing addOnPricing = new DefaultAddOnPricing();

        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo(), roomPricing, addOnPricing);
        calc.process(req);
    }
}
