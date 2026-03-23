import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<SlotType, Double> rates = new LinkedHashMap<>();
        rates.put(SlotType.SMALL, 10.0);
        rates.put(SlotType.MEDIUM, 20.0);
        rates.put(SlotType.LARGE, 30.0);

        ParkingLot lot = new ParkingLot(new NearestSlotStrategy(), new HourlyPricing(rates));

        Gate g1 = new Gate("G1", 0, 0, 0);
        Gate g2 = new Gate("G2", 0, 50, 0);
        lot.addGate(g1);
        lot.addGate(g2);

        lot.addSlot(new ParkingSlot("S1", SlotType.SMALL, 0, 5, 0));
        lot.addSlot(new ParkingSlot("S2", SlotType.SMALL, 0, 10, 0));
        lot.addSlot(new ParkingSlot("S3", SlotType.MEDIUM, 0, 20, 0));
        lot.addSlot(new ParkingSlot("S4", SlotType.MEDIUM, 0, 35, 0));
        lot.addSlot(new ParkingSlot("S5", SlotType.LARGE, 0, 50, 0));
        lot.addSlot(new ParkingSlot("S6", SlotType.SMALL, 1, 5, 0));
        lot.addSlot(new ParkingSlot("S7", SlotType.MEDIUM, 1, 15, 0));
        lot.addSlot(new ParkingSlot("S8", SlotType.LARGE, 1, 30, 0));
        lot.addSlot(new ParkingSlot("S9", SlotType.LARGE, 1, 45, 0));

        System.out.println("before parking:");
        printStatus(lot);

        LocalDateTime t = LocalDateTime.of(2025, 3, 20, 10, 0);

        ParkingTicket tk1 = lot.park(new Vehicle("KA01AB1234", VehicleType.TWO_WHEELER), t, g1);
        System.out.println(tk1);

        ParkingTicket tk2 = lot.park(new Vehicle("MH02CD5678", VehicleType.CAR), t.plusMinutes(10), g2);
        System.out.println(tk2);

        ParkingTicket tk3 = lot.park(new Vehicle("TN03EF9012", VehicleType.BUS), t.plusMinutes(20), g1);
        System.out.println(tk3);

        System.out.println("\nafter parking:");
        printStatus(lot);

        System.out.println("exits:");
        System.out.println(lot.exit(tk1, t.plusHours(3)));
        System.out.println(lot.exit(tk2, t.plusHours(1).plusMinutes(30)));
        System.out.println(lot.exit(tk3, t.plusHours(5)));

        System.out.println("\nfinal:");
        printStatus(lot);
    }

    static void printStatus(ParkingLot lot) {
        for (var e : lot.status().entrySet()) {
            int[] c = e.getValue();
            System.out.println("  " + e.getKey() + " -> " + c[0] + " free out of " + c[1]);
        }
    }
}
