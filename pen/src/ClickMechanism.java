public class ClickMechanism implements OpenCloseStrategy {
    @Override
    public void open() {
        System.out.println("Nib extended.");
    }

    @Override
    public void close() {
        System.out.println("Nib retracted.");
    }
}
