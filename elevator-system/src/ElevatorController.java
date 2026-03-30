import java.util.*;

public class ElevatorController {
    private final List<Elevator> elevators;
    private final List<Floor> floors;
    private final Set<Integer> maintenanceFloors;
    private final ElevatorSelectionStrategy selectionStrategy;

    public ElevatorController(ElevatorSelectionStrategy strategy) {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.maintenanceFloors = new HashSet<>();
        this.selectionStrategy = strategy;
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public Elevator getElevator(String id) {
        for (var e : elevators) {
            if (e.getId().equals(id)) return e;
        }
        throw new IllegalArgumentException("Elevator not found: " + id);
    }

    public synchronized void requestElevator(int floorNumber, Direction direction) {
        if (maintenanceFloors.contains(floorNumber)) {
            throw new IllegalStateException("Floor " + floorNumber + " is under maintenance");
        }
        var elevator = selectionStrategy.selectElevator(elevators, floorNumber, direction);
        if (elevator == null) {
            throw new IllegalStateException("No available elevator for floor " + floorNumber);
        }
        System.out.println("  Dispatching " + elevator.getId() + " to floor " + floorNumber);
        elevator.addExternalRequest(floorNumber);
    }

    public synchronized void pressFloorButton(String elevatorId, int floor) {
        if (maintenanceFloors.contains(floor)) {
            throw new IllegalStateException("Floor " + floor + " is under maintenance");
        }
        getElevator(elevatorId).pressFloorButton(floor);
    }

    public void pressOpenDoor(String elevatorId) {
        getElevator(elevatorId).openDoor();
    }

    public void pressCloseDoor(String elevatorId) {
        getElevator(elevatorId).closeDoor();
    }

    public void pressEmergency(String elevatorId) {
        getElevator(elevatorId).pressEmergency();
    }

    public void pressAlarm(String elevatorId) {
        getElevator(elevatorId).pressAlarm();
    }

    public synchronized void addFloorToMaintenance(int floorNumber) {
        maintenanceFloors.add(floorNumber);
        for (var e : elevators) {
            e.removeFloorRequest(floorNumber);
        }
    }

    public synchronized void removeFloorFromMaintenance(int floorNumber) {
        maintenanceFloors.remove(floorNumber);
    }

    public synchronized void setElevatorMaintenance(String elevatorId, boolean on) {
        getElevator(elevatorId).setMaintenance(on);
    }

    public synchronized void step() {
        for (var elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) continue;
            if (elevator.isAlarmActive() || elevator.isOverweight()) continue;
            if (elevator.getDoorState() == DoorState.OPEN) {
                elevator.closeDoor();
                continue;
            }
            elevator.moveOneFloor();
        }
    }

    public void printStatus() {
        for (var e : elevators) {
            System.out.println("  " + e);
        }
    }
}
