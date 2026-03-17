/**
 * MAIN CLASS - BookMyStayApp
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * Demonstrates how booking requests are
 * queued and processed in FIFO order.
 *
 * @version 5.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Request Queue\n");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single Room");
        Reservation r2 = new Reservation("Subha", "Double Room");
        Reservation r3 = new Reservation("Vanmathi", "Suite Room");

        // Add to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process queue (FIFO)
        while (bookingQueue.hasPendingRequests()) {
            Reservation current = bookingQueue.getNextRequest();

            System.out.println("Processing booking for Guest: "
                    + current.getGuestName()
                    + ", Room Type: "
                    + current.getRoomType());
        }
    }
}