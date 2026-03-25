/**
 * MAIN CLASS - BookMyStayApp
 *
 * Integrated System:
 * UC5 + UC6 + UC7 + UC8 + UC9 + UC10
 */
import java.util.Scanner;

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation & Allocation\n");

        // UC9: Input + Validation
        Scanner scanner = new Scanner(System.in);
        ReservationValidator validator = new ReservationValidator();

        // Core system components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // UC8: History + Reporting
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // UC6: Allocation
        RoomAllocationService allocationService =
                new RoomAllocationService(bookingHistory);

        // UC7: Add-On Services
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // UC10: Cancellation
        CancellationService cancellationService = new CancellationService();

        try {

            // --- USER INPUT ---
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single Room/Double Room/Suite Room): ");
            String roomType = scanner.nextLine();

            // --- VALIDATION (UC9) ---
            validator.validate(guestName, roomType, inventory);

            // --- ADD TO QUEUE (UC5) ---
            bookingQueue.addRequest(new Reservation(guestName, roomType));

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());
        }

        // --- PROCESS BOOKINGS ---
        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            // UC6: Allocate
            allocationService.allocateRoom(request, inventory);

            String roomId =
                    allocationService.getRoomIdForGuest(request.getGuestName());

            if (roomId != null) {

                // UC10: Register for cancellation
                cancellationService.registerBooking(roomId, request.getRoomType());

                // UC7: Add services
                serviceManager.addService(roomId,
                        new AddOnService("Breakfast", 500));

                serviceManager.addService(roomId,
                        new AddOnService("Spa", 800));

                double total =
                        serviceManager.calculateTotalServiceCost(roomId);

                System.out.println("Add-On Cost for " + roomId + ": " + total);
            }
        }

        // --- UC8: REPORT ---
        reportService.generateReport(bookingHistory);

        // --- UC10: CANCELLATION DEMO ---
        System.out.println("\nBooking Cancellation");

        String cancelId = "Single Room-1"; // Example cancellation

        cancellationService.cancelBooking(cancelId, inventory);

        cancellationService.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailability("Single Room"));

        scanner.close();
    }
}