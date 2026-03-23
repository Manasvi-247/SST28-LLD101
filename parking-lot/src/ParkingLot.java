import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private final List<ParkingSlot> slots;
    private final List<Gate> gates;
    private final SlotAssignmentStrategy assignmentStrategy;
    private final PricingStrategy pricingStrategy;
    private final Map<String, ParkingTicket> activeTickets;

    public ParkingLot(SlotAssignmentStrategy assignmentStrategy, PricingStrategy pricingStrategy) {
        this.slots = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.assignmentStrategy = assignmentStrategy;
        this.pricingStrategy = pricingStrategy;
        this.activeTickets = new HashMap<>();
    }

    public void addSlot(ParkingSlot slot) { slots.add(slot); }
    public void addGate(Gate gate) { gates.add(gate); }

    public Gate getGate(String gateId) {
        for (Gate g : gates) {
            if (g.getId().equals(gateId)) return g;
        }
        throw new IllegalArgumentException("Unknown gate: " + gateId);
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime, Gate entryGate) {
        ParkingSlot slot = assignmentStrategy.findSlot(entryGate, slots, vehicle.type);
        if (slot == null)
            throw new RuntimeException("No available slot for " + vehicle);

        slot.markOccupied();
        ParkingTicket tkt = new ParkingTicket(vehicle, slot, entryTime);
        activeTickets.put(tkt.ticketId, tkt);
        return tkt;
    }

    public Bill exit(ParkingTicket ticket, LocalDateTime exitTime) {
        ParkingTicket stored = activeTickets.remove(ticket.ticketId);
        if (stored == null)
            throw new IllegalArgumentException("No active ticket: " + ticket.ticketId);
        stored.allocatedSlot.markFree();
        return new Bill(stored, exitTime, pricingStrategy);
    }

    public Map<SlotType, int[]> status() {
        Map<SlotType, int[]> map = new LinkedHashMap<>();
        for (SlotType t : SlotType.values()) {
            int total = 0, free = 0;
            for (ParkingSlot s : slots) {
                if (s.getType() == t) {
                    total++;
                    if (s.isFree()) free++;
                }
            }
            map.put(t, new int[]{free, total});
        }
        return map;
    }
}
