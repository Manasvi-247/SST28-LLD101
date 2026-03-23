import java.util.*;

public class Board {
    public final int totalCells;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(int n, List<Snake> snakeList, List<Ladder> ladderList) {
        this.totalCells = n * n;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
        for (Snake s : snakeList) snakes.put(s.head, s.tail);
        for (Ladder l : ladderList) ladders.put(l.bottom, l.top);
    }

    public int resolve(int pos, GameListener listener) {
        while (snakes.containsKey(pos) || ladders.containsKey(pos)) {
            if (snakes.containsKey(pos)) {
                int dest = snakes.get(pos);
                listener.onSnake(pos, dest);
                pos = dest;
            } else if (ladders.containsKey(pos)) {
                int dest = ladders.get(pos);
                listener.onLadder(pos, dest);
                pos = dest;
            }
        }
        return pos;
    }

    public boolean isFinish(int pos) {
        return pos == totalCells;
    }

    public Map<Integer, Integer> getSnakes() { return Collections.unmodifiableMap(snakes); }
    public Map<Integer, Integer> getLadders() { return Collections.unmodifiableMap(ladders); }
}
