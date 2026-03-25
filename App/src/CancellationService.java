import java.util.*;

/**
 * CLASS - CancellationService
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * Handles booking cancellations and restores inventory.
 *
 * Uses a Stack to track rollback history.
 *
 * @version 10.0
 */
public class CancellationService {

    // Stack for rollback tracking (LIFO)
    private Stack<String> releasedRoomIds;

    // Maps reservationId → roomType
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels booking and restores inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation. Reservation not found.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Restore inventory
        int current = inventory.getAvailability(roomType);
        inventory.updateAvailability(roomType, current + 1);

        // Track rollback
        releasedRoomIds.push(reservationId);

        // Remove booking record
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: "
                + roomType);
    }

    /**
     * Displays rollback history
     */
    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations yet.");
            return;
        }

        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}