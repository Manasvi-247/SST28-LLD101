import java.util.List;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    @Override
    public ParkingSlot findSlot(Gate entryGate, List<ParkingSlot> slots, VehicleType vehicleType) {
        SlotType required = getRequiredType(vehicleType);
        ParkingSlot nearest = null;
        double minDist = Double.MAX_VALUE;

        for (SlotType candidate : SlotType.values()) {
            if (candidate.ordinal() < required.ordinal()) continue;

            for (ParkingSlot s : slots) {
                if (!s.isFree() || s.getType() != candidate) continue;
                double dist = s.distanceTo(entryGate);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = s;
                }
            }
            if (nearest != null) return nearest;
        }
        return null;
    }

    private SlotType getRequiredType(VehicleType type) {
        return switch (type) {
            case TWO_WHEELER -> SlotType.SMALL;
            case CAR -> SlotType.MEDIUM;
            case BUS -> SlotType.LARGE;
        };
    }
}
