# Pen

## How to run
```
cd src
javac *.java
java Main
```

## Classes

- **PenType** - enum: BALLPOINT, GEL, FOUNTAIN
- **MechanismType** - enum: CAP, CLICK
- **WriteStrategy** - interface for writing behavior
- **RefillStrategy** - interface for refill behavior
- **OpenCloseStrategy** - interface for open/close mechanism
- **BallpointWrite, GelWrite, FountainWrite** - write strategy implementations
- **TubeRefill, BottleRefill** - refill strategy implementations
- **CapMechanism, ClickMechanism** - open/close strategy implementations
- **Pen** - core class with color, state, and delegated strategies
- **PenFactory** - creates pens by wiring correct strategies for a given type and mechanism
- **Main** - demo showing all pen types, error on write-before-open, and refill

## Design

- Strategy pattern on three axes: write, refill, open/close
- Factory pattern to wire the right strategy combination per pen type
- State guard: writing on a closed pen throws IllegalStateException
- Adding a new pen type or mechanism requires no changes to Pen
