# Snakes and Ladders

## How to run
```
cd src
javac *.java
java Main
```

## Classes

- **Snake** - head and tail positions
- **Ladder** - bottom and top positions
- **Player** - name and current position on board
- **Dice** - random roll between 1 and 6
- **Board** - holds snake/ladder maps, resolves landing positions
- **BoardBuilder** - randomly generates snakes and ladders based on difficulty
- **Game** - queue-based turn system, tracks rankings
- **Main** - takes user input (board size, players, difficulty)

## Design

- Board is n x n, snakes and ladders are randomly placed
- Easy mode: snakes only in upper half with shorter drops, ladders are longer
- Hard mode: snakes anywhere with bigger drops, ladders are shorter
- Players take turns in a queue, removed on winning
- Game runs until only one player remains
