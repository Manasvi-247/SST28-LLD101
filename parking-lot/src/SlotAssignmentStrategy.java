import java.util.List;

public interface SlotAssignmentStrategy {
    ParkingSlot findSlot(Gate entryGate, List<ParkingSlot> slots, VehicleType vehicleType);
}
