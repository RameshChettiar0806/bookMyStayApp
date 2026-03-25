import java.util.*;

/**
 * CLASS - RoomAllocationService
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * Confirms booking requests and assigns rooms.
 *
 * @version 6.0
 */
public class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    // UC7 + UC8 additions
    private Map<String, String> reservationToRoomId;
    private BookingHistory bookingHistory;

    public RoomAllocationService(BookingHistory bookingHistory) {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
        reservationToRoomId = new HashMap<>();
        this.bookingHistory = bookingHistory;
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

        // Map reservation → roomId
        reservationToRoomId.put(reservation.getGuestName(), roomId);

        // Store in booking history (UC8)
        bookingHistory.addReservation(reservation);

        // Confirm booking
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        String roomId = roomType + "-" + count;

        while (allocatedRoomIds.contains(roomId)) {
            count++;
            roomId = roomType + "-" + count;
        }

        return roomId;
    }

    // Used by UC7
    public String getRoomIdForGuest(String guestName) {
        return reservationToRoomId.get(guestName);
    }
}