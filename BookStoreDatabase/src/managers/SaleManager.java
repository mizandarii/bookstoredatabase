package managers;

import java.time.Duration;
import java.time.LocalDateTime;

public class SaleManager {
    private final LocalDateTime targetDateTime;

    public SaleManager(LocalDateTime targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    public void start() {
        while (LocalDateTime.now().isBefore(targetDateTime)) {
            Duration remainingTime = Duration.between(LocalDateTime.now(), targetDateTime);
            long seconds = remainingTime.getSeconds();

            long days = seconds / (24 * 60 * 60);
            long hours = (seconds % (24 * 60 * 60)) / (60 * 60);
            long minutes = (seconds % (60 * 60)) / 60;
            seconds = seconds % 60;

            String formattedTime = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);

            // Print on the same line
            System.out.print("\rTime until target: " + formattedTime);

            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print a newline after the countdown is complete
        System.out.println("\rTime until target: 00:00:00:00");
    }
}
