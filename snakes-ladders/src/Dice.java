import java.util.Random;

public class Dice {
    private final Random rng = new Random();
    private final int sides;

    public Dice(int sides) {
        this.sides = sides;
    }

    public int roll() {
        return rng.nextInt(sides) + 1;
    }
}
