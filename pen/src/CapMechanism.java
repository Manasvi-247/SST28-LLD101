public class CapMechanism implements OpenCloseStrategy {
    @Override
    public void open() {
        System.out.println("Cap removed.");
    }

    @Override
    public void close() {
        System.out.println("Cap replaced.");
    }
}
