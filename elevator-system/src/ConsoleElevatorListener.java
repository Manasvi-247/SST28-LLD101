public class ConsoleElevatorListener implements ElevatorEventListener {
    @Override
    public void onFloorReached(String elevatorId, int floor) {
        System.out.println("  [" + elevatorId + "] Reached floor " + floor);
    }

    @Override
    public void onDoorOpened(String elevatorId, int floor) {
        System.out.println("  [" + elevatorId + "] Door OPENED at floor " + floor);
    }

    @Override
    public void onDoorClosed(String elevatorId, int floor) {
        System.out.println("  [" + elevatorId + "] Door CLOSED at floor " + floor);
    }

    @Override
    public void onAlarm(String elevatorId, int floor) {
        System.out.println("  [" + elevatorId + "] ALARM activated at floor " + floor);
    }

    @Override
    public void onOverweight(String elevatorId, int floor, double currentWeight, double limit) {
        System.out.println("  [" + elevatorId + "] OVERWEIGHT at floor " + floor
                + " (" + currentWeight + "kg / " + limit + "kg limit)");
    }

    @Override
    public void onEmergency(String elevatorId, int floor) {
        System.out.println("  [" + elevatorId + "] EMERGENCY at floor " + floor);
    }
}
