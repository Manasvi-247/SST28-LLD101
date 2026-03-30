import java.util.*;

public class Elevator {
    private final String id;
    private final double weightLimit;
    private int currentFloor;
    private ElevatorState state;
    private DoorState doorState;
    private double currentWeight;
    private boolean alarmActive;
    private final TreeSet<Integer> requestedFloors;
    private final Display display;
    private final List<ElevatorEventListener> listeners;

    public Elevator(String id, double weightLimit, int startFloor) {
        this.id = id;
        this.weightLimit = weightLimit;
        this.currentFloor = startFloor;
        this.state = ElevatorState.IDLE;
        this.doorState = DoorState.CLOSED;
        this.currentWeight = 0;
        this.alarmActive = false;
        this.requestedFloors = new TreeSet<>();
        this.display = new Display(startFloor);
        this.listeners = new ArrayList<>();
    }

    public void addListener(ElevatorEventListener listener) {
        listeners.add(listener);
    }

    public String getId() { return id; }
    public double getWeightLimit() { return weightLimit; }
    public synchronized int getCurrentFloor() { return currentFloor; }
    public synchronized ElevatorState getState() { return state; }
    public synchronized DoorState getDoorState() { return doorState; }
    public synchronized double getCurrentWeight() { return currentWeight; }
    public synchronized boolean isAlarmActive() { return alarmActive; }
    public synchronized boolean isOverweight() { return currentWeight > weightLimit; }
    public Display getDisplay() { return display; }

    public synchronized Set<Integer> getRequestedFloors() {
        return new TreeSet<>(requestedFloors);
    }

    public synchronized void openDoor() {
        if (state == ElevatorState.MAINTENANCE) throw new IllegalStateException(id + " is under maintenance");
        doorState = DoorState.OPEN;
        for (var l : listeners) l.onDoorOpened(id, currentFloor);
    }

    public synchronized void closeDoor() {
        if (state == ElevatorState.MAINTENANCE) throw new IllegalStateException(id + " is under maintenance");
        doorState = DoorState.CLOSED;
        for (var l : listeners) l.onDoorClosed(id, currentFloor);
    }

    public synchronized void pressFloorButton(int floor) {
        if (state == ElevatorState.MAINTENANCE) throw new IllegalStateException(id + " is under maintenance");
        if (alarmActive) throw new IllegalStateException(id + " alarm is active");
        if (floor == currentFloor) {
            openDoor();
            return;
        }
        requestedFloors.add(floor);
        if (state == ElevatorState.IDLE) {
            state = floor > currentFloor ? ElevatorState.MOVING_UP : ElevatorState.MOVING_DOWN;
            updateDisplay();
        }
    }

    public synchronized void addExternalRequest(int floor) {
        if (state == ElevatorState.MAINTENANCE) throw new IllegalStateException(id + " is under maintenance");
        if (alarmActive) throw new IllegalStateException(id + " alarm is active");
        if (floor == currentFloor) {
            openDoor();
            for (var l : listeners) l.onFloorReached(id, currentFloor);
            return;
        }
        requestedFloors.add(floor);
        if (state == ElevatorState.IDLE) {
            state = floor > currentFloor ? ElevatorState.MOVING_UP : ElevatorState.MOVING_DOWN;
            updateDisplay();
        }
    }

    public synchronized void moveOneFloor() {
        if (state == ElevatorState.MAINTENANCE || state == ElevatorState.IDLE) return;
        if (alarmActive || isOverweight()) return;
        if (doorState == DoorState.OPEN) return;
        if (requestedFloors.isEmpty()) {
            state = ElevatorState.IDLE;
            updateDisplay();
            return;
        }

        if (state == ElevatorState.MOVING_UP) {
            currentFloor++;
        } else if (state == ElevatorState.MOVING_DOWN) {
            currentFloor--;
        }

        updateDisplay();

        if (requestedFloors.contains(currentFloor)) {
            requestedFloors.remove(currentFloor);
            openDoor();
            for (var l : listeners) l.onFloorReached(id, currentFloor);
        }

        if (requestedFloors.isEmpty()) {
            state = ElevatorState.IDLE;
            updateDisplay();
        } else if (state == ElevatorState.MOVING_UP && requestedFloors.higher(currentFloor) == null) {
            state = ElevatorState.MOVING_DOWN;
            updateDisplay();
        } else if (state == ElevatorState.MOVING_DOWN && requestedFloors.lower(currentFloor) == null) {
            state = ElevatorState.MOVING_UP;
            updateDisplay();
        }
    }

    public synchronized void pressEmergency() {
        state = ElevatorState.IDLE;
        requestedFloors.clear();
        openDoor();
        for (var l : listeners) l.onEmergency(id, currentFloor);
    }

    public synchronized void pressAlarm() {
        alarmActive = true;
        state = ElevatorState.IDLE;
        requestedFloors.clear();
        for (var l : listeners) l.onAlarm(id, currentFloor);
    }

    public synchronized void clearAlarm() {
        alarmActive = false;
    }

    public synchronized void addWeight(double kg) {
        currentWeight += kg;
        if (isOverweight()) {
            state = ElevatorState.IDLE;
            requestedFloors.clear();
            openDoor();
            alarmActive = true;
            for (var l : listeners) l.onOverweight(id, currentFloor, currentWeight, weightLimit);
        }
    }

    public synchronized void removeWeight(double kg) {
        currentWeight = Math.max(0, currentWeight - kg);
        if (!isOverweight()) {
            alarmActive = false;
            if (doorState == DoorState.OPEN) closeDoor();
        }
    }

    public synchronized void setMaintenance(boolean on) {
        if (on) {
            state = ElevatorState.MAINTENANCE;
            requestedFloors.clear();
            alarmActive = false;
        } else {
            state = ElevatorState.IDLE;
        }
        updateDisplay();
    }

    public synchronized void removeFloorRequest(int floor) {
        requestedFloors.remove(floor);
        if (requestedFloors.isEmpty() && state != ElevatorState.MAINTENANCE) {
            state = ElevatorState.IDLE;
            updateDisplay();
        }
    }

    private void updateDisplay() {
        Direction dir = switch (state) {
            case MOVING_UP -> Direction.UP;
            case MOVING_DOWN -> Direction.DOWN;
            default -> null;
        };
        display.update(currentFloor, dir);
    }

    @Override
    public String toString() {
        return id + " [Floor:" + currentFloor + " " + state + " " + doorState
                + " Weight:" + currentWeight + "/" + weightLimit + "kg"
                + (alarmActive ? " ALARM" : "")
                + " Requests:" + requestedFloors + "]";
    }
}
