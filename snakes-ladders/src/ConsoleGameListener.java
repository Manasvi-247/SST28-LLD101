import java.util.*;

public class ConsoleGameListener implements GameListener {

    @Override
    public void onBoardSetup(int totalCells, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        System.out.println("Board size: " + totalCells + " cells");
        System.out.println("Snakes  : " + snakes);
        System.out.println("Ladders : " + ladders);
    }

    @Override
    public void onGameStart(List<Player> players) {
        System.out.println("Players: " + players);
        System.out.println();
    }

    @Override
    public void onRoll(Player player, int rolled) {
        System.out.println(player.name + " rolled " + rolled);
    }

    @Override
    public void onCantMove(Player player, int maxCell) {
        System.out.println("  Can't move past " + maxCell + ", stays at " + player.getPosition());
    }

    @Override
    public void onMove(Player player, int pos) {
        System.out.println("  " + player.name + " is now at " + pos);
    }

    @Override
    public void onSnake(int from, int to) {
        System.out.println("    Oops! Snake at " + from + ", sliding down to " + to);
    }

    @Override
    public void onLadder(int from, int to) {
        System.out.println("    Nice! Ladder at " + from + ", climbing up to " + to);
    }

    @Override
    public void onFinish(Player player, int rank) {
        System.out.println("  >> " + player.name + " finishes at rank #" + rank + "!");
    }

    @Override
    public void onLastRemaining(Player player, int rank) {
        System.out.println(player.name + " is the last one remaining (rank #" + rank + ")");
    }

    @Override
    public void onRankings(List<Player> rankings) {
        System.out.println("\n=== Final Rankings ===");
        for (int i = 0; i < rankings.size(); i++) {
            System.out.println((i + 1) + ". " + rankings.get(i).name);
        }
    }
}
