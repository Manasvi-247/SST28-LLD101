public interface GameListener {
    void onBoardSetup(int totalCells, java.util.Map<Integer, Integer> snakes, java.util.Map<Integer, Integer> ladders);
    void onGameStart(java.util.List<Player> players);
    void onRoll(Player player, int rolled);
    void onCantMove(Player player, int maxCell);
    void onMove(Player player, int pos);
    void onSnake(int from, int to);
    void onLadder(int from, int to);
    void onFinish(Player player, int rank);
    void onLastRemaining(Player player, int rank);
    void onRankings(java.util.List<Player> rankings);
}
