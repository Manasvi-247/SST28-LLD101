# Elevator System

## How to run

```
cd src
javac *.java
java Main
```

## APIs

- `requestElevator(floor, direction)` — outside button press, dispatches nearest suitable elevator
- `pressFloorButton(elevatorId, floor)` — inside button, adds destination
- `pressOpenDoor(elevatorId)` / `pressCloseDoor(elevatorId)` — door controls
- `pressEmergency(elevatorId)` — stops elevator, clears requests, opens door
- `pressAlarm(elevatorId)` — stops elevator, activates alarm
- `addFloorToMaintenance(floor)` / `removeFloorFromMaintenance(floor)` — floor maintenance
- `setElevatorMaintenance(elevatorId, boolean)` — elevator maintenance
- `step()` — simulates one time unit of movement

## Classes

- **Direction** — enum: UP, DOWN
- **ElevatorState** — enum: MOVING_UP, MOVING_DOWN, IDLE, MAINTENANCE
- **DoorState** — enum: OPEN, CLOSED
- **ButtonType** — enum: FLOOR, OPEN_DOOR, CLOSE_DOOR, EMERGENCY, ALARM
- **Floor** — value object with floor number
- **ElevatorButton** — button descriptor with type and target floor, static factory methods
- **Display** — shows current floor and direction per elevator
- **Elevator** — core entity with synchronized state management for floor, door, weight, alarm
- **ElevatorEventListener** — observer interface for elevator events
- **ConsoleElevatorListener** — prints events to console
- **ElevatorSelectionStrategy** — strategy interface for picking which elevator to dispatch
- **NearestElevatorStrategy** — picks same-direction passing elevator first, then nearest idle
- **ElevatorController** — main controller managing all elevators, floors, maintenance, and simulation

## Design

### Elevator Selection

1. Filter out elevators in MAINTENANCE, alarm active, or overweight
2. First priority: elevator already moving in same direction that will pass the requested floor
3. Second priority: nearest IDLE elevator
4. Tie-break by distance

### Step Simulation

Each `step()` call = one time unit per elevator:

- If door is OPEN → close it (auto-close)
- If IDLE with pending requests → determine direction, start moving
- Move one floor in current direction
- If reached a requested floor → remove from set, open door, fire event
- If no more requests → go IDLE
- If no more requests in current direction → flip direction

### Weight Management

- `addWeight(kg)` — if over limit: stops elevator, opens door, activates alarm, fires onOverweight
- `removeWeight(kg)` — if back under limit: clears alarm, closes door

### Concurrency

- **Elevator**: all state-changing methods are `synchronized` on the Elevator instance
- **ElevatorController**: `requestElevator`, `step`, maintenance methods are `synchronized` on the controller
- Two threads requesting elevators simultaneously are serialized at the controller level

### SOLID Principles

- **S** — Elevator manages its own state, Controller manages coordination, Strategy handles selection logic
- **O** — ElevatorSelectionStrategy is open for extension (add priority-based, load-balanced strategies)
- **L** — any strategy implementation can replace NearestElevatorStrategy
- **I** — ElevatorEventListener has focused event callbacks, ElevatorSelectionStrategy is single-method
- **D** — ElevatorController depends on ElevatorSelectionStrategy abstraction, injected via constructor

## PlantUML Class Diagram

```plantuml
`
```

