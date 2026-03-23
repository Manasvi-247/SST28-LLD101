public class PenFactory {

    public static Pen create(PenType type, String color, MechanismType mechanism) {
        WriteStrategy writer = switch (type) {
            case BALLPOINT -> new BallpointWrite();
            case GEL -> new GelWrite();
            case FOUNTAIN -> new FountainWrite();
        };

        RefillStrategy refiller = switch (type) {
            case BALLPOINT, GEL -> new TubeRefill();
            case FOUNTAIN -> new BottleRefill();
        };

        OpenCloseStrategy opener = switch (mechanism) {
            case CAP -> new CapMechanism();
            case CLICK -> new ClickMechanism();
        };

        return new Pen(color, writer, refiller, opener);
    }
}
