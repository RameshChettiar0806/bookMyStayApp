import java.util.Scanner;

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation, Allocation & System Recovery\n");

        Scanner input = new Scanner(System.in);

        // =============================
        // INITIALIZE CORE SERVICES
        // =============================
        ReservationValidator validator = new ReservationValidator();
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        RoomAllocationService allocationService = new RoomAllocationService(history);
        AddOnServiceManager addOnManager = new AddOnServiceManager();
        CancellationService cancellationService = new CancellationService();

        // =============================
        // UC12: FILE PERSISTENCE SETUP
        // =============================
        FilePersistenceService persistenceService = new FilePersistenceService();
        String filePath = "inventory.txt";

        System.out.println("System Recovery");

        // LOAD INVENTORY FROM FILE
        persistenceService.loadInventory(inventory, filePath);

        // SHOW CURRENT INVENTORY
        System.out.println("\nCurrent Inventory:");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));

        // =============================
        // USER INPUT (UC5)
        // =============================
        try {
            System.out.print("\nEnter guest name: ");
            String guestName = input.nextLine();

            System.out.print("Enter room type (Single Room/Double Room/Suite Room): ");
            String roomType = input.nextLine();

            validator.validate(guestName, roomType, inventory);

            bookingQueue.addRequest(new Reservation(guestName, roomType));

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // =============================
        // UC11: CONCURRENT PROCESSING
        // =============================
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory,
                        allocationService,
                        cancellationService,
                        addOnManager));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory,
                        allocationService,
                        cancellationService,
                        addOnManager));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // =============================
        // REPORT (UC8)
        // =============================
        reportService.generateReport(history);

        // =============================
        // CANCELLATION DEMO (UC10)
        // =============================
        System.out.println("\nBooking Cancellation");

        String roomId = "Single Room-1";
        cancellationService.cancelBooking(roomId, inventory);

        cancellationService.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailability("Single Room"));

        // =============================
        // UC12: SAVE FINAL STATE
        // =============================
        persistenceService.saveInventory(inventory, filePath);

        input.close();
    }
}