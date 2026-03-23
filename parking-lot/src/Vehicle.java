public class Vehicle {
    public final String numberPlate;
    public final VehicleType type;

    public Vehicle(String numberPlate, VehicleType type) {
        this.numberPlate = numberPlate;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + "[" + numberPlate + "]";
    }
}
