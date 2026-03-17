/**
 * MAIN CLASS - BookMyStayApp
 *
 * Use Case 1: Application Entry & Welcome Message
 *
 * Description:
 * This class represents the entry point of the
 * Hotel Booking Management System (BookMyStay).
 *
 * At this stage, the application:
 * - Starts execution from the main() method
 * - Displays a welcome message to the user
 * - Confirms that the system has started successfully
 *
 * No business logic, data structures, or user input
 * is implemented in this use case.
 *
 * The goal is to establish a clear and predictable
 * application startup point.
 *
 * @author Ramesh
 * @version 1.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * This method is the first method executed
     * when the program is launched by the JVM.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // Welcome message
        System.out.println("Welcome to BookMyStay - Hotel Booking Management System");

        // System status
        System.out.println("System initialized successfully.");

        // Version info
        System.out.println("Application Version: v1.0");
    }
}