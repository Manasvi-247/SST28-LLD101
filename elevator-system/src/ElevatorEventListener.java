public interface ElevatorEventListener {
    void onFloorReached(String elevatorId, int floor);
    void onDoorOpened(String elevatorId, int floor);
    void onDoorClosed(String elevatorId, int floor);
    void onAlarm(String elevatorId, int floor);
    void onOverweight(String elevatorId, int floor, double currentWeight, double limit);
    void onEmergency(String elevatorId, int floor);
}
