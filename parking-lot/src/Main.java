import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<ParkingSlot> slots = new ArrayList<>();
        int id = 1;
        slots.add(new ParkingSlot(id++, SlotType.SMALL, 0, 0));
        slots.add(new ParkingSlot(id++, SlotType.SMALL, 0, 10));
        slots.add(new ParkingSlot(id++, SlotType.MEDIUM, 0, 20));
        slots.add(new ParkingSlot(id++, SlotType.MEDIUM, 0, 35));
        slots.add(new ParkingSlot(id++, SlotType.LARGE, 0, 50));
        slots.add(new ParkingSlot(id++, SlotType.SMALL, 1, 0));
        slots.add(new ParkingSlot(id++, SlotType.MEDIUM, 1, 15));
        slots.add(new ParkingSlot(id++, SlotType.LARGE, 1, 30));
        slots.add(new ParkingSlot(id++, SlotType.LARGE, 1, 45));

        Map<Integer, Integer> gates = new HashMap<>();
        gates.put(1, 0);
        gates.put(2, 50);

        ParkingLot lot = new ParkingLot(slots, gates);

        System.out.println("before parking:");
        printStatus(lot);

        LocalDateTime t = LocalDateTime.of(2025, 3, 20, 10, 0);

        ParkingTicket tk1 = lot.park(new Vehicle("KA01AB1234", VehicleType.TWO_WHEELER), t, SlotType.SMALL, 1);
        System.out.println(tk1);

        ParkingTicket tk2 = lot.park(new Vehicle("MH02CD5678", VehicleType.CAR), t.plusMinutes(10), SlotType.MEDIUM, 2);
        System.out.println(tk2);

        ParkingTicket tk3 = lot.park(new Vehicle("TN03EF9012", VehicleType.BUS), t.plusMinutes(20), SlotType.LARGE, 1);
        System.out.println(tk3);

        System.out.println("\nafter parking:");
        printStatus(lot);

        System.out.println("exits:");
        System.out.println(lot.exit(tk1, t.plusHours(3)));
        System.out.println(lot.exit(tk2, t.plusHours(1).plusMinutes(30)));
        System.out.println(lot.exit(tk3, t.plusHours(5)));

        ParkingTicket tk4 = lot.park(new Vehicle("KA09ZZ0001", VehicleType.TWO_WHEELER), t.plusHours(6), SlotType.MEDIUM, 1);
        System.out.println("\nbike in medium slot: " + tk4);
        System.out.println(lot.exit(tk4, t.plusHours(7)));

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
