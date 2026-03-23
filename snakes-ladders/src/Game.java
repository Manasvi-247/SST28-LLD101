import java.util.*;

public class Game {
    private final Board board;
    private final Dice dice;
    private final GameListener listener;
    private final List<Player> rankings;

    public Game(Board board, Dice dice, GameListener listener) {
        this.board = board;
        this.dice = dice;
        this.listener = listener;
        this.rankings = new ArrayList<>();
    }

    public void start(List<Player> playerList) {
        listener.onBoardSetup(board.totalCells, board.getSnakes(), board.getLadders());
        listener.onGameStart(playerList);

        LinkedList<Player> queue = new LinkedList<>(playerList);

        while (queue.size() > 1) {
            Player p = queue.poll();
            int rolled = dice.roll();
            int next = p.getPosition() + rolled;

            listener.onRoll(p, rolled);

            if (next > board.totalCells) {
                listener.onCantMove(p, board.totalCells);
                queue.add(p);
                continue;
            }

            next = board.resolve(next, listener);
            p.moveTo(next);
            listener.onMove(p, next);

            if (board.isFinish(next)) {
                rankings.add(p);
                listener.onFinish(p, rankings.size());
            } else {
                queue.add(p);
            }
        }

        if (!queue.isEmpty()) {
            Player last = queue.poll();
            rankings.add(last);
            listener.onLastRemaining(last, rankings.size());
        }

        listener.onRankings(rankings);
    }

    public List<Player> getRankings() {
        return Collections.unmodifiableList(rankings);
    }
}
