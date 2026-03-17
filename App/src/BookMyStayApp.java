/**
 * MAIN CLASS - BookMyStayApp
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @version 6.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Queue (from UC5)
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Abhi", "Single Room"));
        bookingQueue.addRequest(new Reservation("Subha", "Single Room"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite Room"));

        // Allocation service
        RoomAllocationService allocationService = new RoomAllocationService();

        // Process requests (FIFO)
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}