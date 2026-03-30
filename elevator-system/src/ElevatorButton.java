public class ElevatorButton {
    private final ButtonType type;
    private final int targetFloor;

    public ElevatorButton(ButtonType type, int targetFloor) {
        this.type = type;
        this.targetFloor = targetFloor;
    }

    public static ElevatorButton floorButton(int floor) { return new ElevatorButton(ButtonType.FLOOR, floor); }
    public static ElevatorButton openDoor() { return new ElevatorButton(ButtonType.OPEN_DOOR, -1); }
    public static ElevatorButton closeDoor() { return new ElevatorButton(ButtonType.CLOSE_DOOR, -1); }
    public static ElevatorButton emergency() { return new ElevatorButton(ButtonType.EMERGENCY, -1); }
    public static ElevatorButton alarm() { return new ElevatorButton(ButtonType.ALARM, -1); }

    public ButtonType getType() { return type; }
    public int getTargetFloor() { return targetFloor; }

    @Override
    public String toString() {
        return type == ButtonType.FLOOR ? "Floor " + targetFloor : type.toString();
    }
}
