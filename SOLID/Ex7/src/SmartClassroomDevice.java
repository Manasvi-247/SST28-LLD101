public interface SmartClassroomDevice {
    void powerOn();
    void powerOff();
}

interface Dimmable {
    void setBrightness(int pct);
}

interface TemperatureControllable {
    void setTemperatureC(int c);
}

interface Scannable {
    int scanAttendance();
}

interface InputConnectable {
    void connectInput(String port);
}
