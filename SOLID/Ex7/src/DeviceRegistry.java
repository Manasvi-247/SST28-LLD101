import java.util.*;

public class DeviceRegistry {
    private final List<SmartClassroomDevice> devices = new ArrayList<>();

    public void add(SmartClassroomDevice d) { devices.add(d); }

    @SuppressWarnings("unchecked")
    public <T> T getFirstOfType(Class<T> type) {
        for (SmartClassroomDevice d : devices) {
            if (type.isInstance(d)) return (T) d;
        }
        throw new IllegalStateException("Missing: " + type.getSimpleName());
    }
}
