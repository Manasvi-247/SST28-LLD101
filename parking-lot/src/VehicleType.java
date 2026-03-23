import java.util.List;

public enum VehicleType {
    TWO_WHEELER(List.of(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE)),
    CAR(List.of(SlotType.MEDIUM, SlotType.LARGE)),
    BUS(List.of(SlotType.LARGE));

    private final List<SlotType> allowedSlots;

    VehicleType(List<SlotType> allowedSlots) {
        this.allowedSlots = allowedSlots;
    }

    public boolean canFitIn(SlotType st) {
        return allowedSlots.contains(st);
    }

    public List<SlotType> getAllowedSlots() {
        return allowedSlots;
    }
}
