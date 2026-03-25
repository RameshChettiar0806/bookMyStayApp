import java.util.*;

/**
 * CLASS - RoomAllocationService
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * This class is responsible for confirming
 * booking requests and assigning rooms.
 *
 * @version 6.0
 */
public class RoomAllocationService {

    // Stores all allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds;

    // Stores assigned room IDs grouped by room type
    private Map<String, Set<String>> assignedRoomsByType;
    private Map<String, String> reservationToRoomId;
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
        reservationToRoomId = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Booking failed for Guest: "
                    + reservation.getGuestName()
                    + " (No rooms available)");
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Track globally
        allocatedRoomIds.add(roomId);

        // Track per room type
        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        int current = inventory.getAvailability(roomType);
        inventory.updateAvailability(roomType, current - 1);

        // Map reservation → roomId (IMPORTANT)
        reservationToRoomId.put(reservation.getGuestName(), roomId);

        // Confirm booking (ONLY ONCE)
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }

    /**
     * Generates a unique room ID
     */
    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        String roomId = roomType + "-" + count;

        // Extra safety (ensures no duplication globally)
        while (allocatedRoomIds.contains(roomId)) {
            count++;
            roomId = roomType + "-" + count;
        }

        return roomId;
    }

    public String getRoomIdForGuest(String guestName) {
        return reservationToRoomId.get(guestName);
    }
}

