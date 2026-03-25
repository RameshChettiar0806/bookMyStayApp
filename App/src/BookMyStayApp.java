/**
 * MAIN CLASS - BookMyStayApp
 *
 * Integrated:
 * UC5 + UC6 + UC7 + UC8 + UC9
 */
import java.util.Scanner;

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation & Allocation\n");

        // Scanner (UC9)
        Scanner scanner = new Scanner(System.in);

        // Validator (UC9)
        ReservationValidator validator = new ReservationValidator();

        // Core components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // UC8
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // UC6
        RoomAllocationService allocationService =
                new RoomAllocationService(bookingHistory);

        // UC7
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        try {

            // Input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single Room/Double Room/Suite Room): ");
            String roomType = scanner.nextLine();

            // Validation (UC9)
            validator.validate(guestName, roomType, inventory);

            // Add to queue (UC5)
            bookingQueue.addRequest(new Reservation(guestName, roomType));

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());
        }

        // Process queue (UC6 + UC7 + UC8)
        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            allocationService.allocateRoom(request, inventory);

            String roomId =
                    allocationService.getRoomIdForGuest(request.getGuestName());

            if (roomId != null) {

                serviceManager.addService(roomId,
                        new AddOnService("Breakfast", 500));

                serviceManager.addService(roomId,
                        new AddOnService("Spa", 800));

                double total =
                        serviceManager.calculateTotalServiceCost(roomId);

                System.out.println("Add-On Cost for " + roomId + ": " + total);
            }
        }

        // Report (UC8)
        reportService.generateReport(bookingHistory);

        scanner.close();
    }
}