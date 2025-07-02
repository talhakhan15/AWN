package org.example;
import java.io.FileWriter;
import java.io.IOException;

public class BeforeMain {
    SomeClass run = new SomeClass();
    void mains() throws InterruptedException {
// Define file path for output log
        String filePath = "C:\\My Reports\\Login_results.txt";

        try (FileWriter writer = new FileWriter(filePath, true)) {  // Append mode (true)
            writer.write("Login/Logout Test Results:\n");

            for (int i = 1; i < 4; i++) {
                String logMessage = "";
                System.out.print("Trying Login/Logout #" + i + "... ");

                long startTime = System.currentTimeMillis();

                if (run.setup()) {  // Check if setup is successful
                    long setupEndTime = System.currentTimeMillis();
                    long setupTime = (setupEndTime - startTime) / 1000;  // Convert to seconds (integer)

                    long loginStartTime = System.currentTimeMillis();

                    if (run.login()) {  // Check if login is successful
                        long loginEndTime = System.currentTimeMillis();
                        long loginTime = (loginEndTime - loginStartTime) / 1000;  // Convert to seconds (integer)

                        logMessage = "Trying Login/Logout #" + i + "... Success (Setup: " + setupTime + "s, Login: " + loginTime + "s)\n";
                        System.out.println("Success (Setup: " + setupTime + "s, Login/Logout: " + loginTime + "s)");
                    } else {
                        long loginEndTime = System.currentTimeMillis();
                        long loginTime = (loginEndTime - loginStartTime) / 1000;  // Convert to seconds (integer)

                        logMessage = "Trying Login/Logout #" + i + "... Failed (Setup: " + setupTime + "s, Login: " + loginTime + "s)\n";
                        System.out.println("Failed (Setup: " + setupTime + "s, Login: " + loginTime + "s)");
                    }
                } else {
                    logMessage = "Trying Login/Logout #" + i + "... Setup Failed. Skipping Login/Logout.\n";
                    System.out.println("Setup Failed. Skipping Login/Logout.");
                }

                // Print to console & write to file
                writer.write(logMessage);
                run.driver.quit();  // Quit driver after each iteration
            }

            writer.write("Test Completed.\n \n");
            System.out.println("Test results saved to: " + filePath);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }
}
