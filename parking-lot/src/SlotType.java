public enum SlotType {
    SMALL(10),
    MEDIUM(20),
    LARGE(30);

    private final int ratePerHour;

    SlotType(int ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }
}
