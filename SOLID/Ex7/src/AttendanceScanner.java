public class AttendanceScanner implements SmartClassroomDevice, Scannable {
    @Override public void powerOn() {}
    @Override public void powerOff() {}
    @Override public int scanAttendance() { return 3; }
}
