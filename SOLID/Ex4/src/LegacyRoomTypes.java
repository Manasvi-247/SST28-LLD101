import java.util.*;

public class LegacyRoomTypes {
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int DELUXE = 4;

    public static String nameOf(int t) {
        return switch (t) {
            case SINGLE -> "SINGLE";
            case DOUBLE -> "DOUBLE";
            case TRIPLE -> "TRIPLE";
            default -> "DELUXE";
        };
    }
}

interface RoomPricing {
    double monthlyRate(int roomType);
}

interface AddOnPricing {
    double rate(AddOn addOn);
}

class DefaultRoomPricing implements RoomPricing {
    private final Map<Integer, Double> rates = new HashMap<>();

    public DefaultRoomPricing() {
        rates.put(LegacyRoomTypes.SINGLE, 14000.0);
        rates.put(LegacyRoomTypes.DOUBLE, 15000.0);
        rates.put(LegacyRoomTypes.TRIPLE, 12000.0);
        rates.put(LegacyRoomTypes.DELUXE, 16000.0);
    }

    @Override
    public double monthlyRate(int roomType) {
        return rates.getOrDefault(roomType, 16000.0);
    }
}

class DefaultAddOnPricing implements AddOnPricing {
    private final Map<AddOn, Double> rates = new HashMap<>();

    public DefaultAddOnPricing() {
        rates.put(AddOn.MESS, 1000.0);
        rates.put(AddOn.LAUNDRY, 500.0);
        rates.put(AddOn.GYM, 300.0);
    }

    @Override
    public double rate(AddOn addOn) {
        return rates.getOrDefault(addOn, 0.0);
    }
}
