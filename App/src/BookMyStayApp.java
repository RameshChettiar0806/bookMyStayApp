import java.util.*;

// ==========================
// CLASS: Reservation
// ==========================
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ==========================
// CLASS: BookingRequestQueue
// ==========================
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // returns null if empty
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ==========================
// CLASS: RoomInventory
// ==========================
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public boolean allocateRoom(String type) {
        int count = rooms.getOrDefault(type, 0);
        if (count > 0) {
            rooms.put(type, count - 1);
            return true;
        }
        return false;
    }

    public int getAvailable(String type) {
        return rooms.getOrDefault(type, 0);
    }

    public void printInventory() {
        System.out.println("\nRemaining Inventory:");
        System.out.println("Single: " + getAvailable("Single"));
        System.out.println("Double: " + getAvailable("Double"));
        System.out.println("Suite: " + getAvailable("Suite"));
    }
}

// ==========================
// CLASS: RoomAllocationService
// ==========================
class RoomAllocationService {

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        boolean success = inventory.allocateRoom(reservation.roomType);

        if (success) {
            System.out.println("Booking confirmed for Guest: "
                    + reservation.guestName +
                    ", Room ID: " + reservation.roomType + "-" +
                    (int)(Math.random() * 10));
        } else {
            System.out.println("Booking failed for Guest: "
                    + reservation.guestName +
                    " (No " + reservation.roomType + " rooms available)");
        }
    }
}

// ==========================
// CLASS: ConcurrentBookingProcessor
// ==========================
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {
            Reservation reservation;

            // Thread-safe queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            // Thread-safe allocation
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}

// ==========================
// MAIN CLASS
// ==========================
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        // Shared resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // Create threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService));

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Final inventory
        inventory.printInventory();
    }
}