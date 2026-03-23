import java.util.*;

public class BoardBuilder {
    private static final int MAX_ATTEMPTS = 1000;
    private final Random rng = new Random();

    public Board build(int n, Difficulty difficulty) {
        int cells = n * n;
        int count = Math.min(n, (cells - 2) / 4);

        Set<Integer> taken = new HashSet<>();
        taken.add(1);
        taken.add(cells);

        List<Snake> snakeList = new ArrayList<>();
        List<Ladder> ladderList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Snake s = makeSnake(cells, difficulty, taken);
            if (s == null) break;
            snakeList.add(s);
            taken.add(s.head);
            taken.add(s.tail);
        }

        for (int i = 0; i < count; i++) {
            Ladder l = makeLadder(cells, difficulty, taken);
            if (l == null) break;
            ladderList.add(l);
            taken.add(l.bottom);
            taken.add(l.top);
        }

        return new Board(n, snakeList, ladderList);
    }

    private Snake makeSnake(int cells, Difficulty difficulty, Set<Integer> taken) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int head, tail;
            if (difficulty == Difficulty.HARD) {
                head = 2 + rng.nextInt(cells - 2);
                tail = 1 + rng.nextInt(head - 1);
            } else {
                int lowerBound = cells / 2 + 1;
                head = lowerBound + rng.nextInt(cells - lowerBound);
                int drop = 1 + rng.nextInt(Math.max(1, cells / 4));
                tail = Math.max(1, head - drop);
            }
            if (head != tail && !taken.contains(head) && !taken.contains(tail))
                return new Snake(head, tail);
        }
        return null;
    }

    private Ladder makeLadder(int cells, Difficulty difficulty, Set<Integer> taken) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int bottom, top;
            if (difficulty == Difficulty.HARD) {
                bottom = 1 + rng.nextInt(cells - 2);
                int climb = 1 + rng.nextInt(Math.max(1, cells / 4));
                top = Math.min(cells - 1, bottom + climb);
            } else {
                bottom = 1 + rng.nextInt(cells / 2);
                int climb = 1 + rng.nextInt(Math.max(1, cells / 2));
                top = Math.min(cells - 1, bottom + climb);
            }
            if (bottom != top && !taken.contains(bottom) && !taken.contains(top))
                return new Ladder(bottom, top);
        }
        return null;
    }
}
