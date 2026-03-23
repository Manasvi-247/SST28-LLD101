import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private final List<ParkingSlot> slots;
    private final Map<Integer, Integer> gatePositions;
    private final Map<String, ParkingTicket> activeTickets;

    public ParkingLot(List<ParkingSlot> slots, Map<Integer, Integer> gatePositions) {
        this.slots = slots;
        this.gatePositions = gatePositions;
        this.activeTickets = new HashMap<>();
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime,
                              SlotType requestedType, int entryGateId) {
        if (!gatePositions.containsKey(entryGateId))
            throw new IllegalArgumentException("Unknown gate: " + entryGateId);

        List<SlotType> allowed = vehicle.type.getAllowedSlots();
        if (!allowed.contains(requestedType))
            throw new IllegalArgumentException(vehicle.type + " cannot use " + requestedType + " slot");

        int gatePos = gatePositions.get(entryGateId);
        ParkingSlot nearest = null;

        for (SlotType candidate : allowed) {
            if (candidate.ordinal() < requestedType.ordinal()) continue;

            for (ParkingSlot s : slots) {
                if (!s.isFree() || s.type != candidate) continue;
                if (nearest == null || s.distanceFrom(gatePos) < nearest.distanceFrom(gatePos))
                    nearest = s;
            }
            if (nearest != null) break;
        }

        if (nearest == null)
            throw new RuntimeException("No available slot for " + vehicle);

        nearest.markOccupied();
        ParkingTicket tkt = new ParkingTicket(vehicle, nearest, entryTime);
        activeTickets.put(tkt.ticketId, tkt);
        return tkt;
    }

    public Map<SlotType, int[]> status() {
        Map<SlotType, int[]> map = new LinkedHashMap<>();
        for (SlotType t : SlotType.values()) {
            int total = 0, free = 0;
            for (ParkingSlot s : slots) {
                if (s.type == t) {
                    total++;
                    if (s.isFree()) free++;
                }
            }
            map.put(t, new int[]{free, total});
        }
        return map;
    }

    public Bill exit(ParkingTicket ticket, LocalDateTime exitTime) {
        ParkingTicket stored = activeTickets.remove(ticket.ticketId);
        if (stored == null)
            throw new IllegalArgumentException("No active ticket: " + ticket.ticketId);
        stored.allocatedSlot.markFree();
        return new Bill(stored, exitTime);
    }
}
