/**
 * MAIN CLASS - BookMyStayApp
 *
 * Integrated:
 * UC5 + UC6 + UC7 + UC8
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Abhi", "Single Room"));
        bookingQueue.addRequest(new Reservation("Subha", "Single Room"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite Room"));

        // UC8: Booking History
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // UC6: Allocation Service
        RoomAllocationService allocationService =
                new RoomAllocationService(bookingHistory);

        // UC7: Add-On Services
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            allocationService.allocateRoom(request, inventory);

            // UC7: Attach services AFTER confirmation
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

        // UC8: Generate report
        reportService.generateReport(bookingHistory);
    }
}