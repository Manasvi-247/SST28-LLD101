# Multi-Level Parking Lot

## How to run
```
cd src
javac *.java
java Main
```

## Classes

- **SlotType** - enum for SMALL, MEDIUM, LARGE with hourly rates
- **VehicleType** - enum for TWO_WHEELER, CAR, BUS with compatible slot mappings
- **Vehicle** - holds number plate and vehicle type
- **ParkingSlot** - slot with id, type, floor, position; tracks occupancy
- **ParkingTicket** - generated on entry, stores vehicle, slot, entry time
- **Bill** - generated on exit, calculates duration and amount based on slot rate
- **ParkingLot** - core class with `park()`, `status()`, `exit()` methods
- **Main** - demo

## Design

- Slots are spread across multiple floors, each gate has a position
- `park()` finds the nearest free slot of the requested type from the entry gate
- If no slot of that type is available, it tries the next larger compatible type
- Billing is based on the allocated slot type, not the vehicle type
