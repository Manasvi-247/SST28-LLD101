public class Pen {
    private String color;
    private boolean open;
    private final WriteStrategy writer;
    private final RefillStrategy refiller;
    private final OpenCloseStrategy mechanism;

    public Pen(String color, WriteStrategy writer, RefillStrategy refiller, OpenCloseStrategy mechanism) {
        this.color = color;
        this.writer = writer;
        this.refiller = refiller;
        this.mechanism = mechanism;
        this.open = false;
    }

    public void open() {
        mechanism.open();
        this.open = true;
    }

    public void close() {
        mechanism.close();
        this.open = false;
    }

    public boolean isOpen() { return open; }
    public String getColor() { return color; }

    public void write() {
        if (!open) throw new IllegalStateException("Pen is closed. Call open() first.");
        System.out.print("[" + color + "] ");
        writer.write();
    }

    public void refill(String newColor) {
        refiller.refill();
        this.color = newColor;
        System.out.println("Color changed to " + newColor + ".");
    }

    @Override
    public String toString() {
        return "Pen(" + color + ", " + (open ? "open" : "closed") + ")";
    }
}
