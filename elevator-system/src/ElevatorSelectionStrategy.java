import java.util.*;

public interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, int requestedFloor, Direction direction);
}
