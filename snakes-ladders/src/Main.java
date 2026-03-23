import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = readInt(sc, "Board size (n for nxn): ", 3, 20);
        int x = readInt(sc, "Number of players: ", 2, 10);
        Difficulty diff = readDifficulty(sc);

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= x; i++) {
            System.out.print("Player " + i + " name: ");
            players.add(new Player(sc.next()));
        }

        System.out.println();

        BoardBuilder builder = new BoardBuilder();
        Board board = builder.build(n, diff);

        Game game = new Game(board, new Dice(6), new ConsoleGameListener());
        game.start(players);
    }

    private static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Enter a number between " + min + " and " + max);
                continue;
            }
            int val = sc.nextInt();
            if (val >= min && val <= max) return val;
            System.out.println("Enter a number between " + min + " and " + max);
        }
    }

    private static Difficulty readDifficulty(Scanner sc) {
        while (true) {
            System.out.print("Difficulty (easy/hard): ");
            String input = sc.next().trim().toLowerCase();
            if (input.equals("easy")) return Difficulty.EASY;
            if (input.equals("hard")) return Difficulty.HARD;
            System.out.println("Please enter 'easy' or 'hard'");
        }
    }
}
