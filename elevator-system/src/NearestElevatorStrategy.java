import java.util.*;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {
    @Override
    public Elevator selectElevator(List<Elevator> elevators, int requestedFloor, Direction direction) {
        Elevator best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (var elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) continue;
            if (elevator.isAlarmActive() || elevator.isOverweight()) continue;

            int distance = Math.abs(elevator.getCurrentFloor() - requestedFloor);
            var state = elevator.getState();

            boolean sameDirection = (direction == Direction.UP && state == ElevatorState.MOVING_UP && elevator.getCurrentFloor() <= requestedFloor)
                    || (direction == Direction.DOWN && state == ElevatorState.MOVING_DOWN && elevator.getCurrentFloor() >= requestedFloor);

            if (sameDirection && distance < bestDistance) {
                best = elevator;
                bestDistance = distance;
            }
        }

        if (best != null) return best;

        for (var elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) continue;
            if (elevator.isAlarmActive() || elevator.isOverweight()) continue;
            if (elevator.getState() != ElevatorState.IDLE) continue;

            int distance = Math.abs(elevator.getCurrentFloor() - requestedFloor);
            if (distance < bestDistance) {
                best = elevator;
                bestDistance = distance;
            }
        }

        return best;
    }
}
