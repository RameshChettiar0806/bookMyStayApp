import java.util.LinkedList;
import java.util.Queue;

/**
 * CLASS - BookingRequestQueue
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * This class manages booking requests
 * using a queue to ensure fair allocation.
 *
 * @version 5.0
 */
public class BookingRequestQueue {

    // Queue storing booking requests
    private Queue<Reservation> requestQueue;

    // Constructor
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    // Get next request (FIFO)
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    // Check if queue has pending requests
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}