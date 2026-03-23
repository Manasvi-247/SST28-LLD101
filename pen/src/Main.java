public class Main {
    public static void main(String[] args) {
        Pen gel = PenFactory.create(PenType.GEL, "Blue", MechanismType.CLICK);
        Pen fountain = PenFactory.create(PenType.FOUNTAIN, "Black", MechanismType.CAP);
        Pen ballpoint = PenFactory.create(PenType.BALLPOINT, "Red", MechanismType.CLICK);

        System.out.println("-- writing without opening --");
        try {
            gel.write();
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n-- gel pen --");
        gel.open();
        gel.write();
        gel.close();

        System.out.println("\n-- fountain pen --");
        fountain.open();
        fountain.write();
        fountain.refill("Red");
        fountain.write();
        fountain.close();

        System.out.println("\n-- ballpoint pen --");
        ballpoint.open();
        ballpoint.write();
        ballpoint.refill("Green");
        ballpoint.write();
        ballpoint.close();
    }
}
