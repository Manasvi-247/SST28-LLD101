import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var controller = new ElevatorController(new NearestElevatorStrategy());
        var listener = new ConsoleElevatorListener();

        for (int i = 1; i <= 10; i++) {
            controller.addFloor(new Floor(i));
        }

        var e1 = new Elevator("E1", 700, 1);
        var e2 = new Elevator("E2", 500, 5);
        var e3 = new Elevator("E3", 800, 10);
        e1.addListener(listener);
        e2.addListener(listener);
        e3.addListener(listener);
        controller.addElevator(e1);
        controller.addElevator(e2);
        controller.addElevator(e3);

        System.out.println("=== Initial Status ===");
        controller.printStatus();

        System.out.println("\n=== Outside Request: Floor 3 UP ===");
        controller.requestElevator(3, Direction.UP);
        for (int i = 0; i < 3; i++) controller.step();
        controller.printStatus();

        System.out.println("\n=== Inside Button: E1 -> Floor 7 ===");
        controller.pressFloorButton("E1", 7);
        for (int i = 0; i < 5; i++) controller.step();
        controller.printStatus();

        System.out.println("\n=== Outside Request: Floor 8 DOWN ===");
        controller.requestElevator(8, Direction.DOWN);
        for (int i = 0; i < 3; i++) controller.step();
        controller.printStatus();

        System.out.println("\n=== Concurrent Outside Requests ===");
        var latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                latch.await();
                controller.requestElevator(1, Direction.UP);
            } catch (Exception ex) {
                System.out.println("  Thread-1 FAILED: " + ex.getMessage());
            }
        });
        executor.submit(() -> {
            try {
                latch.await();
                controller.requestElevator(10, Direction.DOWN);
            } catch (Exception ex) {
                System.out.println("  Thread-2 FAILED: " + ex.getMessage());
            }
        });
        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        for (int i = 0; i < 5; i++) controller.step();
        controller.printStatus();

        System.out.println("\n=== Overweight Scenario: E2 ===");
        e2.addWeight(300);
        System.out.println("  Added 300kg to E2");
        e2.addWeight(250);
        System.out.println("  Added 250kg to E2 (total 550kg, limit 500kg)");
        controller.printStatus();
        System.out.println("  Removing 200kg from E2...");
        e2.removeWeight(200);
        controller.printStatus();

        System.out.println("\n=== Alarm Scenario: E1 ===");
        controller.pressAlarm("E1");
        controller.printStatus();
        System.out.println("  Clearing alarm...");
        e1.clearAlarm();
        controller.printStatus();

        System.out.println("\n=== Floor Maintenance: Floor 5 ===");
        controller.addFloorToMaintenance(5);
        System.out.println("  Floor 5 added to maintenance");
        try {
            controller.requestElevator(5, Direction.UP);
        } catch (IllegalStateException ex) {
            System.out.println("  Request to floor 5 REJECTED: " + ex.getMessage());
        }
        controller.removeFloorFromMaintenance(5);
        System.out.println("  Floor 5 removed from maintenance");
        controller.requestElevator(5, Direction.UP);
        controller.printStatus();

        System.out.println("\n=== Elevator Maintenance: E3 ===");
        controller.setElevatorMaintenance("E3", true);
        System.out.println("  E3 set to maintenance");
        controller.printStatus();
        controller.setElevatorMaintenance("E3", false);
        System.out.println("  E3 back to service");
        controller.printStatus();
    }
}
